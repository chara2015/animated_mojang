package net.labymod.v26_1.client.network.chat;

import java.util.Collections;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.ObjectComponent;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.object.ObjectSprite;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.CastUtil;
import net.labymod.v26_1.client.util.MinecraftUtil;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.ObjectContents;
import net.minecraft.network.chat.contents.objects.AtlasSprite;
import net.minecraft.network.chat.contents.objects.PlayerSprite;
import net.minecraft.world.item.component.ResolvableProfile;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/network/chat/VersionedObjectComponent.class */
public class VersionedObjectComponent extends VersionedBaseComponent<ObjectComponent, ObjectContents> implements ObjectComponent {
    private final ObjectSprite objectSprite;

    public VersionedObjectComponent(MutableComponent holder) {
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
    private ObjectSprite createSprite(ObjectContents contents) throws MatchException {
        AtlasSprite atlasSpriteContents = contents.contents();
        if (atlasSpriteContents instanceof AtlasSprite) {
            AtlasSprite atlasSprite = atlasSpriteContents;
            return ObjectSprite.atlasSprite((ResourceLocation) CastUtil.cast(atlasSprite.atlas()), (ResourceLocation) CastUtil.cast(atlasSprite.sprite()));
        }
        if (atlasSpriteContents instanceof PlayerSprite) {
            PlayerSprite playerSprite = (PlayerSprite) atlasSpriteContents;
            try {
                ResolvableProfile profile = playerSprite.player();
                boolean hat = playerSprite.hat();
                if (1 != 0) {
                    return ObjectSprite.playerProfile(MinecraftUtil.fromMinecraft(profile), hat);
                }
            } catch (Throwable th) {
                throw new MatchException(th.toString(), th);
            }
        }
        throw new IllegalStateException("Unknown object sprite type: " + String.valueOf(atlasSpriteContents));
    }
}
