package net.labymod.core.main.listener;

import net.labymod.api.client.gfx.pipeline.post.processors.PostProcessors;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.ScreenService;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenDisplayEvent;
import net.labymod.api.event.client.render.ScreenRenderEvent;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/InventoryMenuBlurListener.class */
public final class InventoryMenuBlurListener {
    private boolean isInInventory = false;
    private static final long OFFSET = 50;
    private long lastTimeOpen;

    @Subscribe
    public void onScreenDisplay(ScreenDisplayEvent event) {
        ScreenInstance screen = event.getScreen();
        if (screen == null) {
            setInInventory(false);
            return;
        }
        Object object = screen.mostInnerScreen();
        ScreenService screenService = LabyMod.references().screenService();
        if (!screenService.isInventory(object)) {
            setInInventory(false);
        } else {
            if (this.isInInventory) {
                return;
            }
            if (this.lastTimeOpen < TimeUtil.getMillis()) {
                PostProcessors.resetMenuBlur();
            }
            setInInventory(true);
        }
    }

    @Subscribe
    public void onScreenRender(ScreenRenderEvent event) {
        if (event.phase() != Phase.PRE || !this.isInInventory) {
            return;
        }
        PostProcessors.processMenuBlur(MenuBlurConfig.ScreenType.INVENTORIES, event.screenContext());
    }

    private void setInInventory(boolean state) {
        this.lastTimeOpen = TimeUtil.getMillis() + OFFSET;
        this.isInInventory = state;
    }
}
