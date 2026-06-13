package net.labymod.api.laby3d.textures.opengl;

import net.labymod.api.Laby;
import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture;
import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTextureRegistry;
import net.labymod.api.util.lang.Runnables;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/textures/opengl/DeferredDeviceTexture.class */
public class DeferredDeviceTexture implements DeviceTexture, GlResource {
    private final int id;
    private final DeviceTexture texture;
    private final SamplerDescription samplerDescription;
    private final Runnable flushAction;

    private DeferredDeviceTexture(int id, DeviceTexture texture, SamplerDescription samplerDescription, Runnable flushAction) {
        this.id = id;
        this.texture = texture;
        this.samplerDescription = samplerDescription;
        this.flushAction = flushAction;
    }

    @NotNull
    public static DeviceTexture createDeferredTexture(int id) {
        return createDeferredTexture(id, Runnables.doNothing());
    }

    @NotNull
    public static DeviceTexture createDeferredTexture(int id, Runnable flushAction) {
        ForeignDeviceTextureRegistry registry = Laby.references().laby3D().foreignDeviceTextureRegistry();
        ForeignDeviceTexture deviceTexture = registry.get(Integer.valueOf(id));
        if (deviceTexture == null) {
            throw new IllegalStateException("Foreign device texture not found for id: " + id);
        }
        return createDeferredTexture(id, deviceTexture, flushAction);
    }

    @NotNull
    public static DeviceTexture createDeferredTexture(int id, DeviceTexture texture) {
        return createDeferredTexture(id, texture, Runnables.doNothing());
    }

    @NotNull
    public static DeviceTexture createDeferredTexture(int id, DeviceTexture texture, @NotNull Runnable flushAction) {
        return createDeferredTexture(id, texture, null, flushAction);
    }

    @NotNull
    public static DeviceTexture createDeferredTexture(int id, DeviceTexture texture, @Nullable SamplerDescription samplerDescription, @NotNull Runnable flushAction) {
        return new DeferredDeviceTexture(id, texture, samplerDescription, flushAction);
    }

    @NotNull
    public SamplerDescription samplerDescription() {
        if (this.samplerDescription == null) {
            return this.texture.samplerDescription();
        }
        return this.samplerDescription;
    }

    @NotNull
    public DeviceTexture.Format format() {
        return this.texture.format();
    }

    @NotNull
    public DeviceTexture.Dimension dimension() {
        return this.texture.dimension();
    }

    public int width() {
        return this.texture.width();
    }

    public int width(int level) {
        return this.texture.width(level);
    }

    public int height() {
        return this.texture.height();
    }

    public int height(int level) {
        return this.texture.height(level);
    }

    public int layers() {
        return this.texture.layers();
    }

    public boolean isClosed() {
        return this.texture.isClosed();
    }

    public void close() {
        this.texture.close();
    }

    @NotNull
    public String label() {
        return this.texture.label();
    }

    public int getId() {
        return this.id;
    }

    public void flushChanges() {
        super.flushChanges();
        this.flushAction.run();
    }

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeferredDeviceTexture that = (DeferredDeviceTexture) o;
        return this.id == that.id;
    }

    public int hashCode() {
        return this.id;
    }
}
