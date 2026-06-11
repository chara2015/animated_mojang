package net.labymod.api.client.gui.screen.state.states;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.layout.TextLayoutData;
import net.labymod.api.client.gfx.pipeline.renderer.text.state.TextState;
import net.labymod.api.client.gui.screen.state.GuiComponent;
import net.labymod.api.client.gui.screen.state.GuiTransform;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/states/GuiTextRenderState.class */
public class GuiTextRenderState implements GuiComponent, GuiTransform {
    private static final RenderEnvironmentContext ENVIRONMENT_CONTEXT = Laby.references().renderEnvironmentContext();
    private final FontRenderer fontRenderer;
    private final FormattedTextLayout textLayout;
    private final Matrix4f pose;
    private final float x;
    private final float y;
    private final int argb;
    private final int backgroundArgb;
    private final int flags;

    @Nullable
    private final ScissorArea scissorArea;
    private final float fontWeight;
    private Rectangle bounds;
    private TextState textState;

    public GuiTextRenderState(FontRenderer fontRenderer, TextLayoutData text, Matrix4f pose, @Nullable ScissorArea scissorArea) {
        this(fontRenderer, text.text(), pose, text.x(), text.y(), text.argb(), text.backgroundArgb(), text.fontFlags(), scissorArea);
    }

    public GuiTextRenderState(FontRenderer fontRenderer, FormattedTextLayout textLayout, Matrix4f pose, float x, float y, int argb, int backgroundArgb, int flags, @Nullable ScissorArea scissorArea) {
        this.fontRenderer = fontRenderer;
        this.textLayout = textLayout;
        this.pose = pose;
        this.x = x;
        this.y = y;
        this.argb = ColorUtil.combineAlpha(argb);
        this.backgroundArgb = backgroundArgb;
        this.flags = flags;
        this.scissorArea = scissorArea;
        RenderAttributes attributes = ENVIRONMENT_CONTEXT.renderAttributesStack().last();
        this.fontWeight = attributes.getFontWeight();
    }

    public TextState prepareText() {
        if (this.textState == null) {
            this.fontRenderer.setValue(FontProperties.FONT_WEIGHT_KEY, Float.valueOf(this.fontWeight));
            this.textState = this.fontRenderer.prepareText(this.textLayout, this.x, this.y, this.argb, RenderEnvironmentContext.FULL_BRIGHT, this.backgroundArgb, this.flags);
        }
        return this.textState;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiTransform
    public Matrix4f pose() {
        return this.pose;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiComponent
    public Rectangle bounds() {
        if (this.bounds == null) {
            this.fontRenderer.setValue(FontProperties.FONT_WEIGHT_KEY, Float.valueOf(this.fontWeight));
            this.bounds = Rectangle.relative(this.x, this.y, this.fontRenderer.getWidth(this.textLayout), this.fontRenderer.getLineHeight()).transformedAabb(this.pose);
        }
        return this.bounds;
    }

    @Override // net.labymod.api.client.gui.screen.state.GuiComponent
    @Nullable
    public ScissorArea getScissorArea() {
        return this.scissorArea;
    }
}
