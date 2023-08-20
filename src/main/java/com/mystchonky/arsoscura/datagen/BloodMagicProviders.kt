package com.mystchonky.arsoscura.datagen;

import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.datagen.GlyphRecipeProvider;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.integration.bloodmagic.BloodMagicItems;
import com.mystchonky.arsoscura.integration.bloodmagic.glyphs.EffectSentientHarm;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;
import wayoftime.bloodmagic.altar.AltarTier;
import wayoftime.bloodmagic.common.data.recipe.builder.AlchemyTableRecipeBuilder;
import wayoftime.bloodmagic.common.data.recipe.builder.BloodAltarRecipeBuilder;

import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.hollingsworth.arsnouveau.setup.registry.RegistryHelper.getRegistryName;

public class BloodMagicProviders {

    protected static void bloodmagicRecipe(BiConsumer<Consumer<FinishedRecipe>, ResourceLocation> recipeBuilder, Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        RecipeUtil.modCompatRecipe("bloodmagic", recipeBuilder, consumer, id);
    }

    public static class AltarProvider extends RecipeProvider {

        String basePath = "altar/";

        public AltarProvider(PackOutput packOutput) {
            super(packOutput);
        }

        @Override
        public String getName() {
            return "Altar recipes";
        }

        @Override
        protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
            bloodmagicRecipe(BloodAltarRecipeBuilder.altar(Ingredient.of(ItemsRegistry.NOVICE_SPELLBOOK.get()), new ItemStack(BloodMagicItems.NOVICE_TOME.get()), AltarTier.TWO.ordinal(), 10000, 20, 20)::build, consumer, new ResourceLocation(ArsOscura.MODID, basePath + "novice_blood_tome"));
            bloodmagicRecipe(BloodAltarRecipeBuilder.altar(Ingredient.of(ItemsRegistry.APPRENTICE_SPELLBOOK.get()), new ItemStack(BloodMagicItems.APPRENTICE_TOME.get()), AltarTier.THREE.ordinal(), 25000, 20, 20)::build, consumer, new ResourceLocation(ArsOscura.MODID, basePath + "apprentice_blood_tome"));
            bloodmagicRecipe(BloodAltarRecipeBuilder.altar(Ingredient.of(ItemsRegistry.ARCHMAGE_SPELLBOOK.get()), new ItemStack(BloodMagicItems.ARCHMAGE_TOME.get()), AltarTier.FOUR.ordinal(), 50000, 20, 20)::build, consumer, new ResourceLocation(ArsOscura.MODID, basePath + "archmage_blood_tome"));
        }

    }

    public static class AlchemyTableProvider extends RecipeProvider {
        String basePath = "alchemytable/";

        public AlchemyTableProvider(PackOutput packOutput) {
            super(packOutput);
        }

        @Override
        public String getName() {
            return "Alchemy Table recipes";
        }

        @Override
        protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
            CompoundTag tag = new CompoundTag();
            tag.putString("Potion", ForgeRegistries.POTIONS.getKey(Potions.WATER).toString());

            bloodmagicRecipe(AlchemyTableRecipeBuilder.alchemyTable(new ItemStack(BloodMagicItems.MINT_TEA.get()), 100, 100, 1).addIngredient(Ingredient.of(Items.SUGAR)).addIngredient(Ingredient.of(Items.SEAGRASS)).addIngredient(Ingredient.of(Items.SEAGRASS)).addIngredient(PartialNBTIngredient.of(Items.POTION, tag))::build, consumer, new ResourceLocation(ArsOscura.MODID, basePath + "mint_tea"));
        }
    }

    public static class GlyphProvider extends GlyphRecipeProvider {

        public GlyphProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void collectJsons(CachedOutput pOutput) {
            recipes.add(get(EffectSentientHarm.INSTANCE).withItem(wayoftime.bloodmagic.common.item.BloodMagicItems.PETTY_GEM));

            for (GlyphRecipe recipe : recipes) {
                Path path = getScribeGlyphPath(output, recipe.output.getItem());
                saveStable(pOutput, RecipeUtil.modCompatGlyphRecipe("bloodmagic", recipe.asRecipe()), path);
            }

        }

        protected static Path getScribeGlyphPath(Path pathIn, Item glyph) {
            return pathIn.resolve("data/" + ArsOscura.MODID + "/recipes/" + getRegistryName(glyph).getPath() + ".json");
        }

        @Override
        public String getName() {
            return "Example Glyph Recipes";
        }
    }
}
