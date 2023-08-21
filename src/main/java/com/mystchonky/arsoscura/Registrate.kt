package com.mystchonky.arsoscura

import com.tterrag.registrate.AbstractRegistrate
import net.minecraftforge.eventbus.api.IEventBus
import thedarkcolour.kotlinforforge.forge.MOD_BUS

class Registrate(modid: String) : AbstractRegistrate<Registrate>(modid) {
    companion object {
        fun create(modid: String) = Registrate(modid).apply {
            registerEventListeners(MOD_BUS)
        }
    }

    override fun getModEventBus(): IEventBus {
        return MOD_BUS
    }
}