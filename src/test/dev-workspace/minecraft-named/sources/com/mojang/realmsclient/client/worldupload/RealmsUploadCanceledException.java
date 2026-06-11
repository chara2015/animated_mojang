package com.mojang.realmsclient.client.worldupload;

import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/worldupload/RealmsUploadCanceledException.class */
public class RealmsUploadCanceledException extends RealmsUploadException {
    private static final Component UPLOAD_CANCELED = Component.translatable("mco.upload.cancelled");

    @Override // com.mojang.realmsclient.client.worldupload.RealmsUploadException
    public Component getStatusMessage() {
        return UPLOAD_CANCELED;
    }
}
