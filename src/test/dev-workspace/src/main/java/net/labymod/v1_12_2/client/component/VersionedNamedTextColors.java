package net.labymod.v1_12_2.client.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/component/VersionedNamedTextColors.class */
public enum VersionedNamedTextColors {
    BLACK(a.a, 0),
    DARK_BLUE(a.b, 170),
    DARK_GREEN(a.c, 43520),
    DARK_AQUA(a.d, 43690),
    DARK_RED(a.e, 11141120),
    DARK_PURPLE(a.f, 11141290),
    GOLD(a.g, 16755200),
    GRAY(a.h, 11184810),
    DARK_GRAY(a.i, 5592405),
    BLUE(a.j, 5592575),
    GREEN(a.k, 5635925),
    AQUA(a.l, 5636095),
    RED(a.m, 16733525),
    LIGHT_PURPLE(a.n, 16733695),
    YELLOW(a.o, 16777045),
    WHITE(a.p, 16777215);

    private static final Set<VersionedTextColor> VALUES;
    private static final Map<String, VersionedTextColor> BY_NAME;
    private static final Map<Integer, VersionedTextColor> BY_VALUE;
    private static final Map<String, VersionedTextColor> BY_HEX;
    private static final Map<a, VersionedTextColor> BY_FORMATTING;
    private final VersionedTextColor textColor;

    static {
        Set<VersionedTextColor> values = new HashSet<>();
        for (VersionedNamedTextColors value : values()) {
            values.add(value.textColor);
        }
        VALUES = Collections.unmodifiableSet(values);
        Map<String, VersionedTextColor> byName = new HashMap<>(VALUES.size());
        Map<Integer, VersionedTextColor> byValue = new HashMap<>(VALUES.size());
        Map<String, VersionedTextColor> byHex = new HashMap<>(VALUES.size());
        Map<a, VersionedTextColor> byFormatting = new HashMap<>(VALUES.size());
        for (VersionedTextColor color : VALUES) {
            byName.put(color.getFormatting().y.toLowerCase(Locale.US), color);
            byValue.put(Integer.valueOf(color.color().get()), color);
            byHex.put(color.getHex(), color);
            byFormatting.put(color.getFormatting(), color);
        }
        BY_NAME = Collections.unmodifiableMap(byName);
        BY_VALUE = Collections.unmodifiableMap(byValue);
        BY_HEX = Collections.unmodifiableMap(byHex);
        BY_FORMATTING = Collections.unmodifiableMap(byFormatting);
    }

    VersionedNamedTextColors(a formatting, int color) {
        this.textColor = new VersionedTextColor(formatting, color);
    }

    public VersionedTextColor textColor() {
        return this.textColor;
    }

    public static VersionedTextColor byName(String name) {
        return BY_NAME.get(name);
    }

    public static VersionedTextColor byHex(String hex) {
        return BY_HEX.get(hex);
    }

    public static VersionedTextColor byValue(int value) {
        return BY_VALUE.get(Integer.valueOf(value));
    }

    public static VersionedTextColor byFormatting(a formatting) {
        if (formatting == null) {
            return null;
        }
        return BY_FORMATTING.get(formatting);
    }
}
