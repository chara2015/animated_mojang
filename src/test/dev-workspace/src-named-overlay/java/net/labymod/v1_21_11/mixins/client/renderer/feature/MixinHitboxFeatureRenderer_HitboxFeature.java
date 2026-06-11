package net.labymod.v1_21_11.mixins.client.renderer.feature;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.util.Color;
import net.labymod.api.util.color.format.ColorFormat;
import net.minecraft.client.renderer.debug.EntityHitboxDebugRenderer;
import net.minecraft.gizmos.GizmoProperties;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/renderer/feature/MixinHitboxFeatureRenderer_HitboxFeature.class */
@Mixin({EntityHitboxDebugRenderer.class})
public class MixinHitboxFeatureRenderer_HitboxFeature {
    @WrapOperation(method = {"showHitboxes"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/gizmos/Gizmos;cuboid(Lnet/minecraft/world/phys/AABB;Lnet/minecraft/gizmos/GizmoStyle;)Lnet/minecraft/gizmos/GizmoProperties;")}, slice = {@Slice(from = @At("HEAD"), to = @At(value = "INVOKE", target = "Lnet/minecraft/gizmos/Gizmos;cuboid(Lnet/minecraft/world/phys/AABB;Lnet/minecraft/gizmos/GizmoStyle;)Lnet/minecraft/gizmos/GizmoProperties;", ordinal = 1))})
    private GizmoProperties labyMod$replaceBoxColor(AABB $$0, GizmoStyle $$1, Operation<GizmoProperties> original) {
        HitboxConfig hitbox = Laby.labyAPI().config().ingame().hitbox();
        if (!((Boolean) hitbox.enabled().get()).booleanValue()) {
            return (GizmoProperties) original.call(new Object[]{$$0, $$1});
        }
        return Gizmos.cuboid($$0, GizmoStyle.stroke(ColorFormat.ARGB32.withAlpha(((Color) hitbox.boxColor().get()).get(), 255), ((Float) hitbox.lineSize().get()).floatValue()));
    }

    @WrapOperation(method = {"showHitboxes"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/gizmos/Gizmos;cuboid(Lnet/minecraft/world/phys/AABB;Lnet/minecraft/gizmos/GizmoStyle;)Lnet/minecraft/gizmos/GizmoProperties;", ordinal = 2)})
    private GizmoProperties labyMod$replaceEyeBoxColor(AABB $$0, GizmoStyle $$1, Operation<GizmoProperties> original) {
        LabyConfig config = Laby.labyAPI().config();
        HitboxConfig hitbox = config.ingame().hitbox();
        boolean oldHitbox = ((Boolean) config.multiplayer().classicPvP().oldHitbox().get()).booleanValue();
        if (!((Boolean) hitbox.enabled().get()).booleanValue()) {
            if (!oldHitbox) {
                return (GizmoProperties) original.call(new Object[]{$$0, $$1});
            }
            return null;
        }
        if (!oldHitbox) {
            return Gizmos.cuboid($$0, GizmoStyle.stroke(ColorFormat.ARGB32.withAlpha(((Color) hitbox.eyeBoxColor().get()).get(), 255), ((Float) hitbox.lineSize().get()).floatValue()));
        }
        return null;
    }

    @WrapOperation(method = {"showHitboxes"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/gizmos/Gizmos;arrow(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;I)Lnet/minecraft/gizmos/GizmoProperties;")})
    private GizmoProperties labyMod$replaceArrowColor(Vec3 $$0, Vec3 $$1, int $$2, Operation<GizmoProperties> original) {
        HitboxConfig hitbox = Laby.labyAPI().config().ingame().hitbox();
        if (!((Boolean) hitbox.enabled().get()).booleanValue()) {
            return (GizmoProperties) original.call(new Object[]{$$0, $$1, Integer.valueOf($$2)});
        }
        return Gizmos.arrow($$0, $$1, ColorFormat.ARGB32.withAlpha(((Color) hitbox.eyeLineColor().get()).get(), 255), ((Float) hitbox.lineSize().get()).floatValue());
    }
}
