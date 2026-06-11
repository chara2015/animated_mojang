package net.labymod.api.client.gui.screen.game;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.tag.Taggable;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/game/GameScreen.class */
@ApiStatus.NonExtendable
public interface GameScreen extends Taggable {
    public static final ScreenService SCREEN_SERVICE = Laby.references().screenService();

    String getId();

    boolean allowCustomFont();

    default boolean isOptions() {
        return taggedObject().hasTag(ScreenTags.OPTIONS);
    }

    default ScreenInstance create() {
        return SCREEN_SERVICE.createScreen(this);
    }

    default boolean isScreen(Object screen) {
        if (screen == null) {
            return false;
        }
        if (screen instanceof LabyScreenAccessor) {
            LabyScreenAccessor accessor = (LabyScreenAccessor) screen;
            screen = accessor.screen();
        }
        if (screen instanceof ScreenInstance) {
            ScreenInstance screenInstance = (ScreenInstance) screen;
            screen = screenInstance.mostInnerScreen();
        }
        Class<?> screenClass = screen.getClass();
        String screenName = SCREEN_SERVICE.getScreenNameByClass(screenClass);
        return screenName != null && screenName.equals(getId());
    }
}
