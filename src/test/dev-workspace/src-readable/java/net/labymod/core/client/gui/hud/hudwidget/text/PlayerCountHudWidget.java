package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/PlayerCountHudWidget.class */
@SpriteSlot(x = 3)
public class PlayerCountHudWidget extends TextHudWidget<TextHudWidgetConfig> {
    private TextLine playerCountLine;

    public PlayerCountHudWidget() {
        super("player_count");
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.playerCountLine = super.createLine("Online", "?");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        ClientPacketListener clientPacketListener = this.labyAPI.minecraft().clientPacketListener();
        if (clientPacketListener == null) {
            return;
        }
        int playerCount = clientPacketListener.getPlayerCount();
        this.playerCountLine.updateAndFlush(Integer.valueOf(playerCount));
    }
}
