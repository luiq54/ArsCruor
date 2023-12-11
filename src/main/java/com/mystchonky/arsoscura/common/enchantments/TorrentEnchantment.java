package com.mystchonky.arsoscura.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.TridentRiptideEnchantment;

public class TorrentEnchantment extends TridentRiptideEnchantment implements IManaEnchantment {
    public TorrentEnchantment(Rarity pRarity, EquipmentSlot... pApplicableSlots) {
        super(pRarity, pApplicableSlots);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public int getDefaultManaCost(ItemStack stack) {
        return 25;
    }

    @Override
    public int getCastingCost(Player player, ItemStack stack) {
        return player.isInWaterOrRain() ? 0 : IManaEnchantment.super.getCastingCost(player, stack);
    }

    public boolean checkCompatibility(Enchantment enchant) {
        return super.checkCompatibility(enchant) && enchant != Enchantments.RIPTIDE && !(enchant instanceof IManaEnchantment);
    }
}
