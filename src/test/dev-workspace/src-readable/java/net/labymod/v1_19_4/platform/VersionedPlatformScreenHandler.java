package net.labymod.v1_19_4.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<etd> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, ext.class);
        register(NamedScreen.MULTIPLAYER, ewa.class);
        register(NamedScreen.EDIT_SERVER, esi.class);
        register(NamedScreen.MAIN_MENU, eti.class);
        register(NamedScreen.CHAT_INPUT, erw.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, esm.class);
        register(NamedScreen.INGAME_MENU, esy.class);
        register(NamedScreen.INVENTORY, eva.class);
        register(NamedScreen.CREATIVE_INVENTORY, eup.class);
        register(NamedScreen.CONNECTING, erz.class);
        register(NamedScreen.DISCONNECTED, esh.class);
        register(NamedScreen.CREDITS, esc.class);
        register(NamedScreen.REALMS, eho.class);
        register(NamedScreen.CREATE_WORLD, exn.class);
        register(NamedScreen.LEVEL_LOADING, eso.class);
        register(NamedScreen.RECEIVING_LEVEL, etc.class);
        register(NamedScreen.PROGRESS, etb.class);
        register(NamedScreen.GENERIC_MESSAGE, esk.class);
        register(NamedScreen.OPEN_LAN_WORLD, ete.class);
        register(NamedScreen.STATISTICS, etl.class);
        register(NamedScreen.ADVANCEMENTS, ets.class);
        register(NamedScreen.CONFIRM, ery.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, exh.class);
        register(NamedScreen.EDIT_BOOK, euh.class);
        register(NamedScreen.OPTIONS, esu.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, etg.class);
        register(NamedScreen.VIDEO_SETTINGS, etj.class);
        register(NamedScreen.LANGUAGE_SELECTION, esn.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, ewi.class);
        register(NamedScreen.AUDIO_SETTINGS, eth.class);
        register(NamedScreen.CONTROL_SETTINGS, etu.class);
        register(NamedScreen.CHAT_SETTINGS, erv.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, err.class);
        register(NamedScreen.KEYBIND_SETTINGS, etw.class);
        register(NamedScreen.MOUSE_SETTINGS, ess.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new ext(new eti(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new ewa(new eti(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            etd prevScreen = emh.N().z;
            if (prevScreen instanceof esk) {
                prevScreen = null;
            }
            return new esu(prevScreen, emh.N().m);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new erw("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new esy(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            etd prevScreen = emh.N().z;
            fdq data = new fdq(fug.a("selectServer.defaultName", new Object[0]), "", false);
            return new esg(prevScreen, join -> {
                if (join) {
                    erz.a(prevScreen, emh.N(), fen.a(data.b), data);
                } else {
                    emh.N().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, esc::new);
        registerFactory(NamedScreen.REALMS, eho::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new esn(emh.N().z, emh.N().m, emh.N().ad());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new err(emh.N().z, emh.N().m);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, ete::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<etd, etd> screenFactory) {
        registerFactory(screen, () -> {
            return (etd) screenFactory.apply(emh.N().z);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof eub;
    }
}
