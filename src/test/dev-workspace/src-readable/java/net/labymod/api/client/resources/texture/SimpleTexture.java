package net.labymod.api.client.resources.texture;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/SimpleTexture.class */
@ApiStatus.Internal
public class SimpleTexture extends LabyTexture {
    private final TextureDescription description;
    private GameImage image;
    private boolean uploaded;

    private SimpleTexture(TextureDescription description, @Nullable GameImage image) {
        super(description.location());
        this.description = description;
        this.image = image;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static SimpleTexture simple(ResourceLocation location) {
        return simple(location, null);
    }

    public static SimpleTexture simple(ResourceLocation location, @Nullable GameImage image) {
        return builder().setSamplerDescription(SamplerDescription.builder().setFilter(SamplerDescription.Filter.NEAREST).build()).build(location, image);
    }

    public void upload() {
        if (this.uploaded) {
            return;
        }
        loadImage();
        RenderDevice renderDevice = this.laby3D.renderDevice();
        this.texture = renderDevice.createTexture(getTextureLocation().toString(), this.description.samplerDescription(), this.description.format(), this.description.dimension(), this.image.getWidth(), this.image.getHeight(), 1);
        this.textureView = renderDevice.createTextureView(this.texture);
        this.laby3D.writeToTexture(deviceTexture(), this.image);
        this.uploaded = true;
    }

    private void loadImage() {
        if (this.image != null) {
            return;
        }
        try {
            InputStream stream = getTextureLocation().openStream();
            try {
                this.image = GameImage.IMAGE_PROVIDER.getImage(stream);
                if (stream != null) {
                    stream.close();
                }
            } finally {
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load image (" + String.valueOf(getTextureLocation()) + ")", exception);
        }
    }

    @Override // net.labymod.api.client.resources.texture.LabyTexture
    public GameImage getImage() {
        return this.image;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/SimpleTexture$Builder.class */
    public static class Builder {
        private String debugName;
        private SamplerDescription description;
        private DeviceTexture.Format format = DeviceTexture.Format.R8G8B8A8_UNORM;
        private DeviceTexture.Dimension dimension = DeviceTexture.Dimension.TEXTURE_2D;

        private Builder() {
        }

        public Builder setDebugName(String debugName) {
            this.debugName = debugName;
            return this;
        }

        public Builder setSamplerDescription(SamplerDescription description) {
            this.description = description;
            return this;
        }

        public Builder setDimension(DeviceTexture.Dimension dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setFormat(DeviceTexture.Format format) {
            this.format = format;
            return this;
        }

        public SimpleTexture build(ResourceLocation location, GameImage image) {
            if (this.format == null) {
                throw new IllegalArgumentException("format must not be null");
            }
            if (this.dimension == null) {
                throw new IllegalArgumentException("dimension must not be null");
            }
            if (this.description == null) {
                throw new IllegalArgumentException("description must not be null");
            }
            TextureDescription textureDescription = new TextureDescription(location, this.debugName, this.format, this.dimension, this.description);
            return new SimpleTexture(textureDescription, image);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/SimpleTexture$TextureDescription.class */
    static final class TextureDescription extends Record {
        private final ResourceLocation location;

        @Nullable
        private final String debugName;
        private final DeviceTexture.Format format;
        private final DeviceTexture.Dimension dimension;
        private final SamplerDescription samplerDescription;

        TextureDescription(ResourceLocation location, @Nullable String debugName, DeviceTexture.Format format, DeviceTexture.Dimension dimension, SamplerDescription samplerDescription) {
            this.location = location;
            this.debugName = debugName;
            this.format = format;
            this.dimension = dimension;
            this.samplerDescription = samplerDescription;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TextureDescription.class), TextureDescription.class, "location;debugName;format;dimension;samplerDescription", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->location:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->debugName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->format:Lnet/labymod/laby3d/api/textures/DeviceTexture$Format;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->dimension:Lnet/labymod/laby3d/api/textures/DeviceTexture$Dimension;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->samplerDescription:Lnet/labymod/laby3d/api/textures/SamplerDescription;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TextureDescription.class), TextureDescription.class, "location;debugName;format;dimension;samplerDescription", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->location:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->debugName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->format:Lnet/labymod/laby3d/api/textures/DeviceTexture$Format;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->dimension:Lnet/labymod/laby3d/api/textures/DeviceTexture$Dimension;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->samplerDescription:Lnet/labymod/laby3d/api/textures/SamplerDescription;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TextureDescription.class, Object.class), TextureDescription.class, "location;debugName;format;dimension;samplerDescription", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->location:Lnet/labymod/api/client/resources/ResourceLocation;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->debugName:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->format:Lnet/labymod/laby3d/api/textures/DeviceTexture$Format;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->dimension:Lnet/labymod/laby3d/api/textures/DeviceTexture$Dimension;", "FIELD:Lnet/labymod/api/client/resources/texture/SimpleTexture$TextureDescription;->samplerDescription:Lnet/labymod/laby3d/api/textures/SamplerDescription;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public ResourceLocation location() {
            return this.location;
        }

        @Nullable
        public String debugName() {
            return this.debugName;
        }

        public DeviceTexture.Format format() {
            return this.format;
        }

        public DeviceTexture.Dimension dimension() {
            return this.dimension;
        }

        public SamplerDescription samplerDescription() {
            return this.samplerDescription;
        }

        public String getResolvedName() {
            return this.debugName == null ? this.location.toString() : this.debugName;
        }
    }
}
