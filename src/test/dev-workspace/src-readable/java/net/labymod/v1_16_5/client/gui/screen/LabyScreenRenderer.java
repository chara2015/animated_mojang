package net.labymod.v1_16_5.client.gui.screen;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gui.input.PreeditText;
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
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gui/screen/LabyScreenRenderer.class */
public class LabyScreenRenderer extends dot implements FileDropHandler, LabyScreenAccessor {
    private final VanillaLabyScreen screen;

    public LabyScreenRenderer(LabyScreen screen) {
        super((nr) Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(Component.empty()));
        this.screen = new VanillaLabyScreen(screen);
    }

    protected void b() {
        super.b();
        this.screen.pushCustomFontStack();
        this.m.clear();
        this.screen.init(this.k, this.l);
        this.screen.popCustomFontStack();
    }

    public void d() {
        this.screen.pushCustomFontStack();
        super.d();
        this.screen.tick();
        this.screen.popCustomFontStack();
    }

    public void e() {
        super.e();
        this.screen.onCloseScreen();
    }

    public void a(@NotNull dfm poseStack, int mouseX, int mouseY, float tickDelta) {
        this.screen.pushCustomFontStack();
        RenderEnvironmentContext environmentContext = Laby.references().renderEnvironmentContext();
        ScreenContext screenContext = environmentContext.screenContext();
        a(poseStack);
        super.a(poseStack, mouseX, mouseY, tickDelta);
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
        VanillaLabyScreen vanillaLabyScreen = this.screen;
        Objects.requireNonNull(vanillaLabyScreen);
        screenContext.runInContext(stack, mouseX, mouseY, tickDelta, vanillaLabyScreen::render);
        this.i.aE().b().a();
        this.screen.popCustomFontStack();
    }

    public void a(dfm poseStack) {
        Stack stack = ((VanillaStackAccessor) poseStack).stack();
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
        super.a(poseStack);
    }

    public void a(@NotNull djz mc, int width, int height) {
        this.screen.pushCustomFontStack();
        super.a(mc, width, height);
        this.screen.resize(width, height);
        this.screen.popCustomFontStack();
    }

    public boolean a(int keyCode, int value1, int value2) {
        Key key = DefaultKeyMapper.lastPressed();
        InputType type = KeyMapper.getInputType(key);
        return this.screen.keyPressed(key, type);
    }

    public boolean b(int keyCode, int value1, int value2) {
        Key key = DefaultKeyMapper.lastReleased();
        InputType type = KeyMapper.getInputType(key);
        return this.screen.keyReleased(key, type);
    }

    public boolean a(char character, int value1) {
        Key key = DefaultKeyMapper.lastPressed();
        if (KeyMapper.getInputType(key) != InputType.CHARACTER) {
            return false;
        }
        return this.screen.charTyped(key, character);
    }

    public boolean handlePreeditText(@Nullable PreeditText text) {
        return this.screen.handlePreeditText(text);
    }

    public boolean a(double mouseX, double mouseY, int mouseButton) {
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            MouseButton button = DefaultKeyMapper.pressMouse(mouseButton);
            if (button == null) {
                return false;
            }
            return this.screen.mouseClicked(mouse, button);
        });
    }

    public boolean c(double mouseX, double mouseY, int mouseButton) {
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            MouseButton button = DefaultKeyMapper.releaseMouse(mouseButton);
            if (button == null) {
                return false;
            }
            return this.screen.mouseReleased(mouse, button);
        });
    }

    public boolean a(double mouseX, double mouseY, double scroll) {
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            return this.screen.mouseScrolled(mouse, scroll);
        });
    }

    public boolean a(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.screen.isFirstInTree()) {
            return false;
        }
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
            if (mouseButton == null) {
                return false;
            }
            return this.screen.mouseDragged(mouse, mouseButton, deltaX, deltaY);
        });
    }

    @Override // net.labymod.core.client.gui.screen.accessor.FileDropHandler
    public boolean handleDroppedFiles(MutableMouse mouse, List<Path> paths) {
        return this.screen.fileDropped(mouse, paths);
    }

    public void a(@NotNull List<Path> paths) {
        MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
        handleDroppedFiles(mouse, paths);
    }

    public boolean ay_() {
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
