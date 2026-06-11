package net.labymod.core.main.user.shop.spray.model;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Locale;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureDetails;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.resources.ReleaseTextureEvent;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/spray/model/SprayAssetProvider.class */
@Singleton
@Referenceable
public final class SprayAssetProvider {
    private static final Function<TextureType, ResourceLocation> WEAR_LOCATION = Laby.references().functionMemoizeStorage().memoize(textureType -> {
        String type = textureType == TextureType.WEAR_FADE_IN ? "fadein" : "fadeout";
        return ResourceLocation.create("labymod", "textures/spray/default_wear_" + type + ".png");
    });
    private final Short2ObjectMap<CompletableResourceLocation> diffuseCache = new Short2ObjectOpenHashMap();
    private final Int2ObjectMap<CompletableResourceLocation> wearCache = new Int2ObjectOpenHashMap();
    private final TextureRepository textureRepository;
    private final GameImageProvider gameImageProvider;

    @Inject
    public SprayAssetProvider(EventBus eventBus, TextureRepository textureRepository, GameImageProvider gameImageProvider) {
        this.textureRepository = textureRepository;
        this.gameImageProvider = gameImageProvider;
        eventBus.registerListener(this);
    }

    @Subscribe
    public void onTextureReleased(ReleaseTextureEvent event) {
        ResourceLocation releasedTextureLocation = event.location();
        ObjectIterator it = this.diffuseCache.short2ObjectEntrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Short2ObjectMap.Entry<CompletableResourceLocation> entry = (Short2ObjectMap.Entry) it.next();
            CompletableResourceLocation location = (CompletableResourceLocation) entry.getValue();
            ResourceLocation completedLocation = location.getCompleted();
            if (completedLocation.equals(releasedTextureLocation)) {
                this.diffuseCache.remove(entry.getShortKey());
                break;
            }
        }
        ObjectIterator it2 = this.wearCache.int2ObjectEntrySet().iterator();
        while (it2.hasNext()) {
            Int2ObjectMap.Entry<CompletableResourceLocation> entry2 = (Int2ObjectMap.Entry) it2.next();
            CompletableResourceLocation location2 = (CompletableResourceLocation) entry2.getValue();
            ResourceLocation completedLocation2 = location2.getCompleted();
            if (completedLocation2.equals(releasedTextureLocation)) {
                this.wearCache.remove(entry2.getIntKey());
                return;
            }
        }
    }

    public CompletableResourceLocation getTexture(net.laby.lib.sprays.Spray spray, TextureType textureType) {
        CompletableResourceLocation location;
        short sprayId = (short) spray.id();
        boolean diffuseTexture = textureType == TextureType.DIFFUSE;
        if (diffuseTexture) {
            location = (CompletableResourceLocation) this.diffuseCache.get(sprayId);
        } else {
            location = (CompletableResourceLocation) this.wearCache.get((sprayId * 17) + textureType.ordinal());
        }
        if (location == null) {
            ResourceLocation resourceLocation = ResourceLocation.create("labymod", "textures/spray/" + sprayId + "_" + textureType.getName() + ".png");
            TextureDetails.Builder builder = TextureDetails.builder(resourceLocation);
            builder.withUrl(getUrl(spray, textureType));
            if (diffuseTexture) {
                builder.withFallbackLocation(Textures.SPRAY_LOADING).withImageProcessor(this::scaleImage);
            } else {
                builder.withFallbackLocation(WEAR_LOCATION.apply(textureType));
            }
            location = this.textureRepository.getOrRegisterTexture(builder.build());
            if (diffuseTexture) {
                this.diffuseCache.put(sprayId, location);
            } else {
                this.wearCache.put((sprayId * 17) + textureType.ordinal(), location);
            }
        }
        return location;
    }

    private GameImage scaleImage(GameImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int offset = width / 2;
        BufferedImage bufferedImage = image.getImage();
        Image scaledImage = bufferedImage.getScaledInstance(width - offset, height - offset, 4);
        BufferedImage newImage = new BufferedImage(width, height, 6);
        Graphics2D graphics = newImage.createGraphics();
        int halfOffset = offset / 2;
        graphics.drawImage(scaledImage, halfOffset, halfOffset, (ImageObserver) null);
        graphics.dispose();
        return this.gameImageProvider.getImage(newImage);
    }

    private String getUrl(net.laby.lib.sprays.Spray spray, TextureType textureType) {
        return String.format(Locale.ROOT, Constants.LegacyUrls.CUSTOM_TEXTURES, "sticker", "cut/" + String.valueOf(spray.uuid()) + textureType.getPathSuffix() + ".webp");
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/spray/model/SprayAssetProvider$TextureType.class */
    public enum TextureType {
        DIFFUSE(""),
        WEAR_FADE_IN("_wear_fadein"),
        WEAR_FADE_OUT("_wear_fadeout");

        private final String name = name().toLowerCase(Locale.ROOT);
        private final String pathSuffix;

        TextureType(String pathSuffix) {
            this.pathSuffix = pathSuffix;
        }

        public String getName() {
            return this.name;
        }

        public String getPathSuffix() {
            return this.pathSuffix;
        }
    }
}
