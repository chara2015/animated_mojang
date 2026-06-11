package net.labymod.api.client.render.model.group;

import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/group/RenderGroup.class */
public interface RenderGroup {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/group/RenderGroup$Builder.class */
    @Referenceable
    public interface Builder {
        @NotNull
        Builder id(@NotNull String str);

        @NotNull
        Builder sortOrder(int i);

        @NotNull
        Builder depthSorting(boolean z);

        @NotNull
        RenderGroup build();
    }

    @NotNull
    String getId();

    int getSortOrder();

    boolean requiresDepthSorting();

    @NotNull
    static Builder builder() {
        return Laby.references().renderGroupBuilder();
    }
}
