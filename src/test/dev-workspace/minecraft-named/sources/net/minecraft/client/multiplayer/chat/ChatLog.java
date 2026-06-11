package net.minecraft.client.multiplayer.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/ChatLog.class */
public class ChatLog {
    private final LoggedChatEvent[] buffer;
    private int nextId;

    public static Codec<ChatLog> codec(int $$0) {
        return Codec.list(LoggedChatEvent.CODEC).comapFlatMap($$1 -> {
            int $$2 = $$1.size();
            if ($$2 > $$0) {
                return DataResult.error(() -> {
                    return "Expected: a buffer of size less than or equal to " + $$0 + " but: " + $$2 + " is greater than " + $$0;
                });
            }
            return DataResult.success(new ChatLog($$0, $$1));
        }, (v0) -> {
            return v0.loggedChatEvents();
        });
    }

    public ChatLog(int $$0) {
        this.buffer = new LoggedChatEvent[$$0];
    }

    private ChatLog(int $$0, List<LoggedChatEvent> $$1) {
        this.buffer = (LoggedChatEvent[]) $$1.toArray($$12 -> {
            return new LoggedChatEvent[$$0];
        });
        this.nextId = $$1.size();
    }

    private List<LoggedChatEvent> loggedChatEvents() {
        List<LoggedChatEvent> $$0 = new ArrayList<>(size());
        for (int $$1 = start(); $$1 <= end(); $$1++) {
            $$0.add(lookup($$1));
        }
        return $$0;
    }

    public void push(LoggedChatEvent $$0) {
        LoggedChatEvent[] loggedChatEventArr = this.buffer;
        int i = this.nextId;
        this.nextId = i + 1;
        loggedChatEventArr[index(i)] = $$0;
    }

    public LoggedChatEvent lookup(int $$0) {
        if ($$0 < start() || $$0 > end()) {
            return null;
        }
        return this.buffer[index($$0)];
    }

    private int index(int $$0) {
        return $$0 % this.buffer.length;
    }

    public int start() {
        return Math.max(this.nextId - this.buffer.length, 0);
    }

    public int end() {
        return this.nextId - 1;
    }

    private int size() {
        return (end() - start()) + 1;
    }
}
