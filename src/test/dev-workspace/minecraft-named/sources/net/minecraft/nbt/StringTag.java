package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.TagType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/StringTag.class */
public final class StringTag extends Record implements PrimitiveTag {
    private final String value;
    private static final int SELF_SIZE_IN_BYTES = 36;
    public static final TagType<StringTag> TYPE = new TagType.VariableSize<StringTag>() { // from class: net.minecraft.nbt.StringTag.1
        @Override // net.minecraft.nbt.TagType
        public StringTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
            return StringTag.valueOf(readAccounted($$0, $$1));
        }

        @Override // net.minecraft.nbt.TagType
        public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
            return $$1.visit(readAccounted($$0, $$2));
        }

        private static String readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
            $$1.accountBytes(36L);
            String $$2 = $$0.readUTF();
            $$1.accountBytes(2L, $$2.length());
            return $$2;
        }

        @Override // net.minecraft.nbt.TagType
        public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
            StringTag.skipString($$0);
        }

        @Override // net.minecraft.nbt.TagType
        public String getName() {
            return "STRING";
        }

        @Override // net.minecraft.nbt.TagType
        public String getPrettyName() {
            return "TAG_String";
        }
    };
    private static final StringTag EMPTY = new StringTag("");
    private static final char DOUBLE_QUOTE = '\"';
    private static final char SINGLE_QUOTE = '\'';
    private static final char ESCAPE = '\\';
    private static final char NOT_SET = 0;

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StringTag.class), StringTag.class, "value", "FIELD:Lnet/minecraft/nbt/StringTag;->value:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StringTag.class, Object.class), StringTag.class, "value", "FIELD:Lnet/minecraft/nbt/StringTag;->value:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String value() {
        return this.value;
    }

    public static void skipString(DataInput $$0) throws IOException {
        $$0.skipBytes($$0.readUnsignedShort());
    }

    @Deprecated(forRemoval = true)
    public StringTag(String $$0) {
        this.value = $$0;
    }

    public static StringTag valueOf(String $$0) {
        if ($$0.isEmpty()) {
            return EMPTY;
        }
        return new StringTag($$0);
    }

    @Override // net.minecraft.nbt.Tag
    public void write(DataOutput $$0) throws IOException {
        $$0.writeUTF(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public int sizeInBytes() {
        return 36 + (2 * this.value.length());
    }

    @Override // net.minecraft.nbt.Tag
    public byte getId() {
        return (byte) 8;
    }

    @Override // net.minecraft.nbt.Tag
    public TagType<StringTag> getType() {
        return TYPE;
    }

    @Override // java.lang.Record, net.minecraft.nbt.Tag
    public String toString() {
        StringTagVisitor $$0 = new StringTagVisitor();
        $$0.visitString(this);
        return $$0.build();
    }

    @Override // net.minecraft.nbt.PrimitiveTag, net.minecraft.nbt.Tag
    public StringTag copy() {
        return this;
    }

    @Override // net.minecraft.nbt.Tag
    public Optional<String> asString() {
        return Optional.of(this.value);
    }

    @Override // net.minecraft.nbt.Tag
    public void accept(TagVisitor $$0) {
        $$0.visitString(this);
    }

    public static String quoteAndEscape(String $$0) {
        StringBuilder $$1 = new StringBuilder();
        quoteAndEscape($$0, $$1);
        return $$1.toString();
    }

    public static void quoteAndEscape(String $$0, StringBuilder $$1) {
        int $$2 = $$1.length();
        $$1.append(' ');
        char $$3 = 0;
        for (int $$4 = 0; $$4 < $$0.length(); $$4++) {
            char $$5 = $$0.charAt($$4);
            if ($$5 == '\\') {
                $$1.append("\\\\");
            } else if ($$5 == '\"' || $$5 == '\'') {
                if ($$3 == 0) {
                    $$3 = $$5 == '\"' ? '\'' : '\"';
                }
                if ($$3 == $$5) {
                    $$1.append('\\');
                }
                $$1.append($$5);
            } else {
                String $$6 = SnbtGrammar.escapeControlCharacters($$5);
                if ($$6 != null) {
                    $$1.append('\\');
                    $$1.append($$6);
                } else {
                    $$1.append($$5);
                }
            }
        }
        if ($$3 == 0) {
            $$3 = '\"';
        }
        $$1.setCharAt($$2, $$3);
        $$1.append($$3);
    }

    public static String escapeWithoutQuotes(String $$0) {
        StringBuilder $$1 = new StringBuilder();
        escapeWithoutQuotes($$0, $$1);
        return $$1.toString();
    }

    public static void escapeWithoutQuotes(String $$0, StringBuilder $$1) {
        for (int $$2 = 0; $$2 < $$0.length(); $$2++) {
            char $$3 = $$0.charAt($$2);
            switch ($$3) {
                case '\"':
                case '\'':
                case '\\':
                    $$1.append('\\');
                    $$1.append($$3);
                    break;
                default:
                    String $$4 = SnbtGrammar.escapeControlCharacters($$3);
                    if ($$4 != null) {
                        $$1.append('\\');
                        $$1.append($$4);
                    } else {
                        $$1.append($$3);
                    }
                    break;
            }
        }
    }

    @Override // net.minecraft.nbt.Tag
    public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
        return $$0.visit(this.value);
    }
}
