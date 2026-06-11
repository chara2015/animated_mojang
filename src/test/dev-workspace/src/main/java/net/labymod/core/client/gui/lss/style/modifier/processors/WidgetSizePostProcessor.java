package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.lss.style.modifier.WidgetModifier;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/WidgetSizePostProcessor.class */
public class WidgetSizePostProcessor implements PostProcessor {
    public final WidgetModifier modifier;

    public WidgetSizePostProcessor(WidgetModifier modifier) {
        this.modifier = modifier;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public boolean requiresPostProcessing(String key, Element element, Class<?> type) {
        return type == WidgetSize.class;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public Object process(Widget widget, Class<?> type, String key, Element element) throws Exception {
        String value = element.getRawValue();
        if (value.equals("fit-content")) {
            return WidgetSize.fitContent();
        }
        if (value.equals("maintain-other")) {
            return WidgetSize.maintainOther();
        }
        ProcessedObject object = this.modifier.processValue(widget, Float.TYPE, key, element)[0];
        float result = ((Float) object.value()).floatValue();
        if (object.postProcessor() instanceof PercentagePostProcessor) {
            return WidgetSize.percentage(result);
        }
        return WidgetSize.fixed(result);
    }
}
