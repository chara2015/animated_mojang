package net.labymod.core.main.profiler.frame.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.ProfilerMode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/frame/command/ProfilerModeSubCommand.class */
public class ProfilerModeSubCommand extends SubCommand {
    private static final String I18N_PREFIX = "labymod.command.command.profiler.mode.";

    public ProfilerModeSubCommand() {
        super("mode", new String[0]);
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        if (arguments.length < 1) {
            ProfilerMode current = FrameProfiler.getMode();
            displayMessage(Component.translatable("labymod.command.command.profiler.mode.current", NamedTextColor.GRAY, Component.text(current.name())));
            displayMessage(Component.translatable("labymod.command.command.profiler.mode.usage", NamedTextColor.GRAY));
            displayModeDescriptions();
            return true;
        }
        String modeArg = arguments[0].toUpperCase(Locale.ROOT);
        try {
            ProfilerMode newMode = ProfilerMode.valueOf(modeArg);
            FrameProfiler.setMode(newMode);
            displayMessage(Component.translatable("labymod.command.command.profiler.mode.set", NamedTextColor.GREEN, Component.text(newMode.name())));
            if (newMode == ProfilerMode.GROUPED) {
                displayMessage(Component.translatable("labymod.command.command.profiler.mode.grouped.description", NamedTextColor.GRAY));
                return true;
            }
            displayMessage(Component.translatable("labymod.command.command.profiler.mode.individual.description", NamedTextColor.GRAY));
            displayMessage(Component.translatable("labymod.command.command.profiler.mode.individual.hint", NamedTextColor.GRAY));
            return true;
        } catch (IllegalArgumentException e) {
            displayMessage(Component.translatable("labymod.command.command.profiler.mode.unknown", NamedTextColor.RED, Component.text(arguments[0])));
            displayMessage(Component.translatable("labymod.command.command.profiler.mode.available", NamedTextColor.GRAY));
            return true;
        }
    }

    private void displayModeDescriptions() {
        displayMessage(Component.text("  grouped", NamedTextColor.WHITE).append(Component.text(" - ", NamedTextColor.GRAY)).append(Component.translatable("labymod.command.command.profiler.mode.grouped.short", NamedTextColor.GRAY)));
        displayMessage(Component.text("  individual", NamedTextColor.WHITE).append(Component.text(" - ", NamedTextColor.GRAY)).append(Component.translatable("labymod.command.command.profiler.mode.individual.short", NamedTextColor.GRAY)));
    }

    @Override // net.labymod.api.client.chat.command.Command
    public List<String> complete(String[] arguments) {
        List<String> completions = new ArrayList<>();
        if (arguments.length == 1) {
            String partial = arguments[0].toLowerCase(Locale.ROOT);
            for (ProfilerMode mode : ProfilerMode.values()) {
                String modeName = mode.name().toLowerCase(Locale.ROOT);
                if (modeName.startsWith(partial)) {
                    completions.add(modeName);
                }
            }
        }
        return completions;
    }
}
