package com.mystchonky.arsoscura.integration.occultism.familiars;

import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.familiar.IFamiliar;
import com.klikli_dev.occultism.common.entity.familiar.DragonFamiliarEntity;
import com.klikli_dev.occultism.registry.OccultismEntities;
import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.integration.occultism.entity.FamiliarDragon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class FamiliarDragonHolder extends AbstractFamiliarHolder {

    public FamiliarDragonHolder() {
        super(ArsOscura.prefix(FamiliarLibrary.FAMILIAR_DRAGON), (e) -> e instanceof DragonFamiliarEntity);
    }

    @Override
    public IFamiliar getSummonEntity(Level world, CompoundTag tag) {
        FamiliarDragon familiarEntity = new FamiliarDragon(OccultismEntities.DRAGON_FAMILIAR_TYPE.get(), world);
        return familiarEntity;
    }

    @Override
    public String getBookName() {
        return "Dragon";
    }

    @Override
    public String getBookDescription() {
        return "Small dragon dude";
    }
}
