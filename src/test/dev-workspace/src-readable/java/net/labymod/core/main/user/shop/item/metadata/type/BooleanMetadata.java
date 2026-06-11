package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/type/BooleanMetadata.class */
public class BooleanMetadata extends AbstractMetadata<Boolean> {
    public BooleanMetadata(String... keys) {
        super(keys);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [T, java.lang.Boolean] */
    /* JADX WARN: Type inference failed for: r1v5, types: [T, java.lang.Boolean] */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public void read(String value) throws MetadataException {
        if (value.equals("1")) {
            this.value = true;
        } else {
            this.value = Boolean.valueOf(Boolean.parseBoolean(value));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public Object write() throws MetadataException {
        return Integer.valueOf(((Boolean) this.value).booleanValue() ? 1 : 0);
    }
}
