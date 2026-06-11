package net.labymod.api.client.world.signobject;

import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.world.block.BlockPosition;
import net.labymod.api.client.world.signobject.object.SignObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/signobject/PlacedSignObject.class */
public interface PlacedSignObject {
    public static final SignBlockEntity.SignSide[] SIDES = SignBlockEntity.SignSide.values();

    SignBlockEntity sign();

    SignObject<?>[] objects();

    SignObject<?> object(SignBlockEntity.SignSide signSide);

    default BlockPosition position() {
        return sign().position();
    }
}
