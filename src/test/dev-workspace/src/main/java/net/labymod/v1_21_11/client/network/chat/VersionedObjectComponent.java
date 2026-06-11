package net.labymod.v1_21_11.client.network.chat;

import java.util.Collections;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ObjectComponent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.object.ObjectSprite;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.v1_21_11.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/network/chat/VersionedObjectComponent.class */
public class VersionedObjectComponent extends VersionedBaseComponent<ObjectComponent, zm> implements ObjectComponent {
    private final ObjectSprite objectSprite;

    public VersionedObjectComponent(yw holder) {
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
    private ObjectSprite createSprite(zm contents) throws MatchException {
        zy zyVarB = contents.b();
        if (zyVarB instanceof zy) {
            zy atlasSprite = zyVarB;
            return ObjectSprite.atlasSprite((ResourceLocation) CastUtil.cast(atlasSprite.d()), (ResourceLocation) CastUtil.cast(atlasSprite.e()));
        }
        if (zyVarB instanceof aab) {
            aab aabVar = (aab) zyVarB;
            try {
                doy profile = aabVar.d();
                boolean hat = aabVar.e();
                if (1 != 0) {
                    return ObjectSprite.playerProfile(MinecraftUtil.fromMinecraft(profile), hat);
                }
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
        throw new IllegalStateException("Unknown object sprite type: " + String.valueOf(zyVarB));
    }
}
