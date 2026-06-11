package net.labymod.v1_21_4.mixins.client;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.chat.ChatExecutor;
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
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.input.MouseBridge;
import net.labymod.core.main.LabyMod;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.v1_21_4.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_21_4.client.gui.window.VersionedWindow;
import net.labymod.v1_21_4.client.resources.texture.VersionedMinecraftTextures;
import net.labymod.v1_21_4.laby3d.pipeline.target.VersionedRenderTarget;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/client/MixinMinecraftAPI.class */
@Mixin({flk.class})
@Implements({@Interface(iface = Minecraft.class, prefix = "minecraft$", remap = Interface.Remap.NONE)})
public abstract class MixinMinecraftAPI implements Minecraft {

    @Unique
    private final SampleLogger labyMod$frameTimeLogger = new SampleLogger();

    @Shadow
    private static int bf;

    @Mutable
    @Shadow
    @Final
    private flw Z;

    @Shadow
    @Final
    public fll o;

    @Shadow
    public gkx t;

    @Shadow
    private boolean aW;

    @Shadow
    public gga s;

    @Shadow
    @Final
    public flo n;

    @Shadow
    public fum z;

    @Shadow
    @Final
    public foe m;

    @Shadow
    public flj p;

    @Shadow
    @Final
    private aum ak;

    @Shadow
    @Final
    private fef aq;
    private ComponentMapper labyMod$componentMapper;

    @Shadow
    @Nullable
    public faz w;

    @Shadow
    @Nullable
    public ggk r;

    @Shadow
    private gtd X;

    @Shadow
    private gsd V;
    private ChatExecutor labyMod$chatExecutor;
    private MinecraftAuthenticator labyMod$authenticator;
    private Window labyMod$minecraftWindow;
    private SessionAccessor labyMod$sessionAccessor;
    private MinecraftTextures labyMod$minecraftTextures;
    private MinecraftSounds labyMod$minecraftSounds;
    private ClientWorld labyMod$clientWorld;
    private MouseBridge labyMod$mouseBridge;
    private RenderTarget labyMod$apiMainTarget;
    private boolean labymod$isLastItemUsed;
    private boolean labymod$isLastBlockUsed;

    @Shadow
    @Final
    public glv f;

    @Shadow
    @Final
    public glq j;

    @Mutable
    @Shadow
    @Final
    private fik aQ;

    @Shadow
    protected abstract void shadow$d(boolean z);

    @Shadow
    public abstract ggb L();

    @Shadow
    public abstract fug aM();

    @Shadow
    public abstract CompletableFuture<Void> l();

    @Shadow
    public abstract void a(@org.jetbrains.annotations.Nullable fum fumVar);

    @Shadow
    public abstract void n();

    @Shadow
    public abstract String shadow$j();

    @Shadow
    public abstract boolean shadow$K();

    @Shadow
    public abstract fla av();

    @Shadow
    public static flk Q() {
        return null;
    }

    @Override // net.labymod.api.client.Minecraft
    public int getFPS() {
        return bf;
    }

    @Shadow
    private static void a(flk mc, File directory, o par1) {
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
        return this.t;
    }

    @Override // net.labymod.api.client.Minecraft
    public Entity getTargetEntity() {
        return flk.Q().v;
    }

    @Override // net.labymod.api.client.Minecraft
    public Entity getCameraEntity() {
        return flk.Q().ao();
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftCamera getCamera() {
        if (flk.Q().ao() != null) {
            return this.j.k();
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
        Q().execute(runnable);
    }

    @Override // net.labymod.api.client.Minecraft
    public void executeNextTick(Runnable runnable) {
        Q().a_(runnable);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isOnRenderThread() {
        return Q().bx();
    }

    @Override // net.labymod.api.client.Minecraft
    public void setSessionRaw(Object gameSession) {
        this.Z = (flw) gameSession;
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
        return flk.Q().U();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isIngame() {
        return flk.Q().s != null;
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateKeyRepeatingMode(boolean enabled) {
    }

    @Override // net.labymod.api.client.Minecraft
    public String getTranslation(String translationKey) {
        return tl.a().a(translationKey);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean hasTranslation(String translationKey) {
        return tl.a().b(translationKey);
    }

    @Override // net.labymod.api.client.Minecraft
    public float getPartialTicks() {
        return av().a(false);
    }

    @Override // net.labymod.api.client.Minecraft
    public float getTickDelta() {
        return av().a();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isPaused() {
        return this.aW;
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftOptions options() {
        return this.n;
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
        flk.Q().f.p();
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersion() {
        return ab.b().c();
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersionId() {
        return ab.b().b();
    }

    @Override // net.labymod.api.client.Minecraft
    public int getProtocolVersion() {
        return ab.c();
    }

    @Override // net.labymod.api.client.Minecraft
    public int getDataVersion() {
        return ab.b().d().c();
    }

    @Intrinsic
    public String minecraft$getVersionType() {
        return shadow$j();
    }

    @Intrinsic
    public boolean minecraft$isDemo() {
        return shadow$K();
    }

    @Override // net.labymod.api.client.Minecraft
    public ClientPacketListener getClientPacketListener() {
        return L();
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
        if (this.s != null) {
            return this.s.R();
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
        if (this.m == null) {
            return null;
        }
        return this.m.h();
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateWindowTitle() {
        flk minecraft = flk.Q();
        fey window = minecraft.aO();
        if (window != null) {
            minecraft.d();
        }
    }

    @Override // net.labymod.api.client.Minecraft
    public void openChat(String defaultInput) {
        fti chatScreen = new fti("");
        flk.Q().a(chatScreen);
        chatExecutor().insertText(defaultInput, true);
    }

    @Redirect(method = {"runTick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/DebugScreenOverlay;logFrameDuration(J)V"))
    private void labyMod$logFrameTime(fpc overlay, long sample) {
        overlay.a(sample);
        this.labyMod$frameTimeLogger.log(sample);
    }

    @Override // net.labymod.api.client.Minecraft
    public SampleLogger frameTimeLogger() {
        return this.labyMod$frameTimeLogger;
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isMouseLocked() {
        return this.o.h();
    }

    @Override // net.labymod.api.client.Minecraft
    public MouseHandlerAccessor mouseHandler() {
        return this.o;
    }

    @Override // net.labymod.api.client.Minecraft
    public RenderTarget mainTarget() {
        if (this.labyMod$apiMainTarget == null) {
            this.labyMod$apiMainTarget = new VersionedRenderTarget(this.aq);
        }
        return this.labyMod$apiMainTarget;
    }

    @Override // net.labymod.api.client.Minecraft
    public String getClipboard() {
        return this.p.a();
    }

    @Override // net.labymod.api.client.Minecraft
    public void setClipboard(String value) {
        this.p.a(value);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isDestroying() {
        if (this.r == null) {
            return false;
        }
        return this.r.k();
    }

    @Override // net.labymod.api.client.Minecraft
    public float getDestroyProgress() {
        if (this.r == null) {
            return 0.0f;
        }
        return this.r.f;
    }

    @Override // net.labymod.api.client.Minecraft
    public void reloadResources() {
        l();
    }

    @Override // net.labymod.api.client.Minecraft
    public WorldRenderer worldRenderer() {
        return this.f;
    }

    @Override // net.labymod.api.client.Minecraft
    public ItemStackRenderer itemStackRenderer() {
        return this.X;
    }

    @Override // net.labymod.api.client.Minecraft
    public EntityRenderDispatcher entityRenderDispatcher() {
        return this.V;
    }

    @Override // net.labymod.api.client.Minecraft
    public long getRunningMillis() {
        return af.c();
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
        this.aQ = new fik(fgl.a());
    }

    @Override // net.labymod.api.client.Minecraft
    public void crashGame(GameCrashReport report) {
        a(flk.Q(), flk.Q().q, (o) report.crashReport());
    }

    @Override // net.labymod.api.client.Minecraft
    public void shutdownGame() {
        n();
    }

    @Override // net.labymod.api.client.Minecraft
    public GameMode gameMode() {
        if (this.r == null) {
            return GameMode.UNKNOWN;
        }
        return GameMode.fromId(this.r.j().a());
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isLoadingOverlayPresent() {
        return aM() != null;
    }

    @Override // net.labymod.api.client.Minecraft
    public HitResult getHitResult() {
        if (this.w == null) {
            return null;
        }
        return this.w;
    }

    @Insert(method = {"runTick"}, at = @At("HEAD"))
    private void labyMod$updateMouse(boolean b, InsertInfo callback) {
        fey window = flk.Q().aO();
        double x = (this.o.e() * ((double) window.o())) / ((double) window.m());
        double y = (this.o.f() * ((double) window.p())) / ((double) window.n());
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
        shadow$d(leftClick);
    }
}
