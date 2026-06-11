package net.labymod.v26_1.mixins.world.level.block.entity;

import net.labymod.api.Laby;
import net.labymod.api.event.client.blockentity.BlockEntityPreLoadEvent;
import net.labymod.api.event.client.blockentity.BlockEntityUpdateEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.ThreadSafe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/mixins/world/level/block/entity/MixinBlockEntityUpdateEvent.class */
@Mixin({BlockEntity.class})
public class MixinBlockEntityUpdateEvent {
    /* JADX WARN: Multi-variable type inference failed */
    @Inject(method = {"loadAdditional"}, at = {@At("HEAD")})
    private void labyMod$fireTileEntityPreLoadEvent(ValueInput valueInput, CallbackInfo ci) {
        if (ThreadSafe.isRenderThread() && (valueInput instanceof TagValueInput)) {
            CompoundTag tag = ((TagValueInput) valueInput).tag();
            Laby.fireEvent(new BlockEntityPreLoadEvent((net.labymod.api.client.blockentity.BlockEntity) this, (NBTTagCompound) CastUtil.cast(tag)));
        }
    }

    @Inject(method = {"setChanged(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"}, at = {@At("TAIL")})
    private static void labyMod$fireTileEntityUpdateEvent(Level level, BlockPos pos, BlockState state, CallbackInfo ci) {
        net.labymod.api.client.blockentity.BlockEntity blockEntity = level.getBlockEntity(pos);
        if (level.isClientSide() && (blockEntity instanceof net.labymod.api.client.blockentity.BlockEntity)) {
            Laby.fireEvent(new BlockEntityUpdateEvent(blockEntity));
        }
    }
}
