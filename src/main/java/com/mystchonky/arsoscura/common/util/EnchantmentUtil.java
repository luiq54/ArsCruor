package com.mystchonky.arsoscura.common.util;

import com.mystchonky.arsoscura.common.enchantments.IArcaneEnchantment;
import com.mystchonky.arsoscura.common.registrar.EnchantmentRegistrar;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;
import java.util.Optional;

public class EnchantmentUtil {
    public static boolean hasArcaneEnchantment(ItemStack stack) {
        Map<Enchantment, Integer> enchants = stack.getAllEnchantments();
        return enchants.keySet().stream().anyMatch(it -> it instanceof IArcaneEnchantment);
    }

    public static Optional<IArcaneEnchantment> getArcaneEnchantment(ItemStack stack) {
        Map<Enchantment, Integer> enchants = stack.getAllEnchantments();
        return enchants.keySet().stream().filter(it -> it instanceof IArcaneEnchantment).findFirst().map(IArcaneEnchantment.class::cast);
    }

    public static int getTorrent(ItemStack stack) {
        return stack.getEnchantmentLevel(EnchantmentRegistrar.TORRENT_ENCHANTMENT.get());
    }

    public static int getFealty(ItemStack stack) {
        return stack.getEnchantmentLevel(EnchantmentRegistrar.FEALTY_ENCHANTMENT.get());
    }
}
