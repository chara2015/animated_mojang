package net.labymod.v1_8_9.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;
import net.labymod.v1_8_9.client.gui.screen.VersionedFunctionalConfirmScreen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<axu> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, axv.class);
        register(NamedScreen.MULTIPLAYER, azh.class);
        register(NamedScreen.EDIT_SERVER, axi.class);
        register(NamedScreen.MAIN_MENU, aya.class);
        register(NamedScreen.CHAT_INPUT, awv.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, axk.class);
        register(NamedScreen.INGAME_MENU, axp.class);
        register(NamedScreen.INVENTORY, azc.class);
        register(NamedScreen.CREATIVE_INVENTORY, ayu.class);
        register(NamedScreen.CONNECTING, awz.class);
        register(NamedScreen.DISCONNECTED, axh.class);
        register(NamedScreen.CREDITS, ayc.class);
        register(NamedScreen.CREATE_WORLD, axb.class);
        register(NamedScreen.LEVEL_LOADING, axs.class);
        register(NamedScreen.OPEN_LAN_WORLD, axw.class);
        register(NamedScreen.STATISTICS, ayf.class);
        register(NamedScreen.ADVANCEMENTS, aye.class);
        register(NamedScreen.CONFIRM, awy.class);
        register(NamedScreen.EDIT_BOOK, ayo.class);
        register(NamedScreen.OPTIONS, axn.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, axx.class);
        register(NamedScreen.VIDEO_SETTINGS, ayb.class);
        register(NamedScreen.LANGUAGE_SELECTION, axl.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, azo.class);
        register(NamedScreen.AUDIO_SETTINGS, axz.class);
        register(NamedScreen.CONTROL_SETTINGS, ayj.class);
        register(NamedScreen.CHAT_SETTINGS, awu.class);
        register(NamedScreen.SNOOPER_SETTINGS, axy.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new axv(new aya());
        });
        registerFactory(NamedScreen.MULTIPLAYER, azh::new);
        registerFactory(NamedScreen.OPTIONS, () -> {
            axu previousScreen = ave.A().m;
            return new axn(previousScreen, ave.A().t);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new awv("");
        });
        registerFactory(NamedScreen.INGAME_MENU, axp::new);
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            axu prevScreen = ave.A().m;
            bde data = new bde(bnq.a("selectServer.defaultName", new Object[0]), "", false);
            return new axg(new VersionedFunctionalConfirmScreen(0, join -> {
                if (join.booleanValue()) {
                    ave.A().a(new awz(prevScreen, ave.A(), data));
                } else {
                    ave.A().a(prevScreen);
                }
            }), data);
        });
        registerFactory(NamedScreen.CREDITS, ayc::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new axl(ave.A().m, ave.A().t, ave.A().S());
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, axw::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void registerFactory(GameScreen screen, Function<axu, axu> screenFactory) {
        registerFactory(screen, () -> {
            return (axu) screenFactory.apply(ave.A().m);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof ayl;
    }
}
