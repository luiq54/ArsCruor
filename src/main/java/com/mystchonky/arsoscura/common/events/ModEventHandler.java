package com.mystchonky.arsoscura.common.events;


import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.registrar.EntityRegistrar;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ArsOscura.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandler {

    @SubscribeEvent
    public static void registerEntityAttributes(final EntityAttributeCreationEvent event) {
        event.put(EntityRegistrar.HORSIE.get(), AbstractHorse.createBaseHorseAttributes().build());
    }
}
