package net.labymod.core.main.user.shop.item.metadata.type;

import java.util.Locale;
import net.labymod.api.util.Color;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/type/ColorMetadata.class */
public class ColorMetadata extends AbstractMetadata<Color> {
    public ColorMetadata(String... keys) {
        super(keys);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [T, net.labymod.api.util.Color] */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public void read(String value) throws MetadataException {
        try {
            this.value = Color.of("#" + value);
        } catch (Exception exception) {
            throw new MetadataException(String.format(Locale.ROOT, "The value \"%s\" could not be converted to a color", value), exception);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public Object write() throws MetadataException {
        try {
            Color color = (Color) this.value;
            return String.format(Locale.ROOT, "%02x%02x%02x", Integer.valueOf(color.getRed()), Integer.valueOf(color.getGreen()), Integer.valueOf(color.getBlue()));
        } catch (Exception exception) {
            throw new MetadataException(String.format(Locale.ROOT, "%s could not be converted to hex color", this.value), exception);
        }
    }
}
