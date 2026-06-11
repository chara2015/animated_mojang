package net.minecraft.client.multiplayer.chat.report;

import com.mojang.authlib.minecraft.UserApiService;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/report/ReportingContext.class */
public final class ReportingContext {
    private static final int LOG_CAPACITY = 1024;
    private final AbuseReportSender sender;
    private final ReportEnvironment environment;
    private final ChatLog chatLog;
    private Report draftReport;

    public ReportingContext(AbuseReportSender $$0, ReportEnvironment $$1, ChatLog $$2) {
        this.sender = $$0;
        this.environment = $$1;
        this.chatLog = $$2;
    }

    public static ReportingContext create(ReportEnvironment $$0, UserApiService $$1) {
        ChatLog $$2 = new ChatLog(1024);
        AbuseReportSender $$3 = AbuseReportSender.create($$0, $$1);
        return new ReportingContext($$3, $$0, $$2);
    }

    public void draftReportHandled(Minecraft $$0, Screen $$1, Runnable $$2, boolean $$3) {
        if (this.draftReport != null) {
            Report $$4 = this.draftReport.copy();
            $$0.setScreen(new ConfirmScreen($$42 -> {
                setReportDraft(null);
                if ($$42) {
                    $$0.setScreen($$4.createScreen($$1, this));
                } else {
                    $$2.run();
                }
            }, Component.translatable($$3 ? "gui.abuseReport.draft.quittotitle.title" : "gui.abuseReport.draft.title"), Component.translatable($$3 ? "gui.abuseReport.draft.quittotitle.content" : "gui.abuseReport.draft.content"), Component.translatable("gui.abuseReport.draft.edit"), Component.translatable("gui.abuseReport.draft.discard")));
        } else {
            $$2.run();
        }
    }

    public AbuseReportSender sender() {
        return this.sender;
    }

    public ChatLog chatLog() {
        return this.chatLog;
    }

    public boolean matches(ReportEnvironment $$0) {
        return Objects.equals(this.environment, $$0);
    }

    public void setReportDraft(Report $$0) {
        this.draftReport = $$0;
    }

    public boolean hasDraftReport() {
        return this.draftReport != null;
    }

    public boolean hasDraftReportFor(UUID $$0) {
        return hasDraftReport() && this.draftReport.isReportedPlayer($$0);
    }
}
