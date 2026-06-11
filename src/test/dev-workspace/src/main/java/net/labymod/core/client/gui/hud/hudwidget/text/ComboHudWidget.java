package net.labymod.core.client.gui.hud.hudwidget.text;

import java.util.UUID;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.user.shop.spray.SprayConstants;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/ComboHudWidget.class */
@SpriteSlot(x = 3, y = 1)
public class ComboHudWidget extends TextHudWidget<TextHudWidgetConfig> {
    private float lastHealth;
    private int comboCount;
    private long lastHit;
    private UUID lastEntityId;
    private TextLine textLine;

    public ComboHudWidget() {
        super("combo");
        this.lastEntityId = UUID.randomUUID();
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.textLine = super.createLine("Combo", (Object) 0);
    }

    @Subscribe
    public void attackEntity(ClientPlayerInteractEvent event) {
        if (!isEnabled() || !event.type().equals(ClientPlayerInteractEvent.InteractionType.ATTACK)) {
            return;
        }
        Entity entity = this.labyAPI.minecraft().getTargetEntity();
        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;
        if (player.getHurtTime() > 1) {
            return;
        }
        if (entity.getUniqueId().equals(this.lastEntityId)) {
            setComboCount(this.comboCount + 1);
        } else {
            this.lastEntityId = entity.getUniqueId();
            setComboCount(1);
        }
        this.lastHit = TimeUtil.getMillis();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player == null) {
            return;
        }
        if (player.getHealth() < this.lastHealth) {
            setComboCount(0);
        }
        this.lastHealth = player.getHealth();
        if (this.lastHit + SprayConstants.LABYMOD_PLUS_NEXT_SPRAY < TimeUtil.getMillis()) {
            setComboCount(0);
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return this.labyAPI.minecraft().getClientPlayer() != null && this.comboCount > 0;
    }

    private void setComboCount(int comboCount) {
        this.comboCount = comboCount;
        this.textLine.updateAndFlush(Integer.valueOf(comboCount));
    }
}
