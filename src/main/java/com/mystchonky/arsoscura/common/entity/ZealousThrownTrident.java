package com.mystchonky.arsoscura.common.entity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ZealousThrownTrident extends ThrownTrident {
    public ZealousThrownTrident(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(pLevel, pShooter, pStack);
    }

    @Override
    public void tickDespawn() {
        ++this.life;
        if (this.life >= 300) {
            this.discard();
        }
    }
}
