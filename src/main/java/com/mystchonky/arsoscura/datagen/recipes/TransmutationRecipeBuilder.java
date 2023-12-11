package com.mystchonky.arsoscura.datagen.recipes;

import com.hollingsworth.arsnouveau.setup.registry.RegistryHelper;
import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.recipe.EnchantmentTransmutationRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class TransmutationRecipeBuilder {

    EnchantmentTransmutationRecipe recipe;

    public TransmutationRecipeBuilder() {
        this.recipe = new EnchantmentTransmutationRecipe();
    }

    public static TransmutationRecipeBuilder builder() {
        return new TransmutationRecipeBuilder();
    }

    public TransmutationRecipeBuilder withItem(Ingredient item) {
        this.recipe.reagent = item;
        return this;
    }

    public TransmutationRecipeBuilder withBaseEnchantment(Enchantment enchantment) {
        this.recipe.baseEnchantment = enchantment;
        return this;
    }

    public TransmutationRecipeBuilder withResultEnchantment(Enchantment enchantment) {
        this.recipe.resultEnchantment = enchantment;
        return this;
    }

    public TransmutationRecipeBuilder withPedestalItem(Ingredient i) {
        this.recipe.pedestalItems.add(i);
        return this;
    }

    public TransmutationRecipeBuilder withPedestalItem(RegistryObject<? extends ItemLike> i) {
        return withPedestalItem(i.get());
    }

    public TransmutationRecipeBuilder withPedestalItem(ItemLike i) {
        return this.withPedestalItem(Ingredient.of(i));
    }

    public TransmutationRecipeBuilder withPedestalItem(int count, RegistryObject<? extends ItemLike> i) {
        return withPedestalItem(count, i.get());
    }

    public TransmutationRecipeBuilder withPedestalItem(int count, ItemLike item) {
        for (int i = 0; i < count; i++)
            this.withPedestalItem(item);
        return this;
    }

    public TransmutationRecipeBuilder withPedestalItem(int count, Ingredient ingred) {
        for (int i = 0; i < count; i++)
            this.withPedestalItem(ingred);
        return this;
    }

    public TransmutationRecipeBuilder withPedestalItem(int count, TagKey<Item> ingred) {
        return this.withPedestalItem(count, Ingredient.of(ingred));
    }

    public TransmutationRecipeBuilder withSourceCost(int cost) {
        this.recipe.sourceCost = cost;
        return this;
    }

    public TransmutationRecipeBuilder withId(ResourceLocation id) {
        this.recipe.id = id;
        return this;
    }

    public EnchantmentTransmutationRecipe build() {
        if (recipe.id.getPath().equals("empty"))
            recipe.id = new ResourceLocation(ArsOscura.MODID, RegistryHelper.getRegistryName(recipe.resultEnchantment).getPath());
        return recipe;
    }

    public List<EnchantmentTransmutationRecipe> buildWithBook() {
        EnchantmentTransmutationRecipe recipe = this.build();
        EnchantmentTransmutationRecipe copy = recipe.copy();
        copy.reagent = Ingredient.of(Items.ENCHANTED_BOOK);
        copy.id = new ResourceLocation(ArsOscura.MODID, RegistryHelper.getRegistryName(recipe.resultEnchantment).getPath() + "_book");
        return List.of(recipe, copy);
    }
}
