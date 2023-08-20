package com.mystchonky.arsoscura.datagen

import com.hollingsworth.arsnouveau.common.datagen.GlyphRecipeProvider
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry
import com.hollingsworth.arsnouveau.setup.registry.RegistryHelper
import com.mystchonky.arsoscura.ArsOscura
import com.mystchonky.arsoscura.common.util.RecipeUtil.modCompatGlyphRecipe
import com.mystchonky.arsoscura.common.util.RecipeUtil.modCompatRecipe
import com.mystchonky.arsoscura.integration.bloodmagic.BloodMagicItems
import com.mystchonky.arsoscura.integration.bloodmagic.glyphs.EffectSentientHarm
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataGenerator
import net.minecraft.data.PackOutput
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.data.recipes.RecipeProvider
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.alchemy.Potions
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.common.crafting.PartialNBTIngredient
import net.minecraftforge.registries.ForgeRegistries
import wayoftime.bloodmagic.altar.AltarTier
import wayoftime.bloodmagic.common.data.recipe.builder.AlchemyTableRecipeBuilder
import wayoftime.bloodmagic.common.data.recipe.builder.BloodAltarRecipeBuilder
import java.nio.file.Path
import java.util.function.BiConsumer
import java.util.function.Consumer

object BloodMagicProviders {
    fun bloodmagicRecipe(
        recipeBuilder: BiConsumer<Consumer<FinishedRecipe>, ResourceLocation>,
        consumer: Consumer<FinishedRecipe>,
        id: ResourceLocation
    ) {
        modCompatRecipe("bloodmagic", recipeBuilder, consumer, id)
    }

    class AltarProvider(packOutput: PackOutput) : RecipeProvider(packOutput) {
        var basePath = "altar/"
        override fun getName(): String {
            return "Altar recipes"
        }

        override fun buildRecipes(consumer: Consumer<FinishedRecipe>) {
            bloodmagicRecipe(
                BloodAltarRecipeBuilder.altar(
                    Ingredient.of(ItemsRegistry.NOVICE_SPELLBOOK.get()), ItemStack(
                        BloodMagicItems.NOVICE_TOME.get()
                    ), AltarTier.TWO.ordinal, 10000, 20, 20
                )::build, consumer, ResourceLocation(ArsOscura.MODID, basePath + "novice_blood_tome")
            )
            bloodmagicRecipe(
                BloodAltarRecipeBuilder.altar(
                    Ingredient.of(ItemsRegistry.APPRENTICE_SPELLBOOK.get()), ItemStack(
                        BloodMagicItems.APPRENTICE_TOME.get()
                    ), AltarTier.THREE.ordinal, 25000, 20, 20
                )::build, consumer, ResourceLocation(ArsOscura.MODID, basePath + "apprentice_blood_tome")
            )

            bloodmagicRecipe(
                BloodAltarRecipeBuilder.altar(
                    Ingredient.of(ItemsRegistry.ARCHMAGE_SPELLBOOK.get()), ItemStack(
                        BloodMagicItems.ARCHMAGE_TOME.get()
                    ), AltarTier.FOUR.ordinal, 50000, 20, 20
                )::build, consumer, ResourceLocation(ArsOscura.MODID, basePath + "archmage_blood_tome")
            )
        }
    }

    class AlchemyTableProvider(packOutput: PackOutput?) : RecipeProvider(packOutput) {
        var basePath = "alchemytable/"
        override fun getName(): String {
            return "Alchemy Table recipes"
        }

        override fun buildRecipes(consumer: Consumer<FinishedRecipe>) {
            val tag = CompoundTag()
            tag.putString("Potion", ForgeRegistries.POTIONS.getKey(Potions.WATER).toString())
            bloodmagicRecipe(
                AlchemyTableRecipeBuilder.alchemyTable(
                    ItemStack(
                        BloodMagicItems.MINT_TEA.get()
                    ), 100, 100, 1
                ).addIngredient(Ingredient.of(Items.SUGAR)).addIngredient(
                    Ingredient.of(
                        Items.SEAGRASS
                    )
                ).addIngredient(Ingredient.of(Items.SEAGRASS)).addIngredient(
                    PartialNBTIngredient.of(
                        Items.POTION, tag
                    )
                )::build, consumer, ResourceLocation(ArsOscura.MODID, basePath + "mint_tea")
            )
        }
    }

    class GlyphProvider(generatorIn: DataGenerator) : GlyphRecipeProvider(generatorIn) {
        override fun collectJsons(pOutput: CachedOutput) {
            recipes.add(get(EffectSentientHarm.INSTANCE).withItem(wayoftime.bloodmagic.common.item.BloodMagicItems.PETTY_GEM))

            recipes.forEach {
                val path = getScribeGlyphPath(output, it.output.item)
                saveStable(pOutput, modCompatGlyphRecipe("bloodmagic", it.asRecipe()), path)

            }
        }

        override fun getName(): String {
            return "Blood Magic Glyph Recipes"
        }

        companion object {
            fun getScribeGlyphPath(pathIn: Path, glyph: Item?): Path {
                return pathIn.resolve("data/" + ArsOscura.MODID + "/recipes/" + RegistryHelper.getRegistryName(glyph).path + ".json")
            }
        }
    }
}
