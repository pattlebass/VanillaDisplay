package com.pattlebass.vanilladisplay.entity

import net.minecraft.entity.EntityType
import net.minecraft.text.Text
import net.minecraft.world.World

class VideoDisplayEntity(
    type: EntityType<out TextDisplayEntity>,
    world: World,
    scale: Float,
    private var tpf: Int,
    private var folderPath: String,
    private var frameCount: Int
) : AbstractImageDisplayEntity(type, world, scale) {

    private var tickCounter = 0
    private var frame = 0
    private var textFrames: MutableList<Text> = mutableListOf()

    init {
        textFrames = mutableListOf()
        for (i in 0..<frameCount) {
            val img = loadImage(folderPath + "${"%03d".format(i)}.jpg")
            if (img == null) {
                textFrames.add(Text.empty())
            } else {
                textFrames.add(getImageText(img))
            }
        }
    }

    override fun tick() {
        super.tick()
        if (!entityWorld.isClient) {
            if (tickCounter.mod(tpf) == 0 && textFrames.isNotEmpty()) {
                frame = (frame + 1).mod(textFrames.size)
                text = textFrames[frame]
            }
            tickCounter++
        }
    }
}
