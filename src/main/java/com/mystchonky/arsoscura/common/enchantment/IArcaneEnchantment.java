package com.mystchonky.arsoscura.common.enchantment;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public interface IArcaneEnchantment {
    int getDefaultManaCost(ItemStack stack);

    // TODO: add config wrapper
    default int getCastingCost(Player player, ItemStack stack) {
        return getDefaultManaCost(stack);
    }

    default Component getNameWithStyle(Enchantment enchant, int pLevel) {
        MutableComponent mutablecomponent = Component.translatable(enchant.getDescriptionId());
        mutablecomponent.withStyle(ChatFormatting.LIGHT_PURPLE);
        if (pLevel != 1 || enchant.getMaxLevel() != 1) {
            mutablecomponent.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + pLevel));
        }

        return mutablecomponent;
    }
}
