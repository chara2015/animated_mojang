package net.labymod.v26_2_snapshot_8.client.world.lighting;

import javax.inject.Singleton;
import net.labymod.api.client.world.lighting.LightType;
import net.labymod.api.client.world.lighting.LightingLayerMapper;
import net.labymod.api.models.Implements;
import net.minecraft.world.level.LightLayer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/world/lighting/VersionedLightingLayerMapper.class */
@Singleton
@Implements(LightingLayerMapper.class)
public class VersionedLightingLayerMapper implements LightingLayerMapper {
    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.world.lighting.LightingLayerMapper
    public LightType fromMinecraft(Object value) throws MatchException {
        if (!(value instanceof LightLayer)) {
            throw new IllegalArgumentException(value.getClass().getName() + " is not an instance of " + LightLayer.class.getName());
        }
        LightLayer lightLayer = (LightLayer) value;
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$level$LightLayer[lightLayer.ordinal()]) {
            case 1:
                return LightType.SKY;
            case 2:
                return LightType.BLOCK;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.client.world.lighting.VersionedLightingLayerMapper$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/world/lighting/VersionedLightingLayerMapper$1.class */
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
            $SwitchMap$net$minecraft$world$level$LightLayer = new int[LightLayer.values().length];
            try {
                $SwitchMap$net$minecraft$world$level$LightLayer[LightLayer.SKY.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$world$level$LightLayer[LightLayer.BLOCK.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.world.lighting.LightingLayerMapper
    public Object toMinecraft(LightType value) throws MatchException {
        switch (value) {
            case SKY:
                return LightLayer.SKY;
            case BLOCK:
                return LightLayer.BLOCK;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
