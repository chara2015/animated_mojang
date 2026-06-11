package net.minecraft.network.chat;

import com.google.common.primitives.Ints;
import com.mojang.serialization.Codec;
import io.netty.handler.codec.EncoderException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.util.SignatureUpdater;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/LastSeenMessages.class */
public final class LastSeenMessages extends Record {
    private final List<MessageSignature> entries;
    public static final Codec<LastSeenMessages> CODEC = MessageSignature.CODEC.listOf().xmap(LastSeenMessages::new, (v0) -> {
        return v0.entries();
    });
    public static LastSeenMessages EMPTY = new LastSeenMessages(List.of());
    public static final int LAST_SEEN_MESSAGES_MAX_LENGTH = 20;

    public LastSeenMessages(List<MessageSignature> $$0) {
        this.entries = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LastSeenMessages.class), LastSeenMessages.class, "entries", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages;->entries:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LastSeenMessages.class), LastSeenMessages.class, "entries", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages;->entries:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LastSeenMessages.class, Object.class), LastSeenMessages.class, "entries", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages;->entries:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<MessageSignature> entries() {
        return this.entries;
    }

    public void updateSignature(SignatureUpdater.Output $$0) throws SignatureException {
        $$0.update(Ints.toByteArray(this.entries.size()));
        for (MessageSignature $$1 : this.entries) {
            $$0.update($$1.bytes());
        }
    }

    public Packed pack(MessageSignatureCache $$0) {
        return new Packed((List<MessageSignature.Packed>) this.entries.stream().map($$1 -> {
            return $$1.pack($$0);
        }).toList());
    }

    public byte computeChecksum() {
        int $$0 = 1;
        for (MessageSignature $$1 : this.entries) {
            $$0 = (31 * $$0) + $$1.checksum();
        }
        byte $$2 = (byte) $$0;
        if ($$2 == 0) {
            return (byte) 1;
        }
        return $$2;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/LastSeenMessages$Packed.class */
    public static final class Packed extends Record {
        private final List<MessageSignature.Packed> entries;
        public static final Packed EMPTY = new Packed((List<MessageSignature.Packed>) List.of());

        public Packed(List<MessageSignature.Packed> $$0) {
            this.entries = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Packed.class), Packed.class, "entries", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Packed;->entries:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Packed.class), Packed.class, "entries", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Packed;->entries:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Packed.class, Object.class), Packed.class, "entries", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Packed;->entries:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public List<MessageSignature.Packed> entries() {
            return this.entries;
        }

        public Packed(FriendlyByteBuf $$0) {
            this((List<MessageSignature.Packed>) $$0.readCollection(FriendlyByteBuf.limitValue(ArrayList::new, 20), MessageSignature.Packed::read));
        }

        public void write(FriendlyByteBuf $$0) {
            $$0.writeCollection(this.entries, MessageSignature.Packed::write);
        }

        public Optional<LastSeenMessages> unpack(MessageSignatureCache $$0) {
            List<MessageSignature> $$1 = new ArrayList<>(this.entries.size());
            for (MessageSignature.Packed $$2 : this.entries) {
                Optional<MessageSignature> $$3 = $$2.unpack($$0);
                if ($$3.isEmpty()) {
                    return Optional.empty();
                }
                $$1.add($$3.get());
            }
            return Optional.of(new LastSeenMessages($$1));
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/LastSeenMessages$Update.class */
    public static final class Update extends Record {
        private final int offset;
        private final BitSet acknowledged;
        private final byte checksum;
        public static final byte IGNORE_CHECKSUM = 0;

        public Update(int $$0, BitSet $$1, byte $$2) {
            this.offset = $$0;
            this.acknowledged = $$1;
            this.checksum = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Update.class), Update.class, "offset;acknowledged;checksum", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Update;->offset:I", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Update;->acknowledged:Ljava/util/BitSet;", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Update;->checksum:B").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Update.class), Update.class, "offset;acknowledged;checksum", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Update;->offset:I", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Update;->acknowledged:Ljava/util/BitSet;", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Update;->checksum:B").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Update.class, Object.class), Update.class, "offset;acknowledged;checksum", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Update;->offset:I", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Update;->acknowledged:Ljava/util/BitSet;", "FIELD:Lnet/minecraft/network/chat/LastSeenMessages$Update;->checksum:B").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int offset() {
            return this.offset;
        }

        public BitSet acknowledged() {
            return this.acknowledged;
        }

        public byte checksum() {
            return this.checksum;
        }

        public Update(FriendlyByteBuf $$0) {
            this($$0.readVarInt(), $$0.readFixedBitSet(20), $$0.readByte());
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: io.netty.handler.codec.EncoderException */
        public void write(FriendlyByteBuf $$0) throws EncoderException {
            $$0.writeVarInt(this.offset);
            $$0.writeFixedBitSet(this.acknowledged, 20);
            $$0.m1593writeByte((int) this.checksum);
        }

        public boolean verifyChecksum(LastSeenMessages $$0) {
            return this.checksum == 0 || this.checksum == $$0.computeChecksum();
        }
    }
}
