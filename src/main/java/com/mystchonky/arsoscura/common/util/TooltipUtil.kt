package com.mystchonky.arsoscura.common.util

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.contents.TranslatableContents
import net.minecraft.resources.ResourceLocation

object TooltipUtil {
    /**
     * Style a component italic and gray
     */
    fun style(component: MutableComponent): Component {
        return component.withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY)
    }

    /**
     * Append arguments to a translatable component.
     * If you don't pass a translatable component, it will not be modified.
     */
    fun withArgs(component: MutableComponent, vararg args: Any?): MutableComponent {
        // Translate with args.
        val contents = component.contents
        return if (contents is TranslatableContents) {
            Component.translatable(contents.key, *args)
        } else component
        // Fallback.
    }

    /**
     * Style component and fill its args
     */
    fun styledWithArgs(component: MutableComponent, vararg args: Any?): Component {
        return style(withArgs(component, *args))
    }

    /**
     * Translate, style and fill args
     */
    fun styledWithArgs(key: ResourceLocation, vararg args: Any?): Component {
        return style(Component.translatable(key.toLanguageKey(), *args))
    }

}