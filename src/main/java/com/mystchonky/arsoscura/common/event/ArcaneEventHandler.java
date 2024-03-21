package com.mystchonky.arsoscura.common.event;

import com.mystchonky.arsoscura.common.enchantment.EnchantmentManager;
import com.mystchonky.arsoscura.common.mana.ManaManager;
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
            EnchantmentManager.getArcaneEnchantment(stack).ifPresent(enchant -> {
                if (!ManaManager.enoughMana(player, enchant.getCastingCost(player, stack))) {
                    event.setCanceled(true);
                }
            });
        }
    }

    @SubscribeEvent
    public static void livingUseStop(final LivingEntityUseItemEvent.Stop event) {
        if (!(event.getEntity() instanceof Player player)) return;

        ItemStack stack = event.getItem();
        if (stack.getItem() instanceof TridentItem) {
            if (EnchantmentManager.getTorrent(stack) > 0 && player.isInWaterOrRain()) return;

            EnchantmentManager.getArcaneEnchantment(stack).ifPresent(enchant -> {
                ManaManager.consumeMana(player, enchant.getCastingCost(player, stack));
            });
        }
    }
}
