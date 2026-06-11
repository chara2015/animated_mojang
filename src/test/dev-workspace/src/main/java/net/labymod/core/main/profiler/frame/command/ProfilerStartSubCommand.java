package net.labymod.core.main.profiler.frame.command;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import net.labymod.api.Constants;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.stream.BinaryProfilerStreamWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/frame/command/ProfilerStartSubCommand.class */
public class ProfilerStartSubCommand extends SubCommand {
    private static final String I18N_PREFIX = "labymod.command.command.profiler.start.";
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final String STREAM_FILE_EXTENSION = ".lbfp";

    public ProfilerStartSubCommand() {
        super("start", new String[0]);
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        if (FrameProfiler.isEnabled()) {
            displayMessage(Component.translatable("labymod.command.command.profiler.start.alreadyRunning", NamedTextColor.YELLOW));
            return true;
        }
        FrameProfiler.setEnabled(true);
        startStreaming();
        displayMessage(Component.translatable("labymod.command.command.profiler.start.started", NamedTextColor.GREEN));
        displayMessage(Component.translatable("labymod.command.command.profiler.start.hint", NamedTextColor.GRAY));
        return true;
    }

    private void startStreaming() {
        if (FrameProfiler.getStreamWriter() == null) {
            FrameProfiler.setStreamWriter(new BinaryProfilerStreamWriter());
        }
        String timestamp = TIMESTAMP_FORMAT.format(LocalDateTime.now());
        String fileName = "profiler-stream_" + timestamp + ".lbfp";
        Path outputFile = Constants.Files.PROFILER_EXPORTS.resolve(fileName);
        try {
            FrameProfiler.startStreaming(outputFile);
        } catch (IOException e) {
        }
    }
}
