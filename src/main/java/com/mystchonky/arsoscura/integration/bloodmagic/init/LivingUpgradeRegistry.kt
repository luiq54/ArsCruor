package com.mystchonky.arsoscura.integration.bloodmagic.init

import com.mystchonky.arsoscura.ArsOscura
import wayoftime.bloodmagic.core.LivingArmorRegistrar
import wayoftime.bloodmagic.core.living.LivingUpgrade

object LivingUpgradeRegistry {
    var MANA_UPGRADE = LivingUpgrade(ArsOscura.prefix("mana_bonus")) { levels: MutableList<LivingUpgrade.Level> ->
        levels.add(LivingUpgrade.Level(30, 4))
        levels.add(LivingUpgrade.Level(120, 7))
        levels.add(LivingUpgrade.Level(480, 8))
        levels.add(LivingUpgrade.Level(960, 12))
    }

    fun register() {
        LivingArmorRegistrar.registerUpgrade(MANA_UPGRADE)
    }
}
