package net.labymod.core.main.util.command;

import java.util.Locale;
import java.util.StringJoiner;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.main.util.RenderThreadWatchdog;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/util/command/WatchdogCommand.class */
public class WatchdogCommand extends Command {
    private static final String I18N_PREFIX = "labymod.command.command.watchdog.";

    public WatchdogCommand() {
        super("watchdog", "renderwatchdog", "rwd");
        messagePrefix(Component.translatable("[Watchdog]", NamedTextColor.GOLD));
        withSubCommand(new WatchdogProfileSubCommand());
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        showHelp();
        return true;
    }

    private void showHelp() {
        displayMessage(Component.translatable("labymod.command.command.watchdog.help.title", NamedTextColor.GOLD));
        StringJoiner profiles = new StringJoiner("|", "<", ">");
        for (RenderThreadWatchdog.Profile profile : RenderThreadWatchdog.Profile.values()) {
            profiles.add(profile.name().toLowerCase(Locale.ROOT));
        }
        displayHelpLine("profile " + String.valueOf(profiles), "labymod.command.command.watchdog.help.profile");
    }

    private void displayHelpLine(String command, String descriptionKey) {
        displayMessage(Component.text("  /watchdog " + command, NamedTextColor.WHITE).append(Component.text(" - ", NamedTextColor.GRAY)).append(Component.translatable(descriptionKey, NamedTextColor.GRAY)));
    }
}
