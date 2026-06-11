package net.labymod.api.client.component;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.object.ObjectSprite;
import net.labymod.api.client.component.serializer.Serializer;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.api.util.debug.Preconditions;
import net.labymod.api.util.logging.Logging;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/ComponentService.class */
@Referenceable
public abstract class ComponentService {
    private static final Map<Class<?>, Class<?>> ACTUAL_CLASSES = new HashMap();
    private static final Logging LOGGER = Logging.create((Class<?>) ComponentService.class);
    private final List<Class<?>> reportedTypes = new ArrayList();

    public abstract TextComponent createTextComponent(String str);

    public abstract TextComponent createTextComponent(String str, Style style, List<Component> list);

    public abstract ScoreComponent createScoreComponent(String str, String str2);

    public abstract ScoreComponent createScoreComponent(String str, String str2, Style style, List<Component> list);

    public abstract KeybindComponent createKeybindComponent(String str);

    public abstract KeybindComponent createKeybindComponent(String str, Style style, List<Component> list);

    protected abstract IconComponent createIconComponent(Icon icon);

    protected abstract IconComponent createIconComponent(Icon icon, Style style, List<Component> list);

    public abstract TextComponent createEmptyComponent();

    public abstract <T> HoverEvent<T> createHoverEvent(@NotNull HoverEvent.Action<T> action, @NotNull T t);

    public abstract ClickEvent createClickEvent(@NotNull ClickEvent.Action action, @NotNull String str);

    public abstract TextColor createTextColor(int i);

    public abstract TextColor getParsedTextColor(String str);

    public abstract TranslatableComponent createTranslatableComponent(String str, Component... componentArr);

    public abstract TranslatableComponent createTranslatableComponent(String str, Style style, List<Component> list, List<Component> list2);

    public abstract Style getEmptyStyle();

    public abstract Class<?> getActualVersionedClass(Component component, Class<?> cls);

    public abstract Style createStyleFromBuilder(TextColor textColor, Integer num, Boolean bool, Boolean bool2, Boolean bool3, Boolean bool4, Boolean bool5, ClickEvent clickEvent, HoverEvent<?> hoverEvent, String str, GlyphSourceDescription glyphSourceDescription);

    public abstract Serializer createComponentSerializer();

    public abstract Serializer createStyleSerializer();

    public abstract void applyGsonTypeAdapters(GsonBuilder gsonBuilder);

    protected ComponentService() {
    }

    public static Class<?> getActualClass(Component component) {
        Class<?> cls = component.getClass();
        Class<?> actualClass = ACTUAL_CLASSES.get(cls);
        if (actualClass != null) {
            return actualClass;
        }
        Class<?> actualClass2 = componentService().getActualVersionedClass(component, cls);
        ACTUAL_CLASSES.put(cls, actualClass2);
        return actualClass2;
    }

    public static TextComponent textComponent(String text) {
        Preconditions.notNull(text, "text");
        return componentService().createTextComponent(text);
    }

    public static TextComponent textComponent(String text, Style style, List<Component> children) {
        Preconditions.notNull(text, "text");
        return componentService().createTextComponent(text, style, children);
    }

    public static ScoreComponent scoreComponent(String name, String objective) {
        Preconditions.notNull(name, "name");
        Preconditions.notNull(objective, "objective");
        return componentService().createScoreComponent(name, objective);
    }

    public static ScoreComponent scoreComponent(String name, String objective, Style style, List<Component> children) {
        Preconditions.notNull(name, "name");
        Preconditions.notNull(objective, "objective");
        return componentService().createScoreComponent(name, objective, style, children);
    }

    public static KeybindComponent keybindComponent(String keybind) {
        Preconditions.notNull(keybind, "keybind");
        return componentService().createKeybindComponent(keybind);
    }

    public static KeybindComponent keybindComponent(MinecraftInputMapping keybind) {
        Preconditions.notNull(keybind, "keybind");
        return componentService().createKeybindComponent(keybind.getName());
    }

    public static KeybindComponent keybindComponent(String keybind, Style style, List<Component> children) {
        Preconditions.notNull(keybind, "keybind");
        return componentService().createKeybindComponent(keybind, style, children);
    }

    public static IconComponent iconComponent(Icon icon) {
        Preconditions.notNull(icon, "icon");
        return componentService().createIconComponent(icon);
    }

    public static IconComponent iconComponent(Icon icon, Style style, List<Component> children) {
        Preconditions.notNull(icon, "icon");
        return componentService().createIconComponent(icon, style, children);
    }

    public static ObjectComponent objectComponent(ObjectSprite objectInfo, Style style, List<Component> children) {
        Objects.requireNonNull(objectInfo, "objectInfo");
        return componentService().createObjectComponent(objectInfo, style, children);
    }

    public static TextComponent empty() {
        return componentService().createEmptyComponent();
    }

    public static TranslatableComponent translatableComponent(String key, Component... arguments) {
        Preconditions.notNull(key, "key");
        return componentService().createTranslatableComponent(key, arguments);
    }

    public static TranslatableComponent translatableComponent(String key, Style style, List<Component> children, List<Component> arguments) {
        Preconditions.notNull(key, "key");
        return componentService().createTranslatableComponent(key, style, children, arguments);
    }

    public static <T> HoverEvent<T> hoverEvent(@NotNull HoverEvent.Action<T> action, @NotNull T value) {
        Preconditions.notNull(action, "action");
        Preconditions.notNull(value, "value");
        return componentService().createHoverEvent(action, value);
    }

    public static ClickEvent clickEvent(@NotNull ClickEvent.Action action, @NotNull String value) {
        Preconditions.notNull(action, "action");
        Preconditions.notNull(value, "value");
        return componentService().createClickEvent(action, value);
    }

    public static TextColor textColor(int value) {
        return componentService().createTextColor(value);
    }

    public static TextColor parseTextColor(String value) {
        Preconditions.notNull(value, "value");
        return componentService().getParsedTextColor(value);
    }

    public static Style emptyStyle() {
        return componentService().getEmptyStyle();
    }

    public static Serializer getComponentSerializer() {
        return componentService().createComponentSerializer();
    }

    public static Serializer getStyleSerializer() {
        return componentService().createStyleSerializer();
    }

    public static void applyTypeAdapters(GsonBuilder builder) {
        componentService().applyGsonTypeAdapters(builder);
    }

    public static Style buildStyle(TextColor color, Integer shadowColor, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated, ClickEvent clickEvent, HoverEvent<?> hoverEvent, String insertion, GlyphSourceDescription glyphSourceDescription) {
        return componentService().createStyleFromBuilder(color, shadowColor, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, glyphSourceDescription);
    }

    private static ComponentService componentService() {
        return Laby.references().componentService();
    }

    protected ObjectComponent createObjectComponent(ObjectSprite objectInfo, Style style, List<Component> children) {
        throw new UnsupportedOperationException("Object components are not supported by this version.");
    }

    public void reportMissing(Object object, boolean toSentry) {
        Class<?> clazz = object.getClass();
        if (this.reportedTypes.contains(clazz)) {
            return;
        }
        this.reportedTypes.add(clazz);
        String message = "Component type " + clazz.getName() + " is not supported (" + String.valueOf(object) + ")";
        if (toSentry) {
            LOGGER.error(message, new Object[0]);
        } else {
            LOGGER.info(message, new Object[0]);
        }
        if (toSentry) {
            LabySentry.capture(new IllegalStateException(message));
        }
    }
}
