package com.mystchonky.arsoscura.integration.bloodmagic.spell;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.ISpellValidator;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellValidationError;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.mystchonky.arsoscura.common.config.BaseConfig;
import com.mystchonky.arsoscura.common.init.ArsOscuraLang;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import wayoftime.bloodmagic.core.data.SoulNetwork;
import wayoftime.bloodmagic.core.data.SoulTicket;
import wayoftime.bloodmagic.util.helper.NetworkHelper;

import java.util.List;

public class BloodSpellResolver extends SpellResolver {

    private final ISpellValidator spellValidator;

    public BloodSpellResolver(SpellContext spellContext) {
        super(spellContext);
        this.spellValidator = ArsNouveauAPI.getInstance().getSpellCastingSpellValidator();
    }

    @Override
    public boolean canCast(LivingEntity entity) {
        // Validate the spell
        List<SpellValidationError> validationErrors = spellValidator.validate(spell.recipe);

        if (validationErrors.isEmpty()) {
            // Validation successful. We can check the player's mana now.
            return enoughMana(entity);
        } else {
            // Validation failed, explain why if applicable
            if (!silent && !entity.getCommandSenderWorld().isClientSide) {
                // Sending only the first error to avoid spam
                PortUtil.sendMessageNoSpam(entity, validationErrors.get(0).makeTextComponentExisting());
            }
            return false;
        }
    }

    boolean enoughMana(LivingEntity entity) {
        if (entity instanceof Player player) {
            if (player.isCreative()) return true;
            int totalCost = getResolveCost() * BaseConfig.COMMON.CONVERSION_RATE.get();
            SoulNetwork soulNetwork = NetworkHelper.getSoulNetwork(player.getUUID());
            //LOGGER.debug("Got soulnetwork for " + soulNetwork.getPlayer().getDisplayName().getString());
            int pool = soulNetwork.getCurrentEssence();
            if (pool < totalCost) {
                PortUtil.sendMessageCenterScreen(player, Component.translatable(ArsOscuraLang.LOW_LP.getString()));
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void expendMana() {
        if (spellContext.getUnwrappedCaster() instanceof Player player) {
            if (!player.isCreative()) {
                int totalCost = getResolveCost() * BaseConfig.COMMON.CONVERSION_RATE.get();
                SoulNetwork soulNetwork = NetworkHelper.getSoulNetwork(player.getUUID());
                SoulTicket ticket = new SoulTicket(Component.literal("TomeOfBlood|" + player.getName()), totalCost);
                soulNetwork.syphonAndDamage(player, ticket);
            }
        }
    }

    @Override
    public SpellResolver getNewResolver(SpellContext context) {
        return new BloodSpellResolver(context);
    }
}
