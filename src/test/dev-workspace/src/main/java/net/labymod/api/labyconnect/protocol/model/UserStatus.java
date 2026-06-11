package net.labymod.api.labyconnect.protocol.model;

import java.util.Locale;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.Color;
import net.labymod.api.util.I18n;
import net.labymod.api.util.TextFormat;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/protocol/model/UserStatus.class */
public enum UserStatus {
    ONLINE("online", (byte) 0, NamedTextColor.DARK_GREEN),
    AWAY("away", (byte) 1, NamedTextColor.GOLD),
    BUSY("busy", (byte) 2, NamedTextColor.RED),
    OFFLINE("invisible", (byte) -1, NamedTextColor.GRAY);

    public static final String TRANSLATION_KEY_PREFIX = "labymod.activity.labyconnect.chat.status.%s";
    private static final UserStatus[] VALUES = values();
    private final byte id;
    private final TextColor textColor;
    private final Color color;
    private final String translationKey;
    private final TranslatableComponent component;

    UserStatus(String key, byte id, TextColor textColor) {
        this.id = id;
        this.textColor = textColor;
        this.color = Color.of(textColor.getValue(), 255);
        this.translationKey = String.format(Locale.ROOT, TRANSLATION_KEY_PREFIX, key);
        this.component = Component.translatable(String.format(Locale.ROOT, TRANSLATION_KEY_PREFIX, TextFormat.DASH_CASE.toCamelCase(name(), true)), textColor);
    }

    public byte getId() {
        return this.id;
    }

    @Override // java.lang.Enum
    public String toString() {
        return I18n.translate(this.translationKey, new Object[0]);
    }

    @NotNull
    public Component component() {
        return this.component;
    }

    public String getLocalTranslationKey() {
        return this.translationKey;
    }

    public String getRemoteTranslationKey(boolean self) {
        String key = this.component.getKey();
        if (this == OFFLINE && self) {
            return this.translationKey;
        }
        return key;
    }

    public Color getColor() {
        return this.color;
    }

    public TextColor textColor() {
        return this.textColor;
    }

    @Deprecated
    @NotNull
    public TextColor chatColor() {
        return textColor();
    }

    @Deprecated
    @NotNull
    public String getName() {
        return name();
    }

    public static UserStatus getById(int id) {
        for (UserStatus status : VALUES) {
            if (status.id == id) {
                return status;
            }
        }
        return OFFLINE;
    }
}
