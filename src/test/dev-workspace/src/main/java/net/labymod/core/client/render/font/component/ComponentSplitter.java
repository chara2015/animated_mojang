package net.labymod.core.client.render.font.component;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.BiConsumer;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.IconComponent;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.font.TextOverflowStrategy;
import net.labymod.api.client.render.font.text.StringIterator;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/ComponentSplitter.class */
public class ComponentSplitter {
    private static final String CLIP_TEXT = " ...";

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/ComponentSplitter$LineElement.class */
    interface LineElement {
        float getWidth(TextRenderer textRenderer);

        Component toComponent();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static List<RenderableComponent> render(Component component, float maxWidth, int maxLines, float lineSpacing, TextOverflowStrategy overflowStrategy, HorizontalAlignment alignment, boolean useChatOptions, boolean maxLinesClipText) throws MatchException {
        TextRenderer renderer = Laby.references().textRenderer();
        List<Component> lines = splitText(component, maxWidth, renderer);
        int length = lines.size();
        if (length == 0) {
            return Collections.emptyList();
        }
        if (maxLines > 0) {
            if (length < maxLines) {
                maxLines = length;
            }
            if (maxLinesClipText && length > maxLines) {
                Component line = lines.get(maxLines - 1);
                lines.set(maxLines - 1, line.append(Component.text("...")));
            }
            List<Component> newLines = new ArrayList<>(maxLines);
            for (int index = 0; index < maxLines; index++) {
                newLines.add(lines.get(index));
            }
            lines = newLines;
            length = lines.size();
        }
        boolean clipped = false;
        if (overflowStrategy == TextOverflowStrategy.CLIP && maxWidth != -1.0f && length > 1) {
            Component firstLine = (Component) lines.getFirst();
            lines = new ArrayList(1);
            lines.add(firstLine);
            clipped = true;
        }
        return renderLines(lines, clipped, renderer, lineSpacing, alignment, maxWidth, useChatOptions);
    }

    private static List<RenderableComponent> renderLines(List<Component> lines, boolean clipped, TextRenderer renderer, float lineSpacing, HorizontalAlignment alignment, float maxWidth, boolean useChatOptions) {
        List<RenderableComponent> result = new ArrayList<>(lines.size());
        ComponentFlattener flattener = Laby.references().componentRenderer().getFlattener();
        float y = 0.0f;
        float fullWidth = 0.0f;
        RenderingFlattenerListener listener = new RenderingFlattenerListener(lineSpacing, useChatOptions);
        for (Component line : lines) {
            if (line != null) {
                flattener.flatten(line, listener);
                List<RenderableComponent> components = new ArrayList<>(listener.getComponents());
                listener.reset();
                if (clipped) {
                    clip(components, maxWidth, renderer);
                }
                RenderableComponent merged = RenderableComponent.merge(components);
                merged.setLineYOffset(y, true);
                merged.setClipped(clipped);
                result.add(merged);
                fullWidth = Math.max(fullWidth, merged.getWidth());
                y += renderer.getLineHeight() + lineSpacing;
            }
        }
        for (RenderableComponent line2 : result) {
            if (alignment == HorizontalAlignment.CENTER) {
                line2.addXOffsetWithChildren((fullWidth / 2.0f) - (line2.getWidth() / 2.0f));
            } else if (alignment == HorizontalAlignment.RIGHT) {
                line2.addXOffsetWithChildren(fullWidth - line2.getWidth());
            }
        }
        return result;
    }

    private static void clip(List<RenderableComponent> components, float maxWidth, TextRenderer renderer) {
        String strTrimTextToWidth;
        while (true) {
            float space = maxWidth;
            Iterator<RenderableComponent> it = components.iterator();
            while (it.hasNext()) {
                space -= it.next().getWidth();
            }
            float required = renderer.getWidth(CLIP_TEXT);
            float missing = required - space;
            if (missing > 0.0f && !components.isEmpty()) {
                RenderableComponent current = (RenderableComponent) components.getLast();
                if (current.getIcon() != null) {
                    components.removeLast();
                } else {
                    float trimWidth = current.getSingleWidth() - missing;
                    if (trimWidth <= 0.0f) {
                        strTrimTextToWidth = "";
                    } else {
                        strTrimTextToWidth = renderer.trimTextToWidth(current.getText(), trimWidth);
                    }
                    String trimmed = strTrimTextToWidth;
                    if (trimmed.isEmpty()) {
                        components.removeLast();
                    } else if (trimmed.equals(current.getText())) {
                        break;
                    } else {
                        components.set(components.size() - 1, current.copy(trimmed));
                    }
                }
            } else {
                break;
            }
        }
        int i = components.size() - 1;
        RenderableComponent last = null;
        while (true) {
            if (i < 0) {
                break;
            }
            RenderableComponent c = components.get(i);
            if (c.getText() != null) {
                last = c;
                break;
            }
            i--;
        }
        if (last != null) {
            components.set(i, last.copy(last.getText() + " ..."));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static List<Component> splitText(Component inputComponent, float maxWidth, TextRenderer renderer) throws MatchException {
        CollectingFlattenerListener collector = new CollectingFlattenerListener();
        ComponentRenderer componentRenderer = Laby.references().componentRenderer();
        componentRenderer.getFlattener().flatten(inputComponent, collector);
        ComponentQueue queue = new ComponentQueue(collector.getComponents());
        if (isMaxWidthGreaterThanZero(maxWidth)) {
            splitComponentsByWidth(renderer, maxWidth, queue);
        } else {
            splitComponents(queue);
        }
        return queue.results();
    }

    private static void splitComponents(ComponentQueue queue) {
        TextComponent currentLine = Component.empty();
        for (Component component : queue.entries()) {
            if (component instanceof IconComponent) {
                currentLine = currentLine.append(component);
            } else {
                String content = ((TextComponent) component).getText();
                if (!content.isEmpty()) {
                    if (content.contains("\n")) {
                        String[] entries = content.split("\n");
                        if (entries.length == 0) {
                            queue.addResult(currentLine);
                            currentLine = Component.empty();
                            queue.store();
                        } else {
                            for (String entry : entries) {
                                queue.addResult(currentLine.append(Component.text(entry, component.style())));
                                currentLine = Component.empty();
                            }
                        }
                    } else {
                        Component lastResult = queue.takeComponent();
                        if (lastResult != null) {
                            currentLine = currentLine.append(lastResult);
                        }
                        currentLine = currentLine.append(component);
                    }
                }
            }
        }
        queue.addResult(currentLine);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static void splitComponentsByWidth(TextRenderer renderer, float maxWidth, ComponentQueue queue) throws MatchException {
        List<Component> entries = queue.entries();
        splitComponentsByWidth(renderer, maxWidth, entries, (component, flag) -> {
            queue.addResult(component);
        });
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private static void splitComponentsByWidth(TextRenderer renderer, float maxWidth, List<Component> entries, BiConsumer<Component, Boolean> consumer) throws MatchException {
        List<LineElement> components = new ArrayList<>();
        for (Component entry : entries) {
            if (entry instanceof IconComponent) {
                IconComponent icon = (IconComponent) entry;
                components.add(new LineIconElement(icon));
            } else if (entry instanceof TextComponent) {
                TextComponent textComponent = (TextComponent) entry;
                String text = textComponent.getText();
                if (!text.isEmpty()) {
                    components.add(new LineTextElement(text, textComponent.style()));
                }
            }
        }
        FlatComponents flatComponents = new FlatComponents(components);
        boolean keepSplitting = true;
        boolean endedWithHardNewLine = false;
        boolean shouldPrependContinuationPrefix = false;
        while (keepSplitting) {
            keepSplitting = false;
            LineBreakGlyphConsumer finder = new LineBreakGlyphConsumer(renderer, maxWidth);
            Iterator<LineElement> it = flatComponents.parts.iterator();
            while (true) {
                if (it.hasNext()) {
                    LineElement element = it.next();
                    if (element instanceof LineIconElement) {
                        try {
                            IconComponent icon2 = ((LineIconElement) element).icon();
                            float iconWidth = icon2.getWidth();
                            if (!finder.addIconWidth(iconWidth)) {
                                int splitPosition = finder.getSplitPosition();
                                Style splitStyle = finder.getSplitStyle();
                                Component line = flatComponents.splitAt(splitPosition, 0, splitStyle);
                                consumer.accept(line, Boolean.valueOf(shouldPrependContinuationPrefix));
                                shouldPrependContinuationPrefix = true;
                                keepSplitting = true;
                                break;
                            }
                        } catch (Throwable th) {
                            throw new MatchException(th.toString(), th);
                        }
                    } else {
                        LineTextElement textElement = (LineTextElement) element;
                        String content = textElement.contents();
                        if (content.isEmpty()) {
                            continue;
                        } else {
                            boolean fullyConsumed = StringIterator.iterateFormatted(content, textElement.style(), false, finder);
                            if (!fullyConsumed) {
                                int splitPosition2 = finder.getSplitPosition();
                                Style splitStyle2 = finder.getSplitStyle();
                                char splitChar = flatComponents.charAt(splitPosition2);
                                boolean isHardNewLine = splitChar == '\n';
                                boolean isWhitespaceOrNewLine = isHardNewLine || splitChar == ' ';
                                endedWithHardNewLine = isHardNewLine;
                                Component line2 = flatComponents.splitAt(splitPosition2, isWhitespaceOrNewLine ? 1 : 0, splitStyle2);
                                consumer.accept(line2, Boolean.valueOf(shouldPrependContinuationPrefix));
                                shouldPrependContinuationPrefix = !isHardNewLine;
                                keepSplitting = true;
                            } else {
                                finder.addToOffset(content.length());
                            }
                        }
                    }
                }
            }
        }
        Component remainder = flatComponents.getRemainder();
        if (remainder != null) {
            consumer.accept(remainder, Boolean.valueOf(endedWithHardNewLine));
        } else if (endedWithHardNewLine) {
            consumer.accept(Component.empty(), false);
        }
    }

    private static boolean isMaxWidthGreaterThanZero(float maxWidth) {
        return maxWidth > 0.0f;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/ComponentSplitter$ComponentQueue.class */
    static class ComponentQueue {
        private final List<Component> entries;
        private final List<Component> result = new ArrayList();
        private final List<Component> storage = new ArrayList();

        public ComponentQueue(List<Component> queue) {
            this.entries = queue;
        }

        public void addResult(Component component) {
            this.result.add(component);
        }

        @Nullable
        public Component takeComponent() {
            if (this.result.isEmpty()) {
                return null;
            }
            return (Component) this.result.removeLast();
        }

        public List<Component> entries() {
            return this.entries;
        }

        public List<Component> results() {
            if (!this.result.isEmpty()) {
                store();
            }
            return this.storage;
        }

        public void store() {
            this.storage.addAll(this.result);
            this.result.clear();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/ComponentSplitter$FlatComponents.class */
    static class FlatComponents {
        final List<LineElement> parts;
        String flatPart;

        public FlatComponents(List<LineElement> parts) {
            this.parts = parts;
            StringBuilder bobTheBuilder = new StringBuilder();
            for (LineElement part : this.parts) {
                if (part instanceof LineTextElement) {
                    LineTextElement textElement = (LineTextElement) part;
                    bobTheBuilder.append(textElement.contents());
                }
            }
            this.flatPart = bobTheBuilder.toString();
        }

        public char charAt(int position) {
            return this.flatPart.charAt(position);
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        public Component splitAt(int position, int space, Style style) throws MatchException {
            TextComponent finalComponent = Component.empty();
            ListIterator<LineElement> iterator = this.parts.listIterator();
            int finalPosition = position;
            boolean flag = false;
            while (true) {
                if (!iterator.hasNext()) {
                    break;
                }
                LineElement part = iterator.next();
                if (part instanceof LineIconElement) {
                    try {
                        IconComponent icon = ((LineIconElement) part).icon();
                        if (!flag) {
                            finalComponent = finalComponent.append(icon);
                            iterator.remove();
                        }
                    } catch (Throwable th) {
                        throw new MatchException(th.toString(), th);
                    }
                } else {
                    LineTextElement textElement = (LineTextElement) part;
                    String content = textElement.contents();
                    int length = content.length();
                    if (!flag) {
                        if (finalPosition > length) {
                            finalComponent = finalComponent.append(Component.text(textElement.contents(), textElement.style()));
                            iterator.remove();
                            finalPosition -= length;
                        } else {
                            String substring = content.substring(0, finalPosition);
                            if (!substring.isEmpty()) {
                                finalComponent = finalComponent.append(Component.text(substring, textElement.style()));
                            }
                            finalPosition += space;
                            flag = true;
                        }
                    }
                    if (!flag) {
                        continue;
                    } else if (finalPosition <= length) {
                        String substring2 = content.substring(finalPosition);
                        if (substring2.isEmpty()) {
                            iterator.remove();
                        } else {
                            iterator.set(new LineTextElement(substring2, style));
                        }
                    } else {
                        iterator.remove();
                        finalPosition -= length;
                    }
                }
            }
            this.flatPart = this.flatPart.substring(position + space);
            return finalComponent;
        }

        public Component getRemainder() {
            Component remainder = Component.empty();
            for (LineElement part : this.parts) {
                remainder = remainder.append(part.toComponent());
            }
            this.parts.clear();
            return remainder;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/ComponentSplitter$LineTextElement.class */
    static final class LineTextElement extends Record implements LineElement {
        private final String contents;
        private final Style style;

        LineTextElement(String contents, Style style) {
            this.contents = contents;
            this.style = style;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LineTextElement.class), LineTextElement.class, "contents;style", "FIELD:Lnet/labymod/core/client/render/font/component/ComponentSplitter$LineTextElement;->contents:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/render/font/component/ComponentSplitter$LineTextElement;->style:Lnet/labymod/api/client/component/format/Style;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LineTextElement.class), LineTextElement.class, "contents;style", "FIELD:Lnet/labymod/core/client/render/font/component/ComponentSplitter$LineTextElement;->contents:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/render/font/component/ComponentSplitter$LineTextElement;->style:Lnet/labymod/api/client/component/format/Style;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LineTextElement.class, Object.class), LineTextElement.class, "contents;style", "FIELD:Lnet/labymod/core/client/render/font/component/ComponentSplitter$LineTextElement;->contents:Ljava/lang/String;", "FIELD:Lnet/labymod/core/client/render/font/component/ComponentSplitter$LineTextElement;->style:Lnet/labymod/api/client/component/format/Style;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public String contents() {
            return this.contents;
        }

        public Style style() {
            return this.style;
        }

        @Override // net.labymod.core.client.render.font.component.ComponentSplitter.LineElement
        public float getWidth(TextRenderer renderer) {
            return renderer.getWidth(toComponent());
        }

        @Override // net.labymod.core.client.render.font.component.ComponentSplitter.LineElement
        public Component toComponent() {
            return Component.text(this.contents, this.style);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/ComponentSplitter$LineIconElement.class */
    static final class LineIconElement extends Record implements LineElement {
        private final IconComponent icon;

        LineIconElement(IconComponent icon) {
            this.icon = icon;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LineIconElement.class), LineIconElement.class, "icon", "FIELD:Lnet/labymod/core/client/render/font/component/ComponentSplitter$LineIconElement;->icon:Lnet/labymod/api/client/component/IconComponent;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LineIconElement.class), LineIconElement.class, "icon", "FIELD:Lnet/labymod/core/client/render/font/component/ComponentSplitter$LineIconElement;->icon:Lnet/labymod/api/client/component/IconComponent;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LineIconElement.class, Object.class), LineIconElement.class, "icon", "FIELD:Lnet/labymod/core/client/render/font/component/ComponentSplitter$LineIconElement;->icon:Lnet/labymod/api/client/component/IconComponent;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public IconComponent icon() {
            return this.icon;
        }

        @Override // net.labymod.core.client.render.font.component.ComponentSplitter.LineElement
        public float getWidth(TextRenderer renderer) {
            return this.icon.getWidth();
        }

        @Override // net.labymod.core.client.render.font.component.ComponentSplitter.LineElement
        public Component toComponent() {
            return this.icon;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/ComponentSplitter$LineBreakGlyphConsumer.class */
    static class LineBreakGlyphConsumer implements GlyphProcessor {
        private final TextRenderer textRenderer;
        private final float maxWidth;
        private boolean hadNonZeroWidthChar;
        private float width;
        private int nextChar;
        private int offset;
        private int lastPosition;
        private Style lastStyle;
        private boolean finished;
        private int lineBreak = -1;
        private Style lineBreakStyle = Style.empty();
        private int lastSpace = -1;
        private Style lastSpaceStyle = Style.empty();

        public LineBreakGlyphConsumer(TextRenderer textRenderer, float maxWidth) {
            this.textRenderer = textRenderer;
            this.maxWidth = Math.max(maxWidth, 1.0f);
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor
        public boolean accept(int position, Style style, int codePoint) {
            this.lastPosition = position;
            this.lastStyle = style;
            int finalPosition = position + this.offset;
            switch (codePoint) {
                case 10:
                    return finishIteration(finalPosition, style);
                case 32:
                    this.lastSpace = finalPosition;
                    this.lastSpaceStyle = style;
                    break;
            }
            float width = this.textRenderer.getWidth(codePoint, style);
            this.width += width;
            if (this.hadNonZeroWidthChar && this.width > this.maxWidth) {
                this.finished = true;
                if (this.lastSpace != -1) {
                    return finishIteration(this.lastSpace, this.lastSpaceStyle);
                }
                return finishIteration(finalPosition, style);
            }
            this.hadNonZeroWidthChar |= width != 0.0f;
            this.nextChar = finalPosition + Character.charCount(codePoint);
            return true;
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor
        public void finish() {
            if (this.finished) {
                return;
            }
            this.width = MathHelper.ceil(this.width);
            if (this.hadNonZeroWidthChar && this.width > this.maxWidth) {
                if (this.lastSpace != -1) {
                    finishIteration(this.lastSpace, this.lastSpaceStyle);
                } else {
                    finishIteration(this.lastPosition, this.lastStyle);
                }
            }
        }

        private boolean finishIteration(int position, Style style) {
            this.lineBreak = position;
            this.lineBreakStyle = style;
            return false;
        }

        private boolean lineBreakFound() {
            return this.lineBreak != -1;
        }

        public int getSplitPosition() {
            return lineBreakFound() ? this.lineBreak : this.nextChar;
        }

        public Style getSplitStyle() {
            return this.lineBreakStyle;
        }

        public void addToOffset(int offset) {
            this.offset += offset;
        }

        public boolean addIconWidth(float iconWidth) {
            if (this.hadNonZeroWidthChar && this.width + iconWidth > this.maxWidth) {
                return false;
            }
            this.width += iconWidth;
            this.hadNonZeroWidthChar = true;
            return true;
        }
    }
}
