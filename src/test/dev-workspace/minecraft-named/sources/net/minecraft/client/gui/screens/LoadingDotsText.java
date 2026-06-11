package net.minecraft.client.gui.screens;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/LoadingDotsText.class */
public class LoadingDotsText {
    private static final String[] FRAMES = {"O o o", "o O o", "o o O", "o O o"};
    private static final long INTERVAL_MS = 300;

    public static String get(long $$0) {
        int $$1 = (int) (($$0 / INTERVAL_MS) % ((long) FRAMES.length));
        return FRAMES[$$1];
    }
}
