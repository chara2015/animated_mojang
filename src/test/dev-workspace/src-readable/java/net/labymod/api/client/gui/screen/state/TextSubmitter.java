package net.labymod.api.client.gui.screen.state;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderingOptions;
import net.labymod.api.client.gfx.pipeline.renderer.text.layout.TextLayoutData;
import net.labymod.api.client.gfx.pipeline.renderer.text.layout.TextLayoutEngine;
import net.labymod.api.client.gfx.pipeline.renderer.text.layout.TextLayoutSpec;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.states.GuiTextRenderState;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.util.ComponentUtil;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/TextSubmitter.class */
public final class TextSubmitter {
    private final ScreenContext context;
    private final ScreenCanvas canvas;
    private final TextLayoutEngine layoutEngine;

    public TextSubmitter(ScreenContext context, ScreenCanvas canvas) {
        this.context = context;
        this.canvas = canvas;
        Objects.requireNonNull(canvas);
        this.layoutEngine = new TextLayoutEngine(canvas::getTextWidth);
    }

    public void submitRenderableComponent(RenderableComponent component, float x, float y, int argb, FontSize.PredefinedFontSize fontSize, int flags) {
        submitRenderableComponent(component, x, y, argb, fontSize.fontSize().getSize(), flags);
    }

    public void submitRenderableComponent(RenderableComponent component, float x, float y, int argb, float scale, int flags) {
        this.context.pushStack();
        this.context.scale(scale, scale);
        submitRenderableComponent(component, x / scale, y / scale, argb, flags);
        this.context.popStack();
    }

    public void submitRenderableComponent(RenderableComponent component, float x, float y, int argb, int flags) {
        if (TextRenderingOptions.isCentered(flags)) {
            x -= component.getWidth() / 2.0f;
            flags &= -3;
        }
        boolean useFloatingPointValues = TextRenderingOptions.isFloatingPointValues(flags);
        Style style = component.style();
        int color = ComponentUtil.getTextColor(style, argb);
        float textX = x + component.getXOffset();
        float textY = y + component.getYOffset() + component.getInnerY();
        float posX = MathHelper.applyFloatingPointPosition(useFloatingPointValues, textX);
        float posY = MathHelper.applyFloatingPointPosition(useFloatingPointValues, textY);
        IconComponent icon = component.getIcon();
        if (icon != null) {
            this.canvas.submitIcon(icon.getIcon(), posX, posY, component.getWidth(), component.getHeight(), false, color);
        }
        TextLayoutData textLayoutData = layoutText(component.getText(), style, posX, posY, color, 0, 1.0f, flags);
        submitText(textLayoutData);
        for (RenderableComponent child : component.getChildren()) {
            submitRenderableComponent(child, x, y, color, flags);
        }
    }

    public void submitText(String text, float x, float y, int argb, float scale, int flags) {
        submitText(text, x, y, argb, 0, scale, flags);
    }

    public void submitText(String text, float x, float y, int argb, FontSize.PredefinedFontSize fontSize, int flags) {
        submitText(text, x, y, argb, 0, fontSize, flags);
    }

    public void submitText(String text, float x, float y, int argb, int backgroundArgb, FontSize.PredefinedFontSize fontSize, int flags) {
        submitText(text, x, y, argb, backgroundArgb, fontSize.fontSize().getSize(), flags);
    }

    public void submitText(String text, float x, float y, int argb, int backgroundArgb, float scale, int flags) {
        TextLayoutData textLayoutData = layoutText(text, x, y, argb, backgroundArgb, scale, flags);
        submitText(textLayoutData);
    }

    public void submitText(String text, float x, float y, int argb, int backgroundArgb, int flags) {
        TextLayoutData textLayoutData = layoutText(text, x, y, argb, backgroundArgb, 1.0f, flags);
        submitText(textLayoutData);
    }

    public void submitComponent(Component text, float x, float y, int argb, float scale, int flags) {
        if (containsIcon(text)) {
            submitRenderableComponent(RenderableComponent.of(text), x, y, argb, scale, flags);
        } else {
            submitComponent(text, x, y, argb, 0, scale, flags);
        }
    }

    public void submitComponent(Component text, float x, float y, int argb, int backgroundArgb, float scale, int flags) {
        if (containsIcon(text)) {
            submitRenderableComponent(RenderableComponent.of(text), x, y, argb, scale, flags);
        } else {
            TextLayoutData textLayoutData = this.layoutEngine.layout(TextLayoutSpec.builder(text, x, y, argb).setBackgroundArgb(backgroundArgb).setScale(scale).setFlags(flags).build());
            submitText(textLayoutData);
        }
    }

    public void submitComponent(Component text, float x, float y, int argb, FontSize.PredefinedFontSize fontSize, int flags) {
        if (containsIcon(text)) {
            submitRenderableComponent(RenderableComponent.of(text), x, y, argb, 1.0f, flags);
        } else {
            submitComponent(text, x, y, argb, 0, fontSize, flags);
        }
    }

    public void submitComponent(Component text, float x, float y, int argb, int backgroundArgb, FontSize.PredefinedFontSize fontSize, int flags) {
        if (containsIcon(text)) {
            submitRenderableComponent(RenderableComponent.of(text), x, y, argb, fontSize, flags);
        } else {
            submitComponent(text, x, y, argb, backgroundArgb, fontSize.fontSize().getSize(), flags);
        }
    }

    public void submitComponent(Component text, float x, float y, int argb, int backgroundArgb, int flags) {
        if (containsIcon(text)) {
            submitRenderableComponent(RenderableComponent.of(text), x, y, argb, 1.0f, flags);
        } else {
            TextLayoutData textLayoutData = this.layoutEngine.layout(TextLayoutSpec.builder(text, x, y, argb).setBackgroundArgb(backgroundArgb).setFlags(flags).build());
            submitText(textLayoutData);
        }
    }

    public void submitText(TextLayoutData text) {
        this.context.pushStack();
        float scale = text.scale();
        this.context.scale(scale, scale);
        this.canvas.submitState(new GuiTextRenderState(this.canvas.getTextRenderer().getCurrent(), text, this.canvas.currentPose(), this.canvas.getScissorArea()));
        this.context.popStack();
    }

    private TextLayoutData layoutText(String text, float x, float y, int argb, int backgroundArgb, float scale, int flags) {
        return layoutText(text, Style.EMPTY, x, y, argb, backgroundArgb, scale, flags);
    }

    private TextLayoutData layoutText(String text, Style style, float x, float y, int argb, int backgroundArgb, float scale, int flags) {
        return this.layoutEngine.layout(TextLayoutSpec.builder(text, style, x, y, argb).setBackgroundArgb(backgroundArgb).setScale(scale).setFlags(flags).build());
    }

    private boolean containsIcon(Component component) {
        if (component instanceof IconComponent) {
            return true;
        }
        for (Component child : component.getChildren()) {
            if (containsIcon(child)) {
                return true;
            }
        }
        return false;
    }
}
