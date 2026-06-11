package net.labymod.core.mapping.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.mapping.provider.MappingProvider;
import net.labymod.api.mapping.provider.child.ClassMapping;
import net.labymod.core.mapping.provider.child.FartClassMapping;
import net.minecraftforge.srgutils.IMappingFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/provider/FartMappingProvider.class */
public class FartMappingProvider implements MappingProvider {
    private final String sourceNamespace;
    private final String targetNamespace;
    private final IMappingFile delegate;
    private final Map<String, ClassMapping> classMappings = new HashMap();

    public FartMappingProvider(String sourceNamespace, String targetNamespace, IMappingFile delegate) {
        this.sourceNamespace = sourceNamespace;
        this.targetNamespace = targetNamespace;
        this.delegate = delegate;
        for (IMappingFile.IClass iClass : delegate.getClasses()) {
            this.classMappings.put(iClass.getOriginal(), new FartClassMapping(iClass));
        }
    }

    @Override // net.labymod.api.mapping.provider.MappingProvider
    @NotNull
    public String getSourceNamespace() {
        return this.sourceNamespace;
    }

    @Override // net.labymod.api.mapping.provider.MappingProvider
    @NotNull
    public String getTargetNamespace() {
        return this.targetNamespace;
    }

    @Override // net.labymod.api.mapping.provider.MappingProvider
    @NotNull
    public Collection<ClassMapping> getClassMappings() {
        return this.classMappings.values();
    }

    @Override // net.labymod.api.mapping.provider.MappingProvider
    @Nullable
    public ClassMapping getClassMapping(String name) {
        return this.classMappings.get(name);
    }

    @Override // net.labymod.api.mapping.provider.MappingProvider
    @NotNull
    public String mapClass(@NotNull String name) {
        return this.delegate.remapClass(name);
    }

    @Override // net.labymod.api.mapping.provider.MappingProvider
    @NotNull
    public String mapDescriptor(@NotNull String descriptor) {
        return this.delegate.remapDescriptor(descriptor);
    }

    @Override // net.labymod.api.mapping.provider.MappingProvider
    @NotNull
    public MappingProvider reverse() {
        return new FartMappingProvider(this.targetNamespace, this.sourceNamespace, this.delegate.reverse());
    }

    @Override // net.labymod.api.mapping.provider.MappingProvider
    @NotNull
    public MappingProvider chain(@NotNull MappingProvider other) {
        if (!(other instanceof FartMappingProvider)) {
            throw new IllegalArgumentException("Cannot chain with non-FartMappingProvider");
        }
        FartMappingProvider fartOther = (FartMappingProvider) other;
        return new FartMappingProvider(this.sourceNamespace, fartOther.getTargetNamespace(), this.delegate.chain(fartOther.delegate));
    }

    @Override // net.labymod.api.mapping.provider.MappingProvider
    @NotNull
    public MappingProvider sourceMappings() {
        return chain(reverse());
    }

    @Override // net.labymod.api.mapping.provider.MappingProvider
    @NotNull
    public MappingProvider targetMappings() {
        return reverse().chain(this);
    }

    public IMappingFile getDelegate() {
        return this.delegate;
    }
}
