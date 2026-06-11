package net.labymod.api.profiler.frame.export;

import java.io.IOException;
import java.nio.file.Path;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/export/ProfilerExporter.class */
public interface ProfilerExporter {
    Path export(Path path) throws IOException;

    String getFileExtension();

    String getDisplayName();

    String getDescription();
}
