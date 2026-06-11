package com.mojang.blaze3d;

import com.mojang.jtracy.TracyClient;
import com.mojang.logging.LogListeners;
import org.slf4j.event.Level;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/TracyBootstrap.class */
public class TracyBootstrap {
    private static boolean setup;

    public static void setup() {
        if (setup) {
            return;
        }
        TracyClient.load();
        if (!TracyClient.isAvailable()) {
            return;
        }
        LogListeners.addListener("Tracy", ($$0, $$1) -> {
            TracyClient.message($$0, messageColor($$1));
        });
        setup = true;
    }

    /* JADX INFO: renamed from: com.mojang.blaze3d.TracyBootstrap$1, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/TracyBootstrap$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$slf4j$event$Level = new int[Level.values().length];

        static {
            try {
                $SwitchMap$org$slf4j$event$Level[Level.DEBUG.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$slf4j$event$Level[Level.WARN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$slf4j$event$Level[Level.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private static int messageColor(Level $$0) {
        switch (AnonymousClass1.$SwitchMap$org$slf4j$event$Level[$$0.ordinal()]) {
            case 1:
                return 11184810;
            case 2:
                return 16777130;
            case 3:
                return 16755370;
            default:
                return 16777215;
        }
    }
}
