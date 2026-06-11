package net.labymod.core.client.controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.controller.MeasurementController;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.mesh.MeshRenderer;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.gui.screen.key.HotkeyService;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.configuration.labymod.main.laby.ingame.MeasurementConfig;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.ClientPlayerInteractEvent;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.network.server.ServerSwitchEvent;
import net.labymod.api.event.client.network.server.SubServerSwitchEvent;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/controller/DefaultMeasurementController.class */
@Singleton
@Implements(MeasurementController.class)
public class DefaultMeasurementController implements MeasurementController {
    private static final ResourceLocation TAPE = ResourceLocation.create("labymod", "textures/measurement/tape.png");
    private final LabyAPI labyAPI;
    private final MeasurementConfig settings;
    private final List<DoubleVector3> measurementPoints = new ArrayList();
    private boolean active = false;
    private DoubleVector3 prevCurrentPosition = null;
    private final Laby3D laby3D = Laby.references().laby3D();

    @Inject
    public DefaultMeasurementController(LabyAPI labyAPI, HotkeyService hotkeyService) {
        this.labyAPI = labyAPI;
        this.settings = labyAPI.config().ingame().measurement();
        labyAPI.eventBus().registerListener(this);
        hotkeyService.register("toggleMeasurement", this.settings.toggleKey(), () -> {
            return HotkeyService.Type.TOGGLE;
        }, active -> {
            this.active = active.booleanValue();
            if (!this.measurementPoints.isEmpty() && this.measurementPoints.size() % 2 != 0) {
                this.measurementPoints.removeLast();
            }
            this.labyAPI.minecraft().chatExecutor().clearActionBar();
        });
        hotkeyService.register("clearMeasurement", this.settings.clearKey(), () -> {
            return HotkeyService.Type.HOLD;
        }, active2 -> {
            if (active2.booleanValue()) {
                clearPoints();
            }
        });
    }

    private void addMeasurementPoint() {
        DoubleVector3 position = getCurrentPosition(0.0f);
        if (position == null) {
            return;
        }
        this.measurementPoints.add(position);
        this.labyAPI.minecraft().chatExecutor().clearActionBar();
    }

    private void removeLastMeasurementPoint() {
        if (this.measurementPoints.isEmpty()) {
            return;
        }
        this.measurementPoints.removeLast();
        this.labyAPI.minecraft().chatExecutor().clearActionBar();
    }

    @Nullable
    private DoubleVector3 getCurrentPosition(float partialTicks) {
        ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player == null) {
            return null;
        }
        if (MinecraftVersions.V1_8_9.isCurrent()) {
            partialTicks = 1.0f;
        }
        DoubleVector3 target = player.getTargetBlock(100.0f, 5.0f, partialTicks);
        if (target == null) {
            return null;
        }
        double x = Math.round(target.getX() * 2.0d) / 2.0d;
        double y = Math.round(target.getY() * 2.0d) / 2.0d;
        double z = Math.round(target.getZ() * 2.0d) / 2.0d;
        return new DoubleVector3(x, y, z);
    }

    @Subscribe
    public void onServerSwitch(ServerSwitchEvent event) {
        clearPoints();
    }

    @Subscribe
    public void onSubServerSwitch(SubServerSwitchEvent event) {
        clearPoints();
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        if (!this.active || event.phase() != Phase.PRE) {
            return;
        }
        this.prevCurrentPosition = getCurrentPosition(0.0f);
        if (this.measurementPoints.size() % 2 != 0) {
            DoubleVector3 point1 = (DoubleVector3) this.measurementPoints.getLast();
            DoubleVector3 point2 = getCurrentPosition(0.0f);
            if (point1 == null || point2 == null) {
                return;
            }
            double x1 = point1.getX();
            double y1 = point1.getY();
            double z1 = point1.getZ();
            double x2 = point2.getX();
            double y2 = point2.getY();
            double z2 = point2.getZ();
            double distance = Math.sqrt(Math.pow(x1 - x2, 2.0d) + Math.pow(y1 - y2, 2.0d) + Math.pow(z1 - z2, 2.0d));
            this.labyAPI.minecraft().chatExecutor().displayActionBar((Component) Component.translatable("labymod.measurement.hint.distance", NamedTextColor.YELLOW).argument(Component.text(String.format(Locale.ROOT, "%.1f", Double.valueOf(distance)))), false);
            return;
        }
        this.labyAPI.minecraft().chatExecutor().displayActionBar((Component) Component.translatable("labymod.measurement.hint.controls", NamedTextColor.YELLOW), false);
    }

    @Subscribe
    public void onClientPlayerInteract(ClientPlayerInteractEvent event) {
        if (this.active) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void onMouseButton(MouseButtonEvent event) {
        if (this.active && this.labyAPI.minecraft().minecraftWindow().getCurrentVersionedScreen() == null && event.action() == MouseButtonEvent.Action.CLICK) {
            if (event.button().isLeft()) {
                event.setCancelled(true);
                addMeasurementPoint();
            }
            if (event.button().isRight()) {
                event.setCancelled(true);
                removeLastMeasurementPoint();
            }
        }
    }

    @Subscribe
    public void onRenderWorld(RenderWorldEvent event) {
        DoubleVector3 doubleVector3;
        DoubleVector3 doubleVector32;
        if (event.phase() != Phase.POST) {
            return;
        }
        if (!this.active && this.measurementPoints.isEmpty()) {
            return;
        }
        MinecraftCamera camera = event.camera();
        Stack stack = event.stack();
        float partialTicks = event.getPartialTicks();
        GFXRenderPipeline gfxPipeline = this.labyAPI.gfxRenderPipeline();
        ClientWorld world = this.labyAPI.minecraft().clientWorld();
        RenderEnvironmentContext env = gfxPipeline.renderEnvironmentContext();
        DoubleVector3 cameraPosition = camera.renderPosition();
        DoubleVector3 currentPosition = getCurrentPosition(partialTicks);
        if (currentPosition != null && this.prevCurrentPosition != null && !currentPosition.equals(this.prevCurrentPosition)) {
            currentPosition.add((-(currentPosition.getX() - this.prevCurrentPosition.getX())) * ((double) (1.0f - partialTicks)), (-(currentPosition.getY() - this.prevCurrentPosition.getY())) * ((double) (1.0f - partialTicks)), (-(currentPosition.getZ() - this.prevCurrentPosition.getZ())) * ((double) (1.0f - partialTicks)));
        }
        stack.push();
        int size = this.measurementPoints.size();
        double max = MathHelper.ceil((size / 2.0f) + (this.active ? 0.5f : 0.0f));
        for (int i = 0; i < max; i++) {
            boolean isMarker = this.active && ((double) (i + 1)) == max;
            boolean isTransparent = isMarker && size % 2 != 0;
            if (isMarker && !isTransparent) {
                doubleVector3 = currentPosition;
            } else {
                doubleVector3 = this.measurementPoints.get(i * 2);
            }
            DoubleVector3 point1 = doubleVector3;
            if (isMarker) {
                doubleVector32 = currentPosition;
            } else {
                doubleVector32 = this.measurementPoints.get((i * 2) + 1);
            }
            DoubleVector3 point2 = doubleVector32;
            if (point1 != null && point2 != null) {
                double x1 = point1.getX() - cameraPosition.getX();
                double y1 = point1.getY() - cameraPosition.getY();
                double z1 = point1.getZ() - cameraPosition.getZ();
                double x2 = point2.getX() - cameraPosition.getX();
                double y2 = (point2.getY() - cameraPosition.getY()) + 0.009999999776482582d;
                double z2 = point2.getZ() - cameraPosition.getZ();
                float shiftX = 0.0f;
                float shiftY = 0.0f;
                float shiftZ = 0.0f;
                double midX = point1.getX() + ((point2.getX() - point1.getX()) / 2.0d);
                double midY = point1.getY() + ((point2.getY() - point1.getY()) / 2.0d);
                double midZ = point1.getZ() + ((point2.getZ() - point1.getZ()) / 2.0d);
                boolean up = !world.getBlockState(MathHelper.floor(midX), MathHelper.floor(midY + 0.2d), MathHelper.floor(midZ)).block().isAir();
                boolean down = !world.getBlockState(MathHelper.floor(midX), MathHelper.floor(midY - 0.2d), MathHelper.floor(midZ)).block().isAir();
                float rotation = MathHelper.toDegreesFloat((float) Math.atan2(z1 - z2, x1 - x2));
                if (rotation == 0.0f) {
                    boolean north = !world.getBlockState(MathHelper.floor(midX), MathHelper.floor(midY), MathHelper.floor(midZ - 0.2d)).block().isAir();
                    boolean south = !world.getBlockState(MathHelper.floor(midX), MathHelper.floor(midY), MathHelper.floor(midZ + 0.2d)).block().isAir();
                    boolean east = !world.getBlockState(MathHelper.floor(midX + 0.2d), MathHelper.floor(midY), MathHelper.floor(midZ)).block().isAir();
                    boolean west = !world.getBlockState(MathHelper.floor(midX - 0.2d), MathHelper.floor(midY), MathHelper.floor(midZ)).block().isAir();
                    if (north && !south && east == west) {
                        rotation = 90.0f;
                        shiftZ = 0.02f;
                    }
                    if (south && !north && east == west) {
                        rotation = 90.0f;
                        shiftZ = -0.02f;
                    }
                    if (!west && east && north == south) {
                        shiftX = -0.02f;
                    }
                    if (!east && west && north == south) {
                        shiftX = 0.02f;
                    }
                }
                if (up && !down) {
                    shiftY = -0.02f;
                }
                double radians = MathHelper.toRadiansDouble(-rotation);
                float offsetX = MathHelper.sin(radians) * 0.1f;
                float offsetZ = MathHelper.cos(radians) * 0.1f;
                float distance = (float) Math.sqrt(Math.pow(x1 - x2, 2.0d) + Math.pow(y1 - y2, 2.0d) + Math.pow(z1 - z2, 2.0d));
                int light1 = world.getPackedLight(point1);
                int light1North = world.getPackedLight(point1.getX(), point1.getY(), point1.getZ() - 1.0d);
                int light1South = world.getPackedLight(point1.getX(), point1.getY(), point1.getZ() + 1.0d);
                int light1East = world.getPackedLight(point1.getX() + 1.0d, point1.getY(), point1.getZ());
                int light1West = world.getPackedLight(point1.getX() - 1.0d, point1.getY(), point1.getZ());
                int light2 = world.getPackedLight(point2);
                int light2North = world.getPackedLight(point2.getX(), point2.getY(), point2.getZ() - 1.0d);
                int light2South = world.getPackedLight(point2.getX(), point2.getY(), point2.getZ() + 1.0d);
                int light2East = world.getPackedLight(point2.getX() + 1.0d, point2.getY(), point2.getZ());
                int light2West = world.getPackedLight(point2.getX() - 1.0d, point2.getY(), point2.getZ());
                int light1Sky = Math.max(light1 >> 20, MathHelper.max(light1North >> 20, light1South >> 20, light1East >> 20, light1West >> 20));
                int light1Block = Math.max(light1 & 15, MathHelper.max(light1North & 15, light1South & 15, light1East & 15, light1West & 15));
                int light2Sky = Math.max(light2 >> 20, MathHelper.max(light2North >> 20, light2South >> 20, light2East >> 20, light2West >> 20));
                int light2Block = Math.max(light2 & 15, MathHelper.max(light2North & 15, light2South & 15, light2East & 15, light2West & 15));
                int light12 = (light1Sky << 20) | (light1Block << 4);
                int light22 = (light2Sky << 20) | (light2Block << 4);
                BufferBuilder buffer = this.laby3D.begin(DrawingMode.QUADS, VertexDescriptions.POSITION_COLOR_UV_LIGHTMAP);
                if (isMarker && !isTransparent) {
                    renderCross(stack, buffer, x1, y1, z1);
                } else {
                    int color = isTransparent ? -2130706433 : Color.WHITE.getRGB();
                    Matrix4f pose = stack.getProvider().getPose();
                    buffer.addVertex(pose, x1 + ((double) offsetX) + ((double) shiftX), y1 + ((double) shiftY), z1 + ((double) offsetZ) + ((double) shiftZ)).setColor(color).setUv(0.0f, 1.0f).setPackedLight(light12);
                    buffer.addVertex(pose, (x1 - ((double) offsetX)) + ((double) shiftX), y1 + ((double) shiftY), (z1 - ((double) offsetZ)) + ((double) shiftZ)).setColor(color).setUv(0.0f, 0.0f).setPackedLight(light12);
                    buffer.addVertex(pose, (x2 - ((double) offsetX)) + ((double) shiftX), y2 + ((double) shiftY), (z2 - ((double) offsetZ)) + ((double) shiftZ)).setColor(color).setUv(distance, 0.0f).setPackedLight(light22);
                    buffer.addVertex(pose, x2 + ((double) offsetX) + ((double) shiftX), y2 + ((double) shiftY), z2 + ((double) offsetZ) + ((double) shiftZ)).setColor(color).setUv(distance, 1.0f).setPackedLight(light22);
                }
                ShaderTextures.setShaderTexture(0, TAPE);
                MeshRenderer.drawImmediate(buffer.build(), RenderStates.SIMPLE_LEVEL_GEOMETRY);
                if (!isMarker || isTransparent) {
                    int color2 = isTransparent ? -2130706433 : Color.YELLOW.getRGB();
                    stack.push();
                    int prevPackedLight = env.getPackedLight();
                    env.setPackedLight(light12);
                    stack.translate(x1 + ((x2 - x1) / 2.0d), y1 + ((y2 - y1) / 2.0d) + 0.30000001192092896d, z1 + ((z2 - z1) / 2.0d));
                    stack.rotate(-camera.getYaw(), 0.0f, 1.0f, 0.0f);
                    stack.rotate(camera.getPitch(), 1.0f, 0.0f, 0.0f);
                    stack.translate(-0.01f, 0.0f, 0.0f);
                    this.labyAPI.renderPipeline().componentRenderer().builder().pos(0.0f, 0.0f).centered(true).scale(-0.02f).shadow(false).drawMode(TextDrawMode.SEE_THROUGH).color(color2).text(String.format(Locale.ROOT, "%.1f", Float.valueOf(distance)).replace(".0", "")).render(stack);
                    env.setPackedLight(prevPackedLight);
                    stack.pop();
                }
            }
        }
        stack.pop();
    }

    private void renderCross(Stack stack, VertexConsumer consumer, double x, double y, double z) {
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.025f), y, z + ((double) 0.15f), Face.BOTTOM);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.025f), y, z + ((double) 0.15f), Face.BOTTOM);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.025f), y, z - ((double) 0.15f), Face.BOTTOM);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.025f), y, z - ((double) 0.15f), Face.BOTTOM);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.15f), y, z - ((double) 0.025f), Face.BOTTOM);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.15f), y, z + ((double) 0.025f), Face.BOTTOM);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.15f), y, z - ((double) 0.025f), Face.BOTTOM);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.15f), y, z + ((double) 0.025f), Face.BOTTOM);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.025f), y, z + ((double) 0.15f), Face.TOP);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.025f), y, z + ((double) 0.15f), Face.TOP);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.025f), y, z - ((double) 0.15f), Face.TOP);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.025f), y, z - ((double) 0.15f), Face.TOP);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.15f), y, z - ((double) 0.025f), Face.TOP);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.15f), y, z + ((double) 0.025f), Face.TOP);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.15f), y, z - ((double) 0.025f), Face.TOP);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.15f), y, z + ((double) 0.025f), Face.TOP);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.025f), y + ((double) 0.15f), z, Face.NORTH);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.025f), y + ((double) 0.15f), z, Face.NORTH);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.025f), y - ((double) 0.15f), z, Face.NORTH);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.025f), y - ((double) 0.15f), z, Face.NORTH);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.15f), y - ((double) 0.025f), z, Face.NORTH);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.15f), y + ((double) 0.025f), z, Face.NORTH);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.15f), y - ((double) 0.025f), z, Face.NORTH);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.15f), y + ((double) 0.025f), z, Face.NORTH);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.025f), y + ((double) 0.15f), z, Face.SOUTH);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.025f), y + ((double) 0.15f), z, Face.SOUTH);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.025f), y - ((double) 0.15f), z, Face.SOUTH);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.025f), y - ((double) 0.15f), z, Face.SOUTH);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.15f), y - ((double) 0.025f), z, Face.SOUTH);
        testAndRenderRect(stack, consumer, x, y, z, x + ((double) 0.15f), y + ((double) 0.025f), z, Face.SOUTH);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.15f), y - ((double) 0.025f), z, Face.SOUTH);
        testAndRenderRect(stack, consumer, x, y, z, x - ((double) 0.15f), y + ((double) 0.025f), z, Face.SOUTH);
        testAndRenderRect(stack, consumer, x, y, z, x, y + ((double) 0.15f), z + ((double) 0.025f), Face.EAST);
        testAndRenderRect(stack, consumer, x, y, z, x, y + ((double) 0.15f), z - ((double) 0.025f), Face.EAST);
        testAndRenderRect(stack, consumer, x, y, z, x, y - ((double) 0.15f), z + ((double) 0.025f), Face.EAST);
        testAndRenderRect(stack, consumer, x, y, z, x, y - ((double) 0.15f), z - ((double) 0.025f), Face.EAST);
        testAndRenderRect(stack, consumer, x, y, z, x, y - ((double) 0.025f), z + ((double) 0.15f), Face.EAST);
        testAndRenderRect(stack, consumer, x, y, z, x, y + ((double) 0.025f), z + ((double) 0.15f), Face.EAST);
        testAndRenderRect(stack, consumer, x, y, z, x, y - ((double) 0.025f), z - ((double) 0.15f), Face.EAST);
        testAndRenderRect(stack, consumer, x, y, z, x, y + ((double) 0.025f), z - ((double) 0.15f), Face.EAST);
        testAndRenderRect(stack, consumer, x, y, z, x, y + ((double) 0.15f), z + ((double) 0.025f), Face.WEST);
        testAndRenderRect(stack, consumer, x, y, z, x, y + ((double) 0.15f), z - ((double) 0.025f), Face.WEST);
        testAndRenderRect(stack, consumer, x, y, z, x, y - ((double) 0.15f), z + ((double) 0.025f), Face.WEST);
        testAndRenderRect(stack, consumer, x, y, z, x, y - ((double) 0.15f), z - ((double) 0.025f), Face.WEST);
        testAndRenderRect(stack, consumer, x, y, z, x, y - ((double) 0.025f), z + ((double) 0.15f), Face.WEST);
        testAndRenderRect(stack, consumer, x, y, z, x, y + ((double) 0.025f), z + ((double) 0.15f), Face.WEST);
        testAndRenderRect(stack, consumer, x, y, z, x, y - ((double) 0.025f), z - ((double) 0.15f), Face.WEST);
        testAndRenderRect(stack, consumer, x, y, z, x, y + ((double) 0.025f), z - ((double) 0.15f), Face.WEST);
    }

    private void testAndRenderRect(Stack stack, VertexConsumer consumer, double x1, double y1, double z1, double x2, double y2, double z2, Face face) {
        MinecraftCamera camera = this.labyAPI.minecraft().getCamera();
        if (camera == null) {
            return;
        }
        DoubleVector3 position = camera.renderPosition();
        double cameraX = position.getX();
        double cameraY = position.getY();
        double cameraZ = position.getZ();
        double offsetX = x1 + ((x2 - x1) / 2.0d);
        double offsetY = y1 + ((y2 - y1) / 2.0d);
        double offsetZ = z1 + ((z2 - z1) / 2.0d);
        double faceOffsetX = ((double) face.getX()) / 3.0d;
        double faceOffsetY = ((double) face.getY()) / 3.0d;
        double faceOffsetZ = ((double) face.getZ()) / 3.0d;
        ClientWorld world = this.labyAPI.minecraft().clientWorld();
        BlockState behind = world.getBlockState(MathHelper.floor(cameraX + offsetX + faceOffsetX), MathHelper.floor(cameraY + offsetY + faceOffsetY), MathHelper.floor(cameraZ + offsetZ + faceOffsetZ));
        BlockState front = world.getBlockState(MathHelper.floor((cameraX + offsetX) - faceOffsetX), MathHelper.floor((cameraY + offsetY) - faceOffsetY), MathHelper.floor((cameraZ + offsetZ) - faceOffsetZ));
        boolean behindSolid = (behind.block().isAir() || behind.isFluid()) ? false : true;
        boolean frontSolid = (front.block().isAir() || front.isFluid()) ? false : true;
        Matrix4f pose = stack.getProvider().getPose();
        if (behindSolid && !frontSolid) {
            int color = Color.WHITE.getRGB();
            if (face.isYAxis()) {
                consumer.addVertex(pose, x1, y1, z1).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
                consumer.addVertex(pose, x1, y1, z2).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
                consumer.addVertex(pose, x2, y2, z2).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
                consumer.addVertex(pose, x2, y2, z1).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
            }
            if (face.isXAxis()) {
                consumer.addVertex(pose, x1, y1, z1).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
                consumer.addVertex(pose, x1, y2, z1).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
                consumer.addVertex(pose, x2, y2, z2).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
                consumer.addVertex(pose, x2, y1, z2).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
            }
            if (face.isZAxis()) {
                consumer.addVertex(pose, x1, y1, z1).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
                consumer.addVertex(pose, x1, y2, z1).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
                consumer.addVertex(pose, x2, y2, z2).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
                consumer.addVertex(pose, x2, y1, z2).setColor(color).setBlankUv().setPackedLight(RenderEnvironmentContext.FULL_BRIGHT);
            }
        }
    }

    private void clearPoints() {
        this.measurementPoints.clear();
    }

    @Override // net.labymod.api.client.controller.MeasurementController
    public List<DoubleVector3> getMeasurementPoints() {
        return this.measurementPoints;
    }
}
