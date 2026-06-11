package net.labymod.core.client.render.draw.builder;

import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.render.draw.builder.VanillaWindowBuilder;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.laby3d.pipeline.material.Material;
import net.labymod.api.models.Implements;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/builder/DefaultVanillaWindowBuilder.class */
@Singleton
@Implements(VanillaWindowBuilder.class)
public final class DefaultVanillaWindowBuilder implements VanillaWindowBuilder {
    private static final float TILE_SIZE = 16.0f;
    private static final float SEGMENT_HEIGHT = 8.0f;
    private final ResourceLocation textureLocation = Textures.POPUP_BACKGROUND;
    private boolean topLarge;
    private boolean bottomLarge;
    private boolean rescalable;
    private Rectangle position;

    @Inject
    public DefaultVanillaWindowBuilder() {
    }

    @Override // net.labymod.api.client.render.draw.builder.VanillaWindowBuilder
    public VanillaWindowBuilder top(boolean large) {
        this.topLarge = large;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.VanillaWindowBuilder
    public VanillaWindowBuilder bottom(boolean large) {
        this.bottomLarge = large;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.VanillaWindowBuilder
    public VanillaWindowBuilder rescalable(boolean rescalable) {
        this.rescalable = rescalable;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.VanillaWindowBuilder
    public VanillaWindowBuilder position(Rectangle rectangle) {
        this.position = rectangle;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.VanillaWindowBuilder
    public VanillaWindowBuilder render(ScreenContext context) {
        ScreenCanvas canvas = context.canvas();
        Objects.requireNonNull(this.position, "position must be non null");
        float x = this.position.getX();
        float y = this.position.getY();
        float width = this.position.getWidth();
        float height = this.position.getHeight();
        int segments = (int) Math.floor((height - 32.0f) / SEGMENT_HEIGHT);
        WindowSection section = this.topLarge ? WindowSection.TOP_LARGE : WindowSection.TOP;
        Material material = GuiMaterial.textured(RenderStates.GUI_TEXTURED, this.textureLocation);
        section.renderLine(canvas, material, x, y, width);
        float y2 = y + TILE_SIZE;
        WindowSection section2 = WindowSection.MIDDLE;
        for (int i = 0; i < segments; i++) {
            section2.renderLine(canvas, material, x, y2, width);
            y2 += SEGMENT_HEIGHT;
        }
        WindowSection section3 = this.bottomLarge ? WindowSection.BOTTOM_LARGE : WindowSection.BOTTOM;
        section3.renderLine(canvas, material, x, (this.position.getY() + height) - TILE_SIZE, width);
        if (this.rescalable) {
            Rectangle rescaleRect = Rectangle.relative((x + width) - 10.0f, (this.position.getY() + height) - 10.0f, SEGMENT_HEIGHT, SEGMENT_HEIGHT);
            boolean mouseOverRescale = context.mouse().isInside(rescaleRect);
            canvas.submitGuiBlit(material, rescaleRect, UVCoordinates.of(96 + (mouseOverRescale ? 8 : 0), 0.0f, rescaleRect.getWidth(), rescaleRect.getHeight(), 128, 64), -1);
        }
        reset();
        return this;
    }

    private void reset() {
        this.topLarge = false;
        this.bottomLarge = false;
        this.position = null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/builder/DefaultVanillaWindowBuilder$WindowSection.class */
    enum WindowSection {
        TOP(0.0f, 0.0f, DefaultVanillaWindowBuilder.TILE_SIZE, 0.0f, 32.0f, 0.0f),
        TOP_LARGE(48.0f, 0.0f, 64.0f, 0.0f, 80.0f, 0.0f),
        MIDDLE(0.0f, DefaultVanillaWindowBuilder.TILE_SIZE, DefaultVanillaWindowBuilder.TILE_SIZE, DefaultVanillaWindowBuilder.TILE_SIZE, 32.0f, DefaultVanillaWindowBuilder.TILE_SIZE),
        BOTTOM(0.0f, 32.0f, DefaultVanillaWindowBuilder.TILE_SIZE, 32.0f, 32.0f, 32.0f),
        BOTTOM_LARGE(48.0f, DefaultVanillaWindowBuilder.TILE_SIZE, 64.0f, DefaultVanillaWindowBuilder.TILE_SIZE, 80.0f, DefaultVanillaWindowBuilder.TILE_SIZE);

        private final float leftX;
        private final float leftY;
        private final float middleX;
        private final float middleY;
        private final float rightX;
        private final float rightY;

        WindowSection(float leftX, float leftY, float middleX, float middleY, float rightX, float rightY) {
            this.leftX = leftX;
            this.leftY = leftY;
            this.middleX = middleX;
            this.middleY = middleY;
            this.rightX = rightX;
            this.rightY = rightY;
        }

        public void renderLine(ScreenCanvas canvas, Material material, float x, float y, float width) {
            canvas.submitGuiBlit(material, x, y, DefaultVanillaWindowBuilder.TILE_SIZE, DefaultVanillaWindowBuilder.TILE_SIZE, UVCoordinates.of(this.leftX, this.leftY, DefaultVanillaWindowBuilder.TILE_SIZE, DefaultVanillaWindowBuilder.TILE_SIZE, 128, 64), -1);
            canvas.submitGuiBlit(material, x + DefaultVanillaWindowBuilder.TILE_SIZE, y, width - 32.0f, DefaultVanillaWindowBuilder.TILE_SIZE, UVCoordinates.of(this.middleX, this.middleY, DefaultVanillaWindowBuilder.TILE_SIZE, DefaultVanillaWindowBuilder.TILE_SIZE, 128, 64), -1);
            canvas.submitGuiBlit(material, (x + width) - DefaultVanillaWindowBuilder.TILE_SIZE, y, DefaultVanillaWindowBuilder.TILE_SIZE, DefaultVanillaWindowBuilder.TILE_SIZE, UVCoordinates.of(this.rightX, this.rightY, DefaultVanillaWindowBuilder.TILE_SIZE, DefaultVanillaWindowBuilder.TILE_SIZE, 128, 64), -1);
        }
    }
}
