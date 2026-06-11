package net.labymod.core.main.user.shop.item.metadata.type;

import java.util.Arrays;
import java.util.Locale;
import net.labymod.api.util.Color;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/type/ColorArrayMetadata.class */
public class ColorArrayMetadata extends AbstractMetadata<Color[]> {
    public ColorArrayMetadata(String... keys) {
        super(keys);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [T, java.lang.Object[]] */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public void read(String value) throws MetadataException {
        try {
            int length = ((Color[]) this.value).length;
            this.value = Arrays.copyOf((Color[]) this.value, length + 1);
            ((Color[]) this.value)[length] = Color.of("#" + value);
        } catch (Exception exception) {
            throw new MetadataException(String.format(Locale.ROOT, "The value \"%s\" could not be converted to a color array", value), exception);
        }
    }

    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public Object write() throws MetadataException {
        throw new MetadataException("Color array metadata requires an index to write");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Object write(int index) throws MetadataException {
        try {
            Color color = ((Color[]) this.value)[index];
            return String.format(Locale.ROOT, "%02x%02x%02x", Integer.valueOf(color.getRed()), Integer.valueOf(color.getGreen()), Integer.valueOf(color.getBlue()));
        } catch (Exception exception) {
            throw new MetadataException(String.format(Locale.ROOT, "The array at position %s with length %s could not be converted to hex color", Integer.valueOf(index), Integer.valueOf(((Color[]) this.value).length)), exception);
        }
    }
}
