package com.mystchonky.arsoscura.common.glyphs;

import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import static com.mystchonky.arsoscura.ArsOscura.prefix;

public class AugmentMimic extends AbstractAugment {
    private static final String id = GlyphLib.prependGlyph("mimic");
    public static AugmentMimic INSTANCE = new AugmentMimic();

    public AugmentMimic() {
        super(prefix(id), "Mimic last augment");
    }

    @Override
    public SpellStats.Builder applyModifiers(SpellStats.Builder builder, AbstractSpellPart spellPart, HitResult rayTraceResult, Level world, @Nullable LivingEntity shooter, SpellContext spellContext) {
        Spell spell = spellContext.getSpell();
        AbstractSpellPart part = spell.recipe.get(spellContext.getCurrentIndex() - 1);
        if (part instanceof AbstractAugment augment) {
            augment.applyModifiers(builder, spellPart);
        }
        return super.applyModifiers(builder, spellPart);
    }

    @Override
    public int getDefaultManaCost() {
        return 0;
    }
}
