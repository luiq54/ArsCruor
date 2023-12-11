package com.mystchonky.arsoscura.common.integration.jei;

import com.hollingsworth.arsnouveau.client.jei.MultiInputCategory;
import com.hollingsworth.arsnouveau.setup.registry.BlockRegistry;
import com.mystchonky.arsoscura.common.init.LangRegistry;
import com.mystchonky.arsoscura.common.recipe.EnchantmentTransmutationRecipe;
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

public class EnchantmentTransmutationRecipeCategory extends MultiInputCategory<EnchantmentTransmutationRecipe> {

    public IDrawable background;
    public IDrawable icon;

    public EnchantmentTransmutationRecipeCategory(IGuiHelper helper) {
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
    public RecipeType<EnchantmentTransmutationRecipe> getRecipeType() {
        return ArsOscuraJEIPlugin.TRANSMUTATION_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return LangRegistry.ENCHANTMENT_TRANSMUTATION;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(EnchantmentTransmutationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font renderer = Minecraft.getInstance().font;
        if (recipe.consumesSource())
            guiGraphics.drawString(renderer, Component.translatable("ars_nouveau.source", recipe.sourceCost), 0, 100, 10, false);
    }
}
