package net.labymod.v1_21_10.client.network.chat;

import java.util.Collections;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ObjectComponent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.object.ObjectSprite;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_10.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/network/chat/VersionedObjectComponent.class */
public class VersionedObjectComponent extends VersionedBaseComponent<ObjectComponent, zc> implements ObjectComponent {
    private final ObjectSprite objectSprite;

    public VersionedObjectComponent(ym holder) {
        super(holder);
        this.objectSprite = createSprite(getContents());
    }

    @Override // net.labymod.api.client.component.BaseComponent, net.labymod.api.client.component.Component
    public ObjectComponent plainCopy() {
        return ComponentService.objectComponent(getObjectSprite(), Style.EMPTY, Collections.emptyList());
    }

    @Override // net.labymod.api.client.component.ObjectComponent
    public ObjectSprite getObjectSprite() {
        return this.objectSprite;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private ObjectSprite createSprite(zc contents) throws MatchException {
        zo zoVarB = contents.b();
        if (zoVarB instanceof zo) {
            zo atlasSprite = zoVarB;
            return ObjectSprite.atlasSprite((ResourceLocation) CastUtil.cast(atlasSprite.d()), (ResourceLocation) CastUtil.cast(atlasSprite.e()));
        }
        if (zoVarB instanceof zr) {
            zr zrVar = (zr) zoVarB;
            try {
                dkq profile = zrVar.d();
                boolean hat = zrVar.e();
                if (1 != 0) {
                    return ObjectSprite.playerProfile(MinecraftUtil.fromMinecraft(profile), hat);
                }
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
        throw new IllegalStateException("Unknown object sprite type: " + String.valueOf(zoVarB));
    }
}
