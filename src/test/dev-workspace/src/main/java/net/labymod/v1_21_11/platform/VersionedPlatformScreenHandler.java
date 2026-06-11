package net.labymod.v1_21_11.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<gsb> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, gyf.class);
        register(NamedScreen.MULTIPLAYER, gvp.class);
        register(NamedScreen.EDIT_SERVER, grs.class);
        register(NamedScreen.MAIN_MENU, gsd.class);
        register(NamedScreen.CHAT_INPUT, gqy.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, gro.class);
        register(NamedScreen.INGAME_MENU, grx.class);
        register(NamedScreen.INVENTORY, gul.class);
        register(NamedScreen.CREATIVE_INVENTORY, gua.class);
        register(NamedScreen.CONNECTING, grb.class);
        register(NamedScreen.DISCONNECTED, grj.class);
        register(NamedScreen.CREDITS, gre.class);
        register(NamedScreen.REALMS, fzu.class);
        register(NamedScreen.CREATE_WORLD, gxx.class);
        register(NamedScreen.LEVEL_LOADING, grp.class);
        register(NamedScreen.PACK_CONFIRM, c.class);
        register(NamedScreen.PROGRESS, grz.class);
        register(NamedScreen.GENERIC_MESSAGE, grm.class);
        register(NamedScreen.OPEN_LAN_WORLD, gsc.class);
        register(NamedScreen.STATISTICS, gsf.class);
        register(NamedScreen.ADVANCEMENTS, gsl.class);
        register(NamedScreen.CONFIRM, gra.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, gxq.class);
        register(NamedScreen.EDIT_BOOK, gtq.class);
        register(NamedScreen.OPTIONS, gwb.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, gwd.class);
        register(NamedScreen.VIDEO_SETTINGS, gwg.class);
        register(NamedScreen.LANGUAGE_SELECTION, gvy.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, gwo.class);
        register(NamedScreen.AUDIO_SETTINGS, gwe.class);
        register(NamedScreen.CONTROL_SETTINGS, gwh.class);
        register(NamedScreen.CHAT_SETTINGS, gvw.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, gvv.class);
        register(NamedScreen.KEYBIND_SETTINGS, gwj.class);
        register(NamedScreen.MOUSE_SETTINGS, gvz.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new gyf(new gsd(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new gvp(new gsd(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            gsb prevScreen = gfj.V().x;
            if (prevScreen instanceof grm) {
                prevScreen = null;
            }
            return new gwb(prevScreen, gfj.V().k);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new gqy("", false);
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new grx(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            gsb prevScreen = gfj.V().x;
            hit data = new hit(imu.a("selectServer.defaultName", new Object[0]), "", c.c);
            return new gri(prevScreen, join -> {
                if (join) {
                    grb.a(prevScreen, gfj.V(), hjw.a(data.b), data, false, (hix) null);
                } else {
                    gfj.V().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, gre::new);
        registerFactory(NamedScreen.REALMS, fzu::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new gvy(gfj.V().x, gfj.V().k, gfj.V().am());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new gvv(gfj.V().x, gfj.V().k);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, gsc::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<gsb, gsb> screenFactory) {
        registerFactory(screen, () -> {
            return (gsb) screenFactory.apply(gfj.V().x);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof gti;
    }
}
