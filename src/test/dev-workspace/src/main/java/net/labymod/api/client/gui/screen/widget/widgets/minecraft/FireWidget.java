package net.labymod.api.client.gui.screen.widget.widgets.minecraft;

import net.labymod.api.Namespaces;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/minecraft/FireWidget.class */
@AutoWidget
public class FireWidget extends DivWidget {
    private final Icon fire0 = Icon.texture(ResourceLocation.create(Namespaces.MINECRAFT, "textures/block/fire_0.png"));
    private final Icon fire1 = Icon.texture(ResourceLocation.create(Namespaces.MINECRAFT, "textures/block/fire_1.png"));
    private int tick;

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        IconWidget fire0Widget = new IconWidget(this.fire0);
        fire0Widget.addId("fire-0");
        addChild(fire0Widget);
        IconWidget fire1Widget = new IconWidget(this.fire1);
        fire1Widget.addId("fire-1");
        addChild(fire1Widget);
        updateSprite(this.fire0, 0);
        updateSprite(this.fire1, 0);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
        int y = this.tick * 8;
        updateSprite(this.fire0, y);
        updateSprite(this.fire1, y);
        this.tick++;
        if (this.tick > 32) {
            this.tick = 0;
        }
    }

    private void updateSprite(Icon icon, int y) {
        icon.spritePosition(0, y);
        icon.spriteSize(256, 8);
    }
}
