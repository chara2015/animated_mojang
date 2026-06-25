package labymod_menu.client.internal;

import labymod_menu.client.LabyModMenuClient;
import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DynamicUniforms;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

final class SchematicScene {
	private static final String NORMAL_PATH = "assets/labymod_menu/data/normal_cave.schem";
	private static final SchematicScene EMPTY = new SchematicScene(1, 1, 1, new String[1], new float[1],
			new float[1], new float[1], new float[1], List.of(), List.of(), List.of(), List.of(), List.of());
	private static final Identifier BLOCK_TEXTURE = Identifier.fromNamespaceAndPath(
			LabyModMenuClient.MOD_ID, "textures/gui/title/cave_blocks.png");
	private static final Identifier LAVA_TEXTURE = Identifier.fromNamespaceAndPath(
			LabyModMenuClient.MOD_ID, "textures/gui/title/cave_lava_flow.png");
	private static final Identifier PARTICLE_TEXTURE = Identifier.fromNamespaceAndPath(
			LabyModMenuClient.MOD_ID, "textures/gui/title/cave_particles_clean.png");
	private static final int ATLAS_SIZE = 256;
	private static final int TILE_SIZE = 16;
	private static final int PARTICLE_ATLAS_SIZE = 128;
	private static final int PARTICLE_FLAME_U = 0;
	private static final int PARTICLE_FLAME_V = 24;
	private static final int PARTICLE_SOUL_FLAME_V = 32;
	private static final int PARTICLE_FLAME_SIZE = 8;
	private static final int PARTICLE_SMOKE_U = 0;
	private static final int PARTICLE_SMOKE_V = 0;
	private static final int PARTICLE_SMOKE_SIZE = 8;
	private static final int PARTICLE_SMOKE_FRAMES = 8;
	private static final int LAVA_ATLAS_WIDTH = 32;
	private static final int LAVA_ATLAS_HEIGHT = 512;
	private static final int LAVA_FRAMES = 32;
	private static final int CAMERA_BUCKETS = 48;
	private static final int LAVA_UPDATE_TICKS = 8;
	private static final int TORCH_PARTICLE_INTERVAL = 5;
	private static final int FURNACE_PARTICLE_INTERVAL = 8;
	private static final float PARTICLE_WORLD_SCALE = 0.1F;
	private static final float PARTICLE_PROJECTION_SCALE = 1.0F / (2.0F * (float) Math.tan(25.0F * Mth.DEG_TO_RAD));
	private static final float LABY_BRIGHTNESS = 2.0F;
	private static final float LABY_RESOLUTION = 5.0F;
	private static final int MAX_SHADER_LIGHTS = 48;
	private static final int SCHEMATIC_UBO_SIZE = 16 + MAX_SHADER_LIGHTS * 16 * 5;

	private final int width;
	private final int height;
	private final int length;
	private final String[] blockStates;
	private final float[] blockLights;
	private final float[] blockRedLights;
	private final float[] blockGreenLights;
	private final float[] blockBlueLights;
	private final List<BlockSample> wallBlocks;
	private final List<BlockSample> detailBlocks;
	private final List<MeshFace> meshFaces;
	private final List<LightPoint> lightPoints;
	private final List<TorchSample> torchSamples;
	private final List<CaveParticle> particles = new ArrayList<>();
	private final Random particleRandom = new Random();
	private int lastParticleTick = Integer.MIN_VALUE;
	private List<ProjectedFace> blockRenderCache = List.of();
	private List<ProjectedFace> lavaRenderCache = List.of();
	private int cachedScreenWidth = -1;
	private int cachedScreenHeight = -1;
	private int cachedCameraBucket = Integer.MIN_VALUE;
	private final ProjectionMatrixBuffer projectionBuffer = new ProjectionMatrixBuffer("labymod_menu cave projection");
	private LayerMesh blockMesh;
	private LayerMesh cutoutMesh;
	private LayerMesh lavaMesh;
	private int cachedLavaUpdateTick = -1;
	private CameraPose lastRenderedCameraPose;
	private final GpuBuffer schematicUniform = RenderSystem.getDevice().createBuffer(
			() -> "labymod_menu Schematic UBO", GpuBuffer.USAGE_UNIFORM | GpuBuffer.USAGE_COPY_DST,
			SCHEMATIC_UBO_SIZE);
	private final RenderTarget schematicTarget = new TextureTarget("labymod_menu schematic target", 1, 1, true);

	private static SchematicScene loaded;

	private SchematicScene(int width, int height, int length, String[] blockStates, float[] blockLights,
			float[] blockRedLights, float[] blockGreenLights, float[] blockBlueLights, List<BlockSample> wallBlocks,
			List<BlockSample> detailBlocks, List<MeshFace> meshFaces, List<LightPoint> lightPoints,
			List<TorchSample> torchSamples) {
		this.width = Math.max(1, width);
		this.height = Math.max(1, height);
		this.length = Math.max(1, length);
		this.blockStates = blockStates;
		this.blockLights = blockLights;
		this.blockRedLights = blockRedLights;
		this.blockGreenLights = blockGreenLights;
		this.blockBlueLights = blockBlueLights;
		this.wallBlocks = wallBlocks;
		this.detailBlocks = detailBlocks;
		this.meshFaces = meshFaces;
		this.lightPoints = new ArrayList<>(lightPoints);
		this.torchSamples = torchSamples;
	}

	static SchematicScene get() {
		if (loaded == null) {
			loaded = load(NORMAL_PATH);
		}

		return loaded;
	}

	void render(GuiGraphicsExtractor graphics, float camera, float time) {
		int screenWidth = graphics.guiWidth();
		int screenHeight = graphics.guiHeight();
		int cameraBucket = Math.round(camera * CAMERA_BUCKETS);
		if (screenWidth != cachedScreenWidth || screenHeight != cachedScreenHeight || cameraBucket != cachedCameraBucket) {
			List<ProjectedFace> projected = buildMeshCache(screenWidth, screenHeight, cameraBucket / (float) CAMERA_BUCKETS);
			List<ProjectedFace> blocks = new ArrayList<>(projected.size());
			List<ProjectedFace> lava = new ArrayList<>();
			for (ProjectedFace face : projected) {
				if (face.lava) {
					lava.add(face);
				} else {
					blocks.add(face);
				}
			}
			blockRenderCache = blocks;
			lavaRenderCache = lava;
			cachedScreenWidth = screenWidth;
			cachedScreenHeight = screenHeight;
			cachedCameraBucket = cameraBucket;
		}

		graphics.fill(0, 0, screenWidth, screenHeight, 0xFF07080D);
		AbstractTexture blockTexture = Minecraft.getInstance().getTextureManager().getTexture(BLOCK_TEXTURE);
		AbstractTexture lavaTexture = Minecraft.getInstance().getTextureManager().getTexture(LAVA_TEXTURE);
		ScreenRectangle bounds = new ScreenRectangle(0, 0, screenWidth, screenHeight);
		if (!blockRenderCache.isEmpty()) {
			graphics.guiRenderState.addGuiElement(new MeshRenderState(new Matrix3x2f(graphics.pose()), blockRenderCache,
					bounds, TextureSetup.singleTexture(blockTexture.getTextureView(), blockTexture.getSampler())));
		}
		if (!lavaRenderCache.isEmpty()) {
			graphics.guiRenderState.addGuiElement(new MeshRenderState(new Matrix3x2f(graphics.pose()), lavaRenderCache,
					bounds, TextureSetup.singleTexture(lavaTexture.getTextureView(), lavaTexture.getSampler())));
		}
	}

	void renderDirect(float camera, float time, float openingTime) {
		renderDirect(cameraPose(camera, openingTime), time);
	}

	void renderDirectAt(float x, float y, float z, float yaw, float pitch, float roll, float time, float openingTime) {
		renderDirect(cameraPoseWithWind(x, y, z, yaw, pitch, roll, openingTime), time);
	}

	private void renderDirect(CameraPose pose, float time) {
		lastRenderedCameraPose = pose;
		Minecraft minecraft = Minecraft.getInstance();
		RenderTarget mainTarget = minecraft.getMainRenderTarget();
		int screenWidth = minecraft.getWindow().getWidth();
		int screenHeight = minecraft.getWindow().getHeight();
		if (screenWidth <= 0 || screenHeight <= 0) {
			return;
		}
		float resolutionFactor = 1.0F + (10.0F - LABY_RESOLUTION) / 9.0F;
		int targetWidth = Math.max(1, (int) (screenWidth / resolutionFactor));
		int targetHeight = Math.max(1, (int) (screenHeight / resolutionFactor));
		if (schematicTarget.width != targetWidth || schematicTarget.height != targetHeight) {
			schematicTarget.resize(targetWidth, targetHeight);
		}
		RenderTarget target = schematicTarget;

		float aspect = targetWidth / (float) targetHeight;
		ensureLayerMeshes(time);
		uploadLighting(pose);
		if (target.useDepth) {
			RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(
					target.getColorTexture(), VOID_COLOR, target.getDepthTexture(), 1.0D);
		} else {
			RenderSystem.getDevice().createCommandEncoder().clearColorTexture(target.getColorTexture(), VOID_COLOR);
		}

		Matrix4f projection = new Matrix4f().perspective(50.0F * Mth.DEG_TO_RAD, aspect, 0.05F, 1024.0F);
		RenderSystem.backupProjectionMatrix();
		RenderSystem.setProjectionMatrix(projectionBuffer.getBuffer(projection), ProjectionType.PERSPECTIVE);
		DynamicUniforms dynamicUniforms = RenderSystem.getDynamicUniforms();
		var transform = dynamicUniforms.writeTransform(pose.viewMatrix, new Vector4f(1.0F, 1.0F, 1.0F, 1.0F),
				new Vector3f(), new Matrix4f());

		AbstractTexture blockTexture = minecraft.getTextureManager().getTexture(BLOCK_TEXTURE);
		AbstractTexture lavaTexture = minecraft.getTextureManager().getTexture(LAVA_TEXTURE);
		try (RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
				() -> "labymod_menu schematic", target.getColorTextureView(), java.util.OptionalInt.empty(),
				target.useDepth ? target.getDepthTextureView() : null, java.util.OptionalDouble.empty())) {
			RenderSystem.bindDefaultUniforms(pass);
			pass.setUniform("DynamicTransforms", transform);
			drawLayer(pass, blockMesh, CaveRenderPipelines.SOLID, blockTexture);
			drawLayer(pass, lavaMesh, CaveRenderPipelines.TRANSLUCENT, lavaTexture);
			drawLayer(pass, cutoutMesh, CaveRenderPipelines.CUTOUT, blockTexture);
		} finally {
			RenderSystem.restoreProjectionMatrix();
		}
		try (RenderPass pass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
				() -> "labymod_menu schematic linear blit", mainTarget.getColorTextureView(),
				java.util.OptionalInt.empty())) {
			pass.setPipeline(CaveRenderPipelines.LINEAR_BLIT);
			RenderSystem.bindDefaultUniforms(pass);
			pass.bindTexture("InSampler", schematicTarget.getColorTextureView(),
					RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR));
			pass.draw(0, 3);
		}
	}

	void renderTorchEffects(GuiGraphicsExtractor graphics, float camera, float time, float openingTime) {
		if (torchSamples.isEmpty() && particles.isEmpty()) {
			return;
		}
		int screenWidth = graphics.guiWidth();
		int screenHeight = graphics.guiHeight();
		float aspect = screenWidth / (float) Math.max(1, screenHeight);
		CameraPose pose = lastRenderedCameraPose != null ? lastRenderedCameraPose : cameraPose(camera, openingTime);
		tickParticles(pose, Math.max(0, (int) (time * 20.0F)));
		Matrix4f viewProjection = new Matrix4f().perspective(50.0F * Mth.DEG_TO_RAD, aspect, 0.05F, 1024.0F)
				.mul(pose.viewMatrix);
		for (CaveParticle particle : particles) {
			boolean partialOcclusion = particleNeedsPartialOcclusion(particle);
			if (!partialOcclusion && occludedByBlock(pose, particle.renderX(), particle.renderY(), particle.renderZ(),
					particle.sourceX, particle.sourceY, particle.sourceZ)) {
				continue;
			}
			ProjectedPoint point = project(viewProjection, particle.renderX(), particle.renderY(), particle.renderZ(),
					screenWidth, screenHeight);
			if (point == null || point.depth > 92.0F) {
				continue;
			}
			int x = Math.round(point.x);
			int y = Math.round(point.y);
			if (x < -20 || y < -20 || x > screenWidth + 20 || y > screenHeight + 20) {
				continue;
			}
			float progress = particle.progress();
			float sizeScale = particle.size * (1.0F - progress * progress * 0.5F);
			int size = Math.max(1, Math.round(screenHeight * PARTICLE_PROJECTION_SCALE
					* PARTICLE_WORLD_SCALE * sizeScale / point.depth));
			int textureU;
			int textureV;
			int textureSize;
			int color;
			if (particle.flame) {
				textureU = PARTICLE_FLAME_U;
				textureV = particle.soulFlame ? PARTICLE_SOUL_FLAME_V : PARTICLE_FLAME_V;
				textureSize = PARTICLE_FLAME_SIZE;
				color = 0xFFFFFFFF;
			} else {
				int gray = Mth.clamp(particle.color, 0, 255);
				int frame = particle.smokeFrame();
				textureU = PARTICLE_SMOKE_U + frame * PARTICLE_SMOKE_SIZE;
				textureV = PARTICLE_SMOKE_V;
				textureSize = PARTICLE_SMOKE_SIZE;
				color = 0xFF000000 | gray << 16 | gray << 8 | gray;
			}
			renderParticle(graphics, pose, particle, partialOcclusion, sizeScale, x, y, size,
					textureU, textureV, textureSize, color);
		}
	}

	private void renderParticle(GuiGraphicsExtractor graphics, CameraPose pose, CaveParticle particle,
			boolean partialOcclusion, float sizeScale, int centerX, int centerY, int size,
			int textureU, int textureV, int textureSize, int color) {
		if (!partialOcclusion) {
			graphics.blit(RenderPipelines.GUI_TEXTURED, PARTICLE_TEXTURE, centerX - size, centerY - size,
					textureU, textureV, size * 2, size * 2, textureSize, textureSize,
					PARTICLE_ATLAS_SIZE, PARTICLE_ATLAS_SIZE, color);
			return;
		}
		int diameter = size * 2;
		double radius = PARTICLE_WORLD_SCALE * sizeScale;
		for (int py = 0; py < diameter; py++) {
			double vertical = ((py + 0.5D) / diameter * 2.0D - 1.0D) * radius;
			for (int px = 0; px < diameter; px++) {
				double horizontal = ((px + 0.5D) / diameter * 2.0D - 1.0D) * radius;
				double worldX = particle.x + pose.rightX * horizontal - pose.upX * vertical;
				double worldY = particle.y - pose.upY * vertical;
				double worldZ = particle.z + pose.rightZ * horizontal - pose.upZ * vertical;
				if (occludedByBlock(pose, worldX, worldY, worldZ,
						particle.sourceX, particle.sourceY, particle.sourceZ)) {
					continue;
				}
				int sourceX = textureU + px * textureSize / diameter;
				int sourceY = textureV + py * textureSize / diameter;
				graphics.blit(RenderPipelines.GUI_TEXTURED, PARTICLE_TEXTURE, centerX - size + px, centerY - size + py,
						sourceX, sourceY, 1, 1, 1, 1, PARTICLE_ATLAS_SIZE, PARTICLE_ATLAS_SIZE, color);
			}
		}
	}

	private boolean particleNeedsPartialOcclusion(CaveParticle particle) {
		for (int y = Math.max(0, particle.sourceY - 2); y <= Math.min(height - 1, particle.sourceY + 2); y++) {
			for (int z = Math.max(0, particle.sourceZ - 2); z <= Math.min(length - 1, particle.sourceZ + 2); z++) {
				for (int x = Math.max(0, particle.sourceX - 2); x <= Math.min(width - 1, particle.sourceX + 2); x++) {
					String state = blockStates[index(width, length, x, y, z)];
					if (state != null && (state.contains("cobweb") || isFence(state))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private void tickParticles(CameraPose pose, int currentTick) {
		if (lastParticleTick == Integer.MIN_VALUE) {
			seedVisibleTorchParticles(pose);
			lastParticleTick = currentTick - 1;
		}
		int ticks = Mth.clamp(currentTick - lastParticleTick, 0, 4);
		for (int i = 0; i < ticks; i++) {
			for (int p = 0; p < particles.size(); p++) {
				CaveParticle particle = particles.get(p);
				particle.tick();
				if (particle.dead()) {
					particles.remove(p--);
				}
			}
			displayTick(pose, lastParticleTick + i + 1);
		}
		lastParticleTick = currentTick;
	}

	private void seedVisibleTorchParticles(CameraPose pose) {
		for (TorchSample torch : torchSamples) {
			double dx = torch.x + 0.5D - pose.x;
			double dy = torch.y + 0.7D - pose.y;
			double dz = torch.z + 0.5D - pose.z;
			if (!torch.furnace && dx * dx + dy * dy + dz * dz <= 72.0D * 72.0D) {
				spawnDisplayParticles(torch);
			}
		}
	}

	private void displayTick(CameraPose pose, int currentTick) {
		for (TorchSample sample : torchSamples) {
			double dx = sample.x + 0.5D - pose.x;
			double dy = sample.y + 0.7D - pose.y;
			double dz = sample.z + 0.5D - pose.z;
			if (dx * dx + dy * dy + dz * dz > 72.0D * 72.0D) {
				continue;
			}
			int interval = sample.furnace ? FURNACE_PARTICLE_INTERVAL : TORCH_PARTICLE_INTERVAL;
			if (Math.floorMod(currentTick + sample.hash, interval) == 0) {
				spawnDisplayParticles(sample);
			}
		}
	}

	private void spawnDisplayParticles(TorchSample sample) {
		if (sample.furnace) {
			spawnFurnaceParticles(sample.x, sample.y, sample.z, sample.facing);
		} else {
			spawnTorchParticles(sample.x, sample.y, sample.z, sample.facing, sample.soul);
		}
	}

	private void spawnTorchParticles(int x, int y, int z, String facing) {
		spawnTorchParticles(x, y, z, facing, false);
	}

	private void spawnTorchParticles(int x, int y, int z, String facing, boolean soul) {
		double centerX = x + 0.5D;
		double centerY = y + 0.7D;
		double centerZ = z + 0.5D;
		if ("east".equals(facing)) {
			centerX -= 0.2D;
		} else if ("west".equals(facing)) {
			centerX += 0.2D;
		} else if ("south".equals(facing)) {
			centerZ -= 0.2D;
		} else if ("north".equals(facing)) {
			centerZ += 0.2D;
		}
		particles.add(CaveParticle.smoke(centerX, centerY, centerZ, x, y, z, particleRandom));
		particles.add(CaveParticle.flame(centerX, centerY, centerZ, x, y, z, soul, particleRandom));
	}

	private void spawnFurnaceParticles(int x, int y, int z, String facing) {
		double centerX = x + 0.5D;
		double centerY = y + particleRandom.nextFloat() * 6.0F / 16.0F;
		double centerZ = z + 0.5D;
		double edgeOffset = particleRandom.nextFloat() * 0.6F - 0.3F;
		switch (facing) {
			case "east" -> centerX += 0.52D;
			case "west" -> centerX -= 0.52D;
			case "south" -> centerZ += 0.52D;
			case "north" -> centerZ -= 0.52D;
			default -> {
				return;
			}
		}
		if ("east".equals(facing) || "west".equals(facing)) {
			centerZ += edgeOffset;
		} else {
			centerX += edgeOffset;
		}
		particles.add(CaveParticle.smoke(centerX, centerY, centerZ, x, y, z, particleRandom));
		particles.add(CaveParticle.flame(centerX, centerY, centerZ, x, y, z, false, particleRandom));
	}

	private boolean occludedByBlock(CameraPose pose, double x, double y, double z,
			int sourceX, int sourceY, int sourceZ) {
		double dx = x - pose.x;
		double dy = y - pose.y;
		double dz = z - pose.z;
		double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
		if (distance <= 0.25D) {
			return false;
		}
		int steps = Math.max(1, (int) (distance * 16.0D));
		for (int i = 1; i < steps; i++) {
			double t = i / (double) steps;
			double sampleX = pose.x + dx * t;
			double sampleY = pose.y + dy * t;
			double sampleZ = pose.z + dz * t;
			int blockX = Mth.floor(sampleX);
			int blockY = Mth.floor(sampleY);
			int blockZ = Mth.floor(sampleZ);
			if (blockX == sourceX && blockY == sourceY && blockZ == sourceZ) {
				continue;
			}
			String state = blockStateAt(blockStates, width, height, length, blockX, blockY, blockZ);
			if (occludesParticle(state, sampleX - blockX, sampleY - blockY, sampleZ - blockZ)) {
				return true;
			}
		}
		return false;
	}

	private static boolean occludesParticle(String state, double x, double y, double z) {
		if (isFullBlockLikeLaby(state)) {
			return true;
		}
		if (state == null) {
			return false;
		}
		if (state.contains("cobweb")) {
			return Math.abs(x - z) < 0.055D || Math.abs(x + z - 1.0D) < 0.055D;
		}
		if (!isFence(state)) {
			return false;
		}
		boolean post = x >= 0.375D && x <= 0.625D && z >= 0.375D && z <= 0.625D;
		boolean railY = y >= 0.375D && y <= 0.5625D || y >= 0.75D && y <= 0.9375D;
		boolean northSouth = railY && x >= 0.4375D && x <= 0.5625D
				&& (z <= 0.5D && "true".equals(parameter(state, "north", "false"))
				|| z >= 0.5D && "true".equals(parameter(state, "south", "false")));
		boolean eastWest = railY && z >= 0.4375D && z <= 0.5625D
				&& (x <= 0.5D && "true".equals(parameter(state, "west", "false"))
				|| x >= 0.5D && "true".equals(parameter(state, "east", "false")));
		return post || northSouth || eastWest;
	}

	private static void closeMesh(LayerMesh mesh) {
		if (mesh != null && mesh.vertexBuffer != null) {
			mesh.vertexBuffer.close();
		}
	}

	private static final int VOID_COLOR = 0xFF08070B;

	private void drawLayer(RenderPass pass, LayerMesh mesh, RenderPipeline pipeline, AbstractTexture texture) {
		if (mesh == null || mesh.vertexBuffer == null || mesh.indexCount <= 0) {
			return;
		}
		pass.setPipeline(pipeline);
		pass.setVertexBuffer(0, mesh.vertexBuffer);
		pass.setUniform("Schematic", schematicUniform);
		pass.bindTexture("Sampler0", texture.getTextureView(), texture.getSampler());
		RenderSystem.AutoStorageIndexBuffer indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
		pass.setIndexBuffer(indexBuffer.getBuffer(mesh.indexCount), indexBuffer.type());
		pass.drawIndexed(0, 0, mesh.indexCount, 1);
	}

	private void ensureLayerMeshes(float time) {
		if (blockMesh == null) {
			blockMesh = buildLayerMesh(MeshLayer.SOLID, time);
		}
		if (cutoutMesh == null) {
			cutoutMesh = buildLayerMesh(MeshLayer.CUTOUT, time);
		}
		int lavaUpdateTick = Math.floorMod((int) (time * 20.0F) / LAVA_UPDATE_TICKS, Integer.MAX_VALUE);
		if (lavaMesh == null || cachedLavaUpdateTick != lavaUpdateTick) {
			if (lavaMesh != null && lavaMesh.vertexBuffer != null) {
				lavaMesh.vertexBuffer.close();
			}
			lavaMesh = buildLayerMesh(MeshLayer.LAVA, time);
			cachedLavaUpdateTick = lavaUpdateTick;
		}
	}

	private void uploadLighting(CameraPose pose) {
		List<LightPoint> selected = lightPoints.stream()
				.sorted(Comparator.comparingDouble(light -> light.selectionScore(pose.x, pose.y, pose.z)))
				.limit(MAX_SHADER_LIGHTS)
				.toList();
		try (MemoryStack stack = MemoryStack.stackPush()) {
			Std140Builder builder = Std140Builder.onStack(stack, SCHEMATIC_UBO_SIZE).putFloat(1.0F).align(16);
			for (int index = 0; index < MAX_SHADER_LIGHTS; index++) {
				LightPoint light = index < selected.size() ? selected.get(index) : LightPoint.INACTIVE;
				builder.putVec3(light.x, light.y, light.z);
			}
			for (int index = 0; index < MAX_SHADER_LIGHTS; index++) {
				LightPoint light = index < selected.size() ? selected.get(index) : LightPoint.INACTIVE;
				builder.putVec3(light.red, light.green, light.blue);
			}
			putScalarArray(builder, selected, LightComponent.CONSTANT);
			putScalarArray(builder, selected, LightComponent.LINEAR);
			putScalarArray(builder, selected, LightComponent.QUADRATIC);
			RenderSystem.getDevice().createCommandEncoder().writeToBuffer(schematicUniform.slice(), builder.get());
		}
	}

	private static void putScalarArray(Std140Builder builder, List<LightPoint> lights, LightComponent component) {
		for (int index = 0; index < MAX_SHADER_LIGHTS; index++) {
			LightPoint light = index < lights.size() ? lights.get(index) : LightPoint.INACTIVE;
			builder.putFloat(component.get(light)).align(16);
		}
	}

	private LayerMesh buildLayerMesh(MeshLayer layer, float time) {
		try (ByteBufferBuilder byteBuffer = new ByteBufferBuilder(Math.max(1024, meshFaces.size() * 128))) {
			BufferBuilder builder = new BufferBuilder(byteBuffer, VertexFormat.Mode.QUADS,
					DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL);
			for (MeshFace face : meshFaces) {
				if (!belongsToLayer(face, layer)) {
					continue;
				}
				float minFaceU = min(face.us);
				float maxFaceU = max(face.us);
				float minFaceV = min(face.vs);
				float maxFaceV = max(face.vs);
				for (int i = 0; i < 4; i++) {
					int color = materialColor(face.state, face.normalY, face.redLights[i],
							face.greenLights[i], face.blueLights[i],
							face.directionShade, 0.0F);
					float u = face.us[i];
					float v = face.vs[i];
					if (layer == MeshLayer.LAVA) {
						float[] lavaUv = uvForFaceLikeLaby(face.state, face.normalY,
								face.lavaStillTop ? 0 : lavaFrame(time));
						u = Math.abs(face.us[i] - minFaceU) <= Math.abs(face.us[i] - maxFaceU) ? lavaUv[0] : lavaUv[2];
						v = Math.abs(face.vs[i] - minFaceV) <= Math.abs(face.vs[i] - maxFaceV) ? lavaUv[1] : lavaUv[3];
					}
					builder.addVertex(face.xs[i], face.ys[i], face.zs[i])
							.setColor(color)
							.setUv(u, v)
							.setNormal(face.normalX, face.normalY, face.normalZ);
				}
			}
			MeshData meshData = builder.build();
			if (meshData == null) {
				return LayerMesh.EMPTY;
			}
			try (meshData) {
				GpuBuffer vertexBuffer = RenderSystem.getDevice().createBuffer(
						() -> "labymod_menu " + layer.name().toLowerCase() + " mesh",
						GpuBuffer.USAGE_VERTEX, meshData.vertexBuffer());
				return new LayerMesh(vertexBuffer, meshData.drawState().indexCount());
			}
		}
	}

	private static boolean belongsToLayer(MeshFace face, MeshLayer layer) {
		return switch (layer) {
			case SOLID -> !face.lava && !isCutoutMaterial(face.state);
			case CUTOUT -> !face.lava && isCutoutMaterial(face.state);
			case LAVA -> face.lava;
		};
	}

	private static float min(float[] values) {
		float min = Float.POSITIVE_INFINITY;
		for (float value : values) {
			min = Math.min(min, value);
		}
		return min;
	}

	private static float max(float[] values) {
		float max = Float.NEGATIVE_INFINITY;
		for (float value : values) {
			max = Math.max(max, value);
		}
		return max;
	}

	private static float average(float[] values) {
		if (values.length == 0) {
			return 0.0F;
		}
		float total = 0.0F;
		for (float value : values) {
			total += value;
		}
		return total / values.length;
	}

	private List<ProjectedFace> buildMeshCache(int screenWidth, int screenHeight, float camera) {
		float aspect = screenWidth / (float) screenHeight;
		CameraPose pose = cameraPose(camera);
		Matrix4f viewProjection = new Matrix4f()
				.perspective(50.0F * Mth.DEG_TO_RAD, aspect, 0.05F, 1024.0F)
				.mul(pose.viewMatrix);
		List<ProjectedFace> projected = new ArrayList<>(meshFaces.size());
		for (MeshFace face : meshFaces) {
			if (!face.lava && !isCrossPlane(face.state) && !isLikelyFacingCamera(face, pose)) {
				continue;
			}
			float[] sx = new float[4];
			float[] sy = new float[4];
			float depth = 0.0F;
			boolean visible = true;
			for (int i = 0; i < 4; i++) {
				ProjectedPoint point = project(viewProjection, face.xs[i], face.ys[i], face.zs[i],
						screenWidth, screenHeight);
				if (point == null) {
					visible = false;
					break;
				}
				sx[i] = point.x;
				sy[i] = point.y;
				depth += point.depth;
			}
			if (!visible) {
				continue;
			}
			if (outsideScreen(sx, sy, screenWidth, screenHeight)) {
				continue;
			}

			float fog = Mth.clamp((depth * 0.25F) / 72.0F, 0.0F, 1.0F);
			int color = materialColor(face.state, face.normalY, average(face.redLights), average(face.greenLights), average(face.blueLights),
					face.directionShade, fog);
			float[] us = face.us;
			float[] vs = face.vs;
			if (signedArea(sx, sy) > 0.0F) {
				sx = reverse(sx);
				sy = reverse(sy);
				us = reverse(us);
				vs = reverse(vs);
			}
			projected.add(new ProjectedFace(sx, sy, us, vs, depth * 0.25F, color, face.lava));
		}
		projected.sort(Comparator.comparingDouble(ProjectedFace::depth).reversed());
		return projected;
	}

	private static boolean isLikelyFacingCamera(MeshFace face, CameraPose pose) {
		float centerX = 0.0F;
		float centerY = 0.0F;
		float centerZ = 0.0F;
		for (int i = 0; i < 4; i++) {
			centerX += face.xs[i];
			centerY += face.ys[i];
			centerZ += face.zs[i];
		}
		centerX *= 0.25F;
		centerY *= 0.25F;
		centerZ *= 0.25F;
		float cameraVectorX = pose.x - centerX;
		float cameraVectorY = pose.y - centerY;
		float cameraVectorZ = pose.z - centerZ;
		return face.normalX * cameraVectorX + face.normalY * cameraVectorY + face.normalZ * cameraVectorZ > -0.02F;
	}

	private static boolean outsideScreen(float[] sx, float[] sy, int screenWidth, int screenHeight) {
		float minX = Math.min(Math.min(sx[0], sx[1]), Math.min(sx[2], sx[3]));
		float maxX = Math.max(Math.max(sx[0], sx[1]), Math.max(sx[2], sx[3]));
		float minY = Math.min(Math.min(sy[0], sy[1]), Math.min(sy[2], sy[3]));
		float maxY = Math.max(Math.max(sy[0], sy[1]), Math.max(sy[2], sy[3]));
		float margin = Math.max(screenWidth, screenHeight) * 0.08F;
		return maxX < -margin || minX > screenWidth + margin || maxY < -margin || minY > screenHeight + margin;
	}

	private static ProjectedPoint project(Matrix4f viewProjection, float x, float y, float z, int screenWidth,
			int screenHeight) {
		Vector4f clip = new Vector4f(x, y, z, 1.0F);
		viewProjection.transform(clip);
		if (clip.w <= 0.05F) {
			return null;
		}
		float ndcX = clip.x / clip.w;
		float ndcY = clip.y / clip.w;
		if (ndcX < -4.0F || ndcX > 4.0F || ndcY < -4.0F || ndcY > 4.0F) {
			return null;
		}
		float screenX = (ndcX * 0.5F + 0.5F) * screenWidth;
		float screenY = (0.5F - ndcY * 0.5F) * screenHeight;
		return new ProjectedPoint(screenX, screenY, clip.w);
	}

	private List<RenderCell> buildRenderCache(int screenWidth, int screenHeight, float camera) {
		int step = Mth.clamp(Math.min(screenWidth, screenHeight) / 280, 2, 4);
		float aspect = screenWidth / (float) screenHeight;
		float fov = 0.46F;
		CameraPose pose = cameraPose(camera);
		List<RenderCell> cells = new ArrayList<>((screenWidth / step + 1) * (screenHeight / step + 1));

		for (int y = 0; y < screenHeight; y += step) {
			float sy = ((y + step * 0.5F) / screenHeight - 0.5F) * -2.0F;
			for (int x = 0; x < screenWidth; x += step) {
				float sx = ((x + step * 0.5F) / screenWidth - 0.5F) * 2.0F * aspect;
				RayHit hit = trace(pose, sx * fov, sy * fov);
				if (hit == null) {
					continue;
				}

				float fog = Mth.clamp(hit.distance / 62.0F, 0.0F, 1.0F);
				float light = Mth.clamp(0.34F + hit.light * 0.5F - fog * 0.34F, 0.12F, 1.0F);
				int color = tintColor(Math.round(light * 255.0F));
				cells.add(new RenderCell(x, y, Math.min(screenWidth, x + step + 1), Math.min(screenHeight, y + step + 1),
						hit.u + hit.textureU, hit.v + hit.textureV, hit.sourceSize, hit.sourceSize, color, hit.lava));
			}
		}
		return cells;
	}

	private static void renderSamples(GuiGraphicsExtractor graphics, List<BlockSample> samples, float block, float zoom,
			float originX, float originY, float time, boolean detail) {
		for (BlockSample sample : samples) {
			int size = Math.max(2, Math.round(block * zoom * (detail ? 0.86F : 1.0F)));
			int x = Math.round(originX + sample.x * block * zoom);
			int y = Math.round(originY - sample.y * block * zoom + sample.z * block * zoom * 0.08F);
			if (x < -size || y < -size || x > graphics.guiWidth() + size || y > graphics.guiHeight() + size) {
				continue;
			}

			float depthShade = 0.38F + (sample.z / 64.0F) * 0.26F + sample.light * 0.38F;
			if (sample.lava) {
				depthShade += Mth.sin(time * 6.0F + sample.x) * 0.08F;
			}
			int color = tintColor(Math.round(depthShade * 255.0F));
			graphics.blit(RenderPipelines.GUI_TEXTURED, BLOCK_TEXTURE, x, y, sample.u, sample.v, size, size,
					TILE_SIZE, TILE_SIZE, ATLAS_SIZE, ATLAS_SIZE, color);
			if (!sample.lava && size > 4) {
				graphics.fill(x, y + size - 1, x + size, y + size, packAlpha(0x000000, 0.26F));
				graphics.fill(x, y, x + 1, y + size, packAlpha(0xFFFFFF, 0.06F));
			}
		}
	}

	private CameraPose cameraPose(float progress) {
		return cameraPose(progress, 0.0F);
	}

	private CameraPose cameraPose(float progress, float openingTime) {
		float eased = Mth.clamp(progress, 0.0F, 1.0F);
		float x = naturalCubic3(17.0F, 20.0F, 31.0F, eased);
		float y = naturalCubic3(13.0F, 12.8F, 13.0F, eased);
		float z = naturalCubic3(82.0F, 44.0F, 6.0F, eased);
		float yaw = naturalCubic3(0.0F, 8.0F, 21.0F, eased);
		float pitch = naturalCubic3(0.0F, -5.0F, -13.0F, eased);
		float roll = naturalCubic3(-90.0F, -10.0F, 0.0F, eased);
		return cameraPoseWithWind(x, y, z, yaw, pitch, roll, openingTime);
	}

	private CameraPose cameraPoseWithWind(float x, float y, float z, float yaw, float pitch, float roll,
			float openingTime) {
		float windTime = openingTime / 3.0F;
		float windStrength = 1.0F - (1.0F - windTime * windTime)
				* (float) Math.exp(-0.5F * windTime * windTime);
		float distanceToTitle = Math.abs(z - 6.0F);
		float wind = windStrength * (1.0F - Mth.clamp(distanceToTitle / 30.0F, 0.0F, 1.0F));
		float windRoll = -Mth.sin(windTime) * wind;
		float windPitch = Mth.sin(windTime) * wind;
		float windYaw = Mth.cos(windTime) * wind;
		float windY = (-(1.0F - wind) * wind) + Mth.cos(windTime * 2.0F) * wind / 5.0F;
		return cameraPoseAt(x, y - windY, z, yaw + windYaw, pitch + windPitch, roll + windRoll);
	}

	private CameraPose cameraPoseAt(float x, float y, float z, float yaw, float pitch, float roll) {
		float yawRadians = yaw * Mth.DEG_TO_RAD;
		float pitchRadians = pitch * Mth.DEG_TO_RAD;
		float rollRadians = roll * Mth.DEG_TO_RAD;
		Matrix4f viewMatrix = new Matrix4f()
				.rotateZ(rollRadians)
				.rotateX(pitchRadians)
				.rotateY(yawRadians)
				.rotateY(180.0F * Mth.DEG_TO_RAD)
				.translate(-x, -y, -z);
		float cosPitch = Mth.cos(pitchRadians);
		float forwardX = Mth.sin(yawRadians) * cosPitch;
		float forwardY = -Mth.sin(pitchRadians);
		float forwardZ = Mth.cos(yawRadians) * cosPitch;
		float rightX = Mth.cos(yawRadians);
		float rightY = 0.0F;
		float rightZ = -Mth.sin(yawRadians);
		float upX = -Mth.sin(yawRadians) * Mth.sin(pitchRadians);
		float upY = Mth.cos(pitchRadians);
		float upZ = -Mth.cos(yawRadians) * Mth.sin(pitchRadians);
		return new CameraPose(x, y, z, forwardX, forwardY, forwardZ, rightX, rightY, rightZ, upX, upY, upZ,
				viewMatrix);
	}

	private static float naturalCubic3(float first, float middle, float last, float progress) {
		float gamma0 = 0.5F;
		float gamma1 = 1.0F / (4.0F - gamma0);
		float gamma2 = 1.0F / (2.0F - gamma1);
		float delta0 = 3.0F * (middle - first) * gamma0;
		float delta1 = (3.0F * (last - first) - delta0) * gamma1;
		float delta2 = (3.0F * (last - middle) - delta1) * gamma2;
		float derivative2 = delta2;
		float derivative1 = delta1 - gamma1 * derivative2;
		float derivative0 = delta0 - gamma0 * derivative1;
		float scaled = progress * 2.0F;
		if (scaled < 1.0F) {
			return evalCubic(first, derivative0,
					3.0F * (middle - first) - 2.0F * derivative0 - derivative1,
					2.0F * (first - middle) + derivative0 + derivative1, scaled);
		}
		return evalCubic(middle, derivative1,
				3.0F * (last - middle) - 2.0F * derivative1 - derivative2,
				2.0F * (middle - last) + derivative1 + derivative2, scaled - 1.0F);
	}

	private static float evalCubic(float a, float b, float c, float d, float progress) {
		return ((d * progress + c) * progress + b) * progress + a;
	}

	private static float signedArea(float[] x, float[] y) {
		float area = 0.0F;
		for (int i = 0; i < 4; i++) {
			int next = (i + 1) & 3;
			area += x[i] * y[next] - y[i] * x[next];
		}
		return area;
	}

	private static float[] reverse(float[] values) {
		return new float[] { values[0], values[3], values[2], values[1] };
	}

	private RayHit trace(CameraPose pose, float sx, float sy) {
		float dx = pose.forwardX + pose.rightX * sx + pose.upX * sy;
		float dy = pose.forwardY + pose.upY * sy;
		float dz = pose.forwardZ + pose.rightZ * sx + pose.upZ * sy;
		float rayLength = Mth.sqrt(dx * dx + dy * dy + dz * dz);
		dx /= rayLength;
		dy /= rayLength;
		dz /= rayLength;

		float[] range = intersectVolume(pose.x, pose.y, pose.z, dx, dy, dz);
		if (range == null) {
			return null;
		}

		float startDistance = Math.max(0.0F, range[0]) + 0.001F;
		int x = Mth.clamp(Mth.floor(pose.x + dx * startDistance), 0, width - 1);
		int y = Mth.clamp(Mth.floor(pose.y + dy * startDistance), 0, height - 1);
		int z = Mth.clamp(Mth.floor(pose.z + dz * startDistance), 0, length - 1);
		int stepX = dx > 0.0F ? 1 : -1;
		int stepY = dy > 0.0F ? 1 : -1;
		int stepZ = dz > 0.0F ? 1 : -1;
		float tMaxX = firstBoundaryDistance(pose.x, dx, x, stepX);
		float tMaxY = firstBoundaryDistance(pose.y, dy, y, stepY);
		float tMaxZ = firstBoundaryDistance(pose.z, dz, z, stepZ);
		float tDeltaX = deltaDistance(dx);
		float tDeltaY = deltaDistance(dy);
		float tDeltaZ = deltaDistance(dz);
		while (tMaxX <= startDistance) {
			tMaxX += tDeltaX;
		}
		while (tMaxY <= startDistance) {
			tMaxY += tDeltaY;
		}
		while (tMaxZ <= startDistance) {
			tMaxZ += tDeltaZ;
		}
		float distance = startDistance;
		int faceAxis = 2;
		int faceSign = -1;

		while (distance <= range[1] && x >= 0 && y >= 0 && z >= 0 && x < width && y < height && z < length) {
			int index = index(width, length, x, y, z);
			String state = blockStates[index];
			if (state != null) {
				MaterialTile tile = tileFor(state);
				float px = pose.x + dx * distance;
				float py = pose.y + dy * distance;
				float pz = pose.z + dz * distance;
				float faceU = faceAxis == 0 ? pz - Mth.floor(pz) : px - Mth.floor(px);
				float faceV = faceAxis == 1 ? pz - Mth.floor(pz) : py - Mth.floor(py);
				if (faceAxis == 0 && faceSign > 0 || faceAxis == 2 && faceSign < 0) {
					faceU = 1.0F - faceU;
				}
				faceV = 1.0F - faceV;
				int textureU = Mth.clamp((int) (faceU * TILE_SIZE), 0, TILE_SIZE - 1);
				int textureV = Mth.clamp((int) (faceV * TILE_SIZE), 0, TILE_SIZE - 1);
				int sourceSize = Math.max(1, TILE_SIZE / 8);
				return new RayHit(x, y, z, distance, tile.u, tile.v, textureU, textureV, sourceSize,
						Math.max(blockLights[index], lightFor(state)), state.contains("lava"));
			}

			if (tMaxX < tMaxY && tMaxX < tMaxZ) {
				distance = tMaxX;
				tMaxX += tDeltaX;
				x += stepX;
				faceAxis = 0;
				faceSign = -stepX;
			} else if (tMaxY < tMaxZ) {
				distance = tMaxY;
				tMaxY += tDeltaY;
				y += stepY;
				faceAxis = 1;
				faceSign = -stepY;
			} else {
				distance = tMaxZ;
				tMaxZ += tDeltaZ;
				z += stepZ;
				faceAxis = 2;
				faceSign = -stepZ;
			}
		}

		return null;
	}

	private float[] intersectVolume(float ox, float oy, float oz, float dx, float dy, float dz) {
		float tMin = 0.0F;
		float tMax = 96.0F;
		float[] xRange = intersectAxis(ox, dx, 0.0F, width);
		float[] yRange = intersectAxis(oy, dy, 0.0F, height);
		float[] zRange = intersectAxis(oz, dz, 0.0F, length);
		if (xRange == null || yRange == null || zRange == null) {
			return null;
		}
		tMin = Math.max(tMin, Math.max(xRange[0], Math.max(yRange[0], zRange[0])));
		tMax = Math.min(tMax, Math.min(xRange[1], Math.min(yRange[1], zRange[1])));
		return tMax >= tMin ? new float[] { tMin, tMax } : null;
	}

	private static float[] intersectAxis(float origin, float direction, float min, float max) {
		if (Math.abs(direction) < 1.0E-5F) {
			return origin >= min && origin <= max ? new float[] { Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY } : null;
		}
		float near = (min - origin) / direction;
		float far = (max - origin) / direction;
		return near <= far ? new float[] { near, far } : new float[] { far, near };
	}

	private static float firstBoundaryDistance(float origin, float direction, int block, int step) {
		if (Math.abs(direction) < 1.0E-5F) {
			return Float.POSITIVE_INFINITY;
		}
		float boundary = step > 0 ? block + 1.0F : block;
		return (boundary - origin) / direction;
	}

	private static float deltaDistance(float direction) {
		return Math.abs(direction) < 1.0E-5F ? Float.POSITIVE_INFINITY : Math.abs(1.0F / direction);
	}

	private static float lerp(float from, float to, float delta) {
		return from + (to - from) * delta;
	}

	private static SchematicScene load(String path) {
		try (InputStream stream = SchematicScene.class.getClassLoader().getResourceAsStream(path)) {
			if (stream == null) {
				return EMPTY;
			}

			CompoundTag root = NbtIo.readCompressed(stream, NbtAccounter.unlimitedHeap());
			int width = root.getShort("Width").orElse((short) root.getIntOr("Width", 1)).intValue();
			int height = root.getShort("Height").orElse((short) root.getIntOr("Height", 1)).intValue();
			int length = root.getShort("Length").orElse((short) root.getIntOr("Length", 1)).intValue();
			CompoundTag palette = root.getCompoundOrEmpty("Palette");
			String[] states = invertPalette(palette);
			byte[] data = root.getByteArray("BlockData").orElse(new byte[0]);
			byte[] light = root.getByteArray("BlockLight").orElse(new byte[0]);
			byte[] redLight = root.getByteArray("BlockLightRed").orElse(new byte[0]);
			byte[] greenLight = root.getByteArray("BlockLightGreen").orElse(new byte[0]);
			byte[] blueLight = root.getByteArray("BlockLightBlue").orElse(new byte[0]);
			SceneBlocks samples = decodeBlocks(width, height, length, states, data, light, redLight, greenLight, blueLight);
			return new SchematicScene(width, height, length, samples.blockStates, samples.blockLights,
					samples.blockRedLights, samples.blockGreenLights, samples.blockBlueLights,
					samples.wallBlocks, samples.detailBlocks, samples.meshFaces, samples.lightPoints,
					samples.torchSamples);
		} catch (IOException | RuntimeException ignored) {
			return EMPTY;
		}
	}

	private static String[] invertPalette(CompoundTag palette) {
		int max = 0;
		for (Map.Entry<String, Tag> entry : palette.entrySet()) {
			max = Math.max(max, entry.getValue().asInt().orElse(0));
		}

		String[] states = new String[max + 1];
		for (Map.Entry<String, Tag> entry : palette.entrySet()) {
			int id = entry.getValue().asInt().orElse(-1);
			if (id >= 0 && id < states.length) {
				states[id] = entry.getKey();
			}
		}
		return states;
	}

	private static SceneBlocks decodeBlocks(int width, int height, int length, String[] states, byte[] data,
			byte[] light, byte[] redLight, byte[] greenLight, byte[] blueLight) {
		int volume = Math.max(0, width * height * length);
		int[] paletteIds = decodeVarInts(data, volume);
		String[] blockStates = new String[volume];
		float[] blockLights = new float[volume];
		float[] blockRedLights = new float[volume];
		float[] blockGreenLights = new float[volume];
		float[] blockBlueLights = new float[volume];
		for (int index = 0; index < volume && index < paletteIds.length; index++) {
			int stateId = paletteIds[index];
			String state = stateId >= 0 && stateId < states.length ? states[stateId] : null;
			if (!VersionScenePolicy.isInvisible(state) && isSupportedMaterial(state)) {
				blockStates[index] = state;
				float base = lightChannelAt(index, light);
				float red = lightChannelAt(index, redLight);
				float green = lightChannelAt(index, greenLight);
				float blue = lightChannelAt(index, blueLight);
				blockLights[index] = Mth.clamp(Math.max(base, Math.max(red, Math.max(green, blue))), 0.0F, 1.0F);
				blockRedLights[index] = Mth.clamp(redLight.length > 0 ? red : base, 0.0F, 1.0F);
				blockGreenLights[index] = Mth.clamp(greenLight.length > 0 ? green : base, 0.0F, 1.0F);
				blockBlueLights[index] = Mth.clamp(blueLight.length > 0 ? blue : base, 0.0F, 1.0F);
			}
		}
		float[] propagated = resolveEmbeddedDefaultLegacyLight(width, height, length, blockStates, light);
		System.arraycopy(propagated, 0, blockLights, 0, volume);
		System.arraycopy(propagated, 0, blockRedLights, 0, volume);
		System.arraycopy(propagated, 0, blockGreenLights, 0, volume);
		System.arraycopy(propagated, 0, blockBlueLights, 0, volume);
		for (int index = 0; index < volume; index++) {
			blockLights[index] = Math.max(blockRedLights[index], Math.max(blockGreenLights[index], blockBlueLights[index]));
		}

		List<BlockSample> wallBlocks = new ArrayList<>();
		List<BlockSample> detailBlocks = new ArrayList<>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int bestZ = -1;
				String bestState = null;
				for (int z = 0; z < length; z++) {
					String state = blockStates[index(width, length, x, y, z)];
					if (state == null) {
						continue;
					}
					if (isDetail(state)) {
						addSample(detailBlocks, x, y, z, state, blockLights[index(width, length, x, y, z)]);
						continue;
					}
					if (bestState == null || exposedToCamera(blockStates, width, length, x, y, z)) {
						bestZ = z;
						bestState = state;
						if (z > 5) {
							break;
						}
					}
				}
				if (bestState != null) {
					addSample(wallBlocks, x, y, bestZ, bestState, blockLights[index(width, length, x, y, bestZ)]);
				}
			}
		}

		wallBlocks.sort(Comparator.comparingInt(sample -> sample.z));
		detailBlocks.sort(Comparator.comparingInt(sample -> sample.z));
		List<LightPoint> lightPoints = collectLightPoints(width, height, length, blockStates);
		List<MeshFace> meshFaces = buildMeshFaces(width, height, length, blockStates, blockLights,
				blockRedLights, blockGreenLights, blockBlueLights, lightPoints);
		List<TorchSample> torchSamples = collectTorchSamples(width, height, length, blockStates);
		return new SceneBlocks(blockStates, blockLights, blockRedLights, blockGreenLights, blockBlueLights,
				wallBlocks, detailBlocks, meshFaces, lightPoints, torchSamples);
	}

	private static int[] decodeVarInts(byte[] data, int expected) {
		int[] result = new int[expected];
		int output = 0;
		int value = 0;
		int shift = 0;
		for (byte current : data) {
			value |= (current & 0x7F) << shift;
			if ((current & 0x80) == 0) {
				if (output >= expected) {
					break;
				}
				result[output++] = value;
				value = 0;
				shift = 0;
			} else {
				shift += 7;
				if (shift > 28) {
					throw new IllegalArgumentException("Invalid schematic BlockData VarInt");
				}
			}
		}
		if (output != expected || shift != 0) {
			throw new IllegalArgumentException("Schematic BlockData size does not match its dimensions");
		}
		return result;
	}

	private static float[][] propagateColoredLegacyLight(int width, int height, int length, String[] states) {
		return new float[][] {
				propagateLegacyChannel(width, height, length, states, 16),
				propagateLegacyChannel(width, height, length, states, 8),
				propagateLegacyChannel(width, height, length, states, 0)
		};
	}

	private static float[] propagateDefaultLegacyLight(int width, int height, int length, String[] states) {
		int[] levels = new int[states.length];
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		for (int position = 0; position < states.length; position++) {
			String state = states[position];
			int level = Math.max(legacyLightLevel(state), isTranslucentLikeLaby(state) ? 1 : 0);
			levels[position] = level;
			if (level > 1) {
				queue.add(position);
			}
		}
		while (!queue.isEmpty()) {
			int position = queue.removeFirst();
			int nextLevel = levels[position] - 1;
			if (nextLevel <= 1) {
				continue;
			}
			int x = position % width;
			int y = position / (width * length);
			int z = position / width % length;
			propagateNeighbor(states, levels, queue, width, height, length, x - 1, y, z, nextLevel);
			propagateNeighbor(states, levels, queue, width, height, length, x + 1, y, z, nextLevel);
			propagateNeighbor(states, levels, queue, width, height, length, x, y - 1, z, nextLevel);
			propagateNeighbor(states, levels, queue, width, height, length, x, y + 1, z, nextLevel);
			propagateNeighbor(states, levels, queue, width, height, length, x, y, z - 1, nextLevel);
			propagateNeighbor(states, levels, queue, width, height, length, x, y, z + 1, nextLevel);
		}
		float[] result = new float[states.length];
		for (int position = 0; position < states.length; position++) {
			result[position] = levels[position] / 15.0F;
		}
		return result;
	}

	private static float[] resolveEmbeddedDefaultLegacyLight(int width, int height, int length, String[] states,
			byte[] embedded) {
		if (embedded.length != states.length) {
			return propagateDefaultLegacyLight(width, height, length, states);
		}
		int[] levels = new int[states.length];
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		boolean[] queued = new boolean[states.length];
		for (int position = 0; position < states.length; position++) {
			int level = embedded[position];
			levels[position] = level;
			if (level < 0) {
				queue.add(position);
				queued[position] = true;
			}
		}
		while (!queue.isEmpty()) {
			int position = queue.removeFirst();
			queued[position] = false;
			int x = position % width;
			int y = position / (width * length);
			int z = position / width % length;
			int level = legacyLightLevel(states[position]);
			if (isTranslucentLikeLaby(states[position])) {
				level = Math.max(level, 1);
			}
			level = Math.max(level, embeddedNeighborLevel(levels, width, height, length, x - 1, y, z) - 1);
			level = Math.max(level, embeddedNeighborLevel(levels, width, height, length, x + 1, y, z) - 1);
			level = Math.max(level, embeddedNeighborLevel(levels, width, height, length, x, y - 1, z) - 1);
			level = Math.max(level, embeddedNeighborLevel(levels, width, height, length, x, y + 1, z) - 1);
			level = Math.max(level, embeddedNeighborLevel(levels, width, height, length, x, y, z - 1) - 1);
			level = Math.max(level, embeddedNeighborLevel(levels, width, height, length, x, y, z + 1) - 1);
			if (level != levels[position]) {
				levels[position] = level;
				queueEmbeddedNeighbors(queue, queued, width, height, length, x, y, z);
			}
		}
		float[] result = new float[states.length];
		for (int position = 0; position < states.length; position++) {
			result[position] = Math.max(0, levels[position]) / 15.0F;
		}
		return result;
	}

	private static int embeddedNeighborLevel(int[] levels, int width, int height, int length, int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x >= width || y >= height || z >= length) {
			return 0;
		}
		return levels[index(width, length, x, y, z)];
	}

	private static void queueEmbeddedNeighbors(ArrayDeque<Integer> queue, boolean[] queued,
			int width, int height, int length, int x, int y, int z) {
		queueEmbedded(queue, queued, width, height, length, x - 1, y, z);
		queueEmbedded(queue, queued, width, height, length, x + 1, y, z);
		queueEmbedded(queue, queued, width, height, length, x, y - 1, z);
		queueEmbedded(queue, queued, width, height, length, x, y + 1, z);
		queueEmbedded(queue, queued, width, height, length, x, y, z - 1);
		queueEmbedded(queue, queued, width, height, length, x, y, z + 1);
	}

	private static void queueEmbedded(ArrayDeque<Integer> queue, boolean[] queued,
			int width, int height, int length, int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x >= width || y >= height || z >= length) {
			return;
		}
		int position = index(width, length, x, y, z);
		if (!queued[position]) {
			queue.add(position);
			queued[position] = true;
		}
	}

	private static float[] propagateLegacyChannel(int width, int height, int length, String[] states, int shift) {
		int volume = states.length;
		int[] levels = new int[volume];
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		for (int position = 0; position < volume; position++) {
			String state = states[position];
			int color = legacyLightColor(state);
			if (color == 0) {
				continue;
			}
			int red = color >> 16 & 255;
			int green = color >> 8 & 255;
			int blue = color & 255;
			int maximum = Math.max(red, Math.max(green, blue));
			float correction = maximum > 0 ? 255.0F / maximum : 0.0F;
			int component = color >> shift & 255;
			int corrected = Mth.clamp((int) (component * correction * 1.45F), 0, 255);
			int level = (int) (legacyLightLevel(state) / 255.0F * corrected);
			if (level > levels[position]) {
				levels[position] = level;
				queue.add(position);
			}
		}

		while (!queue.isEmpty()) {
			int position = queue.removeFirst();
			int nextLevel = levels[position] - 1;
			if (nextLevel <= 0) {
				continue;
			}
			int x = position % width;
			int y = position / (width * length);
			int z = position / width % length;
			propagateNeighbor(states, levels, queue, width, height, length, x - 1, y, z, nextLevel);
			propagateNeighbor(states, levels, queue, width, height, length, x + 1, y, z, nextLevel);
			propagateNeighbor(states, levels, queue, width, height, length, x, y - 1, z, nextLevel);
			propagateNeighbor(states, levels, queue, width, height, length, x, y + 1, z, nextLevel);
			propagateNeighbor(states, levels, queue, width, height, length, x, y, z - 1, nextLevel);
			propagateNeighbor(states, levels, queue, width, height, length, x, y, z + 1, nextLevel);
		}

		float[] result = new float[volume];
		for (int position = 0; position < volume; position++) {
			result[position] = levels[position] / 15.0F;
		}
		return result;
	}

	private static void propagateNeighbor(String[] states, int[] levels, ArrayDeque<Integer> queue,
			int width, int height, int length, int x, int y, int z, int level) {
		if (x < 0 || y < 0 || z < 0 || x >= width || y >= height || z >= length) {
			return;
		}
		int position = index(width, length, x, y, z);
		if (level > levels[position]) {
			levels[position] = level;
			queue.add(position);
		}
	}

	private static int legacyLightLevel(String state) {
		if (state != null && state.contains("furnace") && state.contains("lit=true")) {
			return 13;
		}
		return isLightSourceLikeLaby(state) ? 15 : 0;
	}

	private static int legacyLightColor(String state) {
		if (state == null) {
			return 0;
		}
		if (state.contains("soul_") && state.contains("torch")) {
			return 0x0A56A5;
		}
		if (state.contains("torch") || state.contains("furnace") && state.contains("lit=true")) {
			return 0xCC6600;
		}
		if (state.contains("glowstone")) {
			return 0xFFFFFF;
		}
		if (state.contains("lava")) {
			return 0xFF700D;
		}
		return 0;
	}

	private static List<MeshFace> buildMeshFaces(int width, int height, int length, String[] blockStates,
			float[] blockLights, float[] blockRedLights, float[] blockGreenLights, float[] blockBlueLights,
			List<LightPoint> lightPoints) {
		List<MeshFace> faces = new ArrayList<>();
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < length; z++) {
				for (int x = 0; x < width; x++) {
					int index = index(width, length, x, y, z);
					String state = blockStates[index];
					if (state == null) {
						continue;
					}
					float light = Math.max(blockLights[index], lightFor(state));
					if (isCrossPlane(state)) {
						int firstFace = faces.size();
						addCrossPlaneFaces(faces, blockStates, width, height, length, blockLights, blockRedLights,
								blockGreenLights, blockBlueLights, lightPoints, x, y, z, state, light);
						addBackFaces(faces, firstFace);
						continue;
					}
					addFaceIfExposed(faces, blockStates, width, height, length, blockLights, blockRedLights, blockGreenLights,
							blockBlueLights, lightPoints, x, y, z, 1, 0, 0, state, light);
					addFaceIfExposed(faces, blockStates, width, height, length, blockLights, blockRedLights, blockGreenLights,
							blockBlueLights, lightPoints, x, y, z, -1, 0, 0, state, light);
					addFaceIfExposed(faces, blockStates, width, height, length, blockLights, blockRedLights, blockGreenLights,
							blockBlueLights, lightPoints, x, y, z, 0, 1, 0, state, light);
					addFaceIfExposed(faces, blockStates, width, height, length, blockLights, blockRedLights, blockGreenLights,
							blockBlueLights, lightPoints, x, y, z, 0, -1, 0, state, light);
					addFaceIfExposed(faces, blockStates, width, height, length, blockLights, blockRedLights, blockGreenLights,
							blockBlueLights, lightPoints, x, y, z, 0, 0, 1, state, light);
					addFaceIfExposed(faces, blockStates, width, height, length, blockLights, blockRedLights, blockGreenLights,
							blockBlueLights, lightPoints, x, y, z, 0, 0, -1, state, light);
				}
			}
		}
		return faces;
	}

	private static void addBackFaces(List<MeshFace> faces, int firstFace) {
		int end = faces.size();
		for (int index = firstFace; index < end; index++) {
			MeshFace face = faces.get(index);
			faces.add(new MeshFace(reverse(face.xs), reverse(face.ys), reverse(face.zs), reverse(face.us), reverse(face.vs),
					-face.normalX, -face.normalY, -face.normalZ, face.state, face.light, reverse(face.redLights),
					reverse(face.greenLights), reverse(face.blueLights), face.directionShade, face.lava, face.lavaStillTop));
		}
	}

	private static float legacyStrengthAt(String[] blockStates, float[] light, int width, int height, int length,
			int x, int y, int z) {
		int totalLightLevel = 0;
		int totalBlocks = 0;
		for (int offsetX = -1; offsetX <= 0; offsetX++) {
			for (int offsetY = -1; offsetY <= 0; offsetY++) {
				for (int offsetZ = -1; offsetZ <= 0; offsetZ++) {
					int posX = x + offsetX;
					int posY = y + offsetY;
					int posZ = z + offsetZ;
					String state = blockStateAt(blockStates, width, height, length, posX, posY, posZ);
					if (isTranslucentLikeLaby(state) || isLightSourceLikeLaby(state)) {
						totalLightLevel += Math.round(lightAt(light, width, height, length, posX, posY, posZ) * 15.0F);
						totalBlocks++;
					} else if (offsetY == 0) {
						totalBlocks++;
					}
				}
			}
		}
		if (totalBlocks <= 0) {
			return 0.0F;
		}
		float level = totalLightLevel / totalBlocks;
		return Mth.clamp(((0.06F * level) + 0.1F) * LABY_BRIGHTNESS, 0.0F, 1.0F);
	}

	private static float lightAt(float[] light, int width, int height, int length, int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x >= width || y >= height || z >= length) {
			return 0.0F;
		}
		return light[index(width, length, x, y, z)];
	}

	private static boolean isLightSourceLikeLaby(String state) {
		return state != null && (state.contains("torch") || state.contains("lava") || state.contains("glowstone")
				|| state.contains("furnace") && state.contains("lit=true"));
	}

	private static List<LightPoint> collectLightPoints(int width, int height, int length, String[] blockStates) {
		List<LightPoint> lights = new ArrayList<>();
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < length; z++) {
				for (int x = 0; x < width; x++) {
					String state = blockStates[index(width, length, x, y, z)];
					if (state == null) {
						continue;
					}
					if (state.contains("torch")) {
						boolean soul = state.contains("soul_");
						lights.add(new LightPoint(x + 0.5F, y + 0.75F, z + 0.5F,
								soul ? 0x0A / 255.0F : 0xCC / 255.0F,
								soul ? 0x56 / 255.0F : 0x66 / 255.0F,
								soul ? 0xA5 / 255.0F : 0.0F,
								0.12F, 0.20F, 0.256F));
					} else if (state.contains("glowstone")) {
						lights.add(new LightPoint(x + 0.5F, y + 0.5F, z + 0.5F, 1.0F, 1.0F, 1.0F,
								1.0F, 0.045F, 0.0075F));
					} else if (state.contains("furnace") && state.contains("lit=true")) {
						lights.add(new LightPoint(x + 0.5F, y + 0.5F, z + 0.5F,
								0xCC / 255.0F, 0x66 / 255.0F, 0.0F,
								0.12F, 0.20F, 0.256F));
					} else if (state.contains("lava") && (x % 4 == 0 || y % 4 == 0 || z % 4 == 0)) {
						lights.add(new LightPoint(x + 0.5F, y + 0.5F, z + 0.5F, 1.0F, 0.44F, 0.05F,
								1.0F, 0.35F, 0.44F));
					}
				}
			}
		}
		return lights;
	}

	private static List<TorchSample> collectTorchSamples(int width, int height, int length, String[] blockStates) {
		List<TorchSample> torches = new ArrayList<>();
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < length; z++) {
				for (int x = 0; x < width; x++) {
					String state = blockStates[index(width, length, x, y, z)];
					if (state != null && state.contains("torch")) {
						torches.add(new TorchSample(x, y, z, parameter(state, "facing", "up"),
								sampleHash(x * 31 + z, y * 17 + 73), false, state.contains("soul_")));
					} else if (state != null && state.contains("furnace") && state.contains("lit=true")) {
						torches.add(new TorchSample(x, y, z, parameter(state, "facing", "none"),
								sampleHash(x * 31 + z, y * 17 + 73), true, false));
					}
				}
			}
		}
		return torches;
	}

	private static float[] torchTip(TorchSample torch) {
		float x = torch.x + 0.5F;
		float y = torch.y + 0.72F;
		float z = torch.z + 0.5F;
		if ("north".equals(torch.facing)) {
			z += 0.22F;
			y += 0.08F;
		} else if ("south".equals(torch.facing)) {
			z -= 0.22F;
			y += 0.08F;
		} else if ("west".equals(torch.facing)) {
			x += 0.22F;
			y += 0.08F;
		} else if ("east".equals(torch.facing)) {
			x -= 0.22F;
			y += 0.08F;
		}
		return new float[] { x, y, z };
	}

	private static int sampleHash(int a, int b) {
		int hash = a * 73428767 ^ b * 912931 ^ 0x6E624EB7;
		hash ^= hash >>> 16;
		hash *= 0x7FEB352D;
		hash ^= hash >>> 15;
		return hash;
	}

	private static VertexLights vertexLights(String[] states, float[] blockLights, float[] blockRedLights,
			float[] blockGreenLights, float[] blockBlueLights, int width, int height, int length,
			List<LightPoint> lightPoints, float[] xs, float[] ys, float[] zs, String state) {
		float[] red = new float[4];
		float[] green = new float[4];
		float[] blue = new float[4];
		for (int i = 0; i < 4; i++) {
			int lightX = Mth.floor(xs[i]);
			int lightY = Mth.floor(ys[i]);
			int lightZ = Mth.floor(zs[i]);
			float redLegacy = legacyStrengthAt(states, blockRedLights, width, height, length, lightX, lightY, lightZ);
			float greenLegacy = legacyStrengthAt(states, blockGreenLights, width, height, length, lightX, lightY, lightZ);
			float blueLegacy = legacyStrengthAt(states, blockBlueLights, width, height, length, lightX, lightY, lightZ);
			red[i] = Mth.clamp(redLegacy, 0.0F, 1.0F);
			green[i] = Mth.clamp(greenLegacy, 0.0F, 1.0F);
			blue[i] = Mth.clamp(blueLegacy, 0.0F, 1.0F);
		}
		return new VertexLights(red, green, blue);
	}

	private static void addCrossPlaneFaces(List<MeshFace> faces, String[] states, int width, int height, int length,
			float[] blockLights, float[] blockRedLights, float[] blockGreenLights, float[] blockBlueLights,
			List<LightPoint> lightPoints, int x, int y, int z, String state, float light) {
		MaterialTile tile = tileFor(state);
		float u0 = tile.u / (float) ATLAS_SIZE;
		float v0 = tile.v / (float) ATLAS_SIZE;
		float u1 = (tile.u + TILE_SIZE) / (float) ATLAS_SIZE;
		float v1 = (tile.v + TILE_SIZE) / (float) ATLAS_SIZE;
		float min = 0.12F;
		float max = 0.88F;
		addQuad(faces, new float[] { x + min, x + max, x + max, x + min },
				new float[] { y, y, y + 1.0F, y + 1.0F },
				new float[] { z + min, z + max, z + max, z + min },
				new float[] { u0, u1, u1, u0 }, new float[] { v1, v1, v0, v0 },
				0, 0, 1, state, light, vertexLights(states, blockLights, blockRedLights, blockGreenLights, blockBlueLights,
						width, height, length, lightPoints,
						new float[] { x + min, x + max, x + max, x + min },
						new float[] { y, y, y + 1.0F, y + 1.0F },
						new float[] { z + min, z + max, z + max, z + min }, state), 0.02F, false);
		addQuad(faces, new float[] { x + min, x + max, x + max, x + min },
				new float[] { y, y, y + 1.0F, y + 1.0F },
				new float[] { z + max, z + min, z + min, z + max },
				new float[] { u0, u1, u1, u0 }, new float[] { v1, v1, v0, v0 },
				0, 0, -1, state, light, vertexLights(states, blockLights, blockRedLights, blockGreenLights, blockBlueLights,
						width, height, length, lightPoints,
						new float[] { x + min, x + max, x + max, x + min },
						new float[] { y, y, y + 1.0F, y + 1.0F },
						new float[] { z + max, z + min, z + min, z + max }, state), 0.02F, false);
	}

	private static void addQuad(List<MeshFace> faces, float[] xs, float[] ys, float[] zs, float[] us, float[] vs,
			int nx, int ny, int nz, String state, float light, VertexLights lights,
			float directionShade, boolean lava) {
		faces.add(new MeshFace(xs, ys, zs, us, vs, nx, ny, nz, state, light,
				lights.red, lights.green, lights.blue,
				directionShade, lava, false));
	}

	private static void addFaceIfExposed(List<MeshFace> faces, String[] states, int width, int height, int length,
			float[] blockLights, float[] blockRedLights, float[] blockGreenLights, float[] blockBlueLights,
			List<LightPoint> lightPoints, int x, int y, int z, int nx, int ny, int nz, String state, float light) {
		int neighborX = x + nx;
		int neighborY = y + ny;
		int neighborZ = z + nz;
		String neighbor = null;
		if (neighborX >= 0 && neighborY >= 0 && neighborZ >= 0 && neighborX < width && neighborY < height
				&& neighborZ < length) {
			neighbor = states[index(width, length, neighborX, neighborY, neighborZ)];
		}
		if (!shouldRenderFaceLikeLaby(state, neighbor, nx, ny, nz)) {
			return;
		}
		float[][] corners = boundingCornersLikeLaby(states, width, height, length, x, y, z, state);
		float[] uv = uvForFaceLikeLaby(state, nx, ny, nz, lavaFrame(System.nanoTime() / 1_000_000_000.0F));
		int rot = textureRotationLikeLaby(states, width, height, length, x, y, z, state, nx, ny, nz);
		float u0 = uv[0];
		float v0 = uv[1];
		float u1 = uv[2];
		float v1 = uv[3];
		int cornerRotation = Math.max(rot, 0);
		boolean lavaStillTop = state.contains("lava") && ny > 0 && rot == -1;
		if (nx > 0) {
			addFaceFromCorners(faces, states, width, height, length, blockLights, blockRedLights, blockGreenLights,
					blockBlueLights, lightPoints, corners, x, y, z, nx, ny, nz, state, light,
					new int[] { 6, 7, 3, 2 }, new float[] { u0, u1, u1, u0 }, new float[] { v0, v0, v1, v1 },
					0.08F, lavaStillTop);
		} else if (nx < 0) {
			addFaceFromCorners(faces, states, width, height, length, blockLights, blockRedLights, blockGreenLights,
					blockBlueLights, lightPoints, corners, x, y, z, nx, ny, nz, state, light,
					new int[] { 0, 1, 5, 4 }, new float[] { u1, u0, u0, u1 }, new float[] { v1, v1, v0, v0 },
					-0.04F, lavaStillTop);
		} else if (ny > 0) {
			addFaceFromCorners(faces, states, width, height, length, blockLights, blockRedLights, blockGreenLights,
					blockBlueLights, lightPoints, corners, x, y, z, nx, ny, nz, state, light,
					new int[] { cornerIndex(4, cornerRotation), cornerIndex(5, cornerRotation),
							cornerIndex(7, cornerRotation), cornerIndex(6, cornerRotation) },
					new float[] { u0, u0, u1, u1 }, new float[] { v1, v0, v0, v1 }, 0.18F, lavaStillTop);
		} else if (ny < 0) {
			addFaceFromCorners(faces, states, width, height, length, blockLights, blockRedLights, blockGreenLights,
					blockBlueLights, lightPoints, corners, x, y, z, nx, ny, nz, state, light,
					new int[] { 1, 0, 2, 3 }, new float[] { u1, u1, u0, u0 }, new float[] { v1, v0, v0, v1 },
					-0.16F, lavaStillTop);
		} else if (nz > 0) {
			addFaceFromCorners(faces, states, width, height, length, blockLights, blockRedLights, blockGreenLights,
					blockBlueLights, lightPoints, corners, x, y, z, nx, ny, nz, state, light,
					new int[] { 1, 3, 7, 5 }, new float[] { u1, u0, u0, u1 }, new float[] { v1, v1, v0, v0 },
					0.04F, lavaStillTop);
		} else {
			addFaceFromCorners(faces, states, width, height, length, blockLights, blockRedLights, blockGreenLights,
					blockBlueLights, lightPoints, corners, x, y, z, nx, ny, nz, state, light,
					new int[] { 4, 6, 2, 0 }, new float[] { u0, u1, u1, u0 }, new float[] { v0, v0, v1, v1 },
					-0.10F, lavaStillTop);
		}
	}

	private static void addFaceFromCorners(List<MeshFace> faces, String[] states, int width, int height, int length,
			float[] blockLights, float[] blockRedLights, float[] blockGreenLights, float[] blockBlueLights,
			List<LightPoint> lightPoints, float[][] corners, int x, int y, int z, int nx, int ny, int nz,
			String state, float light, int[] indices, float[] us, float[] vs, float directionShade,
			boolean lavaStillTop) {
		float[] xs = new float[4];
		float[] ys = new float[4];
		float[] zs = new float[4];
		for (int i = 0; i < 4; i++) {
			float[] corner = corners[indices[i]];
			xs[i] = x + corner[0];
			ys[i] = y + corner[1];
			zs[i] = z + corner[2];
		}
		VertexLights lights = vertexLights(states, blockLights, blockRedLights, blockGreenLights, blockBlueLights,
				width, height, length, lightPoints, xs, ys, zs, state);
		faces.add(new MeshFace(xs, ys, zs, us, vs, nx, ny, nz, state, light, lights.red, lights.green, lights.blue,
				directionShade, state.contains("lava"), lavaStillTop));
	}

	private static boolean shouldRenderFaceLikeLaby(String state, String neighbor, int nx, int ny, int nz) {
		if (state != null && state.contains("rail")) {
			return ny > 0;
		}
		if (state.contains("lava")) {
			if (neighbor != null && neighbor.contains("lava")) {
				return ny == 0;
			}
			return !isFullBlockLikeLaby(neighbor);
		}
		if (state.contains("ice") && neighbor != null && neighbor.contains("ice")) {
			return false;
		}
		if (state.contains("snow[") && (ny < 0 || neighbor != null && neighbor.contains("snow["))) {
			return false;
		}
		if (isSlab(state)) {
			if (neighbor != null && isSlab(neighbor) && slabType(state).equals(slabType(neighbor))) {
				return false;
			}
			if (isFullBlockLikeLaby(neighbor) && !isTranslucentLikeLaby(neighbor)) {
				return false;
			}
		}
		if (isFence(state) && ny != 0) {
			if (isFence(neighbor)) {
				return false;
			}
			if (isFullBlockLikeLaby(neighbor) && !isTranslucentLikeLaby(neighbor)) {
				return false;
			}
		}
		return !(isFullBlockLikeLaby(state) && isFullBlockLikeLaby(neighbor) && !isTranslucentLikeLaby(neighbor));
	}

	private static boolean isFullBlockLikeLaby(String state) {
		if (state == null || state.startsWith("minecraft:air")) {
			return false;
		}
		return !(state.contains("lava") || state.contains("slab") || state.contains("fence")
				|| state.contains("rail") || state.contains("torch") || isPlantGrass(state)
				|| state.contains("poppy") || state.contains("dandelion") || state.contains("cobweb")
				|| state.contains("lever") || state.contains("snow["));
	}

	private static boolean isTranslucentLikeLaby(String state) {
		return !isFullBlockLikeLaby(state) || state != null && (state.contains("ice") || state.contains("cobweb"));
	}

	private static boolean isSlab(String state) {
		return state != null && state.contains("slab");
	}

	private static boolean isFence(String state) {
		return state != null && state.contains("fence");
	}

	private static boolean isCrossPlane(String state) {
		return state != null && (isPlantGrass(state) || state.contains("poppy")
				|| state.contains("dandelion") || state.contains("cobweb"));
	}

	private static boolean isCutoutMaterial(String state) {
		return state != null && (state.contains("rail") || state.contains("cobweb") || state.contains("poppy")
				|| state.contains("dandelion") || isPlantGrass(state));
	}

	private static boolean isPlantGrass(String state) {
		return state != null && (state.equals("minecraft:grass") || state.startsWith("minecraft:grass["));
	}

	private static String slabType(String state) {
		if (state == null || !state.contains("type=")) {
			return "bottom";
		}
		int start = state.indexOf("type=") + 5;
		int end = state.indexOf(',', start);
		if (end < 0) {
			end = state.indexOf(']', start);
		}
		return end < 0 ? state.substring(start) : state.substring(start, end);
	}

	private static Box boundingBoxLikeLaby(String state) {
		if (isSlab(state)) {
			return switch (slabType(state)) {
				case "top" -> new Box(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
				case "double" -> Box.FULL;
				default -> new Box(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			};
		}
		if (isFence(state)) {
			return new Box(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
		}
		if (state != null && state.contains("rail")) {
			return new Box(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
		}
		if (state != null && state.contains("torch")) {
			return new Box(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.625F, 0.5625F);
		}
		if (state != null && state.contains("snow[")) {
			return new Box(0.0F, 0.0F, 0.0F, 1.0F, snowLayers(state) / 8.0F, 1.0F);
		}
		return Box.FULL;
	}

	private static float[][] boundingCornersLikeLaby(String[] states, int width, int height, int length,
			int x, int y, int z, String state) {
		Box box = boundingBoxLikeLaby(state);
		float[][] corners = new float[][] {
				{ box.minX, box.minY, box.minZ },
				{ box.minX, box.minY, box.maxZ },
				{ box.maxX, box.minY, box.minZ },
				{ box.maxX, box.minY, box.maxZ },
				{ box.minX, box.maxY, box.minZ },
				{ box.minX, box.maxY, box.maxZ },
				{ box.maxX, box.maxY, box.minZ },
				{ box.maxX, box.maxY, box.maxZ }
		};
		if (state != null && state.contains("torch")) {
			corners = new float[][] {
					{ 0.4375F, 0.0F, 0.4375F },
					{ 0.4375F, 0.0F, 0.5625F },
					{ 0.5625F, 0.0F, 0.4375F },
					{ 0.5625F, 0.0F, 0.5625F },
					{ 0.4375F, 0.625F, 0.4375F },
					{ 0.4375F, 0.625F, 0.5625F },
					{ 0.5625F, 0.625F, 0.4375F },
					{ 0.5625F, 0.625F, 0.5625F }
			};
			String facing = parameter(state, "facing", "none");
			boolean south = "south".equals(facing);
			if ("north".equals(facing) || south) {
				rotateCornersX(corners, 0.5F, 1.0F, 0.5F + 0.1F * (south ? -1.0F : 1.0F),
						30.0F * (south ? 1.0F : -1.0F));
			} else {
				boolean west = "west".equals(facing);
				rotateCornersZ(corners, 0.5F + 0.1F * (west ? 1.0F : -1.0F), 1.0F, 0.5F,
						30.0F * (west ? 1.0F : -1.0F));
			}
		}
		if (state != null && state.contains("lava")) {
			applyLavaCorners(states, width, height, length, x, y, z, corners);
		}
		return corners;
	}

	private static void rotateCornersX(float[][] corners, float pivotX, float pivotY, float pivotZ, float degrees) {
		float radians = degrees * Mth.DEG_TO_RAD;
		float sin = Mth.sin(radians);
		float cos = Mth.cos(radians);
		for (float[] corner : corners) {
			float y = corner[1] - pivotY;
			float z = corner[2] - pivotZ;
			corner[1] = cos * y - sin * z + pivotY;
			corner[2] = sin * y + cos * z + pivotZ;
		}
	}

	private static void rotateCornersZ(float[][] corners, float pivotX, float pivotY, float pivotZ, float degrees) {
		float radians = degrees * Mth.DEG_TO_RAD;
		float sin = Mth.sin(radians);
		float cos = Mth.cos(radians);
		for (float[] corner : corners) {
			float x = corner[0] - pivotX;
			float y = corner[1] - pivotY;
			corner[0] = cos * x - sin * y + pivotX;
			corner[1] = sin * x + cos * y + pivotY;
		}
	}

	private static void applyLavaCorners(String[] states, int width, int height, int length, int x, int y, int z,
			float[][] corners) {
		int own = lavaLevel(blockStateAt(states, width, height, length, x, y, z));
		if (isLava(blockStateAt(states, width, height, length, x, y + 1, z))) {
			own = 0;
		}
		int edge1 = own;
		int edge2 = own;
		int edge3 = own;
		int edge4 = own;
		int west = neighborLavaEdge(states, width, height, length, x - 1, y, z, own);
		if (west >= 0) {
			edge1 = Math.min(edge1, west);
			edge2 = Math.min(edge2, west);
		}
		int east = neighborLavaEdge(states, width, height, length, x + 1, y, z, own);
		if (east >= 0) {
			edge3 = Math.min(edge3, east);
			edge4 = Math.min(edge4, east);
		}
		int south = neighborLavaEdge(states, width, height, length, x, y, z + 1, own);
		if (south >= 0) {
			edge2 = Math.min(edge2, south);
			edge4 = Math.min(edge4, south);
		}
		int north = neighborLavaEdge(states, width, height, length, x, y, z - 1, own);
		if (north >= 0) {
			edge1 = Math.min(edge1, north);
			edge3 = Math.min(edge3, north);
		}
		corners[4][1] = 1.0F - edge1 / 8.0F;
		corners[5][1] = 1.0F - edge2 / 8.0F;
		corners[6][1] = 1.0F - edge3 / 8.0F;
		corners[7][1] = 1.0F - edge4 / 8.0F;
	}

	private static int neighborLavaEdge(String[] states, int width, int height, int length, int x, int y, int z,
			int own) {
		String state = blockStateAt(states, width, height, length, x, y, z);
		if (!isLava(state)) {
			return -1;
		}
		return isLava(blockStateAt(states, width, height, length, x, y + 1, z)) ? 0 : lavaLevel(state);
	}

	private static String blockStateAt(String[] states, int width, int height, int length, int x, int y, int z) {
		if (x < 0 || y < 0 || z < 0 || x >= width || y >= height || z >= length) {
			return null;
		}
		return states[index(width, length, x, y, z)];
	}

	private static boolean isLava(String state) {
		return state != null && state.contains("lava");
	}

	private static int lavaLevel(String state) {
		try {
			return Mth.clamp(Integer.parseInt(parameter(state, "level", "0")), 0, 15);
		} catch (NumberFormatException ignored) {
			return 0;
		}
	}

	private static float[] uvForFaceLikeLaby(String state, int normalY, int frame) {
		return uvForFaceLikeLaby(state, 0, normalY, 0, frame);
	}

	private static float[] uvForFaceLikeLaby(String state, int normalX, int normalY, int normalZ, int frame) {
		if (state.contains("lava")) {
			float u0 = 0.0F;
			float u1 = 0.5F;
			float v0 = frame * TILE_SIZE / (float) LAVA_ATLAS_HEIGHT;
			float v1 = (frame * TILE_SIZE + TILE_SIZE) / (float) LAVA_ATLAS_HEIGHT;
			return new float[] { u0, v0, u1, v1 };
		}
		MaterialTile tile = tileForFace(state, normalX, normalY, normalZ);
		float minU = tile.u / (float) ATLAS_SIZE;
		float minV = tile.v / (float) ATLAS_SIZE;
		float maxU = (tile.u + TILE_SIZE) / (float) ATLAS_SIZE;
		float maxV = (tile.v + TILE_SIZE) / (float) ATLAS_SIZE;
		if (state.contains("torch")) {
			float cutX = tile.u + 7.0F;
			float cutY = tile.v + (normalY < 0 ? 14.0F : 6.0F);
			float cutW = 2.0F;
			float cutH = normalY != 0 ? 2.0F : 10.0F;
			return new float[] { cutX / ATLAS_SIZE, cutY / ATLAS_SIZE, (cutX + cutW) / ATLAS_SIZE,
					(cutY + cutH) / ATLAS_SIZE };
		}
		if (isSlab(state) && !"double".equals(slabType(state))) {
			maxV = minV + (maxV - minV) * 0.5F;
		}
		if (isFence(state)) {
			float pixel = 1.0F / ATLAS_SIZE;
			minU += pixel * 6.0F;
			maxU -= pixel * 6.0F;
			if (normalY != 0) {
				minV += pixel * 6.0F;
				maxV -= pixel * 6.0F;
			}
		}
		return new float[] { minU, minV, maxU, maxV };
	}

	private static int lavaFrame(float time) {
		return Math.floorMod((int) (time * 1000.0F / 300.0F), LAVA_FRAMES);
	}

	private static int textureRotationLikeLaby(String[] states, int width, int height, int length, int x, int y, int z,
			String state, int nx, int ny, int nz) {
		if (state.contains("rail") && ny > 0) {
			return switch (parameter(state, "shape", "north_south")) {
				case "east_west", "north_west" -> 1;
				case "south_west" -> 2;
				default -> 0;
			};
		}
		if (!state.contains("lava") || ny <= 0) {
			return 0;
		}
		int own = lavaLevel(state);
		int rotation = -1;
		int west = neighborLavaEdge(states, width, height, length, x - 1, y, z, own);
		int east = neighborLavaEdge(states, width, height, length, x + 1, y, z, own);
		int south = neighborLavaEdge(states, width, height, length, x, y, z + 1, own);
		int north = neighborLavaEdge(states, width, height, length, x, y, z - 1, own);
		if (west >= 0 && west < own) {
			rotation = 1;
		}
		if (east >= 0 && east < own) {
			rotation = 3;
		}
		if (south >= 0 && south < own) {
			rotation = 0;
		}
		if (north >= 0 && north < own) {
			rotation = 2;
		}
		return rotation;
	}

	private static int cornerIndex(int index, int rotation) {
		for (int i = 0; i < rotation; i++) {
			index = switch (index) {
				case 0 -> 2;
				case 1, 5 -> index - 1;
				case 2, 6 -> index + 1;
				case 3 -> 1;
				case 4 -> 6;
				case 7 -> 5;
				default -> index;
			};
		}
		return index;
	}

	private static String parameter(String state, String name, String fallback) {
		if (state == null) {
			return fallback;
		}
		String prefix = name + "=";
		int start = state.indexOf(prefix);
		if (start < 0) {
			return fallback;
		}
		start += prefix.length();
		int end = state.indexOf(',', start);
		if (end < 0) {
			end = state.indexOf(']', start);
		}
		return end < 0 ? state.substring(start) : state.substring(start, end);
	}

	private static int snowLayers(String state) {
		if (state == null || !state.contains("layers=")) {
			return 1;
		}
		int start = state.indexOf("layers=") + 7;
		int end = state.indexOf(',', start);
		if (end < 0) {
			end = state.indexOf(']', start);
		}
		try {
			return Mth.clamp(Integer.parseInt(end < 0 ? state.substring(start) : state.substring(start, end)), 1, 8);
		} catch (NumberFormatException ignored) {
			return 1;
		}
	}

	private static boolean exposedToCamera(String[] states, int width, int length, int x, int y, int z) {
		return z <= 0 || states[index(width, length, x, y, z - 1)] == null;
	}

	private static int index(int width, int length, int x, int y, int z) {
		return x + z * width + y * width * length;
	}

	private static void addSample(List<BlockSample> samples, int x, int y, int z, String state, float schematicLight) {
		MaterialTile tile = tileFor(state);
		samples.add(new BlockSample(x, y, z, tile.u, tile.v, Math.max(lightFor(state), schematicLight),
				state.contains("lava")));
	}

	private static boolean isSupportedMaterial(String state) {
		return state.startsWith("minecraft:stone")
				|| state.startsWith("minecraft:grass_block")
				|| state.startsWith("minecraft:oak_planks")
				|| state.startsWith("minecraft:cobblestone")
				|| state.startsWith("minecraft:coal_ore")
				|| state.startsWith("minecraft:iron_ore")
				|| state.startsWith("minecraft:gold_ore")
				|| state.startsWith("minecraft:diamond_ore")
				|| state.startsWith("minecraft:crafting_table")
				|| state.startsWith("minecraft:furnace")
				|| state.startsWith("minecraft:dirt")
				|| state.startsWith("minecraft:gravel")
				|| state.startsWith("minecraft:torch")
				|| state.startsWith("minecraft:wall_torch")
				|| state.startsWith("minecraft:soul_torch")
				|| state.startsWith("minecraft:soul_wall_torch")
				|| state.startsWith("minecraft:lava")
				|| state.startsWith("minecraft:cobweb")
				|| state.startsWith("minecraft:grass")
				|| state.startsWith("minecraft:oak_fence")
				|| state.startsWith("minecraft:rail")
				|| state.startsWith("minecraft:oak_slab")
				|| state.startsWith("minecraft:cobblestone_slab")
				|| state.startsWith("minecraft:dandelion")
				|| state.startsWith("minecraft:poppy")
				|| state.startsWith("minecraft:glowstone")
				|| state.startsWith("minecraft:snow")
				|| state.startsWith("minecraft:ice")
				|| state.startsWith("minecraft:pumpkin")
				|| state.startsWith("minecraft:carved_pumpkin")
				|| state.startsWith("minecraft:lever");
	}

	private static float lightChannelAt(int index, byte[] light) {
		return Mth.clamp((index < light.length ? light[index] & 0xFF : 0) / 15.0F, 0.0F, 1.0F);
	}

	private static boolean isDetail(String state) {
		return state.contains("oak") || state.contains("rail") || state.contains("torch") || state.contains("lava")
				|| state.contains("furnace") || state.contains("crafting_table");
	}

	private static MaterialTile tileFor(String state) {
		if (state.contains("lava")) {
			return tile(0, 0);
		}
		if (state.contains("glowstone")) {
			return tile(9, 6);
		}
		if (state.contains("torch")) {
			return state.contains("soul_") ? tile(3, 8) : tile(0, 5);
		}
		if (state.contains("oak")) {
			return tile(4, 0);
		}
		if (state.contains("rail")) {
			return state.contains("shape=north_east") || state.contains("shape=north_west")
					|| state.contains("shape=south_east") || state.contains("shape=south_west")
					? tile(0, 7) : tile(0, 8);
		}
		if (isPlantGrass(state)) {
			return tile(8, 5);
		}
		if (state.contains("grass_block")) {
			return state.contains("snowy=true") ? tile(4, 4) : tile(3, 0);
		}
		if (state.contains("dirt")) {
			return tile(2, 0);
		}
		if (state.contains("diamond_ore")) {
			return tile(2, 3);
		}
		if (state.contains("gold_ore")) {
			return tile(0, 2);
		}
		if (state.contains("iron_ore")) {
			return tile(1, 2);
		}
		if (state.contains("coal_ore")) {
			return tile(2, 2);
		}
		if (state.contains("cobweb") || state.contains("glass")) {
			return tile(11, 0);
		}
		if (state.contains("gravel")) {
			return tile(3, 1);
		}
		if (state.contains("snow")) {
			return state.contains("snow_block") ? tile(2, 4) : tile(2, 4);
		}
		if (state.contains("ice")) {
			return tile(3, 4);
		}
		if (state.contains("crafting_table")) {
			return tile(11, 3);
		}
		if (state.contains("furnace")) {
			return state.contains("lit=true") ? tile(13, 3) : tile(12, 2);
		}
		if (state.contains("pumpkin")) {
			if (state.contains("carved_pumpkin")) {
				return state.contains("lit=true") ? tile(8, 7) : tile(7, 7);
			}
			return tile(6, 7);
		}
		if (state.contains("poppy")) {
			return tile(12, 0);
		}
		if (state.contains("dandelion")) {
			return tile(13, 0);
		}
		return state.contains("cobblestone") ? tile(0, 1) : tile(1, 0);
	}

	private static MaterialTile tileForFace(String state, int normalX, int normalY, int normalZ) {
		if (state.contains("grass_block")) {
			if (normalY > 0) {
				return tile(0, 0);
			}
			if (normalY < 0) {
				return tile(2, 0);
			}
			return state.contains("snowy=true") ? tile(4, 4) : tile(3, 0);
		}
		if (state.contains("crafting_table")) {
			if (normalY > 0) {
				return tile(11, 2);
			}
			if (normalY < 0) {
				return tile(4, 0);
			}
			return tile(11, 3);
		}
		if (state.contains("pumpkin")) {
			if (normalY != 0) {
				return tile(6, 6);
			}
			String facing = parameter(state, "facing", "north");
			boolean front = switch (facing) {
				case "east" -> normalX > 0;
				case "west" -> normalX < 0;
				case "south" -> normalZ > 0;
				default -> normalZ < 0;
			};
			if (front && state.contains("carved_pumpkin")) {
				return state.contains("lit=true") ? tile(8, 7) : tile(7, 7);
			}
			return tile(6, 7);
		}
		if (!state.contains("furnace")) {
			return tileFor(state);
		}
		if (normalY > 0) {
			return tile(0, 1);
		}
		if (normalY < 0) {
			return tile(1, 1);
		}
		String facing = parameter(state, "facing", "north");
		boolean front = switch (facing) {
			case "east" -> normalX > 0;
			case "west" -> normalX < 0;
			case "south" -> normalZ > 0;
			default -> normalZ < 0;
		};
		if (front) {
			return state.contains("lit=true") ? tile(13, 3) : tile(12, 2);
		}
		return tile(13, 2);
	}

	private static MaterialTile tile(int x, int y) {
		return new MaterialTile(x * TILE_SIZE, y * TILE_SIZE);
	}

	private static int materialColor(String state, int normalY, float redLight, float greenLight, float blueLight,
			float directionShade, float fog) {
		int color = 0xFFFFFF;
		if (isPlantGrass(state) || state.contains("grass_block") && normalY > 0) {
			color = state.contains("snowy=true") ? 0xD8DFE4 : 0x667B3A;
		}
		float redShade = localLight(redLight, 1.0F);
		float greenShade = localLight(greenLight, 1.0F);
		float blueShade = localLight(blueLight, 1.0F);
		int red = Mth.clamp(Math.round(((color >> 16) & 255) * redShade), 0, 255);
		int green = Mth.clamp(Math.round(((color >> 8) & 255) * greenShade), 0, 255);
		int blue = Mth.clamp(Math.round((color & 255) * blueShade), 0, 255);
		return 0xFF000000 | red << 16 | green << 8 | blue;
	}

	private static float localLight(float value, float max) {
		return Mth.clamp(value, 0.0F, max);
	}

	private static void renderGradient(GuiGraphicsExtractor graphics, int width, int height, float time) {
		graphics.fill(0, 0, width, height, 0xFF07080D);
		graphics.fill(0, Math.round(height * 0.58F), width, height, 0xFF120D10);
		int glowX = Math.round(width * (0.45F + Mth.sin(time * 0.35F) * 0.02F));
		int glowY = Math.round(height * 0.70F);
		int glowWidth = Math.round(width * 0.42F);
		int glowHeight = Math.round(height * 0.24F);
		graphics.fill(glowX - glowWidth / 2, glowY - glowHeight / 2, glowX + glowWidth / 2,
				glowY + glowHeight / 2, packAlpha(0xB94B19, 0.14F));
	}

	private static int tintColor(int brightness) {
		int value = Mth.clamp(brightness, 0, 255);
		return 0xFF000000 | value << 16 | value << 8 | value;
	}

	private static float lightFor(String state) {
		if (state.contains("lava")) {
			return 1.0F;
		}
		if (state.contains("torch") || state.contains("glowstone")) {
			return 0.9F;
		}
		return 0.2F;
	}

	private static float emissionRed(String state) {
		if (state.contains("lava")) {
			return 1.0F;
		}
		if (state.contains("torch")) {
			return state.contains("soul_") ? 0x0A / 255.0F : 0xCC / 255.0F;
		}
		if (state.contains("glowstone") || state.contains("furnace") && state.contains("lit=true")) {
			return 0.9F;
		}
		return lightFor(state) * 0.22F;
	}

	private static float emissionGreen(String state) {
		if (state.contains("lava")) {
			return 0.48F;
		}
		if (state.contains("torch")) {
			return state.contains("soul_") ? 0x56 / 255.0F : 0x66 / 255.0F;
		}
		if (state.contains("glowstone") || state.contains("furnace") && state.contains("lit=true")) {
			return 0.62F;
		}
		return lightFor(state) * 0.22F;
	}

	private static float emissionBlue(String state) {
		if (state.contains("lava")) {
			return 0.08F;
		}
		if (state.contains("torch")) {
			return state.contains("soul_") ? 0xA5 / 255.0F : 0.0F;
		}
		if (state.contains("glowstone") || state.contains("furnace") && state.contains("lit=true")) {
			return 0.28F;
		}
		return lightFor(state) * 0.22F;
	}

	private static int shadeColor(int color, int amount) {
		int red = Mth.clamp(((color >> 16) & 255) + amount, 0, 255);
		int green = Mth.clamp(((color >> 8) & 255) + amount, 0, 255);
		int blue = Mth.clamp((color & 255) + amount, 0, 255);
		return 0xFF000000 | red << 16 | green << 8 | blue;
	}

	private static int packAlpha(int color, float alpha) {
		return Math.round(Mth.clamp(alpha, 0.0F, 1.0F) * 255.0F) << 24 | color & 0xFFFFFF;
	}

	private record BlockSample(int x, int y, int z, int u, int v, float light, boolean lava) {
	}

	private record Box(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
		private static final Box FULL = new Box(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	private record MeshFace(float[] xs, float[] ys, float[] zs, float[] us, float[] vs,
			int normalX, int normalY, int normalZ, String state, float light, float[] redLights,
			float[] greenLights, float[] blueLights, float directionShade, boolean lava, boolean lavaStillTop) {
	}

	private record VertexLights(float[] red, float[] green, float[] blue) {
	}

	private record ProjectedPoint(float x, float y, float depth) {
	}

	private record ProjectedFace(float[] x, float[] y, float[] u, float[] v, float depth, int color, boolean lava) {
	}

	private record LayerMesh(GpuBuffer vertexBuffer, int indexCount) {
		private static final LayerMesh EMPTY = new LayerMesh(null, 0);
	}

	private enum MeshLayer {
		SOLID,
		CUTOUT,
		LAVA
	}

	private record LightPoint(float x, float y, float z, float red, float green, float blue,
			float constant, float linear, float quadratic) {
		private static final LightPoint INACTIVE = new LightPoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
				1.0F, 0.0F, 0.0F);

		private double selectionScore(float cameraX, float cameraY, float cameraZ) {
			float dx = x - cameraX;
			float dy = y - cameraY;
			float dz = z - cameraZ;
			return dy * dy * 100.0F + dx * dx + dz * dz;
		}
	}

	private enum LightComponent {
		CONSTANT {
			@Override
			float get(LightPoint light) {
				return light.constant;
			}
		},
		LINEAR {
			@Override
			float get(LightPoint light) {
				return light.linear;
			}
		},
		QUADRATIC {
			@Override
			float get(LightPoint light) {
				return light.quadratic;
			}
		};

		abstract float get(LightPoint light);
	}

	private record TorchSample(int x, int y, int z, String facing, int hash, boolean furnace, boolean soul) {
	}

	private static final class CaveParticle {
		private double x;
		private double y;
		private double z;
		private double prevX;
		private double prevY;
		private double prevZ;
		private double motionX;
		private double motionY;
		private double motionZ;
		private final boolean flame;
		private final boolean soulFlame;
		private final int color;
		private final float size;
		private final int maxTicks;
		private final int sourceX;
		private final int sourceY;
		private final int sourceZ;
		private int ticks;

		private CaveParticle(double x, double y, double z, double motionScale, double verticalBoost,
				boolean flame, boolean soulFlame, int color, float size,
				int maxTicks, int sourceX, int sourceY, int sourceZ, Random random) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.prevX = x;
			this.prevY = y;
			this.prevZ = z;
			this.flame = flame;
			this.soulFlame = soulFlame;
			this.color = color;
			this.size = size;
			this.maxTicks = Math.max(1, maxTicks);
			this.sourceX = sourceX;
			this.sourceY = sourceY;
			this.sourceZ = sourceZ;
			double randomX = (random.nextDouble() * 2.0D - 1.0D) * 0.4D;
			double randomY = (random.nextDouble() * 2.0D - 1.0D) * 0.4D;
			double randomZ = (random.nextDouble() * 2.0D - 1.0D) * 0.4D;
			double strength = (random.nextDouble() + random.nextDouble() + 1.0D) * 0.15000000596046448D;
			double length = Math.sqrt(randomX * randomX + randomY * randomY + randomZ * randomZ);
			this.motionX = randomX / length * strength * motionScale;
			this.motionY = randomY / length * strength * motionScale + verticalBoost;
			this.motionZ = randomZ / length * strength * motionScale;
		}

		static CaveParticle flame(double x, double y, double z, int sourceX, int sourceY, int sourceZ,
				boolean soul, Random random) {
			float size = (random.nextFloat() * 0.5F + 0.5F) * 2.0F;
			int maxTicks = (int) (8.0D / (random.nextDouble() * 0.8D + 0.2D)) + 4;
			return new CaveParticle(x, y, z, 0.00396D, 0.00099D, true, soul, 255, size, maxTicks,
					sourceX, sourceY, sourceZ, random);
		}

		static CaveParticle smoke(double x, double y, double z, int sourceX, int sourceY, int sourceZ, Random random) {
			float size = (random.nextFloat() * 0.5F + 0.5F) * 2.0F * 0.75F;
			int brightness = (int) (random.nextDouble() * 76.5D);
			int maxTicks = (int) (8.0D / (random.nextDouble() * 0.8D + 0.2D));
			CaveParticle particle = new CaveParticle(x, y, z, 0.04D, 0.01D, false, false, brightness, size, maxTicks,
					sourceX, sourceY, sourceZ, random);
			particle.tick();
			return particle;
		}

		void tick() {
			this.prevX = this.x;
			this.prevY = this.y;
			this.prevZ = this.z;
			this.ticks++;
			if (this.flame) {
				this.x += this.motionX;
				this.y += this.motionY;
				this.z += this.motionZ;
				this.motionX *= 0.95D;
				this.motionY *= 0.95D;
				this.motionZ *= 0.95D;
			} else {
				this.motionY += 0.001D;
				this.x += this.motionX;
				this.y += this.motionY;
				this.z += this.motionZ;
				this.motionX *= 0.95D;
				this.motionY *= 0.95D;
				this.motionZ *= 0.95D;
			}
		}

		boolean dead() {
			return ticks >= maxTicks;
		}

		float progress() {
			return Mth.clamp(ticks / (float) maxTicks, 0.0F, 1.0F);
		}

		int smokeFrame() {
			float progress = Mth.clamp(ticks / (float) maxTicks, 0.0F, 1.0F);
			return Mth.clamp(PARTICLE_SMOKE_FRAMES - (int) (progress * (PARTICLE_SMOKE_FRAMES - 1)) - 1,
					0, PARTICLE_SMOKE_FRAMES - 1);
		}

		float renderX() {
			return (float) x;
		}

		float renderY() {
			return (float) y;
		}

		float renderZ() {
			return (float) z;
		}
	}

	private record MeshRenderState(Matrix3x2f pose, List<ProjectedFace> faces, ScreenRectangle bounds,
			TextureSetup textureSetup)
			implements GuiElementRenderState {

		@Override
		public void buildVertices(VertexConsumer consumer) {
			for (ProjectedFace face : faces) {
				consumer.addVertexWith2DPose(pose, face.x[0], face.y[0]).setUv(face.u[0], face.v[0]).setColor(face.color);
				consumer.addVertexWith2DPose(pose, face.x[1], face.y[1]).setUv(face.u[1], face.v[1]).setColor(face.color);
				consumer.addVertexWith2DPose(pose, face.x[2], face.y[2]).setUv(face.u[2], face.v[2]).setColor(face.color);
				consumer.addVertexWith2DPose(pose, face.x[3], face.y[3]).setUv(face.u[3], face.v[3]).setColor(face.color);
			}
		}

		@Override
		public RenderPipeline pipeline() {
			return RenderPipelines.GUI_TEXTURED;
		}

		@Override
		public ScreenRectangle scissorArea() {
			return null;
		}
	}

	private record CameraPose(float x, float y, float z, float forwardX, float forwardY, float forwardZ,
			float rightX, float rightY, float rightZ, float upX, float upY, float upZ, Matrix4f viewMatrix) {
	}

	private record RayHit(int x, int y, int z, float distance, int u, int v, int textureU, int textureV,
			int sourceSize, float light, boolean lava) {
	}

	private record RenderCell(int x, int y, int x2, int y2, int u, int v, int sourceWidth, int sourceHeight,
			int color, boolean lava) {
	}

	private record MaterialTile(int u, int v) {
	}

	private record SceneBlocks(String[] blockStates, float[] blockLights, float[] blockRedLights,
			float[] blockGreenLights, float[] blockBlueLights, List<BlockSample> wallBlocks,
			List<BlockSample> detailBlocks, List<MeshFace> meshFaces, List<LightPoint> lightPoints,
			List<TorchSample> torchSamples) {
	}
}
