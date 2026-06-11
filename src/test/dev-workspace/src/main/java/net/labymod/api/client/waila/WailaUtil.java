package net.labymod.api.client.waila;

import java.util.Locale;
import net.labymod.api.Namespaces;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.client.world.block.BlockState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/WailaUtil.class */
public final class WailaUtil {
    private static final ResourceLocation FIRE = ResourceLocation.create(Namespaces.MINECRAFT, "fire");

    public static String formatProgress(float value) {
        return String.format(Locale.ROOT, "%.0f%%", Float.valueOf(value));
    }

    public static boolean isFire(BlockState state) {
        return isFire(state.block());
    }

    public static boolean isFire(Block block) {
        return block.id().equals(FIRE);
    }
}
