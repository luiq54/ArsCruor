package com.mystchonky.arsoscura.common.mixin;


import com.hollingsworth.arsnouveau.api.client.IDisplayMana;
import com.mystchonky.arsoscura.common.enchantment.EnchantmentManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SwordItem.class)
public abstract class ArcaneSwordMixin implements IDisplayMana {
    @Override
    public boolean shouldDisplay(ItemStack stack) {
        return EnchantmentManager.hasArcaneEnchantment(stack);
    }
}
