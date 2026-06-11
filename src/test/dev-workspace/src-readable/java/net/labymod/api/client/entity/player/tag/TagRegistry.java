package net.labymod.api.client.entity.player.tag;

import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/TagRegistry.class */
@Referenceable
public interface TagRegistry {
    void render(Stack stack, EntitySnapshot entitySnapshot, float f);

    void render(Stack stack, EntitySnapshot entitySnapshot, float f, TagType tagType);

    void register(String str, PositionType positionType, TagRenderer tagRenderer);

    void registerAfter(String str, String str2, PositionType positionType, TagRenderer tagRenderer);

    void registerBefore(String str, String str2, PositionType positionType, TagRenderer tagRenderer);

    void unregister(String str);
}
