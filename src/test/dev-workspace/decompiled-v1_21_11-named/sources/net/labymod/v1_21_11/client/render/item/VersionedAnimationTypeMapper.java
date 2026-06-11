package net.labymod.v1_21_11.client.render.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.models.Implements;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.item.ItemUseAnimation;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/render/item/VersionedAnimationTypeMapper.class */
@Singleton
@Implements(RenderFirstPersonItemInHandEvent.AnimationTypeMapper.class)
public class VersionedAnimationTypeMapper implements RenderFirstPersonItemInHandEvent.AnimationTypeMapper {
    @Inject
    public VersionedAnimationTypeMapper() {
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public RenderFirstPersonItemInHandEvent.AnimationType fromMinecraft(Object gameType) throws MatchException {
        if (!(gameType instanceof ItemUseAnimation)) {
            throw new IllegalStateException(gameType.getClass().getName() + " is not an instance of " + HumanoidModel.ArmPose.class.getName());
        }
        ItemUseAnimation animation = (ItemUseAnimation) gameType;
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$item$ItemUseAnimation[animation.ordinal()]) {
            case 1:
                return RenderFirstPersonItemInHandEvent.AnimationType.NONE;
            case 2:
                return RenderFirstPersonItemInHandEvent.AnimationType.EAT;
            case 3:
                return RenderFirstPersonItemInHandEvent.AnimationType.DRINK;
            case 4:
                return RenderFirstPersonItemInHandEvent.AnimationType.BLOCK;
            case 5:
                return RenderFirstPersonItemInHandEvent.AnimationType.BOW;
            case 6:
                return RenderFirstPersonItemInHandEvent.AnimationType.SPEAR;
            case 7:
                return RenderFirstPersonItemInHandEvent.AnimationType.TRIDENT;
            case 8:
                return RenderFirstPersonItemInHandEvent.AnimationType.CROSSBOW;
            case 9:
                return RenderFirstPersonItemInHandEvent.AnimationType.SPYGLASS;
            case 10:
                return RenderFirstPersonItemInHandEvent.AnimationType.TOOT_HORN;
            case 11:
                return RenderFirstPersonItemInHandEvent.AnimationType.BRUSH;
            case 12:
                return RenderFirstPersonItemInHandEvent.AnimationType.BUNDLE;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.client.render.item.VersionedAnimationTypeMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/render/item/VersionedAnimationTypeMapper$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$item$ItemUseAnimation;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType = new int[RenderFirstPersonItemInHandEvent.AnimationType.values().length];

        static {
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.EAT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.DRINK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.BLOCK.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.BOW.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.SPEAR.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.TRIDENT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.CROSSBOW.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.SPYGLASS.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.TOOT_HORN.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.BRUSH.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.BUNDLE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            $SwitchMap$net$minecraft$world$item$ItemUseAnimation = new int[ItemUseAnimation.values().length];
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.EAT.ordinal()] = 2;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.DRINK.ordinal()] = 3;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.BLOCK.ordinal()] = 4;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.BOW.ordinal()] = 5;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.SPEAR.ordinal()] = 6;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.TRIDENT.ordinal()] = 7;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.CROSSBOW.ordinal()] = 8;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.SPYGLASS.ordinal()] = 9;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.TOOT_HORN.ordinal()] = 10;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.BRUSH.ordinal()] = 11;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[ItemUseAnimation.BUNDLE.ordinal()] = 12;
            } catch (NoSuchFieldError e24) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public Object toMinecraft(RenderFirstPersonItemInHandEvent.AnimationType type) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[type.ordinal()]) {
            case 1:
                return ItemUseAnimation.NONE;
            case 2:
                return ItemUseAnimation.EAT;
            case 3:
                return ItemUseAnimation.DRINK;
            case 4:
                return ItemUseAnimation.BLOCK;
            case 5:
                return ItemUseAnimation.BOW;
            case 6:
                return ItemUseAnimation.SPEAR;
            case 7:
                return ItemUseAnimation.TRIDENT;
            case 8:
                return ItemUseAnimation.CROSSBOW;
            case 9:
                return ItemUseAnimation.SPYGLASS;
            case 10:
                return ItemUseAnimation.TOOT_HORN;
            case 11:
                return ItemUseAnimation.BRUSH;
            case 12:
                return ItemUseAnimation.BUNDLE;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
