package com.mystchonky.arsoscura.common.enchantments;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IArcaneEnchantment {
    int getDefaultManaCost(ItemStack stack);

    // TODO: add config wrapper
    default int getCastingCost(Player player, ItemStack stack) {
        return getDefaultManaCost(stack);
    }
}
