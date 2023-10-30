package com.mystchonky.arsoscura.common.init;

import com.mystchonky.arsoscura.ArsOscura;
import com.tterrag.registrate.Registrate;

public class LangRegistry {

    private static final Registrate REGISTRATE = ArsOscura.registrate();
//    public static final Component SIGIL_EMPTY = REGISTRATE.addLang("tooltip", ArsOscura.prefix("sigil_empty"), "No entity stored");
//    public static final MutableComponent SIGIL_WITH_ENTITY = REGISTRATE.addLang("tooltip", ArsOscura.prefix("sigil_with_entity"), "Entity stored: %s");



    public static void register() {
        IntegrationRegistry.registeredSpells.forEach(spell -> {
            REGISTRATE.addRawLang(ArsOscura.MODID + ".glyph_name." + spell.getRegistryName().getPath(), spell.getName());
            REGISTRATE.addRawLang(ArsOscura.MODID + ".glyph_desc." + spell.getRegistryName().getPath(), spell.getBookDescription());
        });
    }
}
