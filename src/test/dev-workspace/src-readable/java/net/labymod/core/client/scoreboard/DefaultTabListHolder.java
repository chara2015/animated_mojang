package net.labymod.core.client.scoreboard;

import java.util.function.BooleanSupplier;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.scoreboard.TabListHolder;
import net.labymod.api.event.client.gui.screen.playerlist.PlayerListUpdateEvent;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/scoreboard/DefaultTabListHolder.class */
public final class DefaultTabListHolder implements TabListHolder {
    private static final PlayerListUpdateEvent UPDATE_EVENT = new PlayerListUpdateEvent();
    private final BooleanSupplier visibleSupplier;
    private final ComponentMapper mapper = Laby.references().componentMapper();

    @Nullable
    private Component header;

    @Nullable
    private Component footer;

    public DefaultTabListHolder(BooleanSupplier visibleSupplier) {
        this.visibleSupplier = visibleSupplier;
    }

    @Override // net.labymod.api.client.scoreboard.TabListHolder
    @Nullable
    public Component getHeader() {
        return this.header;
    }

    public void setHeader(Object component) {
        setHeader(component == null ? null : this.mapper.fromMinecraftComponent(component));
    }

    public void setHeader(Component header) {
        this.header = header;
        Laby.fireEvent(UPDATE_EVENT);
    }

    @Override // net.labymod.api.client.scoreboard.TabListHolder
    @Nullable
    public Component getFooter() {
        return this.footer;
    }

    public void setFooter(Object component) {
        setFooter(component == null ? null : this.mapper.fromMinecraftComponent(component));
    }

    public void setFooter(Component footer) {
        this.footer = footer;
        Laby.fireEvent(UPDATE_EVENT);
    }

    public void reset() {
        this.header = null;
        this.footer = null;
        Laby.fireEvent(UPDATE_EVENT);
    }

    @Override // net.labymod.api.client.scoreboard.TabListHolder
    public boolean isVisible() {
        return this.visibleSupplier.getAsBoolean();
    }
}
