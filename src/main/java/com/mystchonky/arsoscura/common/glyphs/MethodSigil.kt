package com.mystchonky.arsoscura.common.glyphs

import com.hollingsworth.arsnouveau.api.spell.*
import com.hollingsworth.arsnouveau.common.lib.GlyphLib
import com.mystchonky.arsoscura.ArsOscura
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.phys.BlockHitResult
import java.util.*

class MethodSigil : AbstractCastMethod(id, "Cast on Sigil") {

    companion object INSTANCE {
        private val id = ArsOscura.prefix(GlyphLib.prependGlyph("sigil_cast"))
    }

    override fun onCast(
        stack: ItemStack?,
        caster: LivingEntity,
        world: Level,
        spellStats: SpellStats,
        context: SpellContext,
        resolver: SpellResolver
    ): CastResolveType {
        val item = getSigilFromCaster(caster)
        if (item.isPresent && caster is Player) {
            if (!caster.cooldowns.isOnCooldown(item.get().item)) {
                return castUsingSigil(caster, item.get(), world, resolver)
            }
        }
        return CastResolveType.FAILURE
    }

    override fun onCastOnBlock(
        context: UseOnContext,
        spellStats: SpellStats,
        spellContext: SpellContext,
        resolver: SpellResolver
    ): CastResolveType {
        val world = context.level
        val player = context.player
        if (player != null) {
            val item = getSigilFromCaster(player)
            if (item.isPresent) {
                if (!player.cooldowns.isOnCooldown(item.get().item)) {
                    return castUsingSigil(player, item.get(), world, resolver)
                }
            }
        }
        return CastResolveType.FAILURE
    }

    override fun onCastOnBlock(
        blockRayTraceResult: BlockHitResult,
        caster: LivingEntity,
        spellStats: SpellStats,
        spellContext: SpellContext,
        resolver: SpellResolver
    ): CastResolveType {
        val world = caster.level()
        val item = getSigilFromCaster(caster)
        if (item.isPresent && caster is Player) {
            if (!caster.cooldowns.isOnCooldown(item.get().item)) {
                return castUsingSigil(caster, item.get(), world, resolver)
            }
        }
        return CastResolveType.FAILURE
    }

    override fun onCastOnEntity(
        stack: ItemStack?,
        caster: LivingEntity,
        target: Entity,
        hand: InteractionHand,
        spellStats: SpellStats,
        spellContext: SpellContext,
        resolver: SpellResolver
    ): CastResolveType {
        val world = caster.level()
        val item = getSigilFromCaster(caster)
        if (item.isPresent && caster is Player) {
            if (!caster.cooldowns.isOnCooldown(item.get().item)) {
                return castUsingSigil(caster, item.get(), world, resolver)
            }
        }
        return CastResolveType.FAILURE
    }

    public override fun getDefaultManaCost(): Int {
        return 10
    }

    override fun getCompatibleAugments(): Set<AbstractAugment> {
        return augmentSetOf()
    }

    private fun castUsingSigil(
        caster: Player,
        sigil: ItemStack,
        world: Level,
        resolver: SpellResolver
    ): CastResolveType {
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
        return CastResolveType.FAILURE
    }

    private fun getEntityFromCasterSigil(world: Level, sigil: ItemStack): Entity? {
        if (world is ServerLevel) {
            val tag = sigil.tag
            if (tag != null && tag.contains("entity_uuid")) {
                val uuid = tag.getString("entity_uuid")
                return world.getEntity(UUID.fromString(uuid))
            }
        }
        return null
    }

    private fun getSigilFromCaster(caster: LivingEntity): Optional<ItemStack> {
//        if (caster.isHolding(ArsOscuraItems.SIGIL.get())) {
//            ItemStack main = caster.getItemInHand(InteractionHand.MAIN_HAND);
//            if (main.is(ArsOscuraItems.SIGIL.get())) return Optional.of(main);
//            return Optional.of(caster.getItemInHand(InteractionHand.OFF_HAND));
//        }
        return Optional.empty()
    }

}
