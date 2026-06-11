package net.labymod.api.client.component.serializer.legacy;

import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.flattener.ComponentFlattener;
import net.labymod.api.client.component.flattener.FlattenerListener;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.serializer.gson.GsonComponentSerializer;
import net.labymod.api.util.ColorUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.include.com.google.common.collect.BiMap;
import org.spongepowered.include.com.google.common.collect.HashBiMap;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/serializer/legacy/LegacyComponentSerializer.class */
public class LegacyComponentSerializer {
    private static final char RESET_CHAR = 'r';
    private static final Map<Character, LegacyComponentSerializer> INSTANCES = new HashMap();
    private static final BiMap<Integer, Character> COLOR_CHARS = HashBiMap.create(16);
    private static final BiMap<TextColor, Character> TEXT_COLOR_CHARS = HashBiMap.create(16);
    private static final BiMap<TextDecoration, Character> DECORATION_CHARS = HashBiMap.create(5);
    private static final LegacyFlattenerListener FLATTENER_LISTENER = new LegacyFlattenerListener();
    private final char character;
    private final ComponentFlattener flattener;

    static {
        registerTextColor(NamedTextColor.BLACK, '0');
        registerTextColor(NamedTextColor.DARK_BLUE, '1');
        registerTextColor(NamedTextColor.DARK_GREEN, '2');
        registerTextColor(NamedTextColor.DARK_AQUA, '3');
        registerTextColor(NamedTextColor.DARK_RED, '4');
        registerTextColor(NamedTextColor.DARK_PURPLE, '5');
        registerTextColor(NamedTextColor.GOLD, '6');
        registerTextColor(NamedTextColor.GRAY, '7');
        registerTextColor(NamedTextColor.DARK_GRAY, '8');
        registerTextColor(NamedTextColor.BLUE, '9');
        registerTextColor(NamedTextColor.GREEN, 'a');
        registerTextColor(NamedTextColor.AQUA, 'b');
        registerTextColor(NamedTextColor.RED, 'c');
        registerTextColor(NamedTextColor.LIGHT_PURPLE, 'd');
        registerTextColor(NamedTextColor.YELLOW, 'e');
        registerTextColor(NamedTextColor.WHITE, 'f');
        DECORATION_CHARS.put(TextDecoration.BOLD, 'l');
        DECORATION_CHARS.put(TextDecoration.ITALIC, 'o');
        DECORATION_CHARS.put(TextDecoration.UNDERLINED, 'n');
        DECORATION_CHARS.put(TextDecoration.STRIKETHROUGH, 'm');
        DECORATION_CHARS.put(TextDecoration.OBFUSCATED, 'k');
    }

    private LegacyComponentSerializer(char character, ComponentFlattener flattener) {
        this.character = character;
        this.flattener = flattener;
    }

    private LegacyComponentSerializer(char character) {
        this(character, ComponentFlattener.BASIC);
    }

    public static LegacyComponentSerializer legacySection() {
        return INSTANCES.computeIfAbsent((char) 167, (v1) -> {
            return new LegacyComponentSerializer(v1);
        });
    }

    public static LegacyComponentSerializer legacyAmpersand() {
        return INSTANCES.computeIfAbsent('&', (v1) -> {
            return new LegacyComponentSerializer(v1);
        });
    }

    public static LegacyComponentSerializer legacy(char character) {
        return INSTANCES.computeIfAbsent(Character.valueOf(character), (v1) -> {
            return new LegacyComponentSerializer(v1);
        });
    }

    public String serialize(Component component) {
        FLATTENER_LISTENER.begin(this.character);
        this.flattener.flatten(component, FLATTENER_LISTENER);
        return FLATTENER_LISTENER.getText();
    }

    public JsonElement deserializeToTree(String input) {
        return GsonComponentSerializer.gson().serializeToTree(deserialize(input));
    }

    public Component deserialize(@Nullable String input) {
        if (input == null) {
            return Component.empty();
        }
        int nextSection = input.lastIndexOf(this.character, input.length() - 2);
        if (nextSection == -1) {
            return Component.text(input);
        }
        boolean reset = false;
        int pos = input.length();
        TextComponent.Builder current = null;
        List<Component> parts = new ArrayList<>();
        do {
            char format = input.charAt(nextSection + 1);
            TextColor textColor = null;
            TextDecoration decoration = null;
            if (format != RESET_CHAR) {
                textColor = (TextColor) TEXT_COLOR_CHARS.inverse().get(Character.valueOf(format));
                if (textColor == null) {
                    decoration = (TextDecoration) DECORATION_CHARS.inverse().get(Character.valueOf(format));
                }
            }
            if (format == RESET_CHAR || textColor != null || decoration != null) {
                int from = nextSection + 2;
                if (from != pos) {
                    if (current != null) {
                        if (reset) {
                            parts.add(current.build());
                            reset = false;
                            current = Component.text();
                        } else {
                            current = Component.text().append(current.build());
                        }
                    } else {
                        current = Component.text();
                    }
                    current.text(input.substring(from, pos));
                } else if (current == null) {
                    current = Component.text();
                }
                if (!reset) {
                    if (format == RESET_CHAR) {
                        reset = true;
                    } else if (textColor != null) {
                        current.color(textColor);
                        reset = true;
                    } else {
                        current.decorate(decoration);
                    }
                }
                pos = nextSection;
            }
            nextSection = input.lastIndexOf(this.character, nextSection - 1);
        } while (nextSection != -1);
        if (current != null) {
            parts.add(current.build());
        }
        String remaining = pos > 0 ? input.substring(0, pos) : "";
        if (parts.size() == 1 && remaining.isEmpty()) {
            return parts.get(0);
        }
        Collections.reverse(parts);
        return Component.text().text(remaining).append(parts).build();
    }

    public LegacyComponentSerializer withFlattener(ComponentFlattener flattener) {
        return new LegacyComponentSerializer(this.character, flattener);
    }

    private static void registerTextColor(TextColor textColor, char colorChar) {
        if (textColor == null) {
            throw new NullPointerException("Invalid default text color: '" + colorChar + "'");
        }
        int rgb = textColor.getValue();
        COLOR_CHARS.put(Integer.valueOf(rgb), Character.valueOf(colorChar));
        TEXT_COLOR_CHARS.put(textColor, Character.valueOf(colorChar));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/component/serializer/legacy/LegacyComponentSerializer$LegacyFlattenerListener.class */
    private static final class LegacyFlattenerListener implements FlattenerListener {
        private Character character;
        private StringBuilder stringBuilder;
        private final Queue<Style> styleQueue = new LinkedList();

        private LegacyFlattenerListener() {
        }

        public void begin(char character) {
            this.character = Character.valueOf(character);
            this.stringBuilder = new StringBuilder();
            this.styleQueue.clear();
        }

        @Override // net.labymod.api.client.component.flattener.FlattenerListener
        public void push(Component source) {
            this.styleQueue.add(source.style());
        }

        @Override // net.labymod.api.client.component.flattener.FlattenerListener
        public void component(String text) {
            Style style = null;
            for (Style s : this.styleQueue) {
                style = s;
            }
            if (style != null) {
                if (style.getColor() != null) {
                    this.stringBuilder.append(this.character);
                    this.stringBuilder.append(getColorChar(style.getColor()));
                }
                for (Map.Entry<TextDecoration, Character> entry : LegacyComponentSerializer.DECORATION_CHARS.entrySet()) {
                    if (style.hasDecoration(entry.getKey())) {
                        this.stringBuilder.append(this.character);
                        this.stringBuilder.append(entry.getValue());
                    }
                }
            }
            this.stringBuilder.append(text);
        }

        @Override // net.labymod.api.client.component.flattener.FlattenerListener
        public void pop(Component source) {
            this.styleQueue.remove();
        }

        public String getText() {
            return this.stringBuilder.toString();
        }

        private char getColorChar(TextColor textColor) {
            Character character = (Character) LegacyComponentSerializer.COLOR_CHARS.get(Integer.valueOf(textColor.getValue()));
            if (character != null) {
                return character.charValue();
            }
            TextColor closestDefaultTextColor = ColorUtil.getClosestDefaultTextColor(textColor);
            return ((Character) LegacyComponentSerializer.COLOR_CHARS.get(Integer.valueOf(closestDefaultTextColor.getValue()))).charValue();
        }
    }
}
