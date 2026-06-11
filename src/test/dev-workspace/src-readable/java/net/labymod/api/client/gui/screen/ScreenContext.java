package net.labymod.api.client.gui.screen;

import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.state.CanvasSnapshot;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.ScreenCanvasRenderer;
import net.labymod.api.client.gui.screen.widget.cursor.CursorType;
import net.labymod.api.client.render.matrix.Stack;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/ScreenContext.class */
public class ScreenContext {
    private final Minecraft minecraft = Laby.labyAPI().minecraft();
    private final ScreenCanvas screenCanvas = new ScreenCanvas(this);
    private final ScreenCanvasRenderer screenRenderer = new ScreenCanvasRenderer(this.screenCanvas);
    private Stack stack;
    private float tickDelta;
    private MutableMouse mouse;
    private CursorType requestedCursor;

    private ScreenContext() {
    }

    public static ScreenContext create() {
        return new ScreenContext();
    }

    public ScreenCanvas canvas() {
        return this.screenCanvas;
    }

    public void requestCursor(CursorType type) {
        this.requestedCursor = type;
    }

    public void render() {
        this.screenRenderer.render();
        this.screenCanvas.reset();
        if (this.requestedCursor != null) {
            this.requestedCursor.select(this.minecraft.minecraftWindow().getPointer());
            this.requestedCursor = this.requestedCursor == CursorType.DEFAULT ? null : CursorType.DEFAULT;
        }
    }

    public void renderSnapshot(CanvasSnapshot snapshot, boolean firstBlit) {
        this.screenRenderer.renderSnapshot(snapshot, firstBlit);
    }

    public void pushStack() {
        this.stack.push();
    }

    public void translate(float x, float y, float z) {
        this.stack.translate(x, y, z);
    }

    public void rotateX(float angle) {
        this.stack.rotate(angle, 1.0f, 0.0f, 0.0f);
    }

    public void rotateY(float angle) {
        this.stack.rotate(angle, 0.0f, 1.0f, 0.0f);
    }

    public void rotateZ(float angle) {
        this.stack.rotate(angle, 0.0f, 0.0f, 1.0f);
    }

    public void rotateRadiansX(float value) {
        this.stack.rotateRadians(value, 1.0f, 0.0f, 0.0f);
    }

    public void rotateRadiansY(float value) {
        this.stack.rotateRadians(value, 0.0f, 1.0f, 0.0f);
    }

    public void rotateRadiansZ(float value) {
        this.stack.rotateRadians(value, 0.0f, 0.0f, 1.0f);
    }

    public void scale(float x, float y) {
        scale(x, y, 1.0f);
    }

    public void scale(float x, float y, float z) {
        this.stack.scale(x, y, z);
    }

    public void popStack() {
        this.stack.pop();
    }

    public Stack stack() {
        return this.stack;
    }

    public MutableMouse mouse() {
        return this.mouse;
    }

    public void setMouse(MutableMouse mouse) {
        this.mouse = mouse;
    }

    public float getTickDelta() {
        return this.tickDelta;
    }

    public void runInContext(Stack stack, MutableMouse mouse, float tickDelta, Consumer<ScreenContext> action) {
        Stack prevStack = stack();
        MutableMouse prevMouse = mouse();
        float prevTickDelta = getTickDelta();
        try {
            this.stack = stack;
            this.mouse = mouse;
            this.tickDelta = tickDelta;
            action.accept(this);
            this.stack = prevStack;
            this.mouse = prevMouse;
            this.tickDelta = prevTickDelta;
        } catch (Throwable th) {
            this.stack = prevStack;
            this.mouse = prevMouse;
            this.tickDelta = prevTickDelta;
            throw th;
        }
    }

    public void runInContext(Stack stack, int mouseX, int mouseY, float tickDelta, Consumer<ScreenContext> action) {
        Stack prevStack = stack();
        MutableMouse prevMouse = mouse();
        float prevTickDelta = getTickDelta();
        try {
            this.stack = stack;
            this.tickDelta = tickDelta;
            this.minecraft.updateMouse(mouseX, mouseY, mouse -> {
                this.mouse = mouse;
                action.accept(this);
            });
            this.stack = prevStack;
            this.mouse = prevMouse;
            this.tickDelta = prevTickDelta;
        } catch (Throwable th) {
            this.stack = prevStack;
            this.mouse = prevMouse;
            this.tickDelta = prevTickDelta;
            throw th;
        }
    }

    public boolean runInContextWithState(Stack stack, MutableMouse mouse, float tickDelta, Function<ScreenContext, Boolean> state) {
        Stack prevStack = stack();
        MutableMouse prevMouse = mouse();
        float prevTickDelta = getTickDelta();
        try {
            this.stack = stack;
            this.mouse = mouse;
            this.tickDelta = tickDelta;
            boolean zBooleanValue = state.apply(this).booleanValue();
            this.stack = prevStack;
            this.mouse = prevMouse;
            this.tickDelta = prevTickDelta;
            return zBooleanValue;
        } catch (Throwable th) {
            this.stack = prevStack;
            this.mouse = prevMouse;
            this.tickDelta = prevTickDelta;
            throw th;
        }
    }
}
