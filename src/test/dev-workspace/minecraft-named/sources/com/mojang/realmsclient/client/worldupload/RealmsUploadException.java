package com.mojang.realmsclient.client.worldupload;

import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/worldupload/RealmsUploadException.class */
public abstract class RealmsUploadException extends RuntimeException {
    public Component getStatusMessage() {
        return null;
    }

    public Component[] getErrorMessages() {
        return null;
    }
}
