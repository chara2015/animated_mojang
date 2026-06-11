package net.labymod.core.client.render.model.group;

import net.labymod.api.client.render.model.group.RenderGroup;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/group/DefaultRenderGroupBuilder.class */
@Implements(RenderGroup.Builder.class)
public class DefaultRenderGroupBuilder implements RenderGroup.Builder {
    private String id;
    private int sortOrder;
    private boolean depthSorting;

    @Override // net.labymod.api.client.render.model.group.RenderGroup.Builder
    @NotNull
    public RenderGroup.Builder id(@NotNull String id) {
        this.id = id;
        return this;
    }

    @Override // net.labymod.api.client.render.model.group.RenderGroup.Builder
    @NotNull
    public RenderGroup.Builder sortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    @Override // net.labymod.api.client.render.model.group.RenderGroup.Builder
    @NotNull
    public RenderGroup.Builder depthSorting(boolean depthSorting) {
        this.depthSorting = depthSorting;
        return this;
    }

    @Override // net.labymod.api.client.render.model.group.RenderGroup.Builder
    @NotNull
    public RenderGroup build() {
        if (this.id == null || this.id.isEmpty()) {
            throw new IllegalStateException("Render group id must be set");
        }
        return new DefaultRenderGroup(this.id, this.sortOrder, this.depthSorting);
    }
}
