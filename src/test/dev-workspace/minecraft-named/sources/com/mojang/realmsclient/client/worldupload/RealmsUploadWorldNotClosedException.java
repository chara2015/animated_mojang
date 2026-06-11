package com.mojang.realmsclient.client.worldupload;

import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/worldupload/RealmsUploadWorldNotClosedException.class */
public class RealmsUploadWorldNotClosedException extends RealmsUploadException {
    @Override // com.mojang.realmsclient.client.worldupload.RealmsUploadException
    public Component getStatusMessage() {
        return Component.translatable("mco.upload.close.failure");
    }
}
