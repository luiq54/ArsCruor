package com.mystchonky.arsoscura.common.glyphs

import com.hollingsworth.arsnouveau.api.spell.AbstractAugment
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart
import com.hollingsworth.arsnouveau.api.spell.SpellContext
import com.hollingsworth.arsnouveau.api.spell.SpellStats
import com.hollingsworth.arsnouveau.common.lib.GlyphLib
import com.mystchonky.arsoscura.ArsOscura
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.phys.HitResult

class AugmentMimic : AbstractAugment(id, "Mimic last augment") {
    companion object INSTANCE {
        private val id = ArsOscura.prefix(GlyphLib.prependGlyph("mimic"))
    }

    override fun applyModifiers(
        builder: SpellStats.Builder,
        spellPart: AbstractSpellPart,
        rayTraceResult: HitResult,
        world: Level,
        shooter: LivingEntity?,
        spellContext: SpellContext
    ): SpellStats.Builder {
        val spell = spellContext.spell
        val part = spell.recipe[spellContext.currentIndex - 1]
        (part as? AbstractAugment)?.applyModifiers(builder, spellPart)
        return super.applyModifiers(builder, spellPart)
    }

    public override fun getDefaultManaCost(): Int {
        return 0
    }

}
