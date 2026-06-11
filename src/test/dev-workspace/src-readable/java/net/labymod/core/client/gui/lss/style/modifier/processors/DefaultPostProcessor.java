package net.labymod.core.client.gui.lss.style.modifier.processors;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.PostProcessor;
import net.labymod.api.client.gui.lss.style.modifier.TypeParser;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/lss/style/modifier/processors/DefaultPostProcessor.class */
@Singleton
@Implements(PostProcessor.class)
public class DefaultPostProcessor implements PostProcessor {
    private final TypeParser typeParser;

    @Inject
    public DefaultPostProcessor(TypeParser typeParser) {
        this.typeParser = typeParser;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public boolean requiresPostProcessing(String key, Element element, Class<?> type) {
        return true;
    }

    @Override // net.labymod.api.client.gui.lss.style.modifier.PostProcessor
    public Object process(Widget widget, Class<?> type, String key, Element element) throws Exception {
        return this.typeParser.parseValue(type, element.getRawValue());
    }
}
