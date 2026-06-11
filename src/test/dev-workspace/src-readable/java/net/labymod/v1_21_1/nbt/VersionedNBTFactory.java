package net.labymod.v1_21_1.nbt;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/nbt/VersionedNBTFactory.class */
@Implements(NBTFactory.class)
public class VersionedNBTFactory implements NBTFactory {
    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagCompound compound() {
        return new ub();
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public <I, T extends NBTTag<I>> NBTTagList<I, T> list() {
        return new uh<>();
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagByte create(boolean value) {
        return tz.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagByte create(byte value) {
        return tz.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagShort create(short value) {
        return ut.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagInt create(int value) {
        return ug.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagLong create(long value) {
        return uj.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagDouble create(double value) {
        return uc.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagFloat create(float value) {
        return ue.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagByteArray create(byte[] value) {
        return new ty(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagIntArray create(int[] value) {
        return new uf(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagLongArray create(long[] value) {
        return new ui(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagString create(String value) {
        return uw.a(value);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public void writeCompressed(NBTTagCompound tag, OutputStream outputStream) throws IOException {
        uo.a((ub) tag, outputStream);
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public NBTTagCompound readCompressed(InputStream inputStream) throws IOException {
        return uo.a(inputStream, uk.a());
    }

    @Override // net.labymod.api.nbt.NBTFactory
    public boolean deepEquals(NBTTag<?> a, NBTTag<?> b, boolean compareLists) {
        if (a == null || b == null) {
            return a == null && b == null;
        }
        return uq.a((uy) a, (uy) b, compareLists);
    }
}
