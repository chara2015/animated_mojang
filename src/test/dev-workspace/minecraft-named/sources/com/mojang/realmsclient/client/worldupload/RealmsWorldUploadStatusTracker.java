package com.mojang.realmsclient.client.worldupload;

import com.mojang.realmsclient.client.UploadStatus;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/client/worldupload/RealmsWorldUploadStatusTracker.class */
public interface RealmsWorldUploadStatusTracker {
    UploadStatus getUploadStatus();

    void setUploading();

    static RealmsWorldUploadStatusTracker noOp() {
        return new RealmsWorldUploadStatusTracker() { // from class: com.mojang.realmsclient.client.worldupload.RealmsWorldUploadStatusTracker.1
            private final UploadStatus uploadStatus = new UploadStatus();

            @Override // com.mojang.realmsclient.client.worldupload.RealmsWorldUploadStatusTracker
            public UploadStatus getUploadStatus() {
                return this.uploadStatus;
            }

            @Override // com.mojang.realmsclient.client.worldupload.RealmsWorldUploadStatusTracker
            public void setUploading() {
            }
        };
    }
}
