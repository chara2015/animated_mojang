package net.labymod.api.client.world;

import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/BossBarColor.class */
public enum BossBarColor {
    PINK("pink", NamedTextColor.RED),
    BLUE("blue", NamedTextColor.BLUE),
    RED("red", NamedTextColor.DARK_RED),
    GREEN("green", NamedTextColor.GREEN),
    YELLOW("yellow", NamedTextColor.YELLOW),
    PURPLE("purple", NamedTextColor.DARK_BLUE),
    WHITE("white", NamedTextColor.WHITE);

    private static final BossBarColor[] VALUES = values();
    private final String name;
    private final TextColor color;

    BossBarColor(String name, TextColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public TextColor color() {
        return this.color;
    }

    public static BossBarColor[] getValues() {
        return VALUES;
    }

    public static BossBarColor getByName(String name) {
        for (BossBarColor value : VALUES) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No boss bar color \"" + name + "\"");
    }

    public static BossBarColor get(String name) {
        for (BossBarColor value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + BossBarColor.class.getCanonicalName() + "." + name);
    }

    public static BossBarColor getOrDefault(String name, BossBarColor defaultValue) {
        for (BossBarColor value : VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return defaultValue;
    }
}
