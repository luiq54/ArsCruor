package com.mystchonky.arsoscura.integration.bloodmagic.items;

import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.mystchonky.arsoscura.integration.bloodmagic.client.renderer.item.TomeOfBloodRenderer;
import com.mystchonky.arsoscura.integration.bloodmagic.spell.BloodSpellResolver;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class TomeOfBlood extends SpellBook {

    public TomeOfBlood(Properties properties, SpellTier tier) {
        super(properties, tier);
        this.tier = tier;
    }

    @Override
    public @NotNull ISpellCaster getSpellCaster(ItemStack stack) {
        return new BloodBookCaster(stack);
    }

    public static class BloodBookCaster extends BookCaster {

        public BloodBookCaster(ItemStack stack) {
            super(stack);
        }

        @Override
        public SpellResolver getSpellResolver(SpellContext context, Level worldIn, LivingEntity playerIn, InteractionHand handIn) {
            return new BloodSpellResolver(context);
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new TomeOfBloodRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }
}
