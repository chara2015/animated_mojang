package net.minecraft.network.chat;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.network.chat.LastSeenMessages;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/LastSeenMessagesValidator.class */
public class LastSeenMessagesValidator {
    private final int lastSeenCount;
    private final ObjectList<LastSeenTrackedEntry> trackedMessages = new ObjectArrayList();
    private MessageSignature lastPendingMessage;

    public LastSeenMessagesValidator(int $$0) {
        this.lastSeenCount = $$0;
        for (int $$1 = 0; $$1 < $$0; $$1++) {
            this.trackedMessages.add((Object) null);
        }
    }

    public void addPending(MessageSignature $$0) {
        if (!$$0.equals(this.lastPendingMessage)) {
            this.trackedMessages.add(new LastSeenTrackedEntry($$0, true));
            this.lastPendingMessage = $$0;
        }
    }

    public int trackedMessagesCount() {
        return this.trackedMessages.size();
    }

    public void applyOffset(int $$0) throws ValidationException {
        int $$1 = this.trackedMessages.size() - this.lastSeenCount;
        if ($$0 < 0 || $$0 > $$1) {
            throw new ValidationException("Advanced last seen window by " + $$0 + " messages, but expected at most " + $$1);
        }
        this.trackedMessages.removeElements(0, $$0);
    }

    public LastSeenMessages applyUpdate(LastSeenMessages.Update $$0) throws ValidationException {
        applyOffset($$0.offset());
        ObjectArrayList objectArrayList = new ObjectArrayList($$0.acknowledged().cardinality());
        if ($$0.acknowledged().length() > this.lastSeenCount) {
            throw new ValidationException("Last seen update contained " + $$0.acknowledged().length() + " messages, but maximum window size is " + this.lastSeenCount);
        }
        for (int $$2 = 0; $$2 < this.lastSeenCount; $$2++) {
            boolean $$3 = $$0.acknowledged().get($$2);
            LastSeenTrackedEntry $$4 = (LastSeenTrackedEntry) this.trackedMessages.get($$2);
            if ($$3) {
                if ($$4 == null) {
                    throw new ValidationException("Last seen update acknowledged unknown or previously ignored message at index " + $$2);
                }
                this.trackedMessages.set($$2, $$4.acknowledge());
                objectArrayList.add($$4.signature());
            } else {
                if ($$4 != null && !$$4.pending()) {
                    throw new ValidationException("Last seen update ignored previously acknowledged message at index " + $$2 + " and signature " + String.valueOf($$4.signature()));
                }
                this.trackedMessages.set($$2, (Object) null);
            }
        }
        LastSeenMessages $$5 = new LastSeenMessages(objectArrayList);
        if (!$$0.verifyChecksum($$5)) {
            throw new ValidationException("Checksum mismatch on last seen update: the client and server must have desynced");
        }
        return $$5;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/LastSeenMessagesValidator$ValidationException.class */
    public static class ValidationException extends Exception {
        public ValidationException(String $$0) {
            super($$0);
        }
    }
}
