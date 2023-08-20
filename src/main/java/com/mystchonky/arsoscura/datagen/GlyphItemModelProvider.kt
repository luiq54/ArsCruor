package com.mystchonky.arsoscura.datagen

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart
import com.mystchonky.arsoscura.ArsOscura
import com.mystchonky.arsoscura.common.init.ArsNouveauIntegration
import net.minecraft.data.PackOutput
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import java.util.function.Consumer

class GlyphItemModelProvider(packOutput: PackOutput, existingFileHelper: ExistingFileHelper) :
    ItemModelProvider(packOutput, ArsOscura.MODID, existingFileHelper) {
    override fun registerModels() {
        ArsNouveauIntegration.registeredSpells.forEach { basicItem(it.registryName) }
    }
}
