package net.minecraft.nbt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/StringTagVisitor.class */
public class StringTagVisitor implements TagVisitor {
    private static final Pattern UNQUOTED_KEY_MATCH = Pattern.compile("[A-Za-z._]+[A-Za-z0-9._+-]*");
    private final StringBuilder builder = new StringBuilder();

    public String build() {
        return this.builder.toString();
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitString(StringTag $$0) {
        this.builder.append(StringTag.quoteAndEscape($$0.value()));
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitByte(ByteTag $$0) {
        this.builder.append((int) $$0.value()).append('b');
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitShort(ShortTag $$0) {
        this.builder.append((int) $$0.value()).append('s');
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitInt(IntTag $$0) {
        this.builder.append($$0.value());
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitLong(LongTag $$0) {
        this.builder.append($$0.value()).append('L');
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitFloat(FloatTag $$0) {
        this.builder.append($$0.value()).append('f');
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitDouble(DoubleTag $$0) {
        this.builder.append($$0.value()).append('d');
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitByteArray(ByteArrayTag $$0) {
        this.builder.append("[B;");
        byte[] $$1 = $$0.getAsByteArray();
        for (int $$2 = 0; $$2 < $$1.length; $$2++) {
            if ($$2 != 0) {
                this.builder.append(',');
            }
            this.builder.append((int) $$1[$$2]).append('B');
        }
        this.builder.append(']');
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitIntArray(IntArrayTag $$0) {
        this.builder.append("[I;");
        int[] $$1 = $$0.getAsIntArray();
        for (int $$2 = 0; $$2 < $$1.length; $$2++) {
            if ($$2 != 0) {
                this.builder.append(',');
            }
            this.builder.append($$1[$$2]);
        }
        this.builder.append(']');
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitLongArray(LongArrayTag $$0) {
        this.builder.append("[L;");
        long[] $$1 = $$0.getAsLongArray();
        for (int $$2 = 0; $$2 < $$1.length; $$2++) {
            if ($$2 != 0) {
                this.builder.append(',');
            }
            this.builder.append($$1[$$2]).append('L');
        }
        this.builder.append(']');
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitList(ListTag $$0) {
        this.builder.append('[');
        for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
            if ($$1 != 0) {
                this.builder.append(',');
            }
            $$0.get($$1).accept(this);
        }
        this.builder.append(']');
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitCompound(CompoundTag $$0) {
        this.builder.append('{');
        List<Map.Entry<String, Tag>> $$1 = new ArrayList<>($$0.entrySet());
        $$1.sort(Map.Entry.comparingByKey());
        for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
            Map.Entry<String, Tag> $$3 = $$1.get($$2);
            if ($$2 != 0) {
                this.builder.append(',');
            }
            handleKeyEscape($$3.getKey());
            this.builder.append(':');
            $$3.getValue().accept(this);
        }
        this.builder.append('}');
    }

    private void handleKeyEscape(String $$0) {
        if (!$$0.equalsIgnoreCase(SnbtOperations.BUILTIN_TRUE) && !$$0.equalsIgnoreCase(SnbtOperations.BUILTIN_FALSE) && UNQUOTED_KEY_MATCH.matcher($$0).matches()) {
            this.builder.append($$0);
        } else {
            StringTag.quoteAndEscape($$0, this.builder);
        }
    }

    @Override // net.minecraft.nbt.TagVisitor
    public void visitEnd(EndTag $$0) {
        this.builder.append("END");
    }
}
