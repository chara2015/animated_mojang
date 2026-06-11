package net.labymod.v1_21.client.gui.window;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.window.TooManyIterationsException;
import net.labymod.core.client.gui.window.DefaultAbstractWindow;
import net.labymod.v1_21.client.gui.screen.LabyScreenRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/client/gui/window/VersionedWindow.class */
public class VersionedWindow extends DefaultAbstractWindow {
    @Override // net.labymod.api.client.gui.window.Window
    public int getRawWidth() {
        return fgo.Q().aM().l();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public int getRawHeight() {
        return fgo.Q().aM().m();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public int getAbsoluteScaledWidth() {
        return fgo.Q().aM().p();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public int getAbsoluteScaledHeight() {
        return fgo.Q().aM().q();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public long getPointer() {
        fam window = fgo.Q().aM();
        if (window == null) {
            return 0L;
        }
        return window.j();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public float getScale() {
        return (float) fgo.Q().aM().t();
    }

    @Override // net.labymod.api.client.gui.window.Window
    public boolean isFocused() {
        return fgo.Q().aA();
    }

    @Override // net.labymod.core.client.gui.window.DefaultAbstractWindow
    public void resetBounds() {
        fam window = fgo.Q().aM();
        this.bounds.setBounds(0.0f, 0.0f, window.p(), window.q(), WINDOW_RESET);
        this.absoluteBounds.setBounds(0.0f, 0.0f, window.p(), window.q(), WINDOW_RESET);
    }

    @Override // net.labymod.core.client.gui.window.DefaultAbstractWindow
    public void resetAbsoluteBounds() {
        fam window = fgo.Q().aM();
        this.absoluteBounds.setBounds(0.0f, 0.0f, window.p(), window.q(), WINDOW_RESET);
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
            fgo.Q().a((fod) screenObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public ScreenWrapper currentScreen() {
        fod screen = fgo.Q().y;
        if (screen == null) {
            this.currentScreen = null;
            return null;
        }
        if (screen != this.currentScreen) {
            this.currentScreen = screen;
            ScreenWrapper wrapper = Laby.references().screenWrapperFactory().create(screen);
            boolean previousScreen = !(screen instanceof fnb);
            wrapper.metadata().set("isPreviousScreen", Boolean.valueOf(previousScreen));
            this.currentScreenWrapper = wrapper;
        }
        return this.currentScreenWrapper;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public LabyScreen currentLabyScreen() {
        fod screen = fgo.Q().y;
        if (screen instanceof LabyScreenRenderer) {
            return ((LabyScreenRenderer) screen).screen();
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.screen.ParentScreen
    public String getCurrentScreenName() {
        Object screen = fgo.Q().y;
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
        return getMostInnerScreen(fgo.Q().y);
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
}
