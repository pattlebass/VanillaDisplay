package com.pattlebass.vanilladisplay.entity

import com.pattlebass.vanilladisplay.VanillaDisplay
import net.minecraft.entity.EntityType
import net.minecraft.entity.decoration.DisplayEntity
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.AffineTransformation
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import org.joml.Vector3f
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

abstract class AbstractImageDisplayEntity(
    type: EntityType<out TextDisplayEntity>,
    world: World,
    private var scale: Float,
) : DisplayEntity.TextDisplayEntity(type, world) {

    private val MAX_SCREEN_WIDTH = 256
    private val MAX_SCREEN_HEIGHT = 256

    init {
        val transform = AffineTransformation(
            null,  // No rotation
            null,  // No translation
            Vector3f(scale, scale, scale),  // Scale X, Y, Z
            null // No shear
        )

        setTransformation(transform)
        background = 0
        lineWidth = 100000
    }

    override fun shouldSave(): Boolean {
        return false
    }

    protected fun loadImage(path: String): BufferedImage? {
        return try {
            val file = File(path)
            if (!file.exists()) {
                VanillaDisplay.LOGGER.warn("Image file not found: $path")
                return null
            }

            // Read image into BufferedImage
            val image = ImageIO.read(file)
            image
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    protected fun getImageText(image: BufferedImage) : Text {
        val text = Text.empty().setStyle(Style.EMPTY.withFont(Identifier.of(VanillaDisplay.MOD_ID, "default")))
        for (y in 0..<MathHelper.clamp(image.height, 0, MAX_SCREEN_HEIGHT)) {
            for (x in 0..<MathHelper.clamp(image.width, 0, MAX_SCREEN_WIDTH)) {
                text.append(Text.literal("█\u200c").setStyle(Style.EMPTY.withColor(image.getRGB(x, y))))
            }
            text.append("\n")
        }
        return text
    }
}
