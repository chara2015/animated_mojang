package net.labymod.v1_8_9.client.gui.window;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.window.TooManyIterationsException;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gui.window.DefaultAbstractWindow;
import net.labymod.v1_8_9.client.gui.screen.LabyScreenRenderer;
import org.lwjgl.opengl.Display;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gui/window/VersionedWindow.class */
public class VersionedWindow extends DefaultAbstractWindow {
    private static final Logging LOGGER = Logging.getLogger();
    private avr scaledResolution;

    @Override // net.labymod.api.client.gui.window.Window
    public int getRawWidth() {
        return ave.A().d;
    }

    @Override // net.labymod.api.client.gui.window.Window
    public int getRawHeight() {
        return ave.A().e;
    }

    @Override // net.labymod.api.client.gui.window.Window
    public int getAbsoluteScaledWidth() {
        return getScaledResolution().a();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public int getAbsoluteScaledHeight() {
        return getScaledResolution().b();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public long getPointer() {
        return Display.getWindowHandle();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public float getScale() {
        return getScaledResolution().e();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public boolean isFocused() {
        return Display.isActive();
    }

    @Override // net.labymod.core.client.gui.window.DefaultAbstractWindow
    public void resetBounds() {
        avr window = getScaledResolution();
        this.bounds.setBounds(0.0f, 0.0f, window.a(), window.b(), WINDOW_RESET);
        this.absoluteBounds.setBounds(0.0f, 0.0f, window.a(), window.b(), WINDOW_RESET);
    }

    @Override // net.labymod.core.client.gui.window.DefaultAbstractWindow
    public void resetAbsoluteBounds() {
        avr window = getScaledResolution();
        this.absoluteBounds.setBounds(0.0f, 0.0f, window.a(), window.b(), WINDOW_RESET);
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public void displayScreen(ScreenInstance screen) {
        if (screen == null) {
            displayScreenRaw(null);
            return;
        }
        if (screen instanceof LabyScreen) {
            LabyScreen labyScreen = (LabyScreen) screen;
            labyScreen.updatePreviousScreen();
            displayScreenRaw(new LabyScreenRenderer(labyScreen));
        } else if (screen instanceof ScreenWrapper) {
            ScreenWrapper screenWrapper = (ScreenWrapper) screen;
            displayScreenRaw(screenWrapper.getVersionedScreen());
        }
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public void displayScreenRaw(Object screenObject) {
        Laby.labyAPI().minecraft().updateKeyRepeatingMode(false);
        try {
            ave.A().a((axu) screenObject);
        } catch (Exception e) {
            LOGGER.error("Could not display screen: " + String.valueOf(screenObject), e);
        }
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public ScreenWrapper currentScreen() {
        axu screen = ave.A().m;
        if (screen == null) {
            this.currentScreen = null;
            return null;
        }
        if (screen != this.currentScreen) {
            this.currentScreen = screen;
            ScreenWrapper wrapper = Laby.references().screenWrapperFactory().create(screen);
            boolean previousScreen = !(screen instanceof awy);
            wrapper.metadata().set("isPreviousScreen", Boolean.valueOf(previousScreen));
            this.currentScreenWrapper = wrapper;
        }
        return this.currentScreenWrapper;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public LabyScreen currentLabyScreen() {
        axu screen = ave.A().m;
        if (screen instanceof LabyScreenRenderer) {
            return ((LabyScreenRenderer) screen).screen();
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public String getCurrentScreenName() {
        Class<?> cls;
        axu screen = ave.A().m;
        if (screen == null) {
            return null;
        }
        if (screen instanceof LabyScreenRenderer) {
            LabyScreenRenderer renderer = (LabyScreenRenderer) screen;
            cls = renderer.screen().getClass();
        } else {
            cls = screen.getClass();
        }
        Class<?> screenClass = cls;
        return Laby.labyAPI().screenService().getScreenNameByClass(screenClass);
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public Object mostInnerScreen() throws TooManyIterationsException {
        return getMostInnerScreen(ave.A().m);
    }

    @Override // net.labymod.api.client.gui.window.Window
    public Object getMostInnerScreen(Object screen) throws TooManyIterationsException {
        if (screen instanceof LabyScreenRenderer) {
            return ((LabyScreenRenderer) screen).screen().mostInnerScreen();
        }
        if (screen instanceof ScreenInstance) {
            return ((ScreenInstance) screen).mostInnerScreen();
        }
        return screen;
    }

    private avr getScaledResolution() {
        ave minecraft = ave.A();
        if (this.scaledResolution != null && !isResolutionOutdated(minecraft)) {
            return this.scaledResolution;
        }
        this.scaledResolution = new avr(minecraft);
        return this.scaledResolution;
    }

    private boolean isResolutionOutdated(ave minecraft) {
        float width;
        float height;
        axu currentScreen = minecraft.m;
        if (currentScreen != null) {
            width = currentScreen.l;
            height = currentScreen.m;
        } else {
            float scale = this.scaledResolution.e();
            width = minecraft.d / scale;
            height = minecraft.e / scale;
        }
        return (width == ((float) this.scaledResolution.a()) && height == ((float) this.scaledResolution.b())) ? false : true;
    }
}
