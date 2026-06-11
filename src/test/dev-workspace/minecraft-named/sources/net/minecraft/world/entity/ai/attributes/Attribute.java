package net.minecraft.world.entity.ai.attributes;

import com.mojang.serialization.Codec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/attributes/Attribute.class */
public class Attribute {
    public static final Codec<Holder<Attribute>> CODEC = BuiltInRegistries.ATTRIBUTE.holderByNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<Attribute>> STREAM_CODEC = ByteBufCodecs.holderRegistry(Registries.ATTRIBUTE);
    private final double defaultValue;
    private boolean syncable;
    private final String descriptionId;
    private Sentiment sentiment = Sentiment.POSITIVE;

    protected Attribute(String $$0, double $$1) {
        this.defaultValue = $$1;
        this.descriptionId = $$0;
    }

    public double getDefaultValue() {
        return this.defaultValue;
    }

    public boolean isClientSyncable() {
        return this.syncable;
    }

    public Attribute setSyncable(boolean $$0) {
        this.syncable = $$0;
        return this;
    }

    public Attribute setSentiment(Sentiment $$0) {
        this.sentiment = $$0;
        return this;
    }

    public double sanitizeValue(double $$0) {
        return $$0;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public ChatFormatting getStyle(boolean $$0) {
        return this.sentiment.getStyle($$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/attributes/Attribute$Sentiment.class */
    public enum Sentiment {
        POSITIVE,
        NEUTRAL,
        NEGATIVE;

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        public ChatFormatting getStyle(boolean $$0) throws MatchException {
            switch (this) {
                case POSITIVE:
                    return $$0 ? ChatFormatting.BLUE : ChatFormatting.RED;
                case NEUTRAL:
                    return ChatFormatting.GRAY;
                case NEGATIVE:
                    return $$0 ? ChatFormatting.RED : ChatFormatting.BLUE;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
    }
}
