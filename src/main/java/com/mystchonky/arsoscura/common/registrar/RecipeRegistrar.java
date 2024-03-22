package com.mystchonky.arsoscura.common.registrar;

import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.recipe.ArcaneFusionRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class RecipeRegistrar {

    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ArsOscura.MODID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArsOscura.MODID);

    public static final RecipeTypeSerializerPair<ArcaneFusionRecipe, ArcaneFusionRecipe.Serializer> ARCANE_FUSION = register("arcane_fusion", ArcaneFusionRecipe.Serializer::new);

    private static <I extends Recipe<?>> RegistryObject<RecipeType<I>> registerType(String name) {
        return RECIPE_TYPES.register(name, () -> RecipeType.simple(ArsOscura.prefix(name)));
    }

    private static <R extends Recipe<?>, S extends RecipeSerializer<? extends R>> RecipeTypeSerializerPair<R, S> register(String name, Supplier<S> serializerFactory) {
        RegistryObject<RecipeType<R>> type = RECIPE_TYPES.register(name, () -> RecipeType.simple(ArsOscura.prefix(name)));
        RegistryObject<S> serializer = RECIPE_SERIALIZERS.register(name, serializerFactory);
        return new RecipeTypeSerializerPair<>(type, serializer);
    }

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RECIPE_TYPES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
    }

    public record RecipeTypeSerializerPair<R extends Recipe<?>, S extends RecipeSerializer<? extends R>>(
            RegistryObject<RecipeType<R>> type, RegistryObject<S> serializer) {
    }
}
