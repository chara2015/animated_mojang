package net.labymod.core.main.user.shop.item.geometry.effect.effects.physic;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/physic/CurrentTimeGeometryEffect.class */
public class CurrentTimeGeometryEffect extends GeometryEffect {
    private PhysicMapping mapping;
    private boolean mirror;
    private int cycle;
    private int offset;
    private int interval;

    public CurrentTimeGeometryEffect(String effectArgument, ModelPart modelPart) {
        super(effectArgument, modelPart, GeometryEffect.Type.PHYSIC, 5);
        this.mapping = PhysicMapping.X;
        this.cycle = 1;
        this.interval = 1;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    protected boolean processParameters() {
        String mappingString = getParameter(0, 1);
        String mirrorString = getParameter(1, 1);
        this.mapping = PhysicMapping.get(mappingString.charAt(0));
        this.mirror = mirrorString.charAt(0) == 'n';
        this.cycle = Math.max(1, Integer.parseInt(getParameter(2)));
        this.offset = Integer.parseInt(getParameter(3));
        this.interval = Math.max(1, Integer.parseInt(getParameter(4)));
        return true;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    public void apply(Player player, PlayerModel playerModel, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
        ZonedDateTime nowZoned = ZonedDateTime.now();
        Instant midnight = nowZoned.toLocalDate().atStartOfDay(nowZoned.getZone()).toInstant();
        Duration duration = Duration.between(midnight, Instant.now());
        long millisToday = duration.toMillis();
        long time = millisToday % (1000 * ((long) this.cycle));
        float seconds = (((int) (time / ((long) this.interval))) * this.interval) / 1000.0f;
        float progress = (seconds % this.cycle) + this.offset;
        float rotation = MathHelper.toRadiansFloat((360.0f / this.cycle) * progress * (this.mirror ? -1 : 1));
        FloatVector3 rotationVector = this.modelPart.getAnimationTransformation().getRotation();
        switch (this.mapping) {
            case X:
                rotationVector.setX(rotation);
                break;
            case Y:
                rotationVector.setY(rotation);
                break;
            case Z:
                rotationVector.setZ(rotation);
                break;
        }
    }
}
