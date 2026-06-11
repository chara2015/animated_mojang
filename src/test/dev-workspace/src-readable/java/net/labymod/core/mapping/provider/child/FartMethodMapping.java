package net.labymod.core.mapping.provider.child;

import net.labymod.api.mapping.provider.child.MethodMapping;
import net.minecraftforge.srgutils.IMappingFile;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/provider/child/FartMethodMapping.class */
public class FartMethodMapping extends FartMapping<IMappingFile.IMethod> implements MethodMapping {
    public FartMethodMapping(IMappingFile.IMethod method) {
        super(method);
    }

    @Override // net.labymod.api.mapping.provider.child.MethodMapping
    @NotNull
    public String getDescriptor() {
        return this.delegate.getDescriptor();
    }

    @Override // net.labymod.api.mapping.provider.child.MethodMapping
    @NotNull
    public String getMappedDescriptor() {
        return this.delegate.getMappedDescriptor();
    }
}
