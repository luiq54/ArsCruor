package com.mystchonky.arsoscura.common.init

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart
import com.mystchonky.arsoscura.ArsOscura
import com.mystchonky.arsoscura.ArsOscura.registrate
import com.mystchonky.arsoscura.integration.bloodmagic.BloodMagicIntegration
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import java.util.function.Consumer

object ArsOscuraLang {
    private val REGISTRATE = registrate()
    val prefix: (String) -> ResourceLocation = { ArsOscura.prefix(it) }
    val SIGIL_EMPTY: Component = REGISTRATE.addLang("tooltip", prefix("sigil_empty"), "No entity stored")
    val SIGIL_WITH_ENTITY: MutableComponent =
        REGISTRATE.addLang("tooltip", prefix("sigil_with_entity"), "Entity stored: %s")

    // Blood Magic Integration
    val SCHOOL_BLOODMAGIC: Component =
        REGISTRATE.addRawLang("ars_nouveau.school." + BloodMagicIntegration.BLOODMAGIC.id, "Blood Magic")
    val LOW_LP: Component = REGISTRATE.addLang("alert", prefix("no_lp"), "Your soul feels weak..")
    val SERENE_EFFECT: Component = REGISTRATE.addLang("effect", prefix("serene"), "Serene")
    val MANA_BONUS_UPGRADE: Component = REGISTRATE.addLang("living_upgrade", prefix("mana_bonus"), "Mana Attunement")


    fun register() {
        ArsNouveauIntegration.registeredSpells.forEach(Consumer { spell: AbstractSpellPart ->
            REGISTRATE.addRawLang(ArsOscura.MODID + ".glyph_name." + spell.registryName.path, spell.getName())
            REGISTRATE.addRawLang(ArsOscura.MODID + ".glyph_desc." + spell.registryName.path, spell.bookDescription)
        })
    }
}
