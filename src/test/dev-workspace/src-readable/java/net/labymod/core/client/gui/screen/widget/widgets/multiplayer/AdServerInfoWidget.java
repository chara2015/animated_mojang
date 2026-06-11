package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.core.labyconnect.session.advertisement.AdServerData;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/AdServerInfoWidget.class */
public class AdServerInfoWidget extends StorageServerInfoWidget {
    private final Runnable keep;

    public AdServerInfoWidget(@NotNull AdServerData serverData, @NotNull LabyNetServerInfoCache<StorageServerData> cache, @NotNull Consumer<StorageServerInfoWidget> delete, @NotNull Runnable save, @NotNull Runnable keep) {
        super(serverData, cache, delete, save);
        this.keep = keep;
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.multiplayer.StorageServerInfoWidget, net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected void initializeServerInfoNameSuffix(FlexibleContentWidget serverInfoHeader) {
        super.initializeServerInfoNameSuffix(serverInfoHeader);
        ComponentWidget componentWidget = ComponentWidget.component(Component.translatable("labymod.activity.multiplayer.private.ad.badge", new Component[0]));
        componentWidget.addId("ad-badge");
        componentWidget.setHoverComponent(Component.translatable("labymod.activity.multiplayer.private.ad.tooltip", new Component[0]));
        serverInfoHeader.addContent(componentWidget);
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.multiplayer.StorageServerInfoWidget
    protected void initializeContextMenu(ContextMenu contextMenu) {
        super.initializeContextMenu(contextMenu);
        contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable("labymod.activity.multiplayer.private.button.keepServer", new Component[0])).clickHandler(entry -> {
            this.keep.run();
            return true;
        }).build());
    }

    @Override // net.labymod.core.client.gui.screen.widget.widgets.multiplayer.StorageServerInfoWidget
    protected boolean canCustomize() {
        return false;
    }
}
