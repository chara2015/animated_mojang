package net.labymod.v1_21_10.platform;

import java.util.function.Function;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.core.platform.PlatformScreenHandler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/platform/VersionedPlatformScreenHandler.class */
public class VersionedPlatformScreenHandler extends PlatformScreenHandler<gmj> {
    @Override // net.labymod.core.platform.PlatformScreenHandler
    public void onInitialize() {
        register(NamedScreen.SINGLEPLAYER, gsl.class);
        register(NamedScreen.MULTIPLAYER, gpv.class);
        register(NamedScreen.EDIT_SERVER, gma.class);
        register(NamedScreen.MAIN_MENU, gml.class);
        register(NamedScreen.CHAT_INPUT, glg.class);
        register(NamedScreen.CHAT_INPUT_IN_BED, glw.class);
        register(NamedScreen.INGAME_MENU, gmf.class);
        register(NamedScreen.INVENTORY, gos.class);
        register(NamedScreen.CREATIVE_INVENTORY, goh.class);
        register(NamedScreen.CONNECTING, glj.class);
        register(NamedScreen.DISCONNECTED, glr.class);
        register(NamedScreen.CREDITS, glm.class);
        register(NamedScreen.REALMS, fui.class);
        register(NamedScreen.CREATE_WORLD, gsd.class);
        register(NamedScreen.LEVEL_LOADING, glx.class);
        register(NamedScreen.PACK_CONFIRM, c.class);
        register(NamedScreen.PROGRESS, gmh.class);
        register(NamedScreen.GENERIC_MESSAGE, glu.class);
        register(NamedScreen.OPEN_LAN_WORLD, gmk.class);
        register(NamedScreen.STATISTICS, gmn.class);
        register(NamedScreen.ADVANCEMENTS, gmt.class);
        register(NamedScreen.CONFIRM, gli.class);
        register(NamedScreen.SOCIAL_INTERACTIONS, grw.class);
        register(NamedScreen.EDIT_BOOK, gnx.class);
        register(NamedScreen.OPTIONS, gqh.class);
        register(NamedScreen.SKIN_CUSTOMIZATION, gqj.class);
        register(NamedScreen.VIDEO_SETTINGS, gqm.class);
        register(NamedScreen.LANGUAGE_SELECTION, gqe.class);
        register(NamedScreen.RESOURCE_PACK_SETTINGS, gqu.class);
        register(NamedScreen.AUDIO_SETTINGS, gqk.class);
        register(NamedScreen.CONTROL_SETTINGS, gqn.class);
        register(NamedScreen.CHAT_SETTINGS, gqc.class);
        register(NamedScreen.ACCESSIBILITY_SETTINGS, gqb.class);
        register(NamedScreen.KEYBIND_SETTINGS, gqp.class);
        register(NamedScreen.MOUSE_SETTINGS, gqf.class);
        registerFactory(NamedScreen.SINGLEPLAYER, () -> {
            return new gsl(new gml(false));
        });
        registerFactory(NamedScreen.MULTIPLAYER, () -> {
            return new gpv(new gml(false));
        });
        registerFactory(NamedScreen.OPTIONS, () -> {
            gmj prevScreen = fzz.W().x;
            if (prevScreen instanceof glu) {
                prevScreen = null;
            }
            return new gqh(prevScreen, fzz.W().k);
        });
        registerFactory(NamedScreen.CHAT_INPUT, () -> {
            return new glg("", false);
        });
        registerFactory(NamedScreen.INGAME_MENU, () -> {
            return new gmf(true);
        });
        registerFactory(NamedScreen.DIRECT_CONNECT, () -> {
            gmj prevScreen = fzz.W().x;
            hab data = new hab(idt.a("selectServer.defaultName", new Object[0]), "", c.c);
            return new glq(prevScreen, join -> {
                if (join) {
                    glj.a(prevScreen, fzz.W(), hbe.a(data.b), data, false, (haf) null);
                } else {
                    fzz.W().a(prevScreen);
                }
            }, data);
        });
        registerFactory(NamedScreen.CREDITS, glm::new);
        registerFactory(NamedScreen.REALMS, fui::new);
        registerFactory(NamedScreen.LANGUAGE_SELECTION, () -> {
            return new gqe(fzz.W().x, fzz.W().k, fzz.W().an());
        });
        registerFactory(NamedScreen.ACCESSIBILITY_SETTINGS, () -> {
            return new gqb(fzz.W().x, fzz.W().k);
        });
        registerFactory(NamedScreen.OPEN_LAN_WORLD, gmk::new);
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    protected void registerFactory(GameScreen screen, Function<gmj, gmj> screenFactory) {
        registerFactory(screen, () -> {
            return (gmj) screenFactory.apply(fzz.W().x);
        });
    }

    @Override // net.labymod.core.platform.PlatformScreenHandler
    public boolean isInventoryScreen(Object screen) {
        return screen instanceof gnq;
    }
}
