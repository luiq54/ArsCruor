package com.mystchonky.arsoscura.common.items

import com.mystchonky.arsoscura.common.init.ArsOscuraLang
import com.mystchonky.arsoscura.common.util.TooltipUtil
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class Sigil(pProperties: Properties) : Item(pProperties) {
    constructor() : this(Properties())

    override fun appendHoverText(
        pStack: ItemStack,
        pLevel: Level?,
        pTooltipComponents: MutableList<Component>,
        pIsAdvanced: TooltipFlag
    ) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced)
        val tag = pStack.tag
        if (tag != null) {
            val type = tag.getString("entity_type")
            pTooltipComponents.add(TooltipUtil.withArgs(ArsOscuraLang.SIGIL_WITH_ENTITY, type))
        } else {
            pTooltipComponents.add(Component.translatable(ArsOscuraLang.SIGIL_EMPTY.string))
        }
    }

    override fun isFoil(pStack: ItemStack): Boolean {
        return true
    }
}
