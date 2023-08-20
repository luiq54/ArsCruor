package com.mystchonky.arsoscura

import com.hollingsworth.arsnouveau.setup.proxy.IProxy
import com.mystchonky.arsoscura.ArsOscura
import com.mystchonky.arsoscura.common.config.BaseConfig
import com.mystchonky.arsoscura.common.init.ArsNouveauIntegration
import com.mystchonky.arsoscura.common.init.ArsOscuraItems
import com.mystchonky.arsoscura.common.init.ArsOscuraLang
import com.mystchonky.arsoscura.common.network.Networking
import com.mystchonky.arsoscura.integration.bloodmagic.BloodMagicIntegration
import com.mystchonky.arsoscura.integration.occultism.OccultismIntegration
import com.tterrag.registrate.Registrate
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.Lazy
import net.minecraftforge.event.server.ServerStartingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.config.ModConfig
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.fml.loading.FMLPaths
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.IOException
import java.nio.file.Files

@Mod(ArsOscura.MODID)
object ArsOscura {
    const val MODID = "ars_oscura"

    private val REGISTRATE = Lazy.of { Registrate.create(MODID) }
    val LOGGER: Logger = LogManager.getLogger()
    fun registrate(): Registrate {
        return REGISTRATE.get()
    }

    fun prefix(path: String?): ResourceLocation {
        return ResourceLocation(MODID, path)
    }

    init {
        // Ensure the config subdirectory is present.
        try {
            Files.createDirectories(FMLPaths.CONFIGDIR.get().resolve(MODID))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Register config files
        val ctx = ModLoadingContext.get()
        ctx.registerConfig(ModConfig.Type.COMMON, BaseConfig.COMMON_SPEC, "$MODID/base-common.toml")
        ctx.registerConfig(ModConfig.Type.CLIENT, BaseConfig.CLIENT_SPEC, "$MODID/base-client.toml")

        // Registration
        ArsOscuraItems.register()
        ArsNouveauIntegration.init()
        ArsOscuraLang.register()

        //Integration
        if (ModList.get().isLoaded("bloodmagic")) BloodMagicIntegration.init()
        if (ModList.get().isLoaded("occultism")) OccultismIntegration.init()

        val modbus = FMLJavaModLoadingContext.get().modEventBus
        modbus.addListener { event: FMLCommonSetupEvent -> setup(event) }
        modbus.addListener { event: FMLClientSetupEvent -> doClientStuff(event) }
        MinecraftForge.EVENT_BUS.register(this)
    }

    private fun setup(event: FMLCommonSetupEvent) {
        ArsNouveauIntegration.postInit()
        Networking.registerMessages()
        if (ModList.get().isLoaded("occultism")) OccultismIntegration.postInit()
    }

    private fun doClientStuff(event: FMLClientSetupEvent) {}

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent?) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting")
    }

}
