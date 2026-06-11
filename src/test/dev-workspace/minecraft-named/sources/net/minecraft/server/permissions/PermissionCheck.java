package net.minecraft.server.permissions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.registries.BuiltInRegistries;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/permissions/PermissionCheck.class */
public interface PermissionCheck {
    public static final Codec<PermissionCheck> CODEC = BuiltInRegistries.PERMISSION_CHECK_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.codec();
    }, $$0 -> {
        return $$0;
    });

    boolean check(PermissionSet permissionSet);

    MapCodec<? extends PermissionCheck> codec();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/permissions/PermissionCheck$AlwaysPass.class */
    public static class AlwaysPass implements PermissionCheck {
        public static final AlwaysPass INSTANCE = new AlwaysPass();
        public static final MapCodec<AlwaysPass> MAP_CODEC = MapCodec.unit(INSTANCE);

        private AlwaysPass() {
        }

        @Override // net.minecraft.server.permissions.PermissionCheck
        public boolean check(PermissionSet $$0) {
            return true;
        }

        @Override // net.minecraft.server.permissions.PermissionCheck
        public MapCodec<AlwaysPass> codec() {
            return MAP_CODEC;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/permissions/PermissionCheck$Require.class */
    public static final class Require extends Record implements PermissionCheck {
        private final Permission permission;
        public static final MapCodec<Require> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Permission.CODEC.fieldOf("permission").forGetter((v0) -> {
                return v0.permission();
            })).apply($$0, Require::new);
        });

        public Require(Permission $$0) {
            this.permission = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Require.class), Require.class, "permission", "FIELD:Lnet/minecraft/server/permissions/PermissionCheck$Require;->permission:Lnet/minecraft/server/permissions/Permission;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Require.class), Require.class, "permission", "FIELD:Lnet/minecraft/server/permissions/PermissionCheck$Require;->permission:Lnet/minecraft/server/permissions/Permission;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Require.class, Object.class), Require.class, "permission", "FIELD:Lnet/minecraft/server/permissions/PermissionCheck$Require;->permission:Lnet/minecraft/server/permissions/Permission;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Permission permission() {
            return this.permission;
        }

        @Override // net.minecraft.server.permissions.PermissionCheck
        public MapCodec<Require> codec() {
            return MAP_CODEC;
        }

        @Override // net.minecraft.server.permissions.PermissionCheck
        public boolean check(PermissionSet $$0) {
            return $$0.hasPermission(this.permission);
        }
    }
}
