package net.labymod.v1_17_1.mixins.world.level.block.entity;

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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/world/level/block/entity/MixinSignBlockEntity.class */
@Mixin({cjq.class})
public abstract class MixinSignBlockEntity implements SignBlockEntity {

    @Shadow
    @Final
    private os[] d;

    @Insert(method = {"load"}, at = @At("TAIL"))
    private void labyMod$fireTileEntityPostLoadEvent(na tag, InsertInfo ci) {
        if (ThreadSafe.isRenderThread()) {
            Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound) CastUtil.cast(tag)));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public float getRotationYaw() throws MatchException {
        ciq entity = (ciq) this;
        ckt state = entity.n();
        bzp block = state.b();
        if (block instanceof cho) {
            gl facing = state.c(cho.d);
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
        if (block instanceof cgn) {
            Integer rotation = (Integer) state.c(cgn.d);
            return 180.0f - ((rotation.intValue() * 360) / 16.0f);
        }
        return 0.0f;
    }

    /* JADX INFO: renamed from: net.labymod.v1_17_1.mixins.world.level.block.entity.MixinSignBlockEntity$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/mixins/world/level/block/entity/MixinSignBlockEntity$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$core$Direction = new int[gl.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$core$Direction[gl.c.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[gl.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[gl.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[gl.f.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[gl.d.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[gl.e.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public boolean hasSide(@NotNull SignBlockEntity.SignSide side) {
        return side == SignBlockEntity.SignSide.FRONT;
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    @NotNull
    public Component[] getLines(@NotNull SignBlockEntity.SignSide side) {
        Component componentEmpty;
        if (side != SignBlockEntity.SignSide.FRONT) {
            throw new IllegalArgumentException("Unsupported side: " + String.valueOf(side));
        }
        Component[] lines = new Component[this.d.length];
        for (int i = 0; i < lines.length; i++) {
            int i2 = i;
            if (this.d[i] != null) {
                componentEmpty = Laby.references().componentMapper().fromMinecraftComponent(this.d[i]);
            } else {
                componentEmpty = Component.empty();
            }
            lines[i2] = componentEmpty;
        }
        return lines;
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public SignBlockEntity.SignType getType() {
        ciq entity = (ciq) this;
        ckt state = entity.n();
        bzp block = state.b();
        if (block instanceof cgn) {
            return SignBlockEntity.SignType.STANDING_SIGN;
        }
        if (block instanceof cho) {
            return SignBlockEntity.SignType.WALL_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
