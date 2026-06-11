package net.labymod.api.client.waila.tooltip;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.waila.Waila;
import net.labymod.api.service.Identifiable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/waila/tooltip/WailaTooltip.class */
public abstract class WailaTooltip implements Identifiable {
    private final String id;
    private Waila<?> currentWaila;

    @Nullable
    public abstract Widget createWidget();

    protected WailaTooltip(String id) {
        this.id = id;
    }

    public final void updateWaila(Waila<?> waila) {
        this.currentWaila = waila;
    }

    @Nullable
    public final Waila<?> getWaila() {
        return this.currentWaila;
    }

    @Override // net.labymod.api.service.Identifiable
    public String getId() {
        return this.id;
    }
}
