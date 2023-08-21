package com.mystchonky.arsoscura.integration.occultism.entity

import com.hollingsworth.arsnouveau.api.familiar.IFamiliar
import com.klikli_dev.occultism.common.entity.familiar.DragonFamiliarEntity
import com.klikli_dev.occultism.registry.OccultismEntities
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import java.util.*

//class FamiliarDragon(ent: EntityType<DragonFamiliarEntity>, world: Level?) : DragonFamiliarEntity(ent, world),
//    IFamiliar {
//    lateinit var holderID: ResourceLocation
//    override fun getType(): EntityType<*> {
//        return OccultismEntities.DRAGON_FAMILIAR.get()
//    }
//
//    override fun getHolderID(): ResourceLocation {
//        return holderID
//    }
//
//    override fun setHolderID(id: ResourceLocation) {
//        holderID = id
//    }
//
//    // Map Ars functions to Occultisms
//    override fun getOwnerID(): UUID {
//        return ownerId
//    }
//
//    override fun setOwnerID(uuid: UUID) {
//        familiarOwner = (level() as ServerLevel).getEntity(uuid) as LivingEntity?
//    }
//}
