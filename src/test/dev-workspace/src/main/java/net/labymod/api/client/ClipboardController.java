package net.labymod.api.client;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/ClipboardController.class */
@Referenceable
public interface ClipboardController extends AutoCloseable {
    String getClipboardContent(long j);

    void setClipboardContent(long j, String str);
}
