package net.labymod.core.client.gui.hud.hudwidget;

import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.util.Color;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/DangerWarnerHudWidget.class */
@SpriteSlot(x = 0, y = 6)
@IntroducedIn("4.3.0")
@PermissionRequired("danger_warner")
public class DangerWarnerHudWidget extends HudWidget<DangerWarnerHudWidgetConfig> {
    private static final long TRANSITION_DURATION = 300;
    private boolean hasTarget;
    private double targetX;
    private double targetY;
    private double targetZ;
    private boolean previousHasTarget;
    private long timeLastChange;

    public DangerWarnerHudWidget() {
        super("danger_warner", DangerWarnerHudWidgetConfig.class);
        bindCategory(HudWidgetCategory.INGAME);
        bindDropzones(NamedHudWidgetDropzones.DANGER_WARNER);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void render(ScreenContext context, boolean isEditorContext, HudSize size) {
        float degree;
        float strength;
        float width = size.getActualWidth();
        float height = size.getActualHeight();
        float centerX = width / 2.0f;
        float centerY = height / 2.0f;
        int color = ((DangerWarnerHudWidgetConfig) this.config).color().get() & 16777215;
        int range = ((DangerWarnerHudWidgetConfig) this.config).range();
        if (isEditorContext) {
            MutableMouse mouse = context.mouse();
            int targetX = mouse.getX();
            int targetY = mouse.getY();
            float targetXDiff = targetX - centerX;
            float targetYDiff = targetY - centerY;
            degree = (float) Math.toDegrees(Math.atan2(targetYDiff, targetXDiff));
            strength = 1.0f - MathHelper.clamp(((float) Math.sqrt(Math.pow(targetXDiff, 2.0d) + Math.pow(targetYDiff, 2.0d))) / 100.0f, 0.0f, 0.8f);
        } else {
            float progress = getTransitionProgress();
            MinecraftCamera camera = this.labyAPI.minecraft().getCamera();
            DoubleVector3 cameraPosition = camera.renderPosition();
            double cameraX = cameraPosition.getX();
            double cameraY = cameraPosition.getY();
            double cameraZ = cameraPosition.getZ();
            double targetXDiff2 = this.targetX - cameraX;
            double targetYDiff2 = this.targetY - cameraY;
            double targetZDiff = this.targetZ - cameraZ;
            degree = (((float) Math.toDegrees(Math.atan2(targetZDiff, targetXDiff2))) - camera.getYaw()) + 180.0f;
            float strength2 = 1.0f - MathHelper.clamp(((float) Math.sqrt((Math.pow(targetXDiff2, 2.0d) + Math.pow(targetYDiff2, 2.0d)) + Math.pow(targetZDiff, 2.0d))) / range, 0.0f, 1.0f);
            strength = strength2 * MathHelper.lerp(this.hasTarget ? 1.0f : 0.0f, this.previousHasTarget ? 1.0f : 0.0f, progress);
        }
        if (strength <= 0.0f) {
            return;
        }
        float innerRadius = (width / 2.0f) - (10.0f * strength);
        float outerRadius = width / 2.0f;
        int minTiles = 30;
        int maxTiles = 100;
        float finalStrength = strength;
        float f = degree;
        context.canvas().submitCustomGeometry(GuiMaterial.untextured(RenderStates.GUI), 0.0f, 0.0f, width, height, (pose, consumer) -> {
            int tiles = (int) (minTiles + ((maxTiles - minTiles) * finalStrength));
            for (int i = 0; i < tiles; i++) {
                float rot1 = (360.0f / tiles) * i;
                float rot2 = (360.0f / tiles) * (i + 1);
                float distanceToDegree1 = Math.abs(MathHelper.wrapDegrees(rot1 - f));
                float distanceToDegree2 = Math.abs(MathHelper.wrapDegrees(rot2 - f));
                float strength1 = MathHelper.clamp(1.0f - (distanceToDegree1 / 30.0f), 0.0f, 1.0f);
                float strength22 = MathHelper.clamp(1.0f - (distanceToDegree2 / 30.0f), 0.0f, 1.0f);
                if (strength1 >= 0.0f || strength22 >= 0.0f) {
                    float x1 = MathHelper.cos(Math.toRadians(rot1));
                    float y1 = MathHelper.sin(Math.toRadians(rot1));
                    float dynRadius1 = innerRadius + ((outerRadius - innerRadius) * strength1);
                    float dynRadius2 = innerRadius + ((outerRadius - innerRadius) * strength22);
                    int alpha1 = (int) (255.0f * strength1);
                    int alpha2 = (int) (255.0f * strength22);
                    int outerColor1 = color | (alpha1 << 24);
                    int outerColor2 = color | (alpha2 << 24);
                    consumer.addVertex(pose, centerX + (x1 * dynRadius1), centerY + (y1 * dynRadius1), 0.0f).setBlankUv().setColor(outerColor1);
                    consumer.addVertex(pose, centerX + (x1 * innerRadius), centerY + (y1 * innerRadius), 0.0f).setBlankUv().setColor(color);
                    float x2 = MathHelper.cos(Math.toRadians(rot2));
                    float y2 = MathHelper.sin(Math.toRadians(rot2));
                    consumer.addVertex(pose, centerX + (x2 * innerRadius), centerY + (y2 * innerRadius), 0.0f).setBlankUv().setColor(color);
                    consumer.addVertex(pose, centerX + (x2 * dynRadius2), centerY + (y2 * dynRadius2), 0.0f).setBlankUv().setColor(outerColor2);
                }
            }
        });
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void updateSize(HudWidgetWidget widget, boolean editorContext, HudSize size) {
        size.set(100.0f, 100.0f);
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        Player player = this.labyAPI.minecraft().getClientPlayer();
        if (player == null) {
            clearTarget();
            return;
        }
        if (player.gameMode() != GameMode.SURVIVAL && player.gameMode() != GameMode.ADVENTURE) {
            clearTarget();
            return;
        }
        Position position = player.position();
        int range = ((DangerWarnerHudWidgetConfig) this.config).range();
        double nearestDistance = 0.0d;
        Entity nearestEntity = null;
        List<Entity> entities = this.labyAPI.minecraft().clientWorld().getEntities();
        for (Entity entity : entities) {
            if (entity != player) {
                double distance = entity.position().distanceSquared(position);
                if (distance <= range * range && isEntityDangerousTo(entity, player) && (nearestEntity == null || distance < nearestDistance)) {
                    nearestDistance = distance;
                    nearestEntity = entity;
                }
            }
        }
        if (nearestEntity == null || nearestDistance > range * range) {
            clearTarget();
        } else {
            Position targetPosition = nearestEntity.position();
            updateTarget(targetPosition.getX(), targetPosition.getY(), targetPosition.getZ());
        }
    }

    private boolean isEntityDangerousTo(Entity entity, Player target) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (!livingEntity.isHostile()) {
                return false;
            }
            Position targetPosition = target.position();
            double targetX = targetPosition.getX();
            double targetY = targetPosition.getY();
            double targetZ = targetPosition.getZ();
            boolean lookingAt = isLivingEntityLookingAt(livingEntity, targetX, targetZ, 30.0d);
            if (!lookingAt) {
                return false;
            }
            ClientWorld world = Laby.labyAPI().minecraft().clientWorld();
            double entityY = livingEntity.position().getY();
            return Math.abs(entityY - targetY) <= 2.8d || !world.isBlockInBetween(livingEntity.eyePosition(), target.eyePosition());
        }
        return false;
    }

    private boolean isLivingEntityLookingAt(LivingEntity entity, double x, double z, double threshold) {
        Position entityPosition = entity.position();
        double entityX = entityPosition.getX();
        double entityZ = entityPosition.getZ();
        float headRotationY = entity.getHeadRotationY();
        double targetXDiff = x - entityX;
        double targetZDiff = z - entityZ;
        double targetYaw = Math.toDegrees(Math.atan2(targetZDiff, targetXDiff));
        double yawDifference = MathHelper.wrapDegrees((targetYaw - ((double) headRotationY)) - 90.0d);
        return Math.abs(yawDifference) < threshold;
    }

    public void updateTarget(double x, double y, double z) {
        if (this.hasTarget && this.targetX == x && this.targetY == y && this.targetZ == z) {
            return;
        }
        this.previousHasTarget = this.hasTarget;
        this.timeLastChange = TimeUtil.getMillis();
        this.hasTarget = true;
        this.targetX = x;
        this.targetY = y;
        this.targetZ = z;
    }

    public void clearTarget() {
        if (!this.hasTarget) {
            return;
        }
        this.timeLastChange = TimeUtil.getMillis();
        this.previousHasTarget = this.hasTarget;
        this.hasTarget = false;
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return this.hasTarget || getTransitionProgress() != 1.0f;
    }

    private float getTransitionProgress() {
        long timePassed = TimeUtil.getMillis() - this.timeLastChange;
        float progress = MathHelper.clamp(timePassed / 300.0f, 0.0f, 1.0f);
        return (float) CubicBezier.EASE_OUT.curve(progress);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/DangerWarnerHudWidget$DangerWarnerHudWidgetConfig.class */
    public static class DangerWarnerHudWidgetConfig extends HudWidgetConfig {

        @ColorPickerWidget.ColorPickerSetting(chroma = true)
        private final ConfigProperty<Color> color = new ConfigProperty<>(Color.of(15026505));

        @SliderWidget.SliderSetting(min = 5.0f, max = 30.0f)
        private final ConfigProperty<Integer> range = new ConfigProperty<>(20);

        public Color color() {
            return this.color.get();
        }

        public int range() {
            return this.range.get().intValue();
        }
    }
}
