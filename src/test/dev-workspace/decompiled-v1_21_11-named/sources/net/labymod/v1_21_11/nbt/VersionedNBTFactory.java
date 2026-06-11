package net.labymod.v1_21_11.nbt;

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
import net.labymod.api.util.CastUtil;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/nbt/VersionedNBTFactory.class */
@Implements(NBTFactory.class)
public class VersionedNBTFactory implements NBTFactory {
    public NBTTagCompound compound() {
        return (NBTTagCompound) CastUtil.cast(new CompoundTag());
    }

    public <I, T extends NBTTag<I>> NBTTagList<I, T> list() {
        return CastUtil.cast(new ListTag());
    }

    public NBTTagByte create(boolean value) {
        return (NBTTagByte) CastUtil.cast(ByteTag.valueOf(value));
    }

    public NBTTagByte create(byte value) {
        return (NBTTagByte) CastUtil.cast(ByteTag.valueOf(value));
    }

    public NBTTagShort create(short value) {
        return (NBTTagShort) CastUtil.cast(ShortTag.valueOf(value));
    }

    public NBTTagInt create(int value) {
        return (NBTTagInt) CastUtil.cast(IntTag.valueOf(value));
    }

    public NBTTagLong create(long value) {
        return (NBTTagLong) CastUtil.cast(LongTag.valueOf(value));
    }

    public NBTTagDouble create(double value) {
        return (NBTTagDouble) CastUtil.cast(DoubleTag.valueOf(value));
    }

    public NBTTagFloat create(float value) {
        return (NBTTagFloat) CastUtil.cast(FloatTag.valueOf(value));
    }

    public NBTTagByteArray create(byte[] value) {
        return (NBTTagByteArray) CastUtil.cast(new ByteArrayTag(value));
    }

    public NBTTagIntArray create(int[] value) {
        return (NBTTagIntArray) CastUtil.cast(new IntArrayTag(value));
    }

    public NBTTagLongArray create(long[] value) {
        return (NBTTagLongArray) CastUtil.cast(new LongArrayTag(value));
    }

    public NBTTagString create(String value) {
        return (NBTTagString) CastUtil.cast(StringTag.valueOf(value));
    }

    public void writeCompressed(NBTTagCompound tag, OutputStream outputStream) throws IOException {
        NbtIo.writeCompressed((CompoundTag) CastUtil.cast(tag), outputStream);
    }

    public NBTTagCompound readCompressed(InputStream inputStream) throws IOException {
        return (NBTTagCompound) CastUtil.cast(NbtIo.readCompressed(inputStream, NbtAccounter.unlimitedHeap()));
    }

    public boolean deepEquals(NBTTag<?> a, NBTTag<?> b, boolean compareLists) {
        if (a == null || b == null) {
            return a == null && b == null;
        }
        return NbtUtils.compareNbt((Tag) CastUtil.cast(a), (Tag) CastUtil.cast(b), compareLists);
    }
}
