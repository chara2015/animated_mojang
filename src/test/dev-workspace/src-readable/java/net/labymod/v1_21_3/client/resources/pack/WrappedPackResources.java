package net.labymod.v1_21_3.client.resources.pack;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Set;
import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/resources/pack/WrappedPackResources.class */
public final class WrappedPackResources extends Record implements aug {
    private final aug delegate;

    public WrappedPackResources(aug delegate) {
        this.delegate = delegate;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WrappedPackResources.class), WrappedPackResources.class, "delegate", "FIELD:Lnet/labymod/v1_21_3/client/resources/pack/WrappedPackResources;->delegate:Laug;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WrappedPackResources.class), WrappedPackResources.class, "delegate", "FIELD:Lnet/labymod/v1_21_3/client/resources/pack/WrappedPackResources;->delegate:Laug;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WrappedPackResources.class, Object.class), WrappedPackResources.class, "delegate", "FIELD:Lnet/labymod/v1_21_3/client/resources/pack/WrappedPackResources;->delegate:Laug;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public aug delegate() {
        return this.delegate;
    }

    @Nullable
    public avn<InputStream> a(String... var1) {
        return this.delegate.a(var1);
    }

    @Nullable
    public avn<InputStream> a(aui var1, alz var2) {
        return this.delegate.a(var1, var2);
    }

    public void a(aui var1, String var2, String var3, a var4) {
        this.delegate.a(var1, var2, var3, new WrappedResourceOutput(var4));
    }

    public Set<String> a(aui var1) {
        return this.delegate.a(var1);
    }

    @Nullable
    public <T> T a(aut<T> autVar) throws IOException {
        return (T) this.delegate.a(autVar);
    }

    public auf a() {
        return this.delegate.a();
    }

    public String b() {
        return this.delegate.b();
    }

    public void close() {
        this.delegate.close();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/resources/pack/WrappedPackResources$WrappedResourceOutput.class */
    public static final class WrappedResourceOutput extends Record implements a {
        private final a delegate;

        public WrappedResourceOutput(a delegate) {
            this.delegate = delegate;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WrappedResourceOutput.class), WrappedResourceOutput.class, "delegate", "FIELD:Lnet/labymod/v1_21_3/client/resources/pack/WrappedPackResources$WrappedResourceOutput;->delegate:Laug$a;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WrappedResourceOutput.class), WrappedResourceOutput.class, "delegate", "FIELD:Lnet/labymod/v1_21_3/client/resources/pack/WrappedPackResources$WrappedResourceOutput;->delegate:Laug$a;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WrappedResourceOutput.class, Object.class), WrappedResourceOutput.class, "delegate", "FIELD:Lnet/labymod/v1_21_3/client/resources/pack/WrappedPackResources$WrappedResourceOutput;->delegate:Laug$a;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public a delegate() {
            return this.delegate;
        }

        public void accept(alz location, avn<InputStream> inputStreamIoSupplier) {
            this.delegate.accept(location, () -> {
                return ((DefaultResourceTransformerRegistry) Laby.references().resourceTransformerRegistry()).applyTransformers((ResourceLocation) location, (InputStream) inputStreamIoSupplier.get());
            });
        }
    }
}
