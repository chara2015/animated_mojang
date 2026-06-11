package net.labymod.core.flint.downloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.io.IOUtil;
import net.labymod.core.flint.FlintDefaultModifications;
import net.labymod.core.flint.marketplace.FlintModification;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/downloader/FlintDownloader.class */
@Singleton
@Referenceable
public class FlintDownloader {
    private final Set<String> scheduledForRemoval = new HashSet();
    private final Map<String, FlintDownloadTask> runningTasks = new HashMap();

    @Inject
    public FlintDownloader() {
    }

    public boolean isScheduledForRemoval(FlintModification modification) {
        return isScheduledForRemoval(modification.getNamespace());
    }

    public boolean isScheduledForRemoval(String namespace) {
        return this.scheduledForRemoval.contains(namespace);
    }

    public void scheduleForRemoval(String namespace) throws IOException {
        if (this.scheduledForRemoval.contains(namespace)) {
            this.scheduledForRemoval.remove(namespace);
            FlintDefaultModifications.instance().install(namespace);
        } else {
            this.scheduledForRemoval.add(namespace);
        }
        writeScheduleForRemoval();
    }

    public void scheduleForRemoval(FlintModification modification) throws IOException {
        scheduleForRemoval(modification.getNamespace());
    }

    public FlintDownloadTask downloadModification(FlintModification modification, List<String> skippedDependencies, Consumer<FlintDownloadTask> finished) {
        String namespace = modification.getNamespace();
        if (isScheduledForRemoval(namespace)) {
            try {
                scheduleForRemoval(namespace);
            } catch (IOException e) {
                e.printStackTrace();
            }
            finished.accept(null);
            return null;
        }
        FlintDefaultModifications.instance().install(namespace);
        FlintDownloadTask flintDownloadTask = this.runningTasks.get(namespace);
        if (flintDownloadTask != null) {
            flintDownloadTask.setFinishedCallback(finished);
            return flintDownloadTask;
        }
        FlintDownloadTask task = new FlintDownloadTask(modification, skippedDependencies, downloadTask -> {
            if (downloadTask.isFinished()) {
                this.runningTasks.remove(namespace);
            }
            if (finished != null) {
                finished.accept(downloadTask);
            }
        });
        this.runningTasks.put(namespace, task);
        task.download();
        return task;
    }

    public Optional<FlintDownloadTask> getDownloadTask(FlintModification modification) {
        return Optional.ofNullable(this.runningTasks.get(modification.getNamespace()));
    }

    public void setDownloadTask(FlintModification modification, FlintDownloadTask task) {
        this.runningTasks.put(modification.getNamespace(), task);
    }

    private void writeScheduleForRemoval() throws IOException {
        Files.write(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL, this.scheduledForRemoval, new OpenOption[0]);
        IOUtil.hideFileInWindowsFileSystem(Constants.Files.ADDONS_SCHEDULE_FOR_REMOVAL);
    }
}
