package com.mystchonky.arsoscura.common.entity;

import com.mystchonky.arsoscura.ArsOscura;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.level.Level;

public class EnchantedHorse extends Horse {
    public EnchantedHorse(EntityType<? extends Horse> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void onPlayerJump(int pJumpPower) {
        super.onPlayerJump(pJumpPower);
        this.setSharedFlag(7, true);
        ArsOscura.LOGGER.info("start fly");
    }

    @Override
    public void updateFallFlying() {
        boolean flag = !this.onGround();
        if (!this.level().isClientSide) {
            this.setSharedFlag(7, flag);
        }
        if (!flag)
            ArsOscura.LOGGER.info("stop fly");
    }
}
