package net.labymod.v1_21_11.mixins.client.player;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.client.entity.player.abilities.BooleanAbilityOption;
import net.labymod.api.client.entity.player.abilities.FloatAbilityOption;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.food.FoodData;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import net.labymod.api.mojang.GameProfile;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.math.position.DynamicPositionWithOwner;
import net.labymod.api.util.math.position.Position;
import net.labymod.core.client.entity.player.abilities.DefaultPlayerAbilities;
import net.labymod.v1_21_11.client.player.ClientAvatarStateAccessor;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.labymod.v1_21_11.mixins.client.entity.MixinLivingEntity;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.entity.player.PlayerModelType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/player/MixinAbstractLocalPlayer.class */
@Mixin({AbstractClientPlayer.class})
@Implements({@Interface(iface = Player.class, prefix = "player$", remap = Interface.Remap.NONE)})
public abstract class MixinAbstractLocalPlayer extends MixinLivingEntity implements Player {
    private final PlayerAbilities labyMod$playerAbilities = new DefaultPlayerAbilities(new BooleanAbilityOption(() -> {
        return labyMod$abilities().invulnerable;
    }, value -> {
        labyMod$abilities().invulnerable = value;
    }), new BooleanAbilityOption(() -> {
        return labyMod$abilities().flying;
    }, value2 -> {
        labyMod$abilities().flying = value2;
    }), new BooleanAbilityOption(() -> {
        return labyMod$abilities().mayfly;
    }, value3 -> {
        labyMod$abilities().mayfly = value3;
    }), new BooleanAbilityOption(() -> {
        return labyMod$abilities().instabuild;
    }, value4 -> {
        labyMod$abilities().instabuild = value4;
    }), new BooleanAbilityOption(() -> {
        return labyMod$abilities().mayBuild;
    }, value5 -> {
        labyMod$abilities().mayBuild = value5;
    }), new FloatAbilityOption(() -> {
        return labyMod$abilities().getFlyingSpeed();
    }, value6 -> {
        labyMod$abilities().setFlyingSpeed(value6);
    }), new FloatAbilityOption(() -> {
        return labyMod$abilities().getWalkingSpeed();
    }, value7 -> {
        labyMod$abilities().setWalkingSpeed(value7);
    }));
    private final Position labyMod$chasingPosition = new DynamicPositionWithOwner(() -> {
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().avatarState());
    }, (v0) -> {
        return v0.labyMod$getXCloak();
    }, (v0, v1) -> {
        v0.labyMod$setXCloak(v1);
    }, (v0) -> {
        return v0.labyMod$getYCloak();
    }, (v0, v1) -> {
        v0.labyMod$setYCloak(v1);
    }, (v0) -> {
        return v0.labyMod$getZCloak();
    }, (v0, v1) -> {
        v0.labyMod$setZCloak(v1);
    });
    private final Position labyMod$previousChasingPosition = new DynamicPositionWithOwner(() -> {
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().avatarState());
    }, (v0) -> {
        return v0.labyMod$getXCloakO();
    }, (v0, v1) -> {
        v0.labyMod$setXCloakO(v1);
    }, (v0) -> {
        return v0.labyMod$getYCloakO();
    }, (v0, v1) -> {
        v0.labyMod$setYCloakO(v1);
    }, (v0) -> {
        return v0.labyMod$getZCloakO();
    }, (v0, v1) -> {
        v0.labyMod$setZCloakO(v1);
    });
    private GameUser labyMod$gameUser;

    public boolean isSwingingHand() {
        return labyMod$getPlayer().swinging;
    }

    public float getArmSwingProgress() {
        return labyMod$getPlayer().attackAnim;
    }

    public float getPreviousArmSwingProgress() {
        return labyMod$getPlayer().oAttackAnim;
    }

    public boolean isDead() {
        return !labyMod$getPlayer().isAlive();
    }

    public String getName() {
        return labyMod$getPlayer().getGameProfile().name();
    }

    public Position chasingPosition() {
        return this.labyMod$chasingPosition;
    }

    public Position previousChasingPosition() {
        return this.labyMod$previousChasingPosition;
    }

    public float getCameraYaw() {
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().avatarState()).labyMod$getBob();
    }

    public float getPreviousCameraYaw() {
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().avatarState()).labyMod$getBobO();
    }

    public float getPreviousWalkDistance() {
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().avatarState()).labyMod$getWalkDistO();
    }

    public float getWalkDistance() {
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().avatarState()).labyMod$getWalkDist();
    }

    public float getPreviousRotationHeadYaw() {
        return labyMod$getPlayer().yHeadRotO;
    }

    public float getRotationHeadYaw() {
        return labyMod$getPlayer().yHeadRot;
    }

    public float getRenderTick(float partialTicks) {
        return labyMod$getPlayer().tickCount + partialTicks;
    }

    public double getSprintingSpeed() {
        return labyMod$getPlayer().getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    public ResourceLocation skinTexture() {
        return labyMod$getPlayer().getSkin().body().texturePath();
    }

    @Nullable
    public ResourceLocation getCloakTexture() {
        ClientAsset.Texture texture = labyMod$getPlayer().getSkin().cape();
        if (texture == null) {
            return null;
        }
        return texture.texturePath();
    }

    public boolean isSlim() {
        return labyMod$getPlayer().getSkin().model() == PlayerModelType.SLIM;
    }

    public boolean isWearingTop() {
        return labyMod$getPlayer().hasItemInSlot(EquipmentSlot.CHEST);
    }

    public float getPreviousLimbSwingStrength() {
        return labyMod$getPlayer().walkAnimation.getSpeedOld();
    }

    public float getLimbSwingStrength() {
        return labyMod$getPlayer().walkAnimation.speed();
    }

    public float getLimbSwing() {
        return labyMod$getPlayer().walkAnimation.position();
    }

    public GameMode gameMode() {
        NetworkPlayerInfo playerInfo = networkPlayerInfo();
        if (playerInfo == null) {
            return GameMode.UNKNOWN;
        }
        return playerInfo.gameMode();
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.mixins.client.player.MixinAbstractLocalPlayer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/player/MixinAbstractLocalPlayer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$client$entity$player$PlayerClothes = new int[PlayerClothes.values().length];

        static {
            try {
                $SwitchMap$net$labymod$api$client$entity$player$PlayerClothes[PlayerClothes.CAPE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$player$PlayerClothes[PlayerClothes.JACKET.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$player$PlayerClothes[PlayerClothes.LEFT_SLEEVE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$player$PlayerClothes[PlayerClothes.RIGHT_SLEEVE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$player$PlayerClothes[PlayerClothes.LEFT_PANTS_LEG.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$player$PlayerClothes[PlayerClothes.RIGHT_PANTS_LEG.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$player$PlayerClothes[PlayerClothes.HAT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public boolean isShownModelPart(PlayerClothes part) {
        PlayerModelPart modelPart;
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$entity$player$PlayerClothes[part.ordinal()]) {
            case 1:
                modelPart = PlayerModelPart.CAPE;
                break;
            case 2:
                modelPart = PlayerModelPart.JACKET;
                break;
            case 3:
                modelPart = PlayerModelPart.LEFT_SLEEVE;
                break;
            case 4:
                modelPart = PlayerModelPart.RIGHT_SLEEVE;
                break;
            case 5:
                modelPart = PlayerModelPart.LEFT_PANTS_LEG;
                break;
            case 6:
                modelPart = PlayerModelPart.RIGHT_PANTS_LEG;
                break;
            case 7:
                modelPart = PlayerModelPart.HAT;
                break;
            default:
                return true;
        }
        return labyMod$getPlayer().isModelPartShown(modelPart);
    }

    public GameProfile profile() {
        NetworkPlayerInfo playerInfo = networkPlayerInfo();
        if (playerInfo != null) {
            return playerInfo.profile();
        }
        return (GameProfile) CastUtil.cast(labyMod$getPlayer().getGameProfile());
    }

    public FoodData foodData() {
        return labyMod$getPlayer().getFoodData();
    }

    public PlayerAbilities abilities() {
        return this.labyMod$playerAbilities;
    }

    public int getExperienceLevel() {
        return labyMod$getPlayer().experienceLevel;
    }

    public int getTotalExperience() {
        return labyMod$getPlayer().totalExperience;
    }

    public float getExperienceProgress() {
        return labyMod$getPlayer().experienceProgress;
    }

    public int getExperienceNeededForNextLevel() {
        return labyMod$getPlayer().getXpNeededForNextLevel();
    }

    public PlayerModel playerModel() {
        return MinecraftUtil.obtainPlayerModel(this);
    }

    private Abilities labyMod$abilities() {
        return labyMod$getPlayer().getAbilities();
    }

    private AbstractClientPlayer labyMod$getPlayer() {
        return (AbstractClientPlayer) this;
    }

    public GameUser gameUser() {
        if (this.labyMod$gameUser == null || this.labyMod$gameUser.isDisposed()) {
            this.labyMod$gameUser = Laby.labyAPI().gameUserService().gameUser(labyMod$getPlayer().getUUID());
        }
        return this.labyMod$gameUser;
    }

    @Inject(method = {"getFieldOfViewModifier"}, at = {@At("RETURN")}, cancellable = true)
    private void modifyFieldOfViewModifier(CallbackInfoReturnable<Float> cir) {
        Float fieldOfView = (Float) cir.getReturnValue();
        FieldOfViewModifierEvent fieldOfViewModifierEvent = new FieldOfViewModifierEvent(fieldOfView.floatValue());
        Laby.fireEvent(fieldOfViewModifierEvent);
        Float fieldOfView2 = Float.valueOf(fieldOfViewModifierEvent.getFieldOfView());
        cir.setReturnValue(fieldOfView2);
    }
}
