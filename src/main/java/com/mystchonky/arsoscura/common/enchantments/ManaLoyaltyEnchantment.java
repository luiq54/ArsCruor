package com.mystchonky.arsoscura.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.TridentLoyaltyEnchantment;

public class ManaLoyaltyEnchantment extends TridentLoyaltyEnchantment implements IManaEnchantment {
    public ManaLoyaltyEnchantment(Rarity pRarity, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pApplicableSlots);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return false;

    }

    @Override
    public int getDefaultManaCost(ItemStack stack) {
        return 25;
    }

    public boolean checkCompatibility(Enchantment enchant) {
        return super.checkCompatibility(enchant) && enchant != Enchantments.LOYALTY && !(enchant instanceof IManaEnchantment);
    }
}
