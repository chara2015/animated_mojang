package net.labymod.core.mapping.provider.child;

import net.labymod.api.mapping.provider.child.FieldMapping;
import net.minecraftforge.srgutils.IMappingFile;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/provider/child/FartFieldMapping.class */
public class FartFieldMapping extends FartMapping<IMappingFile.IField> implements FieldMapping {
    public FartFieldMapping(IMappingFile.IField delegate) {
        super(delegate);
    }

    @Override // net.labymod.api.mapping.provider.child.FieldMapping
    @Nullable
    public String getDescriptor() {
        return this.delegate.getDescriptor();
    }

    @Override // net.labymod.api.mapping.provider.child.FieldMapping
    @Nullable
    public String getMappedDescriptor() {
        return this.delegate.getMappedDescriptor();
    }
}
