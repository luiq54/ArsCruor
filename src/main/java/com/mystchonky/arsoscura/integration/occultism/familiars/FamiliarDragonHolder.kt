package com.mystchonky.arsoscura.integration.occultism.familiars

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder
import com.hollingsworth.arsnouveau.api.familiar.IFamiliar
import com.klikli_dev.occultism.common.entity.familiar.DragonFamiliarEntity
import com.klikli_dev.occultism.registry.OccultismEntities
import com.mystchonky.arsoscura.ArsOscura.prefix
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.Level

//class FamiliarDragonHolder : AbstractFamiliarHolder(
//    prefix(FamiliarLibrary.FAMILIAR_DRAGON),
//    { e: Entity -> e is DragonFamiliarEntity }) {
//    override fun getSummonEntity(
//        world: Level,
//        tag: CompoundTag
//    ): IFamiliar {
//        return FamiliarDragon(OccultismEntities.DRAGON_FAMILIAR_TYPE.get(), world)
//    }
//
//    override fun getBookName(): String {
//        return "Dragon"
//    }
//
//    override fun getBookDescription(): String {
//        return "Small dragon dude"
//    }
//}
