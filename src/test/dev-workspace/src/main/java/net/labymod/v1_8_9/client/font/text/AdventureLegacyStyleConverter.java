package net.labymod.v1_8_9.client.font.text;

import net.labymod.api.Laby;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.v1_8_9.client.component.VersionedTextColor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/font/text/AdventureLegacyStyleConverter.class */
public class AdventureLegacyStyleConverter {
    private static final char LEGACY_COLOR_CODE = 167;

    public static String getColorCodes(Style style) {
        if (style == null || style.isEmpty()) {
            return "§r";
        }
        StringBuilder builder = new StringBuilder();
        if (style.getColor() != null) {
            builder.append((char) 167).append(getTextColor(style.getColor()));
        }
        for (TextDecoration decoration : TextDecoration.getValues()) {
            if (style.hasDecoration(decoration)) {
                builder.append((char) 167).append(getDecorationCharacter(decoration));
            }
        }
        return builder.toString();
    }

    public static String getDecorationCodes(Style style) {
        if (style == null || style.isEmpty()) {
            return "§r";
        }
        StringBuilder builder = new StringBuilder();
        if (style.getColor() != null && (style.getColor() instanceof VersionedTextColor) && ((VersionedTextColor) style.getColor()).isNamed()) {
            builder.append((char) 167).append(getTextColor(style.getColor()));
        }
        for (TextDecoration decoration : TextDecoration.getValues()) {
            if (style.hasDecoration(decoration)) {
                builder.append((char) 167).append(getDecorationCharacter(decoration));
            }
        }
        return builder.toString();
    }

    private static char getTextColor(TextColor color) {
        return Laby.references().legacyChatFormatting().getColorCode(color);
    }

    private static char getDecorationCharacter(TextDecoration decoration) {
        switch (decoration) {
            case OBFUSCATED:
                return 'k';
            case BOLD:
                return 'l';
            case STRIKETHROUGH:
                return 'm';
            case UNDERLINED:
                return 'n';
            case ITALIC:
                return 'o';
            default:
                return 'r';
        }
    }
}
