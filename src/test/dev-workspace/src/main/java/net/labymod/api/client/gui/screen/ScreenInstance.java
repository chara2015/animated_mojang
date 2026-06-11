package net.labymod.api.client.gui.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.Interactable;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.event.client.gui.screen.ScreenOpenEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/ScreenInstance.class */
public interface ScreenInstance extends Interactable {
    void resize(int i, int i2);

    void render(ScreenContext screenContext);

    boolean renderBackground(ScreenContext screenContext);

    void renderOverlay(ScreenContext screenContext);

    void renderHoverComponent(ScreenContext screenContext);

    @NotNull
    Object mostInnerScreen();

    @NotNull
    ScreenInstance mostInnerScreenInstance();

    default void onOpenScreen() {
        onVisibilityChanged(true);
        Laby.fireEvent(new ScreenOpenEvent(this));
    }

    default void onCloseScreen() {
        onVisibilityChanged(false);
    }

    default void onVisibilityChanged(boolean visible) {
    }

    default ScreenWrapper wrap() {
        return Laby.labyAPI().minecraft().wrapScreen(this);
    }

    default ScreenInstance unwrap() {
        if (this instanceof ScreenWrapper) {
            Object versioned = ((ScreenWrapper) this).getVersionedScreen();
            if (versioned instanceof LabyScreenAccessor) {
                LabyScreenAccessor labyScreen = (LabyScreenAccessor) versioned;
                if (labyScreen.screen() instanceof Activity) {
                    return labyScreen.screen().mostInnerScreenInstance();
                }
            }
        }
        return this;
    }
}
