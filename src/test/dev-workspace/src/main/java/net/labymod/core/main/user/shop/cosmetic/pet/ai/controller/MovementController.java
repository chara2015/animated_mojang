package net.labymod.core.main.user.shop.cosmetic.pet.ai.controller;

import java.util.List;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.math.Axis;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/controller/MovementController.class */
public class MovementController {
    private static final float BASE_JUMP_POWER = 0.22f;
    private static final float GRAVITY = 0.08f;
    private final PetBehavior behavior;
    private final JumpController jumpController;
    private final LookController lookController;
    private float yVelocity;
    private final FloatVector2 input = new FloatVector2();
    private int insideFluidTicks = 0;
    private int fakeJumpTicks = 0;
    private final ClientWorld level = Laby.labyAPI().minecraft().clientWorld();

    public MovementController(PetBehavior behavior) {
        this.behavior = behavior;
        this.jumpController = new JumpController(behavior, this.level);
        this.lookController = new LookController(behavior);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public FloatVector3 move(Player owner, FloatVector3 deltaMovement) throws MatchException {
        this.input.set(getImpulse(deltaMovement.getX()), getImpulse(deltaMovement.getZ()));
        boolean canJump = this.jumpController.canJump(this.input, deltaMovement.getX(), deltaMovement.getZ(), this.behavior.transform().rotation().getY());
        boolean isFakeJumping = this.fakeJumpTicks > 0;
        if (canJump || isFakeJumping) {
            deltaMovement.set(deltaMovement.getX(), BASE_JUMP_POWER, deltaMovement.getZ());
            if (!isFakeJumping) {
                this.fakeJumpTicks = 6;
            }
            this.behavior.setOnGround(false);
        }
        if (this.behavior.onGround()) {
            this.yVelocity = 0.0f;
        } else if (this.fakeJumpTicks == 0) {
            this.yVelocity += this.insideFluidTicks <= 0 ? 0.02f : GRAVITY;
            deltaMovement.add(0.0f, -this.yVelocity, 0.0f);
            deltaMovement.multiply(0.6f, 0.98f, 0.6f);
        }
        moveWithCollisions(deltaMovement);
        handleFluidMovement(deltaMovement);
        return deltaMovement;
    }

    private float getImpulse(double value) {
        if (value == 0.0d) {
            return (float) value;
        }
        return value < 0.0d ? -1.0f : 1.0f;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private boolean moveWithCollisions(FloatVector3 deltaMovement) throws MatchException {
        double x = deltaMovement.getX();
        double y = deltaMovement.getY();
        double z = deltaMovement.getZ();
        DoubleVector3 currentPosition = this.behavior.position();
        AxisAlignedBoundingBox boundingBox = this.behavior.boundingBox().move(currentPosition);
        List<AxisAlignedBoundingBox> blockCollisions = this.level.getBlockCollisions(boundingBox.expandTowards(deltaMovement));
        for (AxisAlignedBoundingBox collision : blockCollisions) {
            if (y != 0.0d) {
                y = collision.collide(Axis.Y, boundingBox, y);
            }
            boolean firstZ = Math.abs(x) < Math.abs(z);
            if (firstZ && z != 0.0d) {
                z = collision.collide(Axis.Z, boundingBox, z);
            }
            if (x != 0.0d) {
                x = collision.collide(Axis.X, boundingBox, x);
            }
            if (!firstZ && z != 0.0d) {
                z = collision.collide(Axis.Z, boundingBox, z);
            }
        }
        boolean horizontalX = x != x;
        boolean horizontalZ = z != z;
        this.behavior.setOnGround(y != y && y < 0.0d);
        if (y != y) {
            deltaMovement.setY((float) y);
        }
        if (horizontalX) {
            canStep(deltaMovement, currentPosition, blockCollisions, delta -> {
                delta.setX(0.0f);
            });
        }
        if (horizontalZ) {
            canStep(deltaMovement, currentPosition, blockCollisions, delta2 -> {
                delta2.setZ(0.0f);
            });
        }
        return horizontalX || horizontalZ;
    }

    private void canStep(FloatVector3 deltaMovement, DoubleVector3 currentPosition, List<AxisAlignedBoundingBox> blockCollisions, Consumer<FloatVector3> collidedConsumer) {
        float max = 0.0f;
        for (AxisAlignedBoundingBox blockCollision : blockCollisions) {
            DoubleVector3 center = blockCollision.getCenter();
            BlockState blockState = this.level.getBlockState(center);
            int blockY = MathHelper.floor(currentPosition.getY());
            if (blockState != null && blockState.position().getY() >= blockY) {
                double maxY = blockCollision.getMaxY() - ((double) blockState.position().getY());
                double diff = (((double) blockState.position().getY()) + maxY) - currentPosition.getY();
                if (diff > 0.625d) {
                    collidedConsumer.accept(deltaMovement);
                } else if (this.behavior.onGround()) {
                    float fMax = (float) Math.max(diff, max);
                    max = fMax;
                    deltaMovement.set(0.0f, fMax, 0.0f);
                } else {
                    collidedConsumer.accept(deltaMovement);
                }
            }
        }
    }

    public void tick() {
        if (this.insideFluidTicks > 0) {
            this.insideFluidTicks--;
        }
        if (this.fakeJumpTicks > 0) {
            this.fakeJumpTicks--;
        }
        this.jumpController.tick();
        this.lookController.tick();
    }

    private void handleFluidMovement(FloatVector3 deltaMovement) {
        BlockState blockState = this.level.getBlockState(this.behavior.position());
        if (blockState != null && blockState.isFluid()) {
            if (this.insideFluidTicks <= 0) {
                deltaMovement.add(0.0f, 0.2f, 0.0f);
            }
            deltaMovement.multiply(0.8f, 0.8f, 0.8f);
            this.behavior.setOnGround(false);
            resetGravityVelocity();
            return;
        }
        this.insideFluidTicks = 10;
    }

    public void resetGravityVelocity() {
        this.yVelocity = 0.0f;
    }

    public LookController lookController() {
        return this.lookController;
    }
}
