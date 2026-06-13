package net.labymod.api.laby3d.util;

import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.util.Pool;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.pipeline.target.RenderTargetDescription;
import net.labymod.laby3d.api.pipeline.target.attachment.AttachmentType;
import net.labymod.laby3d.api.pipeline.target.attachment.ClearValue;
import net.labymod.laby3d.api.pipeline.target.attachment.RenderTargetAttachmentDescription;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/util/RenderTargetPool.class */
public class RenderTargetPool extends Pool<RenderTarget> {
    private final Laby3D laby3D;
    private static int counter;
    private RenderTarget sourceTarget;

    public RenderTargetPool(Laby3D laby3D) {
        this.laby3D = laby3D;
    }

    public RenderTarget acquire(RenderTarget sourceTarget) {
        try {
            this.sourceTarget = sourceTarget;
            return (RenderTarget) super.acquire();
        } finally {
            this.sourceTarget = null;
        }
    }

    @Override // net.labymod.api.laby3d.util.Pool
    public void onClose(Pool.Entry<RenderTarget> entry) {
        super.onClose(entry);
        entry.getValue().close();
    }

    @Override // net.labymod.api.laby3d.util.Pool
    protected boolean validateEntry(Pool.Entry<RenderTarget> entry) {
        RenderTarget value = entry.getValue();
        return value.width() == this.sourceTarget.width() && value.height() == this.sourceTarget.height();
    }

    @Override // net.labymod.api.laby3d.util.Pool
    protected Pool.Entry<RenderTarget> createEntry() {
        int id = counter;
        counter = id + 1;
        RenderTargetDescription description = RenderTargetDescription.builder().setSize(this.sourceTarget.width(), this.sourceTarget.height()).addColorAttachment(RenderTargetAttachmentDescription.builder().setName("Color" + id).setSamplerDescription(builder -> {
            builder.setFilter(SamplerDescription.Filter.LINEAR);
        }).setClearValue(ClearValue.color(0.0f, 0.0f, 0.0f, 0.0f)).setFormat(DeviceTexture.Format.R8G8B8A8_UNORM).setType(AttachmentType.COLOR).build()).build();
        return new Pool.Entry<>(this.laby3D.renderDevice().createTarget("Dynamic Target " + id, description));
    }
}
