package com.mystchonky.arsoscura.common.items;

import net.minecraft.world.item.Item;

public class Sigil extends Item {

    public Sigil(Properties pProperties) {
        super(pProperties);
    }

    public Sigil() {
        this(new Properties());
    }

//    @Override
//    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
//        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
//        CompoundTag tag = pStack.getTag();
//        if (tag != null) {
//            String type = tag.getString("entity_type");
//            pTooltipComponents.add(TooltipUtil.withArgs(LangRegistry.SIGIL_WITH_ENTITY, type));
//        } else {
//            pTooltipComponents.add(Component.translatable(LangRegistry.SIGIL_EMPTY.getString()));
//        }
//    }
//
//    @Override
//    public boolean isFoil(ItemStack pStack) {
//        return true;
//    }

}
