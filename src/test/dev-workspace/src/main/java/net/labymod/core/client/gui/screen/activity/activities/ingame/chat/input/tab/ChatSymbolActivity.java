package net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatSymbolRegistry;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabActivity;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.color.format.ColorFormat;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/chat/input/tab/ChatSymbolActivity.class */
@Link("activity/chat/input/chat-symbol.lss")
@AutoActivity
@Singleton
@Referenceable
public class ChatSymbolActivity extends ChatInputTabActivity<DivWidget> {
    private final ListSession<?> session = new ListSession<>();
    private final ChatSymbolRegistry registry = Laby.references().chatSymbolRegistry();
    private final ConfigProperty<List<String>> favorites = this.labyAPI.config().ingame().chatInput().favoriteSymbols();
    private final VerticalListWidget<Widget> list = new VerticalListWidget<>();
    private final TilesGridWidget<Widget> grid;
    private final TilesGridWidget<Widget> favoritesGrid;

    @Inject
    public ChatSymbolActivity() {
        this.list.addId("list");
        this.grid = new TilesGridWidget<>();
        this.grid.addId("grid");
        this.favoritesGrid = new TilesGridWidget<>();
        this.favoritesGrid.addId("grid");
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        DivWidget extraWindow = new DivWidget();
        extraWindow.addId("extra-window");
        HorizontalListWidget wrapper = new HorizontalListWidget();
        wrapper.addId("wrapper");
        int i = 0;
        while (i < 3) {
            VerticalListWidget<Widget> list = new VerticalListWidget<>();
            list.addId("list");
            list.selectable().set(false);
            if (i == 0) {
                Iterator<Character> it = this.registry.getTextDecorations().keySet().iterator();
                while (it.hasNext()) {
                    list.addChild(createFormattedComponentTile(it.next().toString()));
                }
                list.addChild(createFormattedComponentTile("r"));
            } else {
                for (Character symbol : this.registry.getTextColors().keySet()) {
                    if ((i == 1) == Character.isAlphabetic(symbol.charValue())) {
                        list.addChild(createFormattedComponentTile(symbol.toString()));
                    }
                }
            }
            wrapper.addEntry(list);
            i++;
        }
        extraWindow.addChild(wrapper);
        ((Document) this.document).addChild(extraWindow);
        DivWidget window = createSymbolWindow();
        ((Document) this.document).addChild(window);
    }

    @NotNull
    private DivWidget createSymbolWindow() {
        this.contentWidget = new DivWidget();
        ((DivWidget) this.contentWidget).addId("window");
        DivWidget titleBar = new DivWidget();
        titleBar.addId("title-bar");
        ComponentWidget title = ComponentWidget.component(Component.translatable("labymod.chatInput.tab.symbol.name", new Component[0]));
        title.addId("title");
        titleBar.addChild(title);
        ((DivWidget) this.contentWidget).addChild(titleBar);
        DivWidget gridDivWidget = new DivWidget();
        gridDivWidget.addId("grid-div");
        ScrollWidget scrollWidget = new ScrollWidget(this.list, this.session);
        scrollWidget.addId("scroll-widget");
        int favoriteColor = ColorFormat.ARGB32.pack(NamedTextColor.GOLD.getValue(), 255);
        for (String favorite : this.favorites.get()) {
            Widget tile = createComponentTile(favorite, favorite, favoriteColor);
            tile.addId("favorite");
            this.favoritesGrid.addTile(tile);
        }
        this.favoritesGrid.sortChildren();
        this.favoritesGrid.setVisible(!this.favoritesGrid.getChildren().isEmpty());
        this.list.addChild(this.favoritesGrid);
        List<Character> symbols = this.registry.getSymbols();
        for (Character symbol : symbols) {
            String symbolString = symbol.toString();
            int color = this.favorites.get().contains(symbolString) ? Integer.MAX_VALUE : -1;
            this.grid.addTile(createComponentTile(symbolString, symbolString, color));
        }
        this.grid.sortChildren();
        this.list.addChild(this.grid);
        gridDivWidget.addChild(scrollWidget);
        ((DivWidget) this.contentWidget).addChild(gridDivWidget);
        return (DivWidget) this.contentWidget;
    }

    @NotNull
    private Widget createFormattedComponentTile(String symbol) {
        String textToPastInChat = String.format(Locale.ROOT, "%s%s", this.registry.getAmpersand(), symbol);
        String displayText = String.format(Locale.ROOT, "%s%s%<s", this.registry.getParagraph(), symbol);
        return createComponentTile(displayText, textToPastInChat);
    }

    @NotNull
    private Widget createComponentTile(String displayText, String textToPastInChat) {
        return createComponentTile(displayText, textToPastInChat, -1);
    }

    @NotNull
    private Widget createComponentTile(String displayText, String textToPastInChat, int color) {
        TileComponentWidget tile = new TileComponentWidget(displayText, textToPastInChat, color);
        tile.addId("tile");
        tile.setLazy(true);
        tile.updateContextMenu(this);
        tile.setPressable(() -> {
            if (KeyHandler.isShiftDown() || KeyHandler.isAltDown()) {
                boolean isFavoriteSymbol = this.favorites.get().contains(textToPastInChat);
                editFavoriteSymbols(textToPastInChat, !isFavoriteSymbol);
                tile.updateContextMenu(this);
            } else {
                this.labyAPI.minecraft().sounds().playButtonPress();
                this.labyAPI.minecraft().chatExecutor().insertText(textToPastInChat);
            }
        });
        return tile;
    }

    private void editFavoriteSymbols(String symbol, boolean shouldAddAsFavorite) {
        if (shouldAddAsFavorite) {
            addToFavoriteSymbols(symbol);
        } else {
            removeFromFavoriteSymbols(symbol);
        }
    }

    private void addToFavoriteSymbols(String symbol) {
        List<String> favourites = this.favorites.get();
        if (favourites.contains(symbol)) {
            return;
        }
        favourites.add(symbol);
        reload();
    }

    private void removeFromFavoriteSymbols(String symbol) {
        List<String> favourites = this.favorites.get();
        favourites.remove(symbol);
        reload();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/chat/input/tab/ChatSymbolActivity$TileComponentWidget.class */
    @AutoWidget
    public static class TileComponentWidget extends AbstractWidget<Widget> {
        private final String symbol;
        private final String textToPastInChat;
        private final Component component;
        private final int color;

        public TileComponentWidget(String symbol, String textToPastInChat, int color) {
            this.symbol = symbol;
            this.textToPastInChat = textToPastInChat;
            this.component = Component.text(symbol);
            this.color = color;
        }

        public TileComponentWidget(String symbol, String textToPastInChat) {
            this(symbol, textToPastInChat, -1);
        }

        @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
        public void render(ScreenContext context) {
            Bounds bounds = bounds();
            super.render(context);
            ScreenCanvas canvas = context.canvas();
            float x = bounds.getCenterX();
            float y = (bounds.getCenterY() - (canvas.getLineHeight() / 2.0f)) + 0.5f;
            canvas.submitComponent(this.component, x, y, this.color, 3);
        }

        public void updateContextMenu(ChatSymbolActivity activity) {
            setContextMenu(null);
            createContextMenuLazy(menu -> {
                boolean isFavoriteSymbol = activity.favorites.get().contains(this.textToPastInChat);
                menu.with(ContextMenuEntry.builder().text(Component.translatable("labymod.chatInput.tab.symbol.context.copy", new Component[0])).clickHandler(entry -> {
                    this.labyAPI.minecraft().setClipboard(this.textToPastInChat);
                    return true;
                }).build()).with(ContextMenuEntry.builder().text(Component.translatable("labymod.chatInput.tab.symbol.context." + (isFavoriteSymbol ? "removeFromFavorites" : "addToFavorites"), new Component[0])).clickHandler(entry2 -> {
                    activity.editFavoriteSymbols(this.textToPastInChat, !isFavoriteSymbol);
                    updateContextMenu(activity);
                    return true;
                }).build());
            });
        }
    }
}
