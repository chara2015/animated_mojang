package net.labymod.v1_18_2.platform;

import com.google.common.util.concurrent.Runnables;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<edw> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, ehs.class);
        register(NamedScreen.MULTIPLAYER, egk.class);
        register(NamedScreen.EDIT_SERVER, edd.class);
        register(NamedScreen.MAIN_MENU, eeb.class);
        register(NamedScreen.CHAT_INPUT, ecs.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, edg.class);
        register(NamedScreen.INGAME_MENU, edr.class);
        register(NamedScreen.INVENTORY, efq.class);
        register(NamedScreen.CREATIVE_INVENTORY, efh.class);
        register(NamedScreen.CONNECTING, ecv.class);
        register(NamedScreen.DISCONNECTED, edc.class);
        register(NamedScreen.CREDITS, eed.class);
        register(NamedScreen.CREATE_WORLD, eho.class);
        register(NamedScreen.LEVEL_LOADING, edi.class);
        register(NamedScreen.RECEIVING_LEVEL, edv.class);
        register(NamedScreen.PROGRESS, edu.class);
        register(NamedScreen.GENERIC_MESSAGE, edf.class);
        register(NamedScreen.OPEN_LAN_WORLD, edx.class);
        register(NamedScreen.STATISTICS, eee.class);
        register(NamedScreen.ADVANCEMENTS, eel.class);
        register(NamedScreen.CONFIRM, ecu.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, ehm.class);
        register(NamedScreen.EDIT_BOOK, eez.class);
        register(NamedScreen.OPTIONS, edn.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, edz.class);
        register(NamedScreen.VIDEO_SETTINGS, eec.class);
        register(NamedScreen.LANGUAGE_SELECTION, edh.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, egs.class);
        register(NamedScreen.AUDIO_SETTINGS, eea.class);
        register(NamedScreen.CONTROL_SETTINGS, een.class);
        register(NamedScreen.CHAT_SETTINGS, ecr.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, eco.class);
        register(NamedScreen.KEYBIND_SETTINGS, eep.class);
        register(NamedScreen.MOUSE_SETTINGS, edl.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new ehs(new eeb(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new egk(new eeb(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            edw prevScreen = dyr.D().y;
            if (prevScreen instanceof edf) {
                prevScreen = null;
            }
            return new edn(prevScreen, dyr.D().l);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new ecs("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new edr(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            edw prevScreen = dyr.D().y;
            emx data = new emx(fbt.a("selectServer.defaultName", new Object[0]), "", false);
            return new edb(prevScreen, join -> {
                if (join) {
                    ecv.a(prevScreen, dyr.D(), end.a(data.b), data);
                } else {
                    dyr.D().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, () -> {
            return new eed(false, Runnables.doNothing());
        });
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new edh(dyr.D().y, dyr.D().l, dyr.D().R());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new eco(dyr.D().y, dyr.D().l);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, edx::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<edw, edw> screenFactory) {
        registerFactory(screen, () -> {
            return (edw) screenFactory.apply(dyr.D().y);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof eeu;
    }
}
