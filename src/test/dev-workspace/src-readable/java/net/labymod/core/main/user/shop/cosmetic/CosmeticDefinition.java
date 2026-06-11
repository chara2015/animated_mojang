package net.labymod.core.main.user.shop.cosmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.laby.lib.cosmetics.Cosmetic;
import net.labymod.api.tag.Taggable;
import net.labymod.api.tag.TaggedObject;
import net.labymod.api.util.Disposable;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.core.main.user.shop.Purchasable;
import net.labymod.core.main.user.shop.cosmetic.loader.CosmeticAssetLoader;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.item.renderer.ItemModel;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/CosmeticDefinition.class */
public final class CosmeticDefinition implements Purchasable, Disposable, Taggable {
    private final int listId;
    private final CosmeticDetails cosmeticDetails;
    private final CosmeticType cosmeticType;

    @Nullable
    private final Cosmetic cosmetic;
    private final TaggedObject taggedObject;
    private final List<Runnable> disposeListeners;
    private final AtomicReference<CosmeticLoadingState> loadingState;
    private volatile CosmeticAssets assets;
    private volatile int failureCount;
    private volatile long lastFailureTimestamp;
    private ItemModel itemModel;
    private final AnimationContainer animationContainer;

    @Nullable
    private CosmeticAssetLoader assetLoader;

    @Nullable
    private CosmeticTypeData typeData;
    private int priorityLevel;
    private Position position;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/CosmeticDefinition$Position.class */
    public enum Position {
        LEFT,
        RIGHT,
        NONE
    }

    public CosmeticDefinition(int listId, CosmeticDetails cosmeticDetails, CosmeticType cosmeticType) {
        this(listId, cosmeticDetails, cosmeticType, null);
    }

    public CosmeticDefinition(int listId, CosmeticDetails cosmeticDetails, CosmeticType cosmeticType, @Nullable Cosmetic cosmetic) {
        this.taggedObject = new TaggedObject();
        this.disposeListeners = new ArrayList();
        this.loadingState = new AtomicReference<>(CosmeticLoadingState.IDLE);
        this.animationContainer = new AnimationContainer();
        this.position = Position.NONE;
        this.listId = listId;
        this.cosmeticDetails = cosmeticDetails;
        this.cosmeticType = cosmeticType;
        this.cosmetic = cosmetic;
    }

    public int listId() {
        return this.listId;
    }

    public int id() {
        return this.cosmeticDetails.getId();
    }

    public CosmeticDetails details() {
        return this.cosmeticDetails;
    }

    public CosmeticType cosmeticType() {
        return this.cosmeticType;
    }

    @Nullable
    public Cosmetic cosmetic() {
        return this.cosmetic;
    }

    public ItemModel itemModel() {
        return this.itemModel;
    }

    public void setItemModel(ItemModel itemModel) {
        if (this.itemModel != null) {
            this.itemModel.close();
        }
        this.itemModel = itemModel;
    }

    public AnimationContainer animationContainer() {
        return this.animationContainer;
    }

    public int getPriorityLevel() {
        return this.priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Nullable
    public CosmeticAssetLoader assetLoader() {
        return this.assetLoader;
    }

    public void setAssetLoader(@Nullable CosmeticAssetLoader assetLoader) {
        this.assetLoader = assetLoader;
    }

    @Nullable
    public CosmeticTypeData typeData() {
        return this.typeData;
    }

    @Nullable
    public <T extends CosmeticTypeData> T typeData(Class<T> type) {
        if (type.isInstance(this.typeData)) {
            return type.cast(this.typeData);
        }
        return null;
    }

    public void setTypeData(@Nullable CosmeticTypeData typeData) {
        this.typeData = typeData;
    }

    public boolean canBeRendered() {
        return (this.itemModel == null || this.itemModel.getModel() == null) ? false : true;
    }

    @Override // net.labymod.core.main.user.shop.Purchasable
    public String getName() {
        return this.cosmeticDetails.getName();
    }

    public CosmeticLoadingState loadingState() {
        return this.loadingState.get();
    }

    public boolean isAssetsLoaded() {
        return this.loadingState.get() == CosmeticLoadingState.LOADED;
    }

    @Nullable
    public CosmeticAssets assets() {
        return this.assets;
    }

    public boolean tryMarkLoading() {
        return this.loadingState.compareAndSet(CosmeticLoadingState.IDLE, CosmeticLoadingState.LOADING) || this.loadingState.compareAndSet(CosmeticLoadingState.FAILED, CosmeticLoadingState.LOADING);
    }

    public void markLoaded(CosmeticAssets assets) {
        this.assets = assets;
        this.failureCount = 0;
        this.loadingState.set(CosmeticLoadingState.LOADED);
    }

    public void markFailed() {
        this.failureCount++;
        this.lastFailureTimestamp = System.currentTimeMillis();
        this.loadingState.set(CosmeticLoadingState.FAILED);
    }

    public int failureCount() {
        return this.failureCount;
    }

    public long lastFailureTimestamp() {
        return this.lastFailureTimestamp;
    }

    public void setAssets(CosmeticAssets assets) {
        markLoaded(assets);
    }

    @Override // net.labymod.api.tag.Taggable
    public TaggedObject taggedObject() {
        return this.taggedObject;
    }

    @Override // net.labymod.api.util.Disposable
    public void addDisposeListener(Runnable listener) {
        this.disposeListeners.add(listener);
    }

    @Override // net.labymod.api.util.Disposable
    public void dispose() {
        for (Runnable listener : this.disposeListeners) {
            listener.run();
        }
    }

    public int hashCode() {
        return this.cosmeticDetails.getId();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof CosmeticDefinition)) {
            return false;
        }
        CosmeticDefinition other = (CosmeticDefinition) object;
        return id() == other.id();
    }
}
