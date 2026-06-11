package net.labymod.core.client.gui.screen.widget.widgets.title.header.dynamic;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.state.UVCoordinates;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.laby3d.pipeline.material.GuiMaterial;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/header/dynamic/AnimatedMinecraftTextWidget.class */
@AutoWidget
@Deprecated
public class AnimatedMinecraftTextWidget extends AbstractWidget<Widget> {
    private static boolean preLoaded = false;

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (!preLoaded) {
            preLoaded = true;
            preLoad();
        }
    }

    private void preLoad() {
        for (ResourceLocation resourceLocation : Textures.Splash.MINECRAFT_FRAMES) {
            Laby.references().textureRepository().preloadTexture(resourceLocation);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        int maxFrames = Textures.Splash.MINECRAFT_FRAMES.length;
        int lastFrame = ((8 * maxFrames) - 1) - 3;
        float timePassed = LabyMod.references().dynamicBackgroundController().getTimePassed();
        int frame = (int) MathHelper.clamp((timePassed - 1000.0f) / 40.0f, 0.0f, lastFrame);
        float ratio = 2403 / 749;
        int sprite = (frame / 8) % maxFrames;
        int offset = (frame % 8) * 749;
        Bounds bounds = bounds();
        float width = bounds.getWidth() * 1.8f;
        float height = width / ratio;
        ScreenCanvas renderState = context.canvas();
        renderState.submitGuiBlit(GuiMaterial.textured(RenderStates.GUI_TEXTURED, Textures.Splash.MINECRAFT_FRAMES[sprite]), bounds.getCenterX() - (width / 2.0f), bounds.getBottom() - height, width, height, UVCoordinates.of(0, offset, 2403, 749, 2403, 749 * 8), -1);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return 310.0f;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentHeight(BoundsType type) {
        return 51.0f;
    }
}
