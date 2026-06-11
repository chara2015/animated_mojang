package net.labymod.core.main.user.shop.item.metadata.type;

import net.labymod.core.main.user.shop.item.metadata.AbstractMetadata;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.core.main.user.shop.item.model.OffsetVector;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/metadata/type/OffsetVectorMetadata.class */
public class OffsetVectorMetadata extends AbstractMetadata<OffsetVector> {
    public OffsetVectorMetadata(String... keys) {
        super(keys);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [T, net.labymod.core.main.user.shop.item.model.OffsetVector] */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public void read(String value) throws MetadataException {
        String[] split = value.split(";");
        if (split.length != 3) {
            return;
        }
        this.value = new OffsetVector(split[0], split[1], split[2]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.main.user.shop.item.metadata.AbstractMetadata
    public Object write() throws MetadataException {
        double x = ((OffsetVector) this.value).getX();
        double y = ((OffsetVector) this.value).getY();
        ((OffsetVector) this.value).getZ();
        return x + ";" + x + ";" + y;
    }
}
