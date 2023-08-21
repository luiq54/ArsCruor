package com.mystchonky.arsoscura.integration.occultism.mob_jar

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile
import com.klikli_dev.occultism.api.OccultismAPI
import com.klikli_dev.occultism.common.entity.job.SpiritJob
import com.klikli_dev.occultism.common.entity.spirit.SpiritEntity
import com.klikli_dev.occultism.exceptions.ItemHandlerMissingException
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.items.ItemHandlerHelper

class SpiritBehaviour<T : SpiritEntity> : JarBehavior<T>() {
    override fun use(
        state: BlockState,
        world: Level,
        pos: BlockPos,
        player: Player,
        handIn: InteractionHand,
        hit: BlockHitResult,
        tile: MobJarTile
    ) {
        if (world.isClientSide) return
        val heldStack = player.getItemInHand(handIn)
        val spirit: SpiritEntity = entityFromJar(tile)
        val api = OccultismAPI.get()
        api.getItemsToPickUp(spirit).ifPresent { ingredients: List<Ingredient> ->
            if (ingredients.stream().anyMatch { item: Ingredient -> item.test(heldStack) }) {
                val duplicate = heldStack.copy()
                val handler = spirit.itemStackHandler.orElseThrow { ItemHandlerMissingException() }
                if (ItemHandlerHelper.insertItemStacked(handler, duplicate, true).count < duplicate.count) {
                    val remaining = ItemHandlerHelper.insertItemStacked(handler, duplicate, false)
                    heldStack.count = remaining.count
                }
            }
        }
    }

    override fun tick(tile: MobJarTile) {
        if (tile.level!!.isClientSide) return
        val spirit: SpiritEntity = entityFromJar(tile)
        if (!spirit.isInitialized)
            spirit.init()

        if (tile.level!!.getRandom().nextInt(20) == 0) {
            val itemEntities = spirit.level().getEntitiesOfClass(
                ItemEntity::class.java, AABB(tile.blockPos).inflate(3.0)
            ) { it.isAlive }
            if (itemEntities.isNotEmpty()) {
                val itemEntity: ItemEntity? = itemEntities.stream()
                    .filter { OccultismAPI.get().canPickupItem(spirit, it).orElse(false) }
                    .findFirst()
                    .orElse(null)
                if (itemEntity != null) {
                    val duplicate = itemEntity.item.copy()
                    val handler = spirit.itemStackHandler.orElseThrow { ItemHandlerMissingException() }
                    if (ItemHandlerHelper.insertItemStacked(handler, duplicate, true).count < duplicate.count) {
                        val remaining = ItemHandlerHelper.insertItemStacked(handler, duplicate, false)
                        itemEntity.item.count = remaining.count
                    }
                }
            }
        }
        spirit.job.ifPresent {
            it.update()
            tile.updateBlock()
        }
    }
}
