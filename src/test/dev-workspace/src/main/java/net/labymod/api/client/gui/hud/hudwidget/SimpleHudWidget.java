package net.labymod.api.client.gui.hud.hudwidget;

import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/SimpleHudWidget.class */
public abstract class SimpleHudWidget<T extends HudWidgetConfig> extends HudWidget<T> {
    public abstract void render(RenderPhase renderPhase, ScreenContext screenContext, boolean z, HudSize hudSize);

    protected SimpleHudWidget(String id, Class<T> configClass) {
        super(id, configClass);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public final void render(ScreenContext context, boolean isEditorContext, HudSize size) {
        render(RenderPhase.UPDATE_SIZE, context, isEditorContext, size);
        render(RenderPhase.RENDER, context, isEditorContext, size);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/SimpleHudWidget$RenderPhase.class */
    public enum RenderPhase {
        UPDATE_SIZE,
        RENDER;

        public boolean canRender() {
            return this == RENDER;
        }
    }
}
