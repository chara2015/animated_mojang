package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.screen.widget.Widget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/lss/style/modifier/FunctionPostProcessor.class */
public interface FunctionPostProcessor extends PostProcessor {
    boolean requiresPostProcessing(String str, Function function, Class<?> cls);

    Object process(Widget widget, Class<?> cls, String str, Function function) throws Exception;

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    default boolean requiresPostProcessing(String key, Element element, Class<?> type) {
        return (element instanceof Function) && requiresPostProcessing(key, (Function) element, type);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    default Object process(Widget widget, Class<?> type, String key, Element element) throws Exception {
        return process(widget, type, key, (Function) element);
    }
}
