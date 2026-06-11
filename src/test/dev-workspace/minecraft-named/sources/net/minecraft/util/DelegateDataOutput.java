package net.minecraft.util;

import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.SuppressForbidden;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/DelegateDataOutput.class */
public class DelegateDataOutput implements DataOutput {
    private final DataOutput parent;

    public DelegateDataOutput(DataOutput $$0) {
        this.parent = $$0;
    }

    @Override // java.io.DataOutput
    public void write(int $$0) throws IOException {
        this.parent.write($$0);
    }

    @Override // java.io.DataOutput
    public void write(byte[] $$0) throws IOException {
        this.parent.write($$0);
    }

    @Override // java.io.DataOutput
    public void write(byte[] $$0, int $$1, int $$2) throws IOException {
        this.parent.write($$0, $$1, $$2);
    }

    @Override // java.io.DataOutput
    public void writeBoolean(boolean $$0) throws IOException {
        this.parent.writeBoolean($$0);
    }

    @Override // java.io.DataOutput
    public void writeByte(int $$0) throws IOException {
        this.parent.writeByte($$0);
    }

    @Override // java.io.DataOutput
    public void writeShort(int $$0) throws IOException {
        this.parent.writeShort($$0);
    }

    @Override // java.io.DataOutput
    public void writeChar(int $$0) throws IOException {
        this.parent.writeChar($$0);
    }

    @Override // java.io.DataOutput
    public void writeInt(int $$0) throws IOException {
        this.parent.writeInt($$0);
    }

    @Override // java.io.DataOutput
    public void writeLong(long $$0) throws IOException {
        this.parent.writeLong($$0);
    }

    @Override // java.io.DataOutput
    public void writeFloat(float $$0) throws IOException {
        this.parent.writeFloat($$0);
    }

    @Override // java.io.DataOutput
    public void writeDouble(double $$0) throws IOException {
        this.parent.writeDouble($$0);
    }

    @Override // java.io.DataOutput
    @SuppressForbidden(a = "Delegation is not use")
    public void writeBytes(String $$0) throws IOException {
        this.parent.writeBytes($$0);
    }

    @Override // java.io.DataOutput
    public void writeChars(String $$0) throws IOException {
        this.parent.writeChars($$0);
    }

    @Override // java.io.DataOutput
    public void writeUTF(String $$0) throws IOException {
        this.parent.writeUTF($$0);
    }
}
