package net.labymod.v1_16_5.platform;

import com.google.common.util.concurrent.Runnables;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<dot> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, dsj.class);
        register(NamedScreen.MULTIPLAYER, drc.class);
        register(NamedScreen.EDIT_SERVER, dob.class);
        register(NamedScreen.MAIN_MENU, doy.class);
        register(NamedScreen.CHAT_INPUT, dnq.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, doe.class);
        register(NamedScreen.INGAME_MENU, doo.class);
        register(NamedScreen.INVENTORY, dql.class);
        register(NamedScreen.CREATIVE_INVENTORY, dqc.class);
        register(NamedScreen.CONNECTING, dnt.class);
        register(NamedScreen.DISCONNECTED, doa.class);
        register(NamedScreen.CREDITS, dpa.class);
        register(NamedScreen.CREATE_WORLD, dsf.class);
        register(NamedScreen.LEVEL_LOADING, dog.class);
        register(NamedScreen.RECEIVING_LEVEL, dos.class);
        register(NamedScreen.PROGRESS, dor.class);
        register(NamedScreen.GENERIC_MESSAGE, dod.class);
        register(NamedScreen.OPEN_LAN_WORLD, dou.class);
        register(NamedScreen.STATISTICS, dpb.class);
        register(NamedScreen.ADVANCEMENTS, dpi.class);
        register(NamedScreen.CONFIRM, dns.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, dsc.class);
        register(NamedScreen.EDIT_BOOK, dpu.class);
        register(NamedScreen.OPTIONS, dok.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, dow.class);
        register(NamedScreen.VIDEO_SETTINGS, doz.class);
        register(NamedScreen.LANGUAGE_SELECTION, dof.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, dri.class);
        register(NamedScreen.AUDIO_SETTINGS, dox.class);
        register(NamedScreen.CONTROL_SETTINGS, dpl.class);
        register(NamedScreen.CHAT_SETTINGS, dnp.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, dnm.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new dsj(new doy(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new drc(new doy(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            dot prevScreen = djz.C().y;
            if (prevScreen instanceof dod) {
                prevScreen = null;
            }
            return new dok(prevScreen, djz.C().k);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new dnq("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new doo(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            dot prevScreen = djz.C().y;
            dwz data = new dwz(ekx.a("selectServer.defaultName", new Object[0]), "", false);
            return new dnz(prevScreen, join -> {
                if (join) {
                    djz.C().a(new dnt(prevScreen, djz.C(), data));
                } else {
                    djz.C().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, () -> {
            return new dpa(false, Runnables.doNothing());
        });
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new dof(djz.C().y, djz.C().k, djz.C().R());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new dnm(djz.C().y, djz.C().k);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, dou::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<dot, dot> screenFactory) {
        registerFactory(screen, () -> {
            return (dot) screenFactory.apply(djz.C().y);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof dpp;
    }
}
