package com.mystchonky.arsoscura.integration.bloodmagic.items

import com.mystchonky.arsoscura.integration.bloodmagic.init.BloodMagicMobEffects
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemUtils
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

class MintTeaItem(properties: Properties) : Item(properties) {
    override fun finishUsingItem(stack: ItemStack, level: Level, entity: LivingEntity): ItemStack {
        if (!level.isClientSide && entity is Player) {
            if (level.random.nextDouble() < 0.05) {
                entity.addEffect(MobEffectInstance(MobEffects.POISON, 5 * 20))
                entity.addEffect(MobEffectInstance(MobEffects.CONFUSION, 5 * 20))
            } else {
                entity.addEffect(MobEffectInstance(BloodMagicMobEffects.SERENE_EFFECT.get(), 60 * 20))
            }
            if (!entity.isCreative) {
                stack.shrink(1)
            }
        }
        return stack
    }

    override fun getUseDuration(stack: ItemStack): Int {
        return 16
    }

    override fun use(
        level: Level,
        player: Player,
        interactionHand: InteractionHand
    ): InteractionResultHolder<ItemStack> {
        return ItemUtils.startUsingInstantly(level, player, interactionHand)
    }

    override fun getUseAnimation(stack: ItemStack): UseAnim {
        return UseAnim.DRINK
    }
}
