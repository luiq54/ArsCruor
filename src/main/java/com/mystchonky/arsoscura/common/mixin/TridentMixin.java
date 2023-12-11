package com.mystchonky.arsoscura.common.mixin;

import com.hollingsworth.arsnouveau.api.client.IDisplayMana;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mystchonky.arsoscura.common.enchantments.FealtyEnchantment;
import com.mystchonky.arsoscura.common.enchantments.IManaEnchantment;
import com.mystchonky.arsoscura.common.util.EnchantmentUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(TridentItem.class)
public abstract class TridentMixin implements IDisplayMana {

    @Override
    public boolean shouldDisplay(ItemStack stack) {
        return EnchantmentUtil.isManaTool(stack);
    }

    // Allow cast only if player has sufficient Mana
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;startUsingItem(Lnet/minecraft/world/InteractionHand;)V"), cancellable = true)
    public void checkEnchantAndMana(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);
        Optional<IManaEnchantment> manaEnchant = EnchantmentUtil.getManaEnchantment(stack);
        if (manaEnchant.isPresent()) {
            int manaCost = manaEnchant.get().getCastingCost(player, stack);
            CapabilityRegistry.getMana(player).ifPresent(manaCap -> {
                if (manaCap.getCurrentMana() < manaCost) {
                    cir.setReturnValue(InteractionResultHolder.fail(stack));
                    cir.cancel();
                }
            });
        }

    }

    // Allow ManaRiptide as an alternative to Vanilla Riptide
    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getRiptide(Lnet/minecraft/world/item/ItemStack;)I"))
    public int getRiptideLevel(ItemStack stack, Operation<Integer> original, @Share("riptide") LocalBooleanRef usedManaRiptide) {
        if (EnchantmentHelper.getRiptide(stack) <= 0) {
            int level = EnchantmentUtil.getTorrent(stack);
            if (level > 0) {
                usedManaRiptide.set(true);
            }
            return level;
        }
        return original.call(stack);

    }

    // Bypass raincheck when using Mana Enchantments
    @WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    public boolean rainCheck(Player player, Operation<Boolean> isWater, @Share("riptide") LocalBooleanRef usedManaRiptide, @Share("useMana") LocalBooleanRef useMana) {
        if (!isWater.call(player) && usedManaRiptide.get()) {
            useMana.set(true);
        }
        return true;
    }

    // Stop removal of Trident from inventory when using Mana Layalty
    @WrapWithCondition(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;removeItem(Lnet/minecraft/world/item/ItemStack;)V"))
    public boolean removeItem(Inventory inventory, ItemStack inputStack, ItemStack methodStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft, @Share("useMana") LocalBooleanRef useMana) {
        int level = EnchantmentUtil.getFealty(inputStack);
        if (level > 0) {
            ((Player) pEntityLiving).getCooldowns().addCooldown(inputStack.getItem(), FealtyEnchantment.getCooldownInTicks(level));
            useMana.set(true);
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

    @Inject(method = "releaseUsing", at = @At("TAIL"))
    public void useMana(ItemStack stack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft, CallbackInfo ci, @Share("useMana") LocalBooleanRef usedMana) {
        if (usedMana.get()) {
            Player player = (Player) pEntityLiving;
            EnchantmentUtil.getManaEnchantment(stack).ifPresent(manaEnchant -> {
                CapabilityRegistry.getMana(player).ifPresent(manaCap -> manaCap.removeMana(manaEnchant.getCastingCost(player, stack)));
            });
        }
    }
}
