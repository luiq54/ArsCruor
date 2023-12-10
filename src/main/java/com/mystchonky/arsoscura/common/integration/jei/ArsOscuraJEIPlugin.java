package com.mystchonky.arsoscura.common.integration.jei;

import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.recipe.EnchantmentTransmutationRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ArsOscuraJEIPlugin implements IModPlugin {
    public static final RecipeType<EnchantmentTransmutationRecipe> TRANSMUTATION_RECIPE_TYPE = RecipeType.create(ArsOscura.MODID, "transmutation_recipe", EnchantmentTransmutationRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ArsOscura.MODID, "main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new EnchantmentTransmutationRecipeCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        List<EnchantmentTransmutationRecipe> transmuatations = new ArrayList<>();
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        for (Recipe<?> i : manager.getRecipes()) {
            if (i instanceof EnchantmentTransmutationRecipe recipe)
                transmuatations.add(recipe);
        }
        registry.addRecipes(TRANSMUTATION_RECIPE_TYPE, transmuatations);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.ENCHANTING_APP_BLOCK), TRANSMUTATION_RECIPE_TYPE);
    }
}

