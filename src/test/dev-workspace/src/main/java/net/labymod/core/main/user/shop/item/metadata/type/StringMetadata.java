package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/type/StringMetadata.class */
public class StringMetadata extends AbstractMetadata<String> {
    public StringMetadata(String... keys) {
        super(keys);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public void read(String str) throws MetadataException {
        this.value = str;
    }

    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public Object write() throws MetadataException {
        return this.value;
    }
}
