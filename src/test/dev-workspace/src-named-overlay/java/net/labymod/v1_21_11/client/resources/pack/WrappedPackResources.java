package net.labymod.v1_21_11.client.resources.pack;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Set;
import javax.annotation.Nullable;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/resources/pack/WrappedPackResources.class */
public final class WrappedPackResources extends Record implements PackResources {
    private final PackResources delegate;

    public WrappedPackResources(PackResources delegate) {
        this.delegate = delegate;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WrappedPackResources.class), WrappedPackResources.class, "delegate", "FIELD:Lnet/labymod/v1_21_11/client/resources/pack/WrappedPackResources;->delegate:Lnet/minecraft/server/packs/PackResources;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WrappedPackResources.class), WrappedPackResources.class, "delegate", "FIELD:Lnet/labymod/v1_21_11/client/resources/pack/WrappedPackResources;->delegate:Lnet/minecraft/server/packs/PackResources;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WrappedPackResources.class, Object.class), WrappedPackResources.class, "delegate", "FIELD:Lnet/labymod/v1_21_11/client/resources/pack/WrappedPackResources;->delegate:Lnet/minecraft/server/packs/PackResources;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public PackResources delegate() {
        return this.delegate;
    }

    @Nullable
    public IoSupplier<InputStream> getRootResource(String... var1) {
        return this.delegate.getRootResource(var1);
    }

    @Nullable
    public IoSupplier<InputStream> getResource(PackType var1, Identifier var2) {
        return this.delegate.getResource(var1, var2);
    }

    public void listResources(PackType var1, String var2, String var3, PackResources.ResourceOutput var4) {
        this.delegate.listResources(var1, var2, var3, new WrappedResourceOutput(var4));
    }

    public Set<String> getNamespaces(PackType var1) {
        return this.delegate.getNamespaces(var1);
    }

    @Nullable
    public <T> T getMetadataSection(@NotNull MetadataSectionType<T> metadataSectionType) throws IOException {
        return (T) this.delegate.getMetadataSection(metadataSectionType);
    }

    public PackLocationInfo location() {
        return this.delegate.location();
    }

    public String packId() {
        return this.delegate.packId();
    }

    public void close() {
        this.delegate.close();
    }

    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/resources/pack/WrappedPackResources$WrappedResourceOutput.class */
    public static final class WrappedResourceOutput extends Record implements PackResources.ResourceOutput {
        private final PackResources.ResourceOutput delegate;

        public WrappedResourceOutput(PackResources.ResourceOutput delegate) {
            this.delegate = delegate;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WrappedResourceOutput.class), WrappedResourceOutput.class, "delegate", "FIELD:Lnet/labymod/v1_21_11/client/resources/pack/WrappedPackResources$WrappedResourceOutput;->delegate:Lnet/minecraft/server/packs/PackResources$ResourceOutput;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WrappedResourceOutput.class), WrappedResourceOutput.class, "delegate", "FIELD:Lnet/labymod/v1_21_11/client/resources/pack/WrappedPackResources$WrappedResourceOutput;->delegate:Lnet/minecraft/server/packs/PackResources$ResourceOutput;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WrappedResourceOutput.class, Object.class), WrappedResourceOutput.class, "delegate", "FIELD:Lnet/labymod/v1_21_11/client/resources/pack/WrappedPackResources$WrappedResourceOutput;->delegate:Lnet/minecraft/server/packs/PackResources$ResourceOutput;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public PackResources.ResourceOutput delegate() {
            return this.delegate;
        }

        public void accept(Identifier location, IoSupplier<InputStream> inputStreamIoSupplier) {
            this.delegate.accept(location, () -> {
                return Laby.references().resourceTransformerRegistry().applyTransformers((ResourceLocation) location, (InputStream) inputStreamIoSupplier.get());
            });
        }
    }
}
