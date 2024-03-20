package com.mystchonky.arsoscura.common.integration.jei;

import com.hollingsworth.arsnouveau.client.jei.MultiInputCategory;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.mystchonky.arsoscura.common.recipe.ArcaneFusionRecipe;
import com.mystchonky.arsoscura.common.registrar.LangRegistrar;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class ArcaneFusionRecipeCategory extends MultiInputCategory<ArcaneFusionRecipe> {

    public IDrawable background;
    public IDrawable icon;

    public ArcaneFusionRecipeCategory(IGuiHelper helper) {
        super(helper, recipe -> {
            ItemStack output = recipe.reagent.getItems()[0].copy();
            output.enchant(recipe.resultEnchantment, 1);

            ItemStack input = recipe.reagent.getItems()[0].copy();
            input.enchant(recipe.baseEnchantment, 1);

            return new MultiProvider(output, recipe.pedestalItems, Ingredient.of(input));
        });
        background = helper.createBlankDrawable(114, 108);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlockRegistry.ENCHANTING_APP_BLOCK));
    }

    @Override
    public @NotNull RecipeType<ArcaneFusionRecipe> getRecipeType() {
        return ArsOscuraJEIPlugin.ARCANE_FUSION_RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return LangRegistrar.ENCHANTMENT_TRANSMUTATION;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(ArcaneFusionRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font renderer = Minecraft.getInstance().font;
        if (recipe.consumesSource())
            guiGraphics.drawString(renderer, Component.translatable("ars_nouveau.source", recipe.sourceCost), 0, 100, 10, false);
    }
}
