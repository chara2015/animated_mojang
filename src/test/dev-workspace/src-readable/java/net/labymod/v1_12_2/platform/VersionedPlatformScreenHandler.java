package net.labymod.v1_12_2.platform;

import com.google.common.util.concurrent.Runnables;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;
import net.labymod.v1_12_2.client.gui.screen.VersionedFunctionalConfirmScreen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<blk> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, bok.class);
        register(NamedScreen.MULTIPLAYER, bnf.class);
        register(NamedScreen.EDIT_SERVER, bkz.class);
        register(NamedScreen.MAIN_MENU, blr.class);
        register(NamedScreen.CHAT_INPUT, bkn.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, blb.class);
        register(NamedScreen.INGAME_MENU, blg.class);
        register(NamedScreen.INVENTORY, bmx.class);
        register(NamedScreen.CREATIVE_INVENTORY, bmp.class);
        register(NamedScreen.CONNECTING, bkr.class);
        register(NamedScreen.DISCONNECTED, bky.class);
        register(NamedScreen.CREDITS, blt.class);
        register(NamedScreen.CREATE_WORLD, boi.class);
        register(NamedScreen.LEVEL_LOADING, blj.class);
        register(NamedScreen.OPEN_LAN_WORLD, bll.class);
        register(NamedScreen.STATISTICS, blu.class);
        register(NamedScreen.ADVANCEMENTS, bmb.class);
        register(NamedScreen.CONFIRM, bkq.class);
        register(NamedScreen.EDIT_BOOK, bmj.class);
        register(NamedScreen.OPTIONS, ble.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, blm.class);
        register(NamedScreen.VIDEO_SETTINGS, bls.class);
        register(NamedScreen.LANGUAGE_SELECTION, blc.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, bnw.class);
        register(NamedScreen.AUDIO_SETTINGS, blo.class);
        register(NamedScreen.CONTROL_SETTINGS, bme.class);
        register(NamedScreen.CHAT_SETTINGS, bkm.class);
        register(NamedScreen.SNOOPER_SETTINGS, bln.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new bok(new blr());
        });
        registerFactory(NamedScreen.MULTIPLAYER, bnf::new);
        registerFactory(NamedScreen.OPTIONS, () -> {
            blk previousScreen = bib.z().m;
            return new ble(previousScreen, bib.z().t);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new bkn("");
        });
        registerFactory(NamedScreen.INGAME_MENU, blg::new);
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            blk prevScreen = bib.z().m;
            bse data = new bse(cey.a("selectServer.defaultName", new Object[0]), "", false);
            return new bkx(new VersionedFunctionalConfirmScreen(0, join -> {
                if (join.booleanValue()) {
                    bib.z().a(new bkr(prevScreen, bib.z(), data));
                } else {
                    bib.z().a(prevScreen);
                }
            }), data);
        });
        registerFactory(NamedScreen.CREDITS, () -> {
            return new blt(false, Runnables.doNothing());
        });
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new blc(bib.z().m, bib.z().t, bib.z().Q());
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, bll::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<blk, blk> screenFactory) {
        registerFactory(screen, () -> {
            return (blk) screenFactory.apply(bib.z().m);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof bmg;
    }
}
