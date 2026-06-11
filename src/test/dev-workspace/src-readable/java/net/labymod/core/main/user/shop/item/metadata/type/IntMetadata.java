package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/type/IntMetadata.class */
public class IntMetadata extends AbstractMetadata<Integer> {
    public IntMetadata(String... keys) {
        super(keys);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r1v4, types: [T, java.lang.Integer] */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public void read(String value) throws MetadataException {
        try {
            this.value = Integer.valueOf(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            this.value = 0;
        }
    }

    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public Object write() throws MetadataException {
        return null;
    }
}
