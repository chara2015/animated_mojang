package net.labymod.api.client.gui.screen;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/ScreenService.class */
@Referenceable
public interface ScreenService {
    void register(String str, ScreenName screenName);

    void registerFactory(String str, ScreenFactory screenFactory);

    ScreenInstance createScreen(String str);

    String getScreenNameByClass(Class<?> cls);

    boolean isInventory(Object obj);

    void setInventoryCondition(Function<Object, Boolean> function);

    default void registerMinecraft(String name, Class<?> screenClass) {
        register(name, ScreenName.minecraft(screenClass));
    }

    default void registerForge(String name, Class<?> screenClass) {
        register(name, ScreenName.forge(screenClass));
    }

    default void registerFabric(String name, Class<?> screenClass) {
        register(name, ScreenName.fabric(screenClass));
    }

    default void register(GameScreen screen, ScreenName screenName) {
        register(screen.getId(), screenName);
    }

    default void registerFactory(GameScreen screen, ScreenFactory factory) {
        registerFactory(screen.getId(), factory);
    }

    default ScreenInstance createScreen(GameScreen screen) {
        return createScreen(screen.getId());
    }
}
