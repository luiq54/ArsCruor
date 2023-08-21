package com.mystchonky.arsoscura.integration.bloodmagic.spell

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI
import com.hollingsworth.arsnouveau.api.spell.ISpellValidator
import com.hollingsworth.arsnouveau.api.spell.SpellContext
import com.hollingsworth.arsnouveau.api.spell.SpellResolver
import com.hollingsworth.arsnouveau.common.util.PortUtil
import com.mystchonky.arsoscura.common.config.BaseConfig
import com.mystchonky.arsoscura.common.init.ArsOscuraLang
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import wayoftime.bloodmagic.core.data.SoulNetwork
import wayoftime.bloodmagic.core.data.SoulTicket
import wayoftime.bloodmagic.util.helper.NetworkHelper

class BloodSpellResolver(spellContext: SpellContext) : SpellResolver(spellContext) {
    private val spellValidator: ISpellValidator = ArsNouveauAPI.getInstance().spellCastingSpellValidator

    override fun canCast(entity: LivingEntity): Boolean {
        // Validate the spell
        val validationErrors = spellValidator.validate(spell.recipe)
        return if (validationErrors.isEmpty()) {
            // Validation successful. We can check the player's mana now.
            enoughMana(entity)
        } else {
            // Validation failed, explain why if applicable
            if (!silent && !entity.commandSenderWorld.isClientSide) {
                // Sending only the first error to avoid spam
                PortUtil.sendMessageNoSpam(entity, validationErrors[0].makeTextComponentExisting())
            }
            false
        }
    }

    fun enoughMana(entity: LivingEntity): Boolean {
        if (entity is Player) {
            if (entity.isCreative) return true
            val totalCost = resolveCost * BaseConfig.COMMON.CONVERSION_RATE.get()
            val soulNetwork = NetworkHelper.getSoulNetwork(entity.getUUID())
            //LOGGER.debug("Got soulnetwork for " + soulNetwork.getPlayer().getDisplayName().getString());
            val pool = soulNetwork.currentEssence
            if (pool < totalCost) {
                PortUtil.sendMessageCenterScreen(entity, Component.translatable(ArsOscuraLang.LOW_LP.string))
                return false
            }
            return true
        }
        return false
    }

    override fun expendMana() {
        val player = spellContext.unwrappedCaster
        if (player is Player) {
            if (!player.isCreative) {
                val totalCost = resolveCost * BaseConfig.COMMON.CONVERSION_RATE.get()
                val soulNetwork: SoulNetwork = NetworkHelper.getSoulNetwork(player.getUUID())
                val ticket = SoulTicket(Component.literal("TomeOfBlood|" + player.getName()), totalCost)
                soulNetwork.syphonAndDamage(player, ticket)
            }
        }
    }

    override fun getNewResolver(context: SpellContext): SpellResolver {
        return BloodSpellResolver(context)
    }
}
