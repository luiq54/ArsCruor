package com.mystchonky.arsoscura.data;

import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.data.models.GlyphItemModelProvider;
import com.mystchonky.arsoscura.data.recipes.EnchantingAppProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsOscura.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataProvider {
    public static final String root = ArsOscura.MODID;

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();

//        gen.addProvider(event.includeServer(), new ArsProviders.ImbuementProvider(gen));
//        gen.addProvider(event.includeServer(), new ArsProviders.GlyphProvider(gen));
        gen.addProvider(event.includeServer(), new EnchantingAppProvider(gen));

        gen.addProvider(event.includeServer(), new PatchouliProvider(gen));

        gen.addProvider(event.includeClient(), new GlyphItemModelProvider(output, event.getExistingFileHelper()));
    }

}
