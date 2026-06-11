package net.labymod.v1_8_9.mixins.client.player;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.EntityPose;
import net.labymod.api.client.entity.LivingEntity;
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
import net.labymod.api.util.math.position.DynamicPosition;
import net.labymod.api.util.math.position.Position;
import net.labymod.core.client.entity.player.abilities.DefaultPlayerAbilities;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.v1_8_9.mixins.client.entity.MixinEntityLivingBase;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/player/MixinAbstractLocalPlayer.class */
@Mixin({bet.class})
public abstract class MixinAbstractLocalPlayer extends MixinEntityLivingBase implements Player {
    private final PlayerAbilities labyMod$playerAbilities = new DefaultPlayerAbilities(new BooleanAbilityOption(() -> {
        return labyMod$abilities().a;
    }, value -> {
        labyMod$abilities().a = value;
    }), new BooleanAbilityOption(() -> {
        return labyMod$abilities().b;
    }, value2 -> {
        labyMod$abilities().b = value2;
    }), new BooleanAbilityOption(() -> {
        return labyMod$abilities().c;
    }, value3 -> {
        labyMod$abilities().c = value3;
    }), new BooleanAbilityOption(() -> {
        return labyMod$abilities().d;
    }, value4 -> {
        labyMod$abilities().d = value4;
    }), new BooleanAbilityOption(() -> {
        return labyMod$abilities().e;
    }, value5 -> {
        labyMod$abilities().e = value5;
    }), new FloatAbilityOption(() -> {
        return labyMod$abilities().a();
    }, value6 -> {
        labyMod$abilities().a(value6);
    }), new FloatAbilityOption(() -> {
        return labyMod$abilities().b();
    }, value7 -> {
        labyMod$abilities().b(value7);
    }));
    private final Position labyMod$chasingPosition = new DynamicPosition(() -> {
        return labyMod$getPlayer().bt;
    }, x -> {
        labyMod$getPlayer().bt = x;
    }, () -> {
        return labyMod$getPlayer().bu;
    }, y -> {
        labyMod$getPlayer().bu = y;
    }, () -> {
        return labyMod$getPlayer().bv;
    }, z -> {
        labyMod$getPlayer().bv = z;
    });
    private final Position labyMod$previousChasingPosition = new DynamicPosition(() -> {
        return labyMod$getPlayer().bq;
    }, x -> {
        labyMod$getPlayer().bq = x;
    }, () -> {
        return labyMod$getPlayer().br;
    }, y -> {
        labyMod$getPlayer().br = y;
    }, () -> {
        return labyMod$getPlayer().bs;
    }, z -> {
        labyMod$getPlayer().bs = z;
    });
    private GameUser labyMod$gameUser;

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isSwingingHand() {
        return labyMod$getPlayer().ar;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getArmSwingProgress() {
        return labyMod$getPlayer().az;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousArmSwingProgress() {
        return labyMod$getPlayer().ay;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isDead() {
        return labyMod$getPlayer().I;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public String getName() {
        return labyMod$getPlayer().cd().getName();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public Position chasingPosition() {
        return this.labyMod$chasingPosition;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public Position previousChasingPosition() {
        return this.labyMod$previousChasingPosition;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getCameraYaw() {
        return labyMod$getPlayer().bo;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousCameraYaw() {
        return labyMod$getPlayer().bn;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousWalkDistance() {
        return labyMod$getPlayer().L;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getWalkDistance() {
        return labyMod$getPlayer().M;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousRotationHeadYaw() {
        return labyMod$getPlayer().aL;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getRotationHeadYaw() {
        return labyMod$getPlayer().aK;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousLimbSwingStrength() {
        return labyMod$getPlayer().aA;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getLimbSwingStrength() {
        return labyMod$getPlayer().aB;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getLimbSwing() {
        return labyMod$getPlayer().aC;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getRenderTick(float partialTicks) {
        return labyMod$getPlayer().W + partialTicks;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public double getSprintingSpeed() {
        return labyMod$getPlayer().a(vy.d).e();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public ResourceLocation skinTexture() {
        return labyMod$getPlayer().i();
    }

    @Override // net.labymod.api.client.entity.player.Player
    @Nullable
    public ResourceLocation getCloakTexture() {
        ResourceLocation resourceLocationK = labyMod$getPlayer().k();
        if (resourceLocationK == null) {
            return null;
        }
        return resourceLocationK;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isSlim() {
        return labyMod$getPlayer().l().equals("slim");
    }

    @Override // net.labymod.v1_8_9.mixins.client.entity.MixinEntity, net.labymod.api.client.entity.Entity
    public EntityPose entityPose() {
        bet player = labyMod$getPlayer();
        if (player.bJ()) {
            return EntityPose.SLEEPING;
        }
        return super.entityPose();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isWearingTop() {
        return labyMod$getPlayer().q(2) != null;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public GameMode gameMode() {
        NetworkPlayerInfo networkPlayerInfo = networkPlayerInfo();
        if (networkPlayerInfo == null) {
            return GameMode.UNKNOWN;
        }
        return networkPlayerInfo.gameMode();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public GameProfile profile() {
        NetworkPlayerInfo playerInfo = networkPlayerInfo();
        if (playerInfo != null) {
            return playerInfo.profile();
        }
        return labyMod$getPlayer().cd();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public FoodData foodData() {
        return labyMod$getPlayer().cl();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public PlayerAbilities abilities() {
        return this.labyMod$playerAbilities;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getExperienceLevel() {
        return labyMod$getPlayer().bB;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getTotalExperience() {
        return labyMod$getPlayer().bC;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getExperienceProgress() {
        return labyMod$getPlayer().bD;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getExperienceNeededForNextLevel() {
        return labyMod$getPlayer().ck();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public PlayerModel playerModel() {
        return MinecraftUtil.obtainPlayerModel(this);
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isShownModelPart(PlayerClothes part) {
        wo modelPart;
        switch (part) {
            case CAPE:
                modelPart = wo.a;
                break;
            case JACKET:
                modelPart = wo.b;
                break;
            case LEFT_SLEEVE:
                modelPart = wo.c;
                break;
            case RIGHT_SLEEVE:
                modelPart = wo.d;
                break;
            case LEFT_PANTS_LEG:
                modelPart = wo.e;
                break;
            case RIGHT_PANTS_LEG:
                modelPart = wo.f;
                break;
            case HAT:
                modelPart = wo.g;
                break;
            default:
                return true;
        }
        return labyMod$getPlayer().a(modelPart);
    }

    @Override // net.labymod.v1_8_9.mixins.client.entity.MixinEntityLivingBase, net.labymod.api.client.entity.LivingEntity
    public int getItemUseDurationTicks() {
        return labyMod$getPlayer().bT();
    }

    @Override // net.labymod.api.client.entity.LivingEntity
    public LivingEntity.Hand getUsedItemHand() {
        return LivingEntity.Hand.MAIN_HAND;
    }

    private wl labyMod$abilities() {
        return labyMod$getPlayer().bA;
    }

    private bet labyMod$getPlayer() {
        return (bet) this;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public GameUser gameUser() {
        if (this.labyMod$gameUser == null || this.labyMod$gameUser.isDisposed()) {
            this.labyMod$gameUser = Laby.labyAPI().gameUserService().gameUser(labyMod$getPlayer().aK());
        }
        return this.labyMod$gameUser;
    }

    @Inject(method = {"getFovModifier"}, at = {@At("RETURN")}, cancellable = true)
    private void modifyFieldOfViewModifier(CallbackInfoReturnable<Float> cir) {
        Float fieldOfView = (Float) cir.getReturnValue();
        FieldOfViewModifierEvent fieldOfViewModifierEvent = new FieldOfViewModifierEvent(fieldOfView.floatValue());
        Laby.fireEvent(fieldOfViewModifierEvent);
        Float fieldOfView2 = Float.valueOf(fieldOfViewModifierEvent.getFieldOfView());
        cir.setReturnValue(fieldOfView2);
    }
}
