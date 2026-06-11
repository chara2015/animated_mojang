package net.labymod.v1_19_4.laby3d.pipeline;

import java.io.IOException;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.Util;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/laby3d/pipeline/LabyShaderInstance.class */
public class LabyShaderInstance extends fir {
    public LabyShaderInstance(alk provider, ResourceLocation name, ehj format) throws IOException {
        super(provider, name.toString(), format);
    }

    public static ResourceLocation rewriteId(String input, String targetId) {
        return Util.rewriteShaderId(input, targetId);
    }
}
