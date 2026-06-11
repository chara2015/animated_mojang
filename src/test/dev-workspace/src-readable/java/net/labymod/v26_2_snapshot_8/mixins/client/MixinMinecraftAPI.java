package net.labymod.v26_2_snapshot_8.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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
import net.labymod.api.util.CastUtil;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.input.MouseBridge;
import net.labymod.core.main.LabyMod;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.v26_2_snapshot_8.client.gui.screen.LabyScreenRenderer;
import net.labymod.v26_2_snapshot_8.client.gui.window.VersionedWindow;
import net.labymod.v26_2_snapshot_8.client.resources.texture.VersionedMinecraftTextures;
import net.labymod.v26_2_snapshot_8.laby3d.pipeline.target.VersionedRenderTarget;
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
import net.minecraft.client.gui.screens.options.OnlineOptionsScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.locale.Language;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/MixinMinecraftAPI.class */
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
    @Final
    public Gui gui;

    @Shadow
    public KeyboardHandler keyboardHandler;
    private ComponentMapper labyMod$componentMapper;

    @Shadow
    @Nullable
    public HitResult hitResult;

    @Shadow
    @Nullable
    public MultiPlayerGameMode gameMode;

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
    private RenderTarget labyMod$wrappedMainTarget;
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
    public abstract CompletableFuture<Void> reloadResourcePacks();

    @Shadow
    public abstract boolean shadow$isDemo();

    @Shadow
    public abstract DeltaTracker getDeltaTracker();

    @Shadow
    public abstract void close();

    @Shadow
    public abstract void exitWorldAndClose();

    @Shadow
    public static Minecraft getInstance() {
        return null;
    }

    @Override // net.labymod.api.client.Minecraft
    public int getFPS() {
        return fps;
    }

    @Override // net.labymod.api.client.Minecraft
    public Window minecraftWindow() {
        if (this.labyMod$minecraftWindow == null) {
            this.labyMod$minecraftWindow = new VersionedWindow();
        }
        return this.labyMod$minecraftWindow;
    }

    @Override // net.labymod.api.client.Minecraft
    public ClientPlayer getClientPlayer() {
        return this.player;
    }

    @Override // net.labymod.api.client.Minecraft
    public Entity getTargetEntity() {
        return Minecraft.getInstance().crosshairPickEntity;
    }

    @Override // net.labymod.api.client.Minecraft
    public Entity getCameraEntity() {
        GameRenderer renderer = this.gameRenderer;
        if (renderer == null) {
            return null;
        }
        return renderer.mainCamera().entity();
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftCamera getCamera() {
        if (this.gameRenderer != null && Minecraft.getInstance().getCameraEntity() != null) {
            return this.gameRenderer.mainCamera();
        }
        return null;
    }

    @Override // net.labymod.api.client.Minecraft
    public SessionAccessor sessionAccessor() {
        if (this.labyMod$sessionAccessor == null) {
            this.labyMod$sessionAccessor = Laby.references().sessionAccessor();
        }
        return this.labyMod$sessionAccessor;
    }

    @Override // net.labymod.api.client.Minecraft
    public void executeOnRenderThread(Runnable runnable) {
        getInstance().execute(runnable);
    }

    @Override // net.labymod.api.client.Minecraft
    public void executeNextTick(Runnable runnable) {
        getInstance().schedule(runnable);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isOnRenderThread() {
        return getInstance().isSameThread();
    }

    @Override // net.labymod.api.client.Minecraft
    public void setSessionRaw(Object gameSession) {
        this.user = (User) gameSession;
    }

    @Override // net.labymod.api.client.Minecraft
    public ScreenWrapper wrapScreen(ScreenInstance screen) {
        Object labyScreenRenderer;
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

    @Override // net.labymod.api.client.Minecraft
    public MinecraftTextures textures() {
        if (this.labyMod$minecraftTextures == null) {
            this.labyMod$minecraftTextures = new VersionedMinecraftTextures();
        }
        return this.labyMod$minecraftTextures;
    }

    @Override // net.labymod.api.client.Minecraft
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

    @Override // net.labymod.api.client.Minecraft
    public MutableMouse mouse() {
        return labyMod$mouseBridge().mouse();
    }

    @Override // net.labymod.api.client.Minecraft
    public Mouse absoluteMouse() {
        return labyMod$mouseBridge().absoluteMouse();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isSingleplayer() {
        return Minecraft.getInstance().hasSingleplayerServer();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isIngame() {
        return Minecraft.getInstance().level != null;
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateKeyRepeatingMode(boolean enabled) {
    }

    @Override // net.labymod.api.client.Minecraft
    public String getTranslation(String translationKey) {
        return Language.getInstance().getOrDefault(translationKey);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean hasTranslation(String translationKey) {
        return Language.getInstance().has(translationKey);
    }

    @Override // net.labymod.api.client.Minecraft
    public float getPartialTicks() {
        return getDeltaTracker().getGameTimeDeltaPartialTick(false);
    }

    @Override // net.labymod.api.client.Minecraft
    public float getTickDelta() {
        return getDeltaTracker().getGameTimeDeltaTicks();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isPaused() {
        return this.pause;
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftOptions options() {
        return this.options;
    }

    @Override // net.labymod.api.client.Minecraft
    public ComponentMapper componentMapper() {
        if (this.labyMod$componentMapper == null) {
            this.labyMod$componentMapper = Laby.references().componentMapper();
        }
        return this.labyMod$componentMapper;
    }

    @Override // net.labymod.api.client.Minecraft
    public ChatExecutor chatExecutor() {
        if (this.labyMod$chatExecutor == null) {
            this.labyMod$chatExecutor = Laby.references().chatExecutor();
        }
        return this.labyMod$chatExecutor;
    }

    @Override // net.labymod.api.client.Minecraft
    public void requestChunkUpdate() {
        Minecraft.getInstance().levelRenderer.sectionOcclusionGraph().invalidate();
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersion() {
        return SharedConstants.getCurrentVersion().name();
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersionId() {
        return SharedConstants.getCurrentVersion().id();
    }

    @Override // net.labymod.api.client.Minecraft
    public int getProtocolVersion() {
        return SharedConstants.getProtocolVersion();
    }

    @Override // net.labymod.api.client.Minecraft
    public int getDataVersion() {
        return SharedConstants.getCurrentVersion().dataVersion().version();
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersionType() {
        return "release";
    }

    @Intrinsic
    public boolean minecraft$isDemo() {
        return shadow$isDemo();
    }

    @Shadow
    public static void crash(Minecraft par1, File par2, CrashReport par3, int par4) {
    }

    @Override // net.labymod.api.client.Minecraft
    public net.labymod.api.client.network.ClientPacketListener getClientPacketListener() {
        return getConnection();
    }

    @Override // net.labymod.api.client.Minecraft
    public ClientWorld clientWorld() {
        if (this.labyMod$clientWorld == null) {
            this.labyMod$clientWorld = Laby.references().clientWorld();
        }
        return this.labyMod$clientWorld;
    }

    @Override // net.labymod.api.client.Minecraft
    @org.jetbrains.annotations.Nullable
    public Scoreboard getScoreboard() {
        if (this.level != null) {
            return this.level.getScoreboard();
        }
        return null;
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftAuthenticator authenticator() {
        if (this.labyMod$authenticator == null) {
            this.labyMod$authenticator = Laby.references().minecraftAuthenticator();
        }
        return this.labyMod$authenticator;
    }

    @Override // net.labymod.api.client.Minecraft
    public TabList getTabList() {
        if (this.gui == null) {
            return null;
        }
        return this.gui.hud.getTabList();
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateWindowTitle() {
        Minecraft minecraft = Minecraft.getInstance();
        com.mojang.blaze3d.platform.Window window = minecraft.getWindow();
        if (window != null) {
            minecraft.updateTitle();
        }
    }

    @Override // net.labymod.api.client.Minecraft
    public void openChat(String defaultInput) {
        ChatScreen chatScreen = new ChatScreen("", false);
        Minecraft.getInstance().gui.setScreen(chatScreen);
        chatExecutor().insertText(defaultInput, true);
    }

    @WrapOperation(method = {"renderFrame"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/DebugScreenOverlay;logFrameDuration(J)V")})
    private void labyMod$logFrameTime(DebugScreenOverlay instance, long sample, Operation<Void> original) {
        original.call(new Object[]{instance, Long.valueOf(sample)});
        this.labyMod$frameTimeLogger.log(sample);
    }

    @Override // net.labymod.api.client.Minecraft
    public SampleLogger frameTimeLogger() {
        return this.labyMod$frameTimeLogger;
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isMouseLocked() {
        return this.mouseHandler.isMouseGrabbed();
    }

    @Override // net.labymod.api.client.Minecraft
    public MouseHandlerAccessor mouseHandler() {
        return this.mouseHandler;
    }

    @Override // net.labymod.api.client.Minecraft
    public RenderTarget mainTarget() {
        if (this.labyMod$wrappedMainTarget == null) {
            this.labyMod$wrappedMainTarget = new VersionedRenderTarget(this.gameRenderer.mainRenderTarget());
        }
        return this.labyMod$wrappedMainTarget;
    }

    @Override // net.labymod.api.client.Minecraft
    public String getClipboard() {
        return this.keyboardHandler.getClipboard();
    }

    @Override // net.labymod.api.client.Minecraft
    public void setClipboard(String value) {
        this.keyboardHandler.setClipboard(value);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isDestroying() {
        if (this.gameMode == null) {
            return false;
        }
        return this.gameMode.isDestroying();
    }

    @Override // net.labymod.api.client.Minecraft
    public float getDestroyProgress() {
        if (this.gameMode == null) {
            return 0.0f;
        }
        return this.gameMode.destroyProgress;
    }

    @Override // net.labymod.api.client.Minecraft
    public void reloadResources() {
        reloadResourcePacks();
    }

    @Override // net.labymod.api.client.Minecraft
    public WorldRenderer worldRenderer() {
        return this.levelRenderer;
    }

    @Override // net.labymod.api.client.Minecraft
    public ItemStackRenderer itemStackRenderer() {
        return ItemStackRenderer.NOP;
    }

    @Override // net.labymod.api.client.Minecraft
    public net.labymod.api.client.entity.EntityRenderDispatcher entityRenderDispatcher() {
        return this.entityRenderDispatcher;
    }

    @Override // net.labymod.api.client.Minecraft
    public long getRunningMillis() {
        return Util.getMillis();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isLastItemUsed() {
        return this.labymod$isLastItemUsed;
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isLastBlockUsed() {
        return this.labymod$isLastBlockUsed;
    }

    @Override // net.labymod.api.client.Minecraft
    public void refreshRealmsClient() {
        this.realmsDataFetcher = new RealmsDataFetcher(RealmsClient.getOrCreate((Minecraft) this));
    }

    @Override // net.labymod.api.client.Minecraft
    public void crashGame(GameCrashReport report) {
        crash(Minecraft.getInstance(), Minecraft.getInstance().gameDirectory, (CrashReport) report.crashReport(), -1);
    }

    @Override // net.labymod.api.client.Minecraft
    public void shutdownGame() {
        exitWorldAndClose();
    }

    @Override // net.labymod.api.client.Minecraft
    public GameMode gameMode() {
        if (this.gameMode == null) {
            return GameMode.UNKNOWN;
        }
        return GameMode.fromId(this.gameMode.getPlayerMode().getId());
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isLoadingOverlayPresent() {
        return this.gui.overlay() != null;
    }

    @Override // net.labymod.api.client.Minecraft
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

    @Override // net.labymod.api.client.Minecraft
    public void updateBlockBreak(boolean leftClick) {
        shadow$continueAttack(leftClick);
    }

    @Override // net.labymod.api.client.Minecraft
    public void confirmFriendsListEnabled(Runnable onEnabled) {
        OnlineOptionsScreen.confirmFriendsListEnabled((Minecraft) CastUtil.cast(this), onEnabled, this.gui.screen());
    }
}
