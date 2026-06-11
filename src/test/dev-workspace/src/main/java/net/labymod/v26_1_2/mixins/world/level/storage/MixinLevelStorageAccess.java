package net.labymod.v26_1_2.mixins.world.level.storage;

import net.labymod.core.client.world.storage.accessor.LevelStorageAccessor;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/world/level/storage/MixinLevelStorageAccess.class */
@Mixin({LevelStorageSource.LevelStorageAccess.class})
public class MixinLevelStorageAccess implements LevelStorageAccessor {

    @Shadow
    @Final
    private String levelId;

    @Override // net.labymod.core.client.world.storage.accessor.LevelStorageAccessor
    public String getLevelId() {
        return this.levelId;
    }
}
