package net.labymod.v26_1.laby3d;

import com.mojang.blaze3d.ProjectionType;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/laby3d/ProjectionAccessor.class */
public interface ProjectionAccessor {
    ProjectionType labyMod$getProjectionType();

    static ProjectionAccessor self(Object obj) {
        return (ProjectionAccessor) CastUtil.requireInstanceOf(obj, ProjectionAccessor.class);
    }
}
