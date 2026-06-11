package net.labymod.api.util.javapoet;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/javapoet/LabyJavaFile.class */
public class LabyJavaFile {
    private final JavaFile delegate;

    private LabyJavaFile(JavaFile delegate) {
        this.delegate = delegate;
    }

    public static Builder builder(TypeSpec spec, String packageName, Object... args) {
        return new Builder(spec, packageName, args);
    }

    public static LabyJavaFile of(TypeSpec spec, String packageName, Object... args) {
        return builder(spec, packageName, args).build();
    }

    public String getClassName() {
        return this.delegate.packageName + "." + this.delegate.typeSpec.name;
    }

    public void writeTo(Appendable out) throws IOException {
        this.delegate.writeTo(out);
    }

    public void writeTo(Path directory) throws IOException {
        this.delegate.writeTo(directory);
    }

    public void writeTo(Path directory, Charset charset) throws IOException {
        this.delegate.writeTo(directory, charset);
    }

    public Path writeToPath(Path directory) throws IOException {
        return this.delegate.writeToPath(directory);
    }

    public Path writeToPath(Path directory, Charset charset) throws IOException {
        return this.delegate.writeToPath(directory, charset);
    }

    public void writeTo(File directory) throws IOException {
        this.delegate.writeTo(directory);
    }

    public File writeToFile(File directory) throws IOException {
        return this.delegate.writeToFile(directory);
    }

    public void writeTo(Filer filer) throws IOException {
        this.delegate.writeTo(filer);
    }

    public JavaFileObject toJavaFileObject() {
        return this.delegate.toJavaFileObject();
    }

    public JavaFile.Builder toBuilder() {
        return this.delegate.toBuilder();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/javapoet/LabyJavaFile$Builder.class */
    public static class Builder {
        private final JavaFile.Builder builder;

        public Builder(TypeSpec spec, String packageName, Object... args) {
            this.builder = JavaFile.builder(String.format(packageName, args), spec);
        }

        public Builder addFileComment(String format, Object... args) {
            this.builder.addFileComment(format, args);
            return this;
        }

        public Builder addStaticImport(Enum<?> constant) {
            this.builder.addStaticImport(constant);
            return this;
        }

        public Builder addStaticImport(Class<?> clazz, String... names) {
            this.builder.addStaticImport(clazz, names);
            return this;
        }

        public Builder addStaticImport(ClassName className, String... names) {
            this.builder.addStaticImport(className, names);
            return this;
        }

        public Builder skipJavaLangImports(boolean skipJavaLangImports) {
            this.builder.skipJavaLangImports(skipJavaLangImports);
            return this;
        }

        public Builder indent(String indent) {
            this.builder.indent(indent);
            return this;
        }

        public LabyJavaFile build() {
            return new LabyJavaFile(this.builder.build());
        }
    }
}
