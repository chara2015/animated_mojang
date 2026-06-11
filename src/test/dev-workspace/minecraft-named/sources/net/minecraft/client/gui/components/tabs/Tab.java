package net.minecraft.client.gui.components.tabs;

import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/tabs/Tab.class */
public interface Tab {
    Component getTabTitle();

    Component getTabExtraNarration();

    void visitChildren(Consumer<AbstractWidget> consumer);

    void doLayout(ScreenRectangle screenRectangle);
}
