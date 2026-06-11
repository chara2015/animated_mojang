package net.labymod.api.client.gui.screen.util.scissor;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector4;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/util/scissor/Scissor.class */
public final class Scissor {
    private final ScreenContext context;
    private final ScissorStack scissorStack = new ScissorStack();
    private final Window window = Laby.labyAPI().minecraft().minecraftWindow();

    public Scissor(ScreenContext context) {
        this.context = context;
    }

    public void push(float y, float height) {
        push(0.0f, y, this.window.getScaledWidth(), height);
    }

    public void push(float x, float y, float width, float height) {
        push(x, y, width, height, true);
    }

    public void push(float x, float y, float width, float height, boolean useFloatingPoint) {
        push(Rectangle.relative(x, y, width, height), useFloatingPoint);
    }

    public void push(Rectangle rectangle) {
        push(rectangle, true);
    }

    public void push(Rectangle rectangle, boolean useFloatingPoint) {
        float left = MathHelper.applyFloatingPointPosition(useFloatingPoint, rectangle.getLeft());
        float top = MathHelper.applyFloatingPointPosition(useFloatingPoint, rectangle.getTop());
        float right = MathHelper.applyFloatingPointPosition(useFloatingPoint, rectangle.getRight());
        float bottom = MathHelper.applyFloatingPointPosition(useFloatingPoint, rectangle.getBottom());
        Rectangle scissorRectangle = Rectangle.absolute(left, top, right, bottom);
        FloatVector4 transform = this.context.stack().transformVector(rectangle);
        pushToStack(Rectangle.absolute(scissorRectangle.getLeft() + transform.getX(), scissorRectangle.getTop() + transform.getY(), scissorRectangle.getRight() + transform.getZ(), scissorRectangle.getBottom() + transform.getW()));
    }

    public void pop() {
        this.scissorStack.pop();
    }

    @Nullable
    public ScissorArea getScissorArea() {
        return this.scissorStack.peek();
    }

    private void pushToStack(Rectangle bounds) {
        this.scissorStack.push(ScissorArea.fromRectangle(this.context.stack(), bounds));
    }
}
