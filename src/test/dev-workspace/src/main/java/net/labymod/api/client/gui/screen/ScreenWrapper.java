package net.labymod.api.client.gui.screen;

import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.metadata.MetadataExtension;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/ScreenWrapper.class */
public interface ScreenWrapper extends ScreenInstance, MetadataExtension {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/ScreenWrapper$Factory.class */
    @Referenceable
    public interface Factory {
        ScreenWrapper create(Object obj);
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    void onCloseScreen();

    boolean isPauseScreen();

    Object getVersionedScreen();

    boolean isActivity();

    Activity asActivity();

    default void handleClickEvent(ClickEvent event) {
        throw new UnsupportedOperationException("Click event " + String.valueOf(event.action()) + " is not implemented in this version");
    }
}
