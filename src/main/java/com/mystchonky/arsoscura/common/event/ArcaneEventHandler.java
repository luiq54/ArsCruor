package com.mystchonky.arsoscura.common.event;

import com.mystchonky.arsoscura.common.enchantment.EnchantmentManager;
import com.mystchonky.arsoscura.common.enchantment.IArcaneEnchantment;
import com.mystchonky.arsoscura.common.mana.ManaManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

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
            EnchantmentManager.getArcaneEnchantment(stack).ifPresent(enchant -> {
                ManaManager.consumeMana(player, enchant.getCastingCost(player, stack));
            });
        }
    }

    private static boolean recursed = false;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void livingAttack(final LivingAttackEvent event) {
        if (event.getEntity().level().isClientSide() || event.getEntity().isDeadOrDying()) return;
        if (recursed) return;
        if (!(event.getSource().getDirectEntity() instanceof Player player)) return;

        recursed = true;

        ItemStack itemStack = player.getMainHandItem();
        Optional<IArcaneEnchantment> enchant = EnchantmentManager.getArcaneEnchantment(itemStack);
        if (enchant.isPresent()) {
            if (ManaManager.enoughMana(player, enchant.get().getCastingCost(player, itemStack))) {
                if (EnchantmentManager.getSunder(itemStack) > 0) {
                    LivingEntity target = event.getEntity();
                    int remainingTime = target.invulnerableTime;
                    target.invulnerableTime = 0;

                    target.hurt(player.damageSources().magic(), EnchantmentManager.getSunder(itemStack));
                    ManaManager.consumeMana(player, enchant.get().getCastingCost(player, itemStack));
                    // TODO: play sound and particles

                    target.invulnerableTime = remainingTime;
                }
            }
        }


        recursed = false;
    }
}
