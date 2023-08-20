package com.mystchonky.arsoscura.common.glyphs;

public class GlyphLibrary {
    public static final String EffectSentientHarm = prependGlyph("sentient_harm");

    public static String prependGlyph(String glyph) {
        return "glyph_" + glyph;
    }
}
