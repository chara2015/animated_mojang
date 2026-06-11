package net.labymod.v1_21_5.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fzq> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, gez.class);
        register(NamedScreen.MULTIPLAYER, gci.class);
        register(NamedScreen.EDIT_SERVER, fyy.class);
        register(NamedScreen.MAIN_MENU, fzs.class);
        register(NamedScreen.CHAT_INPUT, fym.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, fzd.class);
        register(NamedScreen.INGAME_MENU, fzl.class);
        register(NamedScreen.INVENTORY, gbg.class);
        register(NamedScreen.CREATIVE_INVENTORY, gav.class);
        register(NamedScreen.CONNECTING, fyp.class);
        register(NamedScreen.DISCONNECTED, fyx.class);
        register(NamedScreen.CREDITS, fys.class);
        register(NamedScreen.REALMS, fll.class);
        register(NamedScreen.CREATE_WORLD, ger.class);
        register(NamedScreen.LEVEL_LOADING, fze.class);
        register(NamedScreen.RECEIVING_LEVEL, fzo.class);
        register(NamedScreen.PACK_CONFIRM, b.class);
        register(NamedScreen.PROGRESS, fzn.class);
        register(NamedScreen.GENERIC_MESSAGE, fzb.class);
        register(NamedScreen.OPEN_LAN_WORLD, fzr.class);
        register(NamedScreen.STATISTICS, fzu.class);
        register(NamedScreen.ADVANCEMENTS, gaa.class);
        register(NamedScreen.CONFIRM, fyo.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, gek.class);
        register(NamedScreen.EDIT_BOOK, gam.class);
        register(NamedScreen.OPTIONS, gcv.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, gcx.class);
        register(NamedScreen.VIDEO_SETTINGS, gda.class);
        register(NamedScreen.LANGUAGE_SELECTION, gcs.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, gdi.class);
        register(NamedScreen.AUDIO_SETTINGS, gcy.class);
        register(NamedScreen.CONTROL_SETTINGS, gdb.class);
        register(NamedScreen.CHAT_SETTINGS, gcq.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, gcp.class);
        register(NamedScreen.KEYBIND_SETTINGS, gdd.class);
        register(NamedScreen.MOUSE_SETTINGS, gct.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new gez(new fzs(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new gci(new fzs(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            fzq prevScreen = fqq.Q().z;
            if (prevScreen instanceof fzb) {
                prevScreen = null;
            }
            return new gcv(prevScreen, fqq.Q().n);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new fym("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new fzl(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            fzq prevScreen = fqq.Q().z;
            gmd data = new gmd(hly.a("selectServer.defaultName", new Object[0]), "", c.c);
            return new fyw(prevScreen, join -> {
                if (join) {
                    fyp.a(prevScreen, fqq.Q(), gng.a(data.b), data, false, (gmh) null);
                } else {
                    fqq.Q().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, fys::new);
        registerFactory(NamedScreen.REALMS, fll::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new gcs(fqq.Q().z, fqq.Q().n, fqq.Q().ah());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new gcp(fqq.Q().z, fqq.Q().n);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, fzr::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<fzq, fzq> screenFactory) {
        registerFactory(screen, () -> {
            return (fzq) screenFactory.apply(fqq.Q().z);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof gaf;
    }
}
