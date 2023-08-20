package com.mystchonky.arsoscura.common.config

import com.mystchonky.arsoscura.common.config.client.ClientConfig
import com.mystchonky.arsoscura.common.config.common.CommonConfig
import net.minecraftforge.common.ForgeConfigSpec
import org.apache.commons.lang3.tuple.Pair

object BaseConfig {
    val commonSpecPair = ForgeConfigSpec.Builder().configure { CommonConfig(it) }
    val COMMON: CommonConfig = commonSpecPair.left
    val COMMON_SPEC: ForgeConfigSpec = commonSpecPair.right

    val clientSpecPair: Pair<ClientConfig, ForgeConfigSpec> = ForgeConfigSpec.Builder().configure { ClientConfig(it) }
    val CLIENT: ClientConfig = clientSpecPair.left
    val CLIENT_SPEC: ForgeConfigSpec = clientSpecPair.right

}
