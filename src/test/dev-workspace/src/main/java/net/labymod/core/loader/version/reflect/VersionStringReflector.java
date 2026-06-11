package net.labymod.core.loader.version.reflect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/loader/version/reflect/VersionStringReflector.class */
public final class VersionStringReflector {
    private final String className;
    private final String fieldName;

    public VersionStringReflector(String className, String fieldName) {
        this.className = className;
        this.fieldName = fieldName;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x003c, code lost:
    
        r7.accept((java.lang.String) r0.get(null));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void invoke(net.labymod.api.util.IsolatedClassLoader r6, java.util.function.Consumer<java.lang.String> r7) {
        /*
            r5 = this;
            r0 = r7
            java.lang.Object r0 = java.util.Objects.requireNonNull(r0)
            r0 = r6
            r1 = r5
            java.lang.String r1 = r1.className     // Catch: java.lang.ReflectiveOperationException -> L57
            java.lang.Class r0 = r0.loadClass(r1)     // Catch: java.lang.ReflectiveOperationException -> L57
            r8 = r0
            r0 = r8
            java.lang.reflect.Field[] r0 = r0.getDeclaredFields()     // Catch: java.lang.ReflectiveOperationException -> L57
            r9 = r0
            r0 = r9
            int r0 = r0.length     // Catch: java.lang.ReflectiveOperationException -> L57
            r10 = r0
            r0 = 0
            r11 = r0
        L1c:
            r0 = r11
            r1 = r10
            if (r0 >= r1) goto L54
            r0 = r9
            r1 = r11
            r0 = r0[r1]     // Catch: java.lang.ReflectiveOperationException -> L57
            r12 = r0
            r0 = r12
            java.lang.String r0 = r0.getName()     // Catch: java.lang.ReflectiveOperationException -> L57
            r1 = r5
            java.lang.String r1 = r1.fieldName     // Catch: java.lang.ReflectiveOperationException -> L57
            boolean r0 = r0.equals(r1)     // Catch: java.lang.ReflectiveOperationException -> L57
            if (r0 != 0) goto L3c
            goto L4e
        L3c:
            r0 = r7
            r1 = r12
            r2 = 0
            java.lang.Object r1 = r1.get(r2)     // Catch: java.lang.ReflectiveOperationException -> L57
            java.lang.String r1 = (java.lang.String) r1     // Catch: java.lang.ReflectiveOperationException -> L57
            r0.accept(r1)     // Catch: java.lang.ReflectiveOperationException -> L57
            goto L54
        L4e:
            int r11 = r11 + 1
            goto L1c
        L54:
            goto L6a
        L57:
            r8 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            r1 = r0
            r2 = r5
            java.lang.String r2 = java.lang.String.valueOf(r2)
            java.lang.String r2 = r2 + " could not be called"
            r3 = r8
            r1.<init>(r2, r3)
            throw r0
        L6a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.core.loader.version.reflect.VersionStringReflector.invoke(net.labymod.api.util.IsolatedClassLoader, java.util.function.Consumer):void");
    }

    public String getClassName() {
        return this.className;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String toString() {
        return this.className + "." + this.fieldName;
    }
}
