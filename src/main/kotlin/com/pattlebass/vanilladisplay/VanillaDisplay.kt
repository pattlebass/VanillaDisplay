package com.pattlebass.vanilladisplay

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import org.slf4j.LoggerFactory


class VanillaDisplay : ModInitializer {
    override fun onInitialize() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            ModCommand.register(dispatcher)
        }
    }

    companion object {
        val MOD_ID = "vanilladisplay"
        val LOGGER = LoggerFactory.getLogger(MOD_ID)
    }
}
