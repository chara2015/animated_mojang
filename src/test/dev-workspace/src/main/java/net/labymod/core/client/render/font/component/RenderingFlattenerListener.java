package net.labymod.core.client.render.font.component;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.flattener.FlattenerListener;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.font.text.StringIterator;
import net.labymod.api.util.CharSequences;
import net.labymod.api.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/RenderingFlattenerListener.class */
public class RenderingFlattenerListener implements FlattenerListener {
    private final float lineSpacing;
    private final boolean useChatOptions;
    private float x;
    private float lineHeight;
    private final Deque<Component> styleQueue = new ArrayDeque();
    private final List<RenderableComponent> components = new ArrayList();
    private Style lastStyle;

    public RenderingFlattenerListener(float lineSpacing, boolean useChatOptions) {
        this.lineSpacing = lineSpacing;
        this.useChatOptions = useChatOptions;
        reset();
    }

    public List<RenderableComponent> getComponents() {
        return this.components;
    }

    @Override // net.labymod.api.client.component.flattener.FlattenerListener
    public void push(@NotNull Component source) {
        if (source.style() == Style.EMPTY) {
            this.styleQueue.clear();
        }
        this.styleQueue.offerFirst(source);
    }

    @Override // net.labymod.api.client.component.flattener.FlattenerListener
    public void component(@NotNull String text) {
        if (!this.styleQueue.isEmpty() && (this.styleQueue.peek() instanceof IconComponent)) {
            IconComponent icon = (IconComponent) this.styleQueue.pop();
            Style style = icon.style();
            pushComponent("", icon, style);
            this.x += icon.getWidth();
            this.lineHeight = Math.max(this.lineHeight, icon.getHeight());
            return;
        }
        Component peek = this.styleQueue.peek();
        StringBuilder builder = new StringBuilder();
        StringIterator.iterateFormatted(text, peek == null ? null : peek.style(), false, (position, style2, codePoint) -> {
            char[] characters = Character.toChars(codePoint);
            if (this.lastStyle == null) {
                builder.append(characters);
                this.styleQueue.push(Component.text("", style2));
                this.lastStyle = style2;
                return true;
            }
            if (Objects.equals(this.lastStyle, style2)) {
                builder.append(characters);
                return true;
            }
            render(builder.toString(), null);
            builder.setLength(0);
            this.styleQueue.pop();
            this.styleQueue.push(Component.text("", style2));
            this.lastStyle = style2;
            builder.append(characters);
            return true;
        });
        if (builder.length() != 0) {
            render(builder.toString(), null);
        }
        if (this.lastStyle != null) {
            this.styleQueue.pop();
        }
        this.lastStyle = null;
    }

    private RenderableComponent pushComponent(@NotNull String text, @Nullable IconComponent icon, @NotNull Style style) {
        RenderableComponent component = RenderableComponent.of(text, icon, applyStyleOptions(style), this.x, 0.0f, Collections.emptyList(), this.lineSpacing);
        this.components.add(component);
        return component;
    }

    private void render(@NotNull String text, @Nullable IconComponent icon) {
        String text2 = StringUtil.removeIllegalCharacters(CharSequences.toString(text));
        if (CharSequences.isEmpty(text2) && icon == null) {
            return;
        }
        Style style = Style.empty();
        for (Component queued : this.styleQueue) {
            style = style.merge(queued.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
        }
        RenderableComponent component = pushComponent(text2, icon, style);
        this.x += component.getSingleWidth();
    }

    private Style applyStyleOptions(Style style) {
        if (!this.useChatOptions) {
            return style;
        }
        if (!Laby.labyAPI().minecraft().options().isChatColorsEnabled()) {
            return style.color(null);
        }
        return style;
    }

    @Override // net.labymod.api.client.component.flattener.FlattenerListener
    public void pop(@NotNull Component source) {
        this.styleQueue.removeFirstOccurrence(source);
    }

    public void reset() {
        this.x = 0.0f;
        this.lineHeight = -1.0f;
        this.styleQueue.clear();
        this.components.clear();
    }
}
