package net.minecraft.server.permissions;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/permissions/Permission.class */
public interface Permission {
    public static final Codec<Permission> FULL_CODEC = BuiltInRegistries.PERMISSION_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.codec();
    }, $$0 -> {
        return $$0;
    });
    public static final Codec<Permission> CODEC = Codec.either(FULL_CODEC, Identifier.CODEC).xmap($$0 -> {
        return (Permission) $$0.map($$0 -> {
            return $$0;
        }, Atom::create);
    }, $$02 -> {
        if (!($$02 instanceof Atom)) {
            return Either.left($$02);
        }
        Atom $$1 = (Atom) $$02;
        return Either.right($$1.id());
    });

    MapCodec<? extends Permission> codec();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/permissions/Permission$Atom.class */
    public static final class Atom extends Record implements Permission {
        private final Identifier id;
        public static final MapCodec<Atom> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Identifier.CODEC.fieldOf(Entity.TAG_ID).forGetter((v0) -> {
                return v0.id();
            })).apply($$0, Atom::new);
        });

        public Atom(Identifier $$0) {
            this.id = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Atom.class), Atom.class, "id", "FIELD:Lnet/minecraft/server/permissions/Permission$Atom;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Atom.class), Atom.class, "id", "FIELD:Lnet/minecraft/server/permissions/Permission$Atom;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Atom.class, Object.class), Atom.class, "id", "FIELD:Lnet/minecraft/server/permissions/Permission$Atom;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier id() {
            return this.id;
        }

        @Override // net.minecraft.server.permissions.Permission
        public MapCodec<Atom> codec() {
            return MAP_CODEC;
        }

        public static Atom create(String $$0) {
            return create(Identifier.withDefaultNamespace($$0));
        }

        public static Atom create(Identifier $$0) {
            return new Atom($$0);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/permissions/Permission$HasCommandLevel.class */
    public static final class HasCommandLevel extends Record implements Permission {
        private final PermissionLevel level;
        public static final MapCodec<HasCommandLevel> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(PermissionLevel.CODEC.fieldOf("level").forGetter((v0) -> {
                return v0.level();
            })).apply($$0, HasCommandLevel::new);
        });

        public HasCommandLevel(PermissionLevel $$0) {
            this.level = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, HasCommandLevel.class), HasCommandLevel.class, "level", "FIELD:Lnet/minecraft/server/permissions/Permission$HasCommandLevel;->level:Lnet/minecraft/server/permissions/PermissionLevel;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, HasCommandLevel.class), HasCommandLevel.class, "level", "FIELD:Lnet/minecraft/server/permissions/Permission$HasCommandLevel;->level:Lnet/minecraft/server/permissions/PermissionLevel;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, HasCommandLevel.class, Object.class), HasCommandLevel.class, "level", "FIELD:Lnet/minecraft/server/permissions/Permission$HasCommandLevel;->level:Lnet/minecraft/server/permissions/PermissionLevel;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PermissionLevel level() {
            return this.level;
        }

        @Override // net.minecraft.server.permissions.Permission
        public MapCodec<HasCommandLevel> codec() {
            return MAP_CODEC;
        }
    }
}
