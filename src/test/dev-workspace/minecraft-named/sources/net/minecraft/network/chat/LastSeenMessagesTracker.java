package net.minecraft.network.chat;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.BitSet;
import java.util.Objects;
import net.minecraft.network.chat.LastSeenMessages;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/LastSeenMessagesTracker.class */
public class LastSeenMessagesTracker {
    private final LastSeenTrackedEntry[] trackedMessages;
    private int tail;
    private int offset;
    private MessageSignature lastTrackedMessage;

    public LastSeenMessagesTracker(int $$0) {
        this.trackedMessages = new LastSeenTrackedEntry[$$0];
    }

    public boolean addPending(MessageSignature $$0, boolean $$1) {
        if (Objects.equals($$0, this.lastTrackedMessage)) {
            return false;
        }
        this.lastTrackedMessage = $$0;
        addEntry($$1 ? new LastSeenTrackedEntry($$0, true) : null);
        return true;
    }

    private void addEntry(LastSeenTrackedEntry $$0) {
        int $$1 = this.tail;
        this.tail = ($$1 + 1) % this.trackedMessages.length;
        this.offset++;
        this.trackedMessages[$$1] = $$0;
    }

    public void ignorePending(MessageSignature $$0) {
        for (int $$1 = 0; $$1 < this.trackedMessages.length; $$1++) {
            LastSeenTrackedEntry $$2 = this.trackedMessages[$$1];
            if ($$2 != null && $$2.pending() && $$0.equals($$2.signature())) {
                this.trackedMessages[$$1] = null;
                return;
            }
        }
    }

    public int getAndClearOffset() {
        int $$0 = this.offset;
        this.offset = 0;
        return $$0;
    }

    public Update generateAndApplyUpdate() {
        int $$0 = getAndClearOffset();
        BitSet $$1 = new BitSet(this.trackedMessages.length);
        ObjectArrayList objectArrayList = new ObjectArrayList(this.trackedMessages.length);
        for (int $$3 = 0; $$3 < this.trackedMessages.length; $$3++) {
            int $$4 = (this.tail + $$3) % this.trackedMessages.length;
            LastSeenTrackedEntry $$5 = this.trackedMessages[$$4];
            if ($$5 != null) {
                $$1.set($$3, true);
                objectArrayList.add($$5.signature());
                this.trackedMessages[$$4] = $$5.acknowledge();
            }
        }
        LastSeenMessages $$6 = new LastSeenMessages(objectArrayList);
        LastSeenMessages.Update $$7 = new LastSeenMessages.Update($$0, $$1, $$6.computeChecksum());
        return new Update($$6, $$7);
    }

    public int offset() {
        return this.offset;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/LastSeenMessagesTracker$Update.class */
    public static final class Update extends Record {
        private final LastSeenMessages lastSeen;
        private final LastSeenMessages.Update update;

        public Update(LastSeenMessages $$0, LastSeenMessages.Update $$1) {
            this.lastSeen = $$0;
            this.update = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Update.class), Update.class, "lastSeen;update", "FIELD:Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update;->lastSeen:Lnet/minecraft/network/chat/LastSeenMessages;", "FIELD:Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update;->update:Lnet/minecraft/network/chat/LastSeenMessages$Update;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Update.class), Update.class, "lastSeen;update", "FIELD:Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update;->lastSeen:Lnet/minecraft/network/chat/LastSeenMessages;", "FIELD:Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update;->update:Lnet/minecraft/network/chat/LastSeenMessages$Update;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Update.class, Object.class), Update.class, "lastSeen;update", "FIELD:Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update;->lastSeen:Lnet/minecraft/network/chat/LastSeenMessages;", "FIELD:Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update;->update:Lnet/minecraft/network/chat/LastSeenMessages$Update;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public LastSeenMessages lastSeen() {
            return this.lastSeen;
        }

        public LastSeenMessages.Update update() {
            return this.update;
        }
    }
}
