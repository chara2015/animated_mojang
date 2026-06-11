package net.labymod.v1_21_10.mixins.client.player;

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
import net.labymod.v1_21_10.client.player.ClientAvatarStateAccessor;
import net.labymod.v1_21_10.client.util.MinecraftUtil;
import net.labymod.v1_21_10.mixins.client.entity.MixinLivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/player/MixinAbstractLocalPlayer.class */
@Mixin({hem.class})
@Implements({@Interface(iface = Player.class, prefix = "player$", remap = Interface.Remap.NONE)})
public abstract class MixinAbstractLocalPlayer extends MixinLivingEntity implements Player {
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
    private final Position labyMod$chasingPosition = new DynamicPositionWithOwner(() -> {
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().b());
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
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().b());
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

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isSwingingHand() {
        return labyMod$getPlayer().bo;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getArmSwingProgress() {
        return labyMod$getPlayer().bx;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousArmSwingProgress() {
        return labyMod$getPlayer().bw;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isDead() {
        return !labyMod$getPlayer().bX();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public String getName() {
        return labyMod$getPlayer().gz().name();
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
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().b()).labyMod$getBob();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousCameraYaw() {
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().b()).labyMod$getBobO();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousWalkDistance() {
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().b()).labyMod$getWalkDistO();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getWalkDistance() {
        return ClientAvatarStateAccessor.cast(labyMod$getPlayer().b()).labyMod$getWalkDist();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousRotationHeadYaw() {
        return labyMod$getPlayer().bE;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getRotationHeadYaw() {
        return labyMod$getPlayer().bD;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getRenderTick(float partialTicks) {
        return labyMod$getPlayer().at + partialTicks;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public double getSprintingSpeed() {
        return labyMod$getPlayer().i(cgc.w);
    }

    @Override // net.labymod.api.client.entity.player.Player
    public ResourceLocation skinTexture() {
        return labyMod$getPlayer().c().a().b();
    }

    @Override // net.labymod.api.client.entity.player.Player
    @Nullable
    public ResourceLocation getCloakTexture() {
        c texture = labyMod$getPlayer().c().b();
        if (texture == null) {
            return null;
        }
        return texture.b();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isSlim() {
        return labyMod$getPlayer().c().d() == czo.a;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isWearingTop() {
        return labyMod$getPlayer().d(cef.e);
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousLimbSwingStrength() {
        return labyMod$getPlayer().bz.getSpeedOld();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getLimbSwingStrength() {
        return labyMod$getPlayer().bz.b();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getLimbSwing() {
        return labyMod$getPlayer().bz.c();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public GameMode gameMode() {
        NetworkPlayerInfo playerInfo = networkPlayerInfo();
        if (playerInfo == null) {
            return GameMode.UNKNOWN;
        }
        return playerInfo.gameMode();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isShownModelPart(PlayerClothes part) {
        czn modelPart;
        switch (part) {
            case CAPE:
                modelPart = czn.a;
                break;
            case JACKET:
                modelPart = czn.b;
                break;
            case LEFT_SLEEVE:
                modelPart = czn.c;
                break;
            case RIGHT_SLEEVE:
                modelPart = czn.d;
                break;
            case LEFT_PANTS_LEG:
                modelPart = czn.e;
                break;
            case RIGHT_PANTS_LEG:
                modelPart = czn.f;
                break;
            case HAT:
                modelPart = czn.g;
                break;
            default:
                return true;
        }
        return labyMod$getPlayer().a(modelPart);
    }

    @Override // net.labymod.api.client.entity.player.Player
    public GameProfile profile() {
        NetworkPlayerInfo playerInfo = networkPlayerInfo();
        if (playerInfo != null) {
            return playerInfo.profile();
        }
        return (GameProfile) CastUtil.cast(labyMod$getPlayer().gz());
    }

    @Override // net.labymod.api.client.entity.player.Player
    public FoodData foodData() {
        return labyMod$getPlayer().gM();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public PlayerAbilities abilities() {
        return this.labyMod$playerAbilities;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getExperienceLevel() {
        return labyMod$getPlayer().cr;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getTotalExperience() {
        return labyMod$getPlayer().cs;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getExperienceProgress() {
        return labyMod$getPlayer().ct;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getExperienceNeededForNextLevel() {
        return labyMod$getPlayer().gL();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public PlayerModel playerModel() {
        return MinecraftUtil.obtainPlayerModel(this);
    }

    private czh labyMod$abilities() {
        return labyMod$getPlayer().gC();
    }

    private hem labyMod$getPlayer() {
        return (hem) this;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public GameUser gameUser() {
        if (this.labyMod$gameUser == null || this.labyMod$gameUser.isDisposed()) {
            this.labyMod$gameUser = Laby.labyAPI().gameUserService().gameUser(labyMod$getPlayer().cT());
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
