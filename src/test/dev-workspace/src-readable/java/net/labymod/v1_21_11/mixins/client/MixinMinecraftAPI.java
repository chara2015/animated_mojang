package net.labymod.v1_21_11.mixins.client;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.gui.RealmsDataFetcher;
import java.io.File;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.window.Window;
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
import net.labymod.api.profiler.SampleLogger;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.input.MouseBridge;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_21_11.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_21_11.client.gui.window.VersionedWindow;
import net.labymod.v1_21_11.client.resources.texture.VersionedMinecraftTextures;
import net.labymod.v1_21_11.laby3d.pipeline.target.VersionedRenderTarget;
import net.minecraft.CrashReport;
import net.minecraft.SharedConstants;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;
import net.minecraft.client.User;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.locale.Language;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.util.Util;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinMinecraftAPI.class */
@Mixin({Minecraft.class})
@Implements({@Interface(iface = net.labymod.api.client.Minecraft.class, prefix = "minecraft$", remap = Interface.Remap.NONE)})
public abstract class MixinMinecraftAPI implements net.labymod.api.client.Minecraft {

    @Unique
    private final SampleLogger labyMod$frameTimeLogger = new SampleLogger();

    @Shadow
    private static int fps;

    @Mutable
    @Shadow
    @Final
    private User user;

    @Shadow
    @Final
    public MouseHandler mouseHandler;

    @Shadow
    public LocalPlayer player;

    @Shadow
    private boolean pause;

    @Shadow
    public ClientLevel level;

    @Shadow
    @Final
    public Options options;

    @Shadow
    public Screen screen;

    @Shadow
    @Final
    public Gui gui;

    @Shadow
    public KeyboardHandler keyboardHandler;

    @Shadow
    @Final
    private ReloadableResourceManager resourceManager;

    @Shadow
    @Final
    private RenderTarget mainRenderTarget;
    private ComponentMapper labyMod$componentMapper;

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Nullable
    public MultiPlayerGameMode gameMode;

    @Shadow
    private ItemRenderer itemRenderer;

    @Shadow
    private EntityRenderDispatcher entityRenderDispatcher;
    private ChatExecutor labyMod$chatExecutor;
    private MinecraftAuthenticator labyMod$authenticator;
    private Window labyMod$minecraftWindow;
    private SessionAccessor labyMod$sessionAccessor;
    private MinecraftTextures labyMod$minecraftTextures;
    private MinecraftSounds labyMod$minecraftSounds;
    private ClientWorld labyMod$clientWorld;
    private MouseBridge labyMod$mouseBridge;
    private net.labymod.laby3d.api.pipeline.target.RenderTarget labyMod$wrappedMainTarget;
    private boolean labymod$isLastItemUsed;
    private boolean labymod$isLastBlockUsed;

    @Shadow
    @Final
    public LevelRenderer levelRenderer;

    @Shadow
    @Final
    public GameRenderer gameRenderer;

    @Mutable
    @Shadow
    @Final
    private RealmsDataFetcher realmsDataFetcher;

    @Shadow
    protected abstract void shadow$continueAttack(boolean z);

    @Shadow
    public abstract ClientPacketListener getConnection();

    @Shadow
    public abstract Overlay getOverlay();

    @Shadow
    public abstract CompletableFuture<Void> reloadResourcePacks();

    @Shadow
    public abstract void crash(@org.jetbrains.annotations.Nullable Screen screen);

    @Shadow
    public abstract void destroy();

    @Shadow
    public abstract String shadow$getVersionType();

    @Shadow
    public abstract boolean shadow$isDemo();

    @Shadow
    public abstract DeltaTracker getDeltaTracker();

    @Shadow
    public static Minecraft getInstance() {
        return null;
    }

    public int getFPS() {
        return fps;
    }

    @Shadow
    private static void crash(Minecraft mc, File directory, CrashReport par1) {
    }

    public Window minecraftWindow() {
        if (this.labyMod$minecraftWindow == null) {
            this.labyMod$minecraftWindow = new VersionedWindow();
        }
        return this.labyMod$minecraftWindow;
    }

    public ClientPlayer getClientPlayer() {
        return this.player;
    }

    public Entity getTargetEntity() {
        return Minecraft.getInstance().crosshairPickEntity;
    }

    public Entity getCameraEntity() {
        return Minecraft.getInstance().getCameraEntity();
    }

    public MinecraftCamera getCamera() {
        if (Minecraft.getInstance().getCameraEntity() != null) {
            return this.gameRenderer.getMainCamera();
        }
        return null;
    }

    public SessionAccessor sessionAccessor() {
        if (this.labyMod$sessionAccessor == null) {
            this.labyMod$sessionAccessor = Laby.references().sessionAccessor();
        }
        return this.labyMod$sessionAccessor;
    }

    public void executeOnRenderThread(Runnable runnable) {
        getInstance().execute(runnable);
    }

    public void executeNextTick(Runnable runnable) {
        getInstance().schedule(runnable);
    }

    public boolean isOnRenderThread() {
        return getInstance().isSameThread();
    }

    public void setSessionRaw(Object gameSession) {
        this.user = (User) gameSession;
    }

    public ScreenWrapper wrapScreen(ScreenInstance screen) {
        ScreenInstance labyScreenRenderer;
        if (screen instanceof ScreenWrapper) {
            return (ScreenWrapper) screen;
        }
        ScreenWrapper.Factory factoryScreenWrapperFactory = Laby.references().screenWrapperFactory();
        if (screen instanceof LabyScreen) {
            labyScreenRenderer = new LabyScreenRenderer((LabyScreen) screen);
        } else {
            labyScreenRenderer = screen;
        }
        return factoryScreenWrapperFactory.create(labyScreenRenderer);
    }

    public MinecraftTextures textures() {
        if (this.labyMod$minecraftTextures == null) {
            this.labyMod$minecraftTextures = new VersionedMinecraftTextures();
        }
        return this.labyMod$minecraftTextures;
    }

    public MinecraftSounds sounds() {
        if (this.labyMod$minecraftSounds == null) {
            this.labyMod$minecraftSounds = Laby.references().minecraftSounds();
        }
        return this.labyMod$minecraftSounds;
    }

    private MouseBridge labyMod$mouseBridge() {
        if (this.labyMod$mouseBridge == null) {
            this.labyMod$mouseBridge = LabyMod.references().mouseBridge();
        }
        return this.labyMod$mouseBridge;
    }

    public MutableMouse mouse() {
        return labyMod$mouseBridge().mouse();
    }

    public Mouse absoluteMouse() {
        return labyMod$mouseBridge().absoluteMouse();
    }

    public boolean isSingleplayer() {
        return Minecraft.getInstance().hasSingleplayerServer();
    }

    public boolean isIngame() {
        return Minecraft.getInstance().level != null;
    }

    public void updateKeyRepeatingMode(boolean enabled) {
    }

    public String getTranslation(String translationKey) {
        return Language.getInstance().getOrDefault(translationKey);
    }

    public boolean hasTranslation(String translationKey) {
        return Language.getInstance().has(translationKey);
    }

    public float getPartialTicks() {
        return getDeltaTracker().getGameTimeDeltaPartialTick(false);
    }

    public float getTickDelta() {
        return getDeltaTracker().getGameTimeDeltaTicks();
    }

    public boolean isPaused() {
        return this.pause;
    }

    public MinecraftOptions options() {
        return this.options;
    }

    public ComponentMapper componentMapper() {
        if (this.labyMod$componentMapper == null) {
            this.labyMod$componentMapper = Laby.references().componentMapper();
        }
        return this.labyMod$componentMapper;
    }

    public ChatExecutor chatExecutor() {
        if (this.labyMod$chatExecutor == null) {
            this.labyMod$chatExecutor = Laby.references().chatExecutor();
        }
        return this.labyMod$chatExecutor;
    }

    public void requestChunkUpdate() {
        Minecraft.getInstance().levelRenderer.needsUpdate();
    }

    public String getVersion() {
        return SharedConstants.getCurrentVersion().name();
    }

    public String getVersionId() {
        return SharedConstants.getCurrentVersion().id();
    }

    public int getProtocolVersion() {
        return SharedConstants.getProtocolVersion();
    }

    public int getDataVersion() {
        return SharedConstants.getCurrentVersion().dataVersion().version();
    }

    @Intrinsic
    public String minecraft$getVersionType() {
        return shadow$getVersionType();
    }

    @Intrinsic
    public boolean minecraft$isDemo() {
        return shadow$isDemo();
    }

    public net.labymod.api.client.network.ClientPacketListener getClientPacketListener() {
        return getConnection();
    }

    public ClientWorld clientWorld() {
        if (this.labyMod$clientWorld == null) {
            this.labyMod$clientWorld = Laby.references().clientWorld();
        }
        return this.labyMod$clientWorld;
    }

    @org.jetbrains.annotations.Nullable
    public Scoreboard getScoreboard() {
        if (this.level != null) {
            return this.level.getScoreboard();
        }
        return null;
    }

    public MinecraftAuthenticator authenticator() {
        if (this.labyMod$authenticator == null) {
            this.labyMod$authenticator = Laby.references().minecraftAuthenticator();
        }
        return this.labyMod$authenticator;
    }

    public TabList getTabList() {
        if (this.gui == null) {
            return null;
        }
        return this.gui.getTabList();
    }

    public void updateWindowTitle() {
        Minecraft minecraft = Minecraft.getInstance();
        com.mojang.blaze3d.platform.Window window = minecraft.getWindow();
        if (window != null) {
            minecraft.updateTitle();
        }
    }

    public void openChat(String defaultInput) {
        ChatScreen chatScreen = new ChatScreen("", false);
        Minecraft.getInstance().setScreen(chatScreen);
        chatExecutor().insertText(defaultInput, true);
    }

    @Redirect(method = {"runTick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/DebugScreenOverlay;logFrameDuration(J)V"))
    private void labyMod$logFrameTime(DebugScreenOverlay overlay, long sample) {
        overlay.logFrameDuration(sample);
        this.labyMod$frameTimeLogger.log(sample);
    }

    public SampleLogger frameTimeLogger() {
        return this.labyMod$frameTimeLogger;
    }

    public boolean isMouseLocked() {
        return this.mouseHandler.isMouseGrabbed();
    }

    public MouseHandlerAccessor mouseHandler() {
        return this.mouseHandler;
    }

    public net.labymod.laby3d.api.pipeline.target.RenderTarget mainTarget() {
        if (this.labyMod$wrappedMainTarget == null) {
            this.labyMod$wrappedMainTarget = new VersionedRenderTarget(this.mainRenderTarget);
        }
        return this.labyMod$wrappedMainTarget;
    }

    public String getClipboard() {
        return this.keyboardHandler.getClipboard();
    }

    public void setClipboard(String value) {
        this.keyboardHandler.setClipboard(value);
    }

    public boolean isDestroying() {
        if (this.gameMode == null) {
            return false;
        }
        return this.gameMode.isDestroying();
    }

    public float getDestroyProgress() {
        if (this.gameMode == null) {
            return 0.0f;
        }
        return this.gameMode.destroyProgress;
    }

    public void reloadResources() {
        reloadResourcePacks();
    }

    public WorldRenderer worldRenderer() {
        return this.levelRenderer;
    }

    public ItemStackRenderer itemStackRenderer() {
        return this.itemRenderer;
    }

    public net.labymod.api.client.entity.EntityRenderDispatcher entityRenderDispatcher() {
        return this.entityRenderDispatcher;
    }

    public long getRunningMillis() {
        return Util.getMillis();
    }

    public boolean isLastItemUsed() {
        return this.labymod$isLastItemUsed;
    }

    public boolean isLastBlockUsed() {
        return this.labymod$isLastBlockUsed;
    }

    public void refreshRealmsClient() {
        this.realmsDataFetcher = new RealmsDataFetcher(RealmsClient.getOrCreate((Minecraft) this));
    }

    public void crashGame(GameCrashReport report) {
        crash(Minecraft.getInstance(), Minecraft.getInstance().gameDirectory, (CrashReport) report.crashReport());
    }

    public void shutdownGame() {
        destroy();
    }

    public GameMode gameMode() {
        if (this.gameMode == null) {
            return GameMode.UNKNOWN;
        }
        return GameMode.fromId(this.gameMode.getPlayerMode().getId());
    }

    public boolean isLoadingOverlayPresent() {
        return getOverlay() != null;
    }

    public net.labymod.api.client.world.phys.hit.HitResult getHitResult() {
        if (this.hitResult == null) {
            return null;
        }
        return this.hitResult;
    }

    @Insert(method = {"runTick"}, at = @At("HEAD"))
    private void labyMod$updateMouse(boolean b, InsertInfo callback) {
        com.mojang.blaze3d.platform.Window window = Minecraft.getInstance().getWindow();
        double x = (this.mouseHandler.xpos() * ((double) window.getGuiScaledWidth())) / ((double) window.getScreenWidth());
        double y = (this.mouseHandler.ypos() * ((double) window.getGuiScaledHeight())) / ((double) window.getScreenHeight());
        labyMod$mouseBridge().updateMouse(x, y);
    }

    @Insert(method = {"startUseItem"}, at = @At("HEAD"))
    private void labyMod$fireGameUseItemEvent(InsertInfo ci) {
        this.labymod$isLastItemUsed = false;
        this.labymod$isLastBlockUsed = false;
    }

    @Inject(method = {"startUseItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;swing(Lnet/minecraft/world/InteractionHand;)V", ordinal = 1, shift = At.Shift.AFTER)})
    private void labyMod$fireGameUseItemEventBlock(CallbackInfo ci) {
        this.labymod$isLastBlockUsed = true;
    }

    @Inject(method = {"startUseItem"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;useItem(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", shift = At.Shift.AFTER)})
    private void labyMod$fireGameUseItemEventItem(CallbackInfo ci) {
        this.labymod$isLastItemUsed = true;
    }

    public void updateBlockBreak(boolean leftClick) {
        shadow$continueAttack(leftClick);
    }
}


