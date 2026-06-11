package net.labymod.api.event.client.render.overlay;

import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/overlay/HudWidgetDropzoneElementShiftEvent.class */
public class HudWidgetDropzoneElementShiftEvent extends DefaultCancellable implements Event {
    private final boolean isOffHandSide;
    private final ItemStack itemStack;

    public HudWidgetDropzoneElementShiftEvent(boolean isOffHandSide, ItemStack itemStack) {
        this.isOffHandSide = isOffHandSide;
        this.itemStack = itemStack;
    }

    public boolean isOffHandSide() {
        return this.isOffHandSide;
    }

    public ItemStack itemStack() {
        return this.itemStack;
    }
}
