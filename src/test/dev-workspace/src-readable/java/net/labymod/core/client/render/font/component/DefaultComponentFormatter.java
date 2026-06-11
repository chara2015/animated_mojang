package net.labymod.core.client.render.font.component;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.render.font.ComponentFormatter;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.font.TextOverflowStrategy;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/DefaultComponentFormatter.class */
public class DefaultComponentFormatter implements ComponentFormatter {
    private final ComponentRenderer componentRenderer;
    private float lineSpacing;
    private TextOverflowStrategy overflowStrategy;
    private float maxWidth;
    private HorizontalAlignment alignment;
    private boolean noCache;
    private int maxLines;
    private boolean removeLeadingSpaces;
    private boolean useChatOptions;
    private boolean maxLinesClipText;

    public DefaultComponentFormatter(ComponentRenderer componentRenderer) {
        this.componentRenderer = componentRenderer;
        reset();
    }

    @Override // net.labymod.api.client.render.font.ComponentFormatter
    public ComponentFormatter lineSpacing(float lineSpacing) {
        this.lineSpacing = lineSpacing;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentFormatter
    public ComponentFormatter overflow(TextOverflowStrategy overflowStrategy) {
        this.overflowStrategy = overflowStrategy;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentFormatter
    public ComponentFormatter maxWidth(float maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentFormatter
    public ComponentFormatter alignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentFormatter
    public ComponentFormatter disableCache() {
        this.noCache = true;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentFormatter
    public ComponentFormatter leadingSpaces(boolean leadingSpaces) {
        this.removeLeadingSpaces = !leadingSpaces;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentFormatter
    public ComponentFormatter maxLines(int maxLines) {
        this.maxLines = maxLines;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentFormatter
    public ComponentFormatter maxLinesClipText(boolean maxLinesClipText) {
        this.maxLinesClipText = maxLinesClipText;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentFormatter
    public ComponentFormatter useChatOptions(boolean useChatOptions) {
        this.useChatOptions = useChatOptions;
        return this;
    }

    @Override // net.labymod.api.client.render.font.ComponentFormatter
    public RenderableComponent format(Component component) {
        RenderableComponent renderableComponent = this.componentRenderer.formatComponent(component, this.maxWidth, this.alignment, this.lineSpacing, this.overflowStrategy, this.noCache, this.maxLines, this.removeLeadingSpaces, this.useChatOptions, this.maxLinesClipText);
        reset();
        return renderableComponent;
    }

    private void reset() {
        this.lineSpacing = 0.0f;
        this.overflowStrategy = TextOverflowStrategy.WRAP;
        this.maxWidth = -1.0f;
        this.alignment = HorizontalAlignment.LEFT;
        this.noCache = false;
        this.maxLines = 0;
        this.removeLeadingSpaces = true;
        this.useChatOptions = false;
    }
}
