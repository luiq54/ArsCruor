package com.mystchonky.arsoscura.client.events;

import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.registrar.EntityRegistrar;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ArsOscura.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {


    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistrar.HORSIE.get(), HorseRenderer::new);
    }
}
