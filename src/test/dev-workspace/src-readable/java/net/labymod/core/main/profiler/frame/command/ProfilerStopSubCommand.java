package net.labymod.core.main.profiler.frame.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.labymod.api.Constants;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.export.ZipReportExporter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/frame/command/ProfilerStopSubCommand.class */
public class ProfilerStopSubCommand extends SubCommand {
    private static final String I18N_PREFIX = "labymod.command.command.profiler.stop.";

    public ProfilerStopSubCommand() {
        super("stop", new String[0]);
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        Path outputPath;
        if (!FrameProfiler.isEnabled()) {
            displayMessage(Component.translatable("labymod.command.command.profiler.stop.notRunning", NamedTextColor.YELLOW));
            return true;
        }
        if (FrameProfiler.getStreamWriter() != null) {
            outputPath = FrameProfiler.getStreamWriter().getOutputPath();
        } else {
            outputPath = null;
        }
        Path streamFile = outputPath;
        if (FrameProfiler.isStreaming()) {
            try {
                FrameProfiler.stopStreaming();
            } catch (IOException e) {
            }
        }
        FrameProfiler.setEnabled(false);
        displayMessage(Component.translatable("labymod.command.command.profiler.stop.stopped", NamedTextColor.GREEN));
        long frameCount = FrameProfiler.getFrameCounter();
        int slowCount = FrameProfiler.getSlowFrameCount();
        if (frameCount > 0) {
            double percentage = (((double) slowCount) * 100.0d) / frameCount;
            displayMessage(Component.translatable("labymod.command.command.profiler.stop.summary", NamedTextColor.GRAY, Component.text(Long.valueOf(frameCount)), Component.text(Integer.valueOf(slowCount)), Component.text(String.format("%.2f", Double.valueOf(percentage)))));
        }
        exportToZip(streamFile);
        if (streamFile != null) {
            try {
                Files.deleteIfExists(streamFile);
                return true;
            } catch (IOException e2) {
                return true;
            }
        }
        return true;
    }

    private void exportToZip(Path streamFile) {
        displayMessage(Component.translatable("labymod.command.command.profiler.stop.exporting", NamedTextColor.GRAY));
        try {
            ZipReportExporter exporter = new ZipReportExporter();
            Path zipFile = exporter.export(Constants.Files.PROFILER_EXPORTS, streamFile);
            displayMessage(Component.translatable("labymod.command.command.profiler.stop.exported", NamedTextColor.GREEN, Component.text(zipFile.getFileName().toString(), NamedTextColor.WHITE)));
        } catch (IOException e) {
            displayMessage(Component.translatable("labymod.command.command.profiler.stop.exportFailed", NamedTextColor.RED, Component.text(e.getMessage())));
        }
    }
}
