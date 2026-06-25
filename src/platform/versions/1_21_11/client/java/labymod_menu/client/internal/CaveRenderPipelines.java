package labymod_menu.client.internal;

import labymod_menu.client.LabyModMenuClient;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.resources.Identifier;

final class CaveRenderPipelines {
	private static final RenderPipeline.Snippet CAVE = RenderPipeline.builder()
			.withVertexShader(shader("core/cave"))
			.withFragmentShader(shader("core/cave"))
			.withSampler("Sampler0")
			.withUniform("DynamicTransforms", UniformType.UNIFORM_BUFFER)
			.withUniform("Projection", UniformType.UNIFORM_BUFFER)
			.withUniform("Schematic", UniformType.UNIFORM_BUFFER)
			.withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL, VertexFormat.Mode.QUADS)
			.withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
			.withDepthWrite(true)
			.buildSnippet();

	static final RenderPipeline SOLID = RenderPipeline.builder(CAVE)
			.withLocation(location("solid"))
			.build();
	static final RenderPipeline CUTOUT = RenderPipeline.builder(CAVE)
			.withLocation(location("cutout"))
			.withShaderDefine("ALPHA_CUTOUT", 0.5F)
			.build();
	static final RenderPipeline TRANSLUCENT = RenderPipeline.builder(CAVE)
			.withLocation(location("translucent"))
			.withShaderDefine("ALPHA_CUTOUT", 0.01F)
			.withBlend(BlendFunction.TRANSLUCENT)
			.withDepthWrite(false)
			.build();
	static final RenderPipeline LINEAR_BLIT = RenderPipeline.builder()
			.withLocation(location("linear_blit"))
			.withVertexShader("core/screenquad")
			.withFragmentShader("core/blit_screen")
			.withSampler("InSampler")
			.withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES)
			.build();

	private CaveRenderPipelines() {
	}

	private static Identifier shader(String path) {
		return Identifier.fromNamespaceAndPath(LabyModMenuClient.MOD_ID, path);
	}

	private static Identifier location(String name) {
		return Identifier.fromNamespaceAndPath(LabyModMenuClient.MOD_ID, "pipeline/cave_" + name);
	}
}
