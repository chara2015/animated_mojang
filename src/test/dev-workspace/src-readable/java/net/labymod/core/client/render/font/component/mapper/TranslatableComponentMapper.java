package net.labymod.core.client.render.font.component.mapper;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.labymod.api.Laby;
import net.labymod.api.adventure.ComponentUtils;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.flattener.ComplexMapper;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.loader.DefaultLabyModLoader;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/mapper/TranslatableComponentMapper.class */
public class TranslatableComponentMapper implements ComplexMapper<TranslatableComponent> {
    private static final Logging LOGGER = Logging.create((Class<?>) TranslatableComponentMapper.class, () -> {
        LabyModLoader loader = DefaultLabyModLoader.getInstance();
        return loader.isLabyModDevelopmentEnvironment() || loader.isAddonDevelopmentEnvironment();
    });
    private static final Pattern FORMAT_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

    @Override // net.labymod.api.client.component.flattener.ComplexMapper
    public /* bridge */ /* synthetic */ void exception(TranslatableComponent translatableComponent, Consumer consumer, Exception exc) {
        exception2(translatableComponent, (Consumer<Component>) consumer, exc);
    }

    @Override // net.labymod.api.client.component.flattener.ComplexMapper
    public /* bridge */ /* synthetic */ void map(TranslatableComponent translatableComponent, Consumer consumer) {
        map2(translatableComponent, (Consumer<Component>) consumer);
    }

    private Component mergeStyleRecursive(Component component, Style style) {
        return ComponentUtils.mergeStyleRecursive(component, style, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
    }

    /* JADX INFO: renamed from: map, reason: avoid collision after fix types in other method */
    public void map2(TranslatableComponent component, Consumer<Component> consumer) {
        int i;
        String translation = Laby.labyAPI().minecraft().getTranslationOrDefault(component.getKey(), component.getFallback());
        List<Component> args = component.getArguments();
        if (args.isEmpty()) {
            consumer.accept(Component.text(translation.replace("%%", "%"), component.style()));
            return;
        }
        Matcher matcher = FORMAT_PATTERN.matcher(translation);
        Style style = component.style();
        int incrementalArgIndex = 0;
        int i2 = 0;
        while (true) {
            int currentIndex = i2;
            if (matcher.find(currentIndex)) {
                int start = matcher.start();
                int end = matcher.end();
                if (start > currentIndex) {
                    String text = translation.substring(currentIndex, start);
                    if (text.indexOf(37) != -1) {
                        throw new IllegalArgumentException();
                    }
                    consumer.accept(Component.text(text, style));
                }
                String text2 = matcher.group(2);
                String matchedString = translation.substring(start, end);
                if ("%".equals(text2) && "%%".equals(matchedString)) {
                    consumer.accept(Component.text("%", style));
                } else {
                    if (!"s".equals(text2) && !"d".equals(text2)) {
                        throw new IllegalArgumentException("Unsupported format: '" + matchedString + "'");
                    }
                    String customIndex = matcher.group(1);
                    if (customIndex != null) {
                        i = Integer.parseInt(customIndex) - 1;
                    } else {
                        i = incrementalArgIndex;
                        incrementalArgIndex++;
                    }
                    int index = i;
                    if (index < args.size()) {
                        Component arg = args.get(index);
                        consumer.accept(mergeStyleRecursive(arg, arg.style().merge(component.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET)));
                    }
                }
                i2 = end;
            } else {
                if (currentIndex < translation.length()) {
                    String var10 = translation.substring(currentIndex);
                    if (var10.indexOf(37) != -1) {
                        throw new IllegalArgumentException();
                    }
                    consumer.accept(Component.text(var10, style));
                    return;
                }
                return;
            }
        }
    }

    /* JADX INFO: renamed from: exception, reason: avoid collision after fix types in other method */
    public void exception2(TranslatableComponent value, Consumer<Component> consumer, Exception e) {
        LOGGER.error(e.getMessage(), new Object[0]);
        consumer.accept(Component.text(value.getKey(), value.style()));
    }
}
