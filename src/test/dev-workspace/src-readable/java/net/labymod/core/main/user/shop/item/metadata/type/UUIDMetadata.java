package net.labymod.core.main.user.shop.item.metadata.type;

import java.util.Locale;
import java.util.UUID;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/type/UUIDMetadata.class */
public class UUIDMetadata extends AbstractMetadata<UUID> {
    public UUIDMetadata(String... keys) {
        super(keys);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [T, java.util.UUID] */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public void read(String value) throws MetadataException {
        try {
            this.value = UUID.fromString(value);
        } catch (Exception exception) {
            throw new MetadataException(String.format(Locale.ROOT, "The value \"%s\" could not be converted to a UUID", value), exception);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public Object write() throws MetadataException {
        return ((UUID) this.value).toString();
    }
}
