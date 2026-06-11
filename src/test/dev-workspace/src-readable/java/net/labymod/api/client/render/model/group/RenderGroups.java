package net.labymod.api.client.render.model.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/group/RenderGroups.class */
public final class RenderGroups {
    private static final Map<String, RenderGroup> GROUPS = new HashMap();
    private static List<RenderGroup> sortedGroups = Collections.emptyList();
    public static final RenderGroup OPAQUE = createGroup("opaque", 0, false);
    public static final RenderGroup CUTOUT = createGroup("cutout", 100, false);
    public static final RenderGroup TRANSLUCENT = createGroup("translucent", 200, true);
    public static final RenderGroup EMISSIVE = createGroup("emissive", 300, true);
    public static final RenderGroup OVERLAY = createGroup("overlay", 400, false);

    private RenderGroups() {
    }

    @NotNull
    public static RenderGroup createGroup(@NotNull String id, int sortOrder, boolean depthSorting) {
        RenderGroup existing = GROUPS.get(id);
        if (existing != null) {
            return existing;
        }
        RenderGroup group = new StandardRenderGroup(id, sortOrder, depthSorting);
        GROUPS.put(id, group);
        rebuildSortedGroups();
        return group;
    }

    @Nullable
    public static RenderGroup byId(@Nullable String id) {
        if (id == null) {
            return null;
        }
        return GROUPS.get(id);
    }

    @NotNull
    public static List<RenderGroup> values() {
        return sortedGroups;
    }

    private static void rebuildSortedGroups() {
        List<RenderGroup> sorted = new ArrayList<>(GROUPS.values());
        sorted.sort(Comparator.comparingInt((v0) -> {
            return v0.getSortOrder();
        }));
        sortedGroups = Collections.unmodifiableList(sorted);
    }
}
