package com.mystchonky.arsoscura.common.init;

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.registry.FamiliarRegistry;
import com.hollingsworth.arsnouveau.api.registry.GlyphRegistry;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.mystchonky.arsoscura.integration.bloodmagic.BloodMagicIntegration;
import com.mystchonky.arsoscura.integration.occultism.OccultismIntegration;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public class ArsNouveauIntegration {

    public static List<AbstractSpellPart> registeredSpells = new ArrayList<>();
    public static List<AbstractFamiliarHolder> registeredFamiliars = new ArrayList<>();

    public static void init() {
        registerGlyphs();
        registerFamiliars();
    }

    public static void postInit() {
        registerSounds();
    }

    public static void registerGlyphs() {
        if (ModList.get().isLoaded("bloodmagic"))
            BloodMagicIntegration.registerGlyphs(ArsNouveauIntegration::registerSpellPart);
    }

    public static void registerFamiliars() {
        if (ModList.get().isLoaded("occultism"))
            OccultismIntegration.registerFamiliars(ArsNouveauIntegration::registerFamiliars);
    }

    public static void registerSounds() {
    }

    public static void registerSpellPart(AbstractSpellPart spellPart) {
        GlyphRegistry.registerSpell(spellPart);
        registeredSpells.add(spellPart);
    }

    public static void registerFamiliars(AbstractFamiliarHolder familiarHolder) {
        FamiliarRegistry.registerFamiliar(familiarHolder);
        registeredFamiliars.add(familiarHolder);
    }

}
