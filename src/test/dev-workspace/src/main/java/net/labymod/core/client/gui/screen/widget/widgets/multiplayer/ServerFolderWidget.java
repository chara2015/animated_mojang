package net.labymod.core.client.gui.screen.widget.widgets.multiplayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BooleanSupplier;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.network.server.storage.ServerFolder;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.util.Color;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/ServerFolderWidget.class */
@AutoWidget
public class ServerFolderWidget extends ServerEntryWidget {
    private final List<LabyNetServerInfoCache<StorageServerData>> servers = new ArrayList();
    private final ServerFolder folder;
    private final Runnable delete;
    private final Runnable save;
    private final BooleanSupplier dragging;
    private IconWidget iconWidget;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/ServerFolderWidget$FolderRenameCallback.class */
    public interface FolderRenameCallback {
        void rename(String str, Color color);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/multiplayer/ServerFolderWidget$FolderUpdateCallback.class */
    public interface FolderUpdateCallback {
        void update(String str, Color color);
    }

    public ServerFolderWidget(ServerFolder folder, Runnable delete, Runnable save, BooleanSupplier dragging) {
        this.folder = folder;
        this.delete = delete;
        this.save = save;
        this.dragging = dragging;
        createContextMenu().with(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.rename", new Component[0])).icon(Textures.SpriteCommon.SETTINGS).clickHandler(entry -> {
            String previousName = folder.getName();
            Color previousColor = folder.getColor();
            promptRename(this.folder, (name, color) -> {
                this.folder.setName(name);
                this.folder.setColor(color);
                this.save.run();
                reInitialize();
            }, (name2, color2) -> {
                if (name2 != null) {
                    this.folder.setName(name2);
                    reInitialize();
                }
                if (color2 != null) {
                    this.folder.setColor(color2);
                    reInitialize();
                }
            }, () -> {
                this.folder.setName(previousName);
                this.folder.setColor(previousColor);
                reInitialize();
            });
            return true;
        }).build()).with(ContextMenuEntry.builder().text(Component.translatable("labymod.ui.button.delete", new Component[0])).icon(Textures.SpriteCommon.TRASH).clickHandler(entry2 -> {
            this.delete.run();
            return true;
        }).build());
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (this.dragging.getAsBoolean()) {
            removeId("hover-effect");
            addId("no-transition");
        } else {
            addId("hover-effect");
            removeId("no-transition");
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.iconWidget = new IconWidget(Textures.SpriteCommon.FOLDER);
        this.iconWidget.addId("icon");
        this.iconWidget.color().set(Integer.valueOf(this.folder.getColor().getValue() | (-16777216)));
        addSelectionWidgets(this.iconWidget);
        addChild(this.iconWidget);
        ComponentWidget title = ComponentWidget.text(this.folder.getName());
        title.addId("title");
        addChild(title);
        HorizontalListWidget list = new HorizontalListWidget();
        list.addId("servers");
        int count = 0;
        for (LabyNetServerInfoCache<StorageServerData> server : this.servers) {
            CompletableResourceLocation icon = server.serverInfo().getIcon();
            IconWidget iconWidget = new IconWidget(Icon.completable(icon));
            iconWidget.addId("server-icon");
            iconWidget.setPressOnRelease(true);
            iconWidget.setPressable(() -> {
                server.serverInfo().connect();
            });
            list.addEntry(iconWidget);
            count++;
            if (count >= 16) {
                break;
            }
        }
        addChild(list);
    }

    public void enter() {
        if (this.movable != ServerInfoWidget.Movable.NO && !KeyHandler.isShiftDown()) {
            this.moveHandler.accept(ServerInfoWidget.Movable.ADD);
        }
    }

    public void addServer(LabyNetServerInfoCache<StorageServerData> server) {
        this.servers.add(server);
    }

    public List<LabyNetServerInfoCache<StorageServerData>> getServers() {
        return this.servers;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget
    public int getListIndex() {
        if (this.servers.isEmpty()) {
            return 0;
        }
        return this.servers.get(0).getSortingValue();
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget
    public IconWidget getIconWidget() {
        return this.iconWidget;
    }

    public ServerFolder getFolder() {
        return this.folder;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (LabyNetServerInfoCache<StorageServerData> server : this.servers) {
            if (!string.isEmpty()) {
                string.append(",");
            }
            string.append(server.serverAddress().toString());
        }
        return "[" + String.valueOf(string) + "]";
    }

    public static void promptRename(ServerFolder currentFolder, FolderRenameCallback callback) {
        promptRename(currentFolder, callback, null, null);
    }

    public static void promptRename(ServerFolder currentFolder, FolderRenameCallback callback, @Nullable FolderUpdateCallback updateCallback, @Nullable Runnable onCancel) {
        FlexibleContentWidget list = new FlexibleContentWidget();
        list.addId("rename-options");
        TextFieldWidget textField = new TextFieldWidget();
        textField.setFocused(true);
        textField.setText(currentFolder == null ? "" : currentFolder.getName());
        textField.updateListener(text -> {
            if (updateCallback != null) {
                updateCallback.update(text, null);
            }
        });
        textField.setCursorAtEnd();
        list.addFlexibleContent(textField);
        Color currentColor = currentFolder == null ? ServerFolder.DEFAULT_COLOR : currentFolder.getColor();
        ColorPickerWidget colorPicker = ColorPickerWidget.of(currentColor);
        colorPicker.addUpdateListener(currentColor, color -> {
            if (updateCallback != null) {
                updateCallback.update(null, color);
            }
        });
        list.addContent(colorPicker);
        StyleSheet styleSheet = new LinkReference("labymod", "lss/activity/multiplayer/server-list.lss").loadStyleSheet();
        PopupWidget widget = PopupWidget.builder().title(Component.translatable("labymod.activity.multiplayer.private.folder.rename.title", new Component[0])).widgetSupplier(() -> {
            return list;
        }).confirmCallback(() -> {
            String name = textField.getText().trim();
            if (name.isEmpty()) {
                return;
            }
            callback.rename(name, colorPicker.value());
        }).cancelCallback(() -> {
            if (onCancel != null) {
                onCancel.run();
            }
        }).build();
        WidgetReference ref = widget.displayInOverlay(Collections.singletonList(styleSheet));
        ref.clickRemoveStrategy(WidgetReference.ClickRemoveStrategy.OUTSIDE);
        ref.keyPressRemoveStrategy(WidgetReference.KeyPressRemoveStrategy.ESCAPE);
    }
}
