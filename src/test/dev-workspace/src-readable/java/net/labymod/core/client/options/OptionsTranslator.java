package net.labymod.core.client.options;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/options/OptionsTranslator.class */
@Singleton
@Referenceable
public class OptionsTranslator {
    private static final boolean OLD_LANGUAGE_NAMES = MinecraftVersions.V1_8_9.orOlder();
    private static final Logging LOGGER = Logging.create((Class<?>) OptionsTranslator.class);
    private final Map<String, String> optionCache = new HashMap();

    public Map<String, String> getOptionCache() {
        return this.optionCache;
    }

    public void translateOptions(File file) {
        this.optionCache.clear();
        if (!file.exists()) {
            return;
        }
        AtomicBoolean hasVersion = new AtomicBoolean(false);
        AtomicBoolean modified = new AtomicBoolean(false);
        int currentVersion = Laby.labyAPI().minecraft().getDataVersion();
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            lines.forEach(line -> {
                processLine(line, currentVersion, modified, builder, hasVersion);
            });
            if (!hasVersion.get() && currentVersion != -1) {
                builder.append(buildOptionString("version", String.valueOf(currentVersion)));
                modified.set(true);
            }
            if (modified.get()) {
                Files.writeString(file.toPath(), builder.toString(), new OpenOption[0]);
            }
        } catch (IOException exception) {
            LOGGER.error("An error occurred while translating the options.", exception);
        }
    }

    private void processLine(String line, int currentVersion, AtomicBoolean modified, StringBuilder builder, AtomicBoolean hasVersion) {
        try {
            List<String> optionPair = parseOptionLine(line);
            if (optionPair.isEmpty() || optionPair.size() == 1) {
                return;
            }
            String key = optionPair.get(0);
            String value = optionPair.get(1);
            if (key.startsWith("key_")) {
                value = checkAndConvertKeyValue(currentVersion, value, modified);
            }
            if (key.equalsIgnoreCase("version")) {
                builder.append(updateVersionInformation(currentVersion, value, modified));
                hasVersion.set(true);
            } else if (key.equalsIgnoreCase("lang")) {
                builder.append(buildOptionString(key, updateLanguage(value)));
                modified.set(true);
            } else {
                builder.append(buildOptionString(key, value));
            }
            this.optionCache.put(key, value);
        } catch (Exception exception) {
            LOGGER.warn("Option line could not be processed: {}", line, exception);
        }
    }

    private String updateLanguage(String value) {
        String[] entries = value.split("_");
        if (entries.length != 2) {
            LOGGER.warn("Failed to convert language name: {}", value);
            return value;
        }
        if (OLD_LANGUAGE_NAMES) {
            return entries[0] + "_" + entries[1].toUpperCase(Locale.ROOT);
        }
        return entries[0] + "_" + entries[1].toLowerCase(Locale.ROOT);
    }

    private List<String> parseOptionLine(String line) {
        if (line == null || line.isBlank()) {
            return Collections.emptyList();
        }
        try {
            String[] split = line.split(":", 2);
            return new ArrayList(Arrays.asList(split));
        } catch (Exception exception) {
            LOGGER.warn("Option line could not be parsed. {}", line, exception);
            return Collections.emptyList();
        }
    }

    private String checkAndConvertKeyValue(int currentVersion, String value, AtomicBoolean modified) {
        Integer parsedValue = getParsedValue(value);
        try {
            value = convertVersionIfNeeded(currentVersion, value, parsedValue, modified);
        } catch (NumberFormatException e) {
        }
        return value;
    }

    private String convertVersionIfNeeded(int currentVersion, String value, Integer parsedValue, AtomicBoolean modified) {
        if (currentVersion == -1) {
            return convertNewToLegacyIfNeeded(value, parsedValue, modified);
        }
        return convertLegacyToNewIfNeeded(value, parsedValue, modified);
    }

    private String convertNewToLegacyIfNeeded(String value, Integer parsedValue, AtomicBoolean modified) {
        int keyCode;
        if (parsedValue == null && (keyCode = LegacyKeyIdConverter.getKeyId(value)) != -1) {
            value = String.valueOf(keyCode);
            modified.set(true);
        }
        return value;
    }

    private String convertLegacyToNewIfNeeded(String value, Integer parsedValue, AtomicBoolean modified) {
        if (parsedValue != null) {
            value = LegacyKeyIdConverter.getKeyName(parsedValue.intValue());
            modified.set(true);
        }
        return value;
    }

    private String updateVersionInformation(int currentVersion, String value, AtomicBoolean modified) {
        StringBuilder builder = new StringBuilder();
        if (currentVersion != -1) {
            builder.append(buildOptionString("version", String.valueOf(currentVersion)));
        }
        if (!Objects.equals(String.valueOf(currentVersion), value)) {
            modified.set(true);
        }
        return builder.toString();
    }

    private String buildOptionString(String key, String value) {
        return key + ":" + value + "\n";
    }

    private Integer getParsedValue(String value) {
        try {
            return Integer.valueOf(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/options/OptionsTranslator$OptionWriter.class */
    public static class OptionWriter extends PrintWriter {
        private final Map<String, String> values;
        private String tempKey;
        private boolean closing;

        public OptionWriter(@NotNull Writer out) {
            super(out);
            this.values = new HashMap();
        }

        @Override // java.io.PrintWriter
        public void println(String x) {
            if (this.tempKey != null) {
                this.values.put(this.tempKey, x);
                this.tempKey = null;
            } else {
                String[] split = x.split(":", 2);
                this.values.put(split[0], split[1]);
            }
        }

        @Override // java.io.PrintWriter
        public void println(boolean x) {
            println(x);
        }

        @Override // java.io.PrintWriter
        public void println(char x) {
            println(x);
        }

        @Override // java.io.PrintWriter
        public void println(int x) {
            println(x);
        }

        @Override // java.io.PrintWriter
        public void println(long x) {
            println(x);
        }

        @Override // java.io.PrintWriter
        public void println(float x) {
            println(x);
        }

        @Override // java.io.PrintWriter
        public void println(double x) {
            println(x);
        }

        @Override // java.io.PrintWriter
        public void println(Object x) {
            println(String.valueOf(x));
        }

        @Override // java.io.PrintWriter
        public void print(String s) {
            if (this.closing) {
                return;
            }
            this.tempKey = s;
        }

        @Override // java.io.PrintWriter
        public void print(char c) {
        }

        private void superPrintLine(String string) {
            synchronized (this.lock) {
                super.print(string);
                super.println();
            }
        }

        @Override // java.io.PrintWriter, java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.closing = true;
            Set<Map.Entry<String, String>> entries = this.values.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                superPrintLine(entry.getKey() + ":" + entry.getValue());
            }
            Map<String, String> optionCache = LabyMod.references().optionsTranslator().optionCache;
            for (Map.Entry<String, String> cachedEntry : optionCache.entrySet()) {
                boolean found = false;
                String key = cachedEntry.getKey();
                if (!key.equals("version")) {
                    String value = cachedEntry.getValue();
                    if (key.isEmpty()) {
                        int i = value.lastIndexOf(58);
                        if (i > 0 && i != value.length() - 1) {
                            key = value.substring(value.lastIndexOf(58, i - 1) + 1, i);
                            value = value.substring(i + 1);
                        }
                    }
                    Iterator<Map.Entry<String, String>> it = entries.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().getKey().equalsIgnoreCase(key)) {
                                found = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (!found && !key.equals("fullscreenResolution")) {
                        superPrintLine(key + ":" + value);
                    }
                }
            }
            super.close();
        }
    }
}
