package net.labymod.v1_21_4.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fum> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, fzt.class);
        register(NamedScreen.MULTIPLAYER, fxc.class);
        register(NamedScreen.EDIT_SERVER, ftu.class);
        register(NamedScreen.MAIN_MENU, fuo.class);
        register(NamedScreen.CHAT_INPUT, fti.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, ftz.class);
        register(NamedScreen.INGAME_MENU, fuh.class);
        register(NamedScreen.INVENTORY, fwc.class);
        register(NamedScreen.CREATIVE_INVENTORY, fvr.class);
        register(NamedScreen.CONNECTING, ftl.class);
        register(NamedScreen.DISCONNECTED, ftt.class);
        register(NamedScreen.CREDITS, fto.class);
        register(NamedScreen.REALMS, fgg.class);
        register(NamedScreen.CREATE_WORLD, fzl.class);
        register(NamedScreen.LEVEL_LOADING, fua.class);
        register(NamedScreen.RECEIVING_LEVEL, fuk.class);
        register(NamedScreen.PACK_CONFIRM, b.class);
        register(NamedScreen.PROGRESS, fuj.class);
        register(NamedScreen.GENERIC_MESSAGE, ftx.class);
        register(NamedScreen.OPEN_LAN_WORLD, fun.class);
        register(NamedScreen.STATISTICS, fuq.class);
        register(NamedScreen.ADVANCEMENTS, fuw.class);
        register(NamedScreen.CONFIRM, ftk.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, fze.class);
        register(NamedScreen.EDIT_BOOK, fvi.class);
        register(NamedScreen.OPTIONS, fxp.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, fxr.class);
        register(NamedScreen.VIDEO_SETTINGS, fxu.class);
        register(NamedScreen.LANGUAGE_SELECTION, fxm.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, fyc.class);
        register(NamedScreen.AUDIO_SETTINGS, fxs.class);
        register(NamedScreen.CONTROL_SETTINGS, fxv.class);
        register(NamedScreen.CHAT_SETTINGS, fxk.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, fxj.class);
        register(NamedScreen.KEYBIND_SETTINGS, fxx.class);
        register(NamedScreen.MOUSE_SETTINGS, fxn.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new fzt(new fuo(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new fxc(new fuo(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            fum prevScreen = flk.Q().z;
            if (prevScreen instanceof ftx) {
                prevScreen = null;
            }
            return new fxp(prevScreen, flk.Q().n);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new fti("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new fuh(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            fum prevScreen = flk.Q().z;
            ggp data = new ggp(hgb.a("selectServer.defaultName", new Object[0]), "", c.c);
            return new fts(prevScreen, join -> {
                if (join) {
                    ftl.a(prevScreen, flk.Q(), ghs.a(data.b), data, false, (ggt) null);
                } else {
                    flk.Q().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, fto::new);
        registerFactory(NamedScreen.REALMS, fgg::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new fxm(flk.Q().z, flk.Q().n, flk.Q().ah());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new fxj(flk.Q().z, flk.Q().n);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, fun::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<fum, fum> screenFactory) {
        registerFactory(screen, () -> {
            return (fum) screenFactory.apply(flk.Q().z);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof fvb;
    }
}
