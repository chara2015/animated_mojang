package net.labymod.api.client.entity;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/EntityPoseMapper.class */
@Referenceable
public interface EntityPoseMapper {
    EntityPose fromMinecraft(Object obj);

    @Nullable
    Object toMinecraft(EntityPose entityPose);
}
