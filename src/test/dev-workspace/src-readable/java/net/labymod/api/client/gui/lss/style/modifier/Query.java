package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.client.gui.lss.style.reader.StyleRule;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaRule;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/Query.class */
public interface Query {
    String identifier();

    boolean matches(String str);

    MediaRule process(StyleRule styleRule);
}
