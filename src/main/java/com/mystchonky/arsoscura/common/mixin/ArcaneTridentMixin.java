package com.mystchonky.arsoscura.common.mixin;

import com.hollingsworth.arsnouveau.api.client.IDisplayMana;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mystchonky.arsoscura.common.enchantments.FealtyEnchantment;
import com.mystchonky.arsoscura.common.util.EnchantmentUtil;
import net.minecraft.world.entity.Entity;
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
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TridentItem.class)
public abstract class ArcaneTridentMixin implements IDisplayMana {

    @Override
    public boolean shouldDisplay(ItemStack stack) {
        return EnchantmentUtil.hasArcaneEnchantment(stack);
    }


    // Allow Torrent as an alternative to Vanilla Riptide
    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getRiptide(Lnet/minecraft/world/item/ItemStack;)I"))
    public int getRiptideLevel(ItemStack stack, Operation<Integer> original) {
        if (EnchantmentHelper.getRiptide(stack) <= 0) {
            return EnchantmentUtil.getTorrent(stack);
        }
        return original.call(stack);
    }

    // Bypass Water check when using arcane enchants
    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    public boolean isInWaterOrRain(Player player, Operation<Boolean> isWet) {
        return isWet.call(player) || EnchantmentUtil.getTorrent(player.getMainHandItem()) > 0;
    }

    // Stop removal of Trident from inventory when using Mana Layalty
    @WrapWithCondition(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;removeItem(Lnet/minecraft/world/item/ItemStack;)V"))
    public boolean removeItem(Inventory inventory, ItemStack inputStack, ItemStack methodStack, Level pLevel, LivingEntity livingEntity, int pTimeLeft) {
        if (!(livingEntity instanceof Player player)) {
            return true;
        }

        int level = EnchantmentUtil.getFealty(inputStack);
        if (level > 0) {
            player.getCooldowns().addCooldown(inputStack.getItem(), FealtyEnchantment.getCooldownInTicks(level));
            return false;
        }
        return true;
    }

    // Tridents with Mana loyalty should not be allowed to be picked up
    @ModifyArg(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), index = 0)
    public Entity addFreshEntity(Entity entity, @Local(ordinal = 0) ItemStack stack) {
        if (EnchantmentUtil.getFealty(stack) > 0) {
            ((ThrownTrident) entity).pickup = AbstractArrow.Pickup.DISALLOWED;
        }
        return entity;
    }

}
