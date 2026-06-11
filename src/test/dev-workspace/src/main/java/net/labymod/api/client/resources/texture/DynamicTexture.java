package net.labymod.api.client.resources.texture;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.logging.Logging;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/DynamicTexture.class */
public class DynamicTexture extends LabyTexture {
    private static final Logging LOGGER = Logging.getLogger();
    private final SamplerDescription samplerDescription;

    @Nullable
    private GameImage image;

    public DynamicTexture(ResourceLocation textureLocation, GameImage image, SamplerDescription samplerDescription) {
        super(textureLocation);
        this.image = image;
        this.samplerDescription = samplerDescription;
        initializeHandle(image);
        upload();
    }

    public DynamicTexture(ResourceLocation textureLocation, GameImage image) {
        this(textureLocation, image, SamplerDescription.builder().setFilter(SamplerDescription.Filter.NEAREST).build());
    }

    public DynamicTexture(ResourceLocation textureLocation, int width, int height, SamplerDescription samplerDescription) {
        this(textureLocation, GameImage.IMAGE_PROVIDER.createImage(width, height), samplerDescription);
    }

    public DynamicTexture(ResourceLocation textureLocation, int width, int height) {
        this(textureLocation, GameImage.IMAGE_PROVIDER.createImage(width, height), SamplerDescription.builder().setFilter(SamplerDescription.Filter.NEAREST).build());
    }

    public void upload() {
        if (this.image == null) {
            LOGGER.error("Cannot upload a closed texture ({})", getTextureLocation());
            return;
        }
        if (deviceTexture().isClosed()) {
            initializeHandle(this.image);
        }
        this.laby3D.writeToTexture(deviceTexture(), this.image);
    }

    public void setImageAndUpload(GameImage image) {
        setImage(image);
        upload();
    }

    public void setImage(GameImage image) {
        if (this.image == image) {
            return;
        }
        boolean changedSize = this.image == null;
        if (this.image != null) {
            int width = this.image.getWidth();
            int height = this.image.getHeight();
            if (width != image.getWidth() || height != image.getHeight()) {
                changedSize = true;
            }
            this.image.close();
        }
        this.image = image;
        if (changedSize) {
            deviceTexture().close();
        }
    }

    private void initializeHandle(GameImage image) {
        RenderDevice renderDevice = this.laby3D.renderDevice();
        this.texture = renderDevice.createTexture(getTextureLocation().toString(), this.samplerDescription, DeviceTexture.Format.R8G8B8A8_UNORM, DeviceTexture.Dimension.TEXTURE_2D, image.getWidth(), image.getHeight(), 1);
        this.textureView = renderDevice.createTextureView(this.texture);
    }

    @Override // net.labymod.api.client.resources.texture.LabyTexture
    @Nullable
    public GameImage getImage() {
        return this.image;
    }

    @Override // net.labymod.api.client.resources.texture.LabyTexture, java.lang.AutoCloseable
    public void close() {
        if (this.image != null) {
            this.image.close();
            this.image = null;
            super.close();
        }
    }
}
