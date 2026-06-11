package net.labymod.api.client.gui.screen.widget.widgets.minecraft.entity;

import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/minecraft/entity/EntityWidget.class */
@AutoWidget
public class EntityWidget extends SimpleWidget {
    private final LssProperty<Float> entityRotationX = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> entityRotationY = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> entityRotationZ = new LssProperty<>(Float.valueOf(0.0f));
    private final LssProperty<Float> entityScale = new LssProperty<>(Float.valueOf(1.0f));
    private final Entity entity;

    public EntityWidget(Entity entity) {
        this.entity = entity;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "Entity";
    }

    public Entity entity() {
        return this.entity;
    }

    public LssProperty<Float> entityRotationX() {
        return this.entityRotationX;
    }

    public LssProperty<Float> entityRotationY() {
        return this.entityRotationY;
    }

    public LssProperty<Float> entityRotationZ() {
        return this.entityRotationZ;
    }

    public LssProperty<Float> entityScale() {
        return this.entityScale;
    }
}
