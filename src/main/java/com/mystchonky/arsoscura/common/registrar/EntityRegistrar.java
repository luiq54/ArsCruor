package com.mystchonky.arsoscura.common.registrar;

import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.entity.EnchantedHorse;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.world.entity.MobCategory;

public class EntityRegistrar {
    private static final Registrate REGISTRATE = ArsOscura.registrate();

    public static EntityEntry<EnchantedHorse> HORSIE = REGISTRATE
            .object("horsie")
            .entity(EnchantedHorse::new, MobCategory.CREATURE)
            .properties((builder) -> builder.sized(1.3964844F, 1.6F).clientTrackingRange(10))
            .lang("HORSE")
            .register();

    public static void register() {
    }
}
