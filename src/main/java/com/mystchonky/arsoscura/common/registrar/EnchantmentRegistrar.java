package com.mystchonky.arsoscura.common.registrar;

import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.enchantment.SunderEnchantment;
import com.mystchonky.arsoscura.common.enchantment.TorrentEnchantment;
import com.mystchonky.arsoscura.common.enchantment.ZealousEnchantment;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentRegistrar {
    private static final Registrate REGISTRATE = ArsOscura.registrate();

    public static final RegistryEntry<TorrentEnchantment> TORRENT_ENCHANTMENT = REGISTRATE
            .enchantment("torrent", EnchantmentCategory.TRIDENT, (rarity, type, slots) -> new TorrentEnchantment(rarity, slots))
            .rarity(Enchantment.Rarity.VERY_RARE)
            .addSlots(EquipmentSlot.MAINHAND)
            .register();

    public static final RegistryEntry<ZealousEnchantment> ZEALOUS_ENCHANTMENT = REGISTRATE
            .enchantment("zealous", EnchantmentCategory.TRIDENT, (rarity, type, slots) -> new ZealousEnchantment(rarity, slots))
            .rarity(Enchantment.Rarity.VERY_RARE)
            .addSlots(EquipmentSlot.MAINHAND)
            .register();

    public static final RegistryEntry<SunderEnchantment> SUNDER_ENCHANTMENT = REGISTRATE
            .enchantment("sunder", EnchantmentCategory.WEAPON, (rarity, type, slots) -> new SunderEnchantment(rarity, slots))
            .rarity(Enchantment.Rarity.VERY_RARE)
            .addSlots(EquipmentSlot.MAINHAND)
            .register();

    public static void register() {
    }
}
