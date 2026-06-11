package net.labymod.core.platform;

import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenFactory;
import net.labymod.api.client.gui.screen.ScreenName;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/platform/PlatformScreenHandler.class */
public abstract class PlatformScreenHandler<T> {
    private static final GameScreenRegistry GAME_SCREEN_REGISTRY = Laby.references().gameScreenRegistry();
    private static final ScreenWrapper.Factory SCREEN_WRAPPER_FACTORY = Laby.references().screenWrapperFactory();
    protected final ScreenService screenService = Laby.references().screenService();

    protected abstract void registerFactory(GameScreen gameScreen, Function<T, T> function);

    public abstract void onInitialize();

    public abstract boolean isInventoryScreen(Object obj);

    protected void register(GameScreen screen, Class<?> screenClass) {
        this.screenService.register(screen, ScreenName.minecraft(screenClass));
        GAME_SCREEN_REGISTRY.register(screen);
    }

    protected void registerFactory(GameScreen screen, ScreenFactory factory) {
        this.screenService.registerFactory(screen.getId(), factory);
    }

    protected void registerFactory(GameScreen screen, Supplier<T> screenFactory) {
        registerFactory(screen, () -> {
            return SCREEN_WRAPPER_FACTORY.create(screenFactory.get());
        });
    }

    public ScreenService getScreenService() {
        return this.screenService;
    }
}
