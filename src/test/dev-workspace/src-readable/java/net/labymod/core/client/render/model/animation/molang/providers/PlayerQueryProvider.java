package net.labymod.core.client.render.model.animation.molang.providers;

import net.laby.lib.bedrock.molang.MolangContext;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.animation.AnimationRenderState;
import net.labymod.api.util.math.position.Position;
import net.labymod.core.client.render.model.animation.molang.MolangQueryProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/animation/molang/providers/PlayerQueryProvider.class */
public final class PlayerQueryProvider implements MolangQueryProvider {
    private static final double TICKS_PER_SECOND = 20.0d;

    @Override // net.labymod.core.client.render.model.animation.molang.MolangQueryProvider
    public void populate(MolangContext context, AnimationRenderState state, long progressMs) {
        Player player = state.player();
        if (player == null) {
            return;
        }
        Position pos = player.position();
        Position prev = player.previousPosition();
        double dx = pos.getX() - prev.getX();
        double dy = pos.getY() - prev.getY();
        double dz = pos.getZ() - prev.getZ();
        double horizontalDelta = Math.sqrt((dx * dx) + (dz * dz));
        context.setQuery("query.ground_speed", horizontalDelta * TICKS_PER_SECOND);
        context.setQuery("query.vertical_speed", dy * TICKS_PER_SECOND);
        context.setQuery("query.modified_distance_moved", player.getWalkDistance());
        context.setQuery("query.modified_move_speed", player.getWalkingSpeed());
        context.setQuery("query.is_moving", bool(horizontalDelta > 0.001d));
        context.setQuery("query.is_sneaking", bool(player.isCrouching()));
        context.setQuery("query.is_sprinting", bool(player.isSprinting()));
        context.setQuery("query.is_jumping", bool(!player.isOnGround() && dy > 0.0d));
        context.setQuery("query.is_on_ground", bool(player.isOnGround()));
        context.setQuery("query.is_in_water", bool(player.isInWater()));
        context.setQuery("query.is_under_water", bool(player.isUnderWater()));
        context.setQuery("query.is_in_lava", bool(player.isInLava()));
        context.setQuery("query.is_alive", bool(!player.isDead()));
        context.setQuery("query.is_on_fire", bool(player.isOnFire()));
        context.setQuery("query.is_onfire", bool(player.isOnFire()));
        context.setQuery("query.is_sleeping", bool(player.isSleeping()));
        context.setQuery("query.is_gliding", bool(player.isWearingElytra() && !player.isOnGround()));
        context.setQuery("query.is_swimming", bool(player.isInWater() && horizontalDelta > 0.001d));
        context.setQuery("query.is_riding", bool(player.getVehicle() != null));
        context.setQuery("query.is_invisible", bool(player.isInvisible()));
        context.setQuery("query.is_local_player", bool(isLocalPlayer(player)));
        context.setQuery("query.is_using_item", bool(player.getItemUseDurationTicks() > 0));
        context.setQuery("query.body_x_rotation", player.getRotationPitch());
        context.setQuery("query.body_y_rotation", player.getBodyRotationY());
        context.setQuery("query.head_x_rotation", player.getHeadRotationX());
        context.setQuery("query.head_y_rotation", player.getRotationHeadYaw());
        context.setQuery("query.health", player.getHealth());
        context.setQuery("query.max_health", player.getMaximalHealth());
        context.setQuery("query.hurt_time", player.getHurtTime());
        context.setQuery("query.swing_progress", player.getArmSwingProgress());
        context.setQuery("query.has_cape", bool(player.getCloakTexture() != null));
        context.setQuery("query.cape_flap_amount", capeFlapAmount(horizontalDelta));
    }

    private static boolean isLocalPlayer(Player player) {
        Player client = Laby.labyAPI().minecraft().getClientPlayer();
        return client != null && client.getUniqueId().equals(player.getUniqueId());
    }

    private static double capeFlapAmount(double horizontalDelta) {
        return Math.min(1.0d, horizontalDelta * 5.0d);
    }

    private static double bool(boolean value) {
        return value ? 1.0d : 0.0d;
    }
}
