package com.mystchonky.arsoscura.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.registry.RegistryHelper;
import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.registrar.RecipeRegistrar;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hollingsworth.arsnouveau.setup.registry.RegistryHelper.getRegistryName;

public class ArcaneFusionRecipe extends EnchantingApparatusRecipe {
    public List<Enchantment> baseEnchantments;
    public Enchantment resultEnchantment;

    public ArcaneFusionRecipe() {
        this.pedestalItems = new ArrayList<>();
        this.baseEnchantments = new ArrayList<>();
        this.resultEnchantment = Enchantments.BINDING_CURSE;
        this.sourceCost = 0;
        this.id = new ResourceLocation(ArsOscura.MODID, "empty");
    }

    public ArcaneFusionRecipe(ResourceLocation id, List<Ingredient> pedestalItems, List<Enchantment> baseEnchantments, Enchantment resultEnchantment, int sourceCost) {
        this.id = id;
        this.pedestalItems = pedestalItems;
        this.baseEnchantments = baseEnchantments;
        this.resultEnchantment = resultEnchantment;
        this.sourceCost = sourceCost;
    }

    @Override
    public boolean excludeJei() {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistrar.ARCANE_FUSION.type().get();
    }

    // Override and move reagent match to the end, so we can give feedback
    @Override
    public boolean isMatch(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile, @javax.annotation.Nullable Player player) {
        pedestalItems = pedestalItems.stream().filter(itemStack -> !itemStack.isEmpty()).collect(Collectors.toList());
        return this.pedestalItems.size() == pedestalItems.size() && doItemsMatch(pedestalItems, this.pedestalItems) && doesReagentMatch(reagent, player);
    }

    public boolean doesReagentMatch(ItemStack stack, Player player) {
        if (stack.isEmpty())
            return false;


        if (stack.getItem() != Items.BOOK && stack.getItem() != Items.ENCHANTED_BOOK && !resultEnchantment.canEnchant(stack)) {
            PortUtil.sendMessage(player, Component.translatable("ars_nouveau.enchanting.incompatible"));
            return false;
        }

        Collection<Enchantment> enchantList = EnchantmentHelper.getEnchantments(stack).keySet();
        enchantList.removeIf(it -> baseEnchantments.contains(it));

        if (!EnchantmentHelper.isEnchantmentCompatible(enchantList, resultEnchantment)) {
            PortUtil.sendMessage(player, Component.translatable("ars_nouveau.enchanting.incompatible"));
            return false;
        }

        Map<Enchantment, Integer> stackEnchants = EnchantmentHelper.getEnchantments(stack);
        Optional<Enchantment> target = getTargetEnchantment(stackEnchants);
        if (target.isPresent()) {
            int level = stackEnchants.getOrDefault(target.get(), 0);
            return level > 0;
        }
        return false;
    }

    @Override
    public ItemStack assemble(EnchantingApparatusTile inv, RegistryAccess access) {
        ItemStack stack = inv.getStack().copy();
        Map<Enchantment, Integer> stackEnchants = EnchantmentHelper.getEnchantments(stack);
        Optional<Enchantment> target = getTargetEnchantment(stackEnchants);
        if (target.isPresent()) {
            int level = stackEnchants.getOrDefault(target.get(), 0);
            stackEnchants.remove(target.get());
            stackEnchants.put(resultEnchantment, level);
        }
        if (stack.getItem() == Items.ENCHANTED_BOOK)  // reset stack with empty book
            stack = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.setEnchantments(stackEnchants, stack);
        return stack;
    }

    private Optional<Enchantment> getTargetEnchantment(Map<Enchantment, Integer> enchants) {
        return baseEnchantments.stream().filter(enchants::containsKey).findFirst();
    }

    @Override
    public ItemStack getResult(List<ItemStack> pedestalItems, ItemStack reagent, EnchantingApparatusTile enchantingApparatusTile) {
        return assemble(enchantingApparatusTile, null);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeRegistrar.ARCANE_FUSION.serializer().get();
    }

    @Override
    public JsonElement asRecipe() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("type", RecipeRegistrar.ARCANE_FUSION.type().getId().toString());

        JsonArray enchantArr = new JsonArray();
        for (Enchantment enchant : this.baseEnchantments) {
            JsonObject object = new JsonObject();
            object.addProperty("enchantment", RegistryHelper.getRegistryName(enchant).toString());
            enchantArr.add(object);
        }
        jsonobject.add("baseEnchantments", enchantArr);

        jsonobject.addProperty("resultEnchantment", RegistryHelper.getRegistryName(resultEnchantment).toString());
        jsonobject.addProperty("sourceCost", getSourceCost());

        JsonArray pedestalArr = new JsonArray();
        for (Ingredient i : this.pedestalItems) {
            JsonObject object = new JsonObject();
            object.add("item", i.toJson());
            pedestalArr.add(object);
        }
        jsonobject.add("pedestalItems", pedestalArr);

        return jsonobject;
    }

    public static class Serializer implements RecipeSerializer<ArcaneFusionRecipe> {

        @Override
        public ArcaneFusionRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonArray baseEnchants = GsonHelper.getAsJsonArray(json, "baseEnchantments");
            List<Enchantment> enchants = new ArrayList<>();
            for (JsonElement e : baseEnchants) {
                JsonObject obj = e.getAsJsonObject();
                Enchantment enchant = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(GsonHelper.getAsString(obj, "enchantment")));
                enchants.add(enchant);
            }
            Enchantment resultEnchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(GsonHelper.getAsString(json, "resultEnchantment")));
            int manaCost = GsonHelper.getAsInt(json, "sourceCost", 0);

            JsonArray pedestalItems = GsonHelper.getAsJsonArray(json, "pedestalItems");
            List<Ingredient> stacks = new ArrayList<>();
            for (JsonElement e : pedestalItems) {
                JsonObject obj = e.getAsJsonObject();
                Ingredient input;
                if (GsonHelper.isArrayNode(obj, "item")) {
                    input = Ingredient.fromJson(GsonHelper.getAsJsonArray(obj, "item"));
                } else {
                    input = Ingredient.fromJson(GsonHelper.getAsJsonObject(obj, "item"));
                }
                stacks.add(input);
            }

            return new ArcaneFusionRecipe(recipeId, stacks, enchants, resultEnchantment, manaCost);
        }

        @Override
        public @Nullable ArcaneFusionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int length = buffer.readInt();

            List<Enchantment> enchants = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                try {
                    String id = buffer.readUtf();
                    enchants.add(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(id)));
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            String resultEnchantID = buffer.readUtf();
            int manaCost = buffer.readInt();

            List<Ingredient> stacks = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                try {
                    stacks.add(Ingredient.fromNetwork(buffer));
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            return new ArcaneFusionRecipe(recipeId, stacks, enchants, ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(resultEnchantID)), manaCost);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ArcaneFusionRecipe recipe) {
            buf.writeInt(recipe.pedestalItems.size());
            for (Enchantment enchant : recipe.baseEnchantments) {
                buf.writeUtf(getRegistryName(enchant).toString());
            }
            buf.writeUtf(getRegistryName(recipe.resultEnchantment).toString());
            buf.writeInt(recipe.getSourceCost());
            for (Ingredient i : recipe.pedestalItems) {
                i.toNetwork(buf);
            }
        }
    }
}
