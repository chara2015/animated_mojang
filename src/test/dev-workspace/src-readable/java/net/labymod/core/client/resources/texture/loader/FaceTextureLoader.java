package net.labymod.core.client.resources.texture.loader;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.client.resources.texture.TextureLoader;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.util.gson.UUIDTypeAdapter;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.render.schematic.block.ParameterType;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/loader/FaceTextureLoader.class */
public class FaceTextureLoader implements TextureLoader {
    private static final Logging LOGGER = Logging.create((Class<?>) FaceTextureLoader.class);

    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public boolean canLoad(URI uri) {
        return uri.getScheme().equals(ParameterType.FACE);
    }

    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public void loadTexture(URI uri, @Nullable ResourceLocation resourceLocation, CompletableTextureImage target) throws IOException {
        CompletableResourceLocation texture;
        MojangTextureService textureService = Laby.references().mojangTextureService();
        String input = uri.getHost();
        if (input.equalsIgnoreCase("default")) {
            target.executeCompletableListeners(new TextureImage(getFace(textureService.getDefaultTexture(MojangTextureType.SKIN))));
            return;
        }
        UUID uuid = null;
        if (input.length() == 36) {
            uuid = UUID.fromString(input);
        } else if (input.length() == 32) {
            uuid = UUIDTypeAdapter.fromString(input);
        } else if (input.length() < 2 || input.length() > 16) {
            target.stopLoadingOnError();
            throw new IllegalArgumentException("Invalid uuid/name provided in head:// url: " + input);
        }
        if (uuid != null) {
            texture = textureService.getTexture(uuid, MojangTextureType.SKIN);
        } else {
            texture = textureService.getTexture(input, MojangTextureType.SKIN);
        }
        CompletableResourceLocation skin = texture;
        skin.addCompletableListener(() -> {
            ResourceLocation location;
            if (!skin.isLoading() && (location = skin.getCompleted()) != null) {
                Laby.labyAPI().minecraft().executeNextTick(() -> {
                    TextureRepository textureRepository = Laby.references().textureRepository();
                    GameImage image = textureRepository.getImageFromTexture(textureRepository.getTexture(location));
                    try {
                        target.executeCompletableListeners(new TextureImage(getFace(image)));
                    } catch (Exception exception) {
                        LOGGER.error("Failed to load skin for {}", uri, exception);
                        target.stopLoadingOnError();
                    }
                });
            }
        });
    }

    private GameImage getFace(ResourceLocation location) throws IOException {
        GameImageProvider gameImageProvider = Laby.references().gameImageProvider();
        return getFace(gameImageProvider.getImage(location));
    }

    private GameImage getFace(GameImage skin) {
        GameImageProvider gameImageProvider = Laby.references().gameImageProvider();
        float factor = skin.getWidth() / 64.0f;
        int size = (int) (8.0f * factor);
        GameImage face = gameImageProvider.createImage(size, size);
        face.drawImage(skin, 0, 0, (int) (8.0f * factor), (int) (8.0f * factor), size, size);
        face.drawImageOverlay(skin, 0, 0, (int) (40.0f * factor), (int) (8.0f * factor), size, size);
        return face;
    }
}
