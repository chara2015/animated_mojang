package net.labymod.v1_21_8.client.gfx.pipeline.blaze3d.program;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.laby3d.api.opengl.GlFunctions;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/gfx/pipeline/blaze3d/program/TextureBindingSetRenderStateShard.class */
public class TextureBindingSetRenderStateShard extends gxy {
    private final TextureBindingSet bindingSet;

    public TextureBindingSetRenderStateShard(TextureBindingSet bindingSet) {
        super("TextureBindingSet", () -> {
            DeviceTextureView[] textures = bindingSet.textures();
            for (int index = 0; index < textures.length; index++) {
                GFXBridge gfx = Laby.references().gfxRenderPipeline().gfx();
                Laby3D laby3D = Laby.references().laby3D();
                GlFunctions functions = laby3D.renderDevice().functions();
                functions.activeTexture(GlConst.GL_TEXTURE0 + index);
                gfx.bindTexture2D(textures[index]);
            }
        }, () -> {
        });
        this.bindingSet = bindingSet;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass() || !super/*java.lang.Object*/.equals(object)) {
            return false;
        }
        TextureBindingSetRenderStateShard that = (TextureBindingSetRenderStateShard) object;
        return Objects.equals(this.bindingSet, that.bindingSet);
    }

    public int hashCode() {
        int result = super/*java.lang.Object*/.hashCode();
        return (31 * result) + Objects.hashCode(this.bindingSet);
    }
}
