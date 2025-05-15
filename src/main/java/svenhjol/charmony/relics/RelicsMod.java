package svenhjol.charmony.relics;

import net.minecraft.resources.ResourceLocation;
import svenhjol.charmony.core.annotations.ModDefinition;
import svenhjol.charmony.core.base.Mod;
import svenhjol.charmony.core.enums.Side;

@ModDefinition(
    id = RelicsMod.ID,
    sides = {Side.Client, Side.Common},
    name = "Relics",
    description = "Rare items with overpowered enchantments hidden in lost locations.")
public final class RelicsMod extends Mod {
    public static final String ID = "charmony-relics";
    private static RelicsMod instance;

    private RelicsMod() {}

    public static RelicsMod instance() {
        if (instance == null) {
            instance = new RelicsMod();
        }
        return instance;
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }
}