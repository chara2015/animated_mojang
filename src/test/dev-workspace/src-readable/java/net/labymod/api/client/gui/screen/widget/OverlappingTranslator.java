package net.labymod.api.client.gui.screen.widget;

import java.util.function.BiConsumer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/OverlappingTranslator.class */
@Referenceable
public interface OverlappingTranslator {
    void translateOverlappingElements(BiConsumer<Object, Stack> biConsumer, Runnable runnable);

    void translate(Object obj, Stack stack);

    boolean isTranslated();
}
