package net.labymod.api.util.io.zip;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/ZipMerger.class */
public class ZipMerger {
    private final List<Path> sources = new ArrayList();
    private final List<Predicate<String>> excludes = new ArrayList();
    private final List<String> entries = new ArrayList();
    private final Map<String, List<String>> serviceFiles = new HashMap();
    private Path destination;

    public ZipMerger destination(Path destination) {
        this.destination = destination;
        return this;
    }

    public ZipMerger source(Path source) {
        this.sources.add(source);
        return this;
    }

    public ZipMerger source(Path... sources) {
        Collections.addAll(this.sources, sources);
        return this;
    }

    public ZipMerger exclude(Predicate<String> filter) {
        this.excludes.add(filter);
        return this;
    }

    public ZipMerger exclude(Predicate<String>... filters) {
        Collections.addAll(this.excludes, filters);
        return this;
    }

    public void merge() {
        Objects.requireNonNull(this.destination);
        try {
            ZipOutputStream jos = new ZipOutputStream(Files.newOutputStream(this.destination, new OpenOption[0]));
            try {
                for (Path source : this.sources) {
                    ZipFile zipFile = new ZipFile(source.toFile());
                    try {
                        Enumeration<? extends ZipEntry> entries = zipFile.entries();
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = entries.nextElement();
                            if (!shouldExclude(entry.getName())) {
                                writeEntry(jos, zipFile, entry);
                            }
                        }
                        zipFile.close();
                    } catch (Throwable th) {
                        try {
                            zipFile.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                for (Map.Entry<String, List<String>> entry2 : this.serviceFiles.entrySet()) {
                    String name = entry2.getKey();
                    List<String> value = entry2.getValue();
                    JarEntry newEntry = new JarEntry(name);
                    String newFileContent = mergeContent(name, value);
                    if (newFileContent != null) {
                        jos.putNextEntry(newEntry);
                        jos.write(newFileContent.getBytes(StandardCharsets.UTF_8));
                    }
                }
                jos.close();
            } finally {
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Zip could not be merged.", exception);
        }
    }

    private String mergeContent(String name, List<String> data) {
        boolean isJson = name.endsWith(".json");
        boolean isManifest = name.endsWith("MANIFEST.MF");
        JsonArray array = new JsonArray();
        List<String> mergedLines = new ArrayList<>();
        for (String content : data) {
            if (isJson) {
                JsonElement element = JsonParser.parseString(content);
                if (!element.isJsonArray()) {
                    throw new IllegalStateException("Json cannot be merged because it is not an array. " + String.valueOf(element));
                }
                for (JsonElement arrayElement : element.getAsJsonArray()) {
                    array.add(arrayElement);
                }
            } else {
                String[] lines = content.split(System.lineSeparator());
                for (String line : lines) {
                    if (!mergedLines.contains(line)) {
                        if (isManifest) {
                            if (!line.startsWith("Minecraft-") && !line.isBlank()) {
                                mergedLines.add(line);
                            }
                        } else {
                            mergedLines.add(line);
                        }
                    }
                }
            }
        }
        if (isJson) {
            if (array.size() == 0) {
                return null;
            }
            return array.toString();
        }
        return String.join(System.lineSeparator(), mergedLines);
    }

    private boolean shouldExclude(String name) {
        for (Predicate<String> exclude : this.excludes) {
            if (exclude.test(name)) {
                return true;
            }
        }
        return false;
    }

    private void writeEntry(ZipOutputStream outputStream, ZipFile file, ZipEntry entry) throws IOException {
        String name = entry.getName();
        if (this.entries.contains(name)) {
            return;
        }
        if (name.startsWith("META-INF/")) {
            this.serviceFiles.computeIfAbsent(name, l -> {
                return new ArrayList();
            }).add(new String(readInputStream(file, entry)));
            return;
        }
        byte[] data = readInputStream(file, entry);
        outputStream.putNextEntry(entry);
        outputStream.write(data);
        this.entries.add(name);
    }

    private byte[] readInputStream(ZipFile file, ZipEntry entry) throws IOException {
        InputStream stream = file.getInputStream(entry);
        try {
            byte[] allBytes = stream.readAllBytes();
            if (stream != null) {
                stream.close();
            }
            return allBytes;
        } catch (Throwable th) {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
