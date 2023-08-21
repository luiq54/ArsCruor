package com.mystchonky.arsoscura.integration.bloodmagic.items;

import com.mystchonky.arsoscura.integration.bloodmagic.BloodMagicMobEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class MintTeaItem extends Item {
    public MintTeaItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {

            if (level.random.nextDouble() < 0.05) {
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 5 * 20));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 5 * 20));

            } else {
                player.addEffect(new MobEffectInstance(BloodMagicMobEffects.SERENE_EFFECT.get(), 60 * 20));
            }

            if (!player.isCreative()) {
                stack.shrink(1);
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 16;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }


}
