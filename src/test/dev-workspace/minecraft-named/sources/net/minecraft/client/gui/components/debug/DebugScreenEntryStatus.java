package net.minecraft.client.gui.components.debug;

import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/debug/DebugScreenEntryStatus.class */
public enum DebugScreenEntryStatus implements StringRepresentable {
    ALWAYS_ON("alwaysOn"),
    IN_OVERLAY("inOverlay"),
    NEVER("never");

    public static final StringRepresentable.EnumCodec<DebugScreenEntryStatus> CODEC = StringRepresentable.fromEnum(DebugScreenEntryStatus::values);
    private final String name;

    DebugScreenEntryStatus(String $$0) {
        this.name = $$0;
    }

    @Override // net.minecraft.util.StringRepresentable
    public String getSerializedName() {
        return this.name;
    }
}
