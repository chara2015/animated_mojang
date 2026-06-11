package net.labymod.api.client.gui.screen.game;

import java.util.Collection;
import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/game/GameScreenRegistry.class */
@Referenceable
public interface GameScreenRegistry {
    void register(GameScreen gameScreen);

    Collection<GameScreen> getScreens();

    static GameScreen from(Object screen) {
        GameScreenRegistry registry = Laby.references().gameScreenRegistry();
        for (GameScreen gameScreen : registry.getScreens()) {
            if (gameScreen.isScreen(screen)) {
                return gameScreen;
            }
        }
        return null;
    }
}
