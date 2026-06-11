package net.labymod.v1_19_4.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.labymod.api.models.Implements;
import net.labymod.api.nbt.NBTFactory;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagByte;
import net.labymod.api.nbt.tags.NBTTagByteArray;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.nbt.tags.NBTTagDouble;
import net.labymod.api.nbt.tags.NBTTagFloat;
import net.labymod.api.nbt.tags.NBTTagInt;
import net.labymod.api.nbt.tags.NBTTagIntArray;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.nbt.tags.NBTTagLong;
import net.labymod.api.nbt.tags.NBTTagLongArray;
import net.labymod.api.nbt.tags.NBTTagShort;
import net.labymod.api.nbt.tags.NBTTagString;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/nbt/VersionedNBTFactory.class */
@Implements(NBTFactory.class)
public class VersionedNBTFactory implements NBTFactory {
    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagCompound compound() {
        return new re();
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public <I, T extends NBTTag<I>> NBTTagList<I, T> list() {
        return new rk<>();
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagByte create(boolean value) {
        return rc.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagByte create(byte value) {
        return rc.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagShort create(short value) {
        return rs.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagInt create(int value) {
        return rj.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagLong create(long value) {
        return rm.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagDouble create(double value) {
        return rf.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagFloat create(float value) {
        return rh.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagByteArray create(byte[] value) {
        return new rb(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagIntArray create(int[] value) {
        return new ri(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagLongArray create(long[] value) {
        return new rl(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagString create(String value) {
        return rv.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public void writeCompressed(NBTTagCompound tag, OutputStream outputStream) throws IOException {
        ro.a((re) tag, outputStream);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagCompound readCompressed(InputStream inputStream) throws IOException {
        return ro.a(inputStream);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public boolean deepEquals(NBTTag<?> a, NBTTag<?> b, boolean compareLists) {
        if (a == null || b == null) {
            return a == null && b == null;
        }
        return rq.a((rx) a, (rx) b, compareLists);
    }
}
