package com.mystchonky.arsoscura.common.events;

import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.mystchonky.arsoscura.common.util.EnchantmentUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ArcaneEventHandler {

    @SubscribeEvent
    public static void livingUseStart(final LivingEntityUseItemEvent.Start event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack stack = event.getItem();
        if (stack.getItem() instanceof TridentItem) {
            EnchantmentUtil.getArcaneEnchantment(stack).ifPresent(enchant -> {
                int manaCost = enchant.getCastingCost(player, stack);
                CapabilityRegistry.getMana(player).ifPresent(manaCap -> {
                    if (manaCap.getCurrentMana() < manaCost) {
                        event.setCanceled(true);
                    }
                });
            });
        }
    }

    @SubscribeEvent
    public static void livingUseStop(final LivingEntityUseItemEvent.Stop event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack stack = event.getItem();
        if (stack.getItem() instanceof TridentItem) {
            if (EnchantmentUtil.getTorrent(stack) > 0 && player.isInWaterOrRain()) return;

            EnchantmentUtil.getArcaneEnchantment(stack).ifPresent(enchant -> {
                CapabilityRegistry.getMana(player).ifPresent(manaCap -> manaCap.removeMana(enchant.getCastingCost(player, stack)));
            });
        }
    }
}
