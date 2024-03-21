package com.mystchonky.arsoscura.common.mixin;


import com.hollingsworth.arsnouveau.api.client.IDisplayMana;
import com.mystchonky.arsoscura.common.enchantment.EnchantmentManager;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AxeItem.class)
public abstract class ArcaneAxeMixin implements IDisplayMana {
    @Override
    public boolean shouldDisplay(ItemStack stack) {
        return EnchantmentManager.hasArcaneEnchantment(stack);
    }
}
