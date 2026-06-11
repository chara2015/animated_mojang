package net.labymod.core.client.gui.screen.activity.activities.multiplayer.child;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerEntryWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerInfoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.multiplayer.ServerListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.network.server.storage.MoveActionType;
import net.labymod.api.client.network.server.storage.ServerFolder;
import net.labymod.api.client.network.server.storage.StorageServerData;
import net.labymod.api.client.sound.SoundService;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.LabyNetServerInfoCache;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.MultiplayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.multiplayer.directconnect.DirectConnectActivity;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.AdServerInfoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.LabyNetServerInfoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.ServerFolderWidget;
import net.labymod.core.client.gui.screen.widget.widgets.multiplayer.StorageServerInfoWidget;
import net.labymod.core.client.network.server.storage.DefaultServerList;
import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;
import net.labymod.core.labyconnect.session.advertisement.AdServerData;
import net.labymod.core.labyconnect.session.advertisement.AdvertisementController;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/multiplayer/child/PrivateServerListActivity.class */
@AutoActivity
@Link("activity/multiplayer/private-server-list.lss")
public class PrivateServerListActivity extends LabyNetServerListActivity<StorageServerData, ServerEntryWidget> {
    private final MultiplayerActivity multiplayerActivity;
    private final DefaultServerList serverList;
    private final ButtonWidget editButton;
    private final ButtonWidget keepButton;
    private final ButtonWidget deleteButton;
    private DirectConnectActivity directConnectActivity;
    private ServerFolder currentFolder;

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public PrivateServerListActivity(MultiplayerActivity multiplayerActivity, TextFieldWidget textFieldWidget) {
        super("private", textFieldWidget);
        this.multiplayerActivity = multiplayerActivity;
        this.serverList = (DefaultServerList) this.serverController.serverList();
        if (this.serverList.size() == 0) {
            this.serverList.load();
        }
        getOrRegisterServerData(null);
        List children = this.serverListWidget.getChildren();
        this.serverListWidget.registerServerMover((ServerListWidget.ServerMover<Entry>) (dragging, destination, type) -> {
            SoundType soundType;
            SoundService soundService = Laby.references().soundService();
            int draggingIndex = dragging.getListIndex();
            Widget parent = (Widget) this.serverListWidget.getParent();
            boolean insideFolder = this.currentFolder != null;
            if (insideFolder && !parent.isHovered()) {
                if (this.currentFolder != null) {
                    boolean success = this.serverList.move(draggingIndex, -1, MoveActionType.REMOVE_FROM_FOLDER, this.currentFolder);
                    if (success) {
                        soundService.play(SoundType.SERVER_MOVE, 0.45f);
                        if (this.currentFolder.isEmpty()) {
                            this.currentFolder = null;
                            soundService.play(SoundType.HUD_TRASH, 0.45f);
                        }
                        reload();
                        return;
                    }
                    return;
                }
                return;
            }
            if (destination != null && destination.isMoveable()) {
                int destinationIndex = destination.getListIndex();
                if (type == MoveActionType.ADD_TO_FOLDER && !this.serverList.hasFolder(destinationIndex) && !this.serverList.hasFolder(draggingIndex)) {
                    ServerFolderWidget.promptRename(null, (name, color) -> {
                        ServerFolder target = this.serverList.getOrCreateFolder(destinationIndex);
                        target.setName(name);
                        target.setColor(color);
                        boolean success2 = this.serverList.move(draggingIndex, destinationIndex, type, this.currentFolder);
                        if (success2) {
                            Laby.references().soundService().play(SoundType.HUD_ATTACH, 0.15f);
                            reload();
                        }
                    });
                    return;
                }
                boolean success2 = this.serverList.move(draggingIndex, destinationIndex, type, this.currentFolder);
                if (success2) {
                    if (type == MoveActionType.ADD_TO_FOLDER) {
                        soundType = SoundType.HUD_ATTACH;
                    } else {
                        soundType = SoundType.SERVER_MOVE;
                    }
                    SoundType sound = soundType;
                    soundService.play(sound, 0.45f);
                    reload();
                }
            }
        });
        this.session.setEntrySwapper((a, b) -> {
            ServerEntryWidget widget1 = (ServerEntryWidget) children.get(a);
            ServerEntryWidget widget2 = (ServerEntryWidget) children.get(b);
            if (widget1.isMoveable() && widget2.isMoveable()) {
                this.serverList.swap(widget1.getSortingValue(), widget2.getSortingValue(), this.currentFolder);
                Laby.references().soundService().play(SoundType.SERVER_MOVE, 0.45f);
                reload();
            }
        });
        this.deleteButton = ButtonWidget.i18n("labymod.ui.button.delete", () -> {
            ServerEntryWidget selected = (ServerEntryWidget) this.session.getSelectedEntry();
            if (selected == null) {
                return;
            }
            delete(selected);
        });
        this.editButton = ButtonWidget.i18n("labymod.ui.button.edit", () -> {
            ServerEntryWidget selected = (ServerEntryWidget) this.session.getSelectedEntry();
            if (selected == null || !(selected instanceof ServerInfoWidget)) {
                return;
            }
            ServerInfoWidget<?> serverInfo = (ServerInfoWidget) selected;
            ServerData data = serverInfo.serverData();
            if (!(data instanceof StorageServerData)) {
                return;
            }
            StorageServerData storageServerData = (StorageServerData) data;
            displayScreen(this.serverController.createEditServerScreen(storageServerData, s -> {
                this.serverList.update(s);
                this.serverList.saveAsync();
            }));
        });
        this.keepButton = ButtonWidget.i18n("labymod.activity.multiplayer.private.button.keepServer", () -> {
            ServerEntryWidget selected = (ServerEntryWidget) this.session.getSelectedEntry();
            if (selected == null || !(selected instanceof ServerInfoWidget)) {
                return;
            }
            ServerInfoWidget<?> serverInfo = (ServerInfoWidget) selected;
            ServerData data = serverInfo.serverData();
            if (!(data instanceof AdServerData)) {
                return;
            }
            AdServerData ad = (AdServerData) data;
            keepServer(ad);
        });
        setServerButtonsEnabled(false);
        this.serverListWidget.setSelectCallback((Selectable<T>) widget -> {
            setServerButtonsEnabled(widget instanceof ServerInfoWidget);
        });
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void fillServerList(VerticalListWidget<ServerEntryWidget> serverListWidget, String searchQuery) {
        DefaultLabyConnectSession session;
        AdvertisementController controller;
        ServerFolder currentFolder = searchQuery != null ? null : this.currentFolder;
        int size = this.serverList.size() - 1;
        if (currentFolder == null && (session = (DefaultLabyConnectSession) Laby.references().labyConnect().getSession()) != null && (controller = session.getAdvertisementController()) != null) {
            for (AdServerData server : controller.getServers()) {
                LabyNetServerInfoCache<StorageServerData> adCache = registerCache(server);
                AdServerInfoWidget adWidget = new AdServerInfoWidget(server, adCache, (v1) -> {
                    delete(v1);
                }, () -> {
                }, () -> {
                    keepServer(server);
                });
                serverListWidget.addChild(adWidget, false);
                server.sendEvent(AdvertisementController.Event.SEE);
            }
        }
        getOrRegisterServerData((data, cache) -> {
            ServerEntryWidget entryWidget;
            int endIndex;
            ServerInfoWidget.Movable movable;
            int index = cache.getSortingValue();
            ServerFolder folder = this.serverList.getFolder(index);
            if (currentFolder != null && folder != currentFolder) {
                return;
            }
            if (searchQuery != null) {
                String name = data.getName().trim().toLowerCase(Locale.ROOT);
                String address = data.address().getHost().trim().toLowerCase(Locale.ROOT);
                String folderName = folder != null ? folder.getName().trim().toLowerCase(Locale.ROOT) : null;
                if (!name.contains(searchQuery) && !address.contains(searchQuery) && (folderName == null || !folderName.contains(searchQuery))) {
                    return;
                }
            }
            if (folder == null || folder == currentFolder || searchQuery != null) {
                Consumer consumer = (v1) -> {
                    delete(v1);
                };
                DefaultServerList defaultServerList = this.serverList;
                Objects.requireNonNull(defaultServerList);
                entryWidget = new StorageServerInfoWidget(data, cache, consumer, defaultServerList::saveAsync);
            } else {
                ServerFolderWidget folderWidget = getFolderWidget(folder);
                if (folderWidget == null) {
                    ServerFolderWidget serverFolderWidgetCreateServerFolderWidget = createServerFolderWidget(folder);
                    folderWidget = serverFolderWidgetCreateServerFolderWidget;
                    entryWidget = serverFolderWidgetCreateServerFolderWidget;
                } else {
                    entryWidget = null;
                }
                folderWidget.addServer(cache);
            }
            if (entryWidget != null) {
                if (entryWidget instanceof ServerFolderWidget) {
                    endIndex = index == 0 ? 0 : ((ServerFolderWidget) entryWidget).getFolder().getEndIndex();
                } else {
                    endIndex = index;
                }
                int targetIndex = endIndex;
                int min = currentFolder == null ? 0 : currentFolder.getStartIndex();
                int max = currentFolder == null ? size : currentFolder.getEndIndex();
                if (size == 0) {
                    movable = ServerInfoWidget.Movable.ADD;
                } else if (targetIndex == min) {
                    movable = ServerInfoWidget.Movable.DOWN;
                } else if (targetIndex == max) {
                    movable = ServerInfoWidget.Movable.UP;
                } else {
                    movable = ServerInfoWidget.Movable.ALL;
                }
                ServerEntryWidget serverEntryWidget = entryWidget;
                entryWidget.setMovable(movable, move -> {
                    handleServerMove(serverEntryWidget, move);
                });
                serverListWidget.addChild(entryWidget, false);
            }
        });
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void fillButtonContainer(FlexibleContentWidget container) {
        setServerButtonsEnabled(this.session.getSelectedEntry() != null);
        FlexibleContentWidget buttonRowOne = new FlexibleContentWidget();
        buttonRowOne.addFlexibleContent(this.joinButton);
        buttonRowOne.addFlexibleContent(ButtonWidget.i18n(getTranslationKey("button.directConnection"), () -> {
            if (this.directConnectActivity == null) {
                this.directConnectActivity = new DirectConnectActivity(this.multiplayerActivity);
            }
            displayScreen(this.directConnectActivity);
        }));
        buttonRowOne.addFlexibleContent(ButtonWidget.i18n(getTranslationKey("button.addServer"), () -> {
            ScreenWrapper screen = this.serverController.createNewServerScreen(serverData -> {
                this.serverList.add(serverData);
                reload();
            });
            displayScreen(screen);
        }));
        container.addContent(buttonRowOne);
        FlexibleContentWidget buttonRowTwo = new FlexibleContentWidget();
        buttonRowTwo.addFlexibleContent(this.editButton);
        buttonRowTwo.addFlexibleContent(this.keepButton);
        buttonRowTwo.addFlexibleContent(this.deleteButton);
        buttonRowTwo.addFlexibleContent(ButtonWidget.i18n("labymod.ui.button.refresh", () -> {
            refresh(true);
        }));
        buttonRowTwo.addFlexibleContent(ButtonWidget.i18n("labymod.ui.button.cancel", () -> {
            displayScreen((ScreenInstance) null);
        }));
        container.addContent(buttonRowTwo);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void initializeHeader(FlexibleContentWidget container, String searchQuery) {
        if (this.currentFolder == null || searchQuery != null) {
            return;
        }
        DivWidget header = new DivWidget();
        header.addId("header");
        header.setContextMenu(createServerFolderWidget(this.currentFolder).getContextMenu());
        ButtonWidget backButton = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
        backButton.addId("back-button");
        backButton.setPressable(() -> {
            this.currentFolder = null;
            reload();
        });
        header.addChild(backButton);
        IconWidget backIconWidget = new IconWidget(Textures.SpriteCommon.FOLDER);
        Color color = this.currentFolder.getColor();
        backIconWidget.color().set(Integer.valueOf(ColorFormat.ARGB32.pack(color.getValue(), 255)));
        backIconWidget.addId("icon");
        header.addChild(backIconWidget);
        ComponentWidget title = ComponentWidget.text(this.currentFolder.getName());
        title.addId("title");
        header.addChild(title);
        container.addContent(header);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity
    protected void setServerButtonsEnabled(boolean enabled) {
        super.setServerButtonsEnabled(enabled);
        this.deleteButton.setEnabled(enabled);
        this.editButton.setEnabled(enabled);
        boolean isAdSelected = this.session.getSelectedEntry() instanceof AdServerInfoWidget;
        this.keepButton.setVisible(isAdSelected);
        this.editButton.setVisible(!isAdSelected);
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.screen.ParentScreen
    public void displayScreen(ScreenInstance screen) {
        this.labyAPI.minecraft().minecraftWindow().displayScreen(screen);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.multiplayer.child.ServerListActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        super.render(context);
        applyAnimation();
    }

    private void handleServerMove(ServerEntryWidget widget, ServerInfoWidget.Movable movable) {
        try {
            int listIndex = widget.getSortingValue();
            switch (movable) {
                case SWAP:
                    int targetIndex = -1;
                    Iterator it = this.serverListWidget.getChildren().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            ServerEntryWidget child = (ServerEntryWidget) it.next();
                            if (child.isHovered() && child != widget) {
                                targetIndex = child.getSortingValue();
                            }
                        }
                    }
                    if (this.currentFolder != null) {
                        boolean hoverBackIcon = !this.serverListWidget.isHovered();
                        if (hoverBackIcon) {
                            ServerFolder folder = this.currentFolder;
                            this.serverList.move(listIndex, folder.getStartIndex(), MoveActionType.INSERT_ABOVE, this.currentFolder);
                            if (folder.isEmpty()) {
                                this.currentFolder = null;
                            }
                            reload();
                        }
                    } else if (targetIndex != -1) {
                        int destinationIndex = targetIndex;
                        if (this.serverList.getFolder(destinationIndex) != null) {
                            this.serverList.move(listIndex, destinationIndex, MoveActionType.ADD_TO_FOLDER, this.currentFolder);
                            reload();
                        } else {
                            ServerFolderWidget.promptRename(null, (name, color) -> {
                                ServerFolder target = this.serverList.getOrCreateFolder(destinationIndex);
                                target.setName(name);
                                target.setColor(color);
                                this.serverList.move(listIndex, destinationIndex, MoveActionType.ADD_TO_FOLDER, this.currentFolder);
                                reload();
                            });
                        }
                    }
                    break;
                case UP:
                    this.serverList.swap(listIndex, listIndex - 1, this.currentFolder);
                    Laby.references().soundService().play(SoundType.SERVER_MOVE, 0.45f);
                    reload();
                    break;
                case DOWN:
                    if (widget instanceof ServerFolderWidget) {
                        ServerFolderWidget folderWidget = (ServerFolderWidget) widget;
                        this.serverList.swap(listIndex, listIndex + folderWidget.getFolder().getLength(), this.currentFolder);
                    } else {
                        this.serverList.swap(listIndex, listIndex + 1, this.currentFolder);
                    }
                    Laby.references().soundService().play(SoundType.SERVER_MOVE, 0.45f);
                    reload();
                    break;
                case ADD:
                    if (widget instanceof LabyNetServerInfoWidget) {
                        LabyNetServerInfoWidget<?> server = (LabyNetServerInfoWidget) widget;
                        server.connect();
                    }
                    if (widget instanceof ServerFolderWidget) {
                        ServerFolderWidget folder2 = (ServerFolderWidget) widget;
                        this.currentFolder = folder2.getFolder();
                        Laby.references().soundService().play(SoundType.BUTTON_CLICK, 0.15f);
                        reload();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getOrRegisterServerData(BiConsumer<StorageServerData, LabyNetServerInfoCache<StorageServerData>> consumer) {
        for (int i = 0; i < this.serverList.size(); i++) {
            StorageServerData storageServerData = this.serverList.get(i);
            LabyNetServerInfoCache<StorageServerData> cache = registerCache(storageServerData);
            cache.setSortingValue(i);
            if (consumer != null) {
                consumer.accept(storageServerData, cache);
            }
        }
        sortCache();
    }

    private LabyNetServerInfoCache<StorageServerData> registerCache(StorageServerData storageServerData) {
        LabyNetServerInfoCache labyNetServerInfoCacheRegisterCache = registerCache(storageServerData, server -> {
            ServerInfoWidget<?> serverInfoWidget = getServerInfoWidget(storageServerData);
            if (serverInfoWidget != null) {
                serverInfoWidget.updateServerInfo(server.serverInfo());
            }
        });
        labyNetServerInfoCacheRegisterCache.setProtectIp(this.settings.protectPrivateServerList().get().booleanValue());
        return labyNetServerInfoCacheRegisterCache;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void delete(ServerEntryWidget widget) {
        if (this.session.getSelectedEntry() == widget) {
            this.session.setSelectedEntry(null);
        }
        if (widget instanceof StorageServerInfoWidget) {
            StorageServerInfoWidget storageServerInfoWidget = (StorageServerInfoWidget) widget;
            StorageServerData data = (StorageServerData) storageServerInfoWidget.serverData();
            PopupWidget.builder().title(Component.translatable(getTranslationKey("button.deleteServer.title"), Component.text(data.getName()))).confirmCallback(() -> {
                SoundService soundService = Laby.references().soundService();
                soundService.play(SoundType.HUD_TRASH, 0.45f);
                this.serverList.remove(data);
                if (data instanceof AdServerData) {
                    AdServerData ad = (AdServerData) data;
                    ad.sendEvent(AdvertisementController.Event.DELETE);
                    ad.getController().getServers().remove(ad);
                }
                reload();
            }).build().displayInOverlay();
        }
    }

    private ServerFolderWidget getFolderWidget(ServerFolder folder) {
        for (ServerEntryWidget child : this.serverListWidget.getChildren()) {
            if (child instanceof ServerFolderWidget) {
                ServerFolderWidget folderWidget = (ServerFolderWidget) child;
                if (((ServerFolderWidget) child).getFolder() == folder) {
                    return folderWidget;
                }
            }
        }
        return null;
    }

    @NotNull
    private ServerFolderWidget createServerFolderWidget(ServerFolder folder) {
        return new ServerFolderWidget(folder, () -> {
            this.serverList.removeFolder(folder, false);
            reload();
        }, () -> {
            this.serverList.saveAsync();
            if (this.serverListWidget != null) {
                reload();
            }
        }, () -> {
            return this.serverListWidget.getDraggingWidget() != null;
        });
    }

    private void applyAnimation() {
        ServerEntryWidget draggingWidget = this.serverListWidget.getDraggingWidget();
        if (draggingWidget == null) {
            return;
        }
        boolean insideFolder = this.currentFolder != null;
        if (!insideFolder) {
            return;
        }
        Widget parent = (Widget) this.serverListWidget.getParent();
        draggingWidget.applyAnimation(parent);
    }

    private void keepServer(AdServerData server) {
        server.sendEvent(AdvertisementController.Event.KEEP);
        server.getController().getServers().remove(server);
        this.serverList.add(StorageServerData.of(server.getName(), server.address()));
        reload();
    }
}
