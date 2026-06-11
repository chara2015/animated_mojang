package net.minecraft.client.gui.components.debug;

import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/debug/DebugScreenProfile.class */
public enum DebugScreenProfile implements StringRepresentable {
    DEFAULT(GameTestEnvironments.DEFAULT, "debug.options.profile.default"),
    PERFORMANCE("performance", "debug.options.profile.performance");

    public static final StringRepresentable.EnumCodec<DebugScreenProfile> CODEC = StringRepresentable.fromEnum(DebugScreenProfile::values);
    private final String name;
    private final String translationKey;

    DebugScreenProfile(String $$0, String $$1) {
        this.name = $$0;
        this.translationKey = $$1;
    }

    public String translationKey() {
        return this.translationKey;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }
}
