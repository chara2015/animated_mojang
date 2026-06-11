package net.labymod.core.main.user.shop.item.metadata.type;

import java.util.Locale;
import java.util.UUID;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.core.main.user.shop.item.model.TextureDetails;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/type/TextureDetailsMetadata.class */
public class TextureDetailsMetadata extends AbstractMetadata<TextureDetails> {
    public TextureDetailsMetadata(String... keys) {
        super(keys);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [T, net.labymod.core.main.user.shop.item.model.TextureDetails] */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public void read(String value) throws MetadataException {
        try {
            this.value = TextureDetails.of(UUID.fromString(value));
        } catch (Throwable throwable) {
            throw new MetadataException(String.format(Locale.ROOT, "Failed to parse the value \"%s\"", value), throwable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public Object write() throws MetadataException {
        return ((TextureDetails) this.value).getUuid();
    }
}
