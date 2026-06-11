package net.labymod.core.client.gui.screen.widget.widgets.title;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.post.processors.PostProcessors;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.gui.screen.title.TitleScreenRenderEvent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.core.client.gui.screen.widget.widgets.title.background.PanoramaWidget;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.MinecraftLogoWidget;
import net.labymod.core.client.gui.screen.widget.widgets.title.overlay.MainOverlayWidget;
import net.labymod.core.client.gui.screen.widget.widgets.title.ui.MainMenuButtonsWidget;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.util.camera.spline.position.Location;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/MainMenuWidget.class */
@AutoWidget
public class MainMenuWidget extends AbstractWidget<Widget> {
    private final AbstractBootLogoRenderer logo;
    private final DynamicBackgroundController background;
    private final boolean fadeIn;
    private long timeFadeStart;
    private DivWidget opacityGroupWidget;
    private MainOverlayWidget overlayWidget;

    public MainMenuWidget(AbstractBootLogoRenderer logo, DynamicBackgroundController background, boolean fadeIn) {
        this.logo = logo;
        this.background = background;
        this.fadeIn = fadeIn;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        boolean dynamicBackgroundEnabled = DynamicBackgroundController.isEnabled();
        if (!dynamicBackgroundEnabled) {
            PanoramaWidget panoramaWidget = new PanoramaWidget();
            panoramaWidget.addId("panorama");
            addChild(panoramaWidget);
        }
        this.opacityGroupWidget = new DivWidget();
        this.opacityGroupWidget.addId("ui");
        MainMenuButtonsWidget buttonsWidget = new MainMenuButtonsWidget();
        buttonsWidget.addId("buttons");
        this.opacityGroupWidget.addChild(buttonsWidget);
        MinecraftLogoWidget logoWidget = MinecraftLogoWidget.create();
        logoWidget.addId("logo");
        this.opacityGroupWidget.addChild(logoWidget);
        this.overlayWidget = new MainOverlayWidget();
        this.overlayWidget.addId("overlay");
        this.opacityGroupWidget.addChild(this.overlayWidget);
        addChild(this.opacityGroupWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        Bounds bounds = bounds();
        float left = bounds.getLeft();
        float top = bounds.getTop();
        float right = bounds.getRight();
        float bottom = bounds.getBottom();
        if (!DynamicBackgroundController.isEnabled()) {
            if (this.fadeIn) {
                updateFadeIn();
            }
            super.renderWidget(context);
            return;
        }
        Stack stack = context.stack();
        float tickDelta = context.getTickDelta();
        this.background.renderTick(stack, left, top, right, bottom, tickDelta);
        if (this.background.isLoaded() && this.background.isOpeningPlayed() && this.background.isInTransition()) {
            this.background.renderUIInWorld(stack, left, top, right, bottom, () -> {
                this.logo.renderForeground(context, left, top, right, bottom, false);
            });
        } else {
            this.logo.renderForeground(context, left, top, right, bottom, true);
        }
        this.background.render(context, left, top, right, bottom);
        PostProcessors.processMenuBlur(MenuBlurConfig.ScreenType.TITLE_SCREEN, context);
        if (this.background.isOpeningPlayed()) {
            transformUI(stack, left, top, right, bottom, () -> {
                stack.push();
                MutableMouse mouse = context.mouse();
                Laby.fireEvent(new TitleScreenRenderEvent(Phase.PRE, stack, mouse, tickDelta));
                super.renderWidget(context);
                Laby.fireEvent(new TitleScreenRenderEvent(Phase.POST, stack, mouse, tickDelta));
                stack.pop();
            });
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (DynamicBackgroundController.isEnabled() && this.background.isLoaded() && this.background.isOpeningPlayed() && this.background.isOpeningPlaying() && this.background.isInTransition() && this.background.getTransitionProgress() < 0.6d) {
            return true;
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    private void updateFadeIn() {
        if (this.timeFadeStart == 0 && this.fadeIn) {
            this.timeFadeStart = TimeUtil.getMillis();
        }
        float timePassed = (TimeUtil.getMillis() - this.timeFadeStart) / 1000.0f;
        float opacity = MathHelper.clamp(timePassed - 1.0f, 0.0f, 1.0f);
        this.opacityGroupWidget.opacity().set(Float.valueOf(opacity));
    }

    private void transformUI(Stack stack, float left, float top, float right, float bottom, Runnable runnable) {
        if (!this.background.isOpeningPlaying()) {
            runnable.run();
            return;
        }
        CinematicCamera camera = this.background.getSchematicRenderer().camera();
        Location titleScreen = DynamicBackgroundController.POS_TITLE_SCREEN;
        Location position = camera.location();
        float distance = (float) Math.sqrt(Math.pow(titleScreen.getX() - position.getX(), 2.0d) + Math.pow(titleScreen.getY() - position.getY(), 2.0d) + Math.pow(titleScreen.getZ() - position.getZ(), 2.0d));
        if (distance == 0.0f) {
            runnable.run();
            return;
        }
        float width = right - left;
        float height = bottom - top;
        float fadeInProgress = (float) Math.max(CubicBezier.EASE_IN_OUT.curve(((distance / 100.0f) - 0.05f) + 0.1f) - 0.004999999888241291d, 0.0d);
        float scale = MathHelper.lerp(5.0f, 1.0f, fadeInProgress);
        stack.push();
        stack.translate(width / 2.0f, height / 2.0f, 0.0f);
        stack.scale(scale, scale, 1.0f);
        float rotX = ((float) (titleScreen.getPitch() - position.getPitch())) * fadeInProgress;
        float rotY = ((float) (titleScreen.getYaw() - position.getYaw())) * fadeInProgress;
        float rotZ = ((float) (titleScreen.getRoll() - position.getRoll())) * fadeInProgress;
        stack.rotate(rotZ, 0.0f, 0.0f, 1.0f);
        stack.rotate(rotX, 1.0f, 0.0f, 0.0f);
        stack.rotate(rotY, 0.0f, 1.0f, 0.0f);
        stack.translate((-width) / 2.0f, (-height) / 2.0f, 0.0f);
        float alpha = Math.max(1.0f - (fadeInProgress * 3.0f), 0.0f);
        this.labyAPI.renderPipeline().setAlpha(alpha, runnable);
        stack.pop();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        this.background.tick();
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        super.dispose();
        if (this.background != null) {
            this.background.dispose();
        }
    }

    public DynamicBackgroundController world() {
        return this.background;
    }

    public AbstractBootLogoRenderer logo() {
        return this.logo;
    }

    public void onLabyConnectPlay() {
        if (this.overlayWidget == null) {
            return;
        }
        this.overlayWidget.reInitialize();
    }
}
