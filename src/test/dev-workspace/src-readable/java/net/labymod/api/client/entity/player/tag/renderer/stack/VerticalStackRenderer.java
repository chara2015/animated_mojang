package net.labymod.api.client.entity.player.tag.renderer.stack;

import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.service.Registry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/renderer/stack/VerticalStackRenderer.class */
public abstract class VerticalStackRenderer extends AbstractPositionRenderer {
    private float offset;

    protected abstract float getEntryYOffset();

    protected VerticalStackRenderer() {
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer, net.labymod.api.client.entity.player.tag.renderer.PositionRenderer
    public void render(Stack stack, EntitySnapshot snapshot, SubmissionCollector submissionCollector, Registry<TagRenderer> tags, float usernameWidth, TagType tagType) {
        this.offset = getEntryYOffset();
        super.render(stack, snapshot, submissionCollector, tags, usernameWidth, tagType);
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer
    protected void setupPosition(Stack stack) {
        stack.translate(0.0f, this.offset, 0.0f);
        stack.scale(this.scale, this.scale, 1.0f);
        stack.translate((-this.width) / 2.0f, -this.height, 0.0f);
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer
    protected void shiftPosition(Stack stack) {
        float shift = (this.height * this.scale) + 1.0f;
        stack.translate(0.0f, -shift, 0.0f);
    }
}
