package net.labymod.core.client.gui.hud.hudwidget.text;

import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/text/BiomeHudWidget.class */
@SpriteSlot(x = 5, y = 1)
public class BiomeHudWidget extends TextHudWidget<TextHudWidgetConfig> {
    private TextLine biomeTextLine;

    public BiomeHudWidget() {
        super("biome");
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        this.biomeTextLine = super.createLine("Biome", "Plains");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean editorContext) {
        String biome = this.labyAPI.minecraft().clientWorld().getReadableBiomeName();
        this.biomeTextLine.updateAndFlush(biome);
    }
}
