package com.mystchonky.arsoscura.datagen;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.mystchonky.arsoscura.common.init.IntegrationRegistry;
import net.minecraft.data.DataGenerator;

public class PatchouliProvider extends com.hollingsworth.arsnouveau.common.datagen.PatchouliProvider {

    public PatchouliProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public void addEntries() {
        for (AbstractSpellPart spell : IntegrationRegistry.registeredSpells) {
            addGlyphPage(spell);
        }
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Override
    public String getName() {
        return "Example Patchouli Datagen";
    }
}
