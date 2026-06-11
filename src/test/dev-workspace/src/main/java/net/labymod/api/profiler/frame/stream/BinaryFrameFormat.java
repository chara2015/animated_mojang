package net.labymod.api.profiler.frame.stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import net.labymod.api.profiler.frame.FrameProfile;
import net.labymod.api.profiler.frame.ProfilerSection;
import net.labymod.api.profiler.frame.SystemSnapshot;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/profiler/frame/stream/BinaryFrameFormat.class */
public final class BinaryFrameFormat {
    public static final int FILE_MAGIC = 1279411792;
    public static final short FORMAT_VERSION = 1;
    private static final byte FLAG_FALSE = 0;
    private static final byte FLAG_TRUE = 1;
    private static final int SIZE_MAGIC = 4;
    private static final int SIZE_VERSION = 2;
    private static final int SIZE_RESERVED = 2;
    private static final int SIZE_HEADER = 8;
    private static final int SIZE_FRAME_NUMBER = 8;
    private static final int SIZE_DURATION_NANOS = 8;
    private static final int SIZE_FLAGGED_AS_SLOW = 1;
    private static final int SIZE_HAS_SNAPSHOT = 1;
    private static final int SIZE_FRAME_FIXED = 18;
    private static final int SIZE_NAME_LENGTH = 2;
    private static final int SIZE_ACCUMULATED_TIME = 8;
    private static final int SIZE_SELF_TIME = 8;
    private static final int SIZE_CALL_COUNT = 4;
    private static final int SIZE_MIN_CALL_TIME = 8;
    private static final int SIZE_MAX_CALL_TIME = 8;
    private static final int SIZE_CHILD_COUNT = 2;
    private static final int SIZE_SECTION_FIXED = 40;
    private static final int SIZE_HEAP_USED = 8;
    private static final int SIZE_HEAP_MAX = 8;
    private static final int SIZE_HEAP_COMMITTED = 8;
    private static final int SIZE_NON_HEAP_USED = 8;
    private static final int SIZE_SYSTEM_TOTAL_MEMORY = 8;
    private static final int SIZE_SYSTEM_FREE_MEMORY = 8;
    private static final int SIZE_SYSTEM_LOAD_AVERAGE = 8;
    private static final int SIZE_AVAILABLE_PROCESSORS = 4;
    private static final int SIZE_CPU_TEMPERATURE = 8;
    private static final int SIZE_BATTERY_LEVEL = 8;
    private static final int SIZE_SYSTEM_SNAPSHOT = 76;

    private BinaryFrameFormat() {
    }

    public static void writeHeader(DataOutputStream out) throws IOException {
        out.writeInt(FILE_MAGIC);
        out.writeShort(1);
        out.writeShort(0);
    }

    public static void readHeader(DataInputStream in) throws IOException {
        int magic = in.readInt();
        if (magic != 1279411792) {
            throw new IOException("Invalid file format: expected magic 0x" + Integer.toHexString(FILE_MAGIC) + ", got 0x" + Integer.toHexString(magic));
        }
        short version = in.readShort();
        if (version > 1) {
            throw new IOException("Unsupported format version: " + version);
        }
        in.readShort();
    }

    public static void writeFrame(DataOutputStream out, FrameProfile frame) throws IOException {
        out.writeLong(frame.getFrameNumber());
        out.writeLong(frame.getDurationNanos());
        out.writeByte(frame.isFlaggedAsSlow() ? 1 : 0);
        SystemSnapshot snapshot = frame.getSystemSnapshot();
        if (snapshot != null) {
            out.writeByte(1);
            writeSystemSnapshot(out, snapshot);
        } else {
            out.writeByte(0);
        }
        writeSection(out, frame.getRoot());
    }

    public static void writeSection(DataOutputStream out, ProfilerSection section) throws IOException {
        String name = section.getName();
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        out.writeShort(nameBytes.length);
        out.write(nameBytes);
        out.writeLong(section.getAccumulatedTimeNanos());
        out.writeLong(section.getSelfTimeNanos());
        out.writeInt(section.getCallCount());
        out.writeLong(section.getMinCallTimeNanos());
        out.writeLong(section.getMaxCallTimeNanos());
        int childCount = section.getChildCount();
        out.writeShort(childCount);
        ProfilerSection[] children = section.getChildren();
        for (int i = 0; i < childCount; i++) {
            writeSection(out, children[i]);
        }
    }

    public static void writeSystemSnapshot(DataOutputStream out, SystemSnapshot snapshot) throws IOException {
        out.writeLong(snapshot.getHeapUsedBytes());
        out.writeLong(snapshot.getHeapMaxBytes());
        out.writeLong(snapshot.getHeapCommittedBytes());
        out.writeLong(snapshot.getNonHeapUsedBytes());
        out.writeLong(snapshot.getSystemTotalMemoryBytes());
        out.writeLong(snapshot.getSystemFreeMemoryBytes());
        out.writeDouble(snapshot.getSystemLoadAverage());
        out.writeInt(snapshot.getAvailableProcessors());
        out.writeDouble(snapshot.getCpuTemperature());
        out.writeDouble(snapshot.getBatteryLevel());
    }

    public static int estimateFrameSize(FrameProfile frame) {
        int size = 18;
        if (frame.getSystemSnapshot() != null) {
            size = 18 + SIZE_SYSTEM_SNAPSHOT;
        }
        return size + estimateSectionSize(frame.getRoot());
    }

    private static int estimateSectionSize(ProfilerSection section) {
        String name = section.getName();
        int size = 40 + name.length();
        int childCount = section.getChildCount();
        ProfilerSection[] children = section.getChildren();
        for (int i = 0; i < childCount; i++) {
            size += estimateSectionSize(children[i]);
        }
        return size;
    }
}
