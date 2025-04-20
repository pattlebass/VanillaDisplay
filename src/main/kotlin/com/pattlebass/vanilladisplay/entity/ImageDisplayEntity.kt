package com.pattlebass.vanilladisplay.entity

import net.minecraft.entity.EntityType
import net.minecraft.world.World

class ImageDisplayEntity (
    type: EntityType<out TextDisplayEntity>,
    world: World,
    scale: Float,
    private var path: String,
) : AbstractImageDisplayEntity(type, world, scale)
{
    init {
        val frame = loadImage(path)
        if (frame != null) {
            text = getImageText(frame)
        }
    }
}
