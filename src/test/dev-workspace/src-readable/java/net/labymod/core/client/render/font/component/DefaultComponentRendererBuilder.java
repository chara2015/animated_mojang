package net.labymod.core.client.render.font.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.render.GraphicsColor;
import net.labymod.api.client.render.font.ComponentRenderMeta;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.client.render.font.FontIconRenderListener;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/DefaultComponentRendererBuilder.class */
@Singleton
@Implements(ComponentRendererBuilder.class)
public class DefaultComponentRendererBuilder implements ComponentRendererBuilder {
    private final LabyAPI labyAPI;
    private RenderableComponent text;
    private float x;
    private float y;
    private Mouse mouse;
    private int textColor;
    private boolean adjustTextColor;
    private int iconColor;
    private boolean allowColors;
    private boolean centered;
    private boolean shadow;
    private boolean useFloatingPointPosition;
    private int backgroundColor;
    private boolean shouldBatch;
    private float scale;
    private TextDrawMode drawMode = TextDrawMode.NORMAL;
    private FontIconRenderListener iconRenderer = FontIconRenderListener.NORMAL_2D;
    private final List<Runnable> postRenderQueue = new ArrayList();

    @Inject
    public DefaultComponentRendererBuilder(LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        dispose();
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder text(RenderableComponent component) {
        this.text = component;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder pos(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder mousePos(Mouse mouse) {
        this.mouse = mouse;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder drawMode(TextDrawMode drawMode) {
        this.drawMode = drawMode;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder iconRenderer(FontIconRenderListener renderer) {
        this.iconRenderer = renderer;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder textColor(int textColor, boolean adjustColor) {
        this.textColor = textColor;
        this.adjustTextColor = adjustColor;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder iconColor(int iconColor) {
        this.iconColor = iconColor;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder allowColors(boolean allowColors) {
        this.allowColors = allowColors;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder shadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder discrete(boolean discrete) {
        this.drawMode = discrete ? TextDrawMode.SEE_THROUGH : TextDrawMode.NORMAL;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder useFloatingPointPosition(boolean floatingPoints) {
        this.useFloatingPointPosition = floatingPoints;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder centered(boolean centered) {
        this.centered = centered;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder backgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder shouldBatch(boolean batch) {
        this.shouldBatch = batch;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRendererBuilder scale(float scale) {
        this.scale = scale;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRenderMeta render(Stack stack) {
        ensureInitialized();
        ComponentRenderMeta meta = renderIntern(stack);
        dispose();
        return meta;
    }

    @Override // net.labymod.api.client.render.font.ComponentRendererBuilder
    public ComponentRenderMeta render(ScreenContext context) {
        ensureInitialized();
        ComponentRenderMeta meta = renderIntern(context);
        dispose();
        return meta;
    }

    @Override // net.labymod.api.util.Disposable
    public void dispose() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.mouse = Laby.isInitialized() ? this.labyAPI.minecraft().absoluteMouse() : null;
        this.textColor = -1;
        this.adjustTextColor = true;
        this.iconColor = -1;
        this.scale = 1.0f;
        this.allowColors = true;
        this.shouldBatch = true;
        this.backgroundColor = 0;
        this.centered = false;
        this.shadow = true;
        this.useFloatingPointPosition = false;
        this.drawMode = TextDrawMode.NORMAL;
        this.iconRenderer = FontIconRenderListener.NORMAL_2D;
    }

    private ComponentRenderMeta renderIntern(Stack stack) {
        float width = renderRaw(stack, this.text, this.x, this.y, c -> {
            return Integer.valueOf(this.textColor);
        }, this.allowColors);
        renderIcons();
        float maxX = this.x + (this.text.getWidth() * this.scale);
        float maxY = this.y + (this.text.getHeight() * this.scale);
        return new ComponentRenderMeta(findHoveredComponent(this.text, this.x, this.y), this.x, maxX, this.y, maxY, width);
    }

    private ComponentRenderMeta renderIntern(ScreenContext context) {
        float width = renderRaw(context, this.text, this.x, this.y, c -> {
            return Integer.valueOf(this.textColor);
        }, this.allowColors);
        float maxX = this.x + (this.text.getWidth() * this.scale);
        float maxY = this.y + (this.text.getHeight() * this.scale);
        return new ComponentRenderMeta(findHoveredComponent(this.text, this.x, this.y), this.x, maxX, this.y, maxY, width);
    }

    private void renderIcons() {
        if (!this.postRenderQueue.isEmpty()) {
            this.iconRenderer.preRender();
            for (Runnable task : this.postRenderQueue) {
                task.run();
            }
            this.iconRenderer.postRender();
            this.postRenderQueue.clear();
        }
    }

    private RenderableComponent findHoveredComponent(RenderableComponent text, float x, float y) {
        RenderableComponent component = null;
        Iterator<RenderableComponent> it = text.getChildren().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            RenderableComponent child = it.next();
            RenderableComponent childComponent = findHoveredComponent(child, x, y);
            if (childComponent != null) {
                component = childComponent;
                break;
            }
        }
        Style style = text.style();
        float textX = (x / this.scale) + text.getXOffset();
        float textY = (y / this.scale) + text.getYOffset() + text.getInnerY();
        if (!style.isEmpty() && component == null && this.mouse != null) {
            boolean inside = this.mouse.isInside(textX * this.scale, textY * this.scale, text.getSingleWidth() * this.scale, text.getSingleHeight() * this.scale);
            if (inside) {
                component = text;
            }
        }
        return component;
    }

    private float renderRaw(Stack stack, RenderableComponent text, float x, float y, Function<RenderableComponent, Integer> baseColor, boolean allowColors) {
        float textX = (x / this.scale) + text.getXOffset();
        float textY = (y / this.scale) + text.getYOffset() + text.getInnerY();
        Style style = text.style();
        TextColor color = style.getColor();
        int baseColorRgb = baseColor.apply(text).intValue();
        int rgb = (!allowColors || color == null) ? baseColorRgb : color.getValue();
        if (rgb != baseColorRgb) {
            rgb = ColorFormat.ARGB32.pack(rgb, (int) (GraphicsColor.DEFAULT_COLOR.update(baseColorRgb).alpha() * 255.0f));
        }
        int rgb2 = this.adjustTextColor ? ColorUtil.adjustedColor(rgb) : rgb;
        renderIcon(stack, text, x, y, baseColor, allowColors);
        GFXRenderPipeline renderPipeline = Laby.references().gfxRenderPipeline();
        RenderEnvironmentContext renderEnvironmentContext = renderPipeline.renderEnvironmentContext();
        int packedLight = renderEnvironmentContext.getPackedLight();
        if (this.centered) {
            textX -= text.getWidth() / 2.0f;
        }
        stack.push();
        stack.scale(this.scale);
        int flags = 0;
        if (this.shadow) {
            flags = 0 | 1;
        }
        int flags2 = flags | (this.drawMode == TextDrawMode.SEE_THROUGH ? 8 : 4);
        TextRenderer textRenderer = Laby.references().textRendererProvider().getRenderer();
        Matrix4f pose = stack.getProvider().getPose();
        textRenderer.render(pose, text.getTextLayout(), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, textX), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, textY), rgb2, packedLight, this.backgroundColor, flags2);
        for (RenderableComponent child : text.getChildren()) {
            renderRaw(stack, child, x, y, baseColor, allowColors);
        }
        stack.pop();
        return text.getWidth() * this.scale;
    }

    private float renderRaw(ScreenContext context, RenderableComponent text, float x, float y, Function<RenderableComponent, Integer> baseColor, boolean allowColors) {
        Style style = text.style();
        TextColor color = style.getColor();
        int baseColorRgb = baseColor.apply(text).intValue();
        int rgb = (!allowColors || color == null) ? baseColorRgb : color.getValue();
        if (rgb != baseColorRgb) {
            rgb = ColorFormat.ARGB32.pack(rgb, (int) (GraphicsColor.DEFAULT_COLOR.update(baseColorRgb).alpha() * 255.0f));
        }
        int rgb2 = this.adjustTextColor ? ColorUtil.adjustedColor(rgb) : rgb;
        renderIcon(context, text, x, y, baseColor, allowColors);
        int flags = 0;
        if (this.shadow) {
            flags = 0 | 1;
        }
        if (this.centered) {
            flags |= 2;
        }
        if (this.useFloatingPointPosition) {
            flags |= 4;
        }
        ScreenCanvas canvas = context.canvas();
        canvas.submitRenderableComponent(text, x, y, rgb2, this.scale, flags);
        return canvas.getTextWidth(text.getTextLayout());
    }

    private void renderIcon(ScreenContext context, RenderableComponent text, float x, float y, Function<RenderableComponent, Integer> baseColor, boolean allowColors) {
        IconComponent icon = text.getIcon();
        for (RenderableComponent child : text.getChildren()) {
            renderIcon(context, child, x, y, baseColor, allowColors);
        }
        if (icon == null) {
            return;
        }
        float textX = (x / this.scale) + text.getXOffset();
        float textY = (y / this.scale) + text.getYOffset() + text.getInnerY();
        Style style = text.style();
        TextColor color = style.getColor();
        int baseColorRgb = baseColor.apply(text).intValue();
        int rgb = (!allowColors || color == null) ? baseColorRgb : color.getValue();
        if (rgb != baseColorRgb) {
            rgb = ColorFormat.ARGB32.pack(rgb, (int) (GraphicsColor.DEFAULT_COLOR.update(baseColorRgb).alpha() * 255.0f));
        }
        int rgb2 = this.adjustTextColor ? ColorUtil.adjustedColor(rgb) : rgb;
        ScreenCanvas canvas = context.canvas();
        canvas.submitIcon(icon.getIcon(), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, textX), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, textY), text.getWidth(), text.getHeight(), false, rgb2);
    }

    private void renderIcon(Stack stack, RenderableComponent text, float x, float y, Function<RenderableComponent, Integer> baseColor, boolean allowColors) {
        IconComponent icon = text.getIcon();
        for (RenderableComponent child : text.getChildren()) {
            renderIcon(stack, child, x, y, baseColor, allowColors);
        }
        if (icon == null) {
            return;
        }
        float textX = (x / this.scale) + text.getXOffset();
        float textY = (y / this.scale) + text.getYOffset() + text.getInnerY();
        Style style = text.style();
        TextColor color = style.getColor();
        int baseColorRgb = baseColor.apply(text).intValue();
        int rgb = (!allowColors || color == null) ? baseColorRgb : color.getValue();
        if (rgb != baseColorRgb) {
            rgb = ColorFormat.ARGB32.pack(rgb, (int) (GraphicsColor.DEFAULT_COLOR.update(baseColorRgb).alpha() * 255.0f));
        }
        int rgb2 = this.adjustTextColor ? ColorUtil.adjustedColor(rgb) : rgb;
        this.postRenderQueue.add(() -> {
            this.iconRenderer.render(stack, icon.getIcon(), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, textX), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, textY), text.getWidth(), text.getHeight(), rgb2);
        });
    }

    private void ensureInitialized() {
        if (this.text == null) {
            throw new IllegalStateException("Missing component (call text(RenderableComponent component))");
        }
    }
}
