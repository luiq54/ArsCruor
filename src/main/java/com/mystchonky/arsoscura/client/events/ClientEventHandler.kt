package com.mystchonky.arsoscura.client.events;

import com.mystchonky.arsoscura.ArsOscura;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ArsOscura.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    @SubscribeEvent
    public static void registerOverlays(final RegisterGuiOverlaysEvent event) {
//        event.registerAboveAll("essence_hud", GuiLifeEssenceHUD.OVERLAY);
    }
}
