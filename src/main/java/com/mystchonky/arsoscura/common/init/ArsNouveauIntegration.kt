package com.mystchonky.arsoscura.common.init

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder
import com.hollingsworth.arsnouveau.api.registry.FamiliarRegistry
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart
import com.mystchonky.arsoscura.integration.bloodmagic.BloodMagicIntegration
import com.mystchonky.arsoscura.integration.occultism.OccultismIntegration
import net.minecraftforge.fml.ModList

object ArsNouveauIntegration {
    var registeredSpells: MutableList<AbstractSpellPart> = ArrayList()
    var registeredFamiliars: MutableList<AbstractFamiliarHolder> = ArrayList()
    fun init() {
        registerGlyphs()
        registerFamiliars()
    }

    fun postInit() {
        registerSounds()
    }

    private fun registerGlyphs() {
        if (ModList.get().isLoaded("bloodmagic"))
            BloodMagicIntegration.registerGlyphs { registerSpellPart(it) }
    }

    private fun registerFamiliars() {
        if (ModList.get().isLoaded("occultism"))
            OccultismIntegration.registerFamiliars { registerFamiliars(it) }
    }

    private fun registerSounds() {}
    private fun registerSpellPart(spellPart: AbstractSpellPart) {
        GlyphRegistry.registerSpell(spellPart)
        registeredSpells.add(spellPart)
    }

    private fun registerFamiliars(familiarHolder: AbstractFamiliarHolder) {
        FamiliarRegistry.registerFamiliar(familiarHolder)
        registeredFamiliars.add(familiarHolder)
    }
}
