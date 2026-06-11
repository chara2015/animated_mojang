package net.labymod.core.client.gui.screen.tree;

import java.nio.file.Path;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopHandler;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.models.Implements;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.api.util.KeyValue;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/tree/DefaultScreenTreeTopRegistry.class */
@Singleton
@Implements(ScreenTreeTopRegistry.class)
public class DefaultScreenTreeTopRegistry extends DefaultRegistry<ScreenTreeTopHandler> implements ScreenTreeTopRegistry {
    @Inject
    public DefaultScreenTreeTopRegistry() {
    }

    @Override // net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopHandler
    public void mouseClicked(ScreenPhase phase, MouseUser screen, MutableMouse mouse, MouseButton mouseButton) {
        for (KeyValue<ScreenTreeTopHandler> e : getElements()) {
            e.getValue().mouseClicked(phase, screen, mouse, mouseButton);
        }
    }

    @Override // net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopHandler
    public void mouseReleased(ScreenPhase phase, MouseUser screen, MutableMouse mouse, MouseButton mouseButton) {
        for (KeyValue<ScreenTreeTopHandler> e : getElements()) {
            e.getValue().mouseReleased(phase, screen, mouse, mouseButton);
        }
    }

    @Override // net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopHandler
    public void mouseScrolled(ScreenPhase phase, MouseUser screen, MutableMouse mouse, double scrollDelta) {
        for (KeyValue<ScreenTreeTopHandler> e : getElements()) {
            e.getValue().mouseScrolled(phase, screen, mouse, scrollDelta);
        }
    }

    @Override // net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopHandler
    public void mouseDragged(ScreenPhase phase, MouseUser screen, MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        for (KeyValue<ScreenTreeTopHandler> e : getElements()) {
            e.getValue().mouseDragged(phase, screen, mouse, button, deltaX, deltaY);
        }
    }

    @Override // net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopHandler
    public void fileDropped(ScreenPhase phase, MouseUser screen, MutableMouse mouse, List<Path> paths) {
        for (KeyValue<ScreenTreeTopHandler> e : getElements()) {
            e.getValue().fileDropped(phase, screen, mouse, paths);
        }
    }

    @Override // net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopHandler
    public void keyPressed(ScreenPhase phase, KeyboardUser screen, Key key, InputType type) {
        for (KeyValue<ScreenTreeTopHandler> e : getElements()) {
            e.getValue().keyPressed(phase, screen, key, type);
        }
    }

    @Override // net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopHandler
    public void keyReleased(ScreenPhase phase, KeyboardUser screen, Key key, InputType type) {
        for (KeyValue<ScreenTreeTopHandler> e : getElements()) {
            e.getValue().keyReleased(phase, screen, key, type);
        }
    }

    @Override // net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopHandler
    public void charTyped(ScreenPhase phase, KeyboardUser screen, Key key, char character) {
        for (KeyValue<ScreenTreeTopHandler> e : getElements()) {
            e.getValue().charTyped(phase, screen, key, character);
        }
    }
}
