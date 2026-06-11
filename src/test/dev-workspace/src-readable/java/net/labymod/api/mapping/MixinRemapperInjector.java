package net.labymod.api.mapping;

import net.labymod.api.Laby;
import net.labymod.api.reference.annotation.Referenceable;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mapping/MixinRemapperInjector.class */
@Referenceable
public interface MixinRemapperInjector {
    void injectRemapper(IRemapper iRemapper);

    static MixinRemapperInjector instance() {
        return Laby.references().mixinRemapperInjector();
    }
}
