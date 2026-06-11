package net.labymod.api.client.gui.icon;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.data.Sprite;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.draw.PlayerHeadRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.TextureDetails;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.models.ServerGroup;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.util.HashUtil;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/icon/Icon.class */
public class Icon {
    private static final String REMOTE_ICON_PREFIX = "remote_icon/";
    private final RoundedGeometryBuilder roundedGeometryBuilder;
    private Supplier<ResourceLocation> resourceLocationSupplier;
    private boolean playerHead;
    private HatConsumer hatConsumer;
    private final Sprite sprite;
    private int resolutionWidth;
    private int resolutionHeight;
    private ResourceLocation prevResolutionExtractedLocation;
    private boolean resolutionExtracted;

    @Nullable
    private TextureAtlas textureAtlas;

    @Nullable
    private TextureSprite textureSprite;

    @Nullable
    private Supplier<TextureSprite> hoverTextureSprite;
    private float aspectRatio;
    private boolean checkedAspectRatio;
    private int spriteHoverOffsetX;
    private int spriteHoverOffsetY;
    private BorderRadius borderRadius;
    private final String url;
    private boolean blur;
    private boolean ignoreResolutionExtraction;
    private final Map<String, Runnable> updateListeners;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/icon/Icon$HatConsumer.class */
    public interface HatConsumer {
        boolean isWearingHat();
    }

    protected Icon(ResourceLocation resourceLocation) {
        this(() -> {
            return resourceLocation;
        }, null);
    }

    protected Icon(Supplier<ResourceLocation> resourceLocationSupplier, String url) {
        this.sprite = new Sprite();
        this.resolutionWidth = 256;
        this.resolutionHeight = 256;
        this.resolutionExtracted = false;
        this.aspectRatio = 0.0f;
        this.blur = false;
        this.updateListeners = new HashMap();
        this.roundedGeometryBuilder = Laby.references().roundedGeometryBuilder();
        this.resourceLocationSupplier = resourceLocationSupplier;
        this.url = url;
    }

    public int getSpriteWidth() {
        float width = this.sprite.getWidth();
        return width == 0.0f ? this.resolutionWidth : (int) width;
    }

    public int getSpriteHeight() {
        float height = this.sprite.getHeight();
        return height == 0.0f ? this.resolutionHeight : (int) height;
    }

    public void render(Stack stack, float x, float y, float size) {
        render(stack, x, y, size, size);
    }

    public void render(Stack stack, float x, float y, float size, boolean hover) {
        render(stack, x, y, size, hover, -1);
    }

    public void render(Stack stack, float x, float y, float size, boolean hover, int color) {
        render(stack, x, y, size, size, hover, color);
    }

    public void render(Stack stack, float x, float y, float width, float height) {
        render(stack, x, y, width, height, false, -1);
    }

    public void render(RenderState renderState, Stack stack, float x, float y, float width, float height) {
        render(renderState, stack, x, y, width, height, false, -1);
    }

    public void render(Stack stack, Rectangle rectangle) {
        render(stack, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public void render(Stack stack, float x, float y, float width, float height, boolean hover, int color) {
        render(stack, x, y, width, height, hover, color, (Rectangle) null);
    }

    public void render(RenderState renderState, Stack stack, float x, float y, float width, float height, boolean hover, int color) {
        render(renderState, stack, x, y, width, height, hover, color, null);
    }

    public static Icon url(String url) {
        return url(url, (Consumer<Texture>) null);
    }

    public static Icon url(String url, Consumer<Texture> textureConsumer) {
        return url(url, Textures.EMPTY, textureConsumer);
    }

    public static Icon url(String url, ResourceLocation fallbackLocation) {
        return url(url, fallbackLocation, null);
    }

    public static Icon url(String url, ResourceLocation fallbackLocation, Consumer<Texture> textureConsumer) {
        ResourceLocation location = ResourceLocation.create("labymod", createLocationPath(url));
        TextureDetails details = TextureDetails.builder(location).withFallbackLocation(fallbackLocation).withFinishHandler(textureConsumer).withUrl(url).build();
        TextureRepository textureRepository = Laby.references().textureRepository();
        return new Icon(() -> {
            return textureRepository.getOrRegisterTexture(details).getCompleted();
        }, url);
    }

    private static String createLocationPath(String url) {
        return "remote_icon/" + HashUtil.md5Hex(url.getBytes(StandardCharsets.UTF_8)) + ".png";
    }

    public void render(ResourceRenderContext context, float x, float y, float size) {
        render(context, x, y, size, size);
    }

    public void render(ResourceRenderContext context, float x, float y, float size, boolean hover) {
        render(context, x, y, size, size, hover, -1);
    }

    public void render(ResourceRenderContext context, float x, float y, float size, boolean hover, int color) {
        render(context, x, y, size, size, hover, color);
    }

    public void render(ResourceRenderContext context, float x, float y, float width, float height) {
        render(context, x, y, width, height, false, -1);
    }

    public void render(ResourceRenderContext context, float x, float y, float width, float height, boolean hover, int color) {
        TextureSprite textureSprite = getTextureSprite(hover);
        if (textureSprite == null) {
            ColorFormat colorFormat = ColorFormat.ARGB32;
            context.blit(x, y, width, height, this.sprite.getX() + (hover ? this.spriteHoverOffsetX : 0), this.sprite.getY() + (hover ? this.spriteHoverOffsetY : 0), getSpriteWidth(), getSpriteHeight(), this.resolutionWidth, this.resolutionHeight, color == -1 ? 1.0f : colorFormat.normalizedRed(color), color == -1 ? 1.0f : colorFormat.normalizedGreen(color), color == -1 ? 1.0f : colorFormat.normalizedBlue(color), color == -1 ? 1.0f : colorFormat.normalizedAlpha(color));
        } else {
            ResourceRenderContext.ATLAS_RENDERER.blitSprite(context, textureSprite.uv(), textureSprite.scaling(), MathHelper.ceil(x), MathHelper.ceil(y), MathHelper.ceil(width), MathHelper.ceil(height), 0, color);
        }
    }

    public Icon setHoverOffset(int offsetX, int offsetY) {
        this.spriteHoverOffsetX = offsetX;
        this.spriteHoverOffsetY = offsetY;
        return this;
    }

    public Icon setHoverSprite(Supplier<ResourceLocation> sprite) {
        this.hoverTextureSprite = () -> {
            return this.textureAtlas.findSprite((ResourceLocation) sprite.get());
        };
        return this;
    }

    public static Icon completable(CompletableResourceLocation completable) {
        Objects.requireNonNull(completable);
        Icon icon = new Icon(completable::getCompleted, null);
        registerCompletableListener(icon, completable);
        return icon;
    }

    public static Icon completable(Supplier<CompletableResourceLocation> supplier) {
        AtomicReference<CompletableResourceLocation> ref = new AtomicReference<>();
        AtomicReference<Icon> iconRef = new AtomicReference<>();
        Icon icon = new Icon(() -> {
            CompletableResourceLocation current = (CompletableResourceLocation) supplier.get();
            if (current == null) {
                return null;
            }
            CompletableResourceLocation previous = (CompletableResourceLocation) ref.get();
            if (previous != current) {
                ref.set(current);
                registerCompletableListener((Icon) iconRef.get(), current);
            }
            return current.getCompleted();
        }, null);
        iconRef.set(icon);
        return icon;
    }

    private static void registerCompletableListener(Icon icon, CompletableResourceLocation completable) {
        completable.addCompletableListener(new Runnable() { // from class: net.labymod.api.client.gui.icon.Icon.1
            @Override // java.lang.Runnable
            public void run() {
                Icon.this.resolutionExtracted = false;
            }
        });
    }

    public static Icon head(String name) {
        MojangTextureService textureService = Laby.references().mojangTextureService();
        Icon icon = completable((Supplier<CompletableResourceLocation>) () -> {
            return textureService.getTexture(name, MojangTextureType.SKIN);
        });
        icon.playerHead = true;
        icon.ignoreResolutionExtraction = true;
        return icon.hatConsumer(() -> {
            return true;
        });
    }

    public void setResourceLocationSupplier(Supplier<ResourceLocation> resourceLocationSupplier) {
        this.resourceLocationSupplier = resourceLocationSupplier;
    }

    public static Icon head(UUID uuid) {
        return head(uuid, true);
    }

    public static Icon head(UUID uuid, boolean shouldRenderHat) {
        MojangTextureService textureService = Laby.references().mojangTextureService();
        Icon icon = completable((Supplier<CompletableResourceLocation>) () -> {
            return textureService.getTexture(uuid, MojangTextureType.SKIN);
        });
        icon.playerHead = true;
        icon.ignoreResolutionExtraction = true;
        return icon.hatConsumer(() -> {
            return shouldRenderHat;
        });
    }

    public Icon hatConsumer(HatConsumer consumer) {
        this.hatConsumer = consumer;
        return this;
    }

    public Icon enableHat() {
        return hatConsumer(new HatConsumer(this) { // from class: net.labymod.api.client.gui.icon.Icon.2
            @Override // net.labymod.api.client.gui.icon.Icon.HatConsumer
            public boolean isWearingHat() {
                return true;
            }
        });
    }

    public Icon resourceLocation(final ResourceLocation resourceLocation) {
        this.resourceLocationSupplier = new Supplier<ResourceLocation>(this) { // from class: net.labymod.api.client.gui.icon.Icon.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.function.Supplier
            public ResourceLocation get() {
                return resourceLocation;
            }
        };
        return this;
    }

    public Icon aspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        return this;
    }

    public Icon spritePosition(int spriteX, int spriteY) {
        this.sprite.setPosition(spriteX, spriteY);
        return this;
    }

    public Sprite sprite() {
        return this.sprite;
    }

    public Icon spriteSize(int spriteWidth, int spriteHeight) {
        this.sprite.setSize(spriteWidth, spriteHeight);
        return this;
    }

    public Icon aspectRatio(int width, int height) {
        float aspectRatio = width / height;
        if (this.aspectRatio == aspectRatio) {
            return this;
        }
        this.aspectRatio = aspectRatio;
        fireUpdate();
        return this;
    }

    @Nullable
    public String getUrl() {
        return this.url;
    }

    public float getAspectRatio() {
        if (!this.checkedAspectRatio && this.aspectRatio == 0.0f && !this.ignoreResolutionExtraction) {
            ResourceLocation location = getResourceLocation();
            if (location == null) {
                return this.aspectRatio;
            }
            extractResolutionFromMetadata();
            this.checkedAspectRatio = true;
        }
        return this.aspectRatio;
    }

    private void extractResolutionFromMetadata() {
        ResourceLocation location = this.resourceLocationSupplier.get();
        if (location != null) {
            if (Objects.equals(this.prevResolutionExtractedLocation, location) && this.resolutionExtracted) {
                return;
            }
            Metadata metadata = location.metadata();
            if (metadata.has("width") && metadata.has("height")) {
                resolution(((Integer) metadata.get("width")).intValue(), ((Integer) metadata.get("height")).intValue());
            }
            this.resolutionExtracted = true;
            this.prevResolutionExtractedLocation = location;
        }
    }

    public int getResolutionWidth() {
        extractResolutionFromMetadata();
        return this.resolutionWidth;
    }

    public int getResolutionHeight() {
        extractResolutionFromMetadata();
        return this.resolutionHeight;
    }

    @Deprecated
    public ResourceLocation resourceLocation() {
        return getResourceLocation();
    }

    @Nullable
    public ResourceLocation getResourceLocation() {
        ResourceLocation resourceLocation = this.resourceLocationSupplier.get();
        if (!this.ignoreResolutionExtraction) {
            extractResolutionFromMetadata();
        }
        return resourceLocation;
    }

    public Icon resolution(int resolutionWidth, int resolutionHeight) {
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
        this.sprite.setSize(resolutionWidth, resolutionHeight);
        aspectRatio(resolutionWidth, resolutionHeight);
        return this;
    }

    public static Icon head(GameProfile profile) {
        return head((Supplier<CompletableResourceLocation>) () -> {
            return profile.getTexture(MojangTextureType.SKIN);
        });
    }

    public static Icon texture(ResourceLocation resourceLocation) {
        return new Icon(resourceLocation);
    }

    public static Icon texture(Supplier<ResourceLocation> resourceLocationSupplier) {
        return new Icon(resourceLocationSupplier, null);
    }

    public static Icon currentServer() {
        ServerController serverController = Laby.labyAPI().serverController();
        ServerData currentServerData = serverController.getCurrentServerData();
        if (currentServerData == null) {
            return defaultServer();
        }
        return server(currentServerData.address().toString());
    }

    public static Icon defaultServer() {
        return server("default");
    }

    public static Icon server(String address) {
        LabyNetController labyNetController = Laby.references().labyNetController();
        Optional<ServerGroup> server = labyNetController.getServerByIp(address);
        Icon serverIcon = url("mcserver://" + address);
        if (server.isEmpty()) {
            return serverIcon;
        }
        ServerGroup serverGroup = server.get();
        Optional<ServerGroup.Attachment> attachment = serverGroup.getAttachment("icon");
        if (attachment.isPresent()) {
            ServerGroup.Attachment iconAttachment = attachment.get();
            serverIcon = iconAttachment.getIcon();
        }
        return serverIcon;
    }

    public static Icon head(ResourceLocation resourceLocation) {
        Icon icon = texture(resourceLocation);
        icon.playerHead = true;
        return icon;
    }

    public static Icon head(CompletableResourceLocation resourceLocation) {
        Icon icon = completable(resourceLocation);
        icon.playerHead = true;
        return icon;
    }

    public static Icon head(Supplier<CompletableResourceLocation> supplier) {
        Icon icon = completable(supplier);
        icon.playerHead = true;
        icon.ignoreResolutionExtraction = true;
        return icon;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Icon icon = (Icon) o;
        return this.playerHead == icon.playerHead && Objects.equals(this.sprite, icon.sprite) && this.resolutionWidth == icon.resolutionWidth && this.resolutionHeight == icon.resolutionHeight && Float.compare(icon.aspectRatio, this.aspectRatio) == 0 && this.spriteHoverOffsetX == icon.spriteHoverOffsetX && this.spriteHoverOffsetY == icon.spriteHoverOffsetY && Objects.equals(getResourceLocation(), icon.getResourceLocation());
    }

    public int hashCode() {
        ResourceLocation location = getResourceLocation();
        int result = this.playerHead ? 1 : 0;
        return (31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * ((31 * result) + (location != null ? location.hashCode() : 0))) + (this.hatConsumer != null ? this.hatConsumer.hashCode() : 0))) + this.sprite.hashCode())) + this.resolutionWidth)) + this.resolutionHeight)) + (this.aspectRatio != 0.0f ? Float.floatToIntBits(this.aspectRatio) : 0))) + this.spriteHoverOffsetX)) + this.spriteHoverOffsetY;
    }

    public Icon ignoreResolutionExtraction() {
        this.ignoreResolutionExtraction = true;
        return this;
    }

    public Icon makeBlurry() {
        this.blur = true;
        return this;
    }

    public void render(Stack stack, float x, float y, float width, float height, boolean hover, int color, Rectangle stencil) {
        render(null, stack, x, y, width, height, hover, color, stencil);
    }

    public void render(RenderState renderState, Stack stack, float x, float y, float width, float height, boolean hover, int color, Rectangle stencil) {
        RenderPipeline renderPipeline = Laby.labyAPI().renderPipeline();
        ResourceRenderer renderer = renderPipeline.resourceRenderer();
        float spriteX = this.sprite.getX() + (hover ? this.spriteHoverOffsetX : 0);
        float spriteY = this.sprite.getY() + (hover ? this.spriteHoverOffsetY : 0);
        ResourceLocation location = getResourceLocation();
        float spriteWidth = getSpriteWidth();
        float spriteHeight = getSpriteHeight();
        if (location == null) {
            return;
        }
        if (this.playerHead) {
            boolean wearingHat = this.hatConsumer != null && this.hatConsumer.isWearingHat();
            PlayerHeadRenderer headRenderer = (PlayerHeadRenderer) ((PlayerHeadRenderer) ((PlayerHeadRenderer) renderer.head().pos(x, y)).size(width, height)).wearingHat(wearingHat).player(location).color(color);
            if (renderState == null) {
                headRenderer.render(stack);
                return;
            } else {
                headRenderer.render(stack, renderState);
                return;
            }
        }
        float left = stencil != null ? stencil.getLeft() : x;
        float top = stencil != null ? stencil.getTop() : y;
        float right = stencil != null ? stencil.getRight() : x + width;
        float bottom = stencil != null ? stencil.getBottom() : y + height;
        FloatVector4 transform = stack.transformVector(left, top, right, bottom);
        float translatedLeft = transform.getX();
        float translatedTop = transform.getY();
        float translatedRight = transform.getZ();
        float translatedBottom = transform.getW();
        if (this.borderRadius != null) {
            renderer.roundedData(this.roundedGeometryBuilder.pos(translatedLeft + left, translatedTop + top, translatedRight + right, translatedBottom + bottom).leftTopRadius(this.borderRadius.getLeftTop()).rightTopRadius(this.borderRadius.getRightTop()).rightBottomRadius(this.borderRadius.getRightBottom()).leftBottomRadius(this.borderRadius.getLeftBottom()).build());
        }
        TextureSprite textureSprite = getTextureSprite(hover);
        if (textureSprite == null) {
            boolean currentBlur = this.blur;
            this.blur = false;
            ColorFormat colorFormat = ColorFormat.ARGB32;
            ((ResourceRenderer) ((ResourceRenderer) ((ResourceRenderer) ((ResourceRenderer) renderer.texture(location).pos(x, y)).size(width, height)).sprite(spriteX, spriteY, spriteWidth, spriteHeight).resolution(this.resolutionWidth, this.resolutionHeight).lowerEdgeSoftness(-0.125f)).upperEdgeSoftness(0.0f)).blur(currentBlur).color(color == -1 ? 1.0f : colorFormat.normalizedRed(color), color == -1 ? 1.0f : colorFormat.normalizedGreen(color), color == -1 ? 1.0f : colorFormat.normalizedBlue(color), color == -1 ? 1.0f : colorFormat.normalizedAlpha(color));
            if (renderState == null) {
                renderer.render(stack);
                return;
            } else {
                renderer.render(stack, renderState);
                return;
            }
        }
        ResourceRenderContext resourceRenderContext = renderPipeline.renderContexts().resourceRenderContext();
        resourceRenderContext.begin(stack);
        float spriteWidth2 = this.sprite.getWidth();
        float spriteHeight2 = this.sprite.getHeight();
        ResourceRenderContext.ATLAS_RENDERER.blitSprite(resourceRenderContext, textureSprite.uv(), textureSprite.scaling(), MathHelper.ceil(x), MathHelper.ceil(y), MathHelper.ceil(spriteWidth2 == 0.0f ? width : spriteWidth2), MathHelper.ceil(spriteHeight2 == 0.0f ? height : spriteHeight2), color);
        ShaderTextures.setShaderTexture(0, getResourceLocation());
        if (renderState == null) {
            resourceRenderContext.uploadToBuffer();
        } else {
            resourceRenderContext.uploadToBuffer(renderState);
        }
    }

    @NotNull
    public static Icon spriteCoordinates(ThemeTextureLocation texture, int spriteX, int spriteY, int spriteWidth, int spriteHeight) {
        return sprite(texture, spriteX, spriteY, spriteWidth, spriteHeight, texture.getWidth(), texture.getHeight());
    }

    @NotNull
    public static Icon sprite(ThemeTextureLocation texture, int slotX, int slotY, int width, int height) {
        return spriteCoordinates(texture, slotX * width, slotY * height, width, height);
    }

    @NotNull
    public static Icon sprite(ThemeTextureLocation texture, int slotX, int slotY, int size) {
        return sprite(texture, slotX, slotY, size, size);
    }

    @NotNull
    public static Icon sprite(ResourceLocation resourceLocation, int spriteX, int spriteY, int spriteWidth, int spriteHeight, int resolutionWidth, int resolutionHeight) {
        Icon icon = texture(resourceLocation);
        icon.sprite.set(spriteX, spriteY, spriteWidth, spriteHeight);
        icon.resolutionWidth = resolutionWidth;
        icon.resolutionHeight = resolutionHeight;
        icon.aspectRatio = spriteWidth / spriteHeight;
        return icon;
    }

    @NotNull
    public static Icon sprite(TextureAtlas atlas, TextureSprite sprite) {
        return sprite(atlas, sprite, 0.0f, 0.0f);
    }

    @NotNull
    public static Icon sprite(TextureAtlas atlas, TextureSprite sprite, float width, float height) {
        Icon icon = texture(atlas.resource());
        icon.textureAtlas = atlas;
        icon.textureSprite = sprite;
        icon.resolutionWidth = atlas.getAtlasWidth();
        icon.resolutionHeight = atlas.getAtlasHeight();
        icon.sprite.setSize(width, height);
        return icon;
    }

    @NotNull
    public static Icon sprite(Supplier<ResourceLocation> resourceLocationSupplier, int spriteX, int spriteY, int spriteWidth, int spriteHeight, int resolutionWidth, int resolutionHeight) {
        Icon icon = texture(resourceLocationSupplier);
        icon.sprite.set(spriteX, spriteY, spriteWidth, spriteHeight);
        icon.resolutionWidth = resolutionWidth;
        icon.resolutionHeight = resolutionHeight;
        icon.aspectRatio = spriteWidth / spriteHeight;
        return icon;
    }

    @NotNull
    public static Icon sprite8(ResourceLocation resourceLocation, int slotX, int slotY) {
        return sprite(resourceLocation, slotX << 3, slotY << 3, 8, 8, 128, 128);
    }

    @NotNull
    public static Icon sprite16(ResourceLocation resourceLocation, int slotX, int slotY) {
        return sprite(resourceLocation, slotX << 4, slotY << 4, 16, 16, 128, 128);
    }

    @NotNull
    public static Icon sprite32(ResourceLocation resourceLocation, int slotX, int slotY) {
        return sprite(resourceLocation, slotX << 5, slotY << 5, 32, 32, 128, 128);
    }

    public void setBorderRadius(BorderRadius borderRadius) {
        this.borderRadius = borderRadius;
    }

    public BorderRadius getBorderRadius() {
        return this.borderRadius;
    }

    public void setUpdateListener(String key, Runnable listener) {
        if (listener != null) {
            this.updateListeners.put(key, listener);
        } else {
            this.updateListeners.remove(key);
        }
    }

    @Nullable
    public TextureSprite getTextureSprite(boolean hover) {
        TextureSprite sprite;
        if (hover && this.hoverTextureSprite != null && (sprite = this.hoverTextureSprite.get()) != null) {
            return sprite;
        }
        return this.textureSprite;
    }

    private void fireUpdate() {
        for (Runnable runnable : this.updateListeners.values()) {
            runnable.run();
        }
    }

    public int getSpriteHoverOffsetX() {
        return this.spriteHoverOffsetX;
    }

    public int getSpriteHoverOffsetY() {
        return this.spriteHoverOffsetY;
    }

    public boolean isPlayerHead() {
        return this.playerHead;
    }

    public boolean isWearingHat() {
        return this.hatConsumer != null && this.hatConsumer.isWearingHat();
    }
}
