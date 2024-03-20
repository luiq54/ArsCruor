package com.mystchonky.arsoscura.common.integration.jei;

import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.recipe.ArcaneFusionRecipe;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ArsOscuraJEIPlugin implements IModPlugin {
    public static final RecipeType<ArcaneFusionRecipe> ARCANE_FUSION_RECIPE_TYPE = RecipeType.create(ArsOscura.MODID, "arcane_fusion_recipe", ArcaneFusionRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(ArsOscura.MODID, "main");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new ArcaneFusionRecipeCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        List<ArcaneFusionRecipe> transmuatations = new ArrayList<>();
        RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
        for (Recipe<?> i : manager.getRecipes()) {
            if (i instanceof ArcaneFusionRecipe recipe)
                transmuatations.add(recipe);
        }
        registry.addRecipes(ARCANE_FUSION_RECIPE_TYPE, transmuatations);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.ENCHANTING_APP_BLOCK), ARCANE_FUSION_RECIPE_TYPE);
    }
}

