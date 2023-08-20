package com.mystchonky.arsoscura.datagen

import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeProvider
import com.hollingsworth.arsnouveau.common.datagen.GlyphRecipeProvider
import com.hollingsworth.arsnouveau.common.datagen.ImbuementRecipeProvider
import com.hollingsworth.arsnouveau.common.datagen.PatchouliProvider
import com.hollingsworth.arsnouveau.setup.registry.RegistryHelper
import com.mystchonky.arsoscura.ArsOscura
import com.mystchonky.arsoscura.common.init.ArsNouveauIntegration
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataGenerator
import net.minecraft.world.item.Item
import java.nio.file.Path

object ArsProviders {
    var root = ArsOscura.MODID

    class GlyphProvider(generatorIn: DataGenerator) : GlyphRecipeProvider(generatorIn) {
        override fun collectJsons(pOutput: CachedOutput) {
            recipes.forEach {
                val path = getScribeGlyphPath(output, it.output.item)
                saveStable(pOutput, it.asRecipe(), path)
            }
        }

        override fun getName(): String {
            return "Example Glyph Recipes"
        }

        companion object {
            fun getScribeGlyphPath(pathIn: Path, glyph: Item?): Path {
                return pathIn.resolve("data/" + root + "/recipes/" + RegistryHelper.getRegistryName(glyph).path + ".json")
            }
        }
    }

    class EnchantingAppProvider(generatorIn: DataGenerator) : ApparatusRecipeProvider(generatorIn) {
        override fun collectJsons(pOutput: CachedOutput) {
            //example of an apparatus recipe
            /*
            recipes.add(builder()
                    .withReagent(ItemsRegistry.SOURCE_GEM)
                    .withPedestalItem(4, Recipes.SOURCE_GEM)
                    .withResult(ItemsRegistry.BUCKET_OF_SOURCE)
                    .withSource(100)
                    .build()
            );
             */
            recipes.forEach {

                val path = getRecipePath(output, it.getId().path)
                saveStable(pOutput, it.asRecipe(), path)
            }
        }

        override fun getName(): String {
            return "Example Apparatus"
        }

        companion object {
            fun getRecipePath(pathIn: Path, str: String): Path {
                return pathIn.resolve("data/$root/recipes/$str.json")
            }
        }
    }

    class ImbuementProvider(generatorIn: DataGenerator?) : ImbuementRecipeProvider(generatorIn) {
        override fun collectJsons(pOutput: CachedOutput) {

            /*
            recipes.add(new ImbuementRecipe("example_focus", Ingredient.of(Items.AMETHYST_SHARD), new ItemStack(ItemsRegistry.SUMMONING_FOCUS, 1), 5000)
                    .withPedestalItem(ItemsRegistry.WILDEN_TRIBUTE)
            );
            */
            recipes.forEach {
                val path = getRecipePath(output, it.getId().path)
                saveStable(pOutput, it.asRecipe(), path)

            }
        }

        fun getRecipePath(pathIn: Path, str: String): Path {
            return pathIn.resolve("data/$root/recipes/$str.json")
        }

        override fun getName(): String {
            return "Example Imbuement"
        }
    }

    class PatchouliEntryProvider(generatorIn: DataGenerator) : PatchouliProvider(generatorIn) {
        override fun addEntries() {
            ArsNouveauIntegration.registeredSpells.forEach { addGlyphPage(it) }
        }

        override fun getName(): String {
            return "Example Patchouli Datagen"
        }
    }
}
