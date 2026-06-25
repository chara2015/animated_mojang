package labymod_menu.client.legacy;

import labymod_menu.ModMetadata;
import labymod_menu.common.CameraPose;
import labymod_menu.common.CameraProfiles;
import net.minecraft.client.gui.GuiGraphics;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;

final class LegacySchematicScene {
	private static final String SCHEMATIC = "assets/labymod_menu/data/normal_cave.schem";
	private static final Object BLOCKS = LegacyRenderBridge.location(ModMetadata.MOD_ID, "textures/gui/title/cave_blocks.png");
	private static final Object LAVA = LegacyRenderBridge.location(ModMetadata.MOD_ID, "textures/gui/title/cave_lava_flow.png");
	private static final Object PARTICLES = LegacyRenderBridge.location(ModMetadata.MOD_ID, "textures/gui/title/cave_particles_clean.png");
	private static final int ATLAS = 256;
	private static final int PARTICLE_ATLAS = 128;
	private static final int TILE = 16;
	private static final int CAMERA_BUCKETS = 48;
	private static final LegacySchematicScene EMPTY = new LegacySchematicScene(1, 1, 1, new String[1], new float[1]);
	private static LegacySchematicScene loaded;

	private final int width;
	private final int height;
	private final int length;
	private final String[] states;
	private final float[] lights;
	private final List<Emitter> emitters = new ArrayList<>();
	private final List<Particle> particles = new ArrayList<>();
	private final Random random = new Random();
	private List<Cell> cells = List.of();
	private int cachedWidth = -1;
	private int cachedHeight = -1;
	private int cachedCamera = Integer.MIN_VALUE;
	private int lastParticleTick = Integer.MIN_VALUE;

	private LegacySchematicScene(int width, int height, int length, String[] states, float[] lights) {
		this.width = width;
		this.height = height;
		this.length = length;
		this.states = states;
		this.lights = lights;
		for (int y = 0; y < height; y++) {
			for (int z = 0; z < length; z++) {
				for (int x = 0; x < width; x++) {
					String state = states[index(x, y, z)];
					if (state != null && state.contains("torch")) {
						emitters.add(new Emitter(x, y, z, parameter(state, "facing", "up"), false,
								state.contains("soul_")));
					} else if (state != null && state.contains("furnace") && state.contains("lit=true")) {
						emitters.add(new Emitter(x, y, z, parameter(state, "facing", "none"), true, false));
					}
				}
			}
		}
	}

	static LegacySchematicScene get() {
		if (loaded == null) {
			loaded = load();
		}
		return loaded;
	}

	void render(GuiGraphics graphics, String screenName, float progress, long elapsed) {
		Camera camera = camera(screenName, progress);
		int bucket = camera.hashBucket();
		if (cachedWidth != graphics.guiWidth() || cachedHeight != graphics.guiHeight() || cachedCamera != bucket) {
			cells = buildCells(graphics.guiWidth(), graphics.guiHeight(), camera);
			cachedWidth = graphics.guiWidth();
			cachedHeight = graphics.guiHeight();
			cachedCamera = bucket;
		}
		graphics.fill(0, 0, graphics.guiWidth(), graphics.guiHeight(), 0xFF07080D);
		for (Cell cell : cells) {
			LegacyRenderBridge.blit(graphics, cell.lava ? LAVA : BLOCKS, cell.x, cell.y, cell.u, cell.v, cell.size, cell.size,
					cell.sourceSize, cell.sourceSize, cell.lava ? 32 : ATLAS, cell.lava ? 512 : ATLAS, cell.color);
		}
		renderParticles(graphics, camera, elapsed);
	}

	private List<Cell> buildCells(int screenWidth, int screenHeight, Camera camera) {
		int step = clamp(Math.min(screenWidth, screenHeight) / 180, 3, 6);
		float aspect = screenWidth / (float) screenHeight;
		List<Cell> result = new ArrayList<>((screenWidth / step + 1) * (screenHeight / step + 1));
		for (int y = 0; y < screenHeight; y += step) {
			float sy = ((y + step * 0.5F) / screenHeight - 0.5F) * -2.0F;
			for (int x = 0; x < screenWidth; x += step) {
				float sx = ((x + step * 0.5F) / screenWidth - 0.5F) * 2.0F * aspect;
				Hit hit = trace(camera, sx * 0.46F, sy * 0.46F);
				if (hit == null) {
					continue;
				}
				float fog = clamp(hit.distance / 62.0F, 0.0F, 1.0F);
				float brightness = clamp(0.32F + hit.light * 0.62F - fog * 0.34F, 0.10F, 1.0F);
				int value = clamp(Math.round(brightness * 255.0F), 0, 255);
				int color = 0xFF000000 | value << 16 | value << 8 | value;
				boolean lava = hit.state.contains("lava");
				Tile tile = tileForFace(hit.state, hit.axis, hit.sign);
				int u = lava ? hit.textureU * 2 : tile.u + hit.textureU;
				int v = lava ? hit.textureV + (int) ((System.currentTimeMillis() / 400L) % 32L) * 16 : tile.v + hit.textureV;
				result.add(new Cell(x, y, step + 1, u, v, 2, color, lava));
			}
		}
		return result;
	}

	private Hit trace(Camera camera, float sx, float sy) {
		float dx = camera.fx + camera.rx * sx + camera.ux * sy;
		float dy = camera.fy + camera.uy * sy;
		float dz = camera.fz + camera.rz * sx + camera.uz * sy;
		float magnitude = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		dx /= magnitude;
		dy /= magnitude;
		dz /= magnitude;
		float[] range = intersectVolume(camera.x, camera.y, camera.z, dx, dy, dz);
		if (range == null) {
			return null;
		}
		float distance = Math.max(0.0F, range[0]) + 0.001F;
		int x = clamp(floor(camera.x + dx * distance), 0, width - 1);
		int y = clamp(floor(camera.y + dy * distance), 0, height - 1);
		int z = clamp(floor(camera.z + dz * distance), 0, length - 1);
		int stepX = dx > 0 ? 1 : -1;
		int stepY = dy > 0 ? 1 : -1;
		int stepZ = dz > 0 ? 1 : -1;
		float maxX = firstBoundary(camera.x, dx, x, stepX);
		float maxY = firstBoundary(camera.y, dy, y, stepY);
		float maxZ = firstBoundary(camera.z, dz, z, stepZ);
		float deltaX = delta(dx);
		float deltaY = delta(dy);
		float deltaZ = delta(dz);
		while (maxX <= distance) maxX += deltaX;
		while (maxY <= distance) maxY += deltaY;
		while (maxZ <= distance) maxZ += deltaZ;
		int axis = 2;
		int sign = -1;
		while (distance <= range[1] && x >= 0 && y >= 0 && z >= 0 && x < width && y < height && z < length) {
			String state = states[index(x, y, z)];
			if (state != null && intersectsMaterial(state, camera, dx, dy, dz, distance)) {
				float px = camera.x + dx * distance;
				float py = camera.y + dy * distance;
				float pz = camera.z + dz * distance;
				float faceU = axis == 0 ? fraction(pz) : fraction(px);
				float faceV = axis == 1 ? fraction(pz) : fraction(py);
				if (axis == 0 && sign > 0 || axis == 2 && sign < 0) faceU = 1.0F - faceU;
				faceV = 1.0F - faceV;
				return new Hit(distance, clamp((int) (faceU * TILE), 0, 15),
						clamp((int) (faceV * TILE), 0, 15), Math.max(lights[index(x, y, z)], lightFor(state)),
						state, axis, sign);
			}
			if (maxX < maxY && maxX < maxZ) {
				distance = maxX; maxX += deltaX; x += stepX; axis = 0; sign = -stepX;
			} else if (maxY < maxZ) {
				distance = maxY; maxY += deltaY; y += stepY; axis = 1; sign = -stepY;
			} else {
				distance = maxZ; maxZ += deltaZ; z += stepZ; axis = 2; sign = -stepZ;
			}
		}
		return null;
	}

	private static boolean intersectsMaterial(String state, Camera camera, float dx, float dy, float dz, float distance) {
		if (state.contains("fence")) {
			float x = fraction(camera.x + dx * distance);
			float y = fraction(camera.y + dy * distance);
			float z = fraction(camera.z + dz * distance);
			boolean post = x >= 0.34F && x <= 0.66F && z >= 0.34F && z <= 0.66F;
			boolean rails = (y >= 0.34F && y <= 0.60F || y >= 0.68F && y <= 0.94F)
					&& (x >= 0.40F && x <= 0.60F || z >= 0.40F && z <= 0.60F);
			return post || rails;
		}
		if (!isCrossPlane(state)) {
			return true;
		}
		float x = fraction(camera.x + dx * distance);
		float z = fraction(camera.z + dz * distance);
		return Math.abs(x - z) < 0.16F || Math.abs(x + z - 1.0F) < 0.16F;
	}

	private void renderParticles(GuiGraphics graphics, Camera camera, long elapsed) {
		int tick = (int) (elapsed / 50L);
		tickParticles(camera, tick);
		for (Particle particle : particles) {
			Projected point = project(camera, particle.x, particle.y, particle.z, graphics.guiWidth(), graphics.guiHeight());
			if (point == null || point.depth > 92.0F || occluded(camera, particle.x, particle.y, particle.z)) {
				continue;
			}
			float scale = clamp(42.0F / point.depth, 0.55F, 2.2F);
			int size = Math.max(2, Math.round(graphics.guiHeight() / 260.0F * scale * particle.size()));
			if (particle.flame) {
				LegacyRenderBridge.blit(graphics, PARTICLES, Math.round(point.x) - size, Math.round(point.y) - size,
						0, particle.soul ? 32 : 24, size * 2, size * 2, 8, 8,
						PARTICLE_ATLAS, PARTICLE_ATLAS, 0xFFFFFFFF);
			} else {
				int gray = clamp(particle.color, 0, 255);
				LegacyRenderBridge.blit(graphics, PARTICLES, Math.round(point.x) - size, Math.round(point.y) - size,
						particle.smokeFrame() * 8, 0, size * 2, size * 2, 8, 8,
						PARTICLE_ATLAS, PARTICLE_ATLAS, 0xFF000000 | gray << 16 | gray << 8 | gray);
			}
		}
	}

	private void tickParticles(Camera camera, int tick) {
		if (lastParticleTick == Integer.MIN_VALUE) {
			for (Emitter emitter : emitters) {
				if (!emitter.furnace) spawn(emitter);
			}
			lastParticleTick = tick - 1;
		}
		int count = clamp(tick - lastParticleTick, 0, 4);
		for (int i = 0; i < count; i++) {
			for (int p = particles.size() - 1; p >= 0; p--) {
				particles.get(p).tick();
				if (particles.get(p).dead()) particles.remove(p);
			}
			for (Emitter emitter : emitters) {
				float dx = emitter.x + 0.5F - camera.x;
				float dy = emitter.y + 0.5F - camera.y;
				float dz = emitter.z + 0.5F - camera.z;
				if (dx * dx + dy * dy + dz * dz < 72.0F * 72.0F && random.nextInt(emitter.furnace ? 4 : 2) == 0) {
					spawn(emitter);
				}
			}
			if (particles.size() > 384) particles.subList(0, particles.size() - 384).clear();
		}
		lastParticleTick = tick;
	}

	private void spawn(Emitter emitter) {
		double x = emitter.x + 0.5D;
		double y = emitter.y + (emitter.furnace ? random.nextFloat() * 6.0F / 16.0F : 0.7D);
		double z = emitter.z + 0.5D;
		if (emitter.furnace) {
			double edge = random.nextFloat() * 0.6F - 0.3F;
			switch (emitter.facing) {
				case "east" -> { x += 0.52D; z += edge; }
				case "west" -> { x -= 0.52D; z += edge; }
				case "south" -> { z += 0.52D; x += edge; }
				case "north" -> { z -= 0.52D; x += edge; }
				default -> { return; }
			}
		} else {
			switch (emitter.facing) {
				case "east" -> x -= 0.2D;
				case "west" -> x += 0.2D;
				case "south" -> z -= 0.2D;
				case "north" -> z += 0.2D;
			}
		}
		particles.add(Particle.smoke(x, y, z, random));
		particles.add(Particle.flame(x, y, z, emitter.soul, random));
	}

	private boolean occluded(Camera camera, double x, double y, double z) {
		double dx = x - camera.x;
		double dy = y - camera.y;
		double dz = z - camera.z;
		double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
		for (int i = 1, steps = Math.max(1, (int) (distance * 8)); i < steps; i++) {
			double t = i / (double) steps;
			int bx = floor(camera.x + dx * t);
			int by = floor(camera.y + dy * t);
			int bz = floor(camera.z + dz * t);
			if (bx >= 0 && by >= 0 && bz >= 0 && bx < width && by < height && bz < length) {
				String state = states[index(bx, by, bz)];
				if (state != null && !isCrossPlane(state) && !state.contains("fence") && !state.contains("torch")) return true;
				if (state != null && state.contains("fence")) {
					double px = camera.x + dx * t - bx;
					double py = camera.y + dy * t - by;
					double pz = camera.z + dz * t - bz;
					boolean post = px >= 0.34D && px <= 0.66D && pz >= 0.34D && pz <= 0.66D;
					boolean rails = (py >= 0.34D && py <= 0.60D || py >= 0.68D && py <= 0.94D)
							&& (px >= 0.40D && px <= 0.60D || pz >= 0.40D && pz <= 0.60D);
					if (post || rails) return true;
				}
			}
		}
		return false;
	}

	private static Projected project(Camera camera, double x, double y, double z, int width, int height) {
		float dx = (float) x - camera.x;
		float dy = (float) y - camera.y;
		float dz = (float) z - camera.z;
		float depth = dx * camera.fx + dy * camera.fy + dz * camera.fz;
		if (depth <= 0.05F) return null;
		float horizontal = dx * camera.rx + dy * camera.ry + dz * camera.rz;
		float vertical = dx * camera.ux + dy * camera.uy + dz * camera.uz;
		float f = height / (2.0F * (float) Math.tan(Math.toRadians(25.0D)));
		return new Projected(width * 0.5F + horizontal / depth * f, height * 0.5F - vertical / depth * f, depth);
	}

	private static Camera camera(String screenName, float progress) {
		CameraPose pose;
		if (screenName.contains("Title")) {
			pose = new CameraPose(spline(17, 20, 31, progress), spline(13, 12.8F, 13, progress),
					spline(82, 44, 6, progress), spline(0, 8, 21, progress),
					spline(0, -5, -13, progress), spline(-90, -10, 0, progress));
		} else if (screenName.contains("World") || screenName.contains("Create") || screenName.contains("GameRule")) {
			pose = CameraProfiles.SINGLEPLAYER;
		} else if (screenName.contains("Multiplayer") || screenName.contains("Replay")) {
			pose = CameraProfiles.MULTIPLAYER;
		} else if (screenName.contains("Account") || screenName.contains("Alt") || screenName.contains("Switcher")) {
			pose = CameraProfiles.ACCOUNT;
		} else if (screenName.contains("Connect") || screenName.contains("Disconnected")) {
			pose = CameraProfiles.OPENER_START;
		} else if (screenName.contains("Options") || screenName.contains("Pack")) {
			pose = CameraProfiles.OPTIONS;
		} else {
			pose = CameraProfiles.TITLE;
		}
		return Camera.of(pose);
	}

	private float[] intersectVolume(float ox, float oy, float oz, float dx, float dy, float dz) {
		float[] xr = intersectAxis(ox, dx, 0, width);
		float[] yr = intersectAxis(oy, dy, 0, height);
		float[] zr = intersectAxis(oz, dz, 0, length);
		if (xr == null || yr == null || zr == null) return null;
		float min = Math.max(0, Math.max(xr[0], Math.max(yr[0], zr[0])));
		float max = Math.min(96, Math.min(xr[1], Math.min(yr[1], zr[1])));
		return max >= min ? new float[] { min, max } : null;
	}

	private static float[] intersectAxis(float origin, float direction, float min, float max) {
		if (Math.abs(direction) < 1.0E-5F) return origin >= min && origin <= max
				? new float[] { Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY } : null;
		float near = (min - origin) / direction;
		float far = (max - origin) / direction;
		return near <= far ? new float[] { near, far } : new float[] { far, near };
	}

	private int index(int x, int y, int z) {
		return x + z * width + y * width * length;
	}

	private static LegacySchematicScene load() {
		try (InputStream raw = LegacySchematicScene.class.getClassLoader().getResourceAsStream(SCHEMATIC)) {
			if (raw == null) return EMPTY;
			Map<String, Object> root = readCompound(new DataInputStream(new GZIPInputStream(raw)));
			int width = number(root.get("Width"), 1);
			int height = number(root.get("Height"), 1);
			int length = number(root.get("Length"), 1);
			Map<?, ?> palette = (Map<?, ?>) root.get("Palette");
			String[] names = new String[palette.size()];
			for (Map.Entry<?, ?> entry : palette.entrySet()) {
				int id = number(entry.getValue(), -1);
				if (id >= 0 && id < names.length) names[id] = String.valueOf(entry.getKey());
			}
			int volume = width * height * length;
			int[] ids = decodeVarInts((byte[]) root.get("BlockData"), volume);
			byte[] embedded = (byte[]) root.getOrDefault("BlockLight", new byte[0]);
			String[] states = new String[volume];
			float[] lights = new float[volume];
			for (int i = 0; i < volume; i++) {
				String state = ids[i] >= 0 && ids[i] < names.length ? names[ids[i]] : null;
				if (supported(state)) states[i] = state;
				lights[i] = embedded.length == volume ? Math.max(0, embedded[i]) / 15.0F : lightFor(state);
			}
			return new LegacySchematicScene(width, height, length, states, lights);
		} catch (IOException | RuntimeException ignored) {
			return EMPTY;
		}
	}

	private static Map<String, Object> readCompound(DataInputStream input) throws IOException {
		input.readByte();
		input.readUTF();
		return readCompoundPayload(input);
	}

	private static Map<String, Object> readCompoundPayload(DataInputStream input) throws IOException {
		Map<String, Object> result = new HashMap<>();
		while (true) {
			int type = input.readUnsignedByte();
			if (type == 0) return result;
			result.put(input.readUTF(), readPayload(input, type));
		}
	}

	private static Object readPayload(DataInputStream input, int type) throws IOException {
		return switch (type) {
			case 1 -> input.readByte();
			case 2 -> input.readShort();
			case 3 -> input.readInt();
			case 4 -> input.readLong();
			case 5 -> input.readFloat();
			case 6 -> input.readDouble();
			case 7 -> { byte[] value = new byte[input.readInt()]; input.readFully(value); yield value; }
			case 8 -> input.readUTF();
			case 9 -> {
				int child = input.readUnsignedByte();
				int count = input.readInt();
				List<Object> values = new ArrayList<>(count);
				for (int i = 0; i < count; i++) values.add(readPayload(input, child));
				yield values;
			}
			case 10 -> readCompoundPayload(input);
			case 11 -> { int[] value = new int[input.readInt()]; for (int i = 0; i < value.length; i++) value[i] = input.readInt(); yield value; }
			case 12 -> { long[] value = new long[input.readInt()]; for (int i = 0; i < value.length; i++) value[i] = input.readLong(); yield value; }
			default -> throw new IOException("Unknown NBT tag " + type);
		};
	}

	private static int[] decodeVarInts(byte[] data, int count) {
		int[] values = new int[count];
		int output = 0, value = 0, shift = 0;
		for (byte current : data) {
			value |= (current & 0x7F) << shift;
			if ((current & 0x80) == 0) {
				if (output < count) values[output++] = value;
				value = 0; shift = 0;
			} else shift += 7;
		}
		return values;
	}

	private static Tile tileForFace(String state, int axis, int sign) {
		if (state.contains("grass_block")) return axis == 1 ? tile(sign > 0 ? 0 : 2, 0) : tile(3, 0);
		if (state.contains("lava")) return tile(0, 0);
		if (state.contains("glowstone")) return tile(9, 6);
		if (state.contains("torch")) return state.contains("soul_") ? tile(3, 8) : tile(0, 5);
		if (state.contains("oak")) return tile(4, 0);
		if (state.contains("rail")) return tile(0, 8);
		if (state.contains("grass") && !state.contains("grass_block")) return tile(8, 5);
		if (state.contains("dirt")) return tile(2, 0);
		if (state.contains("diamond_ore")) return tile(2, 3);
		if (state.contains("gold_ore")) return tile(0, 2);
		if (state.contains("iron_ore")) return tile(1, 2);
		if (state.contains("coal_ore")) return tile(2, 2);
		if (state.contains("cobweb")) return tile(11, 0);
		if (state.contains("gravel")) return tile(3, 1);
		if (state.contains("crafting_table")) return tile(11, 3);
		if (state.contains("furnace")) return state.contains("lit=true") ? tile(13, 3) : tile(12, 2);
		if (state.contains("poppy")) return tile(12, 0);
		if (state.contains("dandelion")) return tile(13, 0);
		return state.contains("cobblestone") ? tile(0, 1) : tile(1, 0);
	}

	private static boolean supported(String state) {
		return state != null && !state.contains(":air") && (state.contains("stone") || state.contains("grass")
				|| state.contains("oak") || state.contains("cobblestone") || state.contains("ore")
				|| state.contains("crafting_table") || state.contains("furnace") || state.contains("dirt")
				|| state.contains("gravel") || state.contains("torch") || state.contains("lava")
				|| state.contains("cobweb") || state.contains("rail") || state.contains("dandelion")
				|| state.contains("poppy") || state.contains("glowstone"));
	}

	private static boolean isCrossPlane(String state) {
		return state.contains("torch") || state.contains("cobweb") || state.contains("grass")
				&& !state.contains("grass_block") || state.contains("dandelion") || state.contains("poppy");
	}

	private static float lightFor(String state) {
		if (state == null) return 0.0F;
		if (state.contains("lava")) return 1.0F;
		if (state.contains("torch") || state.contains("glowstone") || state.contains("furnace") && state.contains("lit=true")) return 0.9F;
		return 0.2F;
	}

	private static String parameter(String state, String key, String fallback) {
		int start = state.indexOf(key + "=");
		if (start < 0) return fallback;
		start += key.length() + 1;
		int end = state.indexOf(',', start);
		if (end < 0) end = state.indexOf(']', start);
		return end < 0 ? state.substring(start) : state.substring(start, end);
	}

	private static float spline(float first, float middle, float last, float progress) {
		float t = clamp(progress, 0.0F, 1.0F);
		if (t < 0.5F) return lerp(first, middle, smooth(t * 2.0F));
		return lerp(middle, last, smooth(t * 2.0F - 1.0F));
	}

	private static float smooth(float value) { return value * value * (3.0F - 2.0F * value); }
	private static float lerp(float a, float b, float t) { return a + (b - a) * t; }
	private static Tile tile(int x, int y) { return new Tile(x * TILE, y * TILE); }
	private static int number(Object value, int fallback) { return value instanceof Number n ? n.intValue() : fallback; }
	private static int floor(double value) { return (int) Math.floor(value); }
	private static float fraction(float value) { return value - floor(value); }
	private static float firstBoundary(float origin, float direction, int block, int step) {
		return Math.abs(direction) < 1.0E-5F ? Float.POSITIVE_INFINITY : ((step > 0 ? block + 1.0F : block) - origin) / direction;
	}
	private static float delta(float direction) { return Math.abs(direction) < 1.0E-5F ? Float.POSITIVE_INFINITY : Math.abs(1.0F / direction); }
	private static int clamp(int value, int min, int max) { return Math.max(min, Math.min(max, value)); }
	private static float clamp(float value, float min, float max) { return Math.max(min, Math.min(max, value)); }

	private record Tile(int u, int v) {}
	private record Cell(int x, int y, int size, int u, int v, int sourceSize, int color, boolean lava) {}
	private record Hit(float distance, int textureU, int textureV, float light, String state, int axis, int sign) {}
	private record Emitter(int x, int y, int z, String facing, boolean furnace, boolean soul) {}
	private record Projected(float x, float y, float depth) {}

	private record Camera(float x, float y, float z, float fx, float fy, float fz, float rx, float ry, float rz,
			float ux, float uy, float uz, float yaw, float pitch, float roll) {
		static Camera of(CameraPose pose) {
			float yaw = (float) Math.toRadians(pose.yaw());
			float pitch = (float) Math.toRadians(pose.pitch());
			float roll = (float) Math.toRadians(pose.roll());
			float cosPitch = (float) Math.cos(pitch);
			float fx = (float) Math.sin(yaw) * cosPitch;
			float fy = -(float) Math.sin(pitch);
			float fz = (float) Math.cos(yaw) * cosPitch;
			float rx = (float) Math.cos(yaw);
			float rz = -(float) Math.sin(yaw);
			float ux = -(float) Math.sin(yaw) * (float) Math.sin(pitch);
			float uy = (float) Math.cos(pitch);
			float uz = -(float) Math.cos(yaw) * (float) Math.sin(pitch);
			float cosRoll = (float) Math.cos(roll);
			float sinRoll = (float) Math.sin(roll);
			float rolledRx = rx * cosRoll + ux * sinRoll;
			float rolledRy = uy * sinRoll;
			float rolledRz = rz * cosRoll + uz * sinRoll;
			float rolledUx = ux * cosRoll - rx * sinRoll;
			float rolledUy = uy * cosRoll;
			float rolledUz = uz * cosRoll - rz * sinRoll;
			return new Camera(pose.x(), pose.y(), pose.z(), fx, fy, fz, rolledRx, rolledRy, rolledRz,
					rolledUx, rolledUy, rolledUz,
					pose.yaw(), pose.pitch(), pose.roll());
		}
		int hashBucket() {
			int result = Math.round(x * CAMERA_BUCKETS);
			result = 31 * result + Math.round(y * CAMERA_BUCKETS);
			result = 31 * result + Math.round(z * CAMERA_BUCKETS);
			result = 31 * result + Math.round(yaw * CAMERA_BUCKETS);
			result = 31 * result + Math.round(pitch * CAMERA_BUCKETS);
			result = 31 * result + Math.round(roll * CAMERA_BUCKETS);
			return result;
		}
	}

	private static final class Particle {
		double x, y, z, dx, dy, dz;
		final boolean flame, soul;
		final int lifetime, color;
		int age;

		private Particle(double x, double y, double z, double dx, double dy, double dz,
				boolean flame, boolean soul, int lifetime, int color) {
			this.x = x; this.y = y; this.z = z; this.dx = dx; this.dy = dy; this.dz = dz;
			this.flame = flame; this.soul = soul; this.lifetime = lifetime; this.color = color;
		}
		static Particle flame(double x, double y, double z, boolean soul, Random random) {
			return new Particle(x, y, z, (random.nextDouble() - 0.5D) * 0.00396D,
					random.nextDouble() * 0.00396D + 0.00099D, (random.nextDouble() - 0.5D) * 0.00396D,
					true, soul, 8 + random.nextInt(5), 255);
		}
		static Particle smoke(double x, double y, double z, Random random) {
			return new Particle(x, y, z, (random.nextDouble() - 0.5D) * 0.04D,
					random.nextDouble() * 0.04D + 0.01D, (random.nextDouble() - 0.5D) * 0.04D,
					false, false, 20 + random.nextInt(12), 48 + random.nextInt(24));
		}
		void tick() {
			x += dx; y += dy; z += dz;
			if (!flame) { dy += 0.001D; dx *= 0.95D; dy *= 0.95D; dz *= 0.95D; }
			age++;
		}
		boolean dead() { return age >= lifetime; }
		float progress() { return age / (float) lifetime; }
		float size() { float p = progress(); return 1.0F - p * p * 0.5F; }
		int smokeFrame() { return clamp(7 - (int) (progress() * 7.0F), 0, 7); }
	}
}
