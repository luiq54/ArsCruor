package com.mystchonky.arsoscura.common.network

import com.mystchonky.arsoscura.ArsOscura
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.Level
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.PacketDistributor
import net.minecraftforge.network.simple.SimpleChannel

object Networking {
    lateinit var INSTANCE: SimpleChannel
    private var ID = 0
    fun nextID(): Int {
        return ID++
    }

    fun registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(ResourceLocation(ArsOscura.MODID, "network"),
            { "1.0" },
            { true },
            { true })
    }

    fun sendToNearby(world: Level, pos: BlockPos, toSend: Any) {
        if (world is ServerLevel) {
            world.chunkSource.chunkMap.getPlayers(ChunkPos(pos), false).stream()
                .filter {
                    it.distanceToSqr(
                        pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()
                    ) < 64 * 64
                }.forEach { INSTANCE.send(PacketDistributor.PLAYER.with { it }, toSend) }
        }
    }

    fun sendToNearby(world: Level, e: Entity, toSend: Any) {
        sendToNearby(world, e.blockPosition(), toSend)
    }

    fun sendToPlayerClient(msg: Any, player: ServerPlayer?) {
        INSTANCE.send(PacketDistributor.PLAYER.with { player }, msg)
    }

    fun sendToServer(msg: Any) {
        INSTANCE.sendToServer(msg)
    }
}
