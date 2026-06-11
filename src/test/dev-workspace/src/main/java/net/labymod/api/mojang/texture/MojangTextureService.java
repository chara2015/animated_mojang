package net.labymod.api.mojang.texture;

import java.util.UUID;
import java.util.function.Consumer;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mojang/texture/MojangTextureService.class */
@Referenceable
public interface MojangTextureService {
    public static final UUID DEFAULT_UUID = new UUID(0, 0);

    CompletableResourceLocation getTexture(UUID uuid, MojangTextureType mojangTextureType);

    CompletableResourceLocation getTexture(String str, MojangTextureType mojangTextureType);

    MinecraftServices.SkinVariant getVariant(ResourceLocation resourceLocation);

    ResourceLocation getDefaultTexture(UUID uuid, MojangTextureType mojangTextureType);

    void applyTexture(UUID uuid, MojangTextureType mojangTextureType, String str);

    void applySkinTexture(UUID uuid, MinecraftServices.SkinVariant skinVariant, String str);

    default void getTexture(UUID uuid, MojangTextureType type, Consumer<ResourceLocation> textureResource) {
        CompletableResourceLocation completableTexture = getTexture(uuid, type);
        textureResource.accept(completableTexture.getCompleted());
        completableTexture.addCompletableListener(() -> {
            textureResource.accept(completableTexture.getCompleted());
        });
    }

    default ResourceLocation getDefaultTexture(MojangTextureType type) {
        return getDefaultTexture(DEFAULT_UUID, type);
    }
}
