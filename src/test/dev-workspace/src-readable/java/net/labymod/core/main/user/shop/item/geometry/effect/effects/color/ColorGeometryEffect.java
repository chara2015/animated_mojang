package net.labymod.core.main.user.shop.item.geometry.effect.effects.color;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.Color;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/color/ColorGeometryEffect.class */
public class ColorGeometryEffect extends GeometryEffect {
    private int index;
    private boolean rainbow;
    private long cycleDuration;

    public ColorGeometryEffect(String effectArgument, ModelPart model) {
        super(effectArgument, model, GeometryEffect.Type.BUFFER_CREATION, 1);
        this.index = 0;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    protected boolean processParameters() {
        try {
            String parameter = getParameter(0);
            if (parameter.equalsIgnoreCase("rainbow")) {
                this.rainbow = true;
                this.cycleDuration = Long.parseLong(getParameter(1));
            } else {
                this.index = Integer.parseInt(parameter);
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    public void apply(Player player, PlayerModel playerModel, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
        int colorIndex = this.index;
        Color[] itemColors = itemMetadata.getColors();
        if (this.rainbow) {
            setColor(Color.WHITE);
        } else if (itemColors.length > colorIndex) {
            setColor(itemColors[colorIndex]);
        }
    }

    private void setColor(Color color) {
        if (this.appearanceStore != null) {
            this.appearanceStore.setColor(this.appearance, color);
            this.appearanceStore.setRainbowCycle(this.appearance, this.rainbow ? Long.valueOf(this.cycleDuration) : null);
        }
    }

    public int getIndex() {
        return this.index;
    }
}
