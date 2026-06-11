package net.labymod.core.main.profiler.memory;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/profiler/memory/BufferMemory.class */
public class BufferMemory {
    private final int id;
    private int type;
    private long size;

    public BufferMemory(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
