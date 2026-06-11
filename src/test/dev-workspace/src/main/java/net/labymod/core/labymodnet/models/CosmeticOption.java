package net.labymod.core.labymodnet.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/models/CosmeticOption.class */
public class CosmeticOption {
    private final List<CosmeticOptionEntry> options = new ArrayList();
    private Type type = Type.HIDDEN;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/models/CosmeticOption$Type.class */
    public enum Type {
        HIDDEN,
        DROPDOWN,
        CHECKBOX
    }

    public CosmeticOptionEntry first() {
        return this.options.get(0);
    }

    public Collection<CosmeticOptionEntry> options() {
        return this.options;
    }

    public Type type() {
        return this.type;
    }

    public void push(CosmeticOptionEntry entry) {
        this.options.add(entry);
        int length = this.options.size();
        String name = entry.getName();
        if (length == 2 && name != null && (name.equals("on") || name.equals("off"))) {
            this.type = Type.CHECKBOX;
        } else if (length >= 2) {
            this.type = Type.DROPDOWN;
        }
    }
}
