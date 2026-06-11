package net.labymod.core.client.entity.player;

import java.util.Collection;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.datawatcher.DataWatcher;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.SkinLayer;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.client.world.food.FoodData;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.position.DefaultPosition;
import net.labymod.api.util.math.position.Position;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.entity.player.abilities.NOPPlayerAbilities;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/DummyPlayer.class */
public class DummyPlayer implements Player {
    private static final ResourceLocation ENTITY_ID = ResourceLocation.create(Namespaces.MINECRAFT, "player");
    private final Position position;
    private final Position previousPosition;
    private final Position chasingPosition;
    private final Position previousChasingPosition;
    private GameUser gameUser;
    private boolean wearingElytra;
    private UUID uniqueId;

    public DummyPlayer(UUID uniqueId) {
        this.position = new DefaultPosition();
        this.previousPosition = new DefaultPosition();
        this.chasingPosition = new DefaultPosition();
        this.previousChasingPosition = new DefaultPosition();
        this.wearingElytra = false;
        this.uniqueId = uniqueId;
    }

    public DummyPlayer() {
        this(UUID.randomUUID());
    }

    @Override // net.labymod.api.client.entity.Entity
    public Position position() {
        return this.position;
    }

    @Override // net.labymod.api.client.entity.Entity
    public Position previousPosition() {
        return this.previousPosition;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isCrouching() {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInvisible() {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInvisibleFor(Player player) {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isSprinting() {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    @Override // net.labymod.api.client.entity.Entity
    public AxisAlignedBoundingBox axisAlignedBoundingBox() {
        return null;
    }

    @Override // net.labymod.api.client.entity.Entity
    public FloatVector3 perspectiveVector(float partialTicks) {
        return null;
    }

    @Override // net.labymod.api.client.entity.Entity
    public EntityPose entityPose() {
        return null;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean canEnterEntityPose(EntityPose pose) {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getEyeHeight() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.Entity
    public DataWatcher dataWatcher() {
        return null;
    }

    @Override // net.labymod.api.client.entity.Entity
    public TagType nameTagType() {
        return null;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setNameTagType(TagType type) {
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRendered(boolean rendered) {
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isRendered() {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInWater() {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isInLava() {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isUnderWater() {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isOnFire() {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public boolean isOnGround() {
        return false;
    }

    @Override // net.labymod.api.client.entity.Entity
    public Entity getVehicle() {
        return null;
    }

    @Override // net.labymod.api.client.entity.Entity
    public Component nameComponent() {
        return Component.empty();
    }

    @Override // net.labymod.api.client.entity.Entity
    public ResourceLocation entityId() {
        return ENTITY_ID;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public GameUser gameUser() {
        if (this.gameUser == null || this.gameUser.isDisposed()) {
            this.gameUser = Laby.references().gameUserService().gameUser(getUniqueId());
        }
        return this.gameUser;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
        this.gameUser = Laby.references().gameUserService().gameUser(uniqueId);
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHealth() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getMaximalHealth() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getAbsorptionHealth() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getItemUseDurationTicks() {
        return 0;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public LivingEntity.Hand getUsedItemHand() {
        return LivingEntity.Hand.MAIN_HAND;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getMainHandItemStack() {
        return null;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getOffHandItemStack() {
        return null;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getRightHandItemStack() {
        return null;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getLeftHandItemStack() {
        return null;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public ItemStack getEquipmentItemStack(LivingEntity.EquipmentSpot equipmentSpot) {
        return null;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getHurtTime() {
        return 0;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public int getDeathTime() {
        return 0;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isHostile() {
        return false;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public Collection<PotionEffect> getActivePotionEffects() {
        return null;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public double getMovementSpeedAttribute() {
        return 1.0d;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isSwingingHand() {
        return false;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getArmSwingProgress() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousArmSwingProgress() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isDead() {
        return false;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public String getName() {
        return null;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public Position chasingPosition() {
        return this.chasingPosition;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public Position previousChasingPosition() {
        return this.previousChasingPosition;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getBodyRotationY() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousBodyRotationY() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setBodyRotationY(float rotationY) {
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousBodyRotationY(float rotationY) {
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationX() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationX(float rotationX) {
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationX() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationX(float rotationX) {
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getHeadRotationY() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public float getPreviousHeadRotationY() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setHeadRotationY(float rotationY) {
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public void setPreviousHeadRotationY(float rotationY) {
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getCameraYaw() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousCameraYaw() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousWalkDistance() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getWalkDistance() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationYaw() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationYaw(float rotationYaw) {
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationYaw() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationYaw(float previousRotationYaw) {
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getRotationPitch() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setRotationPitch(float rotationPitch) {
    }

    @Override // net.labymod.api.client.entity.Entity
    public float getPreviousRotationPitch() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.Entity
    public void setPreviousRotationPitch(float previousRotationPitch) {
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousRotationHeadYaw() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getRotationHeadYaw() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousLimbSwingStrength() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getLimbSwingStrength() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getLimbSwing() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getRenderTick(float partialTicks) {
        ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
        if (clientPlayer == null) {
            return (TimeUtil.getMillis() % 10000000) / 50.0f;
        }
        return clientPlayer.getRenderTick(partialTicks);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.entity.player.Player
    public boolean isShownModelPart(PlayerClothes part) throws MatchException {
        MinecraftOptions options = Laby.labyAPI().minecraft().options();
        int modelParts = options.getModelParts();
        switch (part) {
            case CAPE:
                return SkinLayer.CAPE.isEnabled(modelParts);
            case JACKET:
                return SkinLayer.JACKET.isEnabled(modelParts);
            case LEFT_SLEEVE:
                return SkinLayer.LEFT_SLEEVE.isEnabled(modelParts);
            case RIGHT_SLEEVE:
                return SkinLayer.RIGHT_SLEEVE.isEnabled(modelParts);
            case LEFT_PANTS_LEG:
                return SkinLayer.LEFT_PANTS_LEG.isEnabled(modelParts);
            case RIGHT_PANTS_LEG:
                return SkinLayer.RIGHT_PANTS_LEG.isEnabled(modelParts);
            case HAT:
                return SkinLayer.HAT.isEnabled(modelParts);
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    @Override // net.labymod.api.client.entity.player.Player
    public double getSprintingSpeed() {
        return 0.0d;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public ResourceLocation skinTexture() {
        return Laby.references().mojangTextureService().getTexture(this.uniqueId, MojangTextureType.SKIN).getCompleted();
    }

    @Override // net.labymod.api.client.entity.player.Player
    @Nullable
    public ResourceLocation getCloakTexture() {
        return Laby.references().mojangTextureService().getTexture(this.uniqueId, MojangTextureType.CAPE).getCompleted();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isSlim() {
        return false;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public GameMode gameMode() {
        return null;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isWearingTop() {
        return false;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isWearingElytra() {
        return this.wearingElytra;
    }

    public void setWearingElytra(boolean wearingElytra) {
        this.wearingElytra = wearingElytra;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public GameProfile profile() {
        return null;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public FoodData foodData() {
        return null;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public PlayerAbilities abilities() {
        return NOPPlayerAbilities.INSTANCE;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getExperienceLevel() {
        return 0;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getTotalExperience() {
        return 0;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getExperienceProgress() {
        return 0.0f;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getExperienceNeededForNextLevel() {
        return 0;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public PlayerModel playerModel() {
        return null;
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public boolean isSleeping() {
        return false;
    }
}
