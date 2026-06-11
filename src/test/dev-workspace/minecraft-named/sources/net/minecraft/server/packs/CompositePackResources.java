package net.minecraft.server.packs;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.resources.IoSupplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/CompositePackResources.class */
public class CompositePackResources implements PackResources {
    private final PackResources primaryPackResources;
    private final List<PackResources> packResourcesStack;

    public CompositePackResources(PackResources $$0, List<PackResources> $$1) {
        this.primaryPackResources = $$0;
        List<PackResources> $$2 = new ArrayList<>($$1.size() + 1);
        $$2.addAll(Lists.reverse($$1));
        $$2.add($$0);
        this.packResourcesStack = List.copyOf($$2);
    }

    @Override // net.minecraft.server.packs.PackResources
    public IoSupplier<InputStream> getRootResource(String... $$0) {
        return this.primaryPackResources.getRootResource($$0);
    }

    @Override // net.minecraft.server.packs.PackResources
    public IoSupplier<InputStream> getResource(PackType $$0, Identifier $$1) {
        for (PackResources $$2 : this.packResourcesStack) {
            IoSupplier<InputStream> $$3 = $$2.getResource($$0, $$1);
            if ($$3 != null) {
                return $$3;
            }
        }
        return null;
    }

    @Override // net.minecraft.server.packs.PackResources
    public void listResources(PackType $$0, String $$1, String $$2, PackResources.ResourceOutput $$3) {
        Map<Identifier, IoSupplier<InputStream>> $$4 = new HashMap<>();
        for (PackResources $$5 : this.packResourcesStack) {
            Objects.requireNonNull($$4);
            $$5.listResources($$0, $$1, $$2, (v1, v2) -> {
                r4.putIfAbsent(v1, v2);
            });
        }
        $$4.forEach($$3);
    }

    @Override // net.minecraft.server.packs.PackResources
    public Set<String> getNamespaces(PackType $$0) {
        Set<String> $$1 = new HashSet<>();
        for (PackResources $$2 : this.packResourcesStack) {
            $$1.addAll($$2.getNamespaces($$0));
        }
        return $$1;
    }

    @Override // net.minecraft.server.packs.PackResources
    public <T> T getMetadataSection(MetadataSectionType<T> metadataSectionType) throws IOException {
        return (T) this.primaryPackResources.getMetadataSection(metadataSectionType);
    }

    @Override // net.minecraft.server.packs.PackResources
    public PackLocationInfo location() {
        return this.primaryPackResources.location();
    }

    @Override // net.minecraft.server.packs.PackResources, java.lang.AutoCloseable
    public void close() {
        this.packResourcesStack.forEach((v0) -> {
            v0.close();
        });
    }
}
