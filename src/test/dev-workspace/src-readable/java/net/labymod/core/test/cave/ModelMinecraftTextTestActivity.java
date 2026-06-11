package net.labymod.core.test.cave;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.widget.widgets.title.header.dynamic.ModelMinecraftTextWidget;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/cave/ModelMinecraftTextTestActivity.class */
@Link("test/model-minecraft-logo.lss")
@AutoActivity
public class ModelMinecraftTextTestActivity extends TestActivity {
    private ModelMinecraftTextWidget widget;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.widget = new ModelMinecraftTextWidget();
        this.widget.addId("fractured-logo");
        ((Document) this.document).addChild(this.widget);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity
    protected void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        this.widget.play();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        ModelAnimation animation;
        super.render(context);
        AnimationController controller = this.widget.getAnimationController();
        if (this.widget == null || controller == null || (animation = controller.getPlaying()) == null) {
            return;
        }
        float width = bounds().getWidth();
        float timePassed = controller.getProgress();
        long length = animation.getLength();
        float percentage = MathHelper.clamp(timePassed / length, 0.0f, 1.0f);
        ScreenCanvas renderState = context.canvas();
        renderState.submitAbsoluteRect(0.0f, 0.0f, width, 5.0f, Integer.MIN_VALUE);
        renderState.submitAbsoluteRect(0.0f, 0.0f, width * percentage, 5.0f, -7851213);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        if (this.widget == null) {
            return;
        }
        AnimationController controller = this.widget.getAnimationController();
        if (!controller.isPlaying()) {
            reload();
        }
    }
}
