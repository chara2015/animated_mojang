package net.labymod.core.main.user.shop.cosmetic.pet.ai;

import java.util.function.Supplier;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.controller.LookController;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.controller.MovementController;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.FollowOwnerGoal;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.LookAtPlayerGoal;
import net.labymod.core.main.user.shop.cosmetic.pet.ai.goal.TeleportGoal;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/pet/ai/DefaultPetBehavior.class */
public class DefaultPetBehavior implements PetBehavior {
    private final Supplier<Model> modelSupplier;
    private final float movementSpeed;
    private final Brain brain = new Brain();
    private final AxisAlignedBoundingBox boundingBox = new AxisAlignedBoundingBox();
    private final Transform transform = new Transform();
    private final MovementController movementController = new MovementController(this);
    private final FloatVector3 deltaMovement = new FloatVector3();
    private final IntVector3 blockPosition = new IntVector3();
    private final Supplier<Player> ownerSupplier;
    private boolean ground;
    private boolean walking;
    private int petIndex;
    private int maxPets;

    public DefaultPetBehavior(Supplier<Model> modelSupplier, float movementSpeed, Supplier<Player> ownerSupplier) {
        this.modelSupplier = modelSupplier;
        this.movementSpeed = movementSpeed;
        this.ownerSupplier = ownerSupplier;
        registerGoals();
    }

    private void registerGoals() {
        this.brain.addGoal(1, new FollowOwnerGoal(this, this.ownerSupplier, 5.0f, 0.5f));
        this.brain.addGoal(1, new TeleportGoal(this, this.ownerSupplier, 20.0f));
        this.brain.addGoal(2, new LookAtPlayerGoal(this, 8.0f));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public void tick() throws MatchException {
        this.transform.update();
        this.brain.tick();
        this.movementController.tick();
        move(deltaMovement());
        setDeltaMovement(FloatVector3.ZERO);
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public void setPosition(double x, double y, double z) {
        this.transform.set(x, y, z);
        int blockX = MathHelper.floor(x);
        int blockY = MathHelper.floor(y);
        int blockZ = MathHelper.floor(z);
        this.blockPosition.set(blockX, blockY, blockZ);
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public void addDeltaMovement(float x, float y, float z) {
        this.deltaMovement.add(x, y, z);
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public void setDeltaMovement(float x, float y, float z) {
        this.deltaMovement.set(x, y, z);
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public FloatVector3 deltaMovement() {
        return this.deltaMovement;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public Transform transform() {
        return this.transform;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public LookController lookController() {
        return this.movementController.lookController();
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public Model petModel() {
        return this.modelSupplier.get();
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public AxisAlignedBoundingBox boundingBox() {
        return this.boundingBox;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public boolean onGround() {
        return this.ground;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public void setOnGround(boolean ground) {
        this.ground = ground;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public void move(FloatVector3 deltaMovement) throws MatchException {
        this.walking = deltaMovement.lengthSquared() != 0.0d;
        FloatVector3 newDeltaMovement = this.movementController.move(this.ownerSupplier.get(), deltaMovement.copy());
        DoubleVector3 newPosition = position().copy().add(newDeltaMovement);
        setPosition(newPosition);
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public float getMovementSpeed() {
        return this.movementSpeed;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public void teleportTo(double x, double y, double z) {
        setPosition(x, y, z);
        this.movementController.resetGravityVelocity();
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public boolean isWalking() {
        return this.walking;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public int getPetIndex() {
        return this.petIndex;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public int getMaxPets() {
        return this.maxPets;
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.pet.ai.PetBehavior
    public void updatePetIndex(int petIndex, int maxPets) {
        this.petIndex = petIndex;
        this.maxPets = maxPets;
    }
}
