package com.mystchonky.arsoscura.common.util;

import com.mystchonky.arsoscura.common.enchantments.IManaEnchantment;
import com.mystchonky.arsoscura.common.init.EnchantmentRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;
import java.util.Optional;

public class EnchantmentUtil {
    public static boolean isManaTool(ItemStack stack) {
        Map<Enchantment, Integer> enchants = stack.getAllEnchantments();
        return enchants.keySet().stream().anyMatch(it -> it instanceof IManaEnchantment);
    }

    public static Optional<IManaEnchantment> getManaEnchantment(ItemStack stack) {
        Map<Enchantment, Integer> enchants = stack.getAllEnchantments();
        return enchants.keySet().stream().filter(it -> it instanceof IManaEnchantment).findFirst().map(IManaEnchantment.class::cast);
    }

    public static int getManaRiptide(ItemStack pStack) {
        return EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.MANA_RIPTIDE_ENCHANTMENT.get(), pStack);
    }

    public static int getManaLoyalty(ItemStack pStack) {
        return EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.MANA_LOYALTY_ENCHANTMENT.get(), pStack);
    }
}
