package net.labymod.core.client.gui.lss.style.modifier.processors;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.Filter;
import net.labymod.api.client.gui.screen.widget.attributes.FilterType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/FilterPostProcessor.class */
public class FilterPostProcessor implements PostProcessor {
    private static final Map<String, FilterType> FUNCTIONS = new HashMap();

    static {
        for (FilterType type : FilterType.values()) {
            FUNCTIONS.put(type.getName(), type);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public boolean requiresPostProcessing(String key, Element element, Class<?> type) {
        return element instanceof Function ? FUNCTIONS.get(((Function) element).getName()) != null : FUNCTIONS.get(element.getRawValue()) != null;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public Object process(Widget widget, Class<?> type, String key, Element element) throws Exception {
        if (element instanceof Function) {
            Function function = (Function) element;
            ProcessedObject[] values = function.firstValues(widget, key);
            return new Filter(FUNCTIONS.get(function.getName()), values);
        }
        return new Filter(FUNCTIONS.get(element.getRawValue()), (ProcessedObject[]) null);
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public int getPriority() {
        return 2;
    }
}
