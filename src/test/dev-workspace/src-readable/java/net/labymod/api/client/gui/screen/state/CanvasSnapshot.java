package net.labymod.api.client.gui.screen.state;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/CanvasSnapshot.class */
public final class CanvasSnapshot implements LayeredComponent {
    private final List<GuiComponent> capturedComponents;

    public CanvasSnapshot(List<GuiComponent> capturedComponents) {
        this.capturedComponents = capturedComponents;
    }

    public List<GuiComponent> capturedComponents() {
        return this.capturedComponents;
    }

    @Override // net.labymod.api.client.gui.screen.state.LayeredComponent
    public void forEachItem(LayeredElementConsumer consumer) {
        int size = this.capturedComponents.size();
        for (int index = 0; index < size; index++) {
            GuiComponent guiComponent = this.capturedComponents.get(index);
            consumer.accept(guiComponent);
        }
    }

    @Override // net.labymod.api.client.gui.screen.state.LayeredComponent
    public void submitComponent(GuiComponent component) {
        this.capturedComponents.add(component);
    }
}
