package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/PostProcessor.class */
@Referenceable
public interface PostProcessor {
    boolean requiresPostProcessing(String str, Element element, Class<?> cls);

    Object process(Widget widget, Class<?> cls, String str, Element element) throws Exception;

    default int getPriority() {
        return 0;
    }
}
