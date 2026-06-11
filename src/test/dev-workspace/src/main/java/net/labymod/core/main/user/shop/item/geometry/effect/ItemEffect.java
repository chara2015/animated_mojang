package net.labymod.core.main.user.shop.item.geometry.effect;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.collection.map.HashMultimap;
import net.labymod.api.util.collection.map.Multimap;
import net.labymod.core.main.debug.ErrorWrapper;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/ItemEffect.class */
public class ItemEffect {
    private final Supplier<Model> modelSupplier;
    private final Multimap<GeometryEffect.Type, GeometryEffect> effects = new HashMultimap();

    public ItemEffect(Supplier<Model> modelSupplier) {
        this.modelSupplier = modelSupplier;
    }

    public void loadEffects(List<GeometryEffect> effects) {
        for (GeometryEffect effect : effects) {
            Collection<GeometryEffect> geometryEffects = this.effects.get(effect.getType());
            geometryEffects.add(effect);
        }
    }

    public void apply(Player player, PlayerModel playerModel, EffectData effectData, ItemMetadata itemMetadata, GeometryEffect.Type type) {
        if (this.modelSupplier.get() == null) {
            return;
        }
        Collection<GeometryEffect> geometryEffects = this.effects.get(type);
        applyEffects(geometryEffects, player, playerModel, itemMetadata, effectData);
    }

    private void applyEffects(Collection<GeometryEffect> effects, Player player, PlayerModel playerModel, ItemMetadata itemMetadata, EffectData effectData) {
        for (GeometryEffect effect : effects) {
            ErrorWrapper.wrap(() -> {
                effect.apply(player, playerModel, itemMetadata, effectData);
            }, () -> {
                return "Geometry effect (" + effect.getName() + ")";
            });
        }
    }

    public Collection<GeometryEffect> getEffects(GeometryEffect.Type type) {
        return this.effects.get(type);
    }

    public Collection<GeometryEffect> getAllEffects() {
        return this.effects.values();
    }

    @Nullable
    public GeometryEffect findEffect(GeometryEffect.Type type, Predicate<GeometryEffect> effectFilter) {
        Collection<GeometryEffect> effects = getEffects(type);
        for (GeometryEffect effect : effects) {
            if (effectFilter.test(effect)) {
                return effect;
            }
        }
        return null;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/ItemEffect$EffectData.class */
    public static class EffectData {
        private float forward;
        private float strafe;
        private float gravity;
        private float renderYawOffset;
        private float pitch;
        private boolean slim;
        private boolean rightSide;

        public EffectData setPhysic(PhysicData data) {
            this.forward = data.getForward();
            this.strafe = data.getStrafe();
            this.pitch = data.getPitch();
            this.gravity = data.getGravity();
            this.renderYawOffset = data.getRenderYawOffset();
            return this;
        }

        public float getForward() {
            return this.forward;
        }

        public EffectData setForward(float forward) {
            this.forward = forward;
            return this;
        }

        public float getStrafe() {
            return this.strafe;
        }

        public EffectData setStrafe(float strafe) {
            this.strafe = strafe;
            return this;
        }

        public float getGravity() {
            return this.gravity;
        }

        public EffectData setGravity(float gravity) {
            this.gravity = gravity;
            return this;
        }

        public float getRenderYawOffset() {
            return this.renderYawOffset;
        }

        public EffectData setRenderYawOffset(float renderYawOffset) {
            this.renderYawOffset = renderYawOffset;
            return this;
        }

        public float getPitch() {
            return this.pitch;
        }

        public EffectData setPitch(float pitch) {
            this.pitch = pitch;
            return this;
        }

        public boolean isSlim() {
            return this.slim;
        }

        public EffectData setSlim(boolean slim) {
            this.slim = slim;
            return this;
        }

        public boolean isRightSide() {
            return this.rightSide;
        }

        public EffectData setRightSide(boolean rightSide) {
            this.rightSide = rightSide;
            return this;
        }
    }
}
