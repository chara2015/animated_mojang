package net.labymod.api.client.gui.lss.meta;

import java.util.function.Consumer;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/meta/LinkMetaLoader.class */
@Referenceable
public interface LinkMetaLoader {
    void injectMeta(Activity activity);

    void loadMeta(Class<?> cls, Consumer<StyleSheet> consumer);

    void loadStyleSheets();

    LinkMetaList getMeta(Class<?> cls);
}
