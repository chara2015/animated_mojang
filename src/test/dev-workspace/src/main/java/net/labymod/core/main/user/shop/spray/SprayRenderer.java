package net.labymod.core.main.user.shop.spray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Singleton;
import net.laby.lib.sprays.Spray;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.level.SurfaceRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.mesh.MeshRenderer;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.ShutdownEvent;
import net.labymod.api.event.client.render.world.RenderWorldEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.shaders.block.SprayDataUniformBlock;
import net.labymod.api.laby3d.shaders.block.SprayDataUniformBlockData;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.collection.map.Multimap;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.user.shop.spray.model.SprayAssetProvider;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.vertex.VertexConsumer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/spray/SprayRenderer.class */
@Singleton
@Referenceable
public final class SprayRenderer {
    private static final float DEFAULT_RADIUS = 2.0f;
    private final EventBus eventBus;
    private final Laby3D laby3D;
    private final SurfaceRenderer surfaceRenderer;
    private final GFXRenderPipeline renderPipeline;
    private final SprayAssetProvider sprayAssetProvider;
    private final SprayService sprayService;
    private final SprayRegistry sprayRegistry;
    private final List<SprayObject> toRemove = new ArrayList();

    public SprayRenderer(EventBus eventBus, Laby3D laby3D, SurfaceRenderer surfaceRenderer, GFXRenderPipeline renderPipeline, SprayAssetProvider sprayAssetProvider, SprayService sprayService, SprayRegistry sprayRegistry) {
        this.eventBus = eventBus;
        this.laby3D = laby3D;
        this.surfaceRenderer = surfaceRenderer;
        this.renderPipeline = renderPipeline;
        this.sprayAssetProvider = sprayAssetProvider;
        this.sprayService = sprayService;
        this.sprayRegistry = sprayRegistry;
        this.eventBus.registerListener(this);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private void renderObjects(Stack stack, MinecraftCamera camera) throws MatchException {
        int packedLight;
        DoubleVector3 renderPosition = camera.renderPosition();
        double cameraX = renderPosition.getX();
        double cameraY = renderPosition.getY();
        double cameraZ = renderPosition.getZ();
        RenderEnvironmentContext context = this.renderPipeline.renderEnvironmentContext();
        int prevPacketLight = context.getPackedLight();
        Multimap<Direction, SprayObject> objects = this.sprayRegistry.getObjects();
        for (Direction direction : Direction.VALUES) {
            Collection<SprayObject> sprays = objects.get(direction);
            if (!sprays.isEmpty()) {
                stack.push();
                for (SprayObject spray : sprays) {
                    if (spray.isExpired()) {
                        this.toRemove.add(spray);
                    }
                    double x = spray.getX() - cameraX;
                    double y = spray.getY() - cameraY;
                    double z = spray.getZ() - cameraZ;
                    applyOffset(stack, direction);
                    ClientWorld level = Laby.labyAPI().minecraft().clientWorld();
                    DoubleVector3 position = spray.position();
                    switch (direction) {
                        case DOWN:
                            packedLight = level.getPackedLight(position.getX(), position.getY() - 1.0d, position.getZ());
                            break;
                        case UP:
                            packedLight = level.getPackedLight(position.getX(), position.getY() + 1.0d, position.getZ());
                            break;
                        case NORTH:
                            packedLight = level.getPackedLight(position.getX(), position.getY(), position.getZ() - 1.0d);
                            break;
                        case SOUTH:
                            packedLight = level.getPackedLight(position.getX(), position.getY(), position.getZ() + 1.0d);
                            break;
                        case WEST:
                            packedLight = level.getPackedLight(position.getX() - 1.0d, position.getY(), position.getZ());
                            break;
                        case EAST:
                            packedLight = level.getPackedLight(position.getX() + 1.0d, position.getY(), position.getZ());
                            break;
                        default:
                            throw new MatchException((String) null, (Throwable) null);
                    }
                    int packedLight2 = packedLight;
                    context.setPackedLight(packedLight2);
                    stack.push();
                    stack.translate(x, y, z);
                    rotateStack(stack, direction);
                    render(stack, spray);
                    stack.pop();
                }
                stack.pop();
                for (SprayObject sprayObject : this.toRemove) {
                    sprays.remove(sprayObject);
                    Multimap<GameUser, SprayObject> ownerSprays = this.sprayRegistry.getOwnerSprays();
                    ownerSprays.get(sprayObject.getOwner()).remove(sprayObject);
                }
                this.toRemove.clear();
            }
        }
        context.setPackedLight(prevPacketLight);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Subscribe
    public void onWorldRender(RenderWorldEvent event) throws MatchException {
        if (event.phase() != Phase.POST || !this.sprayRegistry.isEnabled()) {
            return;
        }
        MinecraftCamera camera = event.camera();
        Stack stack = event.stack();
        stack.push();
        renderObjects(stack, camera);
        stack.pop();
    }

    @Subscribe
    public void onShutdown(ShutdownEvent event) {
        if (event.stage().destroyResources() && this.surfaceRenderer != null) {
            this.surfaceRenderer.close();
        }
    }

    public void render(Stack stack, SprayObject object) {
        Spray spray = this.sprayService.findSpray(object.getId());
        if (spray == null) {
            return;
        }
        render(stack, object, spray);
    }

    public void render(Stack stack, SprayObject object, Spray spray) {
        CompletableResourceLocation diffuseTextureLocation = this.sprayAssetProvider.getTexture(spray, SprayAssetProvider.TextureType.DIFFUSE);
        CompletableResourceLocation wearTextureLocation = this.sprayAssetProvider.getTexture(spray, object.isFadeOut() ? SprayAssetProvider.TextureType.WEAR_FADE_OUT : SprayAssetProvider.TextureType.WEAR_FADE_IN);
        float duration = getDuration(object);
        float interpolatedDuration = MathHelper.lerp(duration, object.getPreviousDuration());
        object.setPreviousDuration(interpolatedDuration);
        VertexConsumer vertexConsumerBegin = this.laby3D.begin(DrawingMode.QUADS, VertexDescriptions.MODEL);
        this.surfaceRenderer.renderSurface(stack, vertexConsumerBegin, object.getX(), object.getY(), object.getZ(), object.direction(), DEFAULT_RADIUS, object.getRotation());
        ShaderTextures.setShaderTexture(0, diffuseTextureLocation.getCompleted());
        this.laby3D.setupOverlayAndLightingTextures();
        ShaderTextures.setShaderTexture(4, wearTextureLocation.getCompleted());
        MeshRenderer.drawImmediate(vertexConsumerBegin.build(), RenderStates.SPRAY, command -> {
            command.bindTexture(0, ShaderTextures.getShaderTexture(0));
            command.bindTexture(4, ShaderTextures.getShaderTexture(4));
            SprayDataUniformBlockData sprayData = new SprayDataUniformBlockData();
            sprayData.setWear(object.getPreviousDuration());
            command.bindUniformBlock(SprayDataUniformBlock.NAME, Laby.references().laby3D().sprayData());
            command.bindUniformBlockData(SprayDataUniformBlock.NAME, sprayData);
        });
    }

    private float getDuration(SprayObject object) {
        long time = object.getCreationTime() + 60000;
        long lifespan = TimeUtil.getMillis() + 60000;
        long duration = lifespan - time;
        float normalizedDuration = duration / 60000.0f;
        if (object.isFadeIn()) {
            normalizedDuration = Math.abs(normalizedDuration * 60.0f);
        } else if (object.isNormal()) {
            normalizedDuration = 1.0f;
        } else if (object.isFadeOut()) {
            normalizedDuration = (1.0f - normalizedDuration) * 60.0f;
        }
        return normalizedDuration;
    }

    private void rotateStack(Stack stack, Direction direction) {
        if (direction == Direction.UP) {
            stack.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            return;
        }
        if (direction == Direction.EAST) {
            stack.rotate(-90.0f, 0.0f, 1.0f, 0.0f);
            stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            stack.translate(0.0f, 0.0f, 0.001f);
            return;
        }
        if (direction == Direction.WEST) {
            stack.rotate(90.0f, 0.0f, 1.0f, 0.0f);
            stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
            stack.translate(0.0f, 0.0f, 0.001f);
        } else {
            if (direction == Direction.DOWN) {
                stack.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
                stack.rotate(180.0f, 0.0f, 0.0f, 1.0f);
                stack.scale(-1.0f, 1.0f, 1.0f);
                stack.translate(0.0f, 0.0f, -0.01f);
                return;
            }
            if (direction == Direction.SOUTH) {
                stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
                stack.translate(0.0f, 0.0f, -0.001f);
            } else if (direction == Direction.NORTH) {
                stack.rotate(180.0f, 1.0f, 0.0f, 0.0f);
                stack.translate(0.0f, 0.0f, 0.001f);
            }
        }
    }

    private void applyOffset(Stack stack, Direction direction) {
        applyOffset(stack, direction, 0);
    }

    private void applyOffset(Stack stack, Direction direction, int size) {
        if (size <= 0) {
            size = 1;
        }
        float offset = 1.0E-4f * size;
        if (direction == Direction.UP) {
            stack.translate(0.0f, offset, 0.0f);
            return;
        }
        if (direction == Direction.DOWN) {
            stack.translate(0.0f, -offset, 0.0f);
            return;
        }
        if (direction == Direction.NORTH) {
            stack.translate(0.0f, 0.0f, -offset);
            return;
        }
        if (direction == Direction.SOUTH) {
            stack.translate(0.0f, 0.0f, offset);
        } else if (direction == Direction.EAST) {
            stack.translate(offset, 0.0f, 0.0f);
        } else {
            stack.translate(-offset, 0.0f, 0.0f);
        }
    }
}
