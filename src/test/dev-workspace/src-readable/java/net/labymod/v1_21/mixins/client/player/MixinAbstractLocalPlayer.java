package net.labymod.v1_21.mixins.client.player;

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
import net.labymod.api.util.math.position.DynamicPosition;
import net.labymod.api.util.math.position.Position;
import net.labymod.core.client.entity.player.abilities.DefaultPlayerAbilities;
import net.labymod.v1_21.client.util.MinecraftUtil;
import net.labymod.v1_21.mixins.client.entity.MixinLivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21/mixins/client/player/MixinAbstractLocalPlayer.class */
@Mixin({gdy.class})
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
    private final Position labyMod$chasingPosition = new DynamicPosition(() -> {
        return labyMod$getPlayer().cm;
    }, x -> {
        labyMod$getPlayer().cm = x;
    }, () -> {
        return labyMod$getPlayer().cn;
    }, y -> {
        labyMod$getPlayer().cn = y;
    }, () -> {
        return labyMod$getPlayer().co;
    }, z -> {
        labyMod$getPlayer().co = z;
    });
    private final Position labyMod$previousChasingPosition = new DynamicPosition(() -> {
        return labyMod$getPlayer().cj;
    }, x -> {
        labyMod$getPlayer().cj = x;
    }, () -> {
        return labyMod$getPlayer().ck;
    }, y -> {
        labyMod$getPlayer().ck = y;
    }, () -> {
        return labyMod$getPlayer().cl;
    }, z -> {
        labyMod$getPlayer().cl = z;
    });
    private GameUser labyMod$gameUser;

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isSwingingHand() {
        return labyMod$getPlayer().aJ;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getArmSwingProgress() {
        return labyMod$getPlayer().aS;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousArmSwingProgress() {
        return labyMod$getPlayer().aR;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isDead() {
        return !labyMod$getPlayer().bE();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public String getName() {
        return labyMod$getPlayer().fX().getName();
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
        return labyMod$getPlayer().ch;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousCameraYaw() {
        return labyMod$getPlayer().cg;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousWalkDistance() {
        return labyMod$getPlayer().Y;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getWalkDistance() {
        return labyMod$getPlayer().Z;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousRotationHeadYaw() {
        return labyMod$getPlayer().bb;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getRotationHeadYaw() {
        return labyMod$getPlayer().ba;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getRenderTick(float partialTicks) {
        return labyMod$getPlayer().ai + partialTicks;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public double getSprintingSpeed() {
        return labyMod$getPlayer().g(buw.v);
    }

    @Override // net.labymod.api.client.entity.player.Player
    public ResourceLocation skinTexture() {
        return labyMod$getPlayer().b().a();
    }

    @Override // net.labymod.api.client.entity.player.Player
    @Nullable
    public ResourceLocation getCloakTexture() {
        ResourceLocation resourceLocationC = labyMod$getPlayer().b().c();
        if (resourceLocationC == null) {
            return null;
        }
        return resourceLocationC;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isSlim() {
        return labyMod$getPlayer().b().e() == a.a;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public boolean isWearingTop() {
        return labyMod$getPlayer().c(bsy.e);
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getPreviousLimbSwingStrength() {
        return labyMod$getPlayer().aU.getSpeedOld();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getLimbSwingStrength() {
        return labyMod$getPlayer().aU.a();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getLimbSwing() {
        return labyMod$getPlayer().aU.b();
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
        cmy modelPart;
        switch (part) {
            case CAPE:
                modelPart = cmy.a;
                break;
            case JACKET:
                modelPart = cmy.b;
                break;
            case LEFT_SLEEVE:
                modelPart = cmy.c;
                break;
            case RIGHT_SLEEVE:
                modelPart = cmy.d;
                break;
            case LEFT_PANTS_LEG:
                modelPart = cmy.e;
                break;
            case RIGHT_PANTS_LEG:
                modelPart = cmy.f;
                break;
            case HAT:
                modelPart = cmy.g;
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
        return labyMod$getPlayer().fX();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public FoodData foodData() {
        return labyMod$getPlayer().gi();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public PlayerAbilities abilities() {
        return this.labyMod$playerAbilities;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getExperienceLevel() {
        return labyMod$getPlayer().cq;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getTotalExperience() {
        return labyMod$getPlayer().cr;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public float getExperienceProgress() {
        return labyMod$getPlayer().cs;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public int getExperienceNeededForNextLevel() {
        return labyMod$getPlayer().gh();
    }

    @Override // net.labymod.api.client.entity.player.Player
    public PlayerModel playerModel() {
        return MinecraftUtil.obtainPlayerModel(this);
    }

    private cmu labyMod$abilities() {
        return labyMod$getPlayer().fZ();
    }

    private gdy labyMod$getPlayer() {
        return (gdy) this;
    }

    @Override // net.labymod.api.client.entity.player.Player
    public GameUser gameUser() {
        if (this.labyMod$gameUser == null || this.labyMod$gameUser.isDisposed()) {
            this.labyMod$gameUser = Laby.labyAPI().gameUserService().gameUser(labyMod$getPlayer().cz());
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
