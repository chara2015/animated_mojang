package net.labymod.api.client.world.effect;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/effect/PotionEffect.class */
public interface PotionEffect {
    int getDuration();

    int getAmplifier();

    String getTranslationKey();

    @Deprecated
    Icon getIcon();

    void renderIcon(ScreenContext screenContext, int i, int i2, int i3, int i4);

    boolean hasMaxDuration();

    default boolean isInfiniteDuration() {
        return false;
    }
}
