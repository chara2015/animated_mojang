package net.labymod.v26_2_snapshot_8.mixins.mojang.blaze3d.platform;

import com.mojang.blaze3d.platform.Lighting;
import net.labymod.api.laby3d.GameLightingStorage;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/mojang/blaze3d/platform/MixinLighting.class */
@Mixin({Lighting.class})
public class MixinLighting {

    /* JADX INFO: renamed from: net.labymod.v26_2_snapshot_8.mixins.mojang.blaze3d.platform.MixinLighting$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/mojang/blaze3d/platform/MixinLighting$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$mojang$blaze3d$platform$Lighting$Entry = new int[Lighting.Entry.values().length];

        static {
            try {
                $SwitchMap$com$mojang$blaze3d$platform$Lighting$Entry[Lighting.Entry.LEVEL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$platform$Lighting$Entry[Lighting.Entry.ITEMS_FLAT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$platform$Lighting$Entry[Lighting.Entry.ITEMS_3D.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$platform$Lighting$Entry[Lighting.Entry.ENTITY_IN_UI.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$platform$Lighting$Entry[Lighting.Entry.PLAYER_SKIN.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Inject(method = {"updateBuffer"}, at = {@At("HEAD")})
    private void labyMod$updateBuffer(Lighting.Entry entry, Vector3fc light0, Vector3fc light1, CallbackInfo ci) throws MatchException {
        GameLightingStorage.Entry entry2;
        switch (AnonymousClass1.$SwitchMap$com$mojang$blaze3d$platform$Lighting$Entry[entry.ordinal()]) {
            case 1:
                entry2 = GameLightingStorage.Entry.LEVEL;
                break;
            case 2:
                entry2 = GameLightingStorage.Entry.ITEMS_FLAT;
                break;
            case 3:
                entry2 = GameLightingStorage.Entry.ITEMS_3D;
                break;
            case 4:
                entry2 = GameLightingStorage.Entry.ENTITY_IN_UI;
                break;
            case 5:
                entry2 = GameLightingStorage.Entry.PLAYER_SKIN;
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        GameLightingStorage.Entry apiEntry = entry2;
        GameLightingStorage.INSTANCE.setLightDirections(apiEntry, light0, light1);
    }
}
