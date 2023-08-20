package com.mystchonky.arsoscura.common.glyphs

object GlyphLibrary {
    val EffectSentientHarm = prependGlyph("sentient_harm")
    fun prependGlyph(glyph: String): String {
        return "glyph_$glyph"
    }
}
