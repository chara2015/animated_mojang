package net.labymod.core.client.gui.lss.style.modifier.processors;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.style.function.Function;
import net.labymod.api.client.gui.lss.style.modifier.SingleFunctionPostProcessor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/ResourceLocationPostProcessor.class */
public class ResourceLocationPostProcessor extends SingleFunctionPostProcessor {
    private final ResourceLocationFactory resourceLocationFactory;

    public ResourceLocationPostProcessor() {
        super("location");
        this.resourceLocationFactory = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory();
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.FunctionPostProcessor
    public Object process(Widget widget, Class<?> type, String key, Function function) throws Exception {
        Object[] values = function.firstValues(widget, key);
        String namespace = (String) values[0];
        String path = (String) values[1];
        ResourceLocation location = this.resourceLocationFactory.create(namespace, path);
        if (type == Icon.class) {
            return Icon.texture(location);
        }
        return location;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public int getPriority() {
        return 3;
    }
}
