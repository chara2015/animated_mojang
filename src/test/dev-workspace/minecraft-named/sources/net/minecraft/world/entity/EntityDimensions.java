package net.minecraft.world.entity;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.entity.EntityAttachments;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/EntityDimensions.class */
public final class EntityDimensions extends Record {
    private final float width;
    private final float height;
    private final float eyeHeight;
    private final EntityAttachments attachments;
    private final boolean fixed;

    public EntityDimensions(float $$0, float $$1, float $$2, EntityAttachments $$3, boolean $$4) {
        this.width = $$0;
        this.height = $$1;
        this.eyeHeight = $$2;
        this.attachments = $$3;
        this.fixed = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, EntityDimensions.class), EntityDimensions.class, "width;height;eyeHeight;attachments;fixed", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->width:F", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->height:F", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->eyeHeight:F", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->attachments:Lnet/minecraft/world/entity/EntityAttachments;", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->fixed:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, EntityDimensions.class), EntityDimensions.class, "width;height;eyeHeight;attachments;fixed", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->width:F", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->height:F", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->eyeHeight:F", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->attachments:Lnet/minecraft/world/entity/EntityAttachments;", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->fixed:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, EntityDimensions.class, Object.class), EntityDimensions.class, "width;height;eyeHeight;attachments;fixed", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->width:F", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->height:F", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->eyeHeight:F", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->attachments:Lnet/minecraft/world/entity/EntityAttachments;", "FIELD:Lnet/minecraft/world/entity/EntityDimensions;->fixed:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public float width() {
        return this.width;
    }

    public float height() {
        return this.height;
    }

    public float eyeHeight() {
        return this.eyeHeight;
    }

    public EntityAttachments attachments() {
        return this.attachments;
    }

    public boolean fixed() {
        return this.fixed;
    }

    private EntityDimensions(float $$0, float $$1, boolean $$2) {
        this($$0, $$1, defaultEyeHeight($$1), EntityAttachments.createDefault($$0, $$1), $$2);
    }

    private static float defaultEyeHeight(float $$0) {
        return $$0 * 0.85f;
    }

    public AABB makeBoundingBox(Vec3 $$0) {
        return makeBoundingBox($$0.x, $$0.y, $$0.z);
    }

    public AABB makeBoundingBox(double $$0, double $$1, double $$2) {
        float $$3 = this.width / 2.0f;
        float $$4 = this.height;
        return new AABB($$0 - ((double) $$3), $$1, $$2 - ((double) $$3), $$0 + ((double) $$3), $$1 + ((double) $$4), $$2 + ((double) $$3));
    }

    public EntityDimensions scale(float $$0) {
        return scale($$0, $$0);
    }

    public EntityDimensions scale(float $$0, float $$1) {
        if (this.fixed || ($$0 == 1.0f && $$1 == 1.0f)) {
            return this;
        }
        return new EntityDimensions(this.width * $$0, this.height * $$1, this.eyeHeight * $$1, this.attachments.scale($$0, $$1, $$0), false);
    }

    public static EntityDimensions scalable(float $$0, float $$1) {
        return new EntityDimensions($$0, $$1, false);
    }

    public static EntityDimensions fixed(float $$0, float $$1) {
        return new EntityDimensions($$0, $$1, true);
    }

    public EntityDimensions withEyeHeight(float $$0) {
        return new EntityDimensions(this.width, this.height, $$0, this.attachments, this.fixed);
    }

    public EntityDimensions withAttachments(EntityAttachments.Builder $$0) {
        return new EntityDimensions(this.width, this.height, this.eyeHeight, $$0.build(this.width, this.height), this.fixed);
    }
}
