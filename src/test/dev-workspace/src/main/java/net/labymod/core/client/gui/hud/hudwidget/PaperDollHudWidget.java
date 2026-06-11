package net.labymod.core.client.gui.hud.hudwidget;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.EntityRenderBuilder;
import net.labymod.api.client.entity.EntityVisualizer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.ResizeableHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.gui.hud.hudwidget.background.HudWidgetBackgroundRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.DefaultModelBuffer;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.settings.annotation.SettingOrder;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.mojang.model.MojangModelService;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/PaperDollHudWidget.class */
@SpriteSlot(x = 6, y = 4)
@IntroducedIn("4.1.0")
public class PaperDollHudWidget extends ResizeableHudWidget<PaperDollHudWidgetConfig> implements HudWidgetBackgroundRenderer {
    private DefaultModelBuffer modelBuffer;
    private long lastInteraction;

    public PaperDollHudWidget() {
        super("paper_doll", PaperDollHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
        updateDummyModel(Laby.labyAPI().getUniqueId());
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.ResizeableHudWidget
    public void render(ScreenContext context, boolean isEditorContext, float width, float height) {
        renderEntireBackground(context, width, height);
        float margin = ((PaperDollHudWidgetConfig) this.config).background().getMargin();
        float padding = ((PaperDollHudWidgetConfig) this.config).background().getPadding();
        Player player = getTargetPlayer();
        if (this.modelBuffer == null && player == null) {
            renderPlaceholder(context, 0.0f, 0.0f, width, height);
        } else {
            boolean isRightAligned = anchor().isRight();
            renderPaperDoll(context, player, width / 2.0f, ((height - margin) - padding) - 2.0f, 0.0f, ((PaperDollHudWidgetConfig) this.config).rotation().get().intValue() * (isRightAligned ? 1 : -1), 0.0f, ((height - (margin * 2.0f)) - (padding * 2.0f)) / 2.0f, ((PaperDollHudWidgetConfig) this.config).showName().get().booleanValue(), ((PaperDollHudWidgetConfig) this.config).headRotationStrength().get().floatValue());
        }
    }

    private void renderPaperDoll(ScreenContext context, Player player, float x, float y, float rotationX, float rotationY, float rotationZ, float scale, boolean renderName, float headRotationStrength) {
        if (player == null) {
            float modifiedScale = scale - 2.0f;
            this.modelBuffer.submitToCanvas(context, 0.0f, 0.0f, getWidth(), getHeight(), modifiedScale, offscreenStack -> {
                offscreenStack.rotate(rotationY, 0.0f, 1.0f, 0.0f);
            });
            if (((PaperDollHudWidgetConfig) this.config).showName().get().booleanValue()) {
                renderNameTag(context, x, -15.0f, modifiedScale / 38.0f);
                return;
            }
            return;
        }
        EntityVisualizer visualizer = Laby.references().entityVisualizer();
        EntityRenderBuilder builder = visualizer.entity(player);
        builder.position(x, y).rotation(rotationX, rotationY, rotationZ).scale(scale).headRotationStrength(headRotationStrength).noClip();
        if (renderName) {
            builder.withName();
        }
        builder.submit(context);
    }

    private void renderNameTag(ScreenContext context, float x, float y, float scale) {
        Stack stack = context.stack();
        ScreenCanvas canvas = context.canvas();
        stack.push();
        Component component = Component.text(Laby.labyAPI().getName());
        float componentWidth = canvas.getTextWidth(component);
        stack.translate(this.width / 2.0f, 0.0f, 0.0f);
        stack.scale(scale, scale, 1.0f);
        stack.translate((-this.width) / 2.0f, 0.0f, 0.0f);
        canvas.submitAbsoluteRect(((this.width / 2.0f) - (componentWidth / 2.0f)) - 2.0f, y, (this.width / 2.0f) + (componentWidth / 2.0f) + 2.0f, y + 11.0f, 1073741824);
        canvas.submitComponent(component, x, y + 2.0f, -1, 2);
        stack.pop();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return !((PaperDollHudWidgetConfig) this.config).autoHide().get().booleanValue() || TimeUtil.getMillis() - this.lastInteraction <= ((long) ((((PaperDollHudWidgetConfig) this.config).visibleDuration().get().intValue() * SubmissionOrders.DEBUG) + 100));
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        super.onTick(isEditorContext);
        Player player = getTargetPlayer();
        if (player == null) {
            return;
        }
        if (player.entityPose() != EntityPose.STANDING || player.isOnFire() || player.getVehicle() != null || player.isSprinting()) {
            this.lastInteraction = TimeUtil.getMillis();
        }
        EmoteAnimationStorage storage = LabyMod.references().emoteService().getAnimationStorage(player);
        if (storage != null && storage.isPlaying()) {
            this.lastInteraction = TimeUtil.getMillis();
        }
    }

    @Subscribe
    public void onSessionUpdate(SessionUpdateEvent event) {
        ThreadSafe.executeOnRenderThread(() -> {
            updateDummyModel(event.newSession().getUniqueId());
        });
    }

    private void updateDummyModel(UUID uniqueId) {
        ResourcesReloadWatcher watcher = Laby.references().resourcesReloadWatcher();
        watcher.addOrExecuteInitializeListener(() -> {
            updateDummyModel0(uniqueId);
        });
    }

    private void updateDummyModel0(UUID uniqueId) {
        MojangTextureService textureService = Laby.references().mojangTextureService();
        MojangModelService modelService = Laby.references().mojangModelService();
        textureService.getTexture(uniqueId, MojangTextureType.SKIN, resourceLocation -> {
            MinecraftServices.SkinVariant variant = textureService.getVariant(resourceLocation);
            Model model = modelService.getPlayerModel(variant);
            this.modelBuffer = new DefaultModelBuffer(model, true);
            this.modelBuffer.setResourceLocation(resourceLocation);
        });
    }

    private void renderPlaceholder(ScreenContext context, float x, float y, float width, float height) {
        ScreenCanvas renderState = context.canvas();
        renderState.submitRelativeRect(x, y, width, height, Integer.MIN_VALUE);
        renderState.submitText("PAPER", x + (width / 2.0f), (y + (height / 2.0f)) - 5.0f, -1, 0.5f, 3);
        renderState.submitText("DOLL", x + (width / 2.0f), y + (height / 2.0f), -1, 0.5f, 3);
    }

    private Player getTargetPlayer() {
        Entity cameraEntity = this.labyAPI.minecraft().getCameraEntity();
        if (cameraEntity instanceof Player) {
            return (Player) cameraEntity;
        }
        return this.labyAPI.minecraft().getClientPlayer();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.background.HudWidgetBackgroundRenderer
    public BackgroundConfig config() {
        return ((PaperDollHudWidgetConfig) this.config).background();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/PaperDollHudWidget$PaperDollHudWidgetConfig.class */
    public static class PaperDollHudWidgetConfig extends ResizeableHudWidget.ResizeableHudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showName;

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> autoHide;

        @SliderWidget.SliderSetting(min = 0.0f, max = 5.0f, steps = 1.0f)
        @SettingRequires("autoHide")
        private final ConfigProperty<Integer> visibleDuration;

        @SliderWidget.SliderSetting(min = -180.0f, max = 180.0f, steps = 10.0f)
        private final ConfigProperty<Integer> rotation;

        @SliderWidget.SliderSetting(min = 0.0f, max = 1.0f, steps = 0.05f)
        private final ConfigProperty<Float> headRotationStrength;

        @SettingOrder(10)
        @CustomTranslation("labymod.hudWidget.background")
        @SettingRequires(value = "useGlobal", invert = true)
        private BackgroundConfig background;

        public PaperDollHudWidgetConfig() {
            super(20.0f, 50.0f, 100.0f, 20.0f, 80.0f, 100.0f);
            this.showName = new ConfigProperty<>(false);
            this.autoHide = new ConfigProperty<>(false);
            this.visibleDuration = new ConfigProperty<>(2);
            this.rotation = new ConfigProperty<>(20);
            this.headRotationStrength = new ConfigProperty<>(Float.valueOf(0.5f));
            this.background = new BackgroundConfig();
        }

        public ConfigProperty<Boolean> showName() {
            return this.showName;
        }

        public ConfigProperty<Boolean> autoHide() {
            return this.autoHide;
        }

        public ConfigProperty<Integer> visibleDuration() {
            return this.visibleDuration;
        }

        public ConfigProperty<Integer> rotation() {
            return this.rotation;
        }

        public ConfigProperty<Float> headRotationStrength() {
            return this.headRotationStrength;
        }

        public BackgroundConfig background() {
            return (BackgroundConfig) config((v0) -> {
                return v0.background();
            }, this.background);
        }
    }
}
