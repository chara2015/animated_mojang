package net.labymod.api.event.client.gui.screen.title;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/title/TitleScreenLogoInitializeEvent.class */
public class TitleScreenLogoInitializeEvent implements Event {
    private final String type;

    @Nullable
    private Widget minecraftWidget;

    @Nullable
    private Widget editionWidget;

    public TitleScreenLogoInitializeEvent(String type, @Nullable Widget minecraftWidget, @Nullable Widget editionWidget) {
        this.type = type;
        this.minecraftWidget = minecraftWidget;
        this.editionWidget = editionWidget;
    }

    public String getType() {
        return this.type;
    }

    @Nullable
    public Widget getMinecraftWidget() {
        return this.minecraftWidget;
    }

    @Nullable
    public Widget getEditionWidget() {
        return this.editionWidget;
    }

    public void setMinecraftWidget(@Nullable Widget minecraftWidget) {
        this.minecraftWidget = minecraftWidget;
    }

    public void setEditionWidget(@Nullable Widget editionWidget) {
        this.editionWidget = editionWidget;
    }
}
