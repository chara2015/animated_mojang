package net.labymod.core.client.render.model.group;

import net.labymod.api.client.render.model.group.RenderGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/group/DefaultRenderGroup.class */
public class DefaultRenderGroup implements RenderGroup {
    private final String id;
    private final int sortOrder;
    private final boolean depthSorting;

    public DefaultRenderGroup(@NotNull String id, int sortOrder, boolean depthSorting) {
        this.id = id;
        this.sortOrder = sortOrder;
        this.depthSorting = depthSorting;
    }

    @Override // net.labymod.api.client.render.model.group.RenderGroup
    @NotNull
    public String getId() {
        return this.id;
    }

    @Override // net.labymod.api.client.render.model.group.RenderGroup
    public int getSortOrder() {
        return this.sortOrder;
    }

    @Override // net.labymod.api.client.render.model.group.RenderGroup
    public boolean requiresDepthSorting() {
        return this.depthSorting;
    }

    @NotNull
    public String toString() {
        return "RenderGroup{" + this.id + ", order=" + this.sortOrder + "}";
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RenderGroup)) {
            return false;
        }
        return this.id.equals(((RenderGroup) obj).getId());
    }

    public int hashCode() {
        return this.id.hashCode();
    }
}
