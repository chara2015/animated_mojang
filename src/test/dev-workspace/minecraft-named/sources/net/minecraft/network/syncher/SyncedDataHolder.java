package net.minecraft.network.syncher;

import java.util.List;
import net.minecraft.network.syncher.SynchedEntityData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/syncher/SyncedDataHolder.class */
public interface SyncedDataHolder {
    void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor);

    void onSyncedDataUpdated(List<SynchedEntityData.DataValue<?>> list);
}
