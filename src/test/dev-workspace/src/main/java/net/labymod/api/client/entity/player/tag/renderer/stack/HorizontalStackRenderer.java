package net.labymod.api.client.entity.player.tag.renderer.stack;

import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.service.Registry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/renderer/stack/HorizontalStackRenderer.class */
public abstract class HorizontalStackRenderer extends AbstractPositionRenderer {
    private final HorizontalPosition horizontalPosition;
    private float mirror;
    private float offsetX;
    private float offsetY;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/renderer/stack/HorizontalStackRenderer$HorizontalPosition.class */
    public enum HorizontalPosition {
        LEFT,
        RIGHT
    }

    protected HorizontalStackRenderer(HorizontalPosition horizontalPosition) {
        this.horizontalPosition = horizontalPosition;
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer, net.labymod.api.client.entity.player.tag.renderer.PositionRenderer
    public void render(Stack stack, EntitySnapshot snapshot, SubmissionCollector submissionCollector, Registry<TagRenderer> tags, float usernameWidth, TagType tagType) {
        this.mirror = this.horizontalPosition == HorizontalPosition.RIGHT ? 1.0f : -1.0f;
        this.offsetX = (usernameWidth / 2.0f) * this.mirror;
        this.offsetY = 4.0f;
        super.render(stack, snapshot, submissionCollector, tags, usernameWidth, tagType);
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer
    protected void setupPosition(Stack stack) {
        stack.translate(this.offsetX + 0.5f + (1.0f * this.mirror), this.offsetY, 0.0f);
        stack.scale(this.scale, this.scale, 1.0f);
        stack.translate(this.horizontalPosition == HorizontalPosition.RIGHT ? 0.0f : -this.width, (-this.height) / 2.0f, 0.0f);
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.AbstractPositionRenderer
    protected void shiftPosition(Stack stack) {
        float shift = (this.width * this.scale) + 1.0f;
        stack.translate(shift * this.mirror, 0.0f, 0.0f);
    }
}
