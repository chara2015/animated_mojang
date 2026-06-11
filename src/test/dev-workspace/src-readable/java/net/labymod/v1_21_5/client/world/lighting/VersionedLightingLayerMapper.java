package net.labymod.v1_21_5.client.world.lighting;

import javax.inject.Singleton;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.client.world.lighting.LightingLayerMapper;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/world/lighting/VersionedLightingLayerMapper.class */
@Singleton
@Implements(LightingLayerMapper.class)
public class VersionedLightingLayerMapper implements LightingLayerMapper {
    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.world.lighting.LightingLayerMapper
    public LightType fromMinecraft(Object value) throws MatchException {
        if (!(value instanceof dks)) {
            throw new IllegalArgumentException(value.getClass().getName() + " is not an instance of " + dks.class.getName());
        }
        dks lightLayer = (dks) value;
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$LightLayer[lightLayer.ordinal()]) {
            case 1:
                return LightType.SKY;
            case 2:
                return LightType.BLOCK;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_5.client.world.lighting.VersionedLightingLayerMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/world/lighting/VersionedLightingLayerMapper$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$level$LightLayer;

        static {
            try {
                $SwitchMap$net$labymod$api$client$world$lighting$LightType[LightType.SKY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$client$world$lighting$LightType[LightType.BLOCK.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SwitchMap$net$minecraft$world$level$LightLayer = new int[dks.values().length];
            try {
                $SwitchMap$net$minecraft$world$level$LightLayer[dks.a.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$LightLayer[dks.b.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.world.lighting.LightingLayerMapper
    public Object toMinecraft(LightType value) throws MatchException {
        switch (value) {
            case SKY:
                return dks.a;
            case BLOCK:
                return dks.b;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
