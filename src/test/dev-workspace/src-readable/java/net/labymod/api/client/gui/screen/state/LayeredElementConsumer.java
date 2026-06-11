package net.labymod.api.client.gui.screen.state;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/LayeredElementConsumer.class */
@FunctionalInterface
public interface LayeredElementConsumer {
    void accept(GuiComponent guiComponent);

    default LayeredElementConsumer mergeWith(LayeredElementConsumer consumer) {
        return renderState -> {
            consumer.accept(renderState);
            accept(renderState);
        };
    }

    default LayeredElementConsumer andThen(LayeredElementConsumer consumer) {
        return renderState -> {
            accept(renderState);
            consumer.accept(renderState);
        };
    }
}
