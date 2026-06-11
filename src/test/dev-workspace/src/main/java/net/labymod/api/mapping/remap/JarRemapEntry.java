package net.labymod.api.mapping.remap;

import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/remap/JarRemapEntry.class */
public class JarRemapEntry {
    private final Path inputPath;
    private final Path outputPath;

    public JarRemapEntry(@NotNull Path inputPath, @NotNull Path outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    @NotNull
    public Path getInputPath() {
        return this.inputPath;
    }

    @NotNull
    public Path getOutputPath() {
        return this.outputPath;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        JarRemapEntry that = (JarRemapEntry) object;
        if (!this.inputPath.equals(that.inputPath)) {
            return false;
        }
        return this.outputPath.equals(that.outputPath);
    }

    public int hashCode() {
        int result = this.inputPath.hashCode();
        return (31 * result) + this.outputPath.hashCode();
    }
}
