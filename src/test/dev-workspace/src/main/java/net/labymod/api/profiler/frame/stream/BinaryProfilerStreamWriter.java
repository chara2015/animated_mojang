package net.labymod.api.profiler.frame.stream;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import net.labymod.api.profiler.frame.FrameProfile;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/stream/BinaryProfilerStreamWriter.class */
public final class BinaryProfilerStreamWriter implements ProfilerStreamWriter {
    private static final int DEFAULT_QUEUE_SIZE = 32;
    private static final int BUFFER_SIZE = 65536;
    private final int queueSize;
    private final FrameProfile[] queue;
    private volatile int writeIndex;
    private volatile int readIndex;
    private final AtomicBoolean running;
    private final AtomicLong writtenCount;
    private final AtomicLong droppedCount;
    private Thread writerThread;
    private DataOutputStream outputStream;
    private Path outputPath;
    private volatile IOException lastError;

    public BinaryProfilerStreamWriter() {
        this(32);
    }

    public BinaryProfilerStreamWriter(int queueSize) {
        this.queueSize = queueSize;
        this.queue = new FrameProfile[queueSize];
        this.writeIndex = 0;
        this.readIndex = 0;
        this.running = new AtomicBoolean(false);
        this.writtenCount = new AtomicLong(0L);
        this.droppedCount = new AtomicLong(0L);
    }

    @Override // net.labymod.api.profiler.frame.stream.ProfilerStreamWriter
    public void start(Path outputFile) throws IOException {
        if (this.running.get()) {
            throw new IllegalStateException("Writer is already running");
        }
        Files.createDirectories(outputFile.getParent(), new FileAttribute[0]);
        this.outputPath = outputFile;
        this.outputStream = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(outputFile, new OpenOption[0]), 65536));
        BinaryFrameFormat.writeHeader(this.outputStream);
        this.writeIndex = 0;
        this.readIndex = 0;
        this.writtenCount.set(0L);
        this.droppedCount.set(0L);
        this.lastError = null;
        this.running.set(true);
        this.writerThread = new Thread(this::writerLoop, "FrameProfiler-StreamWriter");
        this.writerThread.setDaemon(true);
        this.writerThread.start();
    }

    @Override // net.labymod.api.profiler.frame.stream.ProfilerStreamWriter
    public boolean writeFrame(FrameProfile frame) {
        if (!this.running.get() || frame == null) {
            return false;
        }
        int currentWrite = this.writeIndex;
        int nextWrite = (currentWrite + 1) % this.queueSize;
        if (nextWrite == this.readIndex) {
            this.droppedCount.incrementAndGet();
            return false;
        }
        this.queue[currentWrite] = frame.copy();
        this.writeIndex = nextWrite;
        return true;
    }

    @Override // net.labymod.api.profiler.frame.stream.ProfilerStreamWriter
    public void stop() throws IOException {
        if (!this.running.compareAndSet(true, false)) {
            return;
        }
        if (this.writerThread != null) {
            try {
                this.writerThread.join(5000L);
                if (this.writerThread.isAlive()) {
                    this.writerThread.interrupt();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            this.writerThread = null;
        }
        if (this.outputStream != null) {
            try {
                this.outputStream.flush();
                this.outputStream.close();
            } finally {
                this.outputStream = null;
            }
        }
        for (int i = 0; i < this.queueSize; i++) {
            this.queue[i] = null;
        }
        if (this.lastError != null) {
            throw this.lastError;
        }
    }

    @Override // net.labymod.api.profiler.frame.stream.ProfilerStreamWriter
    public boolean isWriting() {
        return this.running.get();
    }

    @Override // net.labymod.api.profiler.frame.stream.ProfilerStreamWriter
    public long getWrittenFrameCount() {
        return this.writtenCount.get();
    }

    @Override // net.labymod.api.profiler.frame.stream.ProfilerStreamWriter
    public long getDroppedFrameCount() {
        return this.droppedCount.get();
    }

    @Override // net.labymod.api.profiler.frame.stream.ProfilerStreamWriter
    public Path getOutputPath() {
        return this.outputPath;
    }

    public IOException getLastError() {
        return this.lastError;
    }

    public int getQueueSize() {
        int write = this.writeIndex;
        int read = this.readIndex;
        if (write >= read) {
            return write - read;
        }
        return (this.queueSize - read) + write;
    }

    private void writerLoop() {
        while (true) {
            if (this.running.get() || this.readIndex != this.writeIndex) {
                int currentRead = this.readIndex;
                if (currentRead == this.writeIndex) {
                    try {
                        Thread.sleep(1L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    FrameProfile frame = this.queue[currentRead];
                    this.queue[currentRead] = null;
                    if (frame != null) {
                        try {
                            BinaryFrameFormat.writeFrame(this.outputStream, frame);
                            this.writtenCount.incrementAndGet();
                        } catch (IOException e2) {
                            this.lastError = e2;
                            this.running.set(false);
                        }
                    }
                    this.readIndex = (currentRead + 1) % this.queueSize;
                }
            }
            try {
                break;
            } catch (IOException e3) {
                if (this.lastError == null) {
                    this.lastError = e3;
                    return;
                }
                return;
            }
        }
        if (this.outputStream != null) {
            this.outputStream.flush();
        }
    }
}
