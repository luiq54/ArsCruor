package com.mystchonky.arsoscura.integration.bloodmagic

import com.hollingsworth.arsnouveau.api.event.SpellCastEvent
import com.hollingsworth.arsnouveau.api.event.SpellCostCalcEvent
import com.hollingsworth.arsnouveau.api.util.ManaUtil
import com.mystchonky.arsoscura.integration.bloodmagic.init.BloodMagicMobEffects
import com.mystchonky.arsoscura.integration.bloodmagic.init.LivingUpgradeRegistry
import net.minecraft.world.entity.player.Player
import net.minecraftforge.eventbus.api.SubscribeEvent
import wayoftime.bloodmagic.core.living.LivingStats
import wayoftime.bloodmagic.core.living.LivingUtil
import wayoftime.bloodmagic.event.SacrificeKnifeUsedEvent

internal object EventHandler {
    @SubscribeEvent
    fun sacrificeKnifeUsed(event: SacrificeKnifeUsedEvent) {
        if (event.player.hasEffect(BloodMagicMobEffects.SERENE_EFFECT.get())) {
            event.lpAdded = (event.lpAdded * 1.1).toInt()
        }
    }

    @SubscribeEvent
    fun spellDiscount(event: SpellCostCalcEvent) {
        val caster = event.context.unwrappedCaster
        if (caster is Player) {
            if (LivingUtil.hasFullSet(caster)) {
                val stats = LivingStats.fromPlayer(caster)
                val level = stats.getLevel(LivingUpgradeRegistry.MANA_UPGRADE.key)
                val discount = (level / 10).toFloat()
                event.currentCost = (event.currentCost * (1 - discount)).toInt()
            }
        }
    }

    @SubscribeEvent
    fun awardXPForSpellCast(event: SpellCastEvent) {
        val caster = event.context.unwrappedCaster
        if (caster is Player) {
            if (LivingUtil.hasFullSet(caster)) {
                val spell = event.spell
                val spellContext = event.context
                var cost = spellContext.spell.cost - ManaUtil.getPlayerDiscounts(
                    spellContext.unwrappedCaster,
                    spell,
                    spellContext.casterTool
                )
                cost = Math.max(cost, 0)
                val xpAward = cost / 50
                LivingUtil.applyNewExperience(caster, LivingUpgradeRegistry.MANA_UPGRADE, xpAward.toDouble())
            }
        }
    }
}
