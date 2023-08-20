package com.mystchonky.arsoscura.common.init;

import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.integration.bloodmagic.BloodMagicIntegration;
import com.tterrag.registrate.Registrate;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ArsOscuraLang {

    private static final Registrate REGISTRATE = ArsOscura.registrate();
    public static final Component SIGIL_EMPTY = REGISTRATE.addLang("tooltip", ArsOscura.prefix("sigil_empty"), "No entity stored");
    public static final MutableComponent SIGIL_WITH_ENTITY = REGISTRATE.addLang("tooltip", ArsOscura.prefix("sigil_with_entity"), "Entity stored: %s");


    // Blood Magic Integration
    public static final Component SCHOOL_BLOODMAGIC = REGISTRATE.addRawLang("ars_nouveau.school." + BloodMagicIntegration.BLOODMAGIC.getId(), "Blood Magic");
    public static final Component LOW_LP = REGISTRATE.addLang("alert", ArsOscura.prefix("no_lp"), "Your soul feels weak..");
    public static final Component SERENE_EFFECT = REGISTRATE.addLang("effect", ArsOscura.prefix("serene"), "Serene");
    public static final Component MANA_BONUS_UPGRADE = REGISTRATE.addLang("living_upgrade", ArsOscura.prefix("mana_bonus"), "Mana Attunement");

    public static void register() {
        ArsNouveauIntegration.registeredSpells.forEach(spell -> {
            REGISTRATE.addRawLang(ArsOscura.MODID + ".glyph_name." + spell.getRegistryName().getPath(), spell.getName());
            REGISTRATE.addRawLang(ArsOscura.MODID + ".glyph_desc." + spell.getRegistryName().getPath(), spell.getBookDescription());
        });
    }
}
