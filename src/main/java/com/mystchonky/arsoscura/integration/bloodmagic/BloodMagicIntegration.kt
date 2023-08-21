package com.mystchonky.arsoscura.integration.bloodmagic

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart
import com.hollingsworth.arsnouveau.api.spell.SpellSchool
import com.mystchonky.arsoscura.integration.bloodmagic.glyphs.EffectSentientHarm
import com.mystchonky.arsoscura.integration.bloodmagic.init.BloodMagicItems
import com.mystchonky.arsoscura.integration.bloodmagic.init.BloodMagicMobEffects
import com.mystchonky.arsoscura.integration.bloodmagic.init.LivingUpgradeRegistry
import net.minecraftforge.common.MinecraftForge
import java.util.function.Consumer

object BloodMagicIntegration {
    var BLOODMAGIC = SpellSchool("bloodmagic")
    fun init() {
        BloodMagicItems.register()
        LivingUpgradeRegistry.register()
        BloodMagicMobEffects.register()
        MinecraftForge.EVENT_BUS.register(EventHandler::class)
    }

    fun registerGlyphs(register: Consumer<AbstractSpellPart>) {
        register.accept(EffectSentientHarm.INSTANCE)
    }
}
