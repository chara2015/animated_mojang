package net.labymod.api.client.gui.screen.activity.types;

import java.util.Map;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/types/AbstractLayerActivity.class */
public abstract class AbstractLayerActivity extends SimpleActivity {
    protected ScreenInstance parentScreen;
    protected ParentMode mode = ParentMode.UNDERLAY;
    protected ParentMode renderMode = null;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/types/AbstractLayerActivity$ParentMode.class */
    public enum ParentMode {
        UNDERLAY,
        OVERLAY,
        IGNORE_PARENT
    }

    protected AbstractLayerActivity(ScreenInstance parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.SimpleActivity
    public boolean shouldRenderBackground() {
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        if (this.mode == ParentMode.UNDERLAY) {
            this.parentScreen.initialize(parent);
        }
        super.initialize(parent);
        if (this.mode == ParentMode.OVERLAY) {
            this.parentScreen.initialize(parent);
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void resize(int width, int height) {
        if (this.mode == ParentMode.UNDERLAY) {
            this.parentScreen.resize(width, height);
        }
        super.resize(width, height);
        if (this.mode == ParentMode.OVERLAY) {
            this.parentScreen.resize(width, height);
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        ParentMode mode = this.renderMode != null ? this.renderMode : this.mode;
        context.pushStack();
        transformActivity(context);
        if (mode == ParentMode.UNDERLAY) {
            renderParent(context);
        }
        renderSuper(context);
        if (mode == ParentMode.OVERLAY) {
            renderParent(context);
        }
        context.popStack();
    }

    protected void renderSuper(ScreenContext environment) {
        super.render(environment);
    }

    protected void renderParent(ScreenContext environment) {
        this.parentScreen.render(environment);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void renderOverlay(ScreenContext context) {
        ParentMode mode = this.renderMode != null ? this.renderMode : this.mode;
        if (mode == ParentMode.UNDERLAY) {
            renderParentOverlay(context);
        }
        super.renderOverlay(context);
        if (mode == ParentMode.OVERLAY) {
            renderParentOverlay(context);
        }
    }

    protected void renderParentOverlay(ScreenContext context) {
        this.parentScreen.renderOverlay(context);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        if (this.mode == ParentMode.UNDERLAY) {
            this.parentScreen.onCloseScreen();
        }
        super.onCloseScreen();
        if (this.mode == ParentMode.OVERLAY) {
            this.parentScreen.onCloseScreen();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        if (this.mode == ParentMode.UNDERLAY) {
            this.parentScreen.tick();
        }
        super.tick();
        if (this.mode == ParentMode.OVERLAY) {
            this.parentScreen.tick();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (this.mode == ParentMode.UNDERLAY && this.parentScreen.keyPressed(key, type)) {
            return true;
        }
        if (key != Key.ESCAPE && super.keyPressed(key, type)) {
            return true;
        }
        if (this.mode == ParentMode.OVERLAY) {
            if (key != Key.ESCAPE || !shouldHandleEscape()) {
                return this.parentScreen.keyPressed(key, type);
            }
            return super.keyPressed(key, type);
        }
        return super.keyPressed(key, type);
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyReleased(Key key, InputType type) {
        if ((this.mode == ParentMode.UNDERLAY && this.parentScreen.keyReleased(key, type)) || super.keyReleased(key, type)) {
            return true;
        }
        if (this.mode == ParentMode.OVERLAY) {
            return this.parentScreen.keyReleased(key, type);
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean charTyped(Key key, char character) {
        if ((this.mode == ParentMode.UNDERLAY && this.parentScreen.charTyped(key, character)) || super.charTyped(key, character)) {
            return true;
        }
        if (this.mode == ParentMode.OVERLAY) {
            return this.parentScreen.charTyped(key, character);
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        if ((this.mode == ParentMode.UNDERLAY && this.parentScreen.mouseReleased(mouse, mouseButton)) || super.mouseReleased(mouse, mouseButton)) {
            return true;
        }
        if (this.mode == ParentMode.OVERLAY) {
            return this.parentScreen.mouseReleased(mouse, mouseButton);
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if ((this.mode == ParentMode.UNDERLAY && this.parentScreen.mouseClicked(mouse, mouseButton)) || super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if (this.mode == ParentMode.OVERLAY) {
            return this.parentScreen.mouseClicked(mouse, mouseButton);
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        if ((this.mode == ParentMode.UNDERLAY && this.parentScreen.mouseScrolled(mouse, scrollDelta)) || super.mouseScrolled(mouse, scrollDelta)) {
            return true;
        }
        if (this.mode == ParentMode.OVERLAY) {
            return this.parentScreen.mouseScrolled(mouse, scrollDelta);
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        if ((this.mode == ParentMode.UNDERLAY && this.parentScreen.mouseDragged(mouse, button, deltaX, deltaY)) || super.mouseDragged(mouse, button, deltaX, deltaY)) {
            return true;
        }
        if (this.mode == ParentMode.OVERLAY) {
            return this.parentScreen.mouseDragged(mouse, button, deltaX, deltaY);
        }
        return false;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.ScreenUser
    public void doScreenAction(String action, Map<String, Object> parameters) {
        if (this.mode == ParentMode.UNDERLAY) {
            this.parentScreen.doScreenAction(action, parameters);
        }
        super.doScreenAction(action, parameters);
        if (this.mode == ParentMode.OVERLAY) {
            this.parentScreen.doScreenAction(action, parameters);
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance, net.labymod.api.client.gui.screen.ParentScreen
    @NotNull
    public Object mostInnerScreen() {
        return this.parentScreen.mostInnerScreen();
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    @NotNull
    public ScreenInstance mostInnerScreenInstance() {
        return this.parentScreen.mostInnerScreenInstance();
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreen
    public boolean allowCustomFont() {
        GameScreen screen;
        if ((this.parentScreen instanceof ScreenWrapper) && (screen = GameScreenRegistry.from(((ScreenWrapper) this.parentScreen).getVersionedScreen())) != null) {
            return screen.allowCustomFont();
        }
        return super.allowCustomFont();
    }
}
