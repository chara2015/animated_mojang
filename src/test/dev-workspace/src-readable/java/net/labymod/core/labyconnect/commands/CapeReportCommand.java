package net.labymod.core.labyconnect.commands;

import java.io.IOException;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/commands/CapeReportCommand.class */
public class CapeReportCommand extends Command {
    private static final Logging LOGGER = Logging.getLogger();
    private static final String URL_CAPE_REPORT = "https://next.api.labymod.net/cloak/report/v4";
    private static final String OUTPUT = "reporter=%s&owner=%s";
    private long nextReportTime;

    public CapeReportCommand() {
        super("capereport", "reportcape");
        this.nextReportTime = 0L;
        translationKey("labymod.command.command.capeReport");
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        if (arguments.length == 0) {
            displaySyntax();
            return true;
        }
        long currentTime = TimeUtil.getMillis();
        if (this.nextReportTime > currentTime) {
            displayTranslatable("cooldown", NamedTextColor.RED, Integer.valueOf(((int) (this.nextReportTime - currentTime)) / SubmissionOrders.DEBUG));
            return true;
        }
        String playerName = Laby.labyAPI().minecraft().getClientPlayer().getName();
        LabyExecutors.executeBackgroundTask(() -> {
            for (String argument : arguments) {
                try {
                    this.nextReportTime = currentTime + 20000;
                    report(playerName, argument);
                } catch (IOException e) {
                    LOGGER.error("Failed to report {}", argument, e);
                    displayTranslatable("error", NamedTextColor.RED, Component.text(argument, NamedTextColor.YELLOW), Component.text(e.getClass().getSimpleName() + ": " + e.getMessage(), NamedTextColor.DARK_RED));
                }
            }
        });
        return true;
    }

    private void report(String playerName, String reported) throws IOException {
        Response<String> response = Request.ofString().url(URL_CAPE_REPORT, new Object[0]).method(Request.Method.POST).write((Object) String.format(Locale.ROOT, OUTPUT, playerName, reported)).executeSync();
        if (response.hasException()) {
            throw response.exception();
        }
        String rawResponse = response.get();
        ReportResponse reportResponse = ReportResponse.fromString(rawResponse.replace("\"", ""));
        if (reportResponse == null) {
            throw new IOException("No valid response (code: " + response.getStatusCode() + ", response: " + rawResponse + ")");
        }
        switch (reportResponse) {
            case USER_NOT_FOUND:
                displayTranslatable("userNotFound", NamedTextColor.RED, Component.text(reported, NamedTextColor.YELLOW));
                return;
            case SELF_REPORT:
                displayTranslatable("selfReport", NamedTextColor.RED, new Component[0]);
                return;
            case CLOAK_NOT_FOUND:
                displayTranslatable("cloakNotFound", NamedTextColor.RED, Component.text(reported, NamedTextColor.YELLOW));
                return;
            case SUCCESS:
                displayTranslatable("success", NamedTextColor.GREEN, Component.text(reported, NamedTextColor.YELLOW));
                return;
            case LOCKED:
                displayTranslatable("locked", NamedTextColor.RED, new Component[0]);
                return;
            default:
                return;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/commands/CapeReportCommand$ReportResponse.class */
    private enum ReportResponse {
        USER_NOT_FOUND,
        SELF_REPORT,
        CLOAK_NOT_FOUND,
        SUCCESS,
        LOCKED;

        private static final ReportResponse[] VALUES = values();

        public static ReportResponse fromString(String string) {
            for (ReportResponse response : VALUES) {
                if (response.name().toLowerCase(Locale.ROOT).equalsIgnoreCase(string)) {
                    return response;
                }
            }
            return null;
        }
    }
}
