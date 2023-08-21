package com.mystchonky.arsoscura.integration.occultism.familiars

object FamiliarLibrary {
    val FAMILIAR_DRAGON = appendFamiliar("dragon")
    private fun appendFamiliar(name: String): String {
        return "familiar_$name"
    }
}
