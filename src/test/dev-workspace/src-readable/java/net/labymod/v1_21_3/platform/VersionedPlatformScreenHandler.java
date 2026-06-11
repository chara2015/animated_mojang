package net.labymod.v1_21_3.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fty> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, fzf.class);
        register(NamedScreen.MULTIPLAYER, fwo.class);
        register(NamedScreen.EDIT_SERVER, ftg.class);
        register(NamedScreen.MAIN_MENU, fua.class);
        register(NamedScreen.CHAT_INPUT, fsu.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, ftl.class);
        register(NamedScreen.INGAME_MENU, ftt.class);
        register(NamedScreen.INVENTORY, fvo.class);
        register(NamedScreen.CREATIVE_INVENTORY, fvd.class);
        register(NamedScreen.CONNECTING, fsx.class);
        register(NamedScreen.DISCONNECTED, ftf.class);
        register(NamedScreen.CREDITS, fta.class);
        register(NamedScreen.REALMS, fhd.class);
        register(NamedScreen.CREATE_WORLD, fyx.class);
        register(NamedScreen.LEVEL_LOADING, ftm.class);
        register(NamedScreen.RECEIVING_LEVEL, ftw.class);
        register(NamedScreen.PACK_CONFIRM, b.class);
        register(NamedScreen.PROGRESS, ftv.class);
        register(NamedScreen.GENERIC_MESSAGE, ftj.class);
        register(NamedScreen.OPEN_LAN_WORLD, ftz.class);
        register(NamedScreen.STATISTICS, fuc.class);
        register(NamedScreen.ADVANCEMENTS, fui.class);
        register(NamedScreen.CONFIRM, fsw.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, fyq.class);
        register(NamedScreen.EDIT_BOOK, fuu.class);
        register(NamedScreen.OPTIONS, fxb.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, fxd.class);
        register(NamedScreen.VIDEO_SETTINGS, fxg.class);
        register(NamedScreen.LANGUAGE_SELECTION, fwy.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, fxo.class);
        register(NamedScreen.AUDIO_SETTINGS, fxe.class);
        register(NamedScreen.CONTROL_SETTINGS, fxh.class);
        register(NamedScreen.CHAT_SETTINGS, fww.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, fwv.class);
        register(NamedScreen.KEYBIND_SETTINGS, fxj.class);
        register(NamedScreen.MOUSE_SETTINGS, fwz.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new fzf(new fua(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new fwo(new fua(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            fty prevScreen = fmg.Q().z;
            if (prevScreen instanceof ftj) {
                prevScreen = null;
            }
            return new fxb(prevScreen, fmg.Q().n);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new fsu("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new ftt(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            fty prevScreen = fmg.Q().z;
            gfz data = new gfz(hcs.a("selectServer.defaultName", new Object[0]), "", c.c);
            return new fte(prevScreen, join -> {
                if (join) {
                    fsx.a(prevScreen, fmg.Q(), ghc.a(data.b), data, false, (ggd) null);
                } else {
                    fmg.Q().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, fta::new);
        registerFactory(NamedScreen.REALMS, fhd::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new fwy(fmg.Q().z, fmg.Q().n, fmg.Q().ah());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new fwv(fmg.Q().z, fmg.Q().n);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, ftz::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<fty, fty> screenFactory) {
        registerFactory(screen, () -> {
            return (fty) screenFactory.apply(fmg.Q().z);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof fun;
    }
}
