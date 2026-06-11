package net.labymod.api.profiler.frame.export;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/export/AbstractProfilerExporter.class */
public abstract class AbstractProfilerExporter implements ProfilerExporter {
    protected static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private final String filePrefix;
    private final String fileExtension;
    private final String displayName;
    private final String description;

    protected AbstractProfilerExporter(String filePrefix, String fileExtension, String displayName, String description) {
        this.filePrefix = filePrefix;
        this.fileExtension = fileExtension;
        this.displayName = displayName;
        this.description = description;
    }

    @Override // net.labymod.api.profiler.frame.export.ProfilerExporter
    public String getFileExtension() {
        return this.fileExtension;
    }

    @Override // net.labymod.api.profiler.frame.export.ProfilerExporter
    public String getDisplayName() {
        return this.displayName;
    }

    @Override // net.labymod.api.profiler.frame.export.ProfilerExporter
    public String getDescription() {
        return this.description;
    }

    protected Path createExportFile(Path directory) throws IOException {
        Files.createDirectories(directory, new FileAttribute[0]);
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        return directory.resolve(this.filePrefix + timestamp + "." + this.fileExtension);
    }

    protected static String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    protected static String truncate(String value, int maxLength) {
        if (value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }

    protected static String repeat(String str, int count) {
        if (count <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(str.length() * count);
        for (int i = 0; i < count; i++) {
            builder.append(str);
        }
        return builder.toString();
    }
}
