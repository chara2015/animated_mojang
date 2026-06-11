package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.nbt.StreamTagVisitor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/EndTag.class */
public final class EndTag implements Tag {
    private static final int SELF_SIZE_IN_BYTES = 8;
    public static final TagType<EndTag> TYPE = new TagType<EndTag>() { // from class: net.minecraft.nbt.EndTag.1
        @Override // net.minecraft.nbt.TagType
        public EndTag load(DataInput $$0, NbtAccounter $$1) {
            $$1.accountBytes(8L);
            return EndTag.INSTANCE;
        }

        @Override // net.minecraft.nbt.TagType
        public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) {
            $$2.accountBytes(8L);
            return $$1.visitEnd();
        }

        @Override // net.minecraft.nbt.TagType
        public void skip(DataInput $$0, int $$1, NbtAccounter $$2) {
        }

        @Override // net.minecraft.nbt.TagType
        public void skip(DataInput $$0, NbtAccounter $$1) {
        }

        @Override // net.minecraft.nbt.TagType
        public String getName() {
            return "END";
        }

        @Override // net.minecraft.nbt.TagType
        public String getPrettyName() {
            return "TAG_End";
        }
    };
    public static final EndTag INSTANCE = new EndTag();

    private EndTag() {
    }

    @Override // net.minecraft.nbt.Tag
    public void write(DataOutput $$0) throws IOException {
    }

    @Override // net.minecraft.nbt.Tag
    public int sizeInBytes() {
        return 8;
    }

    @Override // net.minecraft.nbt.Tag
    public byte getId() {
        return (byte) 0;
    }

    @Override // net.minecraft.nbt.Tag
    public TagType<EndTag> getType() {
        return TYPE;
    }

    @Override // net.minecraft.nbt.Tag
    public String toString() {
        StringTagVisitor $$0 = new StringTagVisitor();
        $$0.visitEnd(this);
        return $$0.build();
    }

    @Override // net.minecraft.nbt.Tag
    public EndTag copy() {
        return this;
    }

    @Override // net.minecraft.nbt.Tag
    public void accept(TagVisitor $$0) {
        $$0.visitEnd(this);
    }

    @Override // net.minecraft.nbt.Tag
    public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
        return $$0.visitEnd();
    }
}
