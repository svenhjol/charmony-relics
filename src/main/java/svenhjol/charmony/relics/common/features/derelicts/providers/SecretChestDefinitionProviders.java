package svenhjol.charmony.relics.common.features.derelicts.providers;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import svenhjol.charmony.api.secret_chests.SecretChestDefinition;
import svenhjol.charmony.api.secret_chests.SecretChestDefinitionProvider;
import svenhjol.charmony.api.secret_chests.SecretChestPlacement;
import svenhjol.charmony.api.stone_chests.StoneChestMaterial;
import svenhjol.charmony.relics.common.features.derelicts.Tags;

import java.util.List;

public class SecretChestDefinitionProviders implements SecretChestDefinitionProvider {
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
                Tags.BOOKS_CHEST
            );
        }

        @Override
        public int difficultyAmplifier() {
            return 3;
        }

        @Override
        public List<String> puzzleMenus() {
            return List.of(
                "moon_puzzle",
                "clock_puzzle"
            );
        }
    };

    @Override
    public List<SecretChestDefinition> getSecretChestDefinitions() {
        return List.of(DERELICT);
    }
}
