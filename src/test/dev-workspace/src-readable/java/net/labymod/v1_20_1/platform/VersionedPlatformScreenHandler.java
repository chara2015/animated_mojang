package net.labymod.v1_20_1.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<euq> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, ezg.class);
        register(NamedScreen.MULTIPLAYER, exn.class);
        register(NamedScreen.EDIT_SERVER, etu.class);
        register(NamedScreen.MAIN_MENU, euw.class);
        register(NamedScreen.CHAT_INPUT, eti.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, etz.class);
        register(NamedScreen.INGAME_MENU, eul.class);
        register(NamedScreen.INVENTORY, ewo.class);
        register(NamedScreen.CREATIVE_INVENTORY, ewd.class);
        register(NamedScreen.CONNECTING, etl.class);
        register(NamedScreen.DISCONNECTED, ett.class);
        register(NamedScreen.CREDITS, eto.class);
        register(NamedScreen.REALMS, eiu.class);
        register(NamedScreen.CREATE_WORLD, eza.class);
        register(NamedScreen.LEVEL_LOADING, eub.class);
        register(NamedScreen.RECEIVING_LEVEL, eup.class);
        register(NamedScreen.PROGRESS, euo.class);
        register(NamedScreen.GENERIC_MESSAGE, etx.class);
        register(NamedScreen.OPEN_LAN_WORLD, eur.class);
        register(NamedScreen.STATISTICS, euz.class);
        register(NamedScreen.ADVANCEMENTS, evg.class);
        register(NamedScreen.CONFIRM, etk.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, eyu.class);
        register(NamedScreen.EDIT_BOOK, evv.class);
        register(NamedScreen.OPTIONS, euh.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, eut.class);
        register(NamedScreen.VIDEO_SETTINGS, eux.class);
        register(NamedScreen.LANGUAGE_SELECTION, eua.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, exv.class);
        register(NamedScreen.AUDIO_SETTINGS, euu.class);
        register(NamedScreen.CONTROL_SETTINGS, evi.class);
        register(NamedScreen.CHAT_SETTINGS, eth.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, etd.class);
        register(NamedScreen.KEYBIND_SETTINGS, evk.class);
        register(NamedScreen.MOUSE_SETTINGS, euf.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new ezg(new euw(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new exn(new euw(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            euq prevScreen = enn.N().z;
            if (prevScreen instanceof etx) {
                prevScreen = null;
            }
            return new euh(prevScreen, enn.N().m);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new eti("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new eul(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            euq prevScreen = enn.N().z;
            ffd data = new ffd(fvz.a("selectServer.defaultName", new Object[0]), "", false);
            return new ets(prevScreen, join -> {
                if (join) {
                    etl.a(prevScreen, enn.N(), fga.a(data.b), data, false);
                } else {
                    enn.N().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, eto::new);
        registerFactory(NamedScreen.REALMS, eiu::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new eua(enn.N().z, enn.N().m, enn.N().ad());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new etd(enn.N().z, enn.N().m);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, eur::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<euq, euq> screenFactory) {
        registerFactory(screen, () -> {
            return (euq) screenFactory.apply(enn.N().z);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof evp;
    }
}
