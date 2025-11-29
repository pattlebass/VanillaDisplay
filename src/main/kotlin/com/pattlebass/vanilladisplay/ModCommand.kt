package com.pattlebass.vanilladisplay

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.FloatArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import com.pattlebass.vanilladisplay.entity.ImageDisplayEntity
import com.pattlebass.vanilladisplay.entity.SourceDisplayEntity
import com.pattlebass.vanilladisplay.entity.VideoDisplayEntity
import net.minecraft.command.argument.Vec3ArgumentType
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.ClickEvent
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d

object ModCommand {
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register(
            CommandManager.literal("vanilla_display")
                .then(createVideo())
                .then(createImage())
                .then(createSource())
        )
    }

    private fun createVideo(): LiteralArgumentBuilder<ServerCommandSource> {
        return CommandManager.literal("create_video")
            .then(CommandManager.argument("pos", Vec3ArgumentType.vec3(true))
                .then(CommandManager.argument("folder_path", StringArgumentType.string())
                    .then(CommandManager.argument("frames", IntegerArgumentType.integer())
                        .executes { context ->
                            val pos = Vec3ArgumentType.getVec3(context, "pos")
                            val path = StringArgumentType.getString(context, "folder_path")
                            val frames = IntegerArgumentType.getInteger(context, "frames")

                            summonVideoDisplayEntity(context, pos, path, frames, 0.1f, 2)
                        }
                        .then(CommandManager.argument("scale", FloatArgumentType.floatArg())
                            .executes { context ->
                                val pos = Vec3ArgumentType.getVec3(context, "pos")
                                val path = StringArgumentType.getString(context, "folder_path")
                                val frames = IntegerArgumentType.getInteger(context, "frames")
                                val scale = FloatArgumentType.getFloat(context, "scale")

                                summonVideoDisplayEntity(context, pos, path, frames, scale, 2)
                            }
                            .then(CommandManager.argument("tpf", IntegerArgumentType.integer())
                                .executes { context ->
                                    val pos = Vec3ArgumentType.getVec3(context, "pos")
                                    val path = StringArgumentType.getString(context, "folder_path")
                                    val frames = IntegerArgumentType.getInteger(context, "frames")
                                    val scale = FloatArgumentType.getFloat(context, "scale")
                                    val tpf = IntegerArgumentType.getInteger(context, "tpf")

                                    summonVideoDisplayEntity(context, pos, path, frames, scale, tpf)
                                }
                            )
                        )
                    )
                )
            )
    }

    private fun createImage(): LiteralArgumentBuilder<ServerCommandSource> {
        return CommandManager.literal("create_image")
            .then(CommandManager.argument("pos", Vec3ArgumentType.vec3(true))
                .then(CommandManager.argument("path", StringArgumentType.string())
                    .executes { context ->
                        val pos = Vec3ArgumentType.getVec3(context, "pos")
                        val path = StringArgumentType.getString(context, "path")

                        summonImageDisplayEntity(context, pos, path, 0.1f)
                    }
                    .then(CommandManager.argument("scale", FloatArgumentType.floatArg())
                        .executes { context ->
                            val pos = Vec3ArgumentType.getVec3(context, "pos")
                            val path = StringArgumentType.getString(context, "path")
                            val scale = FloatArgumentType.getFloat(context, "scale")

                            summonImageDisplayEntity(context, pos, path, scale)
                        }
                    )
                )
            )
    }

    private fun createSource(): LiteralArgumentBuilder<ServerCommandSource> {
        return CommandManager.literal("create_source")
            .then(CommandManager.argument("pos", Vec3ArgumentType.vec3(true))
                .then(CommandManager.argument("path", StringArgumentType.string())
                    .executes { context ->
                        val pos = Vec3ArgumentType.getVec3(context, "pos")
                        val path = StringArgumentType.getString(context, "path")

                        summonSourceDisplayEntity(context, pos, path, 0.1f, 2)
                    }
                    .then(CommandManager.argument("scale", FloatArgumentType.floatArg())
                        .executes { context ->
                            val pos = Vec3ArgumentType.getVec3(context, "pos")
                            val path = StringArgumentType.getString(context, "path")
                            val scale = FloatArgumentType.getFloat(context, "scale")

                            summonSourceDisplayEntity(context, pos, path, scale, 2)
                        }
                        .then(CommandManager.argument("tpf", IntegerArgumentType.integer())
                            .executes { context ->
                                val pos = Vec3ArgumentType.getVec3(context, "pos")
                                val path = StringArgumentType.getString(context, "path")
                                val scale = FloatArgumentType.getFloat(context, "scale")
                                val tpf = IntegerArgumentType.getInteger(context, "tpf")

                                summonSourceDisplayEntity(context, pos, path, scale, tpf)
                            }
                        )
                    )
                )
            )
    }

    private fun summonVideoDisplayEntity(
        context: CommandContext<ServerCommandSource>,
        pos: Vec3d,
        path: String,
        frames: Int,
        scale: Float,
        tpf: Int
    ): Int {
        val world = context.source.world

        val entity = VideoDisplayEntity(EntityType.TEXT_DISPLAY, world, scale, tpf, path, frames)
        entity.updatePosition(pos.x, pos.y, pos.z)

        world.spawnEntity(entity)
        sendUUID(context, entity)

        return 1
    }

    private fun summonImageDisplayEntity(
        context: CommandContext<ServerCommandSource>,
        pos: Vec3d,
        path: String,
        scale: Float
    ): Int {
        val world = context.source.world

        val entity = ImageDisplayEntity(EntityType.TEXT_DISPLAY, world, scale, path)
        entity.updatePosition(pos.x, pos.y, pos.z)

        world.spawnEntity(entity)
        sendUUID(context, entity)

        return 1
    }

    private fun summonSourceDisplayEntity(
        context: CommandContext<ServerCommandSource>,
        pos: Vec3d,
        path: String,
        scale: Float,
        tpf: Int
    ): Int {
        val world = context.source.world

        val entity = SourceDisplayEntity(EntityType.TEXT_DISPLAY, world, scale, tpf, path)
        entity.updatePosition(pos.x, pos.y, pos.z)

        world.spawnEntity(entity)
        sendUUID(context, entity)

        return 1
    }

    private fun sendUUID(context: CommandContext<ServerCommandSource>, entity: Entity) {
        val uuid = entity.uuidAsString
        val msg = Text.literal("Created display entity with UUID: ")
            .append(
                Text.literal(uuid)
                    .styled { style: Style ->
                        style.withClickEvent(ClickEvent.CopyToClipboard(uuid)).withUnderline(true)
                    }
            )
        context.getSource().sendMessage(msg)
    }

}
