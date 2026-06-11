package net.labymod.v1_21.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<fod> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, fti.class);
        register(NamedScreen.MULTIPLAYER, fqt.class);
        register(NamedScreen.EDIT_SERVER, fnl.class);
        register(NamedScreen.MAIN_MENU, fof.class);
        register(NamedScreen.CHAT_INPUT, fmz.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, fnq.class);
        register(NamedScreen.INGAME_MENU, fny.class);
        register(NamedScreen.INVENTORY, fpt.class);
        register(NamedScreen.CREATIVE_INVENTORY, fpi.class);
        register(NamedScreen.CONNECTING, fnc.class);
        register(NamedScreen.DISCONNECTED, fnk.class);
        register(NamedScreen.CREDITS, fnf.class);
        register(NamedScreen.REALMS, fbt.class);
        register(NamedScreen.CREATE_WORLD, ftc.class);
        register(NamedScreen.LEVEL_LOADING, fnr.class);
        register(NamedScreen.RECEIVING_LEVEL, fob.class);
        register(NamedScreen.PACK_CONFIRM, b.class);
        register(NamedScreen.PROGRESS, foa.class);
        register(NamedScreen.GENERIC_MESSAGE, fno.class);
        register(NamedScreen.OPEN_LAN_WORLD, foe.class);
        register(NamedScreen.STATISTICS, foh.class);
        register(NamedScreen.ADVANCEMENTS, fon.class);
        register(NamedScreen.CONFIRM, fnb.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, fsw.class);
        register(NamedScreen.EDIT_BOOK, foz.class);
        register(NamedScreen.OPTIONS, frg.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, fri.class);
        register(NamedScreen.VIDEO_SETTINGS, frl.class);
        register(NamedScreen.LANGUAGE_SELECTION, frd.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, frt.class);
        register(NamedScreen.AUDIO_SETTINGS, frj.class);
        register(NamedScreen.CONTROL_SETTINGS, frm.class);
        register(NamedScreen.CHAT_SETTINGS, frb.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, fra.class);
        register(NamedScreen.KEYBIND_SETTINGS, fro.class);
        register(NamedScreen.MOUSE_SETTINGS, fre.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new fti(new fof(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new fqt(new fof(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            fod prevScreen = fgo.Q().y;
            if (prevScreen instanceof fno) {
                prevScreen = null;
            }
            return new frg(prevScreen, fgo.Q().m);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new fmz("");
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new fny(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            fod prevScreen = fgo.Q().y;
            fzt data = new fzt(grr.a("selectServer.defaultName", new Object[0]), "", c.c);
            return new fnj(prevScreen, join -> {
                if (join) {
                    fnc.a(prevScreen, fgo.Q(), gax.a(data.b), data, false, (fzy) null);
                } else {
                    fgo.Q().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, fnf::new);
        registerFactory(NamedScreen.REALMS, fbt::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new frd(fgo.Q().y, fgo.Q().m, fgo.Q().ag());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new fra(fgo.Q().y, fgo.Q().m);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, foe::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<fod, fod> screenFactory) {
        registerFactory(screen, () -> {
            return (fod) screenFactory.apply(fgo.Q().y);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof fot;
    }
}
