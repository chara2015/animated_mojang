package net.minecraft.client.multiplayer.chat.report;

import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.client.multiplayer.chat.LoggedChatEvent;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.PlayerChatMessage;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/report/ChatReportContextBuilder.class */
public class ChatReportContextBuilder {
    final int leadingCount;
    private final List<Collector> activeCollectors = new ArrayList();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/report/ChatReportContextBuilder$Handler.class */
    public interface Handler {
        void accept(int i, LoggedChatMessage.Player player);
    }

    public ChatReportContextBuilder(int $$0) {
        this.leadingCount = $$0;
    }

    public void collectAllContext(ChatLog $$0, IntCollection $$1, Handler $$2) {
        IntRBTreeSet intRBTreeSet = new IntRBTreeSet($$1);
        for (int $$4 = intRBTreeSet.lastInt(); $$4 >= $$0.start(); $$4--) {
            if (isActive() || !intRBTreeSet.isEmpty()) {
                LoggedChatEvent loggedChatEventLookup = $$0.lookup($$4);
                if (loggedChatEventLookup instanceof LoggedChatMessage.Player) {
                    LoggedChatMessage.Player $$5 = (LoggedChatMessage.Player) loggedChatEventLookup;
                    boolean $$6 = acceptContext($$5.message());
                    if (intRBTreeSet.remove($$4)) {
                        trackContext($$5.message());
                        $$2.accept($$4, $$5);
                    } else if ($$6) {
                        $$2.accept($$4, $$5);
                    }
                }
            } else {
                return;
            }
        }
    }

    public void trackContext(PlayerChatMessage $$0) {
        this.activeCollectors.add(new Collector($$0));
    }

    public boolean acceptContext(PlayerChatMessage $$0) {
        boolean $$1 = false;
        Iterator<Collector> $$2 = this.activeCollectors.iterator();
        while ($$2.hasNext()) {
            Collector $$3 = $$2.next();
            if ($$3.accept($$0)) {
                $$1 = true;
                if ($$3.isComplete()) {
                    $$2.remove();
                }
            }
        }
        return $$1;
    }

    public boolean isActive() {
        return !this.activeCollectors.isEmpty();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/report/ChatReportContextBuilder$Collector.class */
    class Collector {
        private final Set<MessageSignature> lastSeenSignatures;
        private PlayerChatMessage lastChainMessage;
        private boolean collectingChain = true;
        private int count;

        Collector(PlayerChatMessage $$0) {
            this.lastSeenSignatures = new ObjectOpenHashSet($$0.signedBody().lastSeen().entries());
            this.lastChainMessage = $$0;
        }

        boolean accept(PlayerChatMessage $$0) {
            if ($$0.equals(this.lastChainMessage)) {
                return false;
            }
            boolean $$1 = this.lastSeenSignatures.remove($$0.signature());
            if (this.collectingChain && this.lastChainMessage.sender().equals($$0.sender())) {
                if (this.lastChainMessage.link().isDescendantOf($$0.link())) {
                    $$1 = true;
                    this.lastChainMessage = $$0;
                } else {
                    this.collectingChain = false;
                }
            }
            if ($$1) {
                this.count++;
            }
            return $$1;
        }

        boolean isComplete() {
            return this.count >= ChatReportContextBuilder.this.leadingCount || (!this.collectingChain && this.lastSeenSignatures.isEmpty());
        }
    }
}
