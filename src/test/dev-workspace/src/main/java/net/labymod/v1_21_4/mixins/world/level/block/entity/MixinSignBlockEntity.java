package net.labymod.v1_21_4.mixins.world.level.block.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.client.blockentity.SignBlockEntityPostLoadEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/world/level/block/entity/MixinSignBlockEntity.class */
@Mixin({dvn.class})
public abstract class MixinSignBlockEntity implements SignBlockEntity {

    @Shadow
    private dvo e;

    @Shadow
    private dvo f;

    @Insert(method = {"loadAdditional"}, at = @At("TAIL"))
    private void labyMod$fireTileEntityPreLoadEvent(tq tag, a provider, InsertInfo ci) {
        if (ThreadSafe.isRenderThread()) {
            Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound) CastUtil.cast(tag)));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public float getRotationYaw() throws MatchException {
        dxv dxvVar;
        dxx dxxVar;
        dua entity = (dua) this;
        dwy state = entity.m();
        djn block = state.b();
        if ((block instanceof dss) || (block instanceof dsr)) {
            if (block instanceof dss) {
                dxvVar = dss.b;
            } else {
                dxvVar = dsr.b;
            }
            jn facing = state.c(dxvVar);
            switch (AnonymousClass1.$SwitchMap$net$minecraft$core$Direction[facing.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    return 0.0f;
                case 4:
                    return -90.0f;
                case 5:
                    return 180.0f;
                case 6:
                    return 90.0f;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
        if ((block instanceof drn) || (block instanceof dkn)) {
            if (block instanceof drn) {
                dxxVar = drn.b;
            } else {
                dxxVar = dkn.b;
            }
            Integer rotation = (Integer) state.c(dxxVar);
            return 180.0f - ((rotation.intValue() * 360) / 16.0f);
        }
        return 0.0f;
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_4.mixins.world.level.block.entity.MixinSignBlockEntity$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/mixins/world/level/block/entity/MixinSignBlockEntity$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$core$Direction = new int[jn.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.c.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.f.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.d.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.e.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public boolean hasSide(@NotNull SignBlockEntity.SignSide side) {
        return side == SignBlockEntity.SignSide.FRONT || side == SignBlockEntity.SignSide.BACK;
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    @NotNull
    public Component[] getLines(@NotNull SignBlockEntity.SignSide side) {
        Component componentEmpty;
        if (side != SignBlockEntity.SignSide.FRONT && side != SignBlockEntity.SignSide.BACK) {
            throw new IllegalArgumentException("Unsupported side: " + String.valueOf(side));
        }
        wp[] messages = (side == SignBlockEntity.SignSide.FRONT ? this.e : this.f).b(false);
        Component[] lines = new Component[messages.length];
        for (int i = 0; i < lines.length; i++) {
            int i2 = i;
            if (messages[i] != null) {
                componentEmpty = Laby.references().componentMapper().fromMinecraftComponent(messages[i]);
            } else {
                componentEmpty = Component.empty();
            }
            lines[i2] = componentEmpty;
        }
        return lines;
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public SignBlockEntity.SignType getType() {
        dua entity = (dua) this;
        dwy state = entity.m();
        djn block = state.b();
        if (block instanceof drn) {
            return SignBlockEntity.SignType.STANDING_SIGN;
        }
        if (block instanceof dss) {
            return SignBlockEntity.SignType.WALL_SIGN;
        }
        if (block instanceof dkn) {
            return SignBlockEntity.SignType.CEILING_HANGING_SIGN;
        }
        if (block instanceof dsr) {
            return SignBlockEntity.SignType.WALL_HANGING_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
