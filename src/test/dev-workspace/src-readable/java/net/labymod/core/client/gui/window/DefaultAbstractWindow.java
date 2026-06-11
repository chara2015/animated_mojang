package net.labymod.core.client.gui.window;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/window/DefaultAbstractWindow.class */
public abstract class DefaultAbstractWindow implements Window {
    private static final ModifyReason WINDOW_TRANSFORMED = ModifyReason.of("windowTransformed");
    protected static final ModifyReason WINDOW_RESET = ModifyReason.of("windowReset");
    public Rectangle parent;
    protected Object currentScreen;
    protected ScreenWrapper currentScreenWrapper;
    private final Map<String, LssVariable> lssVariables = new HashMap();
    protected final Bounds bounds = new PositionedBounds();
    protected final Bounds absoluteBounds = new PositionedBounds();
    protected boolean alwaysResetBounds = true;

    public abstract void resetBounds();

    public abstract void resetAbsoluteBounds();

    @Override // net.labymod.api.client.gui.window.Window
    public int getScaledWidth() {
        if (this.alwaysResetBounds) {
            resetBounds();
        }
        return (int) this.bounds.getWidth();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public int getScaledHeight() {
        if (this.alwaysResetBounds) {
            resetBounds();
        }
        return (int) this.bounds.getHeight();
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Bounds bounds() {
        if (this.alwaysResetBounds) {
            resetBounds();
        }
        return this.bounds;
    }

    @Override // net.labymod.api.client.gui.window.Window
    public Bounds absoluteBounds() {
        resetAbsoluteBounds();
        return this.absoluteBounds;
    }

    @Override // net.labymod.api.client.gui.window.Window
    public void transform(Parent parentObject, Stack stack, Rectangle rectangle, Runnable runnable) {
        Rectangle parent = this.bounds.copy();
        this.alwaysResetBounds = false;
        stack.push();
        this.bounds.setPosition(this.bounds.getX() + rectangle.getX(), this.bounds.getY() + rectangle.getY(), WINDOW_TRANSFORMED);
        this.bounds.setSize(rectangle.getWidth(), rectangle.getHeight(), WINDOW_TRANSFORMED);
        updateViewPort(stack, rectangle, parent);
        this.parent = parent;
        runnable.run();
        this.bounds.set(parent, WINDOW_TRANSFORMED);
        stack.pop();
        this.alwaysResetBounds = true;
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Parent getParent() {
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.Parent
    public Parent getRoot() {
        return this;
    }

    protected void updateViewPort(Stack stack, Rectangle rectangle, Rectangle parent) {
        float x = rectangle.getX();
        float y = rectangle.getY();
        stack.translate(x, y, 0.0f);
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public Map<String, LssVariable> getLssVariables() {
        return this.lssVariables;
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void updateLssVariable(LssVariable variable) {
        for (Activity openActivity : Laby.references().activityController().getOpenActivities()) {
            openActivity.updateLssVariable(variable);
        }
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void forceUpdateLssVariable(LssVariable variable) {
        for (Activity openActivity : Laby.references().activityController().getOpenActivities()) {
            openActivity.forceUpdateLssVariable(variable);
        }
    }
}
