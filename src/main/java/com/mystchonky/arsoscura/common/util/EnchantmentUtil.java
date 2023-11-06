package com.mystchonky.arsoscura.common.util;

import com.mystchonky.arsoscura.common.init.EnchantmentRegistry;
import net.minecraft.world.item.ItemStack;

public class EnchantmentUtil {

    public static boolean isManaTool(ItemStack stack) {
        boolean riptide = stack.getEnchantmentLevel(EnchantmentRegistry.MANA_RIPTIDE_ENCHANTMENT.get()) > 0;
        return riptide;
    }
}
