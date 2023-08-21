package com.mystchonky.arsoscura.integration.bloodmagic.init

import com.hollingsworth.arsnouveau.api.spell.SpellTier
import com.mystchonky.arsoscura.ArsOscura.registrate
import com.mystchonky.arsoscura.integration.bloodmagic.items.MintTeaItem
import com.mystchonky.arsoscura.integration.bloodmagic.items.TomeOfBlood
import com.tterrag.registrate.util.entry.ItemEntry
import net.minecraft.world.item.Item

object BloodMagicItems {
    private val REGISTRATE = registrate()

    val NOVICE_TOME: ItemEntry<TomeOfBlood> =
        REGISTRATE.item<TomeOfBlood>("novice_tome_of_blood") { properties -> TomeOfBlood(properties, SpellTier.ONE) }
            .lang("Novice Tome of Blood")
            .properties { properties: Item.Properties -> properties.stacksTo(1) }
            .model { _, _ -> }
            .register()

    val APPRENTICE_TOME: ItemEntry<TomeOfBlood> =
        REGISTRATE.item<TomeOfBlood>("apprentice_tome_of_blood") { properties ->
            TomeOfBlood(
                properties,
                SpellTier.TWO
            )
        }
            .lang("Apprentice Tome of Blood")
            .properties { properties: Item.Properties -> properties.stacksTo(1) }
            .model { _, _ -> }
            .register()

    val ARCHMAGE_TOME: ItemEntry<TomeOfBlood> = REGISTRATE.item<TomeOfBlood>("archmage_tome_of_blood") { properties ->
        TomeOfBlood(
            properties,
            SpellTier.THREE
        )
    }
        .lang("Archmage Tome of Blood")
        .properties { properties: Item.Properties -> properties.stacksTo(1) }
        .model { _, _ -> }
        .register()

    val MINT_TEA: ItemEntry<MintTeaItem> =
        REGISTRATE.item<MintTeaItem>("mint_tea") { properties -> MintTeaItem(properties) }
            .lang("Mint Tea")
            .properties { properties: Item.Properties -> properties.stacksTo(1) }
            .register()

    fun register() {}
}
