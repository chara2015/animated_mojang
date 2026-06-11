package net.labymod.core.client.gui.hud.hudwidget.test;

import java.util.Random;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/test/TestHeightTextHudWidget.class */
public class TestHeightTextHudWidget extends TextHudWidget<TextHudWidgetConfig> {
    public TestHeightTextHudWidget() {
        super("test_height");
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget, net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(TextHudWidgetConfig config) {
        super.load(config);
        Random random = new Random();
        int lines = random.nextInt((10 - 2) + 1) + 2;
        for (int index = 0; index < lines; index++) {
            createLine(Component.text("Key(" + index + ")"), Component.text("Value(" + index + ")"), TextLine::new);
        }
    }
}
