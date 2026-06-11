package net.labymod.v1_21_11.mixins.client;

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
import net.labymod.v1_21_11.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_21_11.client.gui.window.VersionedWindow;
import net.labymod.v1_21_11.client.resources.texture.VersionedMinecraftTextures;
import net.labymod.v1_21_11.laby3d.pipeline.target.VersionedRenderTarget;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/MixinMinecraftAPI.class */
@Mixin({gfj.class})
@Implements({@Interface(iface = Minecraft.class, prefix = "minecraft$", remap = Interface.Remap.NONE)})
public abstract class MixinMinecraftAPI implements Minecraft {

    @Unique
    private final SampleLogger labyMod$frameTimeLogger = new SampleLogger();

    @Shadow
    private static int bb;

    @Mutable
    @Shadow
    @Final
    private gfx W;

    @Shadow
    @Final
    public gfk n;

    @Shadow
    public hnh s;

    @Shadow
    private boolean aS;

    @Shadow
    public hif r;

    @Shadow
    @Final
    public gfo k;

    @Shadow
    public gsb x;

    @Shadow
    @Final
    public giq j;

    @Shadow
    public gfi o;

    @Shadow
    @Final
    private baw ah;

    @Shadow
    @Final
    private fxt an;
    private ComponentMapper labyMod$componentMapper;

    @Shadow
    @Nullable
    public ftk u;

    @Shadow
    @Nullable
    public hio q;

    @Shadow
    private hxp T;

    @Shadow
    private hwo R;
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
    public hoh e;

    @Shadow
    @Final
    public hob i;

    @Mutable
    @Shadow
    @Final
    private gch aJ;

    @Shadow
    protected abstract void shadow$e(boolean z);

    @Shadow
    public abstract hig R();

    @Shadow
    public abstract grw aP();

    @Shadow
    public abstract CompletableFuture<Void> p();

    @Shadow
    public abstract void a(@org.jetbrains.annotations.Nullable gsb gsbVar);

    @Shadow
    public abstract void r();

    @Shadow
    public abstract String shadow$n();

    @Shadow
    public abstract boolean shadow$P();

    @Shadow
    public abstract gez aD();

    @Shadow
    public static gfj V() {
        return null;
    }

    @Override // net.labymod.api.client.Minecraft
    public int getFPS() {
        return bb;
    }

    @Shadow
    private static void a(gfj mc, File directory, m par1) {
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
        return this.s;
    }

    @Override // net.labymod.api.client.Minecraft
    public Entity getTargetEntity() {
        return gfj.V().t;
    }

    @Override // net.labymod.api.client.Minecraft
    public Entity getCameraEntity() {
        return gfj.V().au();
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftCamera getCamera() {
        if (gfj.V().au() != null) {
            return this.i.p();
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
        V().execute(runnable);
    }

    @Override // net.labymod.api.client.Minecraft
    public void executeNextTick(Runnable runnable) {
        V().a_(runnable);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isOnRenderThread() {
        return V().bK();
    }

    @Override // net.labymod.api.client.Minecraft
    public void setSessionRaw(Object gameSession) {
        this.W = (gfx) gameSession;
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
        return gfj.V().Z();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isIngame() {
        return gfj.V().r != null;
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateKeyRepeatingMode(boolean enabled) {
    }

    @Override // net.labymod.api.client.Minecraft
    public String getTranslation(String translationKey) {
        return uu.a().a(translationKey);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean hasTranslation(String translationKey) {
        return uu.a().b(translationKey);
    }

    @Override // net.labymod.api.client.Minecraft
    public float getPartialTicks() {
        return aD().a(false);
    }

    @Override // net.labymod.api.client.Minecraft
    public float getTickDelta() {
        return aD().a();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isPaused() {
        return this.aS;
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftOptions options() {
        return this.k;
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
        gfj.V().e.p();
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersion() {
        return w.b().c();
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersionId() {
        return w.b().b();
    }

    @Override // net.labymod.api.client.Minecraft
    public int getProtocolVersion() {
        return w.c();
    }

    @Override // net.labymod.api.client.Minecraft
    public int getDataVersion() {
        return w.b().a().b();
    }

    @Intrinsic
    public String minecraft$getVersionType() {
        return shadow$n();
    }

    @Intrinsic
    public boolean minecraft$isDemo() {
        return shadow$P();
    }

    @Override // net.labymod.api.client.Minecraft
    public ClientPacketListener getClientPacketListener() {
        return R();
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
        if (this.r != null) {
            return this.r.ab();
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
        if (this.j == null) {
            return null;
        }
        return this.j.i();
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateWindowTitle() {
        gfj minecraft = gfj.V();
        fyk window = minecraft.aR();
        if (window != null) {
            minecraft.g();
        }
    }

    @Override // net.labymod.api.client.Minecraft
    public void openChat(String defaultInput) {
        gqy chatScreen = new gqy("", false);
        gfj.V().a(chatScreen);
        chatExecutor().insertText(defaultInput, true);
    }

    @Redirect(method = {"runTick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/DebugScreenOverlay;logFrameDuration(J)V"))
    private void labyMod$logFrameTime(gjm overlay, long sample) {
        overlay.a(sample);
        this.labyMod$frameTimeLogger.log(sample);
    }

    @Override // net.labymod.api.client.Minecraft
    public SampleLogger frameTimeLogger() {
        return this.labyMod$frameTimeLogger;
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isMouseLocked() {
        return this.n.h();
    }

    @Override // net.labymod.api.client.Minecraft
    public MouseHandlerAccessor mouseHandler() {
        return this.n;
    }

    @Override // net.labymod.api.client.Minecraft
    public RenderTarget mainTarget() {
        if (this.labyMod$wrappedMainTarget == null) {
            this.labyMod$wrappedMainTarget = new VersionedRenderTarget(this.an);
        }
        return this.labyMod$wrappedMainTarget;
    }

    @Override // net.labymod.api.client.Minecraft
    public String getClipboard() {
        return this.o.a();
    }

    @Override // net.labymod.api.client.Minecraft
    public void setClipboard(String value) {
        this.o.a(value);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isDestroying() {
        if (this.q == null) {
            return false;
        }
        return this.q.j();
    }

    @Override // net.labymod.api.client.Minecraft
    public float getDestroyProgress() {
        if (this.q == null) {
            return 0.0f;
        }
        return this.q.f;
    }

    @Override // net.labymod.api.client.Minecraft
    public void reloadResources() {
        p();
    }

    @Override // net.labymod.api.client.Minecraft
    public WorldRenderer worldRenderer() {
        return this.e;
    }

    @Override // net.labymod.api.client.Minecraft
    public ItemStackRenderer itemStackRenderer() {
        return this.T;
    }

    @Override // net.labymod.api.client.Minecraft
    public EntityRenderDispatcher entityRenderDispatcher() {
        return this.R;
    }

    @Override // net.labymod.api.client.Minecraft
    public long getRunningMillis() {
        return bhs.c();
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
        this.aJ = new gch(fzz.a((gfj) this));
    }

    @Override // net.labymod.api.client.Minecraft
    public void crashGame(GameCrashReport report) {
        a(gfj.V(), gfj.V().p, (m) report.crashReport());
    }

    @Override // net.labymod.api.client.Minecraft
    public void shutdownGame() {
        r();
    }

    @Override // net.labymod.api.client.Minecraft
    public GameMode gameMode() {
        if (this.q == null) {
            return GameMode.UNKNOWN;
        }
        return GameMode.fromId(this.q.i().a());
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isLoadingOverlayPresent() {
        return aP() != null;
    }

    @Override // net.labymod.api.client.Minecraft
    public HitResult getHitResult() {
        if (this.u == null) {
            return null;
        }
        return this.u;
    }

    @Insert(method = {"runTick"}, at = @At("HEAD"))
    private void labyMod$updateMouse(boolean b, InsertInfo callback) {
        fyk window = gfj.V().aR();
        double x = (this.n.e() * ((double) window.o())) / ((double) window.m());
        double y = (this.n.f() * ((double) window.p())) / ((double) window.n());
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
        shadow$e(leftClick);
    }
}
