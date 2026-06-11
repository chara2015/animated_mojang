package net.labymod.api.client.component.builder;

import java.util.Objects;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.component.builder.StyleableBuilder;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/builder/StyleableBuilder.class */
public abstract class StyleableBuilder<T, B extends StyleableBuilder<T, B>> {
    protected TextColor textColor;
    protected Integer shadowColor;
    protected Boolean bold;
    protected Boolean italic;
    protected Boolean underlined;
    protected Boolean strikethrough;
    protected Boolean obfuscated;
    protected ClickEvent clickEvent;
    protected HoverEvent<?> hoverEvent;
    protected String insertion;
    protected GlyphSourceDescription glyphSourceDescription;

    public abstract T build();

    protected StyleableBuilder() {
    }

    protected StyleableBuilder(Style style) {
        applyStyle(style);
    }

    public B color(TextColor color) {
        this.textColor = color;
        return this;
    }

    public B colorIfAbsent(TextColor color) {
        if (this.textColor == null) {
            this.textColor = color;
        }
        return this;
    }

    public B shadowColor(Integer color) {
        this.shadowColor = color;
        return this;
    }

    public B shadowColorIfAbsent(Integer color) {
        if (this.shadowColor == null) {
            this.shadowColor = color;
        }
        return this;
    }

    public B decorate(TextDecoration textDecoration) {
        return (B) setDecoration(textDecoration, true);
    }

    public B decorate(TextDecoration... decorations) {
        for (TextDecoration decoration : decorations) {
            decorate(decoration);
        }
        return this;
    }

    public B undecorate(TextDecoration textDecoration) {
        return (B) setDecoration(textDecoration, false);
    }

    public B undecorate(TextDecoration... decorations) {
        for (TextDecoration decoration : decorations) {
            undecorate(decoration);
        }
        return this;
    }

    public B unsetDecoration(TextDecoration textDecoration) {
        return (B) setDecoration(textDecoration, null);
    }

    public B unsetDecoration(TextDecoration... decorations) {
        for (TextDecoration decoration : decorations) {
            unsetDecoration(decoration);
        }
        return this;
    }

    public B clickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }

    public B hoverEvent(HoverEvent<?> hoverEvent) {
        this.hoverEvent = hoverEvent;
        return this;
    }

    public B insertion(String insertion) {
        this.insertion = insertion;
        return this;
    }

    public B glyphSourceDescription(GlyphSourceDescription glyphSourceDescription) {
        this.glyphSourceDescription = glyphSourceDescription;
        return this;
    }

    @Deprecated
    public B decoration(TextDecoration textDecoration, TextDecoration.State state) {
        Objects.requireNonNull(state, "State cannot be null");
        switch (state) {
            case TRUE:
                return (B) decorate(textDecoration);
            case FALSE:
                return (B) undecorate(textDecoration);
            default:
                return (B) unsetDecoration(textDecoration);
        }
    }

    public boolean hasColor() {
        return this.textColor != null;
    }

    public boolean hasShadowColor() {
        return this.shadowColor != null;
    }

    public boolean hasDecoration(TextDecoration decoration) {
        return getDecorationState(decoration) != Boolean.TRUE;
    }

    public boolean isDecorationSet(TextDecoration decoration) {
        return getDecorationState(decoration) != null;
    }

    public boolean hasClickEvent() {
        return this.clickEvent != null;
    }

    public boolean hasHoverEvent() {
        return this.hoverEvent != null;
    }

    public boolean hasInsertion() {
        return this.insertion != null;
    }

    public boolean hasFont() {
        return this.glyphSourceDescription != null;
    }

    public boolean isEmpty() {
        return this.textColor == null && this.shadowColor == null && this.bold == null && this.italic == null && this.underlined == null && this.strikethrough == null && this.obfuscated == null && this.clickEvent == null && this.hoverEvent == null && this.insertion == null;
    }

    protected Style buildStyle() {
        return ComponentService.buildStyle(this.textColor, this.shadowColor, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.glyphSourceDescription);
    }

    protected B applyStyle(Style style) {
        this.textColor = style.getColor();
        this.shadowColor = style.getShadowColor();
        this.bold = getDecorationState(style, TextDecoration.BOLD);
        this.italic = getDecorationState(style, TextDecoration.ITALIC);
        this.underlined = getDecorationState(style, TextDecoration.UNDERLINED);
        this.strikethrough = getDecorationState(style, TextDecoration.STRIKETHROUGH);
        this.obfuscated = getDecorationState(style, TextDecoration.OBFUSCATED);
        this.clickEvent = style.getClickEvent();
        this.hoverEvent = style.getHoverEvent();
        this.insertion = style.getInsertion();
        this.glyphSourceDescription = style.getGlyphSourceDescription();
        return this;
    }

    private Boolean getDecorationState(TextDecoration decoration) {
        switch (decoration) {
            case BOLD:
                return this.bold;
            case ITALIC:
                return this.italic;
            case UNDERLINED:
                return this.underlined;
            case STRIKETHROUGH:
                return this.strikethrough;
            case OBFUSCATED:
                return this.obfuscated;
            default:
                return null;
        }
    }

    private Boolean getDecorationState(Style style, TextDecoration decoration) {
        if (style.isDecorationSet(decoration)) {
            return Boolean.valueOf(style.hasDecoration(decoration));
        }
        return null;
    }

    private B setDecoration(TextDecoration decoration, Boolean value) {
        switch (decoration) {
            case BOLD:
                this.bold = value;
                break;
            case ITALIC:
                this.italic = value;
                break;
            case UNDERLINED:
                this.underlined = value;
                break;
            case STRIKETHROUGH:
                this.strikethrough = value;
                break;
            case OBFUSCATED:
                this.obfuscated = value;
                break;
        }
        return this;
    }
}
