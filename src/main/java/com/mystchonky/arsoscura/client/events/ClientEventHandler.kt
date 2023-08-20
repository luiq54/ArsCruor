package com.mystchonky.arsoscura.client.events

import com.mystchonky.arsoscura.ArsOscura
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(value = [Dist.CLIENT], modid = ArsOscura.MODID, bus = EventBusSubscriber.Bus.MOD)
object ClientEventHandler {
    @SubscribeEvent
    fun registerOverlays(event: RegisterGuiOverlaysEvent?) {
//        event.registerAboveAll("essence_hud", GuiLifeEssenceHUD.OVERLAY);
    }
}
