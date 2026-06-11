package net.labymod.core.client.gui.screen.activity.activities.ingame.playerlist;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Textures;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.numbers.StyledFormat;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.client.entity.player.badge.BadgeRegistry;
import net.labymod.api.client.entity.player.badge.PositionType;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.icon.ping.PingIconRegistry;
import net.labymod.api.client.gui.icon.ping.PingType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.AdvancedTabListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.Color;
import net.labymod.api.util.HealthStatus;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.world.rplace.RPlaceMapRenderer;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.core.localization.keys.CommandTranslationKeys;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/playerlist/PlayerListRenderer.class */
public class PlayerListRenderer {
    private static final int MAX_PLAYERS = 80;
    private static final int DEFAULT_PLAYERS_PER_COLUMN = 20;
    private static final int ROW_ENTRY_HEIGHT = 8;
    private static final int PADDING = 1;
    private final PlayerListOverlay playerList;
    private final int badPing;
    private final int okayPing;
    private final int greatPing;
    private int rowHeight;
    private int columnWidth;
    private int rows;
    private int columns;
    private int maxScoreWidth;
    private int maxPlayerNameWidth;
    private Color backgroundColor;
    private Color foregroundColor;
    private boolean showHeads;
    private boolean showServerBanner;
    private boolean showPing;
    private boolean exactPing;
    private boolean exactPingColored;
    private boolean showPercentage;
    private int maxPlayers;
    private int playersPerColumn;
    private String serverBannerUrl;
    private String serverBannerHash;
    private Icon serverBanner;
    private PlayerListUser[] users = new PlayerListUser[MAX_PLAYERS];
    private final PingIconRegistry pingIconRegistry = Laby.references().pingIconRegistry();
    private final BadgeRegistry badgeRegistry = Laby.references().badgeRegistry();
    private final RenderPipeline renderPipeline = Laby.references().renderPipeline();
    private final ConfigProvider<LabyConfig> labyConfigProvider = LabyConfigProvider.INSTANCE;
    private final Int2ObjectOpenHashMap<CachedComponent> exactPingComponents = new Int2ObjectOpenHashMap<>();
    private final RPlaceMapRenderer rPlaceMapRenderer = new RPlaceMapRenderer();

    protected PlayerListRenderer(PlayerListOverlay playerList) {
        this.playerList = playerList;
        initializeSettings();
        updateTheme();
        this.greatPing = NamedTextColor.GREEN.getValue();
        this.okayPing = NamedTextColor.RED.getValue();
        this.badPing = NamedTextColor.DARK_RED.getValue();
    }

    private void initializeSettings() {
        int iIntValue;
        int iIntValue2;
        TabListConfig tabListConfig = Laby.labyAPI().config().multiplayer().tabList();
        PingConfig pingConfig = tabListConfig.ping();
        tabListConfig.backgroundColor().addChangeListener(value -> {
            this.backgroundColor = value;
        });
        tabListConfig.foregroundColor().addChangeListener(value2 -> {
            this.foregroundColor = value2;
        });
        tabListConfig.labyModPercentage().addChangeListener(value3 -> {
            this.showPercentage = value3.booleanValue();
        });
        tabListConfig.serverBanner().addChangeListener(value4 -> {
            this.showServerBanner = value4.booleanValue();
            if (value4.booleanValue()) {
                updateServerBanner(this.serverBannerUrl, this.serverBannerHash);
            }
        });
        tabListConfig.showHeads().addChangeListener(value5 -> {
            this.showHeads = value5.booleanValue();
            refreshColumnWidth();
        });
        pingConfig.enabled().addChangeListener(value6 -> {
            this.showPing = value6.booleanValue();
            refreshColumnWidth();
        });
        pingConfig.exact().addChangeListener(value7 -> {
            this.exactPing = value7.booleanValue();
            if (!value7.booleanValue()) {
                this.exactPingComponents.clear();
            }
        });
        pingConfig.exactColored().addChangeListener(value8 -> {
            this.exactPingColored = value8.booleanValue();
        });
        AdvancedTabListConfig advancedTabListConfig = tabListConfig.advancedTabList();
        advancedTabListConfig.enabled().addChangeListener(value9 -> {
            this.maxPlayers = value9.booleanValue() ? advancedTabListConfig.maxPlayers().get().intValue() : MAX_PLAYERS;
            this.playersPerColumn = value9.booleanValue() ? advancedTabListConfig.playersPerColumn().get().intValue() : 20;
            refreshColumnWidth();
        });
        advancedTabListConfig.maxPlayers().addChangeListener(value10 -> {
            this.maxPlayers = value10.intValue();
            refreshColumnWidth();
        });
        advancedTabListConfig.playersPerColumn().addChangeListener(value11 -> {
            this.playersPerColumn = value11.intValue();
            refreshColumnWidth();
        });
        this.backgroundColor = tabListConfig.backgroundColor().get();
        this.foregroundColor = tabListConfig.foregroundColor().get();
        this.showPercentage = tabListConfig.labyModPercentage().get().booleanValue();
        this.showServerBanner = tabListConfig.serverBanner().get().booleanValue();
        this.showHeads = tabListConfig.showHeads().get().booleanValue();
        this.showPing = pingConfig.enabled().get().booleanValue();
        this.exactPing = pingConfig.exact().get().booleanValue();
        this.exactPingColored = pingConfig.exactColored().get().booleanValue();
        if (advancedTabListConfig.enabled().get().booleanValue()) {
            iIntValue = advancedTabListConfig.maxPlayers().get().intValue();
        } else {
            iIntValue = MAX_PLAYERS;
        }
        this.maxPlayers = iIntValue;
        if (advancedTabListConfig.enabled().get().booleanValue()) {
            iIntValue2 = advancedTabListConfig.playersPerColumn().get().intValue();
        } else {
            iIntValue2 = 20;
        }
        this.playersPerColumn = iIntValue2;
    }

    public void updateServerBanner(String url, String hash) {
        if (url == null || hash == null) {
            this.serverBannerUrl = null;
            this.serverBannerHash = null;
            this.serverBanner = null;
        } else {
            this.serverBannerUrl = url;
            this.serverBannerHash = hash;
            if (!this.showServerBanner) {
                return;
            }
            CompletableResourceLocation completableResourceLocation = Laby.references().textureRepository().loadCacheResourceAsync("labymod", hash, url, Textures.EMPTY);
            completableResourceLocation.addCompletableListener(() -> {
                if (completableResourceLocation.hasResult()) {
                    this.serverBanner = Icon.texture(completableResourceLocation.getCompleted());
                }
            });
        }
    }

    public void tick() {
        if (this.exactPing) {
            ObjectIterator it = this.exactPingComponents.values().iterator();
            while (it.hasNext()) {
                CachedComponent pingComponent = (CachedComponent) it.next();
                pingComponent.tick();
            }
        }
        if (this.playerList.isDoubleTapped() && this.rPlaceMapRenderer.isFeatureAvailable()) {
            this.rPlaceMapRenderer.update();
        }
    }

    public void render(ScreenContext context, LabyAPI labyAPI, Bounds bounds, boolean update) {
        if (this.playerList.isDoubleTapped() && this.rPlaceMapRenderer.isFeatureAvailable()) {
            renderRPlaceMap(context, bounds);
            return;
        }
        if (update) {
            refresh();
        }
        for (PositionType value : PositionType.VALUES) {
            this.badgeRegistry.beginRender(context, value);
        }
        refreshMaxWidth();
        int screenWidth = (int) bounds.getWidth(BoundsType.INNER);
        float columnsWidth = (this.columnWidth * this.rows) + ((this.rows - 1) * 5);
        float backgroundWidth = columnsWidth;
        RenderableComponent headerRenderableComponent = this.playerList.header.renderableComponent();
        if (headerRenderableComponent != null) {
            backgroundWidth = Math.max(backgroundWidth, headerRenderableComponent.getWidth());
        }
        RenderableComponent footerRenderableComponent = this.playerList.footer.renderableComponent();
        if (footerRenderableComponent != null) {
            backgroundWidth = Math.max(backgroundWidth, footerRenderableComponent.getWidth());
        }
        int x = (int) ((screenWidth / 2.0f) - (backgroundWidth / 2.0f));
        int y = 9;
        if (this.showServerBanner && this.serverBanner != null) {
            context.canvas().submitIcon(this.serverBanner, (screenWidth / 2.0f) - 100.0f, 9, 200.0f, 40.0f);
            y = 9 + 42;
        }
        renderBackground(context, columnsWidth, screenWidth, backgroundWidth, x, y, headerRenderableComponent, footerRenderableComponent);
        renderUsers(labyAPI, context, x, y);
        for (PositionType value2 : PositionType.VALUES) {
            this.badgeRegistry.endRender(context, value2);
        }
        if (this.showPercentage) {
            renderLabyModStats(context, backgroundWidth, x, y);
        }
    }

    private void renderLabyModStats(ScreenContext context, float backgroundWidth, int x, int y) {
        int onlineCount = this.playerList.users.size();
        int userCount = 0;
        for (PlayerListUser playerInfo : this.playerList.users) {
            if (playerInfo.gameUser().isUsingLabyMod()) {
                userCount++;
            }
        }
        Component component = Component.text(Integer.valueOf(userCount), NamedTextColor.GRAY).append(Component.text("/", NamedTextColor.DARK_GRAY)).append(Component.text(Integer.valueOf(onlineCount), NamedTextColor.GRAY)).append(Component.space()).append(Component.text(MathHelper.ceil((100.0f / onlineCount) * userCount) + "%", NamedTextColor.GREEN));
        float fontSize = FontSize.PredefinedFontSize.SMALL.fontSize().getSize();
        ScreenCanvas canvas = context.canvas();
        canvas.submitComponent(component, ((x + backgroundWidth) + 2.0f) - (canvas.getTextWidth(component) * fontSize), y - (canvas.getLineHeight() * fontSize), -1, fontSize, 1);
    }

    private void renderRPlaceMap(ScreenContext context, Bounds bounds) {
        float maxHeight = (bounds.getBottom() - 64.0f) - 1.0f;
        float aspectRatio = this.rPlaceMapRenderer.getAspectRatio();
        float mapHeight = Math.min(maxHeight, bounds.getWidth(BoundsType.INNER) / aspectRatio);
        float mapWidth = mapHeight * aspectRatio;
        this.rPlaceMapRenderer.render(context, bounds.getCenterX() - (mapWidth / 2.0f), 1.0f, mapWidth, mapHeight);
    }

    private void renderBackground(ScreenContext context, float columnsWidth, int screenWidth, float backgroundWidth, int x, int y, RenderableComponent header, RenderableComponent footer) {
        PlayerListUser user;
        ScreenCanvas renderState = context.canvas();
        int maxX = x + ((int) backgroundWidth) + 2;
        int backgroundColor = this.backgroundColor.get();
        int foregroundColor = this.foregroundColor.get();
        int headerY = 0;
        if (!isComponentEmpty(header)) {
            headerY = y + 1;
            int headerHeight = 1 + ((int) header.getHeight()) + 1;
            renderState.submitAbsoluteRect(x, y, maxX, y + headerHeight, backgroundColor);
            y += headerHeight;
        }
        int entriesPerColumn = Math.max(this.columns, 1);
        renderState.submitAbsoluteRect(x, y, maxX, y + 1 + (entriesPerColumn * 9), backgroundColor);
        int playerX = (int) ((screenWidth / 2.0f) - (columnsWidth / 2.0f));
        for (int i = 0; i < this.users.length && (user = this.users[i]) != null; i++) {
            int indexX = i / entriesPerColumn;
            int indexY = i % entriesPerColumn;
            int entryX = playerX + (indexX * this.columnWidth) + (indexX * 5) + 1;
            int entryY = y + 1 + (indexY * 9);
            user.x = entryX;
            user.y = entryY;
            renderState.submitAbsoluteRect(entryX, entryY, entryX + this.columnWidth, entryY + 8, foregroundColor);
        }
        int footerY = y + (entriesPerColumn * 9) + 1;
        if (!isComponentEmpty(footer)) {
            int footerHeight = 1 + ((int) footer.getHeight()) + 1;
            renderState.submitAbsoluteRect(x, footerY, maxX, footerY + footerHeight, backgroundColor);
        }
        if (header == null && footer == null) {
            return;
        }
        if (header != null) {
            renderState.submitRenderableComponent(header, x + (backgroundWidth / 2.0f) + 1.0f, headerY + 1, -1, 3);
        }
        if (footer != null) {
            renderState.submitRenderableComponent(footer, x + (backgroundWidth / 2.0f) + 1.0f, footerY + 1, -1, 3);
        }
        if (this.rPlaceMapRenderer.isFeatureAvailable()) {
            renderState.submitRenderableComponent(RenderableComponent.of(CommandTranslationKeys.getCommandRplaceoverlayTabHint().colorIfAbsent(NamedTextColor.YELLOW)), x + (backgroundWidth / 2.0f) + 1.0f, footerY + 1 + footer.getHeight() + 2.0f, -1, 3);
        }
    }

    public void refresh() {
        int size = this.playerList.users.size();
        int maxPlayers = this.maxPlayers;
        if (this.users.length != maxPlayers) {
            this.users = (PlayerListUser[]) Arrays.copyOf(this.users, maxPlayers);
        }
        for (int i = 0; i < size && i < maxPlayers; i++) {
            PlayerListUser user = this.playerList.users.get(i);
            this.users[i] = user;
        }
        if (size < maxPlayers) {
            for (int i2 = size; i2 < maxPlayers; i2++) {
                this.users[i2] = null;
            }
        }
        refreshColumns();
    }

    public void updateTheme() {
        this.rowHeight = MathHelper.ceil(this.renderPipeline.componentRenderer().height());
        refreshColumnWidth();
        ObjectIterator it = this.exactPingComponents.values().iterator();
        while (it.hasNext()) {
            CachedComponent value = (CachedComponent) it.next();
            value.refresh();
        }
    }

    public void refreshColumns() {
        int playerAmount = Math.min(this.playerList.users.size(), this.maxPlayers);
        int columns = playerAmount;
        int rows = 1;
        while (columns > this.playersPerColumn) {
            rows++;
            columns = ((playerAmount + rows) - 1) / rows;
        }
        this.rows = rows;
        this.columns = columns;
        refreshColumnWidth();
    }

    private void renderUsers(LabyAPI labyAPI, ScreenContext context, int x, int y) {
        PlayerListUser user;
        boolean showHat;
        ScreenCanvas renderState = context.canvas();
        boolean isOnlineMode = this.showHeads && !labyAPI.serverController().isOfflineMode();
        Scoreboard scoreboard = labyAPI.minecraft().getScoreboard();
        ScoreboardObjective scoreObjective = scoreboard == null ? null : scoreboard.getObjective(DisplaySlot.PLAYER_LIST);
        PlayerListUser[] playerListUserArr = this.users;
        int length = playerListUserArr.length;
        for (int i = 0; i < length && (user = playerListUserArr[i]) != null; i++) {
            int entryX = user.x;
            int entryY = user.y;
            int columnWidth = (user.x + this.columnWidth) - 1;
            NetworkPlayerInfo networkPlayerInfo = user.playerInfo();
            if (isOnlineMode) {
                Player player = labyAPI.minecraft().clientWorld().getPlayer(user.getUniqueId()).orElse(null);
                if (MinecraftVersions.V24w44a.orNewer()) {
                    showHat = user.playerInfo().showHat();
                } else {
                    showHat = player != null && player.isShownModelPart(PlayerClothes.HAT);
                }
                renderState.submitPlayerFace(networkPlayerInfo.profile(), entryX, entryY, 8.0f, 8.0f, -1, showHat);
                entryX += 9;
            }
            boolean isSpectator = networkPlayerInfo.gameMode() == GameMode.SPECTATOR;
            int badgeLeftToNameWidth = this.badgeRegistry.render(context, PositionType.LEFT_TO_NAME, entryX, entryY, networkPlayerInfo);
            if (badgeLeftToNameWidth > 0) {
                entryX += badgeLeftToNameWidth + 1;
            }
            RenderableComponent component = user.renderableComponent();
            if (component != null) {
                renderState.submitRenderableComponent(component, entryX, entryY, isSpectator ? -1862270977 : -1, 1);
                this.badgeRegistry.render(context, PositionType.RIGHT_TO_NAME, entryX + component.getWidth() + 1.0f, entryY, networkPlayerInfo);
                this.badgeRegistry.render(context, PositionType.OVERWRITE_PING, columnWidth - 10, user.y, networkPlayerInfo);
                if (scoreObjective != null && !isSpectator) {
                    int scoreRight = (user.x + this.columnWidth) - 8;
                    int scoreLeft = scoreRight - this.maxScoreWidth;
                    if (scoreRight - scoreLeft > 5) {
                        renderScoreboardObjective(context, scoreObjective, networkPlayerInfo, scoreLeft, entryY);
                    }
                }
                renderPing(context, user);
            }
        }
    }

    private void renderPing(ScreenContext context, PlayerListUser user) {
        if (!this.showPing) {
            return;
        }
        NetworkPlayerInfo player = user.playerInfo();
        int badgeWidth = this.badgeRegistry.getWidth(PositionType.OVERWRITE_PING, player);
        if (badgeWidth == 0) {
            ScreenCanvas renderState = context.canvas();
            int entryX = (user.x + this.columnWidth) - 1;
            if (this.exactPing) {
                int ping = player.getCurrentPing();
                int simplifiedPing = ping <= 0 ? 0 : ping;
                CachedComponent cachedPingComponent = (CachedComponent) this.exactPingComponents.get(simplifiedPing);
                if (cachedPingComponent == null) {
                    cachedPingComponent = new CachedComponent(Component.text(ping == 0 ? "?" : ping));
                    this.exactPingComponents.put(simplifiedPing, cachedPingComponent);
                }
                RenderableComponent pingComponent = cachedPingComponent.renderableComponent();
                renderState.submitRenderableComponent(pingComponent, (entryX - (pingComponent.getWidth() * 0.5f)) - 1.0f, user.y + (this.rowHeight / 4.0f), ColorFormat.ARGB32.pack(getPingColor(ping), 255), 0.5f, 1);
                return;
            }
            this.pingIconRegistry.render(context, PingType.PLAYER_PING, player.getCurrentPing(), entryX - 10, user.y);
        }
    }

    private int getPingColor(int ping) {
        if (this.exactPingColored) {
            if (ping < 150) {
                return this.greatPing;
            }
            if (ping < 300) {
                return this.okayPing;
            }
            return this.badPing;
        }
        return this.greatPing;
    }

    private void renderScoreboardObjective(ScreenContext context, ScoreboardObjective scoreObjective, NetworkPlayerInfo networkPlayerInfo, int x, int y) {
        if (scoreObjective.getRenderType() == ObjectiveRenderType.HEARTS) {
            this.renderPipeline.resourceRenderer().heartRenderer().submitHealthBar(context, x, y, 9, HealthStatus.immutable(networkPlayerInfo.getHealth(), 20.0f));
            return;
        }
        ScoreboardScore score = scoreObjective.getScore(networkPlayerInfo.profile().getUsername());
        Component points = ScoreboardScore.formatValue(score, StyledFormat.PLAYER_LIST_DEFAULT);
        RenderableComponent renderableComponent = RenderableComponent.of(points);
        context.canvas().submitRenderableComponent(renderableComponent, x, y, NamedTextColor.YELLOW.getValue() | (-16777216), 1);
    }

    private boolean refreshMaxWidth() {
        List<PlayerListUser> users = this.playerList.users;
        int maxPlayerNameWidth = 0;
        for (PlayerListUser user : users) {
            int playerLength = (int) user.renderableComponent().getWidth();
            for (PositionType value : PositionType.VALUES) {
                if (value.isExpanding()) {
                    int width = this.badgeRegistry.getWidth(value, user.playerInfo());
                    if (width > 0) {
                        width++;
                    }
                    playerLength += width;
                }
            }
            maxPlayerNameWidth = Math.max(maxPlayerNameWidth, playerLength);
        }
        int prevMaxPlayerNameWidth = this.maxPlayerNameWidth;
        this.maxPlayerNameWidth = maxPlayerNameWidth;
        if (prevMaxPlayerNameWidth != maxPlayerNameWidth) {
            refreshColumnWidth();
            return true;
        }
        return false;
    }

    private void refreshColumnWidth() {
        int maxScoreWidth;
        int iMin;
        List<PlayerListUser> users = this.playerList.users;
        if (users.isEmpty()) {
            return;
        }
        LabyAPI labyAPI = Laby.labyAPI();
        Minecraft minecraft = labyAPI.minecraft();
        Scoreboard scoreboard = minecraft.getScoreboard();
        ScoreboardObjective scoreObjective = scoreboard == null ? null : scoreboard.objective(DisplaySlot.PLAYER_LIST);
        int maxScoreValueWidth = 0;
        for (PlayerListUser user : users) {
            if (scoreObjective != null && scoreObjective.getRenderType() != ObjectiveRenderType.HEARTS) {
                ScoreboardScore score = scoreObjective.getScore(user.getUserName());
                Component points = ScoreboardScore.formatValue(score, StyledFormat.PLAYER_LIST_DEFAULT);
                int scoreValueWidth = (int) this.renderPipeline.componentRenderer().width(points);
                maxScoreValueWidth = Math.max(maxScoreValueWidth, scoreValueWidth);
            }
        }
        if (scoreObjective != null) {
            if (scoreObjective.getRenderType() == ObjectiveRenderType.HEARTS) {
                maxScoreWidth = 90;
            } else {
                maxScoreWidth = maxScoreValueWidth;
            }
        } else {
            maxScoreWidth = 0;
        }
        boolean isOnlineMode = this.showHeads && !labyAPI.serverController().isOfflineMode();
        int screenWidth = minecraft.minecraftWindow().getScaledWidth();
        int headOffset = isOnlineMode ? 9 : 0;
        int pingOffset = this.showPing ? 13 : 0;
        this.maxScoreWidth = maxScoreWidth;
        if (this.rows == 0) {
            iMin = 0;
        } else {
            iMin = Math.min(this.rows * (((headOffset + this.maxPlayerNameWidth) + maxScoreWidth) + pingOffset), screenWidth - 50) / this.rows;
        }
        this.columnWidth = iMin;
    }

    private boolean isComponentEmpty(RenderableComponent component) {
        return component == null || component == CachedComponent.EMPTY_COMPONENT;
    }
}
