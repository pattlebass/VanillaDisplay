package com.pattlebass.vanilladisplay.entity

import net.minecraft.entity.EntityType
import net.minecraft.world.World

class SourceDisplayEntity(
    type: EntityType<out TextDisplayEntity>,
    world: World,
    scale: Float,
    private var tpf: Int,
    private var path: String,
) : AbstractImageDisplayEntity(type, world, scale) {

    private var tickCounter = 0

    override fun tick() {
        super.tick()
        if (!entityWorld.isClient) {
            if (tickCounter.mod(tpf) == 0) {
                val frame = loadImage(path)
                if (frame != null) {
                    text = getImageText(frame)
                }
            }
            tickCounter++
        }
    }
}
