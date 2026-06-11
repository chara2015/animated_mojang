package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.LssPropertyException;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ThemeResourceRegistry;
import net.labymod.core.client.gui.lss.style.function.parameter.FixedElement;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/IconPostProcessor.class */
public class IconPostProcessor implements PostProcessor {
    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public boolean requiresPostProcessing(String key, Element element, Class<?> type) {
        return type == Icon.class && (((element instanceof Function) && ((Function) element).getName().equals("sprite")) || (element instanceof FixedElement));
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public Object process(Widget widget, Class<?> type, String key, Element element) throws Exception {
        if (element instanceof Function) {
            ProcessedObject[] values = ((Function) element).firstValues(widget, key);
            ResourceLocation location = (ResourceLocation) values[0].value();
            int x = ((Integer) values[1].value()).intValue();
            int y = ((Integer) values[2].value()).intValue();
            int size = ((Integer) values[3].value()).intValue();
            return Icon.sprite(location, x * size, y * size, size, size, 128, 128);
        }
        String id = element.getRawValue();
        Icon icon = ThemeResourceRegistry.getIcon(id);
        if (icon == null) {
            throw new LssPropertyException("Unknown sprite icon: '" + id + "'");
        }
        return icon;
    }
}
