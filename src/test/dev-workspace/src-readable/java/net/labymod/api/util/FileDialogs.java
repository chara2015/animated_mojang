package net.labymod.api.util;

import java.nio.file.Path;
import java.util.function.Consumer;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/FileDialogs.class */
@Referenceable
public interface FileDialogs {
    void open(@Nullable String str, @Nullable Path path, @Nullable String str2, @NotNull String[] strArr, boolean z, @NotNull Consumer<Path[]> consumer);
}
