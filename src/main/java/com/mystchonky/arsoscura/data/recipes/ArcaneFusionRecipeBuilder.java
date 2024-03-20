package com.mystchonky.arsoscura.data.recipes;

import com.hollingsworth.arsnouveau.setup.registry.RegistryHelper;
import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.recipe.ArcaneFusionRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ArcaneFusionRecipeBuilder {

    ArcaneFusionRecipe recipe;

    public ArcaneFusionRecipeBuilder() {
        this.recipe = new ArcaneFusionRecipe();
    }

    public static ArcaneFusionRecipeBuilder builder() {
        return new ArcaneFusionRecipeBuilder();
    }

    public ArcaneFusionRecipeBuilder withItem(Ingredient item) {
        this.recipe.reagent = item;
        return this;
    }

    public ArcaneFusionRecipeBuilder withBaseEnchantment(Enchantment enchantment) {
        this.recipe.baseEnchantment = enchantment;
        return this;
    }

    public ArcaneFusionRecipeBuilder withResultEnchantment(Enchantment enchantment) {
        this.recipe.resultEnchantment = enchantment;
        return this;
    }

    public ArcaneFusionRecipeBuilder withPedestalItem(Ingredient i) {
        this.recipe.pedestalItems.add(i);
        return this;
    }

    public ArcaneFusionRecipeBuilder withPedestalItem(RegistryObject<? extends ItemLike> i) {
        return withPedestalItem(i.get());
    }

    public ArcaneFusionRecipeBuilder withPedestalItem(ItemLike i) {
        return this.withPedestalItem(Ingredient.of(i));
    }

    public ArcaneFusionRecipeBuilder withPedestalItem(int count, RegistryObject<? extends ItemLike> i) {
        return withPedestalItem(count, i.get());
    }

    public ArcaneFusionRecipeBuilder withPedestalItem(int count, ItemLike item) {
        for (int i = 0; i < count; i++)
            this.withPedestalItem(item);
        return this;
    }

    public ArcaneFusionRecipeBuilder withPedestalItem(int count, Ingredient ingred) {
        for (int i = 0; i < count; i++)
            this.withPedestalItem(ingred);
        return this;
    }

    public ArcaneFusionRecipeBuilder withPedestalItem(int count, TagKey<Item> ingred) {
        return this.withPedestalItem(count, Ingredient.of(ingred));
    }

    public ArcaneFusionRecipeBuilder withSourceCost(int cost) {
        this.recipe.sourceCost = cost;
        return this;
    }

    public ArcaneFusionRecipeBuilder withId(ResourceLocation id) {
        this.recipe.id = id;
        return this;
    }

    public ArcaneFusionRecipe build() {
        if (recipe.id.getPath().equals("empty"))
            recipe.id = new ResourceLocation(ArsOscura.MODID, RegistryHelper.getRegistryName(recipe.resultEnchantment).getPath());
        return recipe;
    }

    public List<ArcaneFusionRecipe> buildWithBook() {
        ArcaneFusionRecipe recipe = this.build();
        ArcaneFusionRecipe copy = recipe.copy();
        copy.reagent = Ingredient.of(Items.ENCHANTED_BOOK);
        copy.id = new ResourceLocation(ArsOscura.MODID, RegistryHelper.getRegistryName(recipe.resultEnchantment).getPath() + "_book");
        return List.of(recipe, copy);
    }
}
