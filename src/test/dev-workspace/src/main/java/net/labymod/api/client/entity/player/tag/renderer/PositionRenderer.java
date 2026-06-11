package net.labymod.api.client.entity.player.tag.renderer;

import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.service.Registry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/renderer/PositionRenderer.class */
public interface PositionRenderer {
    public static final int SPACE = 1;

    void render(Stack stack, EntitySnapshot entitySnapshot, SubmissionCollector submissionCollector, Registry<TagRenderer> registry, float f, TagType tagType);
}
