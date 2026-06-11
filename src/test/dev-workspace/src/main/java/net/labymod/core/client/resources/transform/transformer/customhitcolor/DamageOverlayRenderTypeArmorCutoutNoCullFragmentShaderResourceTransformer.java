package net.labymod.core.client.resources.transform.transformer.customhitcolor;

import java.nio.charset.StandardCharsets;
import javax.inject.Singleton;
import net.labymod.api.client.resources.transform.ResourceTransformer;
import net.labymod.api.models.Implements;
import net.labymod.api.util.InjectionNames;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/transform/transformer/customhitcolor/DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer.class */
@Singleton
@Implements(value = ResourceTransformer.class, key = InjectionNames.DAMAGE_OVERLAY_RENDERTYPE_ARMOR_CUTOUT_NO_CULL_FRAGMENT_SHADER)
public class DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer implements ResourceTransformer {
    @Override // net.labymod.api.client.resources.transform.ResourceTransformer
    public byte[] transform(byte[] resourceData) {
        String content = new String(resourceData);
        String[] lines = content.split("\n");
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.startsWith("in vec4 vertexColor;")) {
                builder.append("in vec4 overlayColor;").append("\n");
            }
            if (trimmedLine.startsWith("fragColor = linear_fog")) {
                builder.append("color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);").append("\n");
            }
            builder.append(line).append("\n");
        }
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
