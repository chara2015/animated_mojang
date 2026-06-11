package net.labymod.core.client.accessor.gui;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/accessor/gui/SpriteIconButtonAccessor.class */
public interface SpriteIconButtonAccessor {
    ResourceLocation getResourceLocation();

    Component getMessage();

    Component getTooltip();

    boolean iconOnly();
}
