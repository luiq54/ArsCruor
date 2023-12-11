package com.mystchonky.arsoscura.datagen.recipes;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeProvider;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.mystchonky.arsoscura.common.init.EnchantmentRegistry;
import com.mystchonky.arsoscura.datagen.DataProvider;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.Tags;

import java.nio.file.Path;

public class EnchantingAppProvider extends ApparatusRecipeProvider {

    public EnchantingAppProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    protected static Path getRecipePath(Path pathIn, String str) {
        return pathIn.resolve("data/" + DataProvider.root + "/recipes/apparatus/" + str + ".json");
    }

    @Override
    public void collectJsons(CachedOutput pOutput) {
        recipes.addAll(TransmutationRecipeBuilder.builder()
                .withItem(Ingredient.of(Tags.Items.TOOLS_TRIDENTS))
                .withPedestalItem(ItemsRegistry.JUMP_RING)
                .withPedestalItem(ItemsRegistry.AIR_ESSENCE)
                .withBaseEnchantment(Enchantments.RIPTIDE)
                .withResultEnchantment(EnchantmentRegistry.TORRENT_ENCHANTMENT.get())
                .withSourceCost(5000)
                .buildWithBook());

        for (EnchantingApparatusRecipe g : recipes) {
            if (g != null) {
                Path path = getRecipePath(output, g.getId().getPath());
                saveStable(pOutput, g.asRecipe(), path);
            }
        }

    }

    @Override
    public String getName() {
        return "Ars Oscura Enchanting Apparatus";
    }
}
