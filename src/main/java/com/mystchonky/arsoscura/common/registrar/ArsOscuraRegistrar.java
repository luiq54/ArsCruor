package com.mystchonky.arsoscura.common.registrar;

import com.mystchonky.arsoscura.common.network.Networking;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ArsOscuraRegistrar {

    public static void register() {
        IntegrationRegistrar.init();

        ItemRegistrar.register();
        EntityRegistrar.register();
        EnchantmentRegistrar.register();
        RecipeRegistrar.register();
        LangRegistrar.register();

        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(ArsOscuraRegistrar::commonSetup);
        modbus.addListener(ArsOscuraRegistrar::clientSetup);
    }

    private static void commonSetup(final FMLCommonSetupEvent event) {
        IntegrationRegistrar.postInit();
        Networking.registerMessages();
    }

    private static void clientSetup(final FMLClientSetupEvent event) {

    }
}
