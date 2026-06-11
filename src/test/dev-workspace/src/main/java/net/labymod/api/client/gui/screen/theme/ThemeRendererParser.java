package net.labymod.api.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/theme/ThemeRendererParser.class */
@Referenceable
public interface ThemeRendererParser {
    <T extends Widget> ThemeRenderer<T> parse(String str);
}
