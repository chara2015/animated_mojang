package com.mojang.realmsclient.client.worldupload;

import com.mojang.realmsclient.Unit;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/worldupload/RealmsUploadTooLargeException.class */
public class RealmsUploadTooLargeException extends RealmsUploadException {
    final long sizeLimit;

    public RealmsUploadTooLargeException(long $$0) {
        this.sizeLimit = $$0;
    }

    @Override // com.mojang.realmsclient.client.worldupload.RealmsUploadException
    public Component[] getErrorMessages() {
        return new Component[]{Component.translatable("mco.upload.failed.too_big.title"), Component.translatable("mco.upload.failed.too_big.description", Unit.humanReadable(this.sizeLimit, Unit.getLargest(this.sizeLimit)))};
    }
}
