package net.minecraft.network.chat;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.ArrayDeque;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/MessageSignatureCache.class */
public class MessageSignatureCache {
    public static final int NOT_FOUND = -1;
    private static final int DEFAULT_CAPACITY = 128;
    private final MessageSignature[] entries;

    public MessageSignatureCache(int $$0) {
        this.entries = new MessageSignature[$$0];
    }

    public static MessageSignatureCache createDefault() {
        return new MessageSignatureCache(128);
    }

    public int pack(MessageSignature $$0) {
        for (int $$1 = 0; $$1 < this.entries.length; $$1++) {
            if ($$0.equals(this.entries[$$1])) {
                return $$1;
            }
        }
        return -1;
    }

    public MessageSignature unpack(int $$0) {
        return this.entries[$$0];
    }

    public void push(SignedMessageBody $$0, MessageSignature $$1) {
        List<MessageSignature> $$2 = $$0.lastSeen().entries();
        ArrayDeque<MessageSignature> $$3 = new ArrayDeque<>($$2.size() + 1);
        $$3.addAll($$2);
        if ($$1 != null) {
            $$3.add($$1);
        }
        push($$3);
    }

    @VisibleForTesting
    void push(List<MessageSignature> $$0) {
        push(new ArrayDeque<>($$0));
    }

    private void push(ArrayDeque<MessageSignature> $$0) {
        ObjectOpenHashSet objectOpenHashSet = new ObjectOpenHashSet($$0);
        for (int $$2 = 0; !$$0.isEmpty() && $$2 < this.entries.length; $$2++) {
            MessageSignature $$3 = this.entries[$$2];
            this.entries[$$2] = $$0.removeLast();
            if ($$3 != null && !objectOpenHashSet.contains($$3)) {
                $$0.addFirst($$3);
            }
        }
    }
}
