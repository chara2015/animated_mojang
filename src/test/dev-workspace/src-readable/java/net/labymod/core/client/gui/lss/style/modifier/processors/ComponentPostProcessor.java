package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/ComponentPostProcessor.class */
public class ComponentPostProcessor implements PostProcessor {
    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public boolean requiresPostProcessing(String key, Element element, Class<?> type) {
        if (!(element instanceof Function)) {
            return false;
        }
        String name = ((Function) element).getName();
        if (name.equals("text") || name.equals("translatable")) {
            return type == String.class || type == Component.class;
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public Object process(Widget widget, Class<?> type, String key, Element element) throws Exception {
        if (!(element instanceof Function)) {
            throw new IllegalArgumentException("Element is not a function");
        }
        Function function = (Function) element;
        String name = function.getName();
        ProcessedObject[] values = function.firstValues(widget, key);
        String value = values[0].rawValue();
        if (type == String.class) {
            if (name.equals("text")) {
                return value;
            }
            if (name.equals("translatable")) {
                return I18n.translate(value, new Object[0]);
            }
        } else if (type == Component.class) {
            if (name.equals("text")) {
                return Component.text(value);
            }
            if (name.equals("translatable")) {
                return Component.translatable(value, new Component[0]);
            }
        }
        throw new UnsupportedOperationException("Unsupported type or function (type: " + String.valueOf(type) + ", function: " + function.getName() + ")");
    }
}
