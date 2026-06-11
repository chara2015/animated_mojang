package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/type/SizeMetadata.class */
public class SizeMetadata extends AbstractMetadata<FloatVector4> {
    public SizeMetadata(String... keys) {
        super(keys);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, net.labymod.api.util.math.vector.FloatVector4] */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public void read(String value) throws MetadataException {
        String[] split = value.split(";");
        if (split.length == 0) {
            return;
        }
        this.value = new FloatVector4(getValue(split, 0), getValue(split, 1), getValue(split, 2), getValue(split, 3));
    }

    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public Object write() throws MetadataException {
        return null;
    }

    private float getValue(String[] options, int index) {
        return getValue(options, index, 0.0f);
    }

    private float getValue(String[] options, int index, float defaultValue) {
        if (options.length >= index + 1) {
            try {
                return Float.parseFloat(options[index]);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    private float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }
}
