package net.labymod.api.client.gui.icon;

import java.util.Objects;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/icon/AnimatedIcon.class */
public class AnimatedIcon extends Icon {
    private final AnimatedResourceLocation location;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected AnimatedIcon(AnimatedResourceLocation location) {
        super(location::getCurrentResourceLocation, null);
        Objects.requireNonNull(location);
        this.location = location;
    }

    public static AnimatedIcon of(AnimatedResourceLocation location) {
        return new AnimatedIcon(location);
    }

    @Override // net.labymod.api.client.gui.icon.Icon
    public void render(RenderState renderState, Stack stack, float x, float y, float width, float height, boolean hover, int color, Rectangle stencil) {
        this.location.update();
        super.render(stack, x, y, width, height, hover, color, stencil);
    }

    public void update() {
        this.location.update();
    }

    @Override // net.labymod.api.client.gui.icon.Icon
    @Nullable
    public ResourceLocation getResourceLocation() {
        return this.location.getCurrentResourceLocation();
    }

    @Override // net.labymod.api.client.gui.icon.Icon
    public Icon resourceLocation(ResourceLocation resourceLocation) {
        return super.resourceLocation(resourceLocation);
    }
}
