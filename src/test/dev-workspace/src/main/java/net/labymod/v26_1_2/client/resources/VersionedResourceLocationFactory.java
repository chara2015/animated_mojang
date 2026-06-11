package net.labymod.v26_1_2.client.resources;

import javax.inject.Singleton;
import net.labymod.api.client.resources.IllegalResourceLocationException;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.models.Implements;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.resources.AbstractResourceLocationFactory;
import net.minecraft.IdentifierException;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/resources/VersionedResourceLocationFactory.class */
@Singleton
@Implements(ResourceLocationFactory.class)
public class VersionedResourceLocationFactory extends AbstractResourceLocationFactory implements ResourceLocationFactory {
    @Override // net.labymod.api.client.resources.ResourceLocationFactory
    public ResourceLocation create(@NotNull String namespace, @NotNull String path) {
        return apply(namespace, path, this::createLocation);
    }

    private ResourceLocation createLocation(@NotNull String namespace, @NotNull String path) {
        try {
            return (ResourceLocation) CastUtil.cast(Identifier.fromNamespaceAndPath(namespace, path));
        } catch (IdentifierException exception) {
            throw new IllegalResourceLocationException((Throwable) exception);
        }
    }
}
