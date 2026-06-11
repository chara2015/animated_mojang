package net.labymod.api.client.render.font;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.Disposable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/ComponentRendererBuilder.class */
@Referenceable
public interface ComponentRendererBuilder extends Disposable {
    ComponentRendererBuilder text(RenderableComponent renderableComponent);

    ComponentRendererBuilder pos(float f, float f2);

    ComponentRendererBuilder mousePos(Mouse mouse);

    ComponentRendererBuilder drawMode(TextDrawMode textDrawMode);

    ComponentRendererBuilder iconRenderer(FontIconRenderListener fontIconRenderListener);

    ComponentRendererBuilder textColor(int i, boolean z);

    ComponentRendererBuilder iconColor(int i);

    ComponentRendererBuilder allowColors(boolean z);

    ComponentRendererBuilder shadow(boolean z);

    ComponentRendererBuilder discrete(boolean z);

    ComponentRendererBuilder useFloatingPointPosition(boolean z);

    ComponentRendererBuilder centered(boolean z);

    ComponentRendererBuilder backgroundColor(int i);

    ComponentRendererBuilder shouldBatch(boolean z);

    ComponentRendererBuilder scale(float f);

    ComponentRenderMeta render(Stack stack);

    ComponentRenderMeta render(ScreenContext screenContext);

    default ComponentRendererBuilder text(String text) {
        text(Component.text(text));
        return this;
    }

    default ComponentRendererBuilder text(Component component) {
        text(RenderableComponent.of(component));
        return this;
    }

    default ComponentRendererBuilder color(int color) {
        return textColor(color).iconColor(color);
    }

    default ComponentRendererBuilder color(int color, boolean adjustColor) {
        return textColor(color, adjustColor).iconColor(color);
    }

    default ComponentRendererBuilder textColor(int textColor) {
        return textColor(textColor, true);
    }
}
