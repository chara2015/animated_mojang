package net.labymod.core.test.gfx;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.AnimatedIcon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.core.test.TestActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/test/gfx/AnimatedIconTestActivity.class */
@Link("test/test-menu.lss")
@AutoActivity
public class AnimatedIconTestActivity extends TestActivity {
    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        AnimatedResourceLocation.Builder builder = Laby.references().resourceLocationFactory().builder();
        AnimatedResourceLocation animatedResourceLocation = builder.resourceLocations("labymod", "textures/spinner/spinner", 30).delay(33L).build();
        IconWidget widget = new IconWidget(AnimatedIcon.of(animatedResourceLocation));
        widget.addId("animated-icon");
        ((Document) this.document).addChild(widget);
    }
}
