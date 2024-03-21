package com.mystchonky.arsoscura.common.mana;

import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.NotEnoughManaPacket;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.registry.CapabilityRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ManaManager {

    public static boolean enoughMana(Player player, int cost) {
        IManaCap manaCap = CapabilityRegistry.getMana(player).orElse(null);

        if (manaCap == null) {
            return false;
        }

        boolean canCast = cost <= manaCap.getCurrentMana() || (player.isCreative());
        if (!canCast && !player.getCommandSenderWorld().isClientSide) {
            PortUtil.sendMessageNoSpam(player, Component.translatable("ars_nouveau.spell.no_mana"));
            if (player instanceof ServerPlayer serverPlayer)
                Networking.sendToPlayerClient(new NotEnoughManaPacket(cost), serverPlayer);
        }

        return canCast;
    }

    public static void consumeMana(Player player, int cost) {
        CapabilityRegistry.getMana(player).ifPresent(manaCap -> manaCap.removeMana(cost));
    }

}
