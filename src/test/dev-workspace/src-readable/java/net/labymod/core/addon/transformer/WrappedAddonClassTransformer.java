package net.labymod.core.addon.transformer;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.labymod.api.addon.exception.AddonTransformException;
import net.labymod.api.addon.transform.LoadedAddonClassTransformer;
import net.labymod.api.util.reflection.Reflection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/transformer/WrappedAddonClassTransformer.class */
public class WrappedAddonClassTransformer implements LoadedAddonClassTransformer {
    private final Class<?> transformerInterface;
    private final Object transformer;
    private final MethodHandle shouldTransformMethod;
    private final MethodHandle transformMethod;

    public WrappedAddonClassTransformer(Class<?> transformerInterface, Object transformer) throws IllegalAccessException, NoSuchMethodException {
        this.transformerInterface = transformerInterface;
        this.transformer = transformer;
        this.transformMethod = Reflection.findVirtual(this.transformerInterface, "transform", MethodType.methodType(byte[].class, String.class, String.class, byte[].class));
        this.shouldTransformMethod = Reflection.findVirtual(this.transformerInterface, "shouldTransform", MethodType.methodType(Boolean.TYPE, String.class, String.class));
    }

    @Override // net.labymod.api.addon.transform.LoadedAddonClassTransformer
    public void init() {
        try {
            Method method = this.transformerInterface.getDeclaredMethod("init", new Class[0]);
            method.invoke(this.transformer, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override // net.labymod.api.addon.transform.LoadedAddonClassTransformer
    public boolean shouldTransform(String name, String transformedName) {
        try {
            return (boolean) this.shouldTransformMethod.invoke(this.transformer, name, transformedName);
        } catch (Throwable throwable) {
            throw new AddonTransformException("Failed to check if the class '" + name + "' should be transformed", throwable);
        }
    }

    @Override // net.labymod.api.addon.transform.LoadedAddonClassTransformer
    public byte[] transform(String name, String transformedName, byte[] classBytes) {
        try {
            return (byte[]) this.transformMethod.invoke(this.transformer, name, transformedName, classBytes);
        } catch (Throwable throwable) {
            throw new AddonTransformException("Failed to transform class '" + name + "'", throwable);
        }
    }
}
