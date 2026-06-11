package net.minecraft.client.gui.layouts;

import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractWidget;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/layouts/Layout.class */
public interface Layout extends LayoutElement {
    void visitChildren(Consumer<LayoutElement> consumer);

    @Override // net.minecraft.client.gui.layouts.LayoutElement
    default void visitWidgets(Consumer<AbstractWidget> $$0) {
        visitChildren($$1 -> {
            $$1.visitWidgets($$0);
        });
    }

    default void arrangeElements() {
        visitChildren($$0 -> {
            if ($$0 instanceof Layout) {
                Layout $$1 = (Layout) $$0;
                $$1.arrangeElements();
            }
        });
    }
}
