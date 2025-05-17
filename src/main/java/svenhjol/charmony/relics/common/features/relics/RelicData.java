package svenhjol.charmony.relics.common.features.relics;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import svenhjol.charmony.api.relics.RelicType;

@SuppressWarnings("unused")
public record RelicData(RelicType type) {
    public static final Codec<RelicData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        RelicType.CODEC.fieldOf("type")
            .forGetter(RelicData::type)
    ).apply(instance, RelicData::new));

    public static final StreamCodec<FriendlyByteBuf, RelicData> STREAM_CODEC = StreamCodec.composite(
        RelicType.STREAM_CODEC,
            RelicData::type,
        RelicData::new
    );

    public static final RelicData EMPTY = new RelicData(RelicType.Unknown);

    public static Mutable create() {
        return new Mutable(EMPTY);
    }

    public static boolean has(ItemStack stack) {
        return stack.has(Relics.feature().registers.data.get());
    }

    public static RelicData get(ItemStack stack) {
        return stack.getOrDefault(Relics.feature().registers.data.get(), EMPTY);
    }

    public static void set(ItemStack stack, RelicData data) {
        stack.set(Relics.feature().registers.data.get(), data);
    }

    public static class Mutable {
        private RelicType type;

        public Mutable(RelicData data) {
            this.type = data.type;
        }

        public Mutable setType(RelicType type) {
            this.type = type;
            return this;
        }

        public RelicData toImmutable() {
            return new RelicData(type);
        }

        public void save(ItemStack stack) {
            RelicData.set(stack, this.toImmutable());
        }
    }
}
