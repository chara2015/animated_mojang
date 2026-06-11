package net.labymod.v26_1.client.gui.window;

import com.mojang.blaze3d.platform.Window;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.window.TooManyIterationsException;
import net.labymod.core.client.gui.window.DefaultAbstractWindow;
import net.labymod.v26_1.client.gui.screen.LabyScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/gui/window/VersionedWindow.class */
public class VersionedWindow extends DefaultAbstractWindow {
    @Override // net.labymod.api.client.gui.window.Window
    public int getRawWidth() {
        return Minecraft.getInstance().getWindow().getWidth();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public int getRawHeight() {
        return Minecraft.getInstance().getWindow().getHeight();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public int getAbsoluteScaledWidth() {
        return Minecraft.getInstance().getWindow().getGuiScaledWidth();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public int getAbsoluteScaledHeight() {
        return Minecraft.getInstance().getWindow().getGuiScaledHeight();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public long getPointer() {
        Window window = Minecraft.getInstance().getWindow();
        if (window == null) {
            return 0L;
        }
        return window.handle();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public float getScale() {
        return Minecraft.getInstance().getWindow().getGuiScale();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public boolean isFocused() {
        return Minecraft.getInstance().isWindowActive();
    }

    @Override // net.labymod.core.client.gui.window.DefaultAbstractWindow
    public void resetBounds() {
        Window window = Minecraft.getInstance().getWindow();
        this.bounds.setBounds(0.0f, 0.0f, window.getGuiScaledWidth(), window.getGuiScaledHeight(), WINDOW_RESET);
        this.absoluteBounds.setBounds(0.0f, 0.0f, window.getGuiScaledWidth(), window.getGuiScaledHeight(), WINDOW_RESET);
    }

    @Override // net.labymod.core.client.gui.window.DefaultAbstractWindow
    public void resetAbsoluteBounds() {
        Window window = Minecraft.getInstance().getWindow();
        this.absoluteBounds.setBounds(0.0f, 0.0f, window.getGuiScaledWidth(), window.getGuiScaledHeight(), WINDOW_RESET);
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public void displayScreen(ScreenInstance screen) {
        if (screen == null) {
            displayScreenRaw(null);
            return;
        }
        if (screen instanceof LabyScreen) {
            ((LabyScreen) screen).updatePreviousScreen();
            displayScreenRaw(new LabyScreenRenderer((LabyScreen) screen));
        }
        if (screen instanceof ScreenWrapper) {
            displayScreenRaw(((ScreenWrapper) screen).getVersionedScreen());
        }
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public void displayScreenRaw(Object screenObject) {
        Laby.labyAPI().minecraft().updateKeyRepeatingMode(false);
        try {
            Minecraft.getInstance().setScreen((Screen) screenObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public ScreenWrapper currentScreen() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen == null) {
            this.currentScreen = null;
            return null;
        }
        if (screen != this.currentScreen) {
            this.currentScreen = screen;
            ScreenWrapper wrapper = Laby.references().screenWrapperFactory().create(screen);
            boolean previousScreen = !(screen instanceof ConfirmScreen);
            wrapper.metadata().set("isPreviousScreen", Boolean.valueOf(previousScreen));
            this.currentScreenWrapper = wrapper;
        }
        return this.currentScreenWrapper;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public LabyScreen currentLabyScreen() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen instanceof LabyScreenRenderer) {
            return ((LabyScreenRenderer) screen).screen();
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public String getCurrentScreenName() {
        Object screen = Minecraft.getInstance().screen;
        if (screen == null) {
            return null;
        }
        if (screen instanceof LabyScreenRenderer) {
            screen = ((LabyScreenRenderer) screen).screen();
        }
        return Laby.labyAPI().screenService().getScreenNameByClass(screen.getClass());
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public Object mostInnerScreen() throws TooManyIterationsException {
        return getMostInnerScreen(Minecraft.getInstance().screen);
    }

    @Override // net.labymod.api.client.gui.window.Window
    public Object getMostInnerScreen(Object screen) {
        if (screen instanceof LabyScreenRenderer) {
            return ((LabyScreenRenderer) screen).screen().mostInnerScreen();
        }
        if (screen instanceof ScreenInstance) {
            return ((ScreenInstance) screen).mostInnerScreen();
        }
        return screen;
    }

    @Override // net.labymod.api.client.gui.window.Window
    public boolean allowCursorChange() {
        return Minecraft.getInstance().getWindow().allowCursorChanges;
    }
}
