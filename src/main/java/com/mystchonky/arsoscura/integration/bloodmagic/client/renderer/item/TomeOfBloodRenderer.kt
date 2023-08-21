package com.mystchonky.arsoscura.integration.bloodmagic.client.renderer.item

import com.hollingsworth.arsnouveau.client.renderer.item.SpellBookRenderer
import com.hollingsworth.arsnouveau.common.items.SpellBook
import com.mystchonky.arsoscura.ArsOscura
import net.minecraft.resources.ResourceLocation

class TomeOfBloodRenderer : SpellBookRenderer() {
    override fun getTextureLocation(o: SpellBook): ResourceLocation {
        val base = "textures/item/spellbook_black"
        return ResourceLocation(ArsOscura.MODID, "$base.png")
    }
}
