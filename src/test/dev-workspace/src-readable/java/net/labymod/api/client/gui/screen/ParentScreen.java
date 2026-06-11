package net.labymod.api.client.gui.screen;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.game.GameScreen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/ParentScreen.class */
public interface ParentScreen extends Parent {
    void displayScreen(ScreenInstance screenInstance);

    void displayScreenRaw(Object obj);

    ScreenWrapper currentScreen();

    LabyScreen currentLabyScreen();

    String getCurrentScreenName();

    Object mostInnerScreen();

    default void displayScreen(String name) {
        ScreenInstance screen = Laby.labyAPI().screenService().createScreen(name);
        if (screen != null) {
            displayScreen(screen);
        }
    }

    default void displayScreen(GameScreen screen) {
        displayScreen(screen.getId());
    }

    default void displayScreen(NamedScreen screen) {
        displayScreen((GameScreen) screen);
    }

    default void closeScreen() {
        displayScreen((ScreenInstance) null);
    }

    default boolean isScreenOpened() {
        return currentScreen() != null;
    }

    default Object getCurrentVersionedScreen() {
        return getCurrentVersionedScreen(false);
    }

    default Object getCurrentVersionedScreen(boolean checkMetadata) {
        ScreenWrapper wrapper = currentScreen();
        if (wrapper == null) {
            return null;
        }
        if (checkMetadata) {
            boolean isPreviousScreen = wrapper.metadata().getBoolean("isPreviousScreen", true);
            if (!isPreviousScreen) {
                return null;
            }
        }
        return wrapper.getVersionedScreen();
    }

    default boolean isScreenDisplayed(String screenName) {
        return screenName.equals(getCurrentScreenName());
    }

    default boolean isScreenDisplayed(GameScreen screen) {
        return screen.getId().equals(getCurrentScreenName());
    }

    default boolean isScreenDisplayed(NamedScreen screen) {
        return isScreenDisplayed((GameScreen) screen);
    }
}
