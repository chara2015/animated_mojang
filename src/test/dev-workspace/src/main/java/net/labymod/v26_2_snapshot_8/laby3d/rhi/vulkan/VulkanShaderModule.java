package net.labymod.v26_2_snapshot_8.laby3d.rhi.vulkan;

import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.shaders.ShaderType;
import net.labymod.laby3d.api.rhi.ResourceCreationException;
import net.labymod.laby3d.api.rhi.shader.ShaderBundle;
import net.labymod.laby3d.api.rhi.shader.ShaderModule;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/rhi/vulkan/VulkanShaderModule.class */
class VulkanShaderModule implements ShaderModule {
    private final Identifier id;
    private final String vertexSource;
    private final String fragmentSource;
    private boolean closed;

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.rhi.ResourceCreationException */
    VulkanShaderModule(ShaderBundle bundle) throws ResourceCreationException, MatchException {
        if (!(bundle instanceof ShaderBundle.GlslSource)) {
            throw new ResourceCreationException("Vulkan backend only supports GlslSource bundles, got " + bundle.getClass().getName());
        }
        ShaderBundle.GlslSource glslSource = (ShaderBundle.GlslSource) bundle;
        try {
            String name = glslSource.name();
            String vs = glslSource.vertexSource();
            String fs = glslSource.fragmentSource();
            this.id = Identifier.parse("laby3d:rhi/" + VulkanConverters.sanitizeIdentifierPath(name));
            this.vertexSource = vs;
            this.fragmentSource = fs;
        } catch (Throwable th) {
            throw new MatchException(th.toString(), th);
        }
    }

    Identifier id() {
        return this.id;
    }

    ShaderSource asShaderSource() {
        return (id, type) -> {
            if (id.equals(this.id)) {
                return type == ShaderType.VERTEX ? this.vertexSource : this.fragmentSource;
            }
            return null;
        };
    }

    public void close() {
        this.closed = true;
    }
}
