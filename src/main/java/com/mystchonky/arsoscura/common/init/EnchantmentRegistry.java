package com.mystchonky.arsoscura.common.init;

import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.enchantments.ManaRiptideEnchantment;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentRegistry {
    private static final Registrate REGISTRATE = ArsOscura.registrate();

    public static final RegistryEntry<ManaRiptideEnchantment> MANA_RIPTIDE_ENCHANTMENT = REGISTRATE.object("mana_riptide")
            .enchantment(EnchantmentCategory.TRIDENT, (rarity, type, slots) -> new ManaRiptideEnchantment(rarity, slots))
            .rarity(Enchantment.Rarity.VERY_RARE)
            .addSlots(EquipmentSlot.MAINHAND)
            .register();

    public static void register() {
    }
}
