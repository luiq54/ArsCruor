package com.mystchonky.arsoscura;

import com.mystchonky.arsoscura.common.config.BaseConfig;
import com.mystchonky.arsoscura.common.registrar.ArsOscuraRegistrar;
import com.tterrag.registrate.Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;

@Mod(ArsOscura.MODID)
public class ArsOscura {
    public static final String MODID = "ars_oscura";

    private static final Lazy<Registrate> REGISTRATE = Lazy.of(() -> Registrate.create(MODID));
    public static final Logger LOGGER = LogManager.getLogger();
    public static Registrate registrate() {
        return REGISTRATE.get();
    }

    public ArsOscura() {
        try {
            Files.createDirectories(FMLPaths.CONFIGDIR.get().resolve(MODID));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Register config files
        var ctx = ModLoadingContext.get();
        ctx.registerConfig(ModConfig.Type.COMMON, BaseConfig.COMMON_SPEC, MODID + "/base-common.toml");
        ctx.registerConfig(ModConfig.Type.CLIENT, BaseConfig.CLIENT_SPEC, MODID + "/base-client.toml");

        ArsOscuraRegistrar.register();
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MODID, path);
    }

}
