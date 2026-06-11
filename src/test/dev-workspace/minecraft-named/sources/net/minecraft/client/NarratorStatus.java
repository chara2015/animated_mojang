package net.minecraft.client;

import com.mojang.serialization.Codec;
import java.util.function.IntFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ByIdMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/NarratorStatus.class */
public enum NarratorStatus {
    OFF(0, "options.narrator.off"),
    ALL(1, "options.narrator.all"),
    CHAT(2, "options.narrator.chat"),
    SYSTEM(3, "options.narrator.system");

    private static final IntFunction<NarratorStatus> BY_ID = ByIdMap.continuous((v0) -> {
        return v0.getId();
    }, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final Codec<NarratorStatus> LEGACY_CODEC = Codec.INT.xmap((v0) -> {
        return byId(v0);
    }, (v0) -> {
        return v0.getId();
    });
    private final int id;
    private final Component name;

    NarratorStatus(int $$0, String $$1) {
        this.id = $$0;
        this.name = Component.translatable($$1);
    }

    public int getId() {
        return this.id;
    }

    public Component getName() {
        return this.name;
    }

    public static NarratorStatus byId(int $$0) {
        return BY_ID.apply($$0);
    }

    public boolean shouldNarrateChat() {
        return this == ALL || this == CHAT;
    }

    public boolean shouldNarrateSystem() {
        return this == ALL || this == SYSTEM;
    }

    public boolean shouldNarrateSystemOrChat() {
        return this == ALL || this == SYSTEM || this == CHAT;
    }
}
