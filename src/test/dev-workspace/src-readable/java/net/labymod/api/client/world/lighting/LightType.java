package net.labymod.api.client.world.lighting;

import net.labymod.api.Laby;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/lighting/LightType.class */
public enum LightType {
    SKY,
    BLOCK;

    private final LightingLayerMapper mapper = Laby.references().lightingLayerMapper();

    LightType() {
    }

    public Object toMinecraft() {
        return this.mapper.toMinecraft(this);
    }
}
