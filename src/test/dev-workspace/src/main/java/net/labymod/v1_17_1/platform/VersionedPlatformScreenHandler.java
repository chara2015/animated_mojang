package net.labymod.v1_17_1.platform;

import com.google.common.util.concurrent.Runnables;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<eaq> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, eei.class);
        register(NamedScreen.MULTIPLAYER, edc.class);
        register(NamedScreen.EDIT_SERVER, dzy.class);
        register(NamedScreen.MAIN_MENU, eav.class);
        register(NamedScreen.CHAT_INPUT, dzn.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, eab.class);
        register(NamedScreen.INGAME_MENU, eal.class);
        register(NamedScreen.INVENTORY, ecj.class);
        register(NamedScreen.CREATIVE_INVENTORY, eca.class);
        register(NamedScreen.CONNECTING, dzq.class);
        register(NamedScreen.DISCONNECTED, dzx.class);
        register(NamedScreen.CREDITS, eax.class);
        register(NamedScreen.CREATE_WORLD, eee.class);
        register(NamedScreen.LEVEL_LOADING, ead.class);
        register(NamedScreen.RECEIVING_LEVEL, eap.class);
        register(NamedScreen.PROGRESS, eao.class);
        register(NamedScreen.GENERIC_MESSAGE, eaa.class);
        register(NamedScreen.OPEN_LAN_WORLD, ear.class);
        register(NamedScreen.STATISTICS, eay.class);
        register(NamedScreen.ADVANCEMENTS, ebf.class);
        register(NamedScreen.CONFIRM, dzp.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, eec.class);
        register(NamedScreen.EDIT_BOOK, ebs.class);
        register(NamedScreen.OPTIONS, eah.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, eat.class);
        register(NamedScreen.VIDEO_SETTINGS, eaw.class);
        register(NamedScreen.LANGUAGE_SELECTION, eac.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, edi.class);
        register(NamedScreen.AUDIO_SETTINGS, eau.class);
        register(NamedScreen.CONTROL_SETTINGS, ebi.class);
        register(NamedScreen.CHAT_SETTINGS, dzm.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, dzj.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new eei(new eav(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new edc(new eav(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            eaq prevScreen = dvp.C().y;
            if (prevScreen instanceof eaa) {
                prevScreen = null;
            }
            return new eah(prevScreen, dvp.C().l);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new dzn("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new eal(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            eaq prevScreen = dvp.C().y;
            ejn data = new ejn(eyh.a("selectServer.defaultName", new Object[0]), "", false);
            return new dzw(prevScreen, join -> {
                if (join) {
                    dzq.a(prevScreen, dvp.C(), ejt.a(data.b), data);
                } else {
                    dvp.C().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, () -> {
            return new eax(false, Runnables.doNothing());
        });
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new eac(dvp.C().y, dvp.C().l, dvp.C().R());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new dzj(dvp.C().y, dvp.C().l);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, ear::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<eaq, eaq> screenFactory) {
        registerFactory(screen, () -> {
            return (eaq) screenFactory.apply(dvp.C().y);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof ebn;
    }
}
