package net.labymod.v1_20_6.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fnf> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, fsa.class);
        register(NamedScreen.MULTIPLAYER, fqd.class);
        register(NamedScreen.EDIT_SERVER, fmh.class);
        register(NamedScreen.MAIN_MENU, fnk.class);
        register(NamedScreen.CHAT_INPUT, flv.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, fmn.class);
        register(NamedScreen.INGAME_MENU, fna.class);
        register(NamedScreen.INVENTORY, fpe.class);
        register(NamedScreen.CREATIVE_INVENTORY, fot.class);
        register(NamedScreen.CONNECTING, fly.class);
        register(NamedScreen.DISCONNECTED, fmg.class);
        register(NamedScreen.CREDITS, fmb.class);
        register(NamedScreen.REALMS, fal.class);
        register(NamedScreen.CREATE_WORLD, fru.class);
        register(NamedScreen.LEVEL_LOADING, fmp.class);
        register(NamedScreen.RECEIVING_LEVEL, fnd.class);
        register(NamedScreen.PACK_CONFIRM, b.class);
        register(NamedScreen.PROGRESS, fnc.class);
        register(NamedScreen.GENERIC_MESSAGE, fml.class);
        register(NamedScreen.OPEN_LAN_WORLD, fng.class);
        register(NamedScreen.STATISTICS, fno.class);
        register(NamedScreen.ADVANCEMENTS, fnu.class);
        register(NamedScreen.CONFIRM, flx.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, fro.class);
        register(NamedScreen.EDIT_BOOK, foj.class);
        register(NamedScreen.OPTIONS, fmw.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, fni.class);
        register(NamedScreen.VIDEO_SETTINGS, fnm.class);
        register(NamedScreen.LANGUAGE_SELECTION, fmo.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, fql.class);
        register(NamedScreen.AUDIO_SETTINGS, fnj.class);
        register(NamedScreen.CONTROL_SETTINGS, fnw.class);
        register(NamedScreen.CHAT_SETTINGS, flu.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, flq.class);
        register(NamedScreen.KEYBIND_SETTINGS, fny.class);
        register(NamedScreen.MOUSE_SETTINGS, fmt.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new fsa(new fnk(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new fqd(new fnk(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            fnf prevScreen = ffh.Q().y;
            if (prevScreen instanceof fml) {
                prevScreen = null;
            }
            return new fmw(prevScreen, ffh.Q().m);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new flv("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new fna(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            fnf prevScreen = ffh.Q().y;
            fyl data = new fyl(gqh.a("selectServer.defaultName", new Object[0]), "", c.c);
            return new fmf(prevScreen, join -> {
                if (join) {
                    fly.a(prevScreen, ffh.Q(), fzo.a(data.b), data, false, (fyp) null);
                } else {
                    ffh.Q().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, fmb::new);
        registerFactory(NamedScreen.REALMS, fal::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new fmo(ffh.Q().y, ffh.Q().m, ffh.Q().ag());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new flq(ffh.Q().y, ffh.Q().m);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, fng::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<fnf, fnf> screenFactory) {
        registerFactory(screen, () -> {
            return (fnf) screenFactory.apply(ffh.Q().y);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof fod;
    }
}
