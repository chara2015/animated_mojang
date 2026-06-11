package net.minecraft.client.multiplayer.chat.report;

import java.util.Locale;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/report/ReportType.class */
public enum ReportType {
    CHAT("chat"),
    SKIN("skin"),
    USERNAME("username");

    private final String backendName;

    ReportType(String $$0) {
        this.backendName = $$0.toUpperCase(Locale.ROOT);
    }

    public String backendName() {
        return this.backendName;
    }
}
