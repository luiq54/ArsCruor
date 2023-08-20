package com.mystchonky.arsoscura.common.items;

import com.mystchonky.arsoscura.common.init.ArsOscuraLang;
import com.mystchonky.arsoscura.common.util.TooltipUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Sigil extends Item {

    public Sigil(Properties pProperties) {
        super(pProperties);
    }

    public Sigil() {
        this(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        CompoundTag tag = pStack.getTag();
        if (tag != null) {
            String type = tag.getString("entity_type");
            pTooltipComponents.add(TooltipUtil.withArgs(ArsOscuraLang.SIGIL_WITH_ENTITY, type));
        } else {
            pTooltipComponents.add(Component.translatable(ArsOscuraLang.SIGIL_EMPTY.getString()));
        }
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

}
