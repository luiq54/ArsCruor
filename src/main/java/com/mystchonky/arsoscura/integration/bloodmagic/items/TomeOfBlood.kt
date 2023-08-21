package com.mystchonky.arsoscura.integration.bloodmagic.items

import com.hollingsworth.arsnouveau.api.spell.ISpellCaster
import com.hollingsworth.arsnouveau.api.spell.SpellContext
import com.hollingsworth.arsnouveau.api.spell.SpellResolver
import com.hollingsworth.arsnouveau.api.spell.SpellTier
import com.hollingsworth.arsnouveau.common.items.SpellBook
import com.mystchonky.arsoscura.integration.bloodmagic.client.renderer.item.TomeOfBloodRenderer
import com.mystchonky.arsoscura.integration.bloodmagic.spell.BloodSpellResolver
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import java.util.function.Consumer

class TomeOfBlood(properties: Properties, tier: SpellTier) : SpellBook(properties, tier) {
//    init {
//        this.tier = tier
//    }

    override fun getSpellCaster(stack: ItemStack): ISpellCaster {
        return BloodBookCaster(stack)
    }

    class BloodBookCaster(stack: ItemStack) : BookCaster(stack) {
        override fun getSpellResolver(
            context: SpellContext,
            worldIn: Level,
            playerIn: LivingEntity,
            handIn: InteractionHand
        ): SpellResolver {
            return BloodSpellResolver(context)
        }
    }

    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        super.initializeClient(consumer)
        consumer.accept(object : IClientItemExtensions {
            private val renderer: BlockEntityWithoutLevelRenderer = TomeOfBloodRenderer()
            override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
                return renderer
            }
        })
    }
}
