package com.mystchonky.arsoscura.common.integration.jei;

import com.hollingsworth.arsnouveau.client.jei.EnchantingApparatusRecipeCategory;
import com.mystchonky.arsoscura.common.recipe.ArcaneFusionRecipe;
import com.mystchonky.arsoscura.common.registrar.LangRegistrar;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ArcaneFusionRecipeCategory extends EnchantingApparatusRecipeCategory<ArcaneFusionRecipe> {

    public ArcaneFusionRecipeCategory(IGuiHelper helper) {
        super(helper);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ArcaneFusionRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> pedestalInputs = multiProvider.apply(recipe).input();
        double angleBetweenEach = 360.0 / pedestalInputs.size();

        List<ItemStack> inputs = new ArrayList<>();
        List<ItemStack> outputs = new ArrayList<>();

        recipe.baseEnchantments.forEach(enchantment ->
                IntStream.rangeClosed(1, enchantment.getMaxLevel()).forEach(level -> {
                    inputs.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level)));
                    outputs.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(recipe.resultEnchantment, level)));
                })
        );

//        Arrays.stream(recipe.reagent.getItems()).forEach(reagent -> {
//            recipe.baseEnchantments.forEach(enchantment ->
//                    IntStream.rangeClosed(1, enchantment.getMaxLevel()).forEach(level -> {
//                        inputs.add(getEnchantedStack(reagent.copy(), enchantment, level));
//                        outputs.add(getEnchantedStack(reagent.copy(), recipe.resultEnchantment, level));
//                    })
//            );
//        });


        IRecipeSlotBuilder reagentSlot = builder.addSlot(RecipeIngredientRole.INPUT, 48, 45).addItemStacks(inputs);

        for (Ingredient input : pedestalInputs) {
            builder.addSlot(RecipeIngredientRole.INPUT, (int) point.x, (int) point.y)
                    .addIngredients(input);
            point = rotatePointAbout(point, center, angleBetweenEach);
        }

        IRecipeSlotBuilder outputSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 86, 10).addItemStacks(outputs);

        if (inputs.size() == outputs.size()) {
            builder.createFocusLink(reagentSlot, outputSlot);
        }
    }

    private ItemStack getEnchantedStack(ItemStack stack, Enchantment enchant, int level) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        enchantments.put(enchant, level);
        EnchantmentHelper.setEnchantments(enchantments, stack);
        return stack;
    }

    @Override
    public @NotNull RecipeType<ArcaneFusionRecipe> getRecipeType() {
        return ArsOscuraJEIPlugin.ARCANE_FUSION_RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return LangRegistrar.ARCANE_FUSION;
    }

}
