package com.mystchonky.arsoscura.common.glyphs;

import com.hollingsworth.arsnouveau.api.spell.*;
import com.hollingsworth.arsnouveau.common.lib.GlyphLib;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.mystchonky.arsoscura.ArsOscura.prefix;

public class MethodSigil extends AbstractCastMethod {

    private static final String id = GlyphLib.prependGlyph("sigil_cast");
    public static MethodSigil INSTANCE = new MethodSigil();

    public MethodSigil() {
        super(prefix(id), "Cast on Sigil");
    }


    @Override
    public CastResolveType onCast(@Nullable ItemStack stack, LivingEntity caster, Level world, SpellStats spellStats, SpellContext context, SpellResolver resolver) {
        Optional<ItemStack> item = getSigilFromCaster(caster);
        if (item.isPresent() && caster instanceof Player player) {
            if (!player.getCooldowns().isOnCooldown(item.get().getItem())) {
                return castUsingSigil(player, item.get(), world, resolver);
            }
        }
        return CastResolveType.FAILURE;
    }

    @Override
    public CastResolveType onCastOnBlock(UseOnContext context, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        Level world = context.getLevel();
        Player player = context.getPlayer();
        if (player != null) {
            Optional<ItemStack> item = getSigilFromCaster(player);
            if (item.isPresent()) {
                if (!player.getCooldowns().isOnCooldown(item.get().getItem())) {
                    return castUsingSigil(player, item.get(), world, resolver);
                }
            }
        }
        return CastResolveType.FAILURE;
    }

    @Override
    public CastResolveType onCastOnBlock(BlockHitResult blockRayTraceResult, LivingEntity caster, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        Level world = caster.level();
        Optional<ItemStack> item = getSigilFromCaster(caster);
        if (item.isPresent() && caster instanceof Player player) {
            if (!player.getCooldowns().isOnCooldown(item.get().getItem())) {
                return castUsingSigil(player, item.get(), world, resolver);
            }
        }
        return CastResolveType.FAILURE;
    }

    @Override
    public CastResolveType onCastOnEntity(@Nullable ItemStack stack, LivingEntity caster, Entity target, InteractionHand hand, SpellStats spellStats, SpellContext spellContext, SpellResolver resolver) {
        Level world = caster.level();
        Optional<ItemStack> item = getSigilFromCaster(caster);
        if (item.isPresent() && caster instanceof Player player) {
            if (!player.getCooldowns().isOnCooldown(item.get().getItem())) {
                return castUsingSigil(player, item.get(), world, resolver);
            }
        }
        return CastResolveType.FAILURE;
    }

    @Override
    public int getDefaultManaCost() {
        return 10;
    }

    @Override
    protected @NotNull Set<AbstractAugment> getCompatibleAugments() {
        return augmentSetOf();
    }

    private CastResolveType castUsingSigil(Player caster, ItemStack sigil, Level world, SpellResolver resolver) {
//        if (CapabilityRegistry.getEssence(caster).isPresent()) {
//            Entity entity = getEntityFromCasterSigil(world, sigil);
//            IEssenceCap essence = CapabilityRegistry.getEssence(caster).orElse(null);
//            if (essence.getCurrentEssence() > getEssenceCost()) {
//                if (entity != null) {
//                    resolver.onResolveEffect(world, new EntityHitResult(entity));
//                    essence.removeEssence(getEssenceCost());
//                    caster.getCooldowns().addCooldown(sigil.getItem(), 100);
//                    Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) caster), new PacketUpdateEssence(essence.getCurrentEssence(), essence.getMaxEssence()));
//                    return CastResolveType.SUCCESS;
//                }
//            }
//
//        }

        return CastResolveType.FAILURE;
    }

    @Nullable
    private Entity getEntityFromCasterSigil(Level world, ItemStack sigil) {
        if (world instanceof ServerLevel level) {
            CompoundTag tag = sigil.getTag();
            if (tag != null && tag.contains("entity_uuid")) {
                String uuid = tag.getString("entity_uuid");
                return level.getEntity(UUID.fromString(uuid));
            }

        }
        return null;
    }

    @NotNull
    private Optional<ItemStack> getSigilFromCaster(LivingEntity caster) {
//        if (caster.isHolding(ArsOscuraItems.SIGIL.get())) {
//            ItemStack main = caster.getItemInHand(InteractionHand.MAIN_HAND);
//            if (main.is(ArsOscuraItems.SIGIL.get())) return Optional.of(main);
//            return Optional.of(caster.getItemInHand(InteractionHand.OFF_HAND));
//        }
        return Optional.empty();
    }
}
