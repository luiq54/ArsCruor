package com.mystchonky.arsoscura.common.registrar;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;

import java.util.ArrayList;
import java.util.List;

public class IntegrationRegistrar {

    public static ArsNouveauAPI ArsNouveau = ArsNouveauAPI.getInstance();

    public static List<AbstractSpellPart> registeredSpells = new ArrayList<>();
    public static List<AbstractFamiliarHolder> registeredFamiliars = new ArrayList<>();

    public static void init() {
    }

    public static void postInit() {
        ArsNouveau.getEnchantingRecipeTypes().add(RecipeRegistrar.ENCHANTMENT_UPAGRADE.type().get());
    }

}
