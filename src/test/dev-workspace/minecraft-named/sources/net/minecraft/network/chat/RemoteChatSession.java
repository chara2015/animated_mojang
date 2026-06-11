package net.minecraft.network.chat;

import com.mojang.authlib.GameProfile;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.SignedMessageChain;
import net.minecraft.network.chat.SignedMessageValidator;
import net.minecraft.util.SignatureValidator;
import net.minecraft.world.entity.player.ProfilePublicKey;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/RemoteChatSession.class */
public final class RemoteChatSession extends Record {
    private final UUID sessionId;
    private final ProfilePublicKey profilePublicKey;

    public RemoteChatSession(UUID $$0, ProfilePublicKey $$1) {
        this.sessionId = $$0;
        this.profilePublicKey = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RemoteChatSession.class), RemoteChatSession.class, "sessionId;profilePublicKey", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession;->sessionId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession;->profilePublicKey:Lnet/minecraft/world/entity/player/ProfilePublicKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RemoteChatSession.class), RemoteChatSession.class, "sessionId;profilePublicKey", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession;->sessionId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession;->profilePublicKey:Lnet/minecraft/world/entity/player/ProfilePublicKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RemoteChatSession.class, Object.class), RemoteChatSession.class, "sessionId;profilePublicKey", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession;->sessionId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession;->profilePublicKey:Lnet/minecraft/world/entity/player/ProfilePublicKey;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public UUID sessionId() {
        return this.sessionId;
    }

    public ProfilePublicKey profilePublicKey() {
        return this.profilePublicKey;
    }

    public SignedMessageValidator createMessageValidator(Duration $$0) {
        return new SignedMessageValidator.KeyBased(this.profilePublicKey.createSignatureValidator(), () -> {
            return this.profilePublicKey.data().hasExpired($$0);
        });
    }

    public SignedMessageChain.Decoder createMessageDecoder(UUID $$0) {
        return new SignedMessageChain($$0, this.sessionId).decoder(this.profilePublicKey);
    }

    public Data asData() {
        return new Data(this.sessionId, this.profilePublicKey.data());
    }

    public boolean hasExpired() {
        return this.profilePublicKey.data().hasExpired();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/RemoteChatSession$Data.class */
    public static final class Data extends Record {
        private final UUID sessionId;
        private final ProfilePublicKey.Data profilePublicKey;

        public Data(UUID $$0, ProfilePublicKey.Data $$1) {
            this.sessionId = $$0;
            this.profilePublicKey = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Data.class), Data.class, "sessionId;profilePublicKey", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession$Data;->sessionId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession$Data;->profilePublicKey:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Data.class), Data.class, "sessionId;profilePublicKey", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession$Data;->sessionId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession$Data;->profilePublicKey:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Data.class, Object.class), Data.class, "sessionId;profilePublicKey", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession$Data;->sessionId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/network/chat/RemoteChatSession$Data;->profilePublicKey:Lnet/minecraft/world/entity/player/ProfilePublicKey$Data;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public UUID sessionId() {
            return this.sessionId;
        }

        public ProfilePublicKey.Data profilePublicKey() {
            return this.profilePublicKey;
        }

        public static Data read(FriendlyByteBuf $$0) {
            return new Data($$0.readUUID(), new ProfilePublicKey.Data($$0));
        }

        public static void write(FriendlyByteBuf $$0, Data $$1) {
            $$0.writeUUID($$1.sessionId);
            $$1.profilePublicKey.write($$0);
        }

        public RemoteChatSession validate(GameProfile $$0, SignatureValidator $$1) throws ProfilePublicKey.ValidationException {
            return new RemoteChatSession(this.sessionId, ProfilePublicKey.createValidated($$1, $$0.id(), this.profilePublicKey));
        }
    }
}
