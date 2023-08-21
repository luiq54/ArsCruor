package com.mystchonky.arsoscura.integration.bloodmagic.glyphs;

import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.spell.augment.*;
import com.hollingsworth.arsnouveau.setup.registry.ModPotions;
import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.glyphs.GlyphLibrary;
import com.mystchonky.arsoscura.integration.bloodmagic.BloodMagicIntegration;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import wayoftime.bloodmagic.api.compat.EnumDemonWillType;
import wayoftime.bloodmagic.common.item.soul.ItemSentientSword;
import wayoftime.bloodmagic.potion.BloodMagicPotions;
import wayoftime.bloodmagic.will.PlayerDemonWillHandler;

import java.util.Set;

public class EffectSentientHarm extends AbstractEffect implements IDamageEffect {

    public static EffectSentientHarm INSTANCE = new EffectSentientHarm();

    public EffectSentientHarm() {
        super(ArsOscura.prefix(GlyphLibrary.EffectSentientHarm), "Sentient Harm");
    }

    @Override
    public void onResolveEntity(EntityHitResult rayTraceResult, Level world, @NotNull LivingEntity shooter, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        if (shooter instanceof Player player) {
            EnumDemonWillType type = PlayerDemonWillHandler.getLargestWillType(player);
            int souls = (int) PlayerDemonWillHandler.getTotalDemonWill(type, player);


            int bracket = getBracket(type, souls);
            Entity entity = rayTraceResult.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                int time = spellStats.getDurationInTicks();
                float damage = (float) (4.0f + (2.0f * getExtraDamage(spellContext, type, souls)) + (2.0f * spellStats.getAmpMultiplier()));
                livingEntity.addEffect(new MobEffectInstance(BloodMagicPotions.SOUL_SNARE.get(), 300, 0));

                switch (type) {
                    case CORROSIVE:
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, (time > 0) ? (ItemSentientSword.poisonTime[bracket] * time) : (ItemSentientSword.poisonTime[bracket]), ItemSentientSword.poisonLevel[bracket] + 1));
                        break;
                    case DEFAULT:
                        break;
                    case DESTRUCTIVE:
                        break;
                    case VENGEFUL:
                        if ((livingEntity.getHealth() < damage)) {
                            player.addEffect(new MobEffectInstance(ModPotions.MANA_REGEN_EFFECT.get(), (time > 0) ? (ItemSentientSword.absorptionTime[bracket] * time) : (ItemSentientSword.absorptionTime[bracket]), ItemSentientSword.absorptionTime[bracket], false, false));
                        }
                        break;
                    case STEADFAST:
                        if ((livingEntity).getHealth() < damage) {
                            float absorption = player.getAbsorptionAmount();
                            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, (time > 0) ? (ItemSentientSword.absorptionTime[bracket] * time) : (ItemSentientSword.absorptionTime[bracket]), 127, false, false));
                            player.setAbsorptionAmount((float) Math.min(absorption + ((LivingEntity) entity).getMaxHealth() * 0.25f, ItemSentientSword.maxAbsorptionHearts));
                        }
                        break;
                }


                // TODO: Change to bloodmagic damage source later
                attemptDamage(world, shooter, spellStats, spellContext, resolver, entity, buildDamageSource(world, shooter), damage);
            }
        }

    }

    public float getExtraDamage(SpellContext spellContext, EnumDemonWillType type, int souls) {


        int bracket = getBracket(type, souls);
        if (bracket < 0) {
            return 0;
        }
        switch (type) {
            case CORROSIVE:
            case DEFAULT:
                return (float) ItemSentientSword.defaultDamageAdded[bracket];
            case DESTRUCTIVE:
                return (float) ItemSentientSword.destructiveDamageAdded[bracket];
            case VENGEFUL:
                return (float) ItemSentientSword.vengefulDamageAdded[bracket];
            case STEADFAST:
                return (float) ItemSentientSword.steadfastDamageAdded[bracket];
        }
        return 0;
    }

    public int getBracket(EnumDemonWillType type, int souls) {
        int bracket = -1;
        for (int i = 0; i < ItemSentientSword.soulBracket.length; i++) {
            if (souls >= ItemSentientSword.soulBracket[i]) {
                bracket = i;
            }
        }
        return bracket;
    }


    @Override
    public int getDefaultManaCost() {
        return 50;
    }


    @Override
    public SpellTier defaultTier() {
        return SpellTier.ONE;
    }

    @Override
    public Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf(
                AugmentAmplify.INSTANCE, AugmentDampen.INSTANCE,
                AugmentExtendTime.INSTANCE, AugmentDurationDown.INSTANCE,
                AugmentFortune.INSTANCE
        );
    }

    @Override
    public String getBookDescription() {
        return "An advanced spell, that utilizes your collected demonic will to improve your damage output.";
    }

    public Set<SpellSchool> getSchools() {
        return setOf(BloodMagicIntegration.BLOODMAGIC);
    }

}
