package com.mojang.realmsclient.util.task;

import com.mojang.logging.LogUtils;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/util/task/ResettingWorldTask.class */
public abstract class ResettingWorldTask extends LongRunningTask {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final long serverId;
    private final Component title;
    private final Runnable callback;

    protected abstract void sendResetRequest(RealmsClient realmsClient, long j) throws RealmsServiceException;

    public ResettingWorldTask(long $$0, Component $$1, Runnable $$2) {
        this.serverId = $$0;
        this.title = $$1;
        this.callback = $$2;
    }

    @Override // java.lang.Runnable
    public void run() {
        RealmsClient $$0 = RealmsClient.getOrCreate();
        for (int $$1 = 0; $$1 < 25; $$1++) {
            try {
                if (aborted()) {
                    return;
                }
                sendResetRequest($$0, this.serverId);
                if (aborted()) {
                    return;
                }
                this.callback.run();
                return;
            } catch (RetryCallException $$2) {
                if (aborted()) {
                    return;
                }
                pause($$2.delaySeconds);
            } catch (Exception $$3) {
                if (aborted()) {
                    return;
                }
                LOGGER.error("Couldn't reset world");
                error($$3);
                return;
            }
        }
    }

    @Override // com.mojang.realmsclient.util.task.LongRunningTask
    public Component getTitle() {
        return this.title;
    }
}
