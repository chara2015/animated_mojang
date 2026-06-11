package net.labymod.v1_21_3.client.render.item;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/render/item/VersionedAnimationTypeMapper.class */
@Singleton
@Implements(RenderFirstPersonItemInHandEvent.AnimationTypeMapper.class)
public class VersionedAnimationTypeMapper implements RenderFirstPersonItemInHandEvent.AnimationTypeMapper {
    @Inject
    public VersionedAnimationTypeMapper() {
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent.AnimationTypeMapper
    public RenderFirstPersonItemInHandEvent.AnimationType fromMinecraft(Object gameType) throws MatchException {
        if (!(gameType instanceof cxr)) {
            throw new IllegalStateException(gameType.getClass().getName() + " is not an instance of " + a.class.getName());
        }
        cxr animation = (cxr) gameType;
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
                return RenderFirstPersonItemInHandEvent.AnimationType.CROSSBOW;
            case 8:
                return RenderFirstPersonItemInHandEvent.AnimationType.SPYGLASS;
            case 9:
                return RenderFirstPersonItemInHandEvent.AnimationType.TOOT_HORN;
            case 10:
                return RenderFirstPersonItemInHandEvent.AnimationType.BRUSH;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_3.client.render.item.VersionedAnimationTypeMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/render/item/VersionedAnimationTypeMapper$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$item$ItemUseAnimation;

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
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.CROSSBOW.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.SPYGLASS.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.TOOT_HORN.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$labymod$api$event$client$render$item$RenderFirstPersonItemInHandEvent$AnimationType[RenderFirstPersonItemInHandEvent.AnimationType.BRUSH.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            $SwitchMap$net$minecraft$world$item$ItemUseAnimation = new int[cxr.values().length];
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[cxr.a.ordinal()] = 1;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[cxr.b.ordinal()] = 2;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[cxr.c.ordinal()] = 3;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[cxr.d.ordinal()] = 4;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[cxr.e.ordinal()] = 5;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[cxr.f.ordinal()] = 6;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[cxr.g.ordinal()] = 7;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[cxr.h.ordinal()] = 8;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[cxr.i.ordinal()] = 9;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemUseAnimation[cxr.j.ordinal()] = 10;
            } catch (NoSuchFieldError e20) {
            }
        }
    }

    @Override // net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent.AnimationTypeMapper
    public Object toMinecraft(RenderFirstPersonItemInHandEvent.AnimationType type) {
        switch (type) {
            case NONE:
                return cxr.a;
            case EAT:
                return cxr.b;
            case DRINK:
                return cxr.c;
            case BLOCK:
                return cxr.d;
            case BOW:
                return cxr.e;
            case SPEAR:
                return cxr.f;
            case CROSSBOW:
                return cxr.g;
            case SPYGLASS:
                return cxr.h;
            case TOOT_HORN:
                return cxr.i;
            case BRUSH:
                return cxr.j;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        }
    }
}
