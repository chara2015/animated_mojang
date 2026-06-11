package net.labymod.api.client.component.format;

import java.util.Objects;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.component.builder.Buildable;
import net.labymod.api.client.component.builder.StyleableBuilder;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.TextDecoration;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/Style.class */
public interface Style extends Buildable<Builder> {
    public static final Style EMPTY = empty();

    HoverEvent<?> getHoverEvent();

    Style hoverEvent(HoverEvent<?> hoverEvent);

    ClickEvent getClickEvent();

    Style clickEvent(ClickEvent clickEvent);

    TextColor getColor();

    Style color(TextColor textColor);

    GlyphSourceDescription getGlyphSourceDescription();

    Style glyphSourceDescription(GlyphSourceDescription glyphSourceDescription);

    boolean hasDecoration(TextDecoration textDecoration);

    boolean isDecorationSet(TextDecoration textDecoration);

    Style decorate(TextDecoration textDecoration);

    Style undecorate(TextDecoration textDecoration);

    Style unsetDecoration(TextDecoration textDecoration);

    boolean isEmpty();

    Style insertion(String str);

    String getInsertion();

    static Style empty() {
        return ComponentService.emptyStyle();
    }

    @Deprecated
    static Style style() {
        return empty();
    }

    @Deprecated
    static Style style(TextColor textColor) {
        return empty().color(textColor);
    }

    @Deprecated
    static Style style(TextDecoration... decorations) {
        return empty().decorate(decorations);
    }

    @Deprecated
    static Style style(TextColor textColor, TextDecoration... decorations) {
        return empty().color(textColor).decorate(decorations);
    }

    static Builder builder() {
        return new Builder();
    }

    default Integer getShadowColor() {
        return null;
    }

    default Style shadowColor(int shadowColor) {
        return this;
    }

    default Style merge(Style style, Merge.Strategy strategy) {
        return merge(style, strategy, 62);
    }

    default Style merge(Style style, Merge.Strategy strategy, int flags) {
        GlyphSourceDescription description;
        String insertion;
        if (strategy == Merge.Strategy.NEVER || style == null || style.isEmpty()) {
            return this;
        }
        Builder builder = toBuilder2();
        if (Merge.contains(flags, 2)) {
            TextColor color = style.getColor();
            if (color != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasColor())) {
                builder.color(color);
            }
            Integer shadowColor = style.getShadowColor();
            if (shadowColor != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasShadowColor())) {
                builder.shadowColor(shadowColor);
            }
        }
        if (Merge.contains(flags, 4)) {
            for (TextDecoration decoration : TextDecoration.getValues()) {
                if (style.isDecorationSet(decoration)) {
                    boolean value = style.hasDecoration(decoration);
                    if (strategy == Merge.Strategy.ALWAYS || !isDecorationSet(decoration)) {
                        if (value) {
                            builder.decorate(decoration);
                        } else {
                            builder.undecorate(decoration);
                        }
                    }
                }
            }
        }
        if (Merge.contains(flags, 8)) {
            ClickEvent clickEvent = style.getClickEvent();
            if (clickEvent != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasClickEvent())) {
                builder.clickEvent(clickEvent);
            }
            HoverEvent<?> hoverEvent = style.getHoverEvent();
            if (hoverEvent != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasHoverEvent())) {
                builder.hoverEvent(hoverEvent);
            }
        }
        if (Merge.contains(flags, 16) && (insertion = style.getInsertion()) != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasInsertion())) {
            builder.insertion(insertion);
        }
        if (Merge.contains(flags, 32) && (description = style.getGlyphSourceDescription()) != null && (strategy == Merge.Strategy.ALWAYS || !builder.hasFont())) {
            builder.glyphSourceDescription(description);
        }
        return builder.build();
    }

    default Style decorate(TextDecoration... decorations) {
        Style style = this;
        for (TextDecoration decoration : decorations) {
            style = style.decorate(decoration);
        }
        return style;
    }

    default Style undecorate(TextDecoration... decorations) {
        Style style = this;
        for (TextDecoration decoration : decorations) {
            style = style.undecorate(decoration);
        }
        return style;
    }

    default Style unsetDecorations(TextDecoration... decorations) {
        Style style = this;
        for (TextDecoration decoration : decorations) {
            style = style.unsetDecoration(decoration);
        }
        return style;
    }

    default Style colorIfAbsent(TextColor textColor) {
        if (getColor() != null) {
            return this;
        }
        return color(textColor);
    }

    @Deprecated
    default Style decoration(TextDecoration decoration, boolean flag) {
        return flag ? decorate(decoration) : undecorate(decoration);
    }

    @Deprecated
    default Style decoration(TextDecoration decoration, TextDecoration.State state) {
        Objects.requireNonNull(state, "State cannot be null");
        switch (state) {
            case TRUE:
                return decorate(decoration);
            case FALSE:
                return undecorate(decoration);
            default:
                return unsetDecoration(decoration);
        }
    }

    @Deprecated
    default TextColor color() {
        return getColor();
    }

    @Deprecated
    default Style build() {
        return this;
    }

    @Deprecated
    default String insertion() {
        return getInsertion();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // net.labymod.api.client.component.builder.Buildable
    /* JADX INFO: renamed from: toBuilder */
    default Builder toBuilder2() {
        return new Builder(this);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/Style$Builder.class */
    public static class Builder extends StyleableBuilder<Style, Builder> {
        private Builder() {
        }

        private Builder(Style style) {
            super(style);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // net.labymod.api.client.component.builder.StyleableBuilder
        public Style build() {
            return buildStyle();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/Style$Merge.class */
    public static class Merge {
        public static final int NONE = 0;
        public static final int COLOR = 2;
        public static final int DECORATIONS = 4;
        public static final int EVENTS = 8;
        public static final int INSERTION = 16;
        public static final int FONT = 32;
        public static final int ALL = 62;
        public static final int COLOR_AND_DECORATIONS = 6;

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/format/Style$Merge$Strategy.class */
        public enum Strategy {
            ALWAYS,
            NEVER,
            IF_ABSENT_ON_TARGET
        }

        public static boolean contains(int flags, int flag) {
            return (flags & flag) == flag;
        }
    }
}
