package net.labymod.v1_21_11.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.DisplayData;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.net.Proxy;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.ElementActivity;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.util.Pair;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.client.WindowController;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.event.client.lifecycle.GameInitializeEvent;
import net.labymod.core.main.LabyMod;
import net.labymod.v1_21_11.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_21_11.client.overlay.CustomLoadingOverlay;
import net.labymod.v1_21_11.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.minecraft.SharedConstants;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.User;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.VirtualScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2fStack;
import org.joml.Matrix4fStack;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinMinecraft.class */
@Mixin({Minecraft.class})
public abstract class MixinMinecraft {

    @Shadow
    @Final
    private Window window;

    @Shadow
    @Nullable
    private IntegratedServer singleplayerServer;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Final
    public Options options;

    @Shadow
    private int rightClickDelay;

    @Shadow
    @Final
    public File gameDirectory;

    @Shadow
    @Nullable
    public abstract ClientPacketListener getConnection();

    @Shadow
    @javax.annotation.Nullable
    public abstract ServerData getCurrentServer();

    @Shadow
    public static Minecraft getInstance() {
        return null;
    }

    @Redirect(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/User;getSessionId()Ljava/lang/String;"))
    private String labyMod$censorSessionId(User instance) {
        return "********************************";
    }

    @Redirect(method = {"<init>"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/main/GameConfig$UserData;proxy:Ljava/net/Proxy;"))
    private Proxy labyMod$callGameInitializeResource(GameConfig.UserData userData) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION));
        return userData.proxy;
    }

    @Inject(method = {"resizeDisplay"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setGuiScale(I)V", shift = At.Shift.AFTER)})
    private void labyMod$updateSizeOfAreas(CallbackInfo ci) {
        Laby.fireEventNextTick(new WindowResizeEvent());
    }

    @Redirect(method = {"<init>"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;gameDirectory:Ljava/io/File;", shift = At.Shift.BEFORE, ordinal = 0))
    private File labyMod$onPreGameStarted(GameConfig.FolderData instance) {
        LabyMod.getInstance().onPreGameStarted();
        return instance.gameDirectory;
    }

    @Redirect(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/VirtualScreen;newWindow(Lcom/mojang/blaze3d/platform/DisplayData;Ljava/lang/String;Ljava/lang/String;)Lcom/mojang/blaze3d/platform/Window;"))
    private Window labyMod$adjustWindowSize(VirtualScreen instance, DisplayData displayData, String $$1, String $$2) {
        Pair<Integer, Integer> sizePair = WindowController.calculateNewScreenSize(displayData.width(), displayData.height(), () -> {
            GLFWVidMode glfwVidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            if (glfwVidMode == null) {
                return null;
            }
            return Pair.of(Integer.valueOf(glfwVidMode.width()), Integer.valueOf(glfwVidMode.height()));
        });
        if (sizePair != null) {
            displayData = new DisplayData(((Integer) sizePair.getFirst()).intValue(), ((Integer) sizePair.getSecond()).intValue(), displayData.fullscreenWidth(), displayData.fullscreenHeight(), displayData.isFullscreen());
        }
        return instance.newWindow(displayData, $$1, $$2);
    }

    @Inject(method = {"<init>"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;resizeDisplay()V", shift = At.Shift.BEFORE)})
    private void labyMod$maximizeDisplay(GameConfig $$0, CallbackInfo ci) {
        WindowController.maximize(() -> {
            GLFW.glfwWindowHint(131075, 1);
            GLFW.glfwMaximizeWindow(this.window.handle());
        });
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$firePostGameStartedInitializeEvent(GameConfig param0, CallbackInfo ci) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.POST_GAME_STARTED));
    }

    @Insert(method = {"tick()V"}, at = @At("HEAD"))
    private void labyMod$firePreTickEvent(InsertInfo ci) {
        labyMod$fireTickEvent(Phase.PRE);
    }

    @Insert(method = {"tick()V"}, at = @At("TAIL"))
    private void labyMod$firePostTickEvent(InsertInfo ci) {
        labyMod$fireTickEvent(Phase.POST);
    }

    @WrapOperation(method = {"createTitle"}, at = {@At(value = "INVOKE", target = "Ljava/lang/StringBuilder;toString()Ljava/lang/String;")})
    private String labyMod$modifiedTitle(StringBuilder instance, Operation<String> original) {
        StringBuilder extraBuilder = new StringBuilder();
        ClientPacketListener listener = getConnection();
        if (listener != null && listener.getConnection().isConnected()) {
            extraBuilder.append(" - ");
            ServerData currentServer = getCurrentServer();
            if (this.singleplayerServer != null && !this.singleplayerServer.isPublished()) {
                extraBuilder.append(I18n.get("title.singleplayer", new Object[0]));
            } else if (currentServer != null && currentServer.isRealm()) {
                extraBuilder.append(I18n.get("title.multiplayer.realms", new Object[0]));
            } else if (this.singleplayerServer == null && (currentServer == null || !currentServer.isLan())) {
                extraBuilder.append(I18n.get("title.multiplayer.other", new Object[0]));
            } else {
                extraBuilder.append(I18n.get("title.multiplayer.lan", new Object[0]));
            }
        }
        return LabyMod.getInstance().createTitle(SharedConstants.getCurrentVersion().name(), extraBuilder.toString());
    }

    @Redirect(method = {"setScreen"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;removed()V"))
    private void labyMod$cancelRemovedScreen(Screen screen) {
    }

    @ModifyVariable(method = {"setScreen"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;", ordinal = 2, shift = At.Shift.BEFORE))
    private Screen labyMod$fireScreenOpenEvent(Screen newScreen) {
        Screen previousScreen = Minecraft.getInstance().screen;
        try {
            ScreenInstance instance = Laby.fireEvent(new ScreenDisplayEvent(labyMod$createScreenWrapper(previousScreen), labyMod$createScreenWrapper(newScreen))).getScreen();
            newScreen = instance == null ? null : (Screen) instance.wrap().getVersionedScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (previousScreen != null && newScreen != previousScreen) {
            previousScreen.removed();
        }
        if (newScreen instanceof LabyScreenRenderer) {
            ElementActivity elementActivityScreen = ((LabyScreenRenderer) newScreen).screen();
            if (elementActivityScreen instanceof ElementActivity) {
                ElementActivity activity = elementActivityScreen;
                activity.onOpenScreen();
            }
        }
        return newScreen;
    }

    private ScreenWrapper labyMod$createScreenWrapper(Screen screen) {
        if (screen == null) {
            return null;
        }
        return Laby.references().screenWrapperFactory().create(screen);
    }

    @Insert(method = {"resizeDisplay()V"}, at = @At("TAIL"))
    private void labyMod$fireScreenResizeEvent(InsertInfo ci) {
        Laby.fireEvent(new ScreenResizeEvent(this.window.getGuiScaledWidth(), this.window.getGuiScaledHeight(), this.window.getWidth(), this.window.getHeight()));
    }

    @Redirect(method = {"<init>"}, at = @At(value = "NEW", target = "net/minecraft/client/gui/screens/LoadingOverlay"))
    private LoadingOverlay labyMod$createLoadingOverlay(Minecraft minecraft, ReloadInstance reloadInstance, Consumer onFinish, boolean fadeIn) {
        AppearanceConfig appearance = Laby.labyAPI().config().appearance();
        FadeOutAnimationType type = (FadeOutAnimationType) appearance.fadeOutAnimation().get();
        if (type == FadeOutAnimationType.FADING && !DynamicBackgroundController.isEnabled()) {
            return new LoadingOverlay(minecraft, reloadInstance, onFinish, fadeIn);
        }
        return new CustomLoadingOverlay(minecraft, reloadInstance, onFinish, fadeIn);
    }

    @Inject(method = {"disconnect(Lnet/minecraft/client/gui/screens/Screen;ZZ)V"}, at = {@At("HEAD")})
    private void labyMod$firePreNetworkDisconnectEvent(CallbackInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.PRE);
    }

    @Inject(method = {"disconnect(Lnet/minecraft/client/gui/screens/Screen;ZZ)V"}, at = {@At("TAIL")})
    private void labyMod$firePostNetworkDisconnectEvent(CallbackInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.POST);
    }

    @Inject(method = {"setLevel"}, at = {@At("HEAD")})
    private void labyMod$firePreWorldLoadEvent(ClientLevel $$0, CallbackInfo ci) {
        if ($$0 != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.PRE));
        }
    }

    @Inject(method = {"setLevel"}, at = {@At("TAIL")})
    private void labyMod$firePostWorldLoadEvent(ClientLevel $$0, CallbackInfo ci) {
        if ($$0 != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.POST));
        }
    }

    @Redirect(method = {"runTick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V"))
    private void labyMod$fireGameRenderEvent(GameRenderer gameRenderer, DeltaTracker deltaTracker, boolean running) {
        Stack stack = Stack.create(new JomlMatrix3x2fStackProvider(new Matrix3x2fStack(128)));
        stack.push();
        float realtimeDeltaTicks = deltaTracker.getRealtimeDeltaTicks();
        labyMod$fireGameRenderEvent(Phase.PRE, stack, realtimeDeltaTicks);
        gameRenderer.render(deltaTracker, running);
        labyMod$fireGameRenderEvent(Phase.POST, stack, realtimeDeltaTicks);
        stack.pop();
    }

    private void labyMod$fireGameRenderEvent(Phase phase, Stack stack, float partialTicks) {
        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        modelViewStack.translate(0.0f, 0.0f, -11000.0f);
        Laby.references().renderEnvironmentContext().screenContext().runInContext(stack, Laby.labyAPI().minecraft().mouse(), partialTicks, context -> {
            Laby.fireEvent(new GameRenderEvent(phase, context));
        });
        modelViewStack.popMatrix();
    }

    private void labyMod$fireTickEvent(Phase phase) {
        Laby.fireEvent(new GameTickEvent(phase));
    }
}

