package com.mystchonky.arsoscura.common.init

import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry
import com.mystchonky.arsoscura.ArsOscura
import net.minecraft.world.item.CreativeModeTab

object ArsOscuraItems {
    private val REGISTRATE = ArsOscura.registrate()
    val ARS_OSCURA_TAB = REGISTRATE.defaultCreativeTab("ars_oscura") { tab: CreativeModeTab.Builder ->
        tab.icon { ItemsRegistry.WAND.get().defaultInstance }.build()
    }.register()

    fun register() {}
}
