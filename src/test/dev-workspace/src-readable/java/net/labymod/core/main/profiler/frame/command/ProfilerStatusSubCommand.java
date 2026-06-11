package net.labymod.core.main.profiler.frame.command;

import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.FrameStatistics;
import net.labymod.api.profiler.frame.ProfilerMode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/frame/command/ProfilerStatusSubCommand.class */
public class ProfilerStatusSubCommand extends SubCommand {
    private static final String I18N_PREFIX = "labymod.command.command.profiler.status.";

    public ProfilerStatusSubCommand() {
        super("status", new String[0]);
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        Component statusComponent;
        boolean enabled = FrameProfiler.isEnabled();
        if (enabled) {
            statusComponent = Component.translatable("labymod.command.command.profiler.status.running", NamedTextColor.GREEN, TextDecoration.BOLD);
        } else {
            statusComponent = Component.translatable("labymod.command.command.profiler.status.stopped", NamedTextColor.RED, TextDecoration.BOLD);
        }
        displayMessage(Component.translatable("labymod.command.command.profiler.status.label", NamedTextColor.WHITE).append(statusComponent));
        ProfilerMode mode = FrameProfiler.getMode();
        displayMessage(Component.translatable("labymod.command.command.profiler.status.mode", NamedTextColor.GRAY, Component.text(mode.name())));
        long frameCount = FrameProfiler.getFrameCounter();
        int historySize = FrameProfiler.getHistorySize();
        int slowCount = FrameProfiler.getSlowFrameCount();
        double threshold = FrameProfiler.getSlowThresholdMillis();
        displayMessage(Component.translatable("labymod.command.command.profiler.status.frames", NamedTextColor.GRAY, Component.text(Long.valueOf(frameCount)), Component.text(Integer.valueOf(historySize))));
        displayMessage(Component.translatable("labymod.command.command.profiler.status.slowFrames", NamedTextColor.GRAY, Component.text(Integer.valueOf(slowCount)), Component.text(String.format("%.1f", Double.valueOf(threshold)))));
        if (frameCount > 0) {
            FrameStatistics stats = FrameStatistics.calculate();
            double avgMs = stats.getAvgFrameTimeMs();
            double avgFps = stats.getAvgFps();
            double p99 = stats.getPercentile99Ms();
            displayMessage(Component.translatable("labymod.command.command.profiler.status.avg", NamedTextColor.GRAY, Component.text(String.format("%.2f", Double.valueOf(avgMs))), Component.text(String.format("%.0f", Double.valueOf(avgFps))), Component.text(String.format("%.2f", Double.valueOf(p99)))));
            double worstTime = FrameProfiler.getWorstFrameTimeMillis();
            if (worstTime > 0.0d) {
                TextColor worstColor = worstTime >= threshold ? NamedTextColor.RED : NamedTextColor.YELLOW;
                displayMessage(Component.translatable("labymod.command.command.profiler.status.worst", worstColor, Component.text(String.format("%.2f", Double.valueOf(worstTime)))));
                return true;
            }
            return true;
        }
        return true;
    }
}
