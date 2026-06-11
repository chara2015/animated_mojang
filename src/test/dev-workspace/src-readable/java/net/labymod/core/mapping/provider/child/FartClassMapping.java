package net.labymod.core.mapping.provider.child;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.mapping.provider.child.ClassMapping;
import net.labymod.api.mapping.provider.child.FieldMapping;
import net.labymod.api.mapping.provider.child.MethodMapping;
import net.minecraftforge.srgutils.IMappingFile;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/provider/child/FartClassMapping.class */
public class FartClassMapping extends FartMapping<IMappingFile.IClass> implements ClassMapping {
    private final Map<String, FieldMapping> fieldMappings;
    private final Map<String, MethodMapping> methodMappings;

    public FartClassMapping(IMappingFile.IClass delegate) {
        super(delegate);
        this.fieldMappings = new HashMap();
        for (IMappingFile.IField iField : delegate.getFields()) {
            this.fieldMappings.put(iField.getOriginal(), new FartFieldMapping(iField));
        }
        this.methodMappings = new HashMap();
        for (IMappingFile.IMethod iMethod : delegate.getMethods()) {
            this.methodMappings.put(iMethod.getOriginal() + iMethod.getDescriptor(), new FartMethodMapping(iMethod));
        }
    }

    @Override // net.labymod.api.mapping.provider.child.ClassMapping
    @NotNull
    public Collection<FieldMapping> getFieldMappings() {
        return this.fieldMappings.values();
    }

    @Override // net.labymod.api.mapping.provider.child.ClassMapping
    public FieldMapping getFieldMapping(String name) {
        return this.fieldMappings.get(name);
    }

    @Override // net.labymod.api.mapping.provider.child.ClassMapping
    @NotNull
    public Collection<MethodMapping> getMethodMappings() {
        return this.methodMappings.values();
    }

    @Override // net.labymod.api.mapping.provider.child.ClassMapping
    public MethodMapping getMethodMapping(String name, String descriptor) {
        return this.methodMappings.get(name + descriptor);
    }

    @Override // net.labymod.api.mapping.provider.child.ClassMapping
    @NotNull
    public String mapField(@NotNull String name) {
        return this.delegate.remapField(name);
    }

    @Override // net.labymod.api.mapping.provider.child.ClassMapping
    @NotNull
    public String mapMethod(@NotNull String name, @NotNull String descriptor) {
        return this.delegate.remapMethod(name, descriptor);
    }
}
