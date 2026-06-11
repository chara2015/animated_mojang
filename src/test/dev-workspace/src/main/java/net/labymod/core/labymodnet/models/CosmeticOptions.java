package net.labymod.core.labymodnet.models;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/models/CosmeticOptions.class */
public class CosmeticOptions {
    private final Map<String, CosmeticOption> options = new HashMap();

    public Map<String, CosmeticOption> getMap() {
        return this.options;
    }

    public CosmeticOption getOptions(String id) {
        return this.options.get(id);
    }
}
