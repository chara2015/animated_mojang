package net.labymod.api.client.gui.screen;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.event.client.gui.screen.FileDroppedEvent;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/VanillaLabyScreen.class */
public class VanillaLabyScreen {
    private static final ScreenTreeTopRegistry TREE_TOP_REGISTRY = Laby.references().screenTreeTopRegistry();
    private static final ScreenCustomFontStack SCREEN_CUSTOM_FONT_STACK = Laby.references().screenCustomFontStack();
    private final LabyScreen screen;
    private ScreenWrapper parentScreen;
    private Point dragStart;
    private MouseButton dragButton;
    private MouseDraggedHandler mouseDraggedHandler = mouse -> {
        return new FloatVector2();
    };

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/VanillaLabyScreen$MouseDraggedHandler.class */
    public interface MouseDraggedHandler {
        FloatVector2 getDeltaMousePosition(Mouse mouse);
    }

    public VanillaLabyScreen(LabyScreen screen) {
        this.screen = screen;
    }

    public void init(int width, int height) {
        this.screen.resize(width, height);
        this.screen.load(Laby.labyAPI().minecraft().minecraftWindow());
    }

    public void tick() {
        try {
            this.screen.tick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCloseScreen() {
        this.screen.onCloseScreen();
    }

    public void render(ScreenContext context) {
        ScreenCanvas canvas = context.canvas();
        canvas.nextLayer();
        this.screen.render(context);
        if (isFirstInTree()) {
            canvas.nextLayer();
            this.screen.renderOverlay(context);
            canvas.nextLayer();
            this.screen.renderHoverComponent(context);
        }
        MutableMouse mouse = context.mouse();
        if (this.dragStart != null && !this.dragStart.matches(mouse)) {
            FloatVector2 deltaMousePosition = this.mouseDraggedHandler.getDeltaMousePosition(mouse);
            mouseDragged(mouse, this.dragButton, deltaMousePosition.getX(), deltaMousePosition.getY());
        }
    }

    public boolean renderBackground(ScreenContext context) {
        return this.screen.renderBackground(context);
    }

    public void resize(int width, int height) {
        this.screen.resize(width, height);
    }

    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        boolean firstInTree = isFirstInTree();
        if (firstInTree) {
            pushCustomFontStack();
            TREE_TOP_REGISTRY.mouseClicked(ScreenPhase.PRE, this.screen, mouse, mouseButton);
            this.dragStart = mouse.copy();
            this.dragButton = mouseButton;
        }
        boolean result = this.screen.mouseClicked(mouse, mouseButton);
        if (firstInTree) {
            TREE_TOP_REGISTRY.mouseClicked(ScreenPhase.POST, this.screen, mouse, mouseButton);
            popCustomFontStack();
        }
        return result;
    }

    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        boolean firstInTree = isFirstInTree();
        if (firstInTree) {
            pushCustomFontStack();
            TREE_TOP_REGISTRY.mouseReleased(ScreenPhase.PRE, this.screen, mouse, mouseButton);
            this.dragStart = null;
            this.dragButton = null;
        }
        boolean result = this.screen.mouseReleased(mouse, mouseButton);
        if (firstInTree) {
            TREE_TOP_REGISTRY.mouseReleased(ScreenPhase.POST, this.screen, mouse, mouseButton);
            popCustomFontStack();
        }
        return result;
    }

    public boolean mouseScrolled(MutableMouse mouse, double scroll) {
        boolean firstInTree = isFirstInTree();
        if (firstInTree) {
            pushCustomFontStack();
            TREE_TOP_REGISTRY.mouseScrolled(ScreenPhase.PRE, this.screen, mouse, scroll);
        }
        boolean result = this.screen.mouseScrolled(mouse, scroll);
        if (firstInTree) {
            TREE_TOP_REGISTRY.mouseScrolled(ScreenPhase.POST, this.screen, mouse, scroll);
            popCustomFontStack();
        }
        return result;
    }

    public boolean mouseDragged(MutableMouse mouse, MouseButton mouseButton, double deltaX, double deltaY) {
        boolean firstInTree = isFirstInTree();
        if (firstInTree) {
            pushCustomFontStack();
            TREE_TOP_REGISTRY.mouseDragged(ScreenPhase.PRE, this.screen, mouse, mouseButton, deltaX, deltaY);
        }
        boolean result = this.screen.mouseDragged(mouse, mouseButton, deltaX, deltaY);
        if (firstInTree) {
            TREE_TOP_REGISTRY.mouseDragged(ScreenPhase.POST, this.screen, mouse, mouseButton, deltaX, deltaY);
            popCustomFontStack();
        }
        return result;
    }

    public boolean charTyped(Key key, char character) {
        boolean firstInTree = isFirstInTree();
        if (firstInTree) {
            pushCustomFontStack();
            TREE_TOP_REGISTRY.charTyped(ScreenPhase.PRE, this.screen, key, character);
        }
        boolean result = this.screen.charTyped(key, character);
        if (firstInTree) {
            TREE_TOP_REGISTRY.charTyped(ScreenPhase.POST, this.screen, key, character);
            popCustomFontStack();
        }
        return result;
    }

    public boolean keyPressed(Key key, InputType type) {
        boolean firstInTree = isFirstInTree();
        if (firstInTree) {
            pushCustomFontStack();
            TREE_TOP_REGISTRY.keyPressed(ScreenPhase.PRE, this.screen, key, type);
        }
        boolean result = this.screen.keyPressed(key, type);
        if (firstInTree) {
            TREE_TOP_REGISTRY.keyPressed(ScreenPhase.POST, this.screen, key, type);
            popCustomFontStack();
        }
        return result;
    }

    public boolean keyReleased(Key key, InputType type) {
        boolean firstInTree = isFirstInTree();
        if (firstInTree) {
            pushCustomFontStack();
            TREE_TOP_REGISTRY.keyReleased(ScreenPhase.PRE, this.screen, key, type);
        }
        boolean result = this.screen.keyReleased(key, type);
        if (firstInTree) {
            TREE_TOP_REGISTRY.keyReleased(ScreenPhase.POST, this.screen, key, type);
            popCustomFontStack();
        }
        return result;
    }

    public boolean handlePreeditText(@Nullable PreeditText text) {
        boolean firstInTree = isFirstInTree();
        if (firstInTree) {
            pushCustomFontStack();
        }
        boolean result = this.screen.handlePreeditText(text);
        if (firstInTree) {
            popCustomFontStack();
        }
        return result;
    }

    public boolean fileDropped(MutableMouse mouse, List<Path> files) {
        FileDroppedEvent event = (FileDroppedEvent) Laby.fireEvent(new FileDroppedEvent(mouse, files));
        if (event.isCancelled()) {
            return true;
        }
        if (isFirstInTree()) {
            TREE_TOP_REGISTRY.fileDropped(ScreenPhase.PRE, this.screen, event.mouse(), event.paths());
        }
        boolean result = this.screen.fileDropped(event.mouse(), event.paths());
        if (isFirstInTree()) {
            TREE_TOP_REGISTRY.fileDropped(ScreenPhase.POST, this.screen, event.mouse(), event.paths());
        }
        return result;
    }

    public boolean isFirstInTree() {
        return this.parentScreen == null;
    }

    public void setParentScreen(ScreenWrapper parentScreen) {
        this.parentScreen = parentScreen;
    }

    public void pushCustomFontStack() {
        SCREEN_CUSTOM_FONT_STACK.push(this.screen);
    }

    public void popCustomFontStack() {
        SCREEN_CUSTOM_FONT_STACK.pop(this.screen);
    }

    public LabyScreen screen() {
        return this.screen;
    }

    public void setMouseDraggedHandler(@NotNull MouseDraggedHandler mouseDraggedHandler) {
        Objects.requireNonNull(mouseDraggedHandler, "mouseDraggedHandler must not be null");
        this.mouseDraggedHandler = mouseDraggedHandler;
    }

    @NotNull
    public MouseDraggedHandler mouseDraggedHandler() {
        return this.mouseDraggedHandler;
    }
}
