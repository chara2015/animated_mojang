package net.labymod.core.client.screenshot;

import java.nio.file.Path;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/screenshot/ScreenshotSection.class */
public class ScreenshotSection {
    private final long timestamp;
    private final int year;
    private boolean dirty;
    private boolean sorted;
    private final List<Screenshot> screenshots = new CopyOnWriteArrayList();
    private boolean beginningOfYear = false;

    public ScreenshotSection(long timestamp) {
        this.timestamp = timestamp;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        this.year = calendar.get(1);
    }

    public void push(Screenshot screenshot) {
        this.screenshots.add(screenshot);
        this.dirty = true;
        this.sorted = false;
    }

    public void sort() {
        if (this.sorted) {
            return;
        }
        this.sorted = true;
        this.screenshots.sort((s1, s2) -> {
            return Long.compare(s2.getMeta().getTimestamp(), s1.getMeta().getTimestamp());
        });
    }

    public void remove(Screenshot screenshot) {
        this.screenshots.remove(screenshot);
        this.dirty = true;
    }

    public int hashCode() {
        return Long.hashCode(this.timestamp);
    }

    public boolean equals(Object obj) {
        return (obj instanceof ScreenshotSection) && ((ScreenshotSection) obj).timestamp == this.timestamp;
    }

    public Screenshot getScreenshot(Path path) {
        for (Screenshot screenshot : this.screenshots) {
            if (screenshot.getPath().equals(path)) {
                return screenshot;
            }
        }
        return null;
    }

    public List<Screenshot> getScreenshots() {
        return this.screenshots;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public int getYear() {
        return this.year;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public boolean isBeginningOfYear() {
        return this.beginningOfYear;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void setBeginningOfYear(boolean beginningOfYear) {
        this.beginningOfYear = beginningOfYear;
    }

    public void setTask(Future<?> task) {
    }

    public boolean isEmpty() {
        return this.screenshots.isEmpty();
    }
}
