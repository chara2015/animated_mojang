package net.labymod.core.client.resources.transform.transformer.customhitcolor;

import java.nio.charset.StandardCharsets;
import javax.inject.Singleton;
import net.labymod.api.client.resources.transform.ResourceTransformer;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.Implements;
import net.labymod.api.util.InjectionNames;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/transform/transformer/customhitcolor/DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer.class */
@Singleton
@Implements(value = ResourceTransformer.class, key = InjectionNames.DAMAGE_OVERLAY_RENDERTYPE_ARMOR_CUTOUT_NO_CULL_VERTEX_SHADER)
public class DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer implements ResourceTransformer {
    @Override // net.labymod.api.client.resources.transform.ResourceTransformer
    public byte[] transform(byte[] resourceData) {
        String content = new String(resourceData);
        String[] lines = content.split("\n");
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            String trimmedLine = line.trim();
            boolean isSampler2 = trimmedLine.startsWith("uniform sampler2D Sampler2;");
            if (isSampler2) {
                builder.append("uniform sampler2D Sampler1;").append("\n");
            }
            if (trimmedLine.startsWith("in vec2 UV1;")) {
                line = "in ivec2 UV1;";
            }
            builder.append(line).append("\n");
            if (isSampler2) {
                builder.append("uniform vec3 customHitColor;").append("\n");
            }
            if (trimmedLine.startsWith("vertexColor = minecraft_mix_light")) {
                builder.append("overlayColor = texelFetch(Sampler1, UV1, 0);").append("\n");
            }
            if (MinecraftVersions.V23w05a.orNewer()) {
                if (trimmedLine.startsWith("out vec2 texCoord1")) {
                    builder.append("out vec4 overlayColor;").append("\n");
                }
            } else if (trimmedLine.startsWith("out vec4 normal;")) {
                builder.append("out vec4 overlayColor;").append("\n");
            }
        }
        return builder.toString().getBytes(StandardCharsets.UTF_8);
    }
}
