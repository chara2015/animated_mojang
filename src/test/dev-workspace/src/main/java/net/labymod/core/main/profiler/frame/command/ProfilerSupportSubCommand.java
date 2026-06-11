package net.labymod.core.main.profiler.frame.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import net.labymod.api.Constants;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.export.ZipReportExporter;
import net.labymod.api.profiler.frame.stream.BinaryProfilerStreamWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/frame/command/ProfilerSupportSubCommand.class */
public class ProfilerSupportSubCommand extends SubCommand {
    private static final String I18N_PREFIX = "labymod.command.command.profiler.support.";
    private static final int DEFAULT_DURATION_SECONDS = 60;
    private static final int MIN_DURATION_SECONDS = 5;
    private static final int MAX_DURATION_SECONDS = 300;
    private static final int PROGRESS_INTERVAL_SECONDS = 15;
    private static final String STREAM_FILE_EXTENSION = ".lbfp";
    private ScheduledFuture<?> currentTask;
    private ScheduledFuture<?> progressTask;
    private volatile boolean isRunning;
    private volatile long endTimeMillis;
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor(r -> {
        Thread thread = new Thread(r, "ProfilerSupport");
        thread.setDaemon(true);
        return thread;
    });

    public ProfilerSupportSubCommand() {
        super("support", new String[0]);
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        if (this.isRunning) {
            displayMessage(Component.translatable("labymod.command.command.profiler.support.alreadyRunning", NamedTextColor.YELLOW));
            displayMessage(Component.translatable("labymod.command.command.profiler.support.remaining", NamedTextColor.GRAY, Component.text(Integer.valueOf(getRemainingSeconds()))));
            return true;
        }
        int durationSeconds = DEFAULT_DURATION_SECONDS;
        if (arguments.length > 0) {
            try {
                durationSeconds = Integer.parseInt(arguments[0]);
                if (durationSeconds < 5) {
                    displayMessage(Component.translatable("labymod.command.command.profiler.support.minDuration", NamedTextColor.RED, Component.text((Object) 5)));
                    return true;
                }
                if (durationSeconds > MAX_DURATION_SECONDS) {
                    displayMessage(Component.translatable("labymod.command.command.profiler.support.maxDuration", NamedTextColor.RED, Component.text(Integer.valueOf(MAX_DURATION_SECONDS))));
                    return true;
                }
            } catch (NumberFormatException e) {
                displayMessage(Component.translatable("labymod.command.command.profiler.support.invalidDuration", NamedTextColor.RED, Component.text(arguments[0])));
                return true;
            }
        }
        startSupportProfiling(durationSeconds);
        return true;
    }

    private void startSupportProfiling(int durationSeconds) {
        FrameProfiler.reset();
        FrameProfiler.setEnabled(true);
        this.isRunning = true;
        this.endTimeMillis = System.currentTimeMillis() + (((long) durationSeconds) * 1000);
        startStreaming();
        displayMessage(Component.translatable("labymod.command.command.profiler.support.started", NamedTextColor.GREEN, TextDecoration.BOLD));
        displayMessage(Component.translatable("labymod.command.command.profiler.support.collecting", NamedTextColor.GRAY, Component.text(Integer.valueOf(durationSeconds))));
        displayMessage(Component.translatable("labymod.command.command.profiler.support.playHint", NamedTextColor.GRAY));
        this.progressTask = SCHEDULER.scheduleAtFixedRate(this::showProgress, 15L, 15L, TimeUnit.SECONDS);
        this.currentTask = SCHEDULER.schedule(this::finishSupportProfiling, durationSeconds, TimeUnit.SECONDS);
    }

    private void startStreaming() {
        if (FrameProfiler.getStreamWriter() == null) {
            FrameProfiler.setStreamWriter(new BinaryProfilerStreamWriter());
        }
        String timestamp = TIMESTAMP_FORMAT.format(LocalDateTime.now());
        String fileName = "support-stream_" + timestamp + ".lbfp";
        Path outputFile = Constants.Files.PROFILER_EXPORTS_SUPPORT.resolve(fileName);
        try {
            FrameProfiler.startStreaming(outputFile);
        } catch (IOException e) {
        }
    }

    private void showProgress() {
        if (!this.isRunning) {
            return;
        }
        long frameCount = FrameProfiler.getFrameCounter();
        int slowCount = FrameProfiler.getSlowFrameCount();
        displayMessage(Component.translatable("labymod.command.command.profiler.support.progress", NamedTextColor.AQUA, Component.text(Long.valueOf(frameCount)), Component.text(Integer.valueOf(slowCount)), Component.text(Integer.valueOf(getRemainingSeconds()))));
    }

    private int getRemainingSeconds() {
        long remaining = this.endTimeMillis - System.currentTimeMillis();
        return (int) Math.max(0L, remaining / 1000);
    }

    private void finishSupportProfiling() {
        Path outputPath;
        if (!this.isRunning) {
            return;
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
        this.isRunning = false;
        if (this.progressTask != null) {
            this.progressTask.cancel(false);
            this.progressTask = null;
        }
        displayMessage(Component.translatable("labymod.command.command.profiler.support.complete", NamedTextColor.GREEN, TextDecoration.BOLD));
        long frameCount = FrameProfiler.getFrameCounter();
        int slowCount = FrameProfiler.getSlowFrameCount();
        double worstTime = FrameProfiler.getWorstFrameTimeMillis();
        displayMessage(Component.translatable("labymod.command.command.profiler.support.captured", NamedTextColor.GRAY, Component.text(Long.valueOf(frameCount)), Component.text(Integer.valueOf(slowCount))));
        if (worstTime > 0.0d) {
            displayMessage(Component.translatable("labymod.command.command.profiler.support.worst", NamedTextColor.GRAY, Component.text(String.format("%.2f", Double.valueOf(worstTime)))));
        }
        exportToZip(streamFile);
        if (streamFile != null) {
            try {
                Files.deleteIfExists(streamFile);
            } catch (IOException e2) {
            }
        }
    }

    private void exportToZip(Path streamFile) {
        displayMessage(Component.translatable("labymod.command.command.profiler.support.exporting", NamedTextColor.GRAY));
        try {
            ZipReportExporter exporter = new ZipReportExporter();
            Path zipFile = exporter.export(Constants.Files.PROFILER_EXPORTS_SUPPORT, streamFile);
            Path parentDir = zipFile.getParent();
            displayMessage(Component.translatable("labymod.command.command.profiler.support.saved", NamedTextColor.GREEN).append(Component.text(zipFile.toAbsolutePath().toString(), NamedTextColor.AQUA, TextDecoration.UNDERLINED).clickEvent(ClickEvent.openFile(parentDir.toAbsolutePath().toString())).hoverEvent(HoverEvent.showText(Component.translatable("labymod.command.command.profiler.support.clickToOpen", new Component[0])))));
            displayMessage(Component.translatable("labymod.command.command.profiler.support.shareHint", NamedTextColor.YELLOW));
        } catch (IOException e) {
            displayMessage(Component.translatable("labymod.command.command.profiler.support.exportFailed", NamedTextColor.RED, Component.text(e.getMessage())));
        }
    }

    public void cancel() {
        if (FrameProfiler.isStreaming()) {
            try {
                FrameProfiler.stopStreaming();
            } catch (IOException e) {
            }
        }
        if (this.currentTask != null) {
            this.currentTask.cancel(false);
            this.currentTask = null;
        }
        if (this.progressTask != null) {
            this.progressTask.cancel(false);
            this.progressTask = null;
        }
        this.isRunning = false;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    @Override // net.labymod.api.client.chat.command.Command
    public List<String> complete(String[] arguments) {
        List<String> completions = new ArrayList<>();
        if (arguments.length == 1) {
            completions.add("30");
            completions.add("60");
            completions.add("120");
            completions.add("180");
        }
        return completions;
    }
}
