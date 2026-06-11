package net.labymod.v1_20_4.client.font.component;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.models.Implements;
import net.labymod.v1_20_4.client.network.chat.MutableComponentAccessor;
import net.labymod.v1_20_4.client.network.chat.VersionedBaseComponent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/font/component/VersionedComponentMapper.class */
@Implements(ComponentMapper.class)
public class VersionedComponentMapper implements ComponentMapper {
    @Override // net.labymod.api.client.render.font.ComponentMapper
    public Component fromMinecraftComponent(Object component) {
        if (component == null) {
            return null;
        }
        return ((MutableComponentAccessor) component).getLabyComponent();
    }

    @Override // net.labymod.api.client.render.font.ComponentMapper
    public Object toMinecraftComponent(Component component) {
        if (component == null) {
            return null;
        }
        return ((VersionedBaseComponent) component).getHolder();
    }

    @Override // net.labymod.api.client.render.font.ComponentMapper
    public String getTranslationKeyOfComponent(Object minecraftComponent) {
        if (minecraftComponent instanceof vf) {
            vf component = (vf) minecraftComponent;
            wq wqVarB = component.b();
            if (wqVarB instanceof wq) {
                return wqVarB.b();
            }
            return null;
        }
        return null;
    }
}
