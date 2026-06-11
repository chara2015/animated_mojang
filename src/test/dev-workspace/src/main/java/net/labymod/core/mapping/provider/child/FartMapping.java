package net.labymod.core.mapping.provider.child;

import net.labymod.api.mapping.provider.child.Mapping;
import net.minecraftforge.srgutils.IMappingFile;
import net.minecraftforge.srgutils.IMappingFile.INode;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/provider/child/FartMapping.class */
public class FartMapping<T extends IMappingFile.INode> implements Mapping {
    protected final T delegate;

    public FartMapping(T delegate) {
        this.delegate = delegate;
    }

    @Override // net.labymod.api.mapping.provider.child.Mapping
    @NotNull
    public String getName() {
        return this.delegate.getOriginal();
    }

    @Override // net.labymod.api.mapping.provider.child.Mapping
    @NotNull
    public String getMappedName() {
        return this.delegate.getMapped();
    }
}
