package com.mystchonky.arsoscura.common.events

import com.mystchonky.arsoscura.ArsOscura
import com.mystchonky.arsoscura.client.ClientInfo
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.TickEvent.ClientTickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(modid = ArsOscura.MODID)
object EventHandler {
    @SubscribeEvent
    fun clientTickEnd(event: ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            ClientInfo.ticksInGame++
        }
    }

    //    @SubscribeEvent
    //    public static void updateAugmentsforMimic(FMLLoadCompleteEvent event) {
    //        ArsNouveauAPI.getInstance()
    //                .getGlyphItemMap()
    //                .values()
    //                .stream()
    //                .filter(spell -> spell instanceof AbstractAugment)
    //                .forEach(spell -> {
    //                    ((AbstractAugment) spell).getCompatibleAugments().add(AugmentMimic.INSTANCE);
    //                });
    //    }
}
