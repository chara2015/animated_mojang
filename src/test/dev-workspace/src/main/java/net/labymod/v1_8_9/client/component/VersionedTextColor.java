package net.labymod.v1_8_9.client.component;

import java.util.Locale;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.Color;
import net.labymod.api.util.ColorUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/component/VersionedTextColor.class */
public class VersionedTextColor implements TextColor {
    private final a formatting;
    private final Color color;
    private final boolean named;
    private String hex;

    private VersionedTextColor(a formatting, Color color, String hex, boolean named) {
        this.named = named;
        this.hex = hex;
        this.color = color;
        if (formatting == null) {
            TextColor closestDefaultTextColor = ColorUtil.getClosestDefaultTextColor(color);
            if (closestDefaultTextColor instanceof VersionedTextColor) {
                this.formatting = ((VersionedTextColor) closestDefaultTextColor).formatting;
                return;
            } else {
                this.formatting = a.p;
                return;
            }
        }
        this.formatting = formatting;
    }

    protected VersionedTextColor(a formatting, int color) {
        this(formatting, Color.of(color), null, true);
    }

    public VersionedTextColor(Color color) {
        this(null, color, null, false);
    }

    public VersionedTextColor(String hex) {
        this(null, Color.of(hex), hex, false);
    }

    @Override // net.labymod.api.client.component.format.TextColor
    public int getValue() {
        return this.color.get();
    }

    @Override // net.labymod.api.client.component.format.TextColor
    public String serialize() {
        return this.formatting == null ? this.hex : this.formatting.e();
    }

    @Override // net.labymod.api.client.component.format.TextColor
    public Color color() {
        return this.color;
    }

    public a getFormatting() {
        return this.formatting;
    }

    public String getHex() {
        if (this.hex == null) {
            this.hex = String.format(Locale.ROOT, "#%06X", Integer.valueOf(this.color.get()));
        }
        return this.hex;
    }

    public boolean isNamed() {
        return this.named;
    }

    public String toString() {
        return this.named ? this.formatting.name().toLowerCase(Locale.ENGLISH) : this.hex;
    }
}
