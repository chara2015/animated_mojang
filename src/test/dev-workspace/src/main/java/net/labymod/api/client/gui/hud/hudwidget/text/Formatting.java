package net.labymod.api.client.gui.hud.hudwidget.text;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/hud/hudwidget/text/Formatting.class */
public enum Formatting {
    NONE("{key} {value}", "{value} {key}"),
    VALUE_ONLY("{value}", "{value}"),
    COLON("{key}: {value}", "{value} :{key}"),
    BRACKETS("{key}> {value}", "{value} <{key}"),
    SQUARE_BRACKETS("[{key}] {value}", "{value} [{key}]"),
    ROUND_BRACKETS("({key}) {value}", "{value} ({key})"),
    HYPHEN("{key} - {value}", "{value} - {key}"),
    SUFFIX("{value} {key}", "{value} {key}"),
    IN_SQUARE_BRACKETS("[{key} {value}]", "[{value} {key}]"),
    GUILLEMET("{key} » {value}", "{value} « {key}");

    private final String leftFormat;
    private final String rightFormat;

    Formatting(String leftFormat, String rightFormat) {
        this.leftFormat = leftFormat;
        this.rightFormat = rightFormat;
    }

    public String getFormat(boolean leftAlignment) {
        return leftAlignment ? this.leftFormat : this.rightFormat;
    }

    public boolean isEnclosed() {
        String format = getFormat(true);
        return !format.startsWith("{");
    }

    @Override // java.lang.Enum
    public String toString() {
        return getFormat(true).replace("{key}", "K").replace("{value}", "V");
    }

    public Component build(Component key, Component value, boolean leftAlignment, int color) {
        return build(key, value, leftAlignment, TextColor.color(color));
    }

    public Component build(Component key, Component value, boolean leftAlignment, TextColor color) {
        String format = getFormat(leftAlignment);
        Component component = Component.empty();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < format.length(); i++) {
            char character = format.charAt(i);
            if (character == '{') {
                if (buffer.length() > 0) {
                    component = component.append(Component.text(buffer.toString(), color));
                }
                buffer.setLength(0);
            } else if (character == '}') {
                switch (buffer.toString()) {
                    case "key":
                        component = component.append(key);
                        break;
                    case "value":
                        component = component.append(value);
                        break;
                }
                buffer.setLength(0);
            } else {
                buffer.append(character);
            }
        }
        if (buffer.length() > 0) {
            component = component.append(Component.text(buffer.toString(), color));
        }
        return component;
    }
}
