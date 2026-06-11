package net.labymod.core.main.profiler.frame.command;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.labymod.api.Constants;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.export.ProfilerExporters;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/frame/command/ProfilerExportSubCommand.class */
public class ProfilerExportSubCommand extends SubCommand {
    private static final String I18N_PREFIX = "labymod.command.command.profiler.export.";
    private static final String[] EXPORT_TYPES = {"text", "json", "zip", "slowframes", "slowframesjson", "flamegraph"};

    public ProfilerExportSubCommand() {
        super("export", new String[0]);
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        Path exportedFile;
        String exportType = "text";
        if (arguments.length > 0) {
            exportType = arguments[0].toLowerCase(Locale.ROOT);
        }
        if (FrameProfiler.getHistorySize() == 0) {
            displayMessage(Component.translatable("labymod.command.command.profiler.export.noData", NamedTextColor.RED));
            return true;
        }
        Path exportDir = Constants.Files.PROFILER_EXPORTS;
        try {
            if (exportType.equals("json")) {
                exportedFile = ProfilerExporters.json().export(exportDir);
            } else if (exportType.equals("zip")) {
                exportedFile = ProfilerExporters.zip().export(exportDir);
            } else if (exportType.equals("slowframes")) {
                exportedFile = ProfilerExporters.slowFrames().export(exportDir);
            } else if (exportType.equals("slowframesjson") || exportType.equals("flamegraph")) {
                exportedFile = ProfilerExporters.slowFramesJson().export(exportDir);
            } else {
                exportedFile = ProfilerExporters.text().export(exportDir);
            }
            displayMessage(Component.translatable("labymod.command.command.profiler.export.success", NamedTextColor.GREEN).append(Component.text(exportedFile.getFileName().toString(), NamedTextColor.WHITE)));
            displayMessage(Component.translatable("labymod.command.command.profiler.export.location", NamedTextColor.GRAY, Component.text(exportDir.toAbsolutePath().toString())));
            return true;
        } catch (IOException e) {
            displayMessage(Component.translatable("labymod.command.command.profiler.export.failed", NamedTextColor.RED, Component.text(e.getMessage())));
            return true;
        }
    }

    @Override // net.labymod.api.client.chat.command.Command
    public List<String> complete(String[] arguments) {
        List<String> completions = new ArrayList<>();
        if (arguments.length == 1) {
            String partial = arguments[0].toLowerCase(Locale.ROOT);
            for (String type : EXPORT_TYPES) {
                if (type.startsWith(partial)) {
                    completions.add(type);
                }
            }
        }
        return completions;
    }
}
