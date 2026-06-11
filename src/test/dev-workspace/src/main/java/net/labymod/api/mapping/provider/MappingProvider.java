package net.labymod.api.mapping.provider;

import java.util.Collection;
import net.labymod.api.mapping.provider.child.ClassMapping;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/provider/MappingProvider.class */
public interface MappingProvider {
    @NotNull
    String getSourceNamespace();

    @NotNull
    String getTargetNamespace();

    @NotNull
    Collection<ClassMapping> getClassMappings();

    @Nullable
    ClassMapping getClassMapping(@Nullable String str);

    @NotNull
    String mapClass(@NotNull String str);

    @NotNull
    String mapDescriptor(@NotNull String str);

    @NotNull
    MappingProvider reverse();

    @NotNull
    MappingProvider chain(@NotNull MappingProvider mappingProvider);

    @NotNull
    MappingProvider sourceMappings();

    @NotNull
    MappingProvider targetMappings();
}
