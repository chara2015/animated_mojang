package com.mojang.realmsclient.util.task;

import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.screens.RealmsGenericErrorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/util/task/LongRunningTask.class */
public abstract class LongRunningTask implements Runnable {
    protected static final int NUMBER_OF_RETRIES = 25;
    private static final Logger LOGGER = LogUtils.getLogger();
    private boolean aborted = false;

    public abstract Component getTitle();

    protected static void pause(long $$0) {
        try {
            Thread.sleep($$0 * 1000);
        } catch (InterruptedException $$1) {
            Thread.currentThread().interrupt();
            LOGGER.error("", $$1);
        }
    }

    public static void setScreen(Screen $$0) {
        Minecraft $$1 = Minecraft.getInstance();
        $$1.execute(() -> {
            $$1.setScreen($$0);
        });
    }

    protected void error(Component $$0) {
        abortTask();
        Minecraft $$1 = Minecraft.getInstance();
        $$1.execute(() -> {
            $$1.setScreen(new RealmsGenericErrorScreen($$0, new RealmsMainScreen(new TitleScreen())));
        });
    }

    protected void error(Exception $$0) {
        if ($$0 instanceof RealmsServiceException) {
            RealmsServiceException $$1 = (RealmsServiceException) $$0;
            error($$1.realmsError.errorMessage());
        } else {
            error(Component.literal($$0.getMessage()));
        }
    }

    protected void error(RealmsServiceException $$0) {
        error($$0.realmsError.errorMessage());
    }

    public boolean aborted() {
        return this.aborted;
    }

    public void tick() {
    }

    public void init() {
    }

    public void abortTask() {
        this.aborted = true;
    }
}
