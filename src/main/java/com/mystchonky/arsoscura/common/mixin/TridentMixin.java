package com.mystchonky.arsoscura.common.mixin;

import com.hollingsworth.arsnouveau.api.client.IDisplayMana;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import com.mystchonky.arsoscura.common.init.EnchantmentRegistry;
import com.mystchonky.arsoscura.common.util.EnchantmentUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentItem.class)
public abstract class TridentMixin implements IDisplayMana {

    @Override
    public boolean shouldDisplay(ItemStack stack) {
        return EnchantmentUtil.isManaTool(stack);
    }

    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;startUsingItem(Lnet/minecraft/world/InteractionHand;)V"), cancellable = true)
    public void checkEnchantAndMana(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getEnchantmentLevel(EnchantmentRegistry.MANA_RIPTIDE_ENCHANTMENT.get()) > 0) {
            if (!player.isInWaterOrRain()) {
                IManaCap manaCap = CapabilityRegistry.getMana(player).orElse(null);
                //TODO: Use configs
                if (manaCap == null || manaCap.getCurrentMana() < 500) {
                    cir.setReturnValue(InteractionResultHolder.fail(stack));
                    cir.cancel();
                }

            }
        }
    }
}
