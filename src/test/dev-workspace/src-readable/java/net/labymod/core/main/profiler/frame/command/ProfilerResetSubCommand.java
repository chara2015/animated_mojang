package net.labymod.core.main.profiler.frame.command;

import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.profiler.frame.FrameProfiler;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/frame/command/ProfilerResetSubCommand.class */
public class ProfilerResetSubCommand extends SubCommand {
    private static final String I18N_PREFIX = "labymod.command.command.profiler.reset.";

    public ProfilerResetSubCommand() {
        super("reset", new String[0]);
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        FrameProfiler.reset();
        displayMessage(Component.translatable("labymod.command.command.profiler.reset.done", NamedTextColor.GREEN));
        return true;
    }
}
