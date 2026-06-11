package net.labymod.api.volt.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.volt.asm.util.ASMContext;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.ClassWriter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/generator/ClassBytecodeComposer.class */
public class ClassBytecodeComposer {
    private final ClassWriter writer;
    private final int version;
    private final int access;
    private final String name;

    @Nullable
    private final String signature;

    @Nullable
    private final String superName;

    @Nullable
    private final String[] interfaces;

    private ClassBytecodeComposer(@NotNull ClassWriter writer, int version, int access, @NotNull String name, @Nullable String signature, @Nullable String superName, @Nullable String[] interfaces) {
        Objects.requireNonNull(writer, "writer must not be null");
        Objects.requireNonNull(name, "name must not be null");
        this.writer = writer;
        this.version = version;
        this.access = access;
        this.name = name;
        this.signature = signature;
        this.superName = superName;
        this.interfaces = interfaces;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void compose(Consumer<ClassWriter> consumer) {
        this.writer.visit(this.version, this.access, this.name, this.signature, this.superName, this.interfaces);
        consumer.accept(this.writer);
        this.writer.visitEnd();
    }

    public byte[] getClassData() {
        return this.writer.toByteArray();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/generator/ClassBytecodeComposer$Builder.class */
    public static class Builder {
        private String name;

        @Nullable
        private String signature;
        private ClassWriter writer = ASMHelper.newClassWriter();
        private int version = ASMContext.getClassVersion();
        private int access = 1;
        private String superName = "java/lang/Object";
        private final List<String> interfaces = new ArrayList();

        public Builder withWriter(ClassWriter writer) {
            this.writer = writer;
            return this;
        }

        public Builder withVersion(int version) {
            this.version = version;
            return this;
        }

        public Builder withAccess(int access) {
            this.access = access;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSignature(@Nullable String signature) {
            this.signature = signature;
            return this;
        }

        public Builder withSuperName(String superName) {
            this.superName = superName;
            return this;
        }

        public Builder addInterface(String name) {
            this.interfaces.add(name);
            return this;
        }

        public Builder addInterfaces(String... names) {
            this.interfaces.addAll(Arrays.asList(names));
            return this;
        }

        public ClassBytecodeComposer build() {
            Objects.requireNonNull(this.writer, "writer must not be null");
            Objects.requireNonNull(this.name, "name must not be null");
            Objects.requireNonNull(this.superName, "superName must not be null");
            return new ClassBytecodeComposer(this.writer, this.version, this.access, this.name, this.signature, this.superName, this.interfaces.isEmpty() ? null : (String[]) this.interfaces.toArray(new String[0]));
        }
    }
}
