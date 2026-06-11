package net.labymod.api.client.gui.screen.widget.widgets.minecraft.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.render.draw.HeartRenderer;
import net.labymod.api.util.HealthStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/minecraft/entity/HealthStatusWidget.class */
@AutoWidget
public class HealthStatusWidget extends SimpleWidget {
    private final HealthStatus healthStatus;
    private final LssProperty<Integer> heartSize = new LssProperty<>(9);
    private final HeartRenderer heartRenderer = Laby.references().heartRenderer();

    public HealthStatusWidget(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public String getDefaultRendererName() {
        return "HealthStatus";
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public float getContentWidth(BoundsType type) {
        return this.heartRenderer.getWidth(this.healthStatus, this.heartSize.get().intValue()) + 2.0f;
    }

    public HealthStatus healthStatus() {
        return this.healthStatus;
    }

    public HeartRenderer heartRenderer() {
        return this.heartRenderer;
    }

    public LssProperty<Integer> heartSize() {
        return this.heartSize;
    }
}
