package com.mystchonky.arsoscura.datagen;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.spell.AbstractCastMethod;
import com.hollingsworth.arsnouveau.api.spell.AbstractEffect;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeProvider;
import com.hollingsworth.arsnouveau.common.datagen.GlyphRecipeProvider;
import com.hollingsworth.arsnouveau.common.datagen.ImbuementRecipeProvider;
import com.hollingsworth.arsnouveau.common.datagen.patchouli.*;
import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.init.ArsNouveauIntegration;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;

import java.io.IOException;
import java.nio.file.Path;

import static com.hollingsworth.arsnouveau.setup.registry.RegistryHelper.getRegistryName;


public class ArsProviders {

    static String root = ArsOscura.MODID;

    public static class GlyphProvider extends GlyphRecipeProvider {

        public GlyphProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void collectJsons(CachedOutput pOutput) {
            for (GlyphRecipe recipe : recipes) {
                Path path = getScribeGlyphPath(output, recipe.output.getItem());
                saveStable(pOutput, recipe.asRecipe(), path);
            }

        }

        protected static Path getScribeGlyphPath(Path pathIn, Item glyph) {
            return pathIn.resolve("data/" + root + "/recipes/" + getRegistryName(glyph).getPath() + ".json");
        }

        @Override
        public String getName() {
            return "Example Glyph Recipes";
        }
    }

    public static class EnchantingAppProvider extends ApparatusRecipeProvider {

        public EnchantingAppProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void collectJsons(CachedOutput pOutput) {
            //example of an apparatus recipe
            /*
            recipes.add(builder()
                    .withReagent(ItemsRegistry.SOURCE_GEM)
                    .withPedestalItem(4, Recipes.SOURCE_GEM)
                    .withResult(ItemsRegistry.BUCKET_OF_SOURCE)
                    .withSource(100)
                    .build()
            );
             */

            for (EnchantingApparatusRecipe g : recipes) {
                if (g != null) {
                    Path path = getRecipePath(output, g.getId().getPath());
                    saveStable(pOutput, g.asRecipe(), path);
                }
            }

        }

        protected static Path getRecipePath(Path pathIn, String str) {
            return pathIn.resolve("data/" + root + "/recipes/" + str + ".json");
        }

        @Override
        public String getName() {
            return "Example Apparatus";
        }
    }

    public static class ImbuementProvider extends ImbuementRecipeProvider {

        public ImbuementProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void collectJsons(CachedOutput pOutput) {

            /*
            recipes.add(new ImbuementRecipe("example_focus", Ingredient.of(Items.AMETHYST_SHARD), new ItemStack(ItemsRegistry.SUMMONING_FOCUS, 1), 5000)
                    .withPedestalItem(ItemsRegistry.WILDEN_TRIBUTE)
            );
            */

            for (ImbuementRecipe g : recipes) {
                Path path = getRecipePath(output, g.getId().getPath());
                saveStable(pOutput, g.asRecipe(), path);
            }

        }

        protected Path getRecipePath(Path pathIn, String str) {
            return pathIn.resolve("data/" + root + "/recipes/" + str + ".json");
        }

        @Override
        public String getName() {
            return "Example Imbuement";
        }

    }

    public static class PatchouliProvider extends com.hollingsworth.arsnouveau.common.datagen.PatchouliProvider {

        public PatchouliProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        public void addEntries() {
            for (AbstractSpellPart spell : ArsNouveauIntegration.registeredSpells) {
                addGlyphPage(spell);
            }
        }

        /**
         * Gets a name for this provider, to use in logging.
         */
        @Override
        public String getName() {
            return "Example Patchouli Datagen";
        }
    }

}
