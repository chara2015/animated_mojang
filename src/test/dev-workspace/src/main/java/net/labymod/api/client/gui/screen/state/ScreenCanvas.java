package net.labymod.api.client.gui.screen.state;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gfx.pipeline.renderer.text.FontRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.TextRenderer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.data.Sprite;
import net.labymod.api.client.gui.icon.AnimatedIcon;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.LayerSpatialHash;
import net.labymod.api.client.gui.screen.state.blitter.Blitter;
import net.labymod.api.client.gui.screen.state.debug.CanvasDebugSnapshot;
import net.labymod.api.client.gui.screen.state.debug.CanvasDebugger;
import net.labymod.api.client.gui.screen.state.offscreen.AtlasOffscreenRenderState;
import net.labymod.api.client.gui.screen.state.offscreen.DirectInlineModelComponent;
import net.labymod.api.client.gui.screen.state.offscreen.DynamicOffscreenRenderState;
import net.labymod.api.client.gui.screen.state.offscreen.OffscreenStrategy;
import net.labymod.api.client.gui.screen.state.states.GuiBlitRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiBlitRenderTargetRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiCircleRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiCustomGeometryRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiLineRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiPostProcessing;
import net.labymod.api.client.gui.screen.state.states.GuiRectangleOutlineRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiRectangleRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiRoundedBlitRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiTextureSet;
import net.labymod.api.client.gui.screen.state.states.GuiTrapezoidRenderState;
import net.labymod.api.client.gui.screen.state.states.GuiTriangleRenderState;
import net.labymod.api.client.gui.screen.state.states.commands.GuiClearComponent;
import net.labymod.api.client.gui.screen.state.states.commands.GuiCommandComponent;
import net.labymod.api.client.gui.screen.state.states.commands.GuiDebugMessageComponent;
import net.labymod.api.client.gui.screen.state.states.commands.GuiGenericTaskComponent;
import net.labymod.api.client.gui.screen.util.scissor.Scissor;
import net.labymod.api.client.gui.screen.util.scissor.ScissorArea;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.debug.DebugFlags;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.GradientDirection;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/ScreenCanvas.class */
public class ScreenCanvas implements LayeredComponent {
    private static final int SCISSOR_AREA_BORDER_SIZE = 1;
    private static final int SCISSOR_AREA_BOUNDS_TEXT_COLOR = -1;
    private static final float SCISSOR_AREA_BOUNDS_TEXT_SCALE = 0.5f;
    private static final float CIRCLE_STARTING_ANGLE = 0.0f;
    private static final float CIRCLE_ENDING_ANGLE = 360.0f;
    private final ScreenContext context;
    private final Blitter blitter;
    private final MojangTextureService mojangTextureService;
    private final Lazy<TextRendererProvider> textRendererProvider;
    private final Laby3D laby3D;
    private final Scissor scissor;
    private final TextSubmitter textSubmitter;
    private Matrix4f pose;
    private LayerNode current;

    @Nullable
    private LayerNode previousLayer;
    private Rectangle lastElementBounds;
    private boolean visualScissorArea;
    private static final int SCISSOR_AREA_BORDER_COLOR = ColorFormat.ARGB32.pack(119, 119, 255, 68);
    private static final int SCISSOR_AREA_COLOR = ColorFormat.ARGB32.pack(119, 119, 255, 136);
    private static final Vector3f CLIP_TRANSFORM_A = new Vector3f();
    private static final Vector3f CLIP_TRANSFORM_B = new Vector3f();
    private final List<LayerNode> layers = new ArrayList();
    private final LayerSpatialHash.Context spatialHashContext = new LayerSpatialHash.Context();
    private final Set<ScissorArea> scissorAreas = new HashSet();
    private boolean scissorActive = true;

    public ScreenCanvas(ScreenContext context) {
        this.context = context;
        nextLayer();
        this.blitter = new Blitter();
        this.scissor = new Scissor(context);
        this.laby3D = Laby.references().laby3D();
        this.mojangTextureService = Laby.references().mojangTextureService();
        this.textRendererProvider = Lazy.of(() -> {
            return Laby.references().textRendererProvider();
        });
        this.textSubmitter = new TextSubmitter(context, this);
    }

    public void nextLayer() {
        this.current = new LayerNode(null, this.spatialHashContext);
        this.layers.add(this.current);
        this.lastElementBounds = null;
    }

    @Deprecated(forRemoval = true, since = "4.4.8")
    public void up() {
        moveUp();
    }

    private void moveUp() {
        if (this.current.getUp() == null) {
            this.current.setUp(new LayerNode(this.current, this.spatialHashContext));
        }
        this.current = this.current.getUp();
    }

    @Deprecated(forRemoval = true, since = "4.4.8")
    public void down() {
        if (this.current.getDown() == null) {
            this.current.setDown(new LayerNode(this.current, this.spatialHashContext));
        }
        this.current = this.current.getDown();
    }

    @ApiStatus.Internal
    public void selectLayer(int index) {
        if (this.previousLayer != null) {
            throw new IllegalStateException("A layer is already selected");
        }
        if (index < 0 || index >= this.layers.size()) {
            throw new IndexOutOfBoundsException("Layer index " + index + " out of bounds for size " + this.layers.size());
        }
        this.previousLayer = this.current;
        this.current = this.layers.get(index);
        this.lastElementBounds = null;
    }

    @ApiStatus.Internal
    public void insertLayer(int index) {
        if (index < 0 || index > this.layers.size()) {
            throw new IndexOutOfBoundsException("Layer index " + index + " out of bounds for size " + this.layers.size());
        }
        LayerNode node = new LayerNode(null, this.spatialHashContext);
        this.layers.add(index, node);
    }

    @ApiStatus.Internal
    public void deselectLayer() {
        if (this.previousLayer == null) {
            throw new IllegalStateException("No layer is currently selected");
        }
        this.current = this.previousLayer;
        this.previousLayer = null;
        this.lastElementBounds = null;
    }

    @ApiStatus.Internal
    public int layerCount() {
        return this.layers.size();
    }

    public void setPose(Matrix4f pose) {
        this.pose = pose;
    }

    public void submitState(BiFunction<Matrix4f, ScissorArea, GuiComponent> component) {
        submitState(component.apply(currentPose(), getScissorArea()));
    }

    public void submitState(GuiComponent component) {
        if ((component instanceof GuiCommandComponent) || findAppropriateNode(component)) {
            submitComponent(component);
        }
    }

    private boolean findAppropriateNode(GuiComponent component) {
        Rectangle bounds = component.bounds();
        if (bounds == null) {
            return false;
        }
        if (this.lastElementBounds != null && this.lastElementBounds.encompasses(bounds)) {
            moveUp();
        } else {
            navigateToAboveHighestElementWithIntersectingBounds(bounds);
        }
        this.lastElementBounds = bounds;
        recordChainDepth();
        return true;
    }

    private void recordChainDepth() {
        int depth = 0;
        LayerNode node = this.current;
        while (node.getParent() != null) {
            node = node.getParent();
            depth++;
        }
        this.spatialHashContext.stats().recordChainDepth(depth);
    }

    private void navigateToAboveHighestElementWithIntersectingBounds(Rectangle bounds) {
        LayerNode node;
        LayerNode parent = this.current;
        while (true) {
            node = parent;
            if (node.getParent() == null) {
                break;
            } else {
                parent = node.getParent();
            }
        }
        while (node.getUp() != null) {
            node = node.getUp();
        }
        while (!node.hasIntersection(bounds)) {
            LayerNode parent2 = node.getParent();
            if (parent2 == null) {
                this.current = node;
                return;
            }
            node = parent2;
        }
        this.current = node;
        moveUp();
    }

    @Override // net.labymod.api.client.gui.screen.state.LayeredComponent
    public void submitComponent(GuiComponent component) {
        this.current.submit(component);
        if (DebugFlags.VISUAL_SCISSOR_AREA) {
            showScissorArea(component);
        }
    }

    public void submitClearColor(float red, float green, float blue, float alpha) {
        submitClear(1, red, green, blue, alpha, 0.0f, 255);
    }

    public void submitClearDepth(float depth) {
        submitClear(2, 0.0f, 0.0f, 0.0f, 0.0f, depth, 255);
    }

    public void pushClipShape(ClipShape clipShape) {
        Matrix4f pose = currentPose();
        Vector3f topLeft = pose.transformPosition(clipShape.left(), clipShape.top(), 0.0f, CLIP_TRANSFORM_A);
        Vector3f bottomRight = pose.transformPosition(clipShape.right(), clipShape.bottom(), 0.0f, CLIP_TRANSFORM_B);
        float transformedWidth = bottomRight.x - topLeft.x;
        float transformedHeight = bottomRight.y - topLeft.y;
        float scaleX = clipShape.width() != 0.0f ? transformedWidth / clipShape.width() : 1.0f;
        float scaleY = clipShape.height() != 0.0f ? transformedHeight / clipShape.height() : 1.0f;
        float radiusScale = Math.min(Math.abs(scaleX), Math.abs(scaleY));
        ClipShape transformed = new ClipShape(topLeft.x, topLeft.y, transformedWidth, transformedHeight, clipShape.topLeftRadius() * radiusScale, clipShape.topRightRadius() * radiusScale, clipShape.bottomRightRadius() * radiusScale, clipShape.bottomLeftRadius() * radiusScale);
        Laby.references().renderEnvironmentContext().renderAttributesStack().pushAndGet().setClipShape(transformed);
    }

    public void popClipShape() {
        Laby.references().renderEnvironmentContext().renderAttributesStack().pop();
    }

    public void submitClear(float red, float green, float blue, float alpha, float depth, int stencil) {
        submitClear(7, red, green, blue, alpha, depth, stencil);
    }

    private void submitClear(int mask, float red, float green, float blue, float alpha, float depth, int stencil) {
        submitState(new GuiClearComponent(new ClearInfo(mask, red, green, blue, alpha, depth, stencil)));
        nextLayer();
    }

    public void submitPushDebugGroup(String name) {
        submitState(new GuiDebugMessageComponent(name));
    }

    public void submitPopDebugGroup() {
        submitState(new GuiDebugMessageComponent(null));
    }

    @ApiStatus.Internal
    public void submitDynamicOffscreen(float left, float top, float right, float bottom, float scale, @Nullable RoundedData roundedData, DynamicOffscreenRenderState.DynamicRenderer renderer) {
        submitState(new DynamicOffscreenRenderState(currentPose(), left, top, right, bottom, scale, roundedData, renderer));
    }

    @ApiStatus.Internal
    public void submitDynamicOffscreen(Rectangle bounds, float scale, @Nullable RoundedData roundedData, DynamicOffscreenRenderState.DynamicRenderer renderer) {
        submitState(new DynamicOffscreenRenderState(currentPose(), bounds, scale, roundedData, renderer));
    }

    @ApiStatus.Internal
    public void submitModelRender(OffscreenStrategy strategy, Rectangle bounds, float scale, @Nullable RoundedData roundedData, DynamicOffscreenRenderState.DynamicRenderer renderer) {
        GuiComponent dynamicOffscreenRenderState;
        Matrix4f pose = currentPose();
        switch (strategy) {
            case DIRECT_INLINE:
                dynamicOffscreenRenderState = new DirectInlineModelComponent(pose, bounds, scale, roundedData, renderer);
                break;
            case ATLAS:
                dynamicOffscreenRenderState = new AtlasOffscreenRenderState(pose, bounds, scale, roundedData, renderer);
                break;
            default:
                dynamicOffscreenRenderState = new DynamicOffscreenRenderState(pose, bounds, scale, roundedData, renderer);
                break;
        }
        GuiComponent component = dynamicOffscreenRenderState;
        submitState(component);
    }

    public void submitRect(Rectangle rectangle, int argb) {
        submitRelativeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), argb);
    }

    public void submitAbsoluteRect(float left, float top, float right, float bottom, int argb) {
        submitRelativeRect(left, top, right - left, bottom - top, argb);
    }

    public void submitRelativeRect(float x, float y, float width, float height, int argb) {
        submitGuiRect(x, y, width, height, GuiRectangleRenderState.RectConfig.builder().setArgb(argb).build());
    }

    public void submitOutlineRect(Rectangle rectangle, float thickness, int innerArgb, int outerArgb) {
        submitAbsoluteOutlineRect(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), thickness, innerArgb, outerArgb);
    }

    public void submitAbsoluteOutlineRect(float left, float top, float right, float bottom, float thickness, int innerArgb, int outerArgb) {
        submitRelativeOutlineRect(left, top, right - left, bottom - top, thickness, innerArgb, outerArgb);
    }

    public void submitRelativeOutlineRect(float x, float y, float width, float height, float thickness, int innerArgb, int outerArgb) {
        submitState(new GuiRectangleOutlineRenderState(GuiMaterial.untextured(RenderStates.GUI), currentPose(), x, y, width, height, innerArgb, outerArgb, thickness, getScissorArea()));
    }

    public void submitGradientRect(GradientDirection direction, Rectangle rectangle, int argb0, int argb1) {
        submitRelativeGradientRect(direction, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), argb0, argb1);
    }

    public void submitAbsoluteGradientRect(GradientDirection direction, float left, float top, float right, float bottom, int argb0, int argb1) {
        submitRelativeGradientRect(direction, left, top, right - left, bottom - top, argb0, argb1);
    }

    public void submitRelativeGradientRect(GradientDirection direction, float x, float y, float width, float height, int argb0, int argb1) {
        submitGuiRect(x, y, width, height, GuiRectangleRenderState.RectConfig.builder().setGradient(direction, argb0, argb1).build());
    }

    public void submitGuiRect(Rectangle rectangle, GuiRectangleRenderState.RectConfig config) {
        submitGuiRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), config);
    }

    public void submitGuiRect(float x, float y, float width, float height, GuiRectangleRenderState.RectConfig config) {
        RenderState renderState;
        if (config.getRoundedData() == null) {
            renderState = RenderStates.GUI;
        } else {
            renderState = RenderStates.GUI_ROUNDED;
        }
        RenderState renderState2 = renderState;
        submitState(new GuiRectangleRenderState(GuiMaterial.untextured(renderState2), currentPose(), x, y, width, height, config, getScissorArea()));
    }

    public void submitRoundedRect(Rectangle rectangle, int argb, RoundedData roundedData) {
        submitRelativeRoundedRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), argb, roundedData);
    }

    public void submitAbsoluteRoundedRect(float left, float top, float right, float bottom, int argb, RoundedData roundedData) {
        submitRelativeRoundedRect(left, top, right - left, bottom - top, argb, roundedData);
    }

    public void submitRelativeRoundedRect(float x, float y, float width, float height, int argb, RoundedData roundedData) {
        submitGuiRect(x, y, width, height, GuiRectangleRenderState.RectConfig.builder().setArgb(argb).setRoundedData(roundedData).build());
    }

    public void submitCircle(float x, float y, float radius, int argb) {
        submitCircle(x, y, 0.0f, radius, argb);
    }

    public void submitCircle(float x, float y, float innerRadius, float outerRadius, int argb) {
        submitCircle(x, y, innerRadius, outerRadius, 0.0f, CIRCLE_ENDING_ANGLE, argb);
    }

    public void submitCircle(RenderState renderProgram, float x, float y, float innerRadius, float outerRadius, int argb) {
        submitCircle(renderProgram, x, y, innerRadius, outerRadius, 0.0f, CIRCLE_ENDING_ANGLE, argb);
    }

    public void submitCircle(float x, float y, float innerRadius, float outerRadius, float startingAngle, float endingAngle, int argb) {
        submitCircle(RenderStates.CIRCLE, x, y, innerRadius, outerRadius, startingAngle, endingAngle, argb);
    }

    public void submitCircle(RenderState renderState, float x, float y, float innerRadius, float outerRadius, float startingAngle, float endingAngle, int argb) {
        submitState(new GuiCircleRenderState(GuiMaterial.untextured(renderState), currentPose(), x, y, GuiRectangleRenderState.RectConfig.builder().setArgb(argb).build(), innerRadius, outerRadius, startingAngle, endingAngle, getScissorArea()));
    }

    public void submitIcon(Icon icon, Rectangle rectangle) {
        submitIcon(icon, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public void submitIcon(Icon icon, float x, float y, float width, float height) {
        submitIcon(icon, x, y, width, height, false, -1, null);
    }

    public void submitIcon(Icon icon, float x, float y, float width, float height, boolean hover, int argb) {
        submitIcon(icon, x, y, width, height, hover, argb, null);
    }

    public void submitIcon(Icon icon, float x, float y, float width, float height, boolean hover, int argb, @Nullable Rectangle clipBounds) {
        RenderState renderState;
        ResourceLocation resourceLocation = icon.getResourceLocation();
        if (resourceLocation == null) {
            return;
        }
        if (icon instanceof AnimatedIcon) {
            AnimatedIcon animatedIcon = (AnimatedIcon) icon;
            animatedIcon.update();
        }
        if (icon.isPlayerHead()) {
            submitPlayerFace(resourceLocation, x, y, width, height, argb, icon.isWearingHat());
            return;
        }
        float spriteX = getIconSpriteX(icon, hover);
        float spriteY = getIconSpriteY(icon, hover);
        float spriteWidth = icon.getSpriteWidth();
        float spriteHeight = icon.getSpriteHeight();
        float left = clipBounds == null ? x : clipBounds.getLeft();
        float top = clipBounds == null ? y : clipBounds.getTop();
        float right = clipBounds == null ? x + width : clipBounds.getRight();
        float bottom = clipBounds == null ? y + height : clipBounds.getBottom();
        BorderRadius borderRadius = icon.getBorderRadius();
        RoundedData roundedData = null;
        if (borderRadius != null) {
            FloatVector4 offset = this.context.stack().transformVector(left, top, right, bottom);
            roundedData = RoundedData.builder().applyBorderRadius(borderRadius).setBounds(left + offset.getX(), top + offset.getY(), right + offset.getZ(), bottom + offset.getW()).build();
        }
        TextureSprite textureSprite = icon.getTextureSprite(hover);
        if (textureSprite == null) {
            if (roundedData == null) {
                renderState = RenderStates.GUI_TEXTURED;
            } else {
                renderState = RenderStates.GUI_TEXTURED_ROUNDED;
            }
            RenderState renderState2 = renderState;
            submitGuiBlit(GuiMaterial.textured(renderState2, resourceLocation), x, y, width, height, UVCoordinates.of(spriteX, spriteY, spriteWidth, spriteHeight, icon.getResolutionWidth(), icon.getResolutionHeight()), argb, roundedData);
            return;
        }
        Sprite sprite = icon.sprite();
        float spriteWidth2 = sprite.getWidth();
        float spriteHeight2 = sprite.getHeight();
        this.blitter.blitSprite(this.context, resourceLocation, textureSprite, MathHelper.ceil(x), MathHelper.ceil(y), MathHelper.ceil(spriteWidth2 == 0.0f ? width : spriteWidth2), MathHelper.ceil(spriteHeight2 == 0.0f ? height : spriteHeight2), argb);
    }

    public void submitGuiSprite(TextureAtlas atlas, ResourceLocation spriteLocation, float x, float y, float width, float height, int argb) {
        submitGuiSprite(atlas.resource(), atlas.findSprite(spriteLocation), x, y, width, height, argb);
    }

    public void submitGuiSprite(ResourceLocation location, TextureSprite textureSprite, float x, float y, float width, float height, int argb) {
        this.blitter.blitSprite(this.context, location, textureSprite, MathHelper.ceil(x), MathHelper.ceil(y), MathHelper.ceil(width), MathHelper.ceil(height), argb);
    }

    public void submitGuiSprite(TextureAtlas atlas, ResourceLocation spriteLocation, int x, int y, int width, int height, int spriteX, int spriteY, int spriteWidth, int spriteHeight, int argb) {
        this.blitter.blitSprite(this.context, atlas, spriteLocation, x, y, width, height, spriteX, spriteY, spriteWidth, spriteHeight, argb);
    }

    public void submitPlayerFace(GameProfile profile, float x, float y, float width, float height, int argb, boolean wearingHat) {
        submitPlayerFace(profile.getUniqueId(), x, y, width, height, argb, wearingHat);
    }

    public void submitPlayerFace(UUID uuid, float x, float y, float width, float height, int argb, boolean wearingHat) {
        submitPlayerFace(this.mojangTextureService.getTexture(uuid, MojangTextureType.SKIN).getCompleted(), x, y, width, height, argb, wearingHat);
    }

    public void submitPlayerFace(ResourceLocation location, float x, float y, float width, float height, int argb, boolean wearingHat) {
        UVCoordinates coordinates = UVCoordinates.of(8, 8, 8, 8, 64, 64);
        Material faceMaterial = GuiMaterial.textured(RenderStates.GUI_TEXTURED, location);
        submitGuiBlit(faceMaterial, x, y, width, height, coordinates, argb);
        if (wearingHat) {
            UVCoordinates coordinates2 = UVCoordinates.of(40, 8, 8, 8, 64, 64);
            submitGuiBlit(faceMaterial, x, y, width, height, coordinates2, argb);
        }
    }

    @Deprecated(forRemoval = true, since = "4.3.37")
    public void submitGuiBlit(RenderState renderState, GuiTextureSet guiTextureSet, float x, float y, float width, float height, UVCoordinates coordinates, int argb) {
        submitGuiBlit(renderState, guiTextureSet, x, y, width, height, coordinates.getMinU(), coordinates.getMinV(), coordinates.getMaxU(), coordinates.getMaxV(), argb);
    }

    public void submitGuiBlit(Material material, float x, float y, float width, float height, UVCoordinates coordinates, int color) {
        submitGuiBlit(material, x, y, width, height, coordinates.getMinU(), coordinates.getMinV(), coordinates.getMaxU(), coordinates.getMaxV(), color);
    }

    public void submitGuiBlit(Material material, Rectangle rectangle, UVCoordinates coordinates, int argb) {
        submitGuiBlit(material, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), coordinates, argb);
    }

    public void submitGuiBlit(Material material, float x, float y, float width, float height, UVCoordinates coordinates, int color, @Nullable RoundedData roundedData) {
        submitGuiBlit(material, x, y, width, height, coordinates.getMinU(), coordinates.getMinV(), coordinates.getMaxU(), coordinates.getMaxV(), color, roundedData);
    }

    public void submitGuiBlit(RenderState renderState, GuiTextureSet guiTextureSet, float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, int argb) {
        submitGuiBlit(GuiMaterial.fromLegacy(renderState, guiTextureSet), x, y, width, height, minU, minV, maxU, maxV, argb, (RoundedData) null);
    }

    public void submitGuiBlit(Material material, float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, int color) {
        submitGuiBlit(material, x, y, width, height, minU, minV, maxU, maxV, color, (RoundedData) null);
    }

    public void submitGuiBlit(Material material, float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, int argb, @Nullable RoundedData roundedData) {
        submitGuiBlit(material, x, y, width, height, minU, minV, maxU, maxV, argb, roundedData, getScissorArea());
    }

    public void submitGuiBlit(Material material, float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, int color, @Nullable RoundedData roundedData, @Nullable ScissorArea scissorArea) {
        submitGuiBlit(material, currentPose(), x, y, width, height, minU, minV, maxU, maxV, color, roundedData, scissorArea);
    }

    public void submitGuiBlit(Material material, Matrix4f pose, float x, float y, float width, float height, float minU, float minV, float maxU, float maxV, int color, @Nullable RoundedData roundedData, @Nullable ScissorArea scissorArea) {
        if (roundedData == null) {
            submitState(new GuiBlitRenderState(material, pose, x, y, width, height, minU, minV, maxU, maxV, color, scissorArea));
        } else {
            submitState(new GuiRoundedBlitRenderState(material, pose, x, y, width, height, minU, minV, maxU, maxV, color, roundedData, scissorArea));
        }
    }

    public void submitRenderableComponent(RenderableComponent component, float x, float y, int argb, FontSize.PredefinedFontSize fontSize, int flags) {
        this.textSubmitter.submitRenderableComponent(component, x, y, argb, fontSize, flags);
    }

    public void submitText(String text, float x, float y, int argb, int backgroundArgb, float scale, int flags) {
        this.textSubmitter.submitText(text, x, y, argb, backgroundArgb, scale, flags);
    }

    public void submitRenderableComponent(RenderableComponent component, float x, float y, int argb, float scale, int flags) {
        this.textSubmitter.submitRenderableComponent(component, x, y, argb, scale, flags);
    }

    public void submitRenderableComponent(RenderableComponent component, float x, float y, int argb, int flags) {
        this.textSubmitter.submitRenderableComponent(component, x, y, argb, flags);
    }

    public void submitText(String text, float x, float y, int argb, int flags) {
        submitText(text, x, y, argb, 1.0f, flags);
    }

    public void submitText(String text, float x, float y, int argb, float scale, int flags) {
        this.textSubmitter.submitText(text, x, y, argb, scale, flags);
    }

    public void submitText(String text, float x, float y, int argb, FontSize.PredefinedFontSize fontSize, int flags) {
        this.textSubmitter.submitText(text, x, y, argb, fontSize, flags);
    }

    public void submitText(String text, float x, float y, int argb, int backgroundArgb, FontSize.PredefinedFontSize fontSize, int flags) {
        this.textSubmitter.submitText(text, x, y, argb, backgroundArgb, fontSize, flags);
    }

    public void submitText(String text, float x, float y, int argb, int backgroundArgb, int flags) {
        this.textSubmitter.submitText(text, x, y, argb, backgroundArgb, flags);
    }

    public void submitComponent(Component text, float x, float y, int argb, int flags) {
        submitComponent(text, x, y, argb, 1.0f, flags);
    }

    public void submitComponent(Component text, float x, float y, int argb, float scale, int flags) {
        this.textSubmitter.submitComponent(text, x, y, argb, scale, flags);
    }

    public void submitComponent(Component text, float x, float y, int argb, int backgroundArgb, float scale, int flags) {
        this.textSubmitter.submitComponent(text, x, y, argb, backgroundArgb, scale, flags);
    }

    public void submitComponent(Component text, float x, float y, int argb, FontSize.PredefinedFontSize fontSize, int flags) {
        this.textSubmitter.submitComponent(text, x, y, argb, fontSize, flags);
    }

    public void submitComponent(Component text, float x, float y, int argb, int backgroundArgb, FontSize.PredefinedFontSize fontSize, int flags) {
        this.textSubmitter.submitComponent(text, x, y, argb, backgroundArgb, fontSize, flags);
    }

    public void submitComponent(Component text, float x, float y, int argb, int backgroundArgb, int flags) {
        this.textSubmitter.submitComponent(text, x, y, argb, backgroundArgb, flags);
    }

    public void submitTriangle(float x, float y, float x1, float y1, float x2, float y2, int argb) {
        submitState(new GuiTriangleRenderState(currentPose(), x, y, x1, y1, x2, y2, argb, getScissorArea()));
    }

    public void submitTrapezoid(float x, float y, float x1, float y1, float x2, float y2, float x3, float y3, int argb) {
        submitState(new GuiTrapezoidRenderState(currentPose(), x, y, x1, y1, x2, y2, x3, y3, argb, getScissorArea()));
    }

    public void submitLine(float x, float y, float x1, float y1, int argb) {
        submitLineGradient(x, y, x1, y1, 1.0f, argb, argb);
    }

    public void submitLine(float x, float y, float x1, float y1, float lineWidth, int argb) {
        submitLineGradient(x, y, x1, y1, lineWidth, argb, argb);
    }

    public void submitLineGradient(float x, float y, float x1, float y1, int argb0, int argb1) {
        submitLineGradient(x, y, x1, y1, 1.0f, argb0, argb1);
    }

    public void submitLineGradient(float x, float y, float x1, float y1, float lineWidth, int argb0, int argb1) {
        submitState(new GuiLineRenderState(currentPose(), x, y, x1, y1, lineWidth, argb0, argb1, getScissorArea()));
    }

    public void submitBlitRenderTarget(float x, float y, float width, float height, RenderTarget destination, RenderTarget... targets) {
        submitState(new GuiBlitRenderTargetRenderState(currentPose(), x, y, width, height, destination, targets));
    }

    public void submitPostProcessing(RenderState renderState, RenderTarget source, RenderTarget destination, @NotNull Consumer<CommandBuffer> commandConsumer) {
        submitPostProcessing(renderState, source, destination, 0.0f, 0.0f, destination.width(), destination.height(), commandConsumer);
    }

    public void submitPostProcessing(RenderState renderState, RenderTarget source, RenderTarget destination, float x, float y, float width, float height, @NotNull Consumer<CommandBuffer> commandConsumer) {
        Material material = GuiMaterial.builder(renderState).setTexture(0, source.findColorTexture(0)).build();
        submitState(new GuiPostProcessing(material, source, destination, currentPose(), x, y, width, height, commandConsumer));
    }

    public void submitCustomGeometry(RenderState renderState, float x, float y, float width, float height, GuiCustomGeometryRenderState.GeometryRenderer renderer, Consumer<CommandBuffer> commandConsumer) {
        submitCustomGeometry(GuiMaterial.untextured(renderState), x, y, width, height, renderer, commandConsumer);
    }

    public void submitCustomGeometry(Material material, float x, float y, float width, float height, GuiCustomGeometryRenderState.GeometryRenderer renderer) {
        submitCustomGeometry(material, x, y, width, height, renderer, (Consumer<CommandBuffer>) null);
    }

    public void submitCustomGeometry(Material material, float x, float y, float width, float height, GuiCustomGeometryRenderState.GeometryRenderer renderer, Consumer<CommandBuffer> commandConsumer) {
        submitState(new GuiCustomGeometryRenderState(material, currentPose(), x, y, width, height, getScissorArea(), renderer, commandConsumer));
    }

    public void submitTask(@NotNull Runnable task) {
        submitState(new GuiGenericTaskComponent(task));
    }

    public float getLineHeight() {
        return getCurrentTextRenderer().getLineHeight();
    }

    public float getTextWidth(String text) {
        return getCurrentTextRenderer().getWidth(text);
    }

    public float getTextWidth(Component text) {
        return getCurrentTextRenderer().getWidth(text);
    }

    public float getTextWidth(FormattedTextLayout layout) {
        return getCurrentTextRenderer().getWidth(layout);
    }

    @Override // net.labymod.api.client.gui.screen.state.LayeredComponent
    public void forEachItem(LayeredElementConsumer consumer) {
        LayerNode currentLayer = this.current;
        traverse(node -> {
            List<GuiComponent> states = node.getStates();
            if (states.isEmpty()) {
                return;
            }
            this.current = node;
            int size = states.size();
            for (int index = 0; index < size; index++) {
                GuiComponent state = states.get(index);
                consumer.accept(state);
            }
        });
        this.current = currentLayer;
    }

    public boolean hasPendingComponents() {
        boolean[] found = {false};
        traverse(node -> {
            if (!node.getStates().isEmpty()) {
                found[0] = true;
            }
        });
        return found[0];
    }

    public List<CanvasSnapshot> captureSnapshot() {
        if (!hasPendingComponents()) {
            return new ArrayList();
        }
        List<CanvasSnapshot> snapshots = new ArrayList<>();
        for (LayerNode layer : this.layers) {
            List<GuiComponent> states = new ArrayList<>();
            layer.traverse(node -> {
                states.addAll(node.getStates());
            });
            if (!states.isEmpty()) {
                captureLayerSnapshots(states, snapshots);
            }
        }
        if (CanvasDebugger.isCaptureEnabled()) {
            captureDebugSnapshots(snapshots);
        }
        reset();
        return snapshots;
    }

    private void captureDebugSnapshots(List<CanvasSnapshot> snapshots) {
        List<CanvasDebugSnapshot> debugSnapshots = new ArrayList<>(snapshots.size());
        for (int index = 0; index < snapshots.size(); index++) {
            debugSnapshots.add(CanvasDebugSnapshot.of(index, snapshots.get(index).capturedComponents()));
        }
        CanvasDebugger.setSnapshots(debugSnapshots);
    }

    private void captureLayerSnapshots(List<GuiComponent> states, List<CanvasSnapshot> snapshots) {
        boolean hasPostProcessing = false;
        Iterator<GuiComponent> it = states.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            GuiComponent state = it.next();
            if (state instanceof GuiPostProcessing) {
                hasPostProcessing = true;
                break;
            }
        }
        if (!hasPostProcessing) {
            snapshots.add(new CanvasSnapshot(states));
            return;
        }
        List<GuiComponent> currentRange = new ArrayList<>();
        for (GuiComponent component : states) {
            if (component instanceof GuiPostProcessing) {
                if (!currentRange.isEmpty()) {
                    snapshots.add(new CanvasSnapshot(currentRange));
                    currentRange = new ArrayList<>();
                }
                List<GuiComponent> postProcessing = new ArrayList<>();
                postProcessing.add(component);
                snapshots.add(new CanvasSnapshot(postProcessing));
            } else {
                currentRange.add(component);
            }
        }
        if (!currentRange.isEmpty()) {
            snapshots.add(new CanvasSnapshot(currentRange));
        }
    }

    public void reset() {
        this.layers.clear();
        this.lastElementBounds = null;
        this.spatialHashContext.resetStats();
        nextLayer();
        if (DebugFlags.VISUAL_SCISSOR_AREA) {
            this.scissorAreas.clear();
        }
    }

    public Scissor scissor() {
        return this.scissor;
    }

    public void enableScissor() {
        this.scissorActive = true;
    }

    public void disableScissor() {
        this.scissorActive = false;
    }

    public ScissorArea getScissorArea() {
        if (this.visualScissorArea || !this.scissorActive) {
            return null;
        }
        return this.scissor.getScissorArea();
    }

    FontRenderer getCurrentTextRenderer() {
        return getTextRenderer().getCurrent();
    }

    TextRenderer getTextRenderer() {
        return this.textRendererProvider.get().getRenderer();
    }

    Matrix4f currentPose() {
        Matrix4f pose;
        if (this.pose == null) {
            Matrix4f acquiredMatrix = this.laby3D.matrices().acquire();
            pose = acquiredMatrix.set(this.context.stack().getProvider().getPose());
        } else {
            pose = this.pose;
        }
        return JomlMath.flattenTo2D(pose);
    }

    private void traverse(Consumer<LayerNode> consumer) {
        int max = this.layers.size();
        for (int index = 0; index < max; index++) {
            LayerNode layer = this.layers.get(index);
            layer.traverse(consumer);
        }
    }

    private float getIconSpriteX(Icon icon, boolean hover) {
        Sprite sprite = icon.sprite();
        return sprite.getX() + (hover ? icon.getSpriteHoverOffsetX() : 0);
    }

    private float getIconSpriteY(Icon icon, boolean hover) {
        Sprite sprite = icon.sprite();
        return sprite.getY() + (hover ? icon.getSpriteHoverOffsetY() : 0);
    }

    private void showScissorArea(GuiComponent renderState) {
        ScissorArea scissorArea;
        Stack stack = this.context.stack();
        if (stack == null || stack.getProvider() == null || (scissorArea = renderState.getScissorArea()) == null || !this.scissorAreas.add(scissorArea)) {
            return;
        }
        this.visualScissorArea = true;
        stack.push();
        stack.identity();
        stack.mul(scissorArea.getPose());
        Rectangle bounds = scissorArea.bounds();
        float left = bounds.getLeft() + 1.0f;
        float top = bounds.getTop() + 1.0f;
        float right = bounds.getRight() - 1.0f;
        float bottom = bounds.getBottom() - 1.0f;
        submitAbsoluteRect(left, top, right, bottom, SCISSOR_AREA_COLOR);
        submitAbsoluteOutlineRect(left, top, right, bottom, 1.0f, SCISSOR_AREA_BORDER_COLOR, SCISSOR_AREA_BORDER_COLOR);
        submitText(scissorArea.bounds().toString(), left, top, -1, 0.5f, 1);
        stack.pop();
        this.visualScissorArea = false;
    }

    @Override // net.labymod.api.client.gui.screen.state.LayeredComponent
    public final void markPreparation() {
        this.layers.forEach((v0) -> {
            v0.prepareLayer();
        });
    }

    @Override // net.labymod.api.client.gui.screen.state.LayeredComponent
    public final void finishPreparation() {
        this.layers.forEach((v0) -> {
            v0.finalizePreparation();
        });
    }
}
