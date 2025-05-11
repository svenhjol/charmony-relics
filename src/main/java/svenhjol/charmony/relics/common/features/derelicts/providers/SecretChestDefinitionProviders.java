package svenhjol.charmony.relics.common.features.derelicts.providers;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import svenhjol.charmony.api.Api;
import svenhjol.charmony.api.secret_chests.SecretChestDefinition;
import svenhjol.charmony.api.secret_chests.SecretChestDefinitionProvider;
import svenhjol.charmony.api.secret_chests.SecretChestPlacement;
import svenhjol.charmony.api.stone_chests.StoneChestMaterial;
import svenhjol.charmony.core.base.Setup;
import svenhjol.charmony.relics.common.features.derelicts.Derelicts;
import svenhjol.charmony.relics.common.features.derelicts.Tags;

import java.util.List;

public class SecretChestDefinitionProviders extends Setup<Derelicts> implements SecretChestDefinitionProvider {
    public static final SecretChestDefinition DERELICT = new SecretChestDefinition() {
        @Override
        public String name() {
            return "derelict";
        }

        @Override
        public StoneChestMaterial material() {
            return StoneChestMaterial.Deepslate;
        }

        @Override
        public SecretChestPlacement placement() {
            return SecretChestPlacement.Buried;
        }

        @Override
        public List<ResourceKey<LootTable>> lootTables() {
            return List.of(
                Tags.LOOT_DIAMONDS
            );
        }

        @Override
        public int difficultyAmplifier() {
            return 3;
        }

        @Override
        public List<String> lockMenus() {
            return List.of(
                "moon_puzzle"
            );
        }
    };

    public SecretChestDefinitionProviders(Derelicts feature) {
        super(feature);
        Api.registerProvider(this);
    }

    @Override
    public List<SecretChestDefinition> getSecretChestDefinitions() {
        return List.of(DERELICT);
    }
}
