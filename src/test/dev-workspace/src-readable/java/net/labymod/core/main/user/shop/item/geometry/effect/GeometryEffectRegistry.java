package net.labymod.core.main.user.shop.item.geometry.effect;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.color.ColorGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.color.GlowingGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.layer.ExtrudeGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.layer.LayerGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.metadata.MinecraftItemGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.physic.CurrentTimeGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.physic.HeadPhysicGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.physic.OrientationGeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.effects.physic.PhysicGeometryEffect;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/GeometryEffectRegistry.class */
public class GeometryEffectRegistry {
    private final Map<String, BiFunction<String, ModelPart, GeometryEffect>> effects = new HashMap();

    public GeometryEffectRegistry() {
        register();
    }

    private void register() {
        this.effects.put("physics", PhysicGeometryEffect::new);
        this.effects.put("headgravity", HeadPhysicGeometryEffect::new);
        this.effects.put("color", ColorGeometryEffect::new);
        this.effects.put("glow", GlowingGeometryEffect::new);
        this.effects.put("layer", LayerGeometryEffect::new);
        this.effects.put("extrude", ExtrudeGeometryEffect::new);
        this.effects.put("orientation", OrientationGeometryEffect::new);
        this.effects.put("currenttime", CurrentTimeGeometryEffect::new);
        this.effects.put("item", MinecraftItemGeometryEffect::new);
    }

    @Nullable
    public <T extends GeometryEffect> T create(String str, ModelPart modelPart) {
        GeometryEffect geometryEffectApply;
        if (!str.contains("_")) {
            return null;
        }
        BiFunction<String, ModelPart, GeometryEffect> biFunction = this.effects.get(str.substring(0, str.indexOf("_")));
        if (biFunction == null || (geometryEffectApply = biFunction.apply(str, modelPart)) == null) {
            return null;
        }
        return (T) geometryEffectApply.get();
    }
}
