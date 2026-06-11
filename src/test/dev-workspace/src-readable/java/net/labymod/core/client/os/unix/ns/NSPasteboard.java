package net.labymod.core.client.os.unix.ns;

import ca.weblite.objc.Client;
import ca.weblite.objc.Proxy;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/unix/ns/NSPasteboard.class */
public class NSPasteboard {
    private final Client client;
    private final Proxy nsPasteboardProxy;

    public NSPasteboard(Client client, Proxy nsPasteboardProxy) {
        this.client = client;
        this.nsPasteboardProxy = nsPasteboardProxy;
    }

    @NotNull
    public static NSPasteboard generalPasteboard() {
        return generalPasteboard(Client.getInstance());
    }

    @NotNull
    public static NSPasteboard generalPasteboard(Client client) {
        return new NSPasteboard(client, client.sendProxy("NSPasteboard", "generalPasteboard", new Object[0]));
    }

    public Proxy getPasteboardItems() {
        Proxy pasteboardItems = this.nsPasteboardProxy.sendProxy("pasteboardItems", new Object[0]);
        if (pasteboardItems == null || pasteboardItems.sendInt("count", new Object[0]) <= 0) {
            throw new IllegalStateException("No pasteboard items found");
        }
        return pasteboardItems;
    }

    public void clearContents() {
        this.nsPasteboardProxy.send("clearContents", new Object[0]);
    }

    public void writeObjects(Proxy... objects) {
        this.nsPasteboardProxy.send("writeObjects:", new Object[]{ClientUtil.newArray(this.client, objects)});
    }
}
