package net.labymod.api.client;

import java.util.function.Consumer;
import java.util.function.Predicate;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.chat.Title;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityRenderDispatcher;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.TabList;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.world.WorldRenderer;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.profiler.SampleLogger;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/Minecraft.class */
public interface Minecraft {
    int getFPS();

    SessionAccessor sessionAccessor();

    Window minecraftWindow();

    RenderTarget mainTarget();

    @Nullable
    ClientPlayer getClientPlayer();

    Entity getTargetEntity();

    @Nullable
    Entity getCameraEntity();

    @Nullable
    MinecraftCamera getCamera();

    void executeOnRenderThread(Runnable runnable);

    void executeNextTick(Runnable runnable);

    boolean isOnRenderThread();

    void setSessionRaw(Object obj);

    ScreenWrapper wrapScreen(ScreenInstance screenInstance);

    MinecraftTextures textures();

    MinecraftSounds sounds();

    MutableMouse mouse();

    Mouse absoluteMouse();

    boolean isSingleplayer();

    boolean isIngame();

    String getTranslation(String str);

    boolean hasTranslation(String str);

    float getTickDelta();

    float getPartialTicks();

    boolean isPaused();

    MinecraftOptions options();

    void updateKeyRepeatingMode(boolean z);

    ComponentMapper componentMapper();

    ChatExecutor chatExecutor();

    String getVersion();

    String getVersionId();

    String getVersionType();

    int getProtocolVersion();

    void requestChunkUpdate();

    int getDataVersion();

    boolean isDemo();

    @Nullable
    ClientPacketListener getClientPacketListener();

    ClientWorld clientWorld();

    @Nullable
    Scoreboard getScoreboard();

    MinecraftAuthenticator authenticator();

    @Nullable
    TabList getTabList();

    void updateWindowTitle();

    void openChat(String str);

    SampleLogger frameTimeLogger();

    boolean isMouseLocked();

    MouseHandlerAccessor mouseHandler();

    String getClipboard();

    void setClipboard(String str);

    boolean isDestroying();

    float getDestroyProgress();

    void reloadResources();

    WorldRenderer worldRenderer();

    ItemStackRenderer itemStackRenderer();

    EntityRenderDispatcher entityRenderDispatcher();

    long getRunningMillis();

    boolean isLastItemUsed();

    boolean isLastBlockUsed();

    void crashGame(GameCrashReport gameCrashReport);

    void shutdownGame();

    GameMode gameMode();

    HitResult getHitResult();

    void updateBlockBreak(boolean z);

    @Deprecated
    default ClientPlayer clientPlayer() {
        return getClientPlayer();
    }

    @Deprecated
    default Entity cameraEntity() {
        return getCameraEntity();
    }

    @Deprecated
    default ScreenWrapper toLabyScreenRenderer(LabyScreen screen) {
        return wrapScreen(screen);
    }

    default void updateMouse(double x, double y, Consumer<MutableMouse> consumer) {
        MutableMouse mouse = mouse();
        mouse.set(x, y, () -> {
            consumer.accept(mouse);
        });
    }

    default boolean updateMouse(double x, double y, Predicate<MutableMouse> predicate) {
        MutableMouse mouse = mouse();
        return mouse.set(x, y, () -> {
            return predicate.test(mouse);
        });
    }

    default String getTranslationOrDefault(String translationKey, String def) {
        if (def == null) {
            return getTranslation(translationKey);
        }
        return hasTranslation(translationKey) ? getTranslation(translationKey) : def;
    }

    default boolean isKeyPressed(Key key) {
        return KeyMapper.isPressed(key);
    }

    default boolean isMouseDown(Key button) {
        return KeyMapper.isPressed(button);
    }

    default boolean isLoadingOverlayPresent() {
        return false;
    }

    default void crashGame(String message, Throwable throwable) {
        crashGame(GameCrashReport.forThrowable(message, throwable));
    }

    default void refreshRealmsClient() {
    }

    @Deprecated
    default Entity targetEntity() {
        return getTargetEntity();
    }

    @Deprecated
    @Nullable
    default ClientPacketListener clientPacketListener() {
        return getClientPacketListener();
    }

    @Nullable
    default Title getTitle() {
        return chatExecutor().getTitle();
    }

    default void showTitle(@NotNull Title title) {
        chatExecutor().showTitle(title);
    }

    default void clearTitle() {
        chatExecutor().clearTitle();
    }

    default void confirmFriendsListEnabled(Runnable onEnabled) {
    }
}
