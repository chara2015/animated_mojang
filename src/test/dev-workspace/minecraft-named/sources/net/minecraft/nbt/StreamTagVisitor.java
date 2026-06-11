package net.minecraft.nbt;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/StreamTagVisitor.class */
public interface StreamTagVisitor {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/StreamTagVisitor$EntryResult.class */
    public enum EntryResult {
        ENTER,
        SKIP,
        BREAK,
        HALT
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/StreamTagVisitor$ValueResult.class */
    public enum ValueResult {
        CONTINUE,
        BREAK,
        HALT
    }

    ValueResult visitEnd();

    ValueResult visit(String str);

    ValueResult visit(byte b);

    ValueResult visit(short s);

    ValueResult visit(int i);

    ValueResult visit(long j);

    ValueResult visit(float f);

    ValueResult visit(double d);

    ValueResult visit(byte[] bArr);

    ValueResult visit(int[] iArr);

    ValueResult visit(long[] jArr);

    ValueResult visitList(TagType<?> tagType, int i);

    EntryResult visitEntry(TagType<?> tagType);

    EntryResult visitEntry(TagType<?> tagType, String str);

    EntryResult visitElement(TagType<?> tagType, int i);

    ValueResult visitContainerEnd();

    ValueResult visitRootEntry(TagType<?> tagType);
}
