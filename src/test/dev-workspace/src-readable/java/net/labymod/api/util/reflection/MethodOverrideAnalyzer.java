package net.labymod.api.util.reflection;

import net.labymod.api.util.Lazy;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/reflection/MethodOverrideAnalyzer.class */
public class MethodOverrideAnalyzer {
    private final Class<?> superClass;
    private final Class<?> subClass;
    private final String name;
    private final Class<?>[] parameterTypes;
    private final Lazy<Boolean> overridden = Lazy.of(() -> {
        return Boolean.valueOf(Reflection.isMethodOverridden(this.superClass, this.subClass, this.name, this.parameterTypes));
    });

    public MethodOverrideAnalyzer(Class<?> superClass, Class<?> subClass, String name, Class<?>... parameterTypes) {
        this.superClass = superClass;
        this.subClass = subClass;
        this.name = name;
        this.parameterTypes = parameterTypes;
    }

    public Class<?> getSuperClass() {
        return this.superClass;
    }

    public Class<?> getSubClass() {
        return this.subClass;
    }

    public String getName() {
        return this.name;
    }

    public Class<?>[] getParameterTypes() {
        return this.parameterTypes;
    }

    public boolean isOverridden() {
        return this.overridden.get().booleanValue();
    }
}
