package net.labymod.api.client.world.lighting;

import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/lighting/LightingLayerMapper.class */
@Referenceable
public interface LightingLayerMapper {
    LightType fromMinecraft(Object obj);

    Object toMinecraft(LightType lightType);
}
