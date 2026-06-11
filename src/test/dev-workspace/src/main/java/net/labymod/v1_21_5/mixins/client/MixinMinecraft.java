package net.labymod.v1_21_5.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.net.Proxy;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
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
import net.labymod.v1_21_5.client.gui.screen.LabyScreenRenderer;
import net.labymod.v1_21_5.client.overlay.CustomLoadingOverlay;
import org.jetbrains.annotations.Nullable;
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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/mixins/client/MixinMinecraft.class */
@Mixin({fqq.class})
public abstract class MixinMinecraft {

    @Shadow
    @Final
    private fki S;

    @Shadow
    @Nullable
    private hpb aS;

    @Shadow
    @Nullable
    public gqm t;

    @Shadow
    @Final
    public fqu n;

    @Shadow
    private int aV;

    @Shadow
    @Final
    public File q;

    @Shadow
    @Nullable
    public abstract glp L();

    @Shadow
    @javax.annotation.Nullable
    public abstract gmd S();

    @Shadow
    public static fqq Q() {
        return null;
    }

    @Redirect(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/User;getSessionId()Ljava/lang/String;"))
    private String labyMod$censorSessionId(frc instance) {
        return "********************************";
    }

    @Redirect(method = {"<init>"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/main/GameConfig$UserData;proxy:Ljava/net/Proxy;"))
    private Proxy labyMod$callGameInitializeResource(d userData) {
        Laby.fireEvent(new GameInitializeEvent(GameInitializeEvent.Lifecycle.RESOURCE_INITIALIZATION));
        return userData.d;
    }

    @Inject(method = {"resizeDisplay"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setGuiScale(D)V", shift = At.Shift.AFTER)})
    private void labyMod$updateSizeOfAreas(CallbackInfo ci) {
        Laby.fireEventNextTick(new WindowResizeEvent());
    }

    @Redirect(method = {"<init>"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;gameDirectory:Ljava/io/File;", shift = At.Shift.BEFORE, ordinal = 0))
    private File labyMod$onPreGameStarted(a instance) {
        LabyMod.getInstance().onPreGameStarted();
        return instance.a;
    }

    @Redirect(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/VirtualScreen;newWindow(Lcom/mojang/blaze3d/platform/DisplayData;Ljava/lang/String;Ljava/lang/String;)Lcom/mojang/blaze3d/platform/Window;"))
    private fki labyMod$adjustWindowSize(gsm instance, fjx displayData, String $$1, String $$2) {
        Pair<Integer, Integer> sizePair = WindowController.calculateNewScreenSize(displayData.a(), displayData.b(), () -> {
            GLFWVidMode glfwVidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            if (glfwVidMode == null) {
                return null;
            }
            return Pair.of(Integer.valueOf(glfwVidMode.width()), Integer.valueOf(glfwVidMode.height()));
        });
        if (sizePair != null) {
            displayData = new fjx(sizePair.getFirst().intValue(), sizePair.getSecond().intValue(), displayData.c(), displayData.d(), displayData.e());
        }
        return instance.a(displayData, $$1, $$2);
    }

    @Inject(method = {"<init>"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;resizeDisplay()V", shift = At.Shift.BEFORE)})
    private void labyMod$maximizeDisplay(gfs $$0, CallbackInfo ci) {
        WindowController.maximize(() -> {
            GLFW.glfwWindowHint(131075, 1);
            GLFW.glfwMaximizeWindow(this.S.h());
        });
    }

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void labyMod$firePostGameStartedInitializeEvent(gfs param0, CallbackInfo ci) {
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
        glp listener = L();
        if (listener != null && listener.j().i()) {
            extraBuilder.append(" - ");
            gmd currentServer = S();
            if (this.aS != null && !this.aS.r()) {
                extraBuilder.append(hly.a("title.singleplayer", new Object[0]));
            } else if (currentServer != null && currentServer.e()) {
                extraBuilder.append(hly.a("title.multiplayer.realms", new Object[0]));
            } else if (this.aS == null && (currentServer == null || !currentServer.d())) {
                extraBuilder.append(hly.a("title.multiplayer.other", new Object[0]));
            } else {
                extraBuilder.append(hly.a("title.multiplayer.lan", new Object[0]));
            }
        }
        return LabyMod.getInstance().createTitle(ac.b().c(), extraBuilder.toString());
    }

    @Redirect(method = {"setScreen"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;removed()V"))
    private void labyMod$cancelRemovedScreen(fzq screen) {
    }

    @ModifyVariable(method = {"setScreen"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;screen:Lnet/minecraft/client/gui/screens/Screen;", ordinal = 2, shift = At.Shift.BEFORE))
    private fzq labyMod$fireScreenOpenEvent(fzq newScreen) {
        fzq previousScreen = fqq.Q().z;
        try {
            ScreenInstance instance = ((ScreenDisplayEvent) Laby.fireEvent(new ScreenDisplayEvent(labyMod$createScreenWrapper(previousScreen), labyMod$createScreenWrapper(newScreen)))).getScreen();
            newScreen = instance == null ? null : (fzq) instance.wrap().getVersionedScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (previousScreen != null && newScreen != previousScreen) {
            previousScreen.aF_();
        }
        if (newScreen instanceof LabyScreenRenderer) {
            LabyScreen labyScreenScreen = ((LabyScreenRenderer) newScreen).screen();
            if (labyScreenScreen instanceof ElementActivity) {
                ElementActivity activity = (ElementActivity) labyScreenScreen;
                activity.onOpenScreen();
            }
        }
        return newScreen;
    }

    private ScreenWrapper labyMod$createScreenWrapper(fzq screen) {
        if (screen == null) {
            return null;
        }
        return Laby.references().screenWrapperFactory().create(screen);
    }

    @Insert(method = {"resizeDisplay()V"}, at = @At("TAIL"))
    private void labyMod$fireScreenResizeEvent(InsertInfo ci) {
        Laby.fireEvent(new ScreenResizeEvent(this.S.o(), this.S.p(), this.S.k(), this.S.l()));
    }

    @Redirect(method = {"<init>"}, at = @At(value = "NEW", target = "net/minecraft/client/gui/screens/LoadingOverlay"))
    private fzg labyMod$createLoadingOverlay(fqq minecraft, avk reloadInstance, Consumer onFinish, boolean fadeIn) {
        AppearanceConfig appearance = Laby.labyAPI().config().appearance();
        FadeOutAnimationType type = appearance.fadeOutAnimation().get();
        if (type == FadeOutAnimationType.FADING && !DynamicBackgroundController.isEnabled()) {
            return new fzg(minecraft, reloadInstance, onFinish, fadeIn);
        }
        return new CustomLoadingOverlay(minecraft, reloadInstance, onFinish, fadeIn);
    }

    @Insert(method = {"disconnect(Lnet/minecraft/client/gui/screens/Screen;Z)V"}, at = @At("HEAD"))
    private void labyMod$firePreNetworkDisconnectEvent(fzq screen, boolean transferring, InsertInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.PRE);
    }

    @Insert(method = {"disconnect(Lnet/minecraft/client/gui/screens/Screen;Z)V"}, at = @At("TAIL"))
    private void labyMod$firePostNetworkDisconnectEvent(fzq screen, boolean transferring, InsertInfo ci) {
        Laby.labyAPI().serverController().disconnect(Phase.POST);
    }

    @Insert(method = {"setLevel"}, at = @At("HEAD"))
    private void labyMod$firePreWorldLoadEvent(glo level, a reason, InsertInfo ci) {
        if (level != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.PRE));
        }
    }

    @Insert(method = {"setLevel"}, at = @At("TAIL"))
    private void labyMod$firePostWorldLoadEvent(glo level, a reason, InsertInfo ci) {
        if (level != null) {
            Laby.fireEvent(new WorldLoadEvent(Phase.POST));
        }
    }

    @Redirect(method = {"runTick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V"))
    private void labyMod$fireGameRenderEvent(grd gameRenderer, fqg deltaTracker, boolean running) {
        Stack stack = new fld().stack();
        float realtimeDeltaTicks = deltaTracker.b();
        labyMod$fireGameRenderEvent(Phase.PRE, stack, realtimeDeltaTicks);
        gameRenderer.a(deltaTracker, running);
        labyMod$fireGameRenderEvent(Phase.POST, stack, realtimeDeltaTicks);
    }

    private void labyMod$fireGameRenderEvent(Phase phase, Stack stack, float partialTicks) {
        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        modelViewStack.translate(0.0f, 0.0f, -2000.0f);
        Laby.references().renderEnvironmentContext().screenContext().runInContext(stack, Laby.labyAPI().minecraft().mouse(), partialTicks, context -> {
            Laby.fireEvent(new GameRenderEvent(phase, context));
        });
        modelViewStack.popMatrix();
    }

    private void labyMod$fireTickEvent(Phase phase) {
        Laby.fireEvent(new GameTickEvent(phase));
    }
}
