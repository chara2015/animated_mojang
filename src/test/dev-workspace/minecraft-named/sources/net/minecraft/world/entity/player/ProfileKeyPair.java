package net.minecraft.world.entity.player;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.security.PrivateKey;
import java.time.Instant;
import net.minecraft.util.Crypt;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/player/ProfileKeyPair.class */
public final class ProfileKeyPair extends Record {
    private final PrivateKey privateKey;
    private final ProfilePublicKey publicKey;
    private final Instant refreshedAfter;
    public static final Codec<ProfileKeyPair> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Crypt.PRIVATE_KEY_CODEC.fieldOf("private_key").forGetter((v0) -> {
            return v0.privateKey();
        }), ProfilePublicKey.TRUSTED_CODEC.fieldOf("public_key").forGetter((v0) -> {
            return v0.publicKey();
        }), ExtraCodecs.INSTANT_ISO8601.fieldOf("refreshed_after").forGetter((v0) -> {
            return v0.refreshedAfter();
        })).apply($$0, ProfileKeyPair::new);
    });

    public ProfileKeyPair(PrivateKey $$0, ProfilePublicKey $$1, Instant $$2) {
        this.privateKey = $$0;
        this.publicKey = $$1;
        this.refreshedAfter = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ProfileKeyPair.class), ProfileKeyPair.class, "privateKey;publicKey;refreshedAfter", "FIELD:Lnet/minecraft/world/entity/player/ProfileKeyPair;->privateKey:Ljava/security/PrivateKey;", "FIELD:Lnet/minecraft/world/entity/player/ProfileKeyPair;->publicKey:Lnet/minecraft/world/entity/player/ProfilePublicKey;", "FIELD:Lnet/minecraft/world/entity/player/ProfileKeyPair;->refreshedAfter:Ljava/time/Instant;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ProfileKeyPair.class), ProfileKeyPair.class, "privateKey;publicKey;refreshedAfter", "FIELD:Lnet/minecraft/world/entity/player/ProfileKeyPair;->privateKey:Ljava/security/PrivateKey;", "FIELD:Lnet/minecraft/world/entity/player/ProfileKeyPair;->publicKey:Lnet/minecraft/world/entity/player/ProfilePublicKey;", "FIELD:Lnet/minecraft/world/entity/player/ProfileKeyPair;->refreshedAfter:Ljava/time/Instant;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ProfileKeyPair.class, Object.class), ProfileKeyPair.class, "privateKey;publicKey;refreshedAfter", "FIELD:Lnet/minecraft/world/entity/player/ProfileKeyPair;->privateKey:Ljava/security/PrivateKey;", "FIELD:Lnet/minecraft/world/entity/player/ProfileKeyPair;->publicKey:Lnet/minecraft/world/entity/player/ProfilePublicKey;", "FIELD:Lnet/minecraft/world/entity/player/ProfileKeyPair;->refreshedAfter:Ljava/time/Instant;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public PrivateKey privateKey() {
        return this.privateKey;
    }

    public ProfilePublicKey publicKey() {
        return this.publicKey;
    }

    public Instant refreshedAfter() {
        return this.refreshedAfter;
    }

    public boolean dueRefresh() {
        return this.refreshedAfter.isBefore(Instant.now());
    }
}
