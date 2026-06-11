package net.labymod.v1_18_2.client.font.component;

import javax.inject.Singleton;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/font/component/VersionedComponentMapper.class */
@Singleton
@Implements(ComponentMapper.class)
public class VersionedComponentMapper implements ComponentMapper {
    @Override // net.labymod.api.client.render.font.ComponentMapper
    public String getTranslationKeyOfComponent(Object minecraftComponent) {
        if (minecraftComponent instanceof qy) {
            return ((qy) minecraftComponent).i();
        }
        return null;
    }
}
