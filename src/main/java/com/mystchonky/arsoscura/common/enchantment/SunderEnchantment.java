package com.mystchonky.arsoscura.common.enchantment;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

public class SunderEnchantment extends DamageEnchantment implements IArcaneEnchantment {
    public SunderEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, 0, equipmentSlots);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public int getDefaultManaCost(ItemStack stack) {
        int level = EnchantmentManager.getSunder(stack);
        return 30 + (level * 10);
    }

    @Override
    public boolean checkCompatibility(@NotNull Enchantment enchant) {
        return super.checkCompatibility(enchant) && !(enchant instanceof IArcaneEnchantment);
    }

    @Override
    public @NotNull Component getFullname(int level) {
        return getNameWithStyle(this, level);
    }
}
