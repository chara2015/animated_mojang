package net.labymod.core.client.gui.screen.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/DefaultOverlappingTranslator.class */
@Singleton
@Implements(OverlappingTranslator.class)
public class DefaultOverlappingTranslator implements OverlappingTranslator {
    private final List<BiConsumer<Object, Stack>> translators = new ArrayList();

    @Override // net.labymod.api.client.gui.screen.widget.OverlappingTranslator
    public void translateOverlappingElements(BiConsumer<Object, Stack> translator, Runnable context) {
        this.translators.add(translator);
        context.run();
        this.translators.remove(translator);
    }

    @Override // net.labymod.api.client.gui.screen.widget.OverlappingTranslator
    public void translate(Object translated, Stack matrix) {
        for (BiConsumer<Object, Stack> translator : this.translators) {
            translator.accept(translated, matrix);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.OverlappingTranslator
    public boolean isTranslated() {
        return !this.translators.isEmpty();
    }
}
