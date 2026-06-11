package net.labymod.core.client.screenshot.meta;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.labynet.insight.controller.InsightWriter;
import net.labymod.core.labynet.insight.model.ScreenshotInsight;
import net.labymod.core.labynet.insight.util.ImageCodec;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/screenshot/meta/ScreenshotMeta.class */
public class ScreenshotMeta {
    private static final Logging LOGGER = Logging.getLogger();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");
    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}_\\d{2}\\.\\d{2}\\.\\d{2})");
    private final String identifier;
    private final long timestamp;
    private long monthTimestamp;
    private final Map<String, String> attributes;

    /* JADX WARN: Type inference failed for: r0v29, types: [java.time.ZonedDateTime] */
    public ScreenshotMeta(Path path) throws IOException {
        long timestamp = -1;
        try {
            String fileName = path.getFileName().toString();
            Matcher matcher = DATE_PATTERN.matcher(fileName);
            if (matcher.find()) {
                String group = matcher.group(1);
                LocalDateTime dateTime = LocalDateTime.parse(group, DATE_FORMAT);
                timestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            }
        } catch (Exception e) {
            LOGGER.warn("Could not parse timestamp from screenshot file name: {}", path.getFileName(), e);
        }
        if (timestamp <= 0 || timestamp > System.currentTimeMillis() + 3600000) {
            BasicFileAttributes attributes = Files.readAttributes(path, (Class<BasicFileAttributes>) BasicFileAttributes.class, new LinkOption[0]);
            timestamp = attributes.lastModifiedTime().toMillis();
        }
        this.timestamp = timestamp;
        this.identifier = identifierOfPath(path);
        ImageCodec codec = new ImageCodec(path);
        this.attributes = codec.map();
        initialize();
    }

    public ScreenshotMeta(String identifier, long timestamp) throws IOException {
        this.identifier = identifier;
        this.timestamp = timestamp;
        this.attributes = new HashMap();
        initialize();
    }

    private void initialize() {
        Instant instance = Instant.ofEpochMilli(this.timestamp);
        ZoneId zone = ZoneId.systemDefault();
        LocalDate date = instance.atZone(zone).toLocalDate();
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        Instant firstDayOfMonthInstant = firstDayOfMonth.atStartOfDay(zone).toInstant();
        this.monthTimestamp = firstDayOfMonthInstant.toEpochMilli();
    }

    public void set(String key, String value) {
        this.attributes.put(key, value);
    }

    public String get(String key) {
        return this.attributes.get(key);
    }

    public boolean has(String key) {
        return this.attributes.containsKey(key);
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public long getMonthTimestamp() {
        return this.monthTimestamp;
    }

    public Map<String, String> getAttributes() {
        return this.attributes;
    }

    public static String identifierOfPath(Path path) {
        return path.getFileName().toString().replace(".png", "");
    }

    public boolean hasInsight() {
        return has(InsightWriter.KEY) || has(InsightWriter.LEGACY_KEY);
    }

    public ScreenshotInsight getInsight() {
        String str;
        try {
            if (has(InsightWriter.KEY)) {
                str = get(InsightWriter.KEY);
            } else {
                str = get(InsightWriter.LEGACY_KEY);
            }
            String json = str;
            if (json == null) {
                return null;
            }
            return new ScreenshotInsight((JsonObject) GsonUtil.DEFAULT_GSON.fromJson(json, JsonObject.class));
        } catch (Exception e) {
            LOGGER.warn("Could not parse screenshot insight: {}", getIdentifier(), e);
            return null;
        }
    }
}
