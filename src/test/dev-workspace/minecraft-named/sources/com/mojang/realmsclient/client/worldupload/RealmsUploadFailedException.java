package com.mojang.realmsclient.client.worldupload;

import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/worldupload/RealmsUploadFailedException.class */
public class RealmsUploadFailedException extends RealmsUploadException {
    private final Component errorMessage;

    public RealmsUploadFailedException(Component $$0) {
        this.errorMessage = $$0;
    }

    public RealmsUploadFailedException(String $$0) {
        this(Component.literal($$0));
    }

    @Override // com.mojang.realmsclient.client.worldupload.RealmsUploadException
    public Component getStatusMessage() {
        return Component.translatable("mco.upload.failed", this.errorMessage);
    }
}
