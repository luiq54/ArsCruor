package com.mystchonky.arsoscura.common.util

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.common.crafting.ConditionalRecipe
import net.minecraftforge.common.crafting.CraftingHelper
import net.minecraftforge.common.crafting.conditions.ICondition
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition
import java.util.function.BiConsumer
import java.util.function.Consumer

object RecipeUtil {
    /**
     * Register a recipe wrapped in a [ConditionalRecipe]. Useful for mod compat recipes, as those recipes
     * would otherwise crash if the other mod is not installed
     *
     * @param condition     The condition for this recipe
     * @param recipeBuilder The recipe (pass reference to `XyzRecipeBuilder.build(Consumer, ResourceLocation)`)
     * @param consumer      The recipe consumer given by [RecipeProvider.buildRecipes]
     * @param id            The recipe id
     */
    fun conditionalRecipe(
        condition: ICondition,
        recipeBuilder: BiConsumer<Consumer<FinishedRecipe>, ResourceLocation>,
        consumer: Consumer<FinishedRecipe>,
        id: ResourceLocation
    ) {
        ConditionalRecipe.builder().addCondition(condition)
            .addRecipe { recipeBuilder.accept(it, id) }
            .build(consumer, id)
    }

    fun modCompatRecipe(
        modid: String,
        recipeBuilder: BiConsumer<Consumer<FinishedRecipe>, ResourceLocation>,
        consumer: Consumer<FinishedRecipe>,
        id: ResourceLocation
    ) {
        conditionalRecipe(ModLoadedCondition(modid), recipeBuilder, consumer, id)
    }

    fun modCompatGlyphRecipe(modid: String, recipe: JsonElement): JsonObject {
        val json = JsonObject()
        val conds = JsonArray()
        conds.add(CraftingHelper.serialize(ModLoadedCondition(modid)))
        json.add("conditions", conds)
        recipe.asJsonObject.entrySet().stream().forEach { (key, value) -> json.add(key, value) }
        return json
    }
}
