package net.minecraft.client;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/InactivityFpsLimit.class */
public enum InactivityFpsLimit implements StringRepresentable {
    MINIMIZED("minimized", "options.inactivityFpsLimit.minimized"),
    AFK("afk", "options.inactivityFpsLimit.afk");

    public static final Codec<InactivityFpsLimit> CODEC = StringRepresentable.fromEnum(InactivityFpsLimit::values);
    private final String serializedName;
    private final Component caption;

    InactivityFpsLimit(String $$0, String $$1) {
        this.serializedName = $$0;
        this.caption = Component.translatable($$1);
    }

    public Component caption() {
        return this.caption;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.serializedName;
    }
}
