package com.mystchonky.arsoscura.integration.bloodmagic.init

import com.mystchonky.arsoscura.ArsOscura
import com.mystchonky.arsoscura.integration.bloodmagic.mobeffects.SereneEffect
import com.tterrag.registrate.util.entry.RegistryEntry
import net.minecraft.world.effect.MobEffect
import net.minecraftforge.registries.ForgeRegistries

object BloodMagicMobEffects {
    private val REGISTRATE = ArsOscura.registrate()
    val SERENE_EFFECT: RegistryEntry<MobEffect> =
        REGISTRATE.simple<MobEffect, MobEffect>("serene", ForgeRegistries.MOB_EFFECTS.registryKey) { SereneEffect() }

    fun register() {}
}
