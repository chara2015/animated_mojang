package net.minecraft.network.protocol;

import com.mojang.logging.LogUtils;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.network.PacketListener;
import net.minecraft.network.PacketProcessor;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.server.level.ServerLevel;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/protocol/PacketUtils.class */
public class PacketUtils {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static <T extends PacketListener> void ensureRunningOnSameThread(Packet<T> $$0, T $$1, ServerLevel $$2) throws RunningOnDifferentThreadException {
        ensureRunningOnSameThread($$0, $$1, $$2.getServer().packetProcessor());
    }

    public static <T extends PacketListener> void ensureRunningOnSameThread(Packet<T> $$0, T $$1, PacketProcessor $$2) throws RunningOnDifferentThreadException {
        if (!$$2.isSameThread()) {
            $$2.scheduleIfPossible($$1, $$0);
            throw RunningOnDifferentThreadException.RUNNING_ON_DIFFERENT_THREAD;
        }
    }

    public static <T extends PacketListener> ReportedException makeReportedException(Exception $$0, Packet<T> $$1, T $$2) {
        if ($$0 instanceof ReportedException) {
            ReportedException $$3 = (ReportedException) $$0;
            fillCrashReport($$3.getReport(), $$2, $$1);
            return $$3;
        }
        CrashReport $$4 = CrashReport.forThrowable($$0, "Main thread packet handler");
        fillCrashReport($$4, $$2, $$1);
        return new ReportedException($$4);
    }

    public static <T extends PacketListener> void fillCrashReport(CrashReport $$0, T $$1, Packet<T> $$2) {
        if ($$2 != null) {
            CrashReportCategory $$3 = $$0.addCategory("Incoming Packet");
            $$3.setDetail("Type", () -> {
                return $$2.type().toString();
            });
            $$3.setDetail("Is Terminal", () -> {
                return Boolean.toString($$2.isTerminal());
            });
            $$3.setDetail("Is Skippable", () -> {
                return Boolean.toString($$2.isSkippable());
            });
        }
        $$1.fillCrashReport($$0);
    }
}
