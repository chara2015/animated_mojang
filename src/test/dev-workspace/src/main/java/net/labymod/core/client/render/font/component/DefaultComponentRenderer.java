package net.labymod.core.client.render.font.component;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.client.component.ObjectComponent;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.mapper.ObjectComponentMapper;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.component.serializer.plain.PlainTextComponentSerializer;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffectRenderer;
import net.labymod.api.client.render.font.ComponentFormatter;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.font.TextOverflowStrategy;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;
import net.labymod.api.models.Implements;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.render.font.component.mapper.KeybindComponentMapper;
import net.labymod.core.client.render.font.component.mapper.ScoreComponentMapper;
import net.labymod.core.client.render.font.component.mapper.TranslatableComponentMapper;
import net.labymod.core.util.collection.TimestampedCache;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/DefaultComponentRenderer.class */
@Singleton
@Implements(ComponentRenderer.class)
public class DefaultComponentRenderer implements ComponentRenderer {
    private final RenderingFlattenerListener flattenerListener;
    private final ComponentRendererBuilder componentRendererBuilder;
    private final HoverBackgroundEffectRenderer hoverBackgroundEffectRenderer;
    private PlainTextComponentSerializer plainSerializer;
    private LegacyComponentSerializer legacySerializer;
    private final TimestampedCache<CacheKey, RenderableComponent> componentCache = new TimestampedCache<>(256, 60000, 5000);
    private ComponentFlattener flattener = ComponentFlattener.BASIC;
    private ComponentFlattener colorStrippingFlattener = new ColorStrippingComponentFlattener(ComponentFlattener.BASIC);
    private final ComponentFormatter formatter = new DefaultComponentFormatter(this);

    @Inject
    public DefaultComponentRenderer(ComponentRendererBuilder componentRendererBuilder, HoverBackgroundEffectRenderer hoverBackgroundEffectRenderer, EventBus eventBus) {
        this.componentRendererBuilder = componentRendererBuilder;
        this.hoverBackgroundEffectRenderer = hoverBackgroundEffectRenderer;
        setFlattener(ComponentFlattener.BASIC.toBuilder2().withIdentifier(ComponentFlattener.BASIC_COMPLEX_FLATTENER_IDENTIFIER).complexMapper(TranslatableComponent.class, new TranslatableComponentMapper()).complexMapper(KeybindComponent.class, new KeybindComponentMapper()).complexMapper(ScoreComponent.class, new ScoreComponentMapper()).complexMapper(ObjectComponent.class, new ObjectComponentMapper()).build());
        eventBus.registerListener(this);
        this.flattenerListener = new RenderingFlattenerListener(0.0f, false);
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public ComponentRendererBuilder builder() {
        return this.componentRendererBuilder;
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public ComponentFlattener getFlattener() {
        return this.flattener;
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public ComponentFlattener getColorStrippingFlattener() {
        return this.colorStrippingFlattener;
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public void setFlattener(ComponentFlattener flattener) {
        if (flattener == null) {
            throw new NullPointerException("flattener cannot be null");
        }
        this.flattener = flattener;
        this.colorStrippingFlattener = new ColorStrippingComponentFlattener(flattener);
        this.plainSerializer = PlainTextComponentSerializer.of(this.colorStrippingFlattener);
        this.legacySerializer = LegacyComponentSerializer.legacySection().withFlattener(flattener);
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public PlainTextComponentSerializer plainSerializer() {
        return this.plainSerializer;
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public LegacyComponentSerializer legacySectionSerializer() {
        return this.legacySerializer;
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public float width(Component component) {
        CacheKey cacheKey = getCacheKey(component, -1.0f, HorizontalAlignment.LEFT, 0.0f, TextOverflowStrategy.CLIP, false);
        RenderableComponent cachedComponent = this.componentCache.getValue(cacheKey);
        if (cachedComponent != null) {
            return (int) cachedComponent.getWidth();
        }
        RenderingFlattenerListener listener = this.flattenerListener;
        listener.reset();
        getFlattener().flatten(component, this.flattenerListener);
        float width = 0.0f;
        for (RenderableComponent flattened : listener.getComponents()) {
            width += flattened.getWidth();
        }
        return width;
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public float height() {
        return Laby.references().textRendererProvider().getRenderer().getLineHeight();
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public RenderableComponent realignedMerge(List<RenderableComponent> components) {
        resetYOffset(components);
        if (components.size() == 1) {
            return (RenderableComponent) components.getFirst();
        }
        for (int index = 1; index < components.size(); index++) {
            RenderableComponent previous = components.get(index - 1);
            RenderableComponent current = components.get(index);
            float difference = (previous.getYOffset() + previous.getHeight()) - current.getYOffset();
            recursiveAddY(current, difference);
        }
        return RenderableComponent.of("", null, Style.EMPTY, 0.0f, 0.0f, components, 0.0f);
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public ComponentFormatter formatter() {
        return this.formatter;
    }

    private void recursiveAddY(RenderableComponent component, float delta) {
        component.setYOffset(component.getYOffset() + delta);
        for (RenderableComponent child : component.getChildren()) {
            recursiveAddY(child, delta);
        }
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public RenderableComponent formatComponent(Component component, float maxWidth, HorizontalAlignment alignment, float lineSpacing, TextOverflowStrategy overflowStrategy, boolean noCache, int maxLines, boolean removeLeadingSpaces, boolean useChatOptions, boolean maxLinesClipText) {
        CacheKey cacheKey = null;
        if (!noCache) {
            cacheKey = getCacheKey(component, maxWidth, alignment, lineSpacing, overflowStrategy, useChatOptions && Laby.labyAPI().minecraft().options().isChatColorsEnabled());
            RenderableComponent cachedComponent = this.componentCache.getValue(cacheKey);
            if (cachedComponent != null) {
                return cachedComponent;
            }
        }
        RenderableComponent result = RenderableComponent.merge(ComponentSplitter.render(component, maxWidth, maxLines, lineSpacing, overflowStrategy, alignment, useChatOptions, maxLinesClipText));
        if (!noCache) {
            this.componentCache.putTimestamped(cacheKey, result);
        }
        return result;
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public void renderHoverComponent(ScreenContext context, RenderableComponent component, int color, boolean allowColors, Rectangle windowBounds) {
        Theme currentTheme = Laby.labyAPI().themeService().currentTheme();
        HoverBackgroundEffect effect = currentTheme.getHoverBackgroundRenderer();
        float scale = effect == null ? 1.0f : effect.getScale();
        float padding = effect == null ? 0.0f : effect.getPadding();
        float width = (component.getWidth() + (padding * 2.0f)) * scale;
        float height = (component.getHeight() + (padding * 2.0f)) * scale;
        float screenWidth = windowBounds.getWidth();
        float screenHeight = windowBounds.getHeight();
        MutableMouse mouse = context.mouse();
        float x = mouse.getX() + padding;
        float y = (mouse.getY() - height) + padding;
        if (y < 0.0f) {
            y += height + 3.0f;
            x += 3.0f;
        }
        if (x + width > screenWidth) {
            x -= width;
        }
        this.hoverBackgroundEffectRenderer.hoverEffect(effect).pos(MathHelper.clamp(x, 0.0f, screenWidth - width), MathHelper.clamp(y, 0.0f, screenHeight - height), component.getWidth() * scale, component.getCeilHeight() * scale).component(component).render(context);
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public void invalidate() {
        this.componentCache.clear();
    }

    @Subscribe
    public void invalidateCache(ScreenOpenEvent event) {
        invalidate();
    }

    @Override // net.labymod.api.client.render.font.ComponentRenderer
    public List<Component> split(Component component, float width) {
        TextRenderer renderer = Laby.references().textRenderer();
        return ComponentSplitter.splitText(component, width, renderer);
    }

    private void resetYOffset(Collection<RenderableComponent> components) {
        for (RenderableComponent component : components) {
            component.setYOffset(0.0f);
            resetYOffset(component.getChildren());
        }
    }

    private CacheKey getCacheKey(Component component, float maxWidth, HorizontalAlignment alignment, float lineSpacing, TextOverflowStrategy overflowStrategy, boolean chatOptions) {
        return new CacheKey(component, maxWidth, alignment, lineSpacing, overflowStrategy, chatOptions);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey.class */
    private static final class CacheKey extends Record {
        private final Component component;
        private final float maxWidth;
        private final HorizontalAlignment alignment;
        private final float lineSpacing;
        private final TextOverflowStrategy overflowStrategy;
        private final boolean chatOptions;

        private CacheKey(Component component, float maxWidth, HorizontalAlignment alignment, float lineSpacing, TextOverflowStrategy overflowStrategy, boolean chatOptions) {
            this.component = component;
            this.maxWidth = maxWidth;
            this.alignment = alignment;
            this.lineSpacing = lineSpacing;
            this.overflowStrategy = overflowStrategy;
            this.chatOptions = chatOptions;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CacheKey.class), CacheKey.class, "component;maxWidth;alignment;lineSpacing;overflowStrategy;chatOptions", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->component:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->maxWidth:F", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->alignment:Lnet/labymod/api/client/gui/HorizontalAlignment;", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->lineSpacing:F", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->overflowStrategy:Lnet/labymod/api/client/render/font/TextOverflowStrategy;", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->chatOptions:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CacheKey.class), CacheKey.class, "component;maxWidth;alignment;lineSpacing;overflowStrategy;chatOptions", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->component:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->maxWidth:F", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->alignment:Lnet/labymod/api/client/gui/HorizontalAlignment;", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->lineSpacing:F", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->overflowStrategy:Lnet/labymod/api/client/render/font/TextOverflowStrategy;", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->chatOptions:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CacheKey.class, Object.class), CacheKey.class, "component;maxWidth;alignment;lineSpacing;overflowStrategy;chatOptions", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->component:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->maxWidth:F", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->alignment:Lnet/labymod/api/client/gui/HorizontalAlignment;", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->lineSpacing:F", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->overflowStrategy:Lnet/labymod/api/client/render/font/TextOverflowStrategy;", "FIELD:Lnet/labymod/core/client/render/font/component/DefaultComponentRenderer$CacheKey;->chatOptions:Z").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public Component component() {
            return this.component;
        }

        public float maxWidth() {
            return this.maxWidth;
        }

        public HorizontalAlignment alignment() {
            return this.alignment;
        }

        public float lineSpacing() {
            return this.lineSpacing;
        }

        public TextOverflowStrategy overflowStrategy() {
            return this.overflowStrategy;
        }

        public boolean chatOptions() {
            return this.chatOptions;
        }
    }
}
