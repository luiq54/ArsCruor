package com.mystchonky.arsoscura.integration.occultism;

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.mob_jar.JarBehaviorRegistry;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.mystchonky.arsoscura.integration.occultism.client.ClientEventHandler;
import com.mystchonky.arsoscura.integration.occultism.mob_jar.SpiritBehaviour;
import net.minecraftforge.common.MinecraftForge;

import java.util.function.Consumer;

public class OccultismIntegration {

    public static void init() {
        OccultismItems.register();

        MinecraftForge.EVENT_BUS.register(EventHandler.class);
        MinecraftForge.EVENT_BUS.register(ClientEventHandler.class);
    }

    public static void postInit() {
        registerJarBehaviours();
    }

    public static void registerFamiliars(Consumer<AbstractFamiliarHolder> register) {
//        register.accept(new FamiliarDragonHolder());
    }


    public static void registerJarBehaviours() {
        JarBehaviorRegistry.register(OccultismEntities.FOLIOT.get(), new SpiritBehaviour<>());
        JarBehaviorRegistry.register(OccultismEntities.DJINNI.get(), new SpiritBehaviour<>());
        JarBehaviorRegistry.register(OccultismEntities.AFRIT.get(), new SpiritBehaviour<>());
        JarBehaviorRegistry.register(OccultismEntities.MARID.get(), new SpiritBehaviour<>());
    }
}
