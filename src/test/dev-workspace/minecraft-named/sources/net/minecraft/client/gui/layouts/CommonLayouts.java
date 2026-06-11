package net.minecraft.client.gui.layouts;

import java.util.function.Consumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/layouts/CommonLayouts.class */
public class CommonLayouts {
    private static final int LABEL_SPACING = 4;

    private CommonLayouts() {
    }

    public static Layout labeledElement(Font $$0, LayoutElement $$1, Component $$2) {
        return labeledElement($$0, $$1, $$2, $$02 -> {
        });
    }

    public static Layout labeledElement(Font $$0, LayoutElement $$1, Component $$2, Consumer<LayoutSettings> $$3) {
        LinearLayout $$4 = LinearLayout.vertical().spacing(4);
        $$4.addChild(new StringWidget($$2, $$0));
        $$4.addChild($$1, $$3);
        return $$4;
    }
}
