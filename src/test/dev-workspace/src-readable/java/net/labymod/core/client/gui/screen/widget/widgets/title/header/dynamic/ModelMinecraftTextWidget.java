package net.labymod.core.client.gui.screen.widget.widgets.title.header.dynamic;

import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.main.user.shop.item.geometry.animation.AnimationLoader;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.util.camera.spline.position.Location;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/title/header/dynamic/ModelMinecraftTextWidget.class */
@AutoWidget
public class ModelMinecraftTextWidget extends AbstractWidget<Widget> {
    private Model model;
    private AnimationLoader animationLoader;
    private AnimationController animationController;
    private CinematicCamera camera;

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        try {
            this.camera = new CinematicCamera(70.0f);
            ModelService modelService = Laby.references().modelService();
            this.model = modelService.loadBlockBenchModel(Constants.Resources.MINECRAFT_LOGO);
            this.animationLoader = new AnimationLoader(Constants.Resources.MINECRAFT_LOGO_ANIMATION);
            this.animationLoader.load();
            this.animationController = modelService.createAnimationController();
            this.animationController.tickProvider(() -> {
                return TimeUtil.convertMillisecondsToTicks(TimeUtil.getMillis() - getLastInitialTime());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void renderWidget(ScreenContext context) {
        super.renderWidget(context);
        if (this.camera == null || this.model == null || this.animationController == null) {
            return;
        }
        Window window = this.labyAPI.minecraft().minecraftWindow();
        ModelPart modelPart = this.model.getPart("logo");
        modelPart.getAnimationTransformation().setRotationX(MathHelper.toRadiansFloat(25.0f));
        Location location = new Location(0.0d, -3.0d, 0.0d, 0.0d, -90.0d, 0.0d);
        this.camera.teleport(location);
        Bounds bounds = window.bounds();
        this.camera.setup(bounds.getLeft(), bounds.getTop(), bounds.getRight(), bounds.getBottom(), context.getTickDelta());
        Matrix4f modelViewMatrix = new Matrix4f();
        modelViewMatrix.translate(0.0f, 0.0f, CinematicCamera.getZLevel());
        modelViewMatrix.mul(this.camera.viewMatrix());
        renderModel();
    }

    private void renderModel() {
        this.animationController.applyAnimation(this.model, new String[0]);
        ShaderTextures.setShaderTexture(0, Textures.Splash.MINECRAFT_SPRITE);
    }

    public void play() {
        ModelAnimation animation = this.animationLoader.getAnimation("assemble");
        if (animation != null) {
            this.animationController.playNext(animation);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.util.Disposable
    public void dispose() {
        super.dispose();
    }

    public AnimationLoader getAnimationLoader() {
        return this.animationLoader;
    }

    public AnimationController getAnimationController() {
        return this.animationController;
    }

    public CinematicCamera getCamera() {
        return this.camera;
    }
}
