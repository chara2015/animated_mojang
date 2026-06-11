package net.labymod.api.client.gfx.pipeline.post.data;

import com.google.gson.annotations.SerializedName;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.window.Window;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.pipeline.target.RenderTargetDescription;
import net.labymod.laby3d.api.pipeline.target.attachment.AttachmentType;
import net.labymod.laby3d.api.pipeline.target.attachment.ClearValue;
import net.labymod.laby3d.api.pipeline.target.attachment.RenderTargetAttachmentDescription;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/PostProcessorTarget.class */
public interface PostProcessorTarget {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/PostProcessorTarget$Filter.class */
    public enum Filter {
        LINEAR,
        NEAREST
    }

    String getName();

    Filter filter();

    RenderTarget create(RenderDevice renderDevice);

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    default SamplerDescription.Filter samplerFilter() throws MatchException {
        switch (filter()) {
            case LINEAR:
                return SamplerDescription.Filter.LINEAR;
            case NEAREST:
                return SamplerDescription.Filter.NEAREST;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/PostProcessorTarget$FullSizeTarget.class */
    public static class FullSizeTarget implements PostProcessorTarget {

        @SerializedName("name")
        private String name;

        @SerializedName("filter")
        private Filter filter = Filter.NEAREST;

        public FullSizeTarget(String name) {
            this.name = name;
        }

        @Override // net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget
        public String getName() {
            return this.name;
        }

        @Override // net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget
        public Filter filter() {
            return this.filter;
        }

        @Override // net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget
        public RenderTarget create(RenderDevice renderDevice) {
            Window window = Laby.labyAPI().minecraft().minecraftWindow();
            return renderDevice.createTarget(getName(), RenderTargetDescription.builder().addColorAttachment(RenderTargetAttachmentDescription.builder().setSamplerDescription(builder -> {
                builder.setFilter(samplerFilter());
            }).setType(AttachmentType.COLOR).setFormat(DeviceTexture.Format.R8G8B8A8_UNORM).setClearValue(ClearValue.color(0.0f, 0.0f, 0.0f, 0.0f)).setName("Color-" + getName()).build()).setSize(window.getRawWidth(), window.getRawHeight()).build());
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/PostProcessorTarget$CustomSizeTarget.class */
    public static class CustomSizeTarget implements PostProcessorTarget {

        @SerializedName("name")
        private String name;

        @SerializedName("filter")
        private Filter filter = Filter.NEAREST;

        @SerializedName("width")
        private int width;

        @SerializedName("height")
        private int height;

        public CustomSizeTarget(String name, int width, int height) {
            this.name = name;
            this.width = width;
            this.height = height;
        }

        @Override // net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget
        public String getName() {
            return this.name;
        }

        @Override // net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget
        public Filter filter() {
            return this.filter;
        }

        @Override // net.labymod.api.client.gfx.pipeline.post.data.PostProcessorTarget
        public RenderTarget create(RenderDevice renderDevice) {
            return renderDevice.createTarget(getName(), RenderTargetDescription.builder().addColorAttachment(RenderTargetAttachmentDescription.builder().setSamplerDescription(builder -> {
                builder.setFilter(samplerFilter());
            }).setType(AttachmentType.COLOR).setName("Color-" + getName()).setClearValue(ClearValue.color(0.0f, 0.0f, 0.0f, 0.0f)).setFormat(DeviceTexture.Format.R8G8B8A8_UNORM).build()).setSize(this.width, this.height).build());
        }
    }
}
