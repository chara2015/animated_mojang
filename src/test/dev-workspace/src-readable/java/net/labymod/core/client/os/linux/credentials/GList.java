package net.labymod.core.client.os.linux.credentials;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/linux/credentials/GList.class */
public class GList extends Structure {
    public Pointer data;
    public Pointer next;

    public GList(Pointer p) {
        super(p);
        read();
    }

    protected List<String> getFieldOrder() {
        return Arrays.asList("data", "next");
    }
}
