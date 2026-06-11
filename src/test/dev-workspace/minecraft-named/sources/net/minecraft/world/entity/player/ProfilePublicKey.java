package net.minecraft.world.entity.player;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.PublicKey;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Arrays;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ThrowingComponent;
import net.minecraft.util.Crypt;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.SignatureValidator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/player/ProfilePublicKey.class */
public final class ProfilePublicKey extends Record {
    private final Data data;
    public static final Component EXPIRED_PROFILE_PUBLIC_KEY = Component.translatable("multiplayer.disconnect.expired_public_key");
    private static final Component INVALID_SIGNATURE = Component.translatable("multiplayer.disconnect.invalid_public_key_signature");
    public static final Duration EXPIRY_GRACE_PERIOD = Duration.ofHours(8);
    public static final Codec<ProfilePublicKey> TRUSTED_CODEC = Data.CODEC.xmap(ProfilePublicKey::new, (v0) -> {
        return v0.data();
    });

    public ProfilePublicKey(Data $$0) {
        this.data = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ProfilePublicKey.class), ProfilePublicKey.class, "data", "FIELD:Lnet/minecraft/world/entity/player/ProfilePublicKey;->data:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ProfilePublicKey.class), ProfilePublicKey.class, "data", "FIELD:Lnet/minecraft/world/entity/player/ProfilePublicKey;->data:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ProfilePublicKey.class, Object.class), ProfilePublicKey.class, "data", "FIELD:Lnet/minecraft/world/entity/player/ProfilePublicKey;->data:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Data data() {
        return this.data;
    }

    public static ProfilePublicKey createValidated(SignatureValidator $$0, UUID $$1, Data $$2) throws ValidationException {
        if (!$$2.validateSignature($$0, $$1)) {
            throw new ValidationException(INVALID_SIGNATURE);
        }
        return new ProfilePublicKey($$2);
    }

    public SignatureValidator createSignatureValidator() {
        return SignatureValidator.from(this.data.key, Crypt.SIGNING_ALGORITHM);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/player/ProfilePublicKey$Data.class */
    public static final class Data extends Record {
        private final Instant expiresAt;
        private final PublicKey key;
        private final byte[] keySignature;
        private static final int MAX_KEY_SIGNATURE_SIZE = 4096;
        public static final Codec<Data> CODEC = RecordCodecBuilder.create($$0 -> {
            return $$0.group(ExtraCodecs.INSTANT_ISO8601.fieldOf("expires_at").forGetter((v0) -> {
                return v0.expiresAt();
            }), Crypt.PUBLIC_KEY_CODEC.fieldOf("key").forGetter((v0) -> {
                return v0.key();
            }), ExtraCodecs.BASE64_STRING.fieldOf("signature_v2").forGetter((v0) -> {
                return v0.keySignature();
            })).apply($$0, Data::new);
        });

        public Data(Instant $$0, PublicKey $$1, byte[] $$2) {
            this.expiresAt = $$0;
            this.key = $$1;
            this.keySignature = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Data.class), Data.class, "expiresAt;key;keySignature", "FIELD:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;->expiresAt:Ljava/time/Instant;", "FIELD:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;->key:Ljava/security/PublicKey;", "FIELD:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;->keySignature:[B").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Data.class), Data.class, "expiresAt;key;keySignature", "FIELD:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;->expiresAt:Ljava/time/Instant;", "FIELD:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;->key:Ljava/security/PublicKey;", "FIELD:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;->keySignature:[B").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        public Instant expiresAt() {
            return this.expiresAt;
        }

        public PublicKey key() {
            return this.key;
        }

        public byte[] keySignature() {
            return this.keySignature;
        }

        public Data(FriendlyByteBuf $$0) {
            this($$0.readInstant(), $$0.readPublicKey(), $$0.readByteArray(4096));
        }

        public void write(FriendlyByteBuf $$0) {
            $$0.writeInstant(this.expiresAt);
            $$0.writePublicKey(this.key);
            $$0.writeByteArray(this.keySignature);
        }

        boolean validateSignature(SignatureValidator $$0, UUID $$1) {
            return $$0.validate(signedPayload($$1), this.keySignature);
        }

        private byte[] signedPayload(UUID $$0) {
            byte[] $$1 = this.key.getEncoded();
            byte[] $$2 = new byte[24 + $$1.length];
            ByteBuffer $$3 = ByteBuffer.wrap($$2).order(ByteOrder.BIG_ENDIAN);
            $$3.putLong($$0.getMostSignificantBits()).putLong($$0.getLeastSignificantBits()).putLong(this.expiresAt.toEpochMilli()).put($$1);
            return $$2;
        }

        public boolean hasExpired() {
            return this.expiresAt.isBefore(Instant.now());
        }

        public boolean hasExpired(Duration $$0) {
            return this.expiresAt.plus((TemporalAmount) $$0).isBefore(Instant.now());
        }

        @Override // java.lang.Record
        public boolean equals(Object $$0) {
            if (!($$0 instanceof Data)) {
                return false;
            }
            Data $$1 = (Data) $$0;
            return this.expiresAt.equals($$1.expiresAt) && this.key.equals($$1.key) && Arrays.equals(this.keySignature, $$1.keySignature);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/player/ProfilePublicKey$ValidationException.class */
    public static class ValidationException extends ThrowingComponent {
        public ValidationException(Component $$0) {
            super($$0);
        }
    }
}
