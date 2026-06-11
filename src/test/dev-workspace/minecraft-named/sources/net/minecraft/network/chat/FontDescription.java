package net.minecraft.network.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.component.ResolvableProfile;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/FontDescription.class */
public interface FontDescription {
    public static final Codec<FontDescription> CODEC = Identifier.CODEC.flatComapMap(Resource::new, $$0 -> {
        if ($$0 instanceof Resource) {
            Resource $$1 = (Resource) $$0;
            return DataResult.success($$1.id());
        }
        return DataResult.error(() -> {
            return "Unsupported font description type: " + String.valueOf($$0);
        });
    });
    public static final Resource DEFAULT = new Resource(Identifier.withDefaultNamespace(GameTestEnvironments.DEFAULT));

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/FontDescription$Resource.class */
    public static final class Resource extends Record implements FontDescription {
        private final Identifier id;

        public Resource(Identifier $$0) {
            this.id = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Resource.class), Resource.class, "id", "FIELD:Lnet/minecraft/network/chat/FontDescription$Resource;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Resource.class), Resource.class, "id", "FIELD:Lnet/minecraft/network/chat/FontDescription$Resource;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Resource.class, Object.class), Resource.class, "id", "FIELD:Lnet/minecraft/network/chat/FontDescription$Resource;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier id() {
            return this.id;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/FontDescription$AtlasSprite.class */
    public static final class AtlasSprite extends Record implements FontDescription {
        private final Identifier atlasId;
        private final Identifier spriteId;

        public AtlasSprite(Identifier $$0, Identifier $$1) {
            this.atlasId = $$0;
            this.spriteId = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AtlasSprite.class), AtlasSprite.class, "atlasId;spriteId", "FIELD:Lnet/minecraft/network/chat/FontDescription$AtlasSprite;->atlasId:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/network/chat/FontDescription$AtlasSprite;->spriteId:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AtlasSprite.class), AtlasSprite.class, "atlasId;spriteId", "FIELD:Lnet/minecraft/network/chat/FontDescription$AtlasSprite;->atlasId:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/network/chat/FontDescription$AtlasSprite;->spriteId:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AtlasSprite.class, Object.class), AtlasSprite.class, "atlasId;spriteId", "FIELD:Lnet/minecraft/network/chat/FontDescription$AtlasSprite;->atlasId:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/network/chat/FontDescription$AtlasSprite;->spriteId:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier atlasId() {
            return this.atlasId;
        }

        public Identifier spriteId() {
            return this.spriteId;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/FontDescription$PlayerSprite.class */
    public static final class PlayerSprite extends Record implements FontDescription {
        private final ResolvableProfile profile;
        private final boolean hat;

        public PlayerSprite(ResolvableProfile $$0, boolean $$1) {
            this.profile = $$0;
            this.hat = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PlayerSprite.class), PlayerSprite.class, "profile;hat", "FIELD:Lnet/minecraft/network/chat/FontDescription$PlayerSprite;->profile:Lnet/minecraft/world/item/component/ResolvableProfile;", "FIELD:Lnet/minecraft/network/chat/FontDescription$PlayerSprite;->hat:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PlayerSprite.class), PlayerSprite.class, "profile;hat", "FIELD:Lnet/minecraft/network/chat/FontDescription$PlayerSprite;->profile:Lnet/minecraft/world/item/component/ResolvableProfile;", "FIELD:Lnet/minecraft/network/chat/FontDescription$PlayerSprite;->hat:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PlayerSprite.class, Object.class), PlayerSprite.class, "profile;hat", "FIELD:Lnet/minecraft/network/chat/FontDescription$PlayerSprite;->profile:Lnet/minecraft/world/item/component/ResolvableProfile;", "FIELD:Lnet/minecraft/network/chat/FontDescription$PlayerSprite;->hat:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ResolvableProfile profile() {
            return this.profile;
        }

        public boolean hat() {
            return this.hat;
        }
    }
}
