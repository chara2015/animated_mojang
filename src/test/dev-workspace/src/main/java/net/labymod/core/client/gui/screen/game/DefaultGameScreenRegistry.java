package net.labymod.core.client.gui.screen.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.models.Implements;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/game/DefaultGameScreenRegistry.class */
@Singleton
@Implements(GameScreenRegistry.class)
public class DefaultGameScreenRegistry implements GameScreenRegistry {
    private static final Logging LOGGER = Logging.getLogger();
    private final List<GameScreen> screens = new ArrayList();

    @Override // net.labymod.api.client.gui.screen.game.GameScreenRegistry
    public void register(GameScreen screen) {
        LOGGER.debug("Registering game screen: {}", screen.getId());
        this.screens.add(screen);
    }

    @Override // net.labymod.api.client.gui.screen.game.GameScreenRegistry
    public Collection<GameScreen> getScreens() {
        return this.screens;
    }
}
