package net.labymod.core.client.input;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/input/KeyboardBridge.class */
@Singleton
@Referenceable
public class KeyboardBridge {
    private final LabyAPI labyAPI;
    private final ScreenTreeTopRegistry treeTopRegistry;

    @Inject
    public KeyboardBridge(LabyAPI labyAPI, ScreenTreeTopRegistry treeTopRegistry) {
        this.labyAPI = labyAPI;
        this.treeTopRegistry = treeTopRegistry;
    }

    public boolean onCharTyped(Key key, char character) {
        boolean result = false;
        for (IngameOverlayActivity activity : Laby.labyAPI().ingameOverlay().getActivities()) {
            if (activity.isAcceptingInput()) {
                this.treeTopRegistry.charTyped(ScreenPhase.PRE, activity, key, character);
                if (activity.charTyped(key, character)) {
                    result = true;
                }
                this.treeTopRegistry.charTyped(ScreenPhase.POST, activity, key, character);
            }
        }
        return result;
    }

    public boolean onKeyPress(Key key, InputType inputType) {
        if (key == Key.ESCAPE) {
            return false;
        }
        boolean pressed = false;
        for (IngameOverlayActivity activity : this.labyAPI.ingameOverlay().getActivities()) {
            if (activity.isAcceptingInput()) {
                this.treeTopRegistry.keyPressed(ScreenPhase.PRE, activity, key, inputType);
                if (activity.keyPressed(key, inputType)) {
                    pressed = true;
                }
                this.treeTopRegistry.keyPressed(ScreenPhase.POST, activity, key, inputType);
            }
        }
        return pressed;
    }

    public boolean onKeyRelease(Key key, InputType inputType) {
        if (key == Key.ESCAPE) {
            return false;
        }
        boolean pressed = false;
        for (IngameOverlayActivity activity : this.labyAPI.ingameOverlay().getActivities()) {
            if (activity.isAcceptingInput()) {
                this.treeTopRegistry.keyReleased(ScreenPhase.PRE, activity, key, inputType);
                if (activity.keyReleased(key, inputType)) {
                    pressed = true;
                }
                this.treeTopRegistry.keyReleased(ScreenPhase.POST, activity, key, inputType);
            }
        }
        return pressed;
    }
}
