package net.labymod.core.client.gui.screen.theme.font;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/font/ThemeFont.class */
public class ThemeFont {

    @SerializedName("default")
    private Font defaultFont;

    @ApiStatus.Experimental
    private List<Font> others = new ArrayList();

    public Font defaultFont() {
        return this.defaultFont;
    }

    @ApiStatus.Experimental
    public List<Font> others() {
        return this.others;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/theme/font/ThemeFont$Font.class */
    public static class Font {
        private String name;
        private String font;

        public Font() {
        }

        public Font(String name, String font) {
            this.name = name;
            this.font = font;
        }

        public String name() {
            return this.name;
        }

        public void name(String name) {
            this.name = name;
        }

        public String font() {
            return this.font;
        }

        public void font(String font) {
            this.font = font;
        }
    }
}
