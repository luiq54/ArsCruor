package com.mystchonky.arsoscura.integration.bloodmagic;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.mystchonky.arsoscura.integration.bloodmagic.glyphs.EffectSentientHarm;
import net.minecraftforge.common.MinecraftForge;

import java.util.function.Consumer;

public class BloodMagicIntegration {
    public static SpellSchool BLOODMAGIC = new SpellSchool("bloodmagic");

    public static void init() {
        BloodMagicItems.register();
        LivingUpgradeRegistry.register();
        BloodMagicMobEffects.register();

        MinecraftForge.EVENT_BUS.register(EventHandler.class);
    }

    public static void registerGlyphs(Consumer<AbstractSpellPart> register) {
        register.accept(EffectSentientHarm.INSTANCE);
    }
}
