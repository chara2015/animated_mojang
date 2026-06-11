package net.labymod.v26_1_2.client.gui.screen;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.VanillaLabyScreen;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.core.client.gui.screen.ModernMouseDraggedHandler;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.v26_1_2.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.PreeditEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/gui/screen/LabyScreenRenderer.class */
public class LabyScreenRenderer extends Screen implements FileDropHandler, LabyScreenAccessor {
    private final VanillaLabyScreen screen;

    public LabyScreenRenderer(LabyScreen screen) {
        super((Component) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(net.labymod.api.client.component.Component.empty()));
        this.screen = new VanillaLabyScreen(screen);
        this.screen.setMouseDraggedHandler(new ModernMouseDraggedHandler());
    }

    protected void init() {
        super.init();
        this.screen.pushCustomFontStack();
        clearWidgets();
        this.screen.init(this.width, this.height);
        this.screen.popCustomFontStack();
    }

    public void tick() {
        this.screen.pushCustomFontStack();
        super.tick();
        this.screen.tick();
        this.screen.popCustomFontStack();
    }

    public void removed() {
        super.removed();
        this.screen.onCloseScreen();
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        this.screen.pushCustomFontStack();
        super.extractRenderState(graphics, mouseX, mouseY, a);
        Stack stack = Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics));
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        VanillaLabyScreen vanillaLabyScreen = this.screen;
        Objects.requireNonNull(vanillaLabyScreen);
        screenContext.runInContext(stack, mouseX, mouseY, a, vanillaLabyScreen::render);
        this.screen.popCustomFontStack();
        graphics.nextStratum();
    }

    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float tickDelta) {
        Stack stack = Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics));
        MutableMouse mouse = new MutableMouse(mouseX, mouseY);
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        VanillaLabyScreen vanillaLabyScreen = this.screen;
        Objects.requireNonNull(vanillaLabyScreen);
        boolean renderBackground = screenContext.runInContextWithState(stack, mouse, tickDelta, vanillaLabyScreen::renderBackground);
        if (renderBackground) {
            return;
        }
        super.extractBackground(graphics, mouseX, mouseY, tickDelta);
    }

    public void resize(int width, int height) {
        this.screen.pushCustomFontStack();
        super.resize(width, height);
        this.screen.resize(width, height);
        this.screen.popCustomFontStack();
    }

    public boolean keyPressed(@NotNull KeyEvent event) {
        Key key = DefaultKeyMapper.lastPressed();
        InputType type = KeyMapper.getInputType(key);
        return this.screen.keyPressed(key, type);
    }

    public boolean keyReleased(@NotNull KeyEvent event) {
        Key key = DefaultKeyMapper.lastReleased();
        InputType type = KeyMapper.getInputType(key);
        return this.screen.keyReleased(key, type);
    }

    public boolean charTyped(@NotNull CharacterEvent event) {
        Key key = DefaultKeyMapper.lastPressed();
        if (KeyMapper.getInputType(key) != InputType.CHARACTER) {
            return false;
        }
        boolean consumed = false;
        for (char character : Character.toChars(event.codepoint())) {
            consumed |= this.screen.charTyped(key, character);
        }
        return consumed;
    }

    public boolean preeditUpdated(PreeditEvent event) {
        return this.screen.handlePreeditText(MinecraftUtil.fromMinecraft(event));
    }

    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();
        int mouseButton = event.button();
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            MouseButton button = DefaultKeyMapper.pressMouse(mouseButton);
            if (button == null) {
                return false;
            }
            return this.screen.mouseClicked(mouse, button);
        });
    }

    public boolean mouseReleased(@NotNull MouseButtonEvent event) {
        double mouseX = event.x();
        double mouseY = event.y();
        int mouseButton = event.button();
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            MouseButton button = DefaultKeyMapper.releaseMouse(mouseButton);
            if (button == null) {
                return false;
            }
            return this.screen.mouseReleased(mouse, button);
        });
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollDeltaX, double scrollDeltaY) {
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            return this.screen.mouseScrolled(mouse, scrollDeltaY);
        });
    }

    public boolean mouseDragged(@NotNull MouseButtonEvent event, double deltaX, double deltaY) {
        if (this.screen.isFirstInTree()) {
            return false;
        }
        double mouseX = event.x();
        double mouseY = event.y();
        int button = event.button();
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
            if (mouseButton == null) {
                return false;
            }
            return this.screen.mouseDragged(mouse, mouseButton, deltaX, deltaY);
        });
    }

    @Override // net.labymod.core.client.gui.screen.accessor.FileDropHandler
    public boolean handleDroppedFiles(MutableMouse mouse, List<Path> files) {
        return this.screen.fileDropped(mouse, files);
    }

    public void onFilesDrop(@NotNull List<Path> paths) {
        MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
        handleDroppedFiles(mouse, paths);
    }

    public boolean isPauseScreen() {
        return screen().isPauseScreen();
    }

    @Override // net.labymod.api.client.gui.screen.LabyScreenAccessor
    public LabyScreen screen() {
        return this.screen.screen();
    }

    public void setParentScreen(ScreenWrapper parentScreen) {
        this.screen.setParentScreen(parentScreen);
    }
}
