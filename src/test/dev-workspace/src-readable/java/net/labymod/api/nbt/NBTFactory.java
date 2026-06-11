package net.labymod.api.nbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/nbt/NBTFactory.class */
@Referenceable
public interface NBTFactory {
    NBTTagCompound compound();

    <I, T extends NBTTag<I>> NBTTagList<I, T> list();

    NBTTagByte create(boolean z);

    NBTTagByte create(byte b);

    NBTTagShort create(short s);

    NBTTagInt create(int i);

    NBTTagLong create(long j);

    NBTTagDouble create(double d);

    NBTTagFloat create(float f);

    NBTTagByteArray create(byte[] bArr);

    NBTTagIntArray create(int[] iArr);

    NBTTagLongArray create(long[] jArr);

    NBTTagString create(String str);

    void writeCompressed(NBTTagCompound nBTTagCompound, OutputStream outputStream) throws IOException;

    NBTTagCompound readCompressed(InputStream inputStream) throws IOException;

    boolean deepEquals(NBTTag<?> nBTTag, NBTTag<?> nBTTag2, boolean z);
}
