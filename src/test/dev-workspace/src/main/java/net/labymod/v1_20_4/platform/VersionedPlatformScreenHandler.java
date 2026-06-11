package net.labymod.v1_20_4.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fdb> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, fhx.class);
        register(NamedScreen.MULTIPLAYER, ffz.class);
        register(NamedScreen.EDIT_SERVER, fce.class);
        register(NamedScreen.MAIN_MENU, fdg.class);
        register(NamedScreen.CHAT_INPUT, fbs.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, fcj.class);
        register(NamedScreen.INGAME_MENU, fcw.class);
        register(NamedScreen.INVENTORY, ffa.class);
        register(NamedScreen.CREATIVE_INVENTORY, fep.class);
        register(NamedScreen.CONNECTING, fbv.class);
        register(NamedScreen.DISCONNECTED, fcd.class);
        register(NamedScreen.CREDITS, fby.class);
        register(NamedScreen.REALMS, eqm.class);
        register(NamedScreen.CREATE_WORLD, fhr.class);
        register(NamedScreen.LEVEL_LOADING, fcl.class);
        register(NamedScreen.RECEIVING_LEVEL, fcz.class);
        register(NamedScreen.PACK_CONFIRM, b.class);
        register(NamedScreen.PROGRESS, fcy.class);
        register(NamedScreen.GENERIC_MESSAGE, fch.class);
        register(NamedScreen.OPEN_LAN_WORLD, fdc.class);
        register(NamedScreen.STATISTICS, fdk.class);
        register(NamedScreen.ADVANCEMENTS, fdr.class);
        register(NamedScreen.CONFIRM, fbu.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, fhl.class);
        register(NamedScreen.EDIT_BOOK, feg.class);
        register(NamedScreen.OPTIONS, fcs.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, fde.class);
        register(NamedScreen.VIDEO_SETTINGS, fdi.class);
        register(NamedScreen.LANGUAGE_SELECTION, fck.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, fgi.class);
        register(NamedScreen.AUDIO_SETTINGS, fdf.class);
        register(NamedScreen.CONTROL_SETTINGS, fdt.class);
        register(NamedScreen.CHAT_SETTINGS, fbr.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, fbn.class);
        register(NamedScreen.KEYBIND_SETTINGS, fdv.class);
        register(NamedScreen.MOUSE_SETTINGS, fcp.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new fhx(new fdg(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new ffz(new fdg(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            fdb prevScreen = evi.O().y;
            if (prevScreen instanceof fch) {
                prevScreen = null;
            }
            return new fcs(prevScreen, evi.O().m);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new fbs("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new fcw(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            fdb prevScreen = evi.O().y;
            fod data = new fod(gfs.a("selectServer.defaultName", new Object[0]), "", b.c);
            return new fcc(prevScreen, join -> {
                if (join) {
                    fbv.a(prevScreen, evi.O(), fpf.a(data.b), data, false);
                } else {
                    evi.O().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, fby::new);
        registerFactory(NamedScreen.REALMS, eqm::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new fck(evi.O().y, evi.O().m, evi.O().ae());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new fbn(evi.O().y, evi.O().m);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, fdc::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<fdb, fdb> screenFactory) {
        registerFactory(screen, () -> {
            return (fdb) screenFactory.apply(evi.O().y);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof fea;
    }
}
