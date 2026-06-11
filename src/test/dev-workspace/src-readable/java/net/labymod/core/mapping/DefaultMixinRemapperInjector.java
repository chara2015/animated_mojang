package net.labymod.core.mapping;

import java.lang.reflect.Method;
import javax.inject.Singleton;
import net.labymod.api.mapping.MixinRemapperInjector;
import net.labymod.api.models.Implements;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/mapping/DefaultMixinRemapperInjector.class */
@Singleton
@Implements(MixinRemapperInjector.class)
public class DefaultMixinRemapperInjector implements MixinRemapperInjector {
    private Object remapperChain;
    private Method addRemapperMethod;

    @Override // net.labymod.api.mapping.MixinRemapperInjector
    public void injectRemapper(IRemapper remapper) {
        try {
            if (this.remapperChain == null) {
                Method getRemappersMethod = MixinEnvironment.class.getMethod("getRemappers", new Class[0]);
                getRemappersMethod.setAccessible(true);
                this.remapperChain = getRemappersMethod.invoke(MixinEnvironment.getDefaultEnvironment(), new Object[0]);
            }
            if (this.addRemapperMethod == null) {
                this.addRemapperMethod = this.remapperChain.getClass().getMethod("add", IRemapper.class);
                this.addRemapperMethod.setAccessible(true);
            }
            this.addRemapperMethod.invoke(this.remapperChain, remapper);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException("Failed to register Mixin remapper", exception);
        }
    }
}
