package net.labymod.core.main.user.shop.cosmetic.pet.ai.goal;

import java.util.function.Supplier;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/goal/TeleportGoal.class */
public class TeleportGoal extends Goal {
    private final Supplier<Player> owner;
    private final float teleportDistance;

    public TeleportGoal(PetBehavior behavior, Supplier<Player> owner, float teleportDistance) {
        super(behavior);
        this.owner = owner;
        this.teleportDistance = teleportDistance;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public boolean canUse() {
        Player player = this.owner.get();
        if (player == null || player.gameMode() == GameMode.SPECTATOR || !player.isOnGround() || getDistanceSquared(player) < getTeleportDistance()) {
            return false;
        }
        return true;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.Goal
    public void start() {
        super.start();
        Player player = this.owner.get();
        behavior().teleportTo(player);
    }

    private float getTeleportDistance() {
        return MathHelper.square(this.teleportDistance);
    }

    private float getDistanceSquared(Player player) {
        return (float) player.getDistanceSquared(behavior().position());
    }
}
