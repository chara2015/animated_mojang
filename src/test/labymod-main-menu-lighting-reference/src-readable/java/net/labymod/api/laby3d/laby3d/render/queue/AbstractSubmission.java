package net.labymod.api.laby3d.render.queue;

import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.tag.TaggedObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/render/queue/AbstractSubmission.class */
public abstract class AbstractSubmission implements Submission {
    private final Material material;
    private final TaggedObject taggedObject = new TaggedObject();
    private long sequenceId;

    protected AbstractSubmission(Material material) {
        this.material = material;
    }

    @Override // net.labymod.api.laby3d.render.queue.Submission
    public Material material() {
        return this.material;
    }

    @Override // net.labymod.api.laby3d.render.queue.Submission
    public long sequenceId() {
        return this.sequenceId;
    }

    @Override // net.labymod.api.laby3d.render.queue.Submission
    public void setSequenceId(long sequenceId) {
        this.sequenceId = sequenceId;
    }

    @Override // net.labymod.api.laby3d.render.queue.Submission, net.labymod.api.tag.Taggable
    public TaggedObject taggedObject() {
        return this.taggedObject;
    }
}
