package com.mystchonky.arsoscura.integration.bloodmagic.glyphs

import com.hollingsworth.arsnouveau.api.spell.*
import com.hollingsworth.arsnouveau.common.spell.augment.*
import com.hollingsworth.arsnouveau.setup.registry.ModPotions
import com.mystchonky.arsoscura.ArsOscura.prefix
import com.mystchonky.arsoscura.common.glyphs.GlyphLibrary
import com.mystchonky.arsoscura.integration.bloodmagic.BloodMagicIntegration
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.phys.EntityHitResult
import wayoftime.bloodmagic.api.compat.EnumDemonWillType
import wayoftime.bloodmagic.common.item.soul.ItemSentientSword
import wayoftime.bloodmagic.potion.BloodMagicPotions
import wayoftime.bloodmagic.will.PlayerDemonWillHandler

class EffectSentientHarm : AbstractEffect(prefix(GlyphLibrary.EffectSentientHarm), "Sentient Harm"), IDamageEffect {
    companion object {
        var INSTANCE = EffectSentientHarm()
    }

    override fun onResolveEntity(
        rayTraceResult: EntityHitResult,
        world: Level,
        shooter: LivingEntity,
        spellStats: SpellStats,
        spellContext: SpellContext,
        resolver: SpellResolver
    ) {
        if (shooter is Player) {
            val type = PlayerDemonWillHandler.getLargestWillType(shooter)
            val souls = PlayerDemonWillHandler.getTotalDemonWill(type, shooter).toInt()
            val bracket = getBracket(type, souls)
            val entity = rayTraceResult.entity
            if (entity is LivingEntity) {
                val time = spellStats.durationInTicks
                val damage = (4.0f + 2.0f * getExtraDamage(
                    spellContext,
                    type,
                    souls
                ) + 2.0f * spellStats.ampMultiplier).toFloat()
                entity.addEffect(MobEffectInstance(BloodMagicPotions.SOUL_SNARE.get(), 300, 0))
                when (type) {
                    EnumDemonWillType.CORROSIVE -> entity.addEffect(
                        MobEffectInstance(
                            MobEffects.WITHER,
                            ItemSentientSword.poisonTime[bracket] * (if (time > 0) time else 1),
                            ItemSentientSword.poisonLevel[bracket] + 1
                        )
                    )

                    EnumDemonWillType.DEFAULT -> {}
                    EnumDemonWillType.DESTRUCTIVE -> {}
                    EnumDemonWillType.VENGEFUL -> if (entity.health < damage) {
                        shooter.addEffect(
                            MobEffectInstance(
                                ModPotions.MANA_REGEN_EFFECT.get(),
                                ItemSentientSword.absorptionTime[bracket] * (if (time > 0) time else 1),
                                ItemSentientSword.absorptionTime[bracket],
                                false,
                                false
                            )
                        )
                    }

                    EnumDemonWillType.STEADFAST -> if (entity.health < damage) {
                        val absorption = shooter.getAbsorptionAmount()
                        shooter.addEffect(
                            MobEffectInstance(
                                MobEffects.ABSORPTION,
                                ItemSentientSword.absorptionTime[bracket] * (if (time > 0) time else 1),
                                127,
                                false,
                                false
                            )
                        )
                        shooter.setAbsorptionAmount(
                            Math.min(
                                (absorption + entity.maxHealth * 0.25f).toDouble(),
                                ItemSentientSword.maxAbsorptionHearts
                            ).toFloat()
                        )
                    }

                    null -> {}
                }


                // TODO: Change to bloodmagic damage source later
                attemptDamage(
                    world,
                    shooter,
                    spellStats,
                    spellContext,
                    resolver,
                    entity,
                    buildDamageSource(world, shooter),
                    damage
                )
            }
        }
    }

    fun getExtraDamage(spellContext: SpellContext, type: EnumDemonWillType, souls: Int): Float {
        val bracket = getBracket(type, souls)
        if (bracket < 0) {
            return 0f
        }
        return when (type) {
            EnumDemonWillType.CORROSIVE, EnumDemonWillType.DEFAULT -> ItemSentientSword.defaultDamageAdded[bracket].toFloat()
            EnumDemonWillType.DESTRUCTIVE -> ItemSentientSword.destructiveDamageAdded[bracket].toFloat()
            EnumDemonWillType.VENGEFUL -> ItemSentientSword.vengefulDamageAdded[bracket].toFloat()
            EnumDemonWillType.STEADFAST -> ItemSentientSword.steadfastDamageAdded[bracket].toFloat()
        }
    }

    fun getBracket(type: EnumDemonWillType, souls: Int): Int {
        var bracket = -1
        for (i in ItemSentientSword.soulBracket.indices) {
            if (souls >= ItemSentientSword.soulBracket[i]) {
                bracket = i
            }
        }
        return bracket
    }

    public override fun getDefaultManaCost(): Int {
        return 50
    }

    override fun defaultTier(): SpellTier {
        return SpellTier.ONE
    }

    public override fun getCompatibleAugments(): Set<AbstractAugment> {
        return augmentSetOf(
            AugmentAmplify.INSTANCE,
            AugmentDampen.INSTANCE,
            AugmentExtendTime.INSTANCE,
            AugmentDurationDown.INSTANCE,
            AugmentFortune.INSTANCE
        )
    }

    override fun getBookDescription(): String {
        return "An advanced spell, that utilizes your collected demonic will to improve your damage output."
    }

    public override fun getSchools(): Set<SpellSchool> {
        return setOf(BloodMagicIntegration.BLOODMAGIC)
    }

}
