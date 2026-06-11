package net.labymod.core.client.gui.hud.hudwidget.text;

import java.text.DecimalFormat;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.world.phys.hit.HitResult;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.animation.old.animations.RangeOldAnimation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/RangeHudWidget.class */
@SpriteSlot(x = 3, y = 3)
public class RangeHudWidget extends TextHudWidget<TextHudWidgetConfig> {
    private final DecimalFormat df;
    private TextLine textLine;
    private long lastAttack;
    private double lastDistance;

    public RangeHudWidget() {
        super(RangeOldAnimation.NAME);
        this.df = new DecimalFormat("#.##");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Range", "0 blocks");
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Subscribe
    public void attackEntity(ClientPlayerInteractEvent event) {
        if (!isEnabled() || !event.type().equals(ClientPlayerInteractEvent.InteractionType.ATTACK)) {
            return;
        }
        Minecraft minecraft = this.labyAPI.minecraft();
        Entity entity = minecraft.getTargetEntity();
        HitResult hitResult = minecraft.getHitResult();
        if (hitResult == null || !(entity instanceof LivingEntity)) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity) entity;
        if (livingEntity.getHealth() == 0.0f) {
            return;
        }
        double distanceSquared = hitResult.location().distance(minecraft.getCameraEntity().eyePosition());
        this.textLine.updateAndFlush(this.df.format(distanceSquared) + " blocks");
        this.lastAttack = TimeUtil.getMillis();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return this.lastAttack + 3000 > TimeUtil.getMillis();
    }
}
