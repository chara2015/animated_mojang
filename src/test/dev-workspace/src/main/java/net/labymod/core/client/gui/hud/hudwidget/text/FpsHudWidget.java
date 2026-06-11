package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/FpsHudWidget.class */
@SpriteSlot
public class FpsHudWidget extends TextHudWidget<TextHudWidgetConfig> {
    private TextLine fpsLine;

    public FpsHudWidget() {
        super("fps");
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.fpsLine = super.createLine("FPS", (Object) 0);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        this.fpsLine.updateAndFlush(Integer.valueOf(this.labyAPI.minecraft().getFPS()));
    }
}
