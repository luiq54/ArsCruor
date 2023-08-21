package com.mystchonky.arsoscura.integration.occultism

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder
import com.hollingsworth.arsnouveau.api.mob_jar.JarBehaviorRegistry
import com.klikli_dev.occultism.registry.OccultismEntities
import com.mystchonky.arsoscura.integration.occultism.client.ClientEventHandler
import com.mystchonky.arsoscura.integration.occultism.mob_jar.SpiritBehaviour
import net.minecraftforge.common.MinecraftForge
import java.util.function.Consumer

object OccultismIntegration {
    fun init() {
        OccultismItems.register()
        MinecraftForge.EVENT_BUS.register(EventHandler::class)
        MinecraftForge.EVENT_BUS.register(ClientEventHandler::class)
    }

    fun postInit() {
        registerJarBehaviours()
    }

    fun registerFamiliars(register: Consumer<AbstractFamiliarHolder>) {
//        register.accept(new FamiliarDragonHolder());
    }

    private fun registerJarBehaviours() {
        JarBehaviorRegistry.register(OccultismEntities.FOLIOT.get(), SpiritBehaviour())
        JarBehaviorRegistry.register(OccultismEntities.DJINNI.get(), SpiritBehaviour())
        JarBehaviorRegistry.register(OccultismEntities.AFRIT.get(), SpiritBehaviour())
        JarBehaviorRegistry.register(OccultismEntities.MARID.get(), SpiritBehaviour())
    }
}
