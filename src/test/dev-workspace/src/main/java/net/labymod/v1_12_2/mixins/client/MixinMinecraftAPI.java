package net.labymod.v1_12_2.mixins.client;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.FutureTask;
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
import net.labymod.api.util.time.ModernTickDeltaTimer;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.input.MouseBridge;
import net.labymod.core.main.LabyMod;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.v1_12_2.client.ClientCamera;
import net.labymod.v1_12_2.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_12_2.client.gui.window.VersionedWindow;
import net.labymod.v1_12_2.client.input.KeyBindingHelper;
import net.labymod.v1_12_2.client.resources.VersionedI18n;
import net.labymod.v1_12_2.client.resources.VersionedLocale;
import net.labymod.v1_12_2.client.resources.texture.VersionedMinecraftTextures;
import net.labymod.v1_12_2.laby3d.pipeline.target.VersionedRenderTarget;
import net.minecraft.realms.RealmsSharedConstants;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/MixinMinecraftAPI.class */
@Mixin({bib.class})
@Implements({@Interface(iface = Minecraft.class, prefix = "minecraft$", remap = Interface.Remap.NONE)})
public abstract class MixinMinecraftAPI implements Minecraft {

    @Shadow
    private static int ar;

    @Mutable
    @Shadow
    @Final
    private bii af;

    @Shadow
    public bud h;

    @Shadow
    private bih Y;

    @Shadow
    public bid t;

    @Shadow
    @Final
    private Queue<FutureTask<?>> aQ;

    @Shadow
    public bsb f;

    @Shadow
    public biq q;

    @Shadow
    @Final
    public rb z;

    @Shadow
    private bvd aJ;

    @Shadow
    public bsa c;

    @Shadow
    private bzw ab;

    @Shadow
    private boolean ag;

    @Shadow
    private bzf aa;
    private Window labyMod$minecraftWindow;
    private MinecraftTextures labyMod$minecraftTextures;
    private long labyMod$startupMillis;
    private RenderTarget labyMod$wrappedMainRenderTarget;
    private final ModernTickDeltaTimer labymod$modernTimer = new ModernTickDeltaTimer();
    private boolean labymod$isLastItemUsed;
    private boolean labymod$isLastBlockUsed;

    @Shadow
    public buy g;

    @Shadow
    public bhc s;

    @Shadow
    public bic v;

    @Shadow
    public abstract ListenableFuture<Object> shadow$a(Runnable runnable);

    @Shadow
    public abstract brz shadow$v();

    @Shadow
    public abstract void f();

    @Shadow
    public abstract boolean aF();

    @Shadow
    public abstract void a(b bVar);

    @Shadow
    public abstract void n();

    @Shadow
    protected abstract void shadow$b(boolean z);

    @Shadow
    public abstract String shadow$d();

    @Shadow
    public abstract boolean shadow$u();

    @Override // net.labymod.api.client.Minecraft
    public int getFPS() {
        return ar;
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
        return this.h;
    }

    @Override // net.labymod.api.client.Minecraft
    public Entity getTargetEntity() {
        return bib.z().i;
    }

    @Override // net.labymod.api.client.Minecraft
    public Entity getCameraEntity() {
        return bib.z().aa();
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftCamera getCamera() {
        if (bib.z().aa() != null) {
            return ClientCamera.INSTANCE;
        }
        return null;
    }

    @Override // net.labymod.api.client.Minecraft
    public void executeOnRenderThread(Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable");
        shadow$a(runnable);
    }

    @Override // net.labymod.api.client.Minecraft
    public void executeNextTick(Runnable runnable) {
        Objects.requireNonNull(runnable, "runnable");
        this.aQ.add(ListenableFutureTask.create(runnable, (Object) null));
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isOnRenderThread() {
        return aF();
    }

    @Override // net.labymod.api.client.Minecraft
    public void setSessionRaw(Object gameSession) {
        this.af = (bii) gameSession;
    }

    @Override // net.labymod.api.client.Minecraft
    public ScreenWrapper wrapScreen(ScreenInstance screen) {
        if (screen instanceof ScreenWrapper) {
            return (ScreenWrapper) screen;
        }
        return Laby.references().screenWrapperFactory().create(screen instanceof LabyScreen ? new LabyScreenRenderer((LabyScreen) screen) : screen);
    }

    @Override // net.labymod.api.client.Minecraft
    public RenderTarget mainTarget() {
        if (this.labyMod$wrappedMainRenderTarget == null) {
            this.labyMod$wrappedMainRenderTarget = new VersionedRenderTarget(this.aJ);
        }
        return this.labyMod$wrappedMainRenderTarget;
    }

    @Override // net.labymod.api.client.Minecraft
    public SessionAccessor sessionAccessor() {
        return Laby.references().sessionAccessor();
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
        return Laby.references().minecraftSounds();
    }

    private MouseBridge labyMod$mouseBridge() {
        return LabyMod.references().mouseBridge();
    }

    @Override // net.labymod.api.client.Minecraft
    public MutableMouse mouse() {
        return labyMod$mouseBridge().mouse();
    }

    @Override // net.labymod.api.client.Minecraft
    public Mouse absoluteMouse() {
        return labyMod$mouseBridge().absoluteMouse();
    }

    @Intrinsic
    public boolean minecraft$isSingleplayer() {
        return bib.z().E();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isIngame() {
        return bib.z().f != null;
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateKeyRepeatingMode(boolean enabled) {
        Keyboard.enableRepeatEvents(enabled);
    }

    @Override // net.labymod.api.client.Minecraft
    public String getTranslation(String translationKey) {
        VersionedLocale locale = VersionedI18n.getLocale();
        if (locale == null) {
            return translationKey;
        }
        return locale.getTranslation(translationKey);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean hasTranslation(String translationKey) {
        VersionedLocale locale = VersionedI18n.getLocale();
        if (locale == null) {
            return false;
        }
        return locale.hasTranslation(translationKey);
    }

    @Override // net.labymod.api.client.Minecraft
    public float getPartialTicks() {
        return this.Y.b;
    }

    @Override // net.labymod.api.client.Minecraft
    public float getTickDelta() {
        return this.labymod$modernTimer.getTickDelta();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isPaused() {
        return this.ag;
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftOptions options() {
        return this.t;
    }

    @Override // net.labymod.api.client.Minecraft
    public ComponentMapper componentMapper() {
        return Laby.references().componentMapper();
    }

    @Override // net.labymod.api.client.Minecraft
    public ChatExecutor chatExecutor() {
        return Laby.references().chatExecutor();
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersion() {
        return RealmsSharedConstants.VERSION_STRING;
    }

    @Override // net.labymod.api.client.Minecraft
    public String getVersionId() {
        return RealmsSharedConstants.VERSION_STRING;
    }

    @Override // net.labymod.api.client.Minecraft
    public int getProtocolVersion() {
        return RealmsSharedConstants.NETWORK_PROTOCOL_VERSION;
    }

    @Override // net.labymod.api.client.Minecraft
    public int getDataVersion() {
        return -1;
    }

    @Intrinsic
    public String minecraft$getVersionType() {
        return shadow$d();
    }

    @Intrinsic
    public boolean minecraft$isDemo() {
        return shadow$u();
    }

    @Override // net.labymod.api.client.Minecraft
    public void requestChunkUpdate() {
        bib.z().g.o();
    }

    @Override // net.labymod.api.client.Minecraft
    public MinecraftAuthenticator authenticator() {
        return Laby.references().minecraftAuthenticator();
    }

    @Override // net.labymod.api.client.Minecraft
    public ClientPacketListener getClientPacketListener() {
        return shadow$v();
    }

    @Override // net.labymod.api.client.Minecraft
    public ClientWorld clientWorld() {
        return Laby.references().clientWorld();
    }

    @Override // net.labymod.api.client.Minecraft
    @Nullable
    public Scoreboard getScoreboard() {
        if (this.f != null) {
            return this.f.af();
        }
        return null;
    }

    @Override // net.labymod.api.client.Minecraft
    @Nullable
    public TabList getTabList() {
        if (this.q == null) {
            return null;
        }
        return this.q.h();
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateWindowTitle() {
        Display.setTitle(LabyMod.getInstance().createTitle(getVersion(), ""));
    }

    @Override // net.labymod.api.client.Minecraft
    public void openChat(String defaultInput) {
        bkn chat = new bkn("");
        bib.z().a(chat);
        chatExecutor().insertText(defaultInput, true);
        executeNextTick(KeyBindingHelper::unpressKeybindings);
    }

    @Override // net.labymod.api.client.Minecraft
    public SampleLogger frameTimeLogger() {
        return this.z.logger();
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isMouseLocked() {
        return org.lwjgl.input.Mouse.isGrabbed();
    }

    @Override // net.labymod.api.client.Minecraft
    public MouseHandlerAccessor mouseHandler() {
        return this.v;
    }

    @Override // net.labymod.api.client.Minecraft
    public String getClipboard() {
        return Laby.references().clipboardController().getClipboardContent(Display.getWindowHandle());
    }

    @Override // net.labymod.api.client.Minecraft
    public void setClipboard(String value) {
        Laby.references().clipboardController().setClipboardContent(Display.getWindowHandle(), value);
    }

    @Override // net.labymod.api.client.Minecraft
    public boolean isDestroying() {
        if (this.c == null) {
            return false;
        }
        return this.c.m();
    }

    @Override // net.labymod.api.client.Minecraft
    public float getDestroyProgress() {
        if (this.c == null) {
            return 0.0f;
        }
        return this.c.e;
    }

    @Override // net.labymod.api.client.Minecraft
    public void reloadResources() {
        f();
    }

    @Override // net.labymod.api.client.Minecraft
    public WorldRenderer worldRenderer() {
        return this.g;
    }

    @Override // net.labymod.api.client.Minecraft
    public ItemStackRenderer itemStackRenderer() {
        return this.ab;
    }

    @Override // net.labymod.api.client.Minecraft
    public EntityRenderDispatcher entityRenderDispatcher() {
        return this.aa;
    }

    @Override // net.labymod.api.client.Minecraft
    public long getRunningMillis() {
        return TimeUtil.getMillis() - this.labyMod$startupMillis;
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
    public GameMode gameMode() {
        if (this.c == null) {
            return GameMode.UNKNOWN;
        }
        return GameMode.fromId(this.c.l().a());
    }

    @Override // net.labymod.api.client.Minecraft
    public void crashGame(GameCrashReport report) {
        a((b) report.crashReport());
    }

    @Override // net.labymod.api.client.Minecraft
    public void shutdownGame() {
        n();
    }

    @Override // net.labymod.api.client.Minecraft
    public HitResult getHitResult() {
        if (this.s == null) {
            return null;
        }
        return this.s;
    }

    @Override // net.labymod.api.client.Minecraft
    public void updateBlockBreak(boolean leftClick) {
        shadow$b(leftClick);
    }

    @Insert(method = {"runGameLoop"}, at = @At("HEAD"))
    private void labyMod$updateMouse(InsertInfo callback) {
        Window window = minecraftWindow();
        int scaledHeight = window.getScaledHeight();
        double x = (((double) org.lwjgl.input.Mouse.getX()) * ((double) window.getScaledWidth())) / ((double) window.getRawWidth());
        double y = ((double) scaledHeight) - ((((double) org.lwjgl.input.Mouse.getY()) * ((double) scaledHeight)) / ((double) window.getRawHeight()));
        labyMod$mouseBridge().updateMouse(x, y);
        this.labymod$modernTimer.advanceTime();
    }

    @Inject(method = {"createDisplay"}, at = {@At("TAIL")})
    private void labyMod$displayCreated(CallbackInfo ci) {
        this.labyMod$startupMillis = TimeUtil.getMillis();
    }

    @Insert(method = {"rightClickMouse"}, at = @At("HEAD"))
    private void labyMod$fireGameUseItemEvent(InsertInfo ci) {
        this.labymod$isLastItemUsed = false;
        this.labymod$isLastBlockUsed = false;
    }

    @Inject(method = {"rightClickMouse"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;swingArm(Lnet/minecraft/util/EnumHand;)V", shift = At.Shift.AFTER)})
    private void labyMod$fireGameUseItemEventBlock(CallbackInfo ci) {
        this.labymod$isLastBlockUsed = true;
    }

    @Inject(method = {"rightClickMouse"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;processRightClick(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/EnumHand;)Lnet/minecraft/util/EnumActionResult;", shift = At.Shift.AFTER)})
    private void labyMod$fireGameUseItemEventItem(CallbackInfo ci) {
        this.labymod$isLastItemUsed = true;
    }
}
