package net.minecraft.client;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.SnbtOperations;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/CloudStatus.class */
public enum CloudStatus implements StringRepresentable {
    OFF(SnbtOperations.BUILTIN_FALSE, "options.off"),
    FAST("fast", "options.clouds.fast"),
    FANCY(SnbtOperations.BUILTIN_TRUE, "options.clouds.fancy");

    public static final Codec<CloudStatus> CODEC = StringRepresentable.fromEnum(CloudStatus::values);
    private final String legacyName;
    private final Component caption;

    CloudStatus(String $$0, String $$1) {
        this.legacyName = $$0;
        this.caption = Component.translatable($$1);
    }

    public Component caption() {
        return this.caption;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.legacyName;
    }
}
