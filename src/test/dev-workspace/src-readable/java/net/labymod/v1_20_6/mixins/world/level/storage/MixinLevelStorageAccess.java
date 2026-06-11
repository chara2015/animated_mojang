package net.labymod.v1_20_6.mixins.world.level.storage;

import net.labymod.core.client.world.storage.accessor.LevelStorageAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/world/level/storage/MixinLevelStorageAccess.class */
@Mixin({c.class})
public class MixinLevelStorageAccess implements LevelStorageAccessor {

    @Shadow
    @Final
    private String d;

    @Override // net.labymod.core.client.world.storage.accessor.LevelStorageAccessor
    public String getLevelId() {
        return this.d;
    }
}
