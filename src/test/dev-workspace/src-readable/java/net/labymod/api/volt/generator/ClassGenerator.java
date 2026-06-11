package net.labymod.api.volt.generator;

import java.util.Objects;
import net.labymod.api.volt.classloader.DefiningClassLoader;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/generator/ClassGenerator.class */
public abstract class ClassGenerator {
    protected final DefiningClassLoader definingClassLoader;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/generator/ClassGenerator$Context.class */
    public interface Context {
    }

    public abstract byte[] generateClass(@Nullable Context context);

    public ClassGenerator(Class<?> cls) {
        this(cls.getClassLoader());
    }

    public ClassGenerator(ClassLoader parent) {
        this(new DefiningClassLoader(parent));
    }

    private ClassGenerator(DefiningClassLoader definingClassLoader) {
        this.definingClassLoader = definingClassLoader;
    }

    @MustBeInvokedByOverriders
    public Class<?> generateClass(@NotNull String name, @Nullable Context context) {
        Objects.requireNonNull(name, "name must not be null");
        String name2 = name.replace('/', '.');
        byte[] generatedClassData = generateClass(context);
        Objects.requireNonNull(generatedClassData, "generatedClassData must not be null");
        return this.definingClassLoader.defineClass(name2, generatedClassData);
    }
}
