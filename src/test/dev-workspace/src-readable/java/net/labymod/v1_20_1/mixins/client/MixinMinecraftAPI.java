package net.labymod.v1_20_1.mixins.client;

import java.util.concurrent.CompletableFuture;
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
import net.labymod.v1_20_1.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_20_1.client.gui.window.VersionedWindow;
import net.labymod.v1_20_1.client.resources.texture.VersionedMinecraftTextures;
import net.labymod.v1_20_1.laby3d.pipeline.target.VersionedRenderTarget;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/MixinMinecraftAPI.class */
@Mixin({enn.class})
@Implements({@Interface(iface = Minecraft.class, prefix = "minecraft$", remap = Interface.Remap.NONE)})
public abstract class MixinMinecraftAPI implements Minecraft {

    @Shadow
    private static int bb;

    @Mutable
    @Shadow
    @Final
    private eoc W;

    @Shadow
    @Final
    public eno n;

    @Shadow
    public fiy t;

    @Shadow
    private boolean aR;

    @Shadow
    private float aS;

    @Shadow
    @Final
    private eoa R;

    @Shadow
    public few s;

    @Shadow
    @Final
    public enr m;

    @Shadow
    public euq z;

    @Shadow
    @Final
    public eow l;

    @Shadow
    public enm o;

    @Shadow
    @Final
    public aoo q;

    @Shadow
    @Final
    private aku ai;

    @Shadow
    @Final
    private egv ap;
    private ComponentMapper labyMod$componentMapper;

    @Shadow
    @Nullable
    public eeg w;

    @Shadow
    @Nullable
    public ffa r;

    @Shadow
    private fpw U;

    @Shadow
    private fow T;
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
    public fjv f;

    @Shadow
    @Final
    public fjq j;

    @Mutable
    @Shadow
    @Final
    private ekq aL;

    @Shadow
    public abstract fex I();

    @Shadow
    public abstract euk aJ();

    @Shadow
    public abstract CompletableFuture<Void> j();

    @Shadow
    public abstract void a(@Nullable euq euqVar);

    @Shadow
    protected abstract void shadow$g(boolean z);

    @Shadow
    public abstract void l();

    @Shadow
    public abstract String shadow$h();

    @Shadow
    public abstract boolean shadow$H();

    @Shadow
    public static enn N() {
        return null;
    }

    @Override // net.labymod.api.client.Minecraft
    public int getFPS() {
        return bb;
    }

    @Shadow
    private static void c(o par1) {
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
        return enn.N().v;
    }

    @Override // net.labymod.api.client.Minecraft
    public Entity getCameraEntity() {
        return enn.N().al();
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftCamera getCamera() {
        if (enn.N().al() != null) {
            return this.j.m();
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
        N().execute(runnable);
    }

    @Override // net.labymod.api.client.Minecraft
    public void executeNextTick(Runnable runnable) {
        N().i(runnable);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isOnRenderThread() {
        return N().bl();
    }

    @Override // net.labymod.api.client.Minecraft
    public void setSessionRaw(Object gameSession) {
        this.W = (eoc) gameSession;
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
        return enn.N().R();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isIngame() {
        return enn.N().s != null;
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateKeyRepeatingMode(boolean enabled) {
    }

    @Override // net.labymod.api.client.Minecraft
    public String getTranslation(String translationKey) {
        return qm.a().a(translationKey);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean hasTranslation(String translationKey) {
        return qm.a().b(translationKey);
    }

    @Override // net.labymod.api.client.Minecraft
    public float getPartialTicks() {
        return this.aR ? this.aS : this.R.a;
    }

    @Override // net.labymod.api.client.Minecraft
    public float getTickDelta() {
        return this.R.b;
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isPaused() {
        return this.aR;
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftOptions options() {
        return this.m;
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
        enn.N().f.r();
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersion() {
        return aa.b().c();
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersionId() {
        return aa.b().b();
    }

    @Override // net.labymod.api.client.Minecraft
    public int getProtocolVersion() {
        return aa.c();
    }

    @Override // net.labymod.api.client.Minecraft
    public int getDataVersion() {
        return aa.b().d().c();
    }

    @Intrinsic
    public String minecraft$getVersionType() {
        return shadow$h();
    }

    @Intrinsic
    public boolean minecraft$isDemo() {
        return shadow$H();
    }

    @Override // net.labymod.api.client.Minecraft
    public ClientPacketListener getClientPacketListener() {
        return I();
    }

    @Override // net.labymod.api.client.Minecraft
    public ClientWorld clientWorld() {
        if (this.labyMod$clientWorld == null) {
            this.labyMod$clientWorld = Laby.references().clientWorld();
        }
        return this.labyMod$clientWorld;
    }

    @Override // net.labymod.api.client.Minecraft
    @Nullable
    public Scoreboard getScoreboard() {
        if (this.s != null) {
            return this.s.I();
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
        if (this.l == null) {
            return null;
        }
        return this.l.h();
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateWindowTitle() {
        enn minecraft = enn.N();
        ehn window = minecraft.aM();
        if (window != null) {
            minecraft.c();
        }
    }

    @Override // net.labymod.api.client.Minecraft
    public void openChat(String defaultInput) {
        eti chatScreen = new eti("");
        enn.N().a(chatScreen);
        chatExecutor().insertText(defaultInput, true);
    }

    @Override // net.labymod.api.client.Minecraft
    public SampleLogger frameTimeLogger() {
        return this.q.logger();
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
            this.labyMod$wrappedMainTarget = new VersionedRenderTarget(this.ap);
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
        if (this.r == null) {
            return false;
        }
        return this.r.m();
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
        j();
    }

    @Override // net.labymod.api.client.Minecraft
    public WorldRenderer worldRenderer() {
        return this.f;
    }

    @Override // net.labymod.api.client.Minecraft
    public ItemStackRenderer itemStackRenderer() {
        return this.U;
    }

    @Override // net.labymod.api.client.Minecraft
    public EntityRenderDispatcher entityRenderDispatcher() {
        return this.T;
    }

    @Override // net.labymod.api.client.Minecraft
    public long getRunningMillis() {
        return ac.b();
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
        this.aL = new ekq(eiz.a());
    }

    @Override // net.labymod.api.client.Minecraft
    public void crashGame(GameCrashReport report) {
        c((o) report.crashReport());
    }

    @Override // net.labymod.api.client.Minecraft
    public void shutdownGame() {
        l();
    }

    @Override // net.labymod.api.client.Minecraft
    public GameMode gameMode() {
        if (this.r == null) {
            return GameMode.UNKNOWN;
        }
        return GameMode.fromId(this.r.l().a());
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isLoadingOverlayPresent() {
        return aJ() != null;
    }

    @Override // net.labymod.api.client.Minecraft
    public HitResult getHitResult() {
        if (this.w == null) {
            return null;
        }
        return this.w;
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateBlockBreak(boolean leftClick) {
        shadow$g(leftClick);
    }

    @Insert(method = {"runTick"}, at = @At("HEAD"))
    private void labyMod$updateMouse(boolean b, InsertInfo callback) {
        ehn window = enn.N().aM();
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
}
