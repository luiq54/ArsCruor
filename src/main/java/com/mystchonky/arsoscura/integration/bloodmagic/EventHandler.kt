package com.mystchonky.arsoscura.integration.bloodmagic;

import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.event.SpellCostCalcEvent;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import wayoftime.bloodmagic.core.living.LivingStats;
import wayoftime.bloodmagic.core.living.LivingUtil;
import wayoftime.bloodmagic.event.SacrificeKnifeUsedEvent;

import static com.hollingsworth.arsnouveau.api.util.ManaUtil.getPlayerDiscounts;

class EventHandler {
    @SubscribeEvent
    public static void sacrificeKnifeUsed(SacrificeKnifeUsedEvent event) {
        if (event.player.hasEffect(BloodMagicMobEffects.SERENE_EFFECT.get())) {
            event.lpAdded *= 1.1;
        }
    }

    @SubscribeEvent
    public static void spellDiscount(SpellCostCalcEvent event) {
        if (event.context.getUnwrappedCaster() instanceof Player player) {
            if (LivingUtil.hasFullSet(player)) {
                LivingStats stats = LivingStats.fromPlayer(player);
                int level = stats.getLevel(LivingUpgradeRegistry.MANA_UPGRADE.getKey());
                float discount = level / 10;
                event.currentCost *= 1 - discount;
            }
        }

    }

    @SubscribeEvent
    public static void awardXPForSpellCast(SpellCastEvent event) {
        if (event.context.getUnwrappedCaster() instanceof Player player) {
            if (LivingUtil.hasFullSet(player)) {
                Spell spell = event.spell;
                SpellContext spellContext = event.context;

                int cost = spellContext.getSpell().getCost() - getPlayerDiscounts(spellContext.getUnwrappedCaster(), spell, spellContext.getCasterTool());
                cost = Math.max(cost, 0);
                int xpAward = cost / 50;
                LivingUtil.applyNewExperience(player, LivingUpgradeRegistry.MANA_UPGRADE, xpAward);
            }
        }
    }
}
