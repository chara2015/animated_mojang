package net.labymod.v1_8_9.client.gui.screen;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.input.PreeditText;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.VanillaLabyScreen;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gui/screen/LabyScreenRenderer.class */
public class LabyScreenRenderer extends axu implements LabyScreenRendererAccessor, LabyScreenAccessor {
    private final VanillaLabyScreen screen;

    public LabyScreenRenderer(LabyScreen screen) {
        this.screen = new VanillaLabyScreen(screen);
        this.screen.setMouseDraggedHandler(new LegacyMouseDraggedHandler());
    }

    public void a(int mouseX, int mouseY, float partialTicks) {
        float tickDelta = Laby.labyAPI().minecraft().getTickDelta();
        LabyAPI labyAPI = Laby.labyAPI();
        Stack stack = VersionedStackProvider.DEFAULT_STACK;
        Minecraft minecraft = labyAPI.minecraft();
        c();
        this.screen.pushCustomFontStack();
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        MutableMouse mutableMouseMouse = minecraft.mouse();
        VanillaLabyScreen vanillaLabyScreen = this.screen;
        Objects.requireNonNull(vanillaLabyScreen);
        screenContext.runInContext(stack, mutableMouseMouse, tickDelta, vanillaLabyScreen::render);
        this.screen.popCustomFontStack();
    }

    public void c() {
        Stack stack = VersionedStackProvider.DEFAULT_STACK;
        Minecraft minecraft = Laby.labyAPI().minecraft();
        MutableMouse mouse = minecraft.mouse();
        float tickDelta = minecraft.getTickDelta();
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        VanillaLabyScreen vanillaLabyScreen = this.screen;
        Objects.requireNonNull(vanillaLabyScreen);
        boolean renderBackground = screenContext.runInContextWithState(stack, mouse, tickDelta, vanillaLabyScreen::renderBackground);
        if (renderBackground) {
            return;
        }
        super.c();
    }

    public void l() {
        this.j.Z();
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedCharTyped(Key key, char character) {
        if (key.isAction()) {
            return false;
        }
        return this.screen.charTyped(key, character);
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedKeyPressed(Key key, InputType type) {
        return this.screen.keyPressed(key, type);
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedKeyReleased(Key key, InputType type) {
        return this.screen.keyReleased(key, type);
    }

    public boolean handlePreeditText(@Nullable PreeditText text) {
        return this.screen.handlePreeditText(text);
    }

    protected void a(int mouseX, int mouseY, int mouseButton) {
        wrappedMouseClicked(Laby.labyAPI().minecraft().mouse(), mouseButton);
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedMouseClicked(MutableMouse mouse, int mouseButton) {
        MouseButton button = DefaultKeyMapper.pressMouse(mouseButton);
        if (button == null) {
            return false;
        }
        return this.screen.mouseClicked(mouse, button);
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor, net.labymod.core.client.gui.screen.accessor.FileDropHandler
    public boolean handleDroppedFiles(MutableMouse mouse, List<Path> paths) {
        return this.screen.fileDropped(mouse, paths);
    }

    protected void b(int mouseX, int mouseY, int mouseButton) {
        wrappedMouseReleased(Laby.labyAPI().minecraft().mouse(), mouseButton);
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedMouseReleased(MutableMouse mouse, int mouseButton) {
        MouseButton button = DefaultKeyMapper.releaseMouse(mouseButton);
        if (button == null) {
            return false;
        }
        return this.screen.mouseReleased(mouse, button);
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean wrappedMouseClickMove(MutableMouse mouse, int mouseButton, double deltaX, double deltaY) {
        MouseButton button;
        if (this.screen.isFirstInTree() || (button = DefaultKeyMapper.pressMouse(mouseButton)) == null) {
            return false;
        }
        return this.screen.mouseDragged(mouse, button, deltaX, deltaY);
    }

    protected void a(int mouseX, int mouseY, int mouseButton, long lastMouseEvent) {
        VanillaLabyScreen.MouseDraggedHandler mouseDraggedHandler = this.screen.mouseDraggedHandler();
        MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
        FloatVector2 deltaMousePosition = mouseDraggedHandler.getDeltaMousePosition(mouse);
        wrappedMouseClickMove(mouse, mouseButton, deltaMousePosition.getX(), deltaMousePosition.getY());
    }

    public void b() {
        this.n.clear();
        this.screen.pushCustomFontStack();
        this.screen.init(this.l, this.m);
        this.screen.popCustomFontStack();
    }

    public void e() {
        this.screen.pushCustomFontStack();
        this.screen.tick();
        this.screen.popCustomFontStack();
    }

    public void m() {
        this.screen.onCloseScreen();
    }

    public boolean d() {
        return screen().isPauseScreen();
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreenAccessor
    public LabyScreen screen() {
        return this.screen.screen();
    }

    @Override // net.labymod.v1_8_9.client.gui.screen.LabyScreenRendererAccessor
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        return this.screen.mouseScrolled(mouse, Math.max(-1.0d, Math.min(1.0d, scrollDelta)));
    }

    public void a(ave lvt_1_1_, int lvt_2_1_, int lvt_3_1_) {
        super.a(lvt_1_1_, lvt_2_1_, lvt_3_1_);
        Object versionedScreen = screen().mostInnerScreen();
        if (versionedScreen instanceof axu) {
            Laby.fireEvent(new VersionedScreenInitEvent(versionedScreen));
        }
    }

    public void setParentScreen(ScreenWrapper parentScreen) {
        this.screen.setParentScreen(parentScreen);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gui/screen/LabyScreenRenderer$LegacyMouseDraggedHandler.class */
    private static class LegacyMouseDraggedHandler implements VanillaLabyScreen.MouseDraggedHandler {
        private LegacyMouseDraggedHandler() {
        }

        @Override // net.labymod.api.client.gui.screen.VanillaLabyScreen.MouseDraggedHandler
        public FloatVector2 getDeltaMousePosition(Mouse mouse) {
            Window window = Laby.labyAPI().minecraft().minecraftWindow();
            float factor = window.getScaledWidth() / window.getRawWidth();
            float deltaX = org.lwjgl.input.Mouse.getEventDX() * factor;
            float deltaY = (-org.lwjgl.input.Mouse.getEventDY()) * factor;
            return new FloatVector2(deltaX, deltaY);
        }
    }
}
