package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.network.server.global.PublicServerData;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/PublicServerInfoWidget.class */
@AutoWidget
public class PublicServerInfoWidget extends LabyNetServerInfoWidget<PublicServerData> {
    private final Predicate<PublicServerInfoWidget> savable;
    private final Consumer<PublicServerInfoWidget> save;

    public PublicServerInfoWidget(@NotNull PublicServerData serverData, @NotNull LabyNetServerInfoCache<PublicServerData> cache, @NotNull Predicate<PublicServerInfoWidget> savable, @NotNull Consumer<PublicServerInfoWidget> save) {
        super(serverData, cache);
        Objects.requireNonNull(savable, "Savable predicate cannot be null!");
        Objects.requireNonNull(save, "Save consumer cannot be null!");
        this.savable = savable;
        this.save = save;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ContextMenu contextMenu = createContextMenu();
        ContextMenuEntry saveContextMenuEntry = ContextMenuEntry.builder().icon(Textures.SpriteCommon.PIN).text(Component.translatable("labymod.activity.multiplayer.public.button.saveServer", new Component[0])).disabled(() -> {
            return !this.savable.test(this);
        }).simpleClickHandler(entry -> {
            this.save.accept(this);
        }).build();
        contextMenu.addEntry(saveContextMenuEntry);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget, net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget
    protected Component serverName() {
        TextComponent rank;
        TextComponent rank2 = Component.text("#" + (cache().getSortingValue() + 1) + " ");
        if (((PublicServerData) serverData()).isPartner()) {
            rank = rank2.color(NamedTextColor.YELLOW).hoverEvent(HoverEvent.showText(Component.text("LabyMod Partner").color(NamedTextColor.YELLOW)));
        } else {
            rank = rank2.color(NamedTextColor.GRAY);
        }
        return Component.empty().append(rank).append(super.serverName());
    }
}
