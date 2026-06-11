package net.minecraft.world.level.validation;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.file.Path;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/validation/ForbiddenSymlinkInfo.class */
public final class ForbiddenSymlinkInfo extends Record {
    private final Path link;
    private final Path target;

    public ForbiddenSymlinkInfo(Path $$0, Path $$1) {
        this.link = $$0;
        this.target = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ForbiddenSymlinkInfo.class), ForbiddenSymlinkInfo.class, "link;target", "FIELD:Lnet/minecraft/world/level/validation/ForbiddenSymlinkInfo;->link:Ljava/nio/file/Path;", "FIELD:Lnet/minecraft/world/level/validation/ForbiddenSymlinkInfo;->target:Ljava/nio/file/Path;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ForbiddenSymlinkInfo.class), ForbiddenSymlinkInfo.class, "link;target", "FIELD:Lnet/minecraft/world/level/validation/ForbiddenSymlinkInfo;->link:Ljava/nio/file/Path;", "FIELD:Lnet/minecraft/world/level/validation/ForbiddenSymlinkInfo;->target:Ljava/nio/file/Path;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ForbiddenSymlinkInfo.class, Object.class), ForbiddenSymlinkInfo.class, "link;target", "FIELD:Lnet/minecraft/world/level/validation/ForbiddenSymlinkInfo;->link:Ljava/nio/file/Path;", "FIELD:Lnet/minecraft/world/level/validation/ForbiddenSymlinkInfo;->target:Ljava/nio/file/Path;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Path link() {
        return this.link;
    }

    public Path target() {
        return this.target;
    }
}
