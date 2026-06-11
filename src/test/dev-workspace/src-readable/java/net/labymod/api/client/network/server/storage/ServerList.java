package net.labymod.api.client.network.server.storage;

import net.labymod.api.client.network.server.ServerAddress;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/network/server/storage/ServerList.class */
public interface ServerList {
    void load();

    void save();

    void saveAsync();

    StorageServerData get(int i);

    void remove(StorageServerData storageServerData);

    void add(StorageServerData storageServerData);

    int size();

    void swap(int i, int i2, ServerFolder serverFolder);

    boolean move(int i, int i2, MoveActionType moveActionType, ServerFolder serverFolder);

    void replace(int i, StorageServerData storageServerData);

    void update(StorageServerData storageServerData);

    boolean has(ServerAddress serverAddress);

    int index(StorageServerData storageServerData);

    default void swap(int index, int index2) {
        swap(index, index2, null);
    }
}
