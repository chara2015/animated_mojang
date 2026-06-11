package net.labymod.api.util.io.zip;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import net.labymod.api.property.Property;
import net.labymod.api.util.function.Consumers;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.io.zip.entry.Entry;
import net.labymod.api.util.io.zip.entry.NewEntry;
import net.labymod.api.util.io.zip.entry.factory.ClassEntryFactory;
import net.labymod.api.util.io.zip.entry.factory.EntryFactory;
import net.labymod.api.util.io.zip.entry.factory.ManifestEntryFactory;
import net.labymod.api.util.io.zip.entry.factory.ResourceEntryFactory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/zip/ZipTransformer.class */
public class ZipTransformer {
    private static final ResourceEntryFactory FALLBACK_ENTRY_FACTORY = new ResourceEntryFactory();
    private final Path source;
    private final Path destination;
    private final List<EntryTransformer<?>> entryTransformers;
    private final List<EntryFactory<?>> entryFactories;
    private final List<NewEntry> newEntries;
    private final Property<BiFunction<ZipEntry, byte[], Entry>> fallbackFunction;

    private ZipTransformer(Path source, Path destination) {
        ResourceEntryFactory resourceEntryFactory = FALLBACK_ENTRY_FACTORY;
        Objects.requireNonNull(resourceEntryFactory);
        this.fallbackFunction = new Property<>(resourceEntryFactory::create);
        Objects.requireNonNull(source, "source must not be null");
        Objects.requireNonNull(destination, "destination must not be null");
        this.source = source;
        this.destination = destination;
        this.entryTransformers = new ArrayList();
        this.entryFactories = new ArrayList();
        this.newEntries = new ArrayList();
    }

    public static ZipTransformer createDefault(Path source, Path destination) {
        return createDefault(source, destination, zipTransformer -> {
            zipTransformer.addEntryFactory(new ClassEntryFactory()).addEntryFactory(new ManifestEntryFactory());
        });
    }

    public static ZipTransformer createDefault(Path source, Path destination, Consumer<ZipTransformer> configureZipConsumer) {
        ZipTransformer zipTransformer = new ZipTransformer(source, destination);
        Consumers.accept(configureZipConsumer, zipTransformer);
        return zipTransformer;
    }

    public <T extends Entry<T>> ZipTransformer addTransformer(EntryTransformer<T> transformer) {
        Objects.requireNonNull(transformer, "transformer must not be null");
        this.entryTransformers.add(transformer);
        return this;
    }

    public <T extends Entry<T>> ZipTransformer addEntryFactory(EntryFactory<T> factory) {
        Objects.requireNonNull(factory, "factory must not be null");
        this.entryFactories.add(factory);
        return this;
    }

    public ZipTransformer fallbackEntry(BiFunction<ZipEntry, byte[], Entry> fallbackFunction) {
        this.fallbackFunction.set(fallbackFunction);
        return this;
    }

    public ZipTransformer addNewEntry(NewEntry entry) {
        this.newEntries.add(entry);
        return this;
    }

    public void transform() throws ZipException {
        try {
            ZipFile zipFile = new ZipFile(IOUtil.toFile(this.source));
            try {
                ZipOutputStream outputStream = new ZipOutputStream(Files.newOutputStream(this.destination, new OpenOption[0]));
                try {
                    Enumeration<? extends ZipEntry> entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry zipEntry = entries.nextElement();
                        String name = zipEntry.getName();
                        Entry entry = null;
                        InputStream stream = zipFile.getInputStream(zipEntry);
                        try {
                            byte[] data = IOUtil.readBytes(stream);
                            if (stream != null) {
                                stream.close();
                            }
                            Iterator<EntryFactory<?>> it = this.entryFactories.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                EntryFactory<?> entryFactory = it.next();
                                if (entryFactory.shouldCreate(name)) {
                                    entry = entryFactory.create(zipEntry, data);
                                    break;
                                }
                            }
                            if (entry == null) {
                                entry = this.fallbackFunction.getOrDefault().apply(zipEntry, data);
                            }
                            for (EntryTransformer<?> entryTransformer : this.entryTransformers) {
                                if (entryTransformer.canProcess(entry)) {
                                    entry = entry.process(entryTransformer);
                                    if (entry == null) {
                                        break;
                                    }
                                }
                            }
                            if (entry != null) {
                                writeEntry(outputStream, entry);
                            }
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
                    for (NewEntry newEntry : this.newEntries) {
                        writeEntry(outputStream, newEntry);
                    }
                    outputStream.close();
                    zipFile.close();
                } catch (Throwable th3) {
                    try {
                        outputStream.close();
                    } catch (Throwable th4) {
                        th3.addSuppressed(th4);
                    }
                    throw th3;
                }
            } finally {
            }
        } catch (IOException exception) {
            throw new ZipException(String.format(Locale.ROOT, "Zip could not be transformed (Source: %s | Destination: %s)", this.source.toAbsolutePath(), this.destination.toAbsolutePath()), exception);
        }
    }

    <T extends Entry<T>> void writeEntry(ZipOutputStream outputStream, T entry) throws IOException {
        ZipEntry zipEntry = new ZipEntry(entry.getName());
        zipEntry.setTime(entry.getTime());
        outputStream.putNextEntry(zipEntry);
        outputStream.write(entry.getData());
    }
}
