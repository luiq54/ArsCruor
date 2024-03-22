package com.mystchonky.arsoscura.common.enchantment;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.TridentLoyaltyEnchantment;
import org.jetbrains.annotations.NotNull;

public class ZealousEnchantment extends TridentLoyaltyEnchantment implements IArcaneEnchantment {
    public ZealousEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, equipmentSlots);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public int getCastingCost(Player player, ItemStack stack) {
        return 25 + ((EnchantmentManager.getZealous(stack) - 1) * 15);
    }

    public boolean checkCompatibility(@NotNull Enchantment enchant) {
        return super.checkCompatibility(enchant) && enchant != Enchantments.LOYALTY && !(enchant instanceof IArcaneEnchantment);
    }

    //TODO Configs
    public static int getCooldownInTicks(int level) {
        int cooldown = 6;
        if (level > 1)
            cooldown = 4;
        if (level > 2)
            cooldown = 2;

        return cooldown * 20;
    }

    @Override
    public @NotNull Component getFullname(int level) {
        return getNameWithStyle(this, level);
    }
}
