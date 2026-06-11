package net.labymod.core.client.gui.screen.activity.activities.ingame.interaction;

import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import net.labymod.api.client.entity.player.interaction.InteractionMenuRegistry;
import net.labymod.api.client.gfx.pipeline.post.processors.PostProcessors;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.AbstractInteractionOverlayActivity;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ModelWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.entity.HumanoidModel;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.labymod.AutoTextConfigAccessor;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.labynet.models.SocialMediaEntry;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.entity.player.interaction.autotext.AutoTextBulletPoint;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet.BulletPointRendererWidget;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.bullet.BulletPointWidget;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.social.SocialPointRendererWidget;
import net.labymod.core.client.gui.screen.widget.widgets.interaction.social.SocialPointWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/interaction/InteractionMenuOverlay.class */
@AutoActivity
@Link("activity/overlay/interaction-menu.lss")
public class InteractionMenuOverlay extends AbstractInteractionOverlayActivity implements InteractionAnimationController {
    private static final float OPEN_DURATION = 100.0f;
    private static final float COLLAPSE_DURATION = 300.0f;
    private final InteractionMenuRegistry registry;
    private final Model headModel;
    private final AnimationController animationController;
    private final InteractionMenuKeyListener interactionMenuKeyListener;
    private Player player;
    private DivWidget wrapperWidget;
    private BulletPointRendererWidget rendererWidget;
    private ModelWidget headWidget;
    private SocialPointRendererWidget socialBarWidget;
    private boolean focusSocialBar;
    private long timeSocialBarTransitionStart;

    @Inject
    public InteractionMenuOverlay() {
        super(Laby.references().cameraLockController());
        this.registry = Laby.references().interactionMenuRegistry();
        ModelService modelService = Laby.references().modelService();
        this.headModel = modelService.loadBlockBenchModel(Constants.Resources.MODEL_HEAD);
        this.animationController = modelService.createAnimationController();
        this.interactionMenuKeyListener = new InteractionMenuKeyListener(this);
        this.labyAPI.eventBus().registerListener(this.interactionMenuKeyListener);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        super.onOpenScreen();
        this.labyAPI.eventBus().registerListener(this.interactionMenuKeyListener);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        this.labyAPI.eventBus().unregisterListener(this.interactionMenuKeyListener);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractInteractionOverlayActivity
    public void closeInteraction() {
        super.closeInteraction();
        this.player = null;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        if (this.player == null) {
            return;
        }
        super.tick();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (this.player == null) {
            return;
        }
        this.wrapperWidget = new DivWidget();
        this.wrapperWidget.addId("wrapper");
        List<BulletPoint> bulletPoints = this.registry.values();
        bulletPoints.removeIf(bulletPoint -> {
            return !bulletPoint.isVisible(this.player);
        });
        AutoTextConfigAccessor autoTextConfig = Laby.references().chatProvider().autoTextConfigAccessor();
        for (AutoTextEntry entry : autoTextConfig.getEntries()) {
            if (entry.displayInInteractionMenu().get().booleanValue() && entry.isActiveOnCurrentServer()) {
                bulletPoints.add(new AutoTextBulletPoint(entry));
            }
        }
        this.rendererWidget = new BulletPointRendererWidget(this, bulletPoints);
        this.rendererWidget.addId("bullet-point-renderer");
        this.wrapperWidget.addChild(this.rendererWidget);
        ClientPacketListener listener = this.labyAPI.minecraft().getClientPacketListener();
        CompletableResourceLocation location = null;
        if (listener != null) {
            UUID id = this.player.profile().getUniqueId();
            NetworkPlayerInfo info = listener.getNetworkPlayerInfo(id);
            if (info != null) {
                location = info.getSkin().getCompletableSkinTexture();
            }
        }
        this.headWidget = new ModelWidget(ResourceLocation.create("labymod", "head_model"), this.headModel, this.animationController, 8.0f, 8.0f);
        if (location != null) {
            this.headWidget.setResourceLocation(location.getCompleted());
        }
        this.headWidget.addId(HumanoidModel.HEAD_NAME);
        ComponentWidget nameWidget = ComponentWidget.text(this.player.getName());
        nameWidget.addId("name");
        this.headWidget.addChild(nameWidget);
        this.wrapperWidget.addChild(this.headWidget);
        this.socialBarWidget = new SocialPointRendererWidget(this, this.player);
        this.socialBarWidget.addId("social-bar");
        this.wrapperWidget.addChild(this.socialBarWidget);
        ((Document) this.document).addChild(this.wrapperWidget);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractInteractionOverlayActivity, net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        PostProcessors.processMenuBlur(MenuBlurConfig.ScreenType.INTERACTION_WHEEL, context);
        super.render(context);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractInteractionOverlayActivity
    public void renderInteractionOverlay(ScreenContext context) {
        Bounds bounds = bounds();
        MutableMouse mouse = context.mouse();
        float mouseOffsetX = mouse.getX() - bounds.getCenterX();
        float mouseOffsetY = mouse.getY() - bounds.getCenterY();
        float rotationX = (float) Math.toRadians(MathHelper.clamp(mouseOffsetY, -80.0f, 80.0f));
        float rotationY = (float) Math.toRadians(MathHelper.clamp(mouseOffsetX, -80.0f, 80.0f));
        float scale = this.rendererWidget == null ? 0.0f : getOpenProgress();
        ModelPart head = this.headModel.getPart(HumanoidModel.HEAD_NAME);
        Transformation headTransform = head.getAnimationTransformation();
        headTransform.setScale(scale);
        headTransform.setRotation(rotationX, -rotationY, 0.0f);
        if (this.headWidget != null) {
            this.headWidget.setScale(scale);
        }
        boolean inSocialTriggerZone = mouseOffsetY > getRadius() - 10.0f;
        if (inSocialTriggerZone && !this.focusSocialBar) {
            this.focusSocialBar = true;
            this.timeSocialBarTransitionStart = TimeUtil.getMillis();
        }
        if (mouseOffsetY < 0.0f && this.focusSocialBar) {
            this.focusSocialBar = false;
            this.timeSocialBarTransitionStart = TimeUtil.getMillis();
        }
    }

    private boolean openInteraction(Player player) {
        this.player = player;
        return super.openInteraction();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractInteractionOverlayActivity
    protected float getRadius() {
        return this.wrapperWidget.bounds().getHeight() / 2.0f;
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.interaction.InteractionAnimationController
    public float getOpenProgress() {
        long timePassed = TimeUtil.getMillis() - this.rendererWidget.getLastInitialTime();
        return ((float) (-Math.exp((-timePassed) / OPEN_DURATION))) + 1.0f;
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.interaction.InteractionAnimationController
    public float getSocialTransitionProgress() {
        long timePassedSinceHiddenChanged = TimeUtil.getMillis() - this.timeSocialBarTransitionStart;
        float hideProgress = MathHelper.clamp(timePassedSinceHiddenChanged / COLLAPSE_DURATION, 0.0f, 1.0f);
        return (float) CubicBezier.EASE_IN_OUT.curve(this.focusSocialBar ? hideProgress : 1.0f - hideProgress);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.ingame.interaction.InteractionAnimationController
    public float getBulletRotationOffset(BulletPointWidget.Side side) {
        float size = this.socialBarWidget.getChildren().size() / 10.0f;
        float offset = (float) (0.7853981633974483d * ((double) size));
        float progress = offset * getSocialTransitionProgress();
        return side == BulletPointWidget.Side.LEFT ? progress : -progress;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/interaction/InteractionMenuOverlay$InteractionMenuKeyListener.class */
    public static class InteractionMenuKeyListener {
        private final InteractionMenuOverlay overlay;
        private final LabyAPI labyAPI = Laby.labyAPI();

        public InteractionMenuKeyListener(InteractionMenuOverlay overlay) {
            this.overlay = overlay;
        }

        @Subscribe
        public void onKey(KeyEvent event) {
            if (!this.labyAPI.minecraft().isMouseLocked() || event.key() != this.labyAPI.config().hotkeys().interactionMenuKey().get()) {
                return;
            }
            if (event.state() == KeyEvent.State.PRESS) {
                Entity targetEntity = this.labyAPI.minecraft().getTargetEntity();
                if (!(targetEntity instanceof Player)) {
                    return;
                }
                Player player = (Player) targetEntity;
                this.overlay.openInteraction(player);
                PostProcessors.resetMenuBlur();
            }
            if (this.overlay.isInteractionOpen() && event.state() == KeyEvent.State.UNPRESSED) {
                BulletPointWidget bulletPoint = this.overlay.rendererWidget.getNearestPoint();
                if (bulletPoint == null) {
                    SocialPointWidget socialPoint = this.overlay.socialBarWidget.getNearestPoint();
                    if (socialPoint != null) {
                        SocialMediaEntry entry = socialPoint.entry();
                        if (entry.getService().equals("discord")) {
                            socialPoint.entry().copyName();
                        } else {
                            socialPoint.entry().open();
                        }
                    }
                } else {
                    bulletPoint.bulletPoint().execute(this.overlay.player);
                }
                this.overlay.closeInteraction();
            }
        }
    }
}
