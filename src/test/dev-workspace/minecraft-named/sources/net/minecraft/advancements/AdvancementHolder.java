package net.minecraft.advancements;

import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/AdvancementHolder.class */
public final class AdvancementHolder extends Record {
    private final Identifier id;
    private final Advancement value;
    public static final StreamCodec<RegistryFriendlyByteBuf, AdvancementHolder> STREAM_CODEC = StreamCodec.composite(Identifier.STREAM_CODEC, (v0) -> {
        return v0.id();
    }, Advancement.STREAM_CODEC, (v0) -> {
        return v0.value();
    }, AdvancementHolder::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, List<AdvancementHolder>> LIST_STREAM_CODEC = STREAM_CODEC.apply(ByteBufCodecs.list());

    public AdvancementHolder(Identifier $$0, Advancement $$1) {
        this.id = $$0;
        this.value = $$1;
    }

    public Identifier id() {
        return this.id;
    }

    public Advancement value() {
        return this.value;
    }

    @Override // java.lang.Record
    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 instanceof AdvancementHolder) {
            AdvancementHolder $$1 = (AdvancementHolder) $$0;
            if (this.id.equals($$1.id)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.lang.Record
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override // java.lang.Record
    public String toString() {
        return this.id.toString();
    }
}
