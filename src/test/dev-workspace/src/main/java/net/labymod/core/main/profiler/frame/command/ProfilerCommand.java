package net.labymod.core.main.profiler.frame.command;

import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/frame/command/ProfilerCommand.class */
public class ProfilerCommand extends Command {
    private static final String I18N_PREFIX = "labymod.command.command.profiler.";

    public ProfilerCommand() {
        super("profiler", "frameprofiler", "fps-profiler", "fpsprofiler");
        messagePrefix(Component.translatable("[Profiler]", NamedTextColor.GOLD));
        withSubCommand(new ProfilerStartSubCommand());
        withSubCommand(new ProfilerStopSubCommand());
        withSubCommand(new ProfilerStatusSubCommand());
        withSubCommand(new ProfilerResetSubCommand());
        withSubCommand(new ProfilerExportSubCommand());
        withSubCommand(new ProfilerThresholdSubCommand());
        withSubCommand(new ProfilerModeSubCommand());
        withSubCommand(new ProfilerSupportSubCommand());
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        showHelp();
        return true;
    }

    private void showHelp() {
        displayMessage(Component.translatable("labymod.command.command.profiler.help.title", NamedTextColor.GOLD));
        displayHelpLine("start", "labymod.command.command.profiler.help.start");
        displayHelpLine("stop", "labymod.command.command.profiler.help.stop");
        displayHelpLine("status", "labymod.command.command.profiler.help.status");
        displayHelpLine("reset", "labymod.command.command.profiler.help.reset");
        displayHelpLine("export [text|json|zip|slowframes|slowframesjson]", "labymod.command.command.profiler.help.export");
        displayHelpLine("threshold <ms>", "labymod.command.command.profiler.help.threshold");
        displayHelpLine("mode <grouped|individual>", "labymod.command.command.profiler.help.mode");
        displayHelpLine("support [seconds]", "labymod.command.command.profiler.help.support");
    }

    private void displayHelpLine(String command, String descriptionKey) {
        displayMessage(Component.text("  /profiler " + command, NamedTextColor.WHITE).append(Component.text(" - ", NamedTextColor.GRAY)).append(Component.translatable(descriptionKey, NamedTextColor.GRAY)));
    }
}
