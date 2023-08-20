package com.mystchonky.arsoscura.common.config.common

import net.minecraftforge.common.ForgeConfigSpec

class CommonConfig(builder: ForgeConfigSpec.Builder) {
    val CONVERSION_RATE: ForgeConfigSpec.ConfigValue<Int>

    init {
        CONVERSION_RATE = builder.comment("Conversion rate of LP into player mana").define("conversionRate", 10)
    }
}
