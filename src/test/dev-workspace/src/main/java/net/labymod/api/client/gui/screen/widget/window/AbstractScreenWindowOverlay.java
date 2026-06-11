package net.labymod.api.client.gui.screen.widget.window;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.client.gui.ScreenUser;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/window/AbstractScreenWindowOverlay.class */
@Deprecated
public abstract class AbstractScreenWindowOverlay implements ScreenUser, MouseUser, KeyboardUser {
    protected final LabyAPI labyAPI = Laby.labyAPI();
    protected final int identifier;
    protected boolean enabled;

    public abstract void renderWindow(ScreenContext screenContext);

    public abstract void renderWidget(ScreenContext screenContext, Widget widget);

    public abstract void preRenderActivity(ScreenContext screenContext, Activity activity);

    public AbstractScreenWindowOverlay(int identifier) {
        this.identifier = identifier;
    }

    public int getIdentifier() {
        return this.identifier;
    }

    public void widgetPreInitialize(Widget widget, Parent parent) {
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double delta) {
        return false;
    }

    @Override // net.labymod.api.client.gui.MouseUser
    public boolean fileDropped(MutableMouse mouse, List<Path> paths) {
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType inputType) {
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean keyReleased(Key key, InputType type) {
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        return false;
    }

    @Override // net.labymod.api.client.gui.KeyboardUser
    public boolean handlePreeditText(@Nullable PreeditText text) {
        return false;
    }

    @Override // net.labymod.api.client.gui.ScreenUser
    public void doScreenAction(String action, Map<String, Object> parameters) {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
