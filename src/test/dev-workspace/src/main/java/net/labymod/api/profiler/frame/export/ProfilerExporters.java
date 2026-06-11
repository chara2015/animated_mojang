package net.labymod.api.profiler.frame.export;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/export/ProfilerExporters.class */
public final class ProfilerExporters {
    private static final List<ProfilerExporter> EXPORTERS = new ArrayList();
    private static final ZipReportExporter ZIP_EXPORTER = new ZipReportExporter();
    private static final TextReportExporter TEXT_EXPORTER = (TextReportExporter) register(new TextReportExporter());
    private static final JsonReportExporter JSON_EXPORTER = (JsonReportExporter) register(new JsonReportExporter());
    private static final SlowFramesExporter SLOW_FRAMES_EXPORTER = (SlowFramesExporter) register(new SlowFramesExporter());
    private static final SlowFramesJsonExporter SLOW_FRAMES_JSON_EXPORTER = (SlowFramesJsonExporter) register(new SlowFramesJsonExporter());

    private ProfilerExporters() {
    }

    public static ZipReportExporter zip() {
        return ZIP_EXPORTER;
    }

    public static TextReportExporter text() {
        return TEXT_EXPORTER;
    }

    public static JsonReportExporter json() {
        return JSON_EXPORTER;
    }

    public static SlowFramesExporter slowFrames() {
        return SLOW_FRAMES_EXPORTER;
    }

    public static SlowFramesJsonExporter slowFramesJson() {
        return SLOW_FRAMES_JSON_EXPORTER;
    }

    public static List<ProfilerExporter> getAll() {
        return Collections.unmodifiableList(EXPORTERS);
    }

    public static <T extends ProfilerExporter> T register(T exporter) {
        EXPORTERS.add(exporter);
        return exporter;
    }
}
