package net.labymod.api.profiler.frame.stream;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.function.Consumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/stream/ProfilerStreamReader.class */
public final class ProfilerStreamReader implements AutoCloseable {
    private static final int BUFFER_SIZE = 65536;
    private final DataInputStream input;
    private final Path path;
    private boolean headerRead = false;

    public ProfilerStreamReader(Path path) throws IOException {
        this.path = path;
        this.input = new DataInputStream(new BufferedInputStream(Files.newInputStream(path, new OpenOption[0]), 65536));
    }

    public Path getPath() {
        return this.path;
    }

    public void readHeader() throws IOException {
        BinaryFrameFormat.readHeader(this.input);
        this.headerRead = true;
    }

    public StreamedFrameProfile readFrame() throws IOException {
        if (!this.headerRead) {
            readHeader();
        }
        try {
            long frameNumber = this.input.readLong();
            long durationNanos = this.input.readLong();
            boolean flaggedAsSlow = this.input.readByte() != 0;
            StreamedSystemSnapshot snapshot = null;
            if (this.input.readByte() != 0) {
                snapshot = readSystemSnapshot();
            }
            StreamedSection root = readSection();
            return new StreamedFrameProfile(frameNumber, durationNanos, flaggedAsSlow, snapshot, root);
        } catch (EOFException e) {
            return null;
        }
    }

    public void readAllFrames(Consumer<StreamedFrameProfile> consumer) throws IOException {
        while (true) {
            StreamedFrameProfile frame = readFrame();
            if (frame != null) {
                consumer.accept(frame);
            } else {
                return;
            }
        }
    }

    public long countFrames() throws IOException {
        long j = 0;
        while (true) {
            long count = j;
            if (readFrame() != null) {
                j = count + 1;
            } else {
                return count;
            }
        }
    }

    private StreamedSystemSnapshot readSystemSnapshot() throws IOException {
        return new StreamedSystemSnapshot(this.input.readLong(), this.input.readLong(), this.input.readLong(), this.input.readLong(), this.input.readLong(), this.input.readLong(), this.input.readDouble(), this.input.readInt(), this.input.readDouble(), this.input.readDouble());
    }

    private StreamedSection readSection() throws IOException {
        byte[] nameBytes = new byte[this.input.readShort()];
        this.input.readFully(nameBytes);
        String name = new String(nameBytes, StandardCharsets.UTF_8);
        long accumulatedTimeNanos = this.input.readLong();
        long selfTimeNanos = this.input.readLong();
        int callCount = this.input.readInt();
        long minCallTimeNanos = this.input.readLong();
        long maxCallTimeNanos = this.input.readLong();
        int i = this.input.readShort();
        StreamedSection[] children = new StreamedSection[i];
        for (int i2 = 0; i2 < i; i2++) {
            children[i2] = readSection();
        }
        return new StreamedSection(name, accumulatedTimeNanos, selfTimeNanos, callCount, minCallTimeNanos, maxCallTimeNanos, children);
    }

    @Override // java.lang.AutoCloseable
    public void close() throws IOException {
        this.input.close();
    }

    public static void readFile(Path path, Consumer<StreamedFrameProfile> consumer) throws IOException {
        ProfilerStreamReader reader = new ProfilerStreamReader(path);
        try {
            reader.readAllFrames(consumer);
            reader.close();
        } catch (Throwable th) {
            try {
                reader.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
