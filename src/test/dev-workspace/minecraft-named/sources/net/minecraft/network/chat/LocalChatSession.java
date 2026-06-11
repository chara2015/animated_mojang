package net.minecraft.network.chat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.UUID;
import net.minecraft.network.chat.SignedMessageChain;
import net.minecraft.util.Crypt;
import net.minecraft.util.Signer;
import net.minecraft.world.entity.player.ProfileKeyPair;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/LocalChatSession.class */
public final class LocalChatSession extends Record {
    private final UUID sessionId;
    private final ProfileKeyPair keyPair;

    public LocalChatSession(UUID $$0, ProfileKeyPair $$1) {
        this.sessionId = $$0;
        this.keyPair = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LocalChatSession.class), LocalChatSession.class, "sessionId;keyPair", "FIELD:Lnet/minecraft/network/chat/LocalChatSession;->sessionId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/network/chat/LocalChatSession;->keyPair:Lnet/minecraft/world/entity/player/ProfileKeyPair;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LocalChatSession.class), LocalChatSession.class, "sessionId;keyPair", "FIELD:Lnet/minecraft/network/chat/LocalChatSession;->sessionId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/network/chat/LocalChatSession;->keyPair:Lnet/minecraft/world/entity/player/ProfileKeyPair;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LocalChatSession.class, Object.class), LocalChatSession.class, "sessionId;keyPair", "FIELD:Lnet/minecraft/network/chat/LocalChatSession;->sessionId:Ljava/util/UUID;", "FIELD:Lnet/minecraft/network/chat/LocalChatSession;->keyPair:Lnet/minecraft/world/entity/player/ProfileKeyPair;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public UUID sessionId() {
        return this.sessionId;
    }

    public ProfileKeyPair keyPair() {
        return this.keyPair;
    }

    public static LocalChatSession create(ProfileKeyPair $$0) {
        return new LocalChatSession(UUID.randomUUID(), $$0);
    }

    public SignedMessageChain.Encoder createMessageEncoder(UUID $$0) {
        return new SignedMessageChain($$0, this.sessionId).encoder(Signer.from(this.keyPair.privateKey(), Crypt.SIGNING_ALGORITHM));
    }

    public RemoteChatSession asRemote() {
        return new RemoteChatSession(this.sessionId, this.keyPair.publicKey());
    }
}
