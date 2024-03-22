package com.mystchonky.arsoscura.common.mixin;

import com.hollingsworth.arsnouveau.api.client.IDisplayMana;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mystchonky.arsoscura.common.enchantment.EnchantmentManager;
import com.mystchonky.arsoscura.common.enchantment.ZealousEnchantment;
import com.mystchonky.arsoscura.common.entity.ZealousThrownTrident;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TridentItem.class)
public abstract class ArcaneTridentMixin implements IDisplayMana {

    @Override
    public boolean shouldDisplay(ItemStack stack) {
        return EnchantmentManager.hasArcaneEnchantment(stack);
    }

    // Allow Torrent as an alternative to Vanilla Riptide
    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getRiptide(Lnet/minecraft/world/item/ItemStack;)I"))
    public int getRiptideLevel(ItemStack stack, Operation<Integer> original) {
        if (EnchantmentHelper.getRiptide(stack) <= 0) {
            return EnchantmentManager.getTorrent(stack);
        }
        return original.call(stack);
    }

    // Bypass Water check when using Torrent
    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    public boolean isInWaterOrRain(Player player, Operation<Boolean> isWet) {
        return isWet.call(player) || EnchantmentManager.getTorrent(player.getMainHandItem()) > 0;
    }

    // Stop removal of Trident from inventory when using Zealous
    @WrapWithCondition(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;removeItem(Lnet/minecraft/world/item/ItemStack;)V"))
    public boolean removeItem(Inventory inventory, ItemStack inputStack, ItemStack methodStack, Level pLevel, LivingEntity livingEntity, int pTimeLeft) {
        if (!(livingEntity instanceof Player player)) {
            return true;
        }

        int level = EnchantmentManager.getZealous(inputStack);
        if (level > 0) {
            player.getCooldowns().addCooldown(inputStack.getItem(), ZealousEnchantment.getCooldownInTicks(level));
            return false;
        }
        return true;
    }

    // Thrown Tridents with Zealous should have different properties
    @WrapOperation(method = "releaseUsing", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/projectile/ThrownTrident;"))
    public ThrownTrident swapZealousTrident(Level level, LivingEntity livingEntity, ItemStack stack, Operation<ThrownTrident> original) {
        if (EnchantmentManager.getZealous(stack) > 0) {
            ZealousThrownTrident trident = new ZealousThrownTrident(level, livingEntity, stack);
            trident.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            return trident;
        }
        return original.call(level, livingEntity, stack);
    }

}
