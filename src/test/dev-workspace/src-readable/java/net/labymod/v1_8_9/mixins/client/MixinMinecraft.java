package net.labymod.v1_8_9.mixins.client;

import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.chat.ChatScreenUpdateEvent;
import net.labymod.api.event.client.entity.player.ClientHotbarSlotChangeEvent;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.core.client.gui.window.DefaultAbstractWindow;
import net.labymod.core.event.client.lifecycle.GameFpsLimitEventCaller;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_8_9.client.VersionedMinecraft;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_8_9.client.input.InputHandler;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/MixinMinecraft.class */
@Mixin({ave.class})
@Implements({@Interface(iface = Minecraft.class, prefix = "minecraft$", remap = Interface.Remap.NONE)})
public abstract class MixinMinecraft {

    @Shadow
    public axu m;

    @Shadow
    public bew h;

    @Shadow
    public bdb f;

    @Shadow
    public avh t;

    @Shadow
    public int d;

    @Shadow
    public int e;
    private final ChatScreenUpdateEvent tooltipChatScreenUpdateEvent = new ChatScreenUpdateEvent(ChatScreenUpdateEvent.Reason.ITEM_TOOLTIPS);

    @Shadow
    public static ave A() {
        return null;
    }

    @Redirect(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Session;getSessionID()Ljava/lang/String;"))
    private String labyMod$censorSessionId(avm instance) {
        return "********************************";
    }

    @Insert(method = {"runTick()V"}, at = @At("HEAD"))
    private void labyMod$firePreTickEvent(InsertInfo ci) {
        labyMod$fireTickEvent(Phase.PRE);
    }

    @Insert(method = {"runTick()V"}, at = @At("TAIL"))
    private void labyMod$firePostTickEvent(InsertInfo ci) {
        labyMod$fireTickEvent(Phase.POST);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Redirect(method = {"runTick()V"}, at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;next()Z", remap = false))
    private boolean labyMod$fireKeyInputEvent() {
        if (!Keyboard.next()) {
            return false;
        }
        LabyScreenRenderer screenRenderer = this.m instanceof LabyScreenRenderer ? (LabyScreenRenderer) this.m : null;
        if (((VersionedMinecraft) this).dispatchKeyboardInput(screenRenderer)) {
            return labyMod$fireKeyInputEvent();
        }
        return true;
    }

    @Inject(method = {"runTick()V"}, at = {@At(remap = false, value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventButton()I", shift = At.Shift.BEFORE)})
    private void labyMod$fireMouseKeyInputEvent(CallbackInfo ci) {
        InputHandler.fireMouseInput(false);
    }

    @Inject(method = {"startGame"}, at = {@At("HEAD")})
    private void labyMod$firePreGameStartedInitializeEvent(CallbackInfo ci) {
        LabyMod.getInstance().onPreGameStarted();
    }

    @Inject(method = {"runTick"}, at = {@At(value = "FIELD", opcode = 181, target = "Lnet/minecraft/client/settings/GameSettings;advancedItemTooltips:Z", shift = At.Shift.AFTER)})
    private void labyMod$refreshChatForAdvancedItemTooltips(CallbackInfo ci) {
        Laby.fireEvent(this.tooltipChatScreenUpdateEvent);
    }

    @Inject(method = {"startGame"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;mcResourceManager:Lnet/minecraft/client/resources/IReloadableResourceManager;", shift = At.Shift.AFTER, ordinal = 0)})
    private void labyMod$fireResourceInitializationInitializeEvent(CallbackInfo ci) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION));
    }

    @Inject(method = {"checkGLError"}, at = {@At("TAIL")})
    private void labyMod$firePostStartupInitializeEvent(String s, CallbackInfo ci) {
        if (!s.equalsIgnoreCase("Post startup")) {
            return;
        }
        LabyMod.getInstance().eventBus().fire(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_STARTUP));
    }

    @Inject(method = {"startGame"}, at = {@At("TAIL")})
    private void labyMod$firePostGameStartedInitializeEvent(CallbackInfo ci) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_GAME_STARTED));
    }

    @Redirect(method = {"createDisplay"}, at = @At(remap = false, value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V"))
    private void labyMod$modifiedTitle(String newTitle) {
        ave.A().updateWindowTitle();
    }

    @Redirect(method = {"displayGuiScreen"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;onGuiClosed()V"))
    private void labyMod$cancelOnGuiClosed(axu screen) {
    }

    @ModifyVariable(method = {"displayGuiScreen"}, index = 1, at = @At(value = "FIELD", opcode = 181, shift = At.Shift.BEFORE, ordinal = 1))
    public axu labyMod$fireScreenOpenEvent(axu newScreen) {
        axu previousScreen = ave.A().m;
        try {
            ScreenInstance instance = ((ScreenDisplayEvent) Laby.fireEvent(new ScreenDisplayEvent(labyMod$createScreenWrapper(previousScreen), labyMod$createScreenWrapper(newScreen)))).getScreen();
            newScreen = instance == null ? null : (axu) instance.wrap().getVersionedScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (previousScreen != null && newScreen != previousScreen) {
            previousScreen.m();
        }
        if ((newScreen instanceof LabyScreenRenderer) && (((LabyScreenRenderer) newScreen).screen() instanceof Activity)) {
            ((LabyScreenRenderer) newScreen).screen().onOpenScreen();
        }
        return newScreen;
    }

    private ScreenWrapper labyMod$createScreenWrapper(axu screen) {
        if (screen == null) {
            return null;
        }
        return Laby.references().screenWrapperFactory().create(screen);
    }

    @Redirect(method = {"displayGuiScreen"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;setWorldAndResolution(Lnet/minecraft/client/Minecraft;II)V"))
    public void labyMod$overrideInitScreen(axu screen, ave mc, int width, int height) {
        this.m.a(mc, width, height);
    }

    @Inject(method = {"resize"}, at = {@At("TAIL")})
    private void labyMod$fireScreenResizeEvent(int rawWidth, int rawHeight, CallbackInfo ci) {
        Window minecraftWindow = labyMod$getMinecraft().minecraftWindow();
        Laby.fireEvent(new ScreenResizeEvent(minecraftWindow.getScaledWidth(), minecraftWindow.getScaledHeight(), rawWidth, rawHeight));
    }

    @Inject(method = {"launchIntegratedServer"}, at = {@At("HEAD")})
    private void labyMod$disconnectFromServer(String p_launchIntegratedServer_1_, String p_launchIntegratedServer_2_, adp p_launchIntegratedServer_3_, CallbackInfo ci) {
        bdb world = this.f;
        if (world != null) {
            world.H();
        }
    }

    @Inject(method = {"loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V"}, at = {@At("HEAD")})
    private void labyMod$fireLoadWorldEvent(bdb world, String name, CallbackInfo ci) {
        labyMod$fireLoadWorldEventPhase(world, Phase.PRE);
    }

    @Inject(method = {"loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V"}, at = {@At("TAIL")})
    private void labyMod$firePostNetworkDisconnectEvent(bdb world, String name, CallbackInfo ci) {
        labyMod$fireLoadWorldEventPhase(world, Phase.POST);
    }

    private void labyMod$fireLoadWorldEventPhase(bdb worldClient, Phase phase) {
        if (worldClient == null) {
            Laby.labyAPI().serverController().disconnect(phase);
        } else {
            Laby.fireEvent(new WorldLoadEvent(phase));
        }
    }

    private void labyMod$fireTickEvent(Phase phase) {
        Laby.fireEvent(new GameTickEvent(phase));
    }

    @Inject(method = {"clickMouse"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$handleClickMouse(CallbackInfo callbackInfo) {
        if (labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.ATTACK)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"rightClickMouse"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$handleRightClickMouse(CallbackInfo callbackInfo) {
        if (labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.INTERACT)) {
            callbackInfo.cancel();
        }
    }

    @Redirect(method = {"sendClickBlockToController"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;onPlayerDamageBlock(Lnet/minecraft/util/BlockPos;Lnet/minecraft/util/EnumFacing;)Z"))
    private boolean labyMod$handleSendClickBlockToController(bda instance, cj blockPos, cq facing) {
        if (labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType.ATTACK)) {
            return false;
        }
        return instance.c(blockPos, facing);
    }

    @Redirect(method = {"runGameLoop"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;updateCameraAndRender(FJ)V"))
    private void labyMod$fireGameRenderEvent(bfk gameRenderer, float partialTicks, long nanoTime) {
        labyMod$fireGameRenderEvent(Phase.PRE, VersionedStackProvider.DEFAULT_STACK, partialTicks);
        gameRenderer.a(partialTicks, nanoTime);
        labyMod$fireGameRenderEvent(Phase.POST, VersionedStackProvider.DEFAULT_STACK, partialTicks);
    }

    private void labyMod$fireGameRenderEvent(Phase phase, Stack stack, float partialTicks) {
        Laby.references().renderEnvironmentContext().screenContext().runInContext(stack, Laby.labyAPI().minecraft().mouse(), partialTicks, context -> {
            Laby.fireEvent(new GameRenderEvent(phase, context));
        });
    }

    @Inject(method = {"toggleFullscreen"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;updateDisplay()V", shift = At.Shift.AFTER)})
    private void labyMod$toggleFullScreen(CallbackInfo ci) {
        if (this.m != null) {
            return;
        }
        Window minecraftWindow = labyMod$getMinecraft().minecraftWindow();
        ((DefaultAbstractWindow) minecraftWindow).resetBounds();
        Laby.fireEvent(new ScreenResizeEvent((int) (this.d / minecraftWindow.getScale()), (int) (this.e / minecraftWindow.getScale()), this.d, this.e));
    }

    @Insert(method = {"getLimitFramerate"}, at = @At("HEAD"), cancellable = true)
    private void labyMod$fireGameFpsLimitEvent(InsertInfoReturnable<Integer> ci) {
        int i;
        GameFpsLimitEventCaller.callEvent(ci);
        if (ci.isCancelled()) {
            return;
        }
        if (this.f == null && this.m != null) {
            i = 60;
        } else {
            i = this.t.g;
        }
        ci.setReturnValue(Integer.valueOf(i));
    }

    @Inject(method = {"createDisplay"}, at = {@At("TAIL")})
    private void labyMod$fireWindowResizeEvent(CallbackInfo ci) {
        Laby.fireEvent(new WindowResizeEvent());
    }

    @Insert(method = {"resize"}, at = @At("TAIL"))
    private void labyMod$fireWindowResizeEvent(int width, int height, InsertInfo ci) {
        Laby.fireEventNextTick(new WindowResizeEvent());
    }

    @Redirect(method = {"runTick"}, at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getEventDWheel()I", remap = false))
    private int labyMod$fireHotbarSlotChangeEvent() {
        int delta = Mouse.getEventDWheel();
        if (delta == 0) {
            return 0;
        }
        int slotDelta = MathHelper.clamp(delta, -1, 1);
        ClientHotbarSlotChangeEvent event = new ClientHotbarSlotChangeEvent(this.h.bi.c, slotDelta);
        int originalToSlot = event.toSlot();
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return 0;
        }
        if (event.toSlot() != originalToSlot && !this.h.v()) {
            this.h.bi.c = MathHelper.clamp(event.toSlot(), 0, 8);
            return 0;
        }
        return delta;
    }

    @ModifyArg(method = {"getProfileProperties"}, at = @At(value = "INVOKE", target = "Lcom/mojang/authlib/minecraft/MinecraftSessionService;fillProfileProperties(Lcom/mojang/authlib/GameProfile;Z)Lcom/mojang/authlib/GameProfile;"), index = 1)
    public boolean labymod$forceSecure(boolean requireSecure) {
        return true;
    }

    private boolean labyMod$fireClientPlayerInteractEvent(ClientPlayerInteractEvent.InteractionType interactionType) {
        return ((ClientPlayerInteractEvent) Laby.fireEvent(new ClientPlayerInteractEvent(this.h, interactionType))).isCancelled();
    }

    private Minecraft labyMod$getMinecraft() {
        return ave.A();
    }
}
