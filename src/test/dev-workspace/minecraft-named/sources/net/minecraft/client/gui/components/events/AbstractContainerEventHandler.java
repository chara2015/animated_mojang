package net.minecraft.client.gui.components.events;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/components/events/AbstractContainerEventHandler.class */
public abstract class AbstractContainerEventHandler implements ContainerEventHandler {
    private GuiEventListener focused;
    private boolean isDragging;

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
        if (this.focused == $$0) {
            return;
        }
        if (this.focused != null) {
            this.focused.setFocused(false);
        }
        if ($$0 != null) {
            $$0.setFocused(true);
        }
        this.focused = $$0;
    }
}
