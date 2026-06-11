package com.mojang.realmsclient.util.task;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.WorldTemplate;
import com.mojang.realmsclient.exception.RealmsServiceException;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/util/task/ResettingTemplateWorldTask.class */
public class ResettingTemplateWorldTask extends ResettingWorldTask {
    private final WorldTemplate template;

    public ResettingTemplateWorldTask(WorldTemplate $$0, long $$1, Component $$2, Runnable $$3) {
        super($$1, $$2, $$3);
        this.template = $$0;
    }

    @Override // com.mojang.realmsclient.util.task.ResettingWorldTask
    protected void sendResetRequest(RealmsClient $$0, long $$1) throws RealmsServiceException {
        $$0.resetWorldWithTemplate($$1, this.template.id());
    }
}
