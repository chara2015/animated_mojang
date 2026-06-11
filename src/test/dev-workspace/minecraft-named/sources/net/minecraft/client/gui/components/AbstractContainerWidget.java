package net.minecraft.client.gui.components;

import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/AbstractContainerWidget.class */
public abstract class AbstractContainerWidget extends AbstractScrollArea implements ContainerEventHandler {
    private GuiEventListener focused;
    private boolean isDragging;

    public AbstractContainerWidget(int $$0, int $$1, int $$2, int $$3, Component $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @Override // net.minecraft.client.gui.components.events.ContainerEventHandler
    public final boolean isDragging() {
        return this.isDragging;
    }

    @Override // net.minecraft.client.gui.components.events.ContainerEventHandler
    public final void setDragging(boolean $$0) {
        this.isDragging = $$0;
    }

    @Override // net.minecraft.client.gui.components.events.ContainerEventHandler
    public GuiEventListener getFocused() {
        return this.focused;
    }

    @Override // net.minecraft.client.gui.components.events.ContainerEventHandler
    public void setFocused(GuiEventListener $$0) {
        if (this.focused != null) {
            this.focused.setFocused(false);
        }
        if ($$0 != null) {
            $$0.setFocused(true);
        }
        this.focused = $$0;
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.components.events.GuiEventListener, net.minecraft.client.gui.components.events.ContainerEventHandler
    public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
        return super.nextFocusPath($$0);
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.components.events.GuiEventListener
    public boolean mouseClicked(MouseButtonEvent $$0, boolean $$1) {
        boolean $$2 = updateScrolling($$0);
        return super.mouseClicked($$0, $$1) || $$2;
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.components.events.GuiEventListener, net.minecraft.client.gui.components.events.ContainerEventHandler
    public boolean mouseReleased(MouseButtonEvent $$0) {
        super.mouseReleased($$0);
        return super.mouseReleased($$0);
    }

    @Override // net.minecraft.client.gui.components.AbstractScrollArea, net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.components.events.GuiEventListener, net.minecraft.client.gui.components.events.ContainerEventHandler
    public boolean mouseDragged(MouseButtonEvent $$0, double $$1, double $$2) {
        super.mouseDragged($$0, $$1, $$2);
        return super.mouseDragged($$0, $$1, $$2);
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.components.events.GuiEventListener, net.minecraft.client.gui.components.events.ContainerEventHandler
    public boolean isFocused() {
        return super.isFocused();
    }

    @Override // net.minecraft.client.gui.components.AbstractWidget, net.minecraft.client.gui.components.events.GuiEventListener, net.minecraft.client.gui.components.events.ContainerEventHandler
    public void setFocused(boolean $$0) {
        super.setFocused($$0);
    }
}
