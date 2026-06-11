package net.minecraft.client.gui.screens.reporting;

import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.client.multiplayer.chat.LoggedChatEvent;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
import net.minecraft.client.multiplayer.chat.report.ChatReportContextBuilder;
import net.minecraft.client.multiplayer.chat.report.ReportingContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.SignedMessageLink;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/reporting/ChatSelectionLogFiller.class */
public class ChatSelectionLogFiller {
    private final ChatLog log;
    private final ChatReportContextBuilder contextBuilder;
    private final Predicate<LoggedChatMessage.Player> canReport;
    private SignedMessageLink previousLink = null;
    private int eventId;
    private int missedCount;
    private PlayerChatMessage lastMessage;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/reporting/ChatSelectionLogFiller$Output.class */
    public interface Output {
        void acceptMessage(int i, LoggedChatMessage.Player player);

        void acceptDivider(Component component);
    }

    public ChatSelectionLogFiller(ReportingContext $$0, Predicate<LoggedChatMessage.Player> $$1) {
        this.log = $$0.chatLog();
        this.contextBuilder = new ChatReportContextBuilder($$0.sender().reportLimits().leadingContextMessageCount());
        this.canReport = $$1;
        this.eventId = this.log.end();
    }

    public void fillNextPage(int $$0, Output $$1) {
        LoggedChatEvent $$3;
        int $$2 = 0;
        while ($$2 < $$0 && ($$3 = this.log.lookup(this.eventId)) != null) {
            int $$4 = this.eventId;
            this.eventId--;
            if ($$3 instanceof LoggedChatMessage.Player) {
                LoggedChatMessage.Player $$5 = (LoggedChatMessage.Player) $$3;
                if (!$$5.message().equals(this.lastMessage)) {
                    if (acceptMessage($$1, $$5)) {
                        if (this.missedCount > 0) {
                            $$1.acceptDivider(Component.translatable("gui.chatSelection.fold", Integer.valueOf(this.missedCount)));
                            this.missedCount = 0;
                        }
                        $$1.acceptMessage($$4, $$5);
                        $$2++;
                    } else {
                        this.missedCount++;
                    }
                    this.lastMessage = $$5.message();
                }
            }
        }
    }

    private boolean acceptMessage(Output $$0, LoggedChatMessage.Player $$1) {
        PlayerChatMessage $$2 = $$1.message();
        boolean $$3 = this.contextBuilder.acceptContext($$2);
        if (this.canReport.test($$1)) {
            this.contextBuilder.trackContext($$2);
            if (this.previousLink != null && !this.previousLink.isDescendantOf($$2.link())) {
                $$0.acceptDivider(Component.translatable("gui.chatSelection.join", $$1.profile().name()).withStyle(ChatFormatting.YELLOW));
            }
            this.previousLink = $$2.link();
            return true;
        }
        return $$3;
    }
}
