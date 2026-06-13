package net.labymod.api.laby3d.render.queue;

import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.tag.Taggable;
import net.labymod.api.tag.TaggedObject;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/Submission.class */
public interface Submission extends Taggable {
    Material material();

    long sequenceId();

    @ApiStatus.Internal
    void setSequenceId(long j);

    @Override // net.labymod.api.tag.Taggable
    TaggedObject taggedObject();
}
