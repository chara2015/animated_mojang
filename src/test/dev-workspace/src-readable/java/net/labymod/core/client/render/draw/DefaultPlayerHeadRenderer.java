package net.labymod.core.client.render.draw;

import java.util.Objects;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.PlayerHeadRenderer;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/DefaultPlayerHeadRenderer.class */
@Singleton
@Implements(PlayerHeadRenderer.class)
public class DefaultPlayerHeadRenderer extends DefaultRectangleBuilder<PlayerHeadRenderer> implements PlayerHeadRenderer {
    private ResourceLocation resourceLocation;
    private boolean wearingHat;

    @Inject
    public DefaultPlayerHeadRenderer() {
        resetBuilder();
    }

    @Override // net.labymod.api.client.render.draw.builder.DirectRendererBuilder
    public void render(Stack stack) {
        validateBuilder();
        BatchResourceRenderer batchResourceRenderer = Laby.references().resourceRenderer().beginBatch(stack, this.resourceLocation);
        ((BatchResourceRenderer) ((BatchResourceRenderer) ((BatchResourceRenderer) batchResourceRenderer.pos(this.x, this.y)).size(this.width, this.height)).color(this.color)).sprite(8.0f, 8.0f, 8.0f, 8.0f).resolution(64.0f, 64.0f).build();
        if (this.wearingHat) {
            ((BatchResourceRenderer) ((BatchResourceRenderer) ((BatchResourceRenderer) batchResourceRenderer.pos(this.x, this.y)).size(this.width, this.height)).color(this.color)).sprite(40.0f, 8.0f, 8.0f, 8.0f).resolution(64.0f, 64.0f).build();
        }
        batchResourceRenderer.upload();
        resetBuilder();
    }

    @Override // net.labymod.api.client.render.draw.builder.DirectRendererBuilder
    public void render(Stack stack, RenderState renderState) {
        validateBuilder();
        BatchResourceRenderer batchResourceRenderer = Laby.references().resourceRenderer().beginBatch(stack, this.resourceLocation);
        ((BatchResourceRenderer) ((BatchResourceRenderer) ((BatchResourceRenderer) batchResourceRenderer.pos(this.x, this.y)).size(this.width, this.height)).color(this.color)).sprite(8.0f, 8.0f, 8.0f, 8.0f).resolution(64.0f, 64.0f).build();
        if (this.wearingHat) {
            ((BatchResourceRenderer) ((BatchResourceRenderer) ((BatchResourceRenderer) batchResourceRenderer.pos(this.x, this.y)).size(this.width, this.height)).color(this.color)).sprite(40.0f, 8.0f, 8.0f, 8.0f).resolution(64.0f, 64.0f).build();
        }
        batchResourceRenderer.upload(renderState);
        resetBuilder();
    }

    @Override // net.labymod.api.client.render.draw.builder.PlayerHeadBuilder
    public PlayerHeadRenderer player(UUID uniqueId) {
        this.resourceLocation = Laby.references().mojangTextureService().getTexture(uniqueId, MojangTextureType.SKIN).getCompleted();
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.PlayerHeadBuilder
    public PlayerHeadRenderer player(GameProfile profile) {
        return player(profile.getUniqueId());
    }

    @Override // net.labymod.api.client.render.draw.builder.PlayerHeadBuilder
    public PlayerHeadRenderer player(ResourceLocation skinTexture) {
        this.resourceLocation = skinTexture;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.PlayerHeadBuilder
    public PlayerHeadRenderer wearingHat(boolean wearingHat) {
        this.wearingHat = wearingHat;
        return this;
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.core.client.render.draw.builder.DefaultRendererBuilder, net.labymod.api.client.render.draw.builder.RendererBuilder
    public void validateBuilder() {
        super.validateBuilder();
        Objects.requireNonNull(this.resourceLocation, "Missing skin texture (call player())");
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder, net.labymod.core.client.render.draw.builder.DefaultRendererBuilder, net.labymod.api.client.render.draw.builder.RendererBuilder
    public void resetBuilder() {
        super.resetBuilder();
        this.resourceLocation = null;
        this.wearingHat = true;
    }
}
