package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.lss.LssPropertyException;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.util.StringUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/FontSizePostProcessor.class */
public class FontSizePostProcessor implements PostProcessor {
    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public boolean requiresPostProcessing(String key, Element element, Class<?> type) {
        return type == FontSize.class;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public Object process(Widget widget, Class<?> type, String key, Element element) throws Exception {
        FontSize.PredefinedFontSize fontSize;
        String rawValue = element.getRawValue();
        if (!rawValue.contains(".") && (fontSize = FontSize.PredefinedFontSize.of(StringUtil.toUppercase(rawValue))) != null) {
            return FontSize.predefined(fontSize);
        }
        try {
            return FontSize.custom(Float.parseFloat(rawValue));
        } catch (NumberFormatException e) {
            throw new LssPropertyException("Invalid fontSize value: " + rawValue);
        }
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public int getPriority() {
        return 1;
    }
}
