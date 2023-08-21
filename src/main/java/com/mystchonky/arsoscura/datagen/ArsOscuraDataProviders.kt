package com.mystchonky.arsoscura.datagen

import com.mystchonky.arsoscura.ArsOscura
import com.mystchonky.arsoscura.datagen.BloodMagicProviders.AlchemyTableProvider
import com.mystchonky.arsoscura.datagen.BloodMagicProviders.AltarProvider
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(modid = ArsOscura.MODID, bus = EventBusSubscriber.Bus.MOD)
object ArsOscuraDataProviders {
    //use runData configuration to generate stuff, event.includeServer() for data, event.includeClient() for assets
    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) {
        val gen = event.generator
        val output = gen.packOutput

//        gen.addProvider(event.includeServer(), new ArsProviders.ImbuementProvider(gen));
//        gen.addProvider(event.includeServer(), new ArsProviders.GlyphProvider(gen));
//        gen.addProvider(event.includeServer(), new ArsProviders.EnchantingAppProvider(gen));
        gen.addProvider(event.includeServer(), AltarProvider(output))
        gen.addProvider(event.includeServer(), AlchemyTableProvider(output))
        gen.addProvider(event.includeServer(), BloodMagicProviders.GlyphProvider(gen))
        gen.addProvider(event.includeServer(), ArsNouveauProviders.PatchouliEntryProvider(gen))
        gen.addProvider(event.includeClient(), GlyphItemModelProvider(output, event.existingFileHelper))
    }
}
