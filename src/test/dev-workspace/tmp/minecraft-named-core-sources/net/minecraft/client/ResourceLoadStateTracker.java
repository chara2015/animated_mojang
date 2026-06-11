package net.minecraft.client;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.packs.PackResources;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/ResourceLoadStateTracker.class */
public class ResourceLoadStateTracker {
    private static final Logger LOGGER = LogUtils.getLogger();
    private ReloadState reloadState;
    private int reloadCount;

    public void startReload(ReloadReason $$0, List<PackResources> $$1) {
        this.reloadCount++;
        if (this.reloadState != null && !this.reloadState.finished) {
            LOGGER.warn("Reload already ongoing, replacing");
        }
        this.reloadState = new ReloadState($$0, (List) $$1.stream().map((v0) -> {
            return v0.packId();
        }).collect(ImmutableList.toImmutableList()));
    }

    public void startRecovery(Throwable $$0) {
        if (this.reloadState == null) {
            LOGGER.warn("Trying to signal reload recovery, but nothing was started");
            this.reloadState = new ReloadState(ReloadReason.UNKNOWN, ImmutableList.of());
        }
        this.reloadState.recoveryReloadInfo = new RecoveryInfo($$0);
    }

    public void finishReload() {
        if (this.reloadState == null) {
            LOGGER.warn("Trying to finish reload, but nothing was started");
        } else {
            this.reloadState.finished = true;
        }
    }

    public void fillCrashReport(CrashReport $$0) {
        CrashReportCategory $$1 = $$0.addCategory("Last reload");
        $$1.setDetail("Reload number", Integer.valueOf(this.reloadCount));
        if (this.reloadState != null) {
            this.reloadState.fillCrashInfo($$1);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/ResourceLoadStateTracker$RecoveryInfo.class */
    static class RecoveryInfo {
        private final Throwable error;

        RecoveryInfo(Throwable $$0) {
            this.error = $$0;
        }

        public void fillCrashInfo(CrashReportCategory $$0) {
            $$0.setDetail("Recovery", "Yes");
            $$0.setDetail("Recovery reason", () -> {
                StringWriter $$02 = new StringWriter();
                this.error.printStackTrace(new PrintWriter($$02));
                return $$02.toString();
            });
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/ResourceLoadStateTracker$ReloadState.class */
    static class ReloadState {
        private final ReloadReason reloadReason;
        private final List<String> packs;
        RecoveryInfo recoveryReloadInfo;
        boolean finished;

        ReloadState(ReloadReason $$0, List<String> $$1) {
            this.reloadReason = $$0;
            this.packs = $$1;
        }

        public void fillCrashInfo(CrashReportCategory $$0) {
            $$0.setDetail("Reload reason", this.reloadReason.name);
            $$0.setDetail("Finished", this.finished ? "Yes" : "No");
            $$0.setDetail("Packs", () -> {
                return String.join(ComponentUtils.DEFAULT_SEPARATOR_TEXT, this.packs);
            });
            if (this.recoveryReloadInfo != null) {
                this.recoveryReloadInfo.fillCrashInfo($$0);
            }
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/ResourceLoadStateTracker$ReloadReason.class */
    public enum ReloadReason {
        INITIAL("initial"),
        MANUAL("manual"),
        UNKNOWN("unknown");

        final String name;

        ReloadReason(String $$0) {
            this.name = $$0;
        }
    }
}
