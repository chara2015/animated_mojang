package net.labymod.api.mapping.provider.child;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/provider/child/ClassMapping.class */
public interface ClassMapping extends Mapping {
    @NotNull
    Collection<FieldMapping> getFieldMappings();

    @Nullable
    FieldMapping getFieldMapping(@Nullable String str);

    @NotNull
    Collection<MethodMapping> getMethodMappings();

    @Nullable
    MethodMapping getMethodMapping(@Nullable String str, @Nullable String str2);

    @NotNull
    String mapField(@NotNull String str);

    @NotNull
    String mapMethod(@NotNull String str, @NotNull String str2);
}
