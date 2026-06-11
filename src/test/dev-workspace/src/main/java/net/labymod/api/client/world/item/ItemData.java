package net.labymod.api.client.world.item;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.component.data.DataComponentContainer;
import net.labymod.api.component.data.DataComponentKey;
import net.labymod.api.component.data.DataComponentPatch;
import net.labymod.api.component.data.NbtDataComponentContainer;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.nbt.NBTFactory;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagCompound;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/item/ItemData.class */
public final class ItemData {
    private static final NBTFactory NBT_FACTORY = Laby.references().nbtFactory();
    private static final ItemStackFactory ITEM_STACK_FACTORY = Laby.references().itemStackFactory();
    private final String namespace;
    private final String path;
    private final NBTTagCompound nbt;
    private final NBTTagCompound components;

    @SerializedName("legacy_data")
    private final int legacyData;

    public ItemData(String namespace, String path, NBTTagCompound nbt, NBTTagCompound components, int legacyData) {
        this.namespace = namespace;
        this.path = path;
        this.nbt = nbt;
        this.components = components;
        this.legacyData = legacyData;
    }

    public static ItemData from(ItemStack stack) {
        ResourceLocation identifier = stack.getIdentifier();
        String namespace = identifier.getNamespace();
        String path = identifier.getPath();
        if (PlatformEnvironment.ITEM_COMPONENTS) {
            return new ItemData(namespace, path, toNbtCompound(DataComponentContainer.EMPTY), toNbtCompound(stack.getDataComponentContainer()), stack.getLegacyItemData());
        }
        return new ItemData(namespace, path, toNbtCompound(stack.getDataComponentContainer()), toNbtCompound(DataComponentContainer.EMPTY), stack.getLegacyItemData());
    }

    private static NBTTagCompound toNbtCompound(DataComponentContainer container) {
        NBTTagCompound compound = NBT_FACTORY.compound();
        if (container.isEmpty()) {
            return compound;
        }
        for (DataComponentKey key : container.keySet()) {
            compound.set(key.name(), (NBTTag) container.get(key));
        }
        return compound;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getPath() {
        return this.path;
    }

    public NBTTagCompound nbt() {
        return this.nbt;
    }

    public NBTTagCompound components() {
        return this.components;
    }

    @SerializedName("legacy_data")
    public int legacyData() {
        return this.legacyData;
    }

    public ItemStack toItemStack() {
        ItemStack stack = ITEM_STACK_FACTORY.create(this.namespace, this.path);
        stack.setLegacyItemData(this.legacyData);
        DataComponentPatch patch = DataComponentPatch.createPatch(createComponentContainer());
        stack.applyPatchToDataComponent(patch);
        return stack;
    }

    public ResourceLocation toResourceLocation() {
        return ResourceLocation.create(this.namespace, this.path);
    }

    private DataComponentContainer createComponentContainer() {
        NBTTagCompound nBTTagCompound;
        if (PlatformEnvironment.ITEM_COMPONENTS) {
            nBTTagCompound = this.components;
        } else {
            nBTTagCompound = this.nbt;
        }
        NBTTagCompound compound = nBTTagCompound;
        if (compound == null) {
            compound = NBT_FACTORY.compound();
        }
        return new NbtDataComponentContainer(compound);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        ItemData that = (ItemData) obj;
        return Objects.equals(this.namespace, that.namespace) && Objects.equals(this.path, that.path) && Objects.equals(this.nbt, that.nbt) && Objects.equals(this.components, that.components) && this.legacyData == that.legacyData;
    }

    public int hashCode() {
        int result = Objects.hashCode(this.namespace);
        return (31 * ((31 * ((31 * ((31 * result) + Objects.hashCode(this.path))) + Objects.hashCode(this.nbt))) + Objects.hashCode(this.components))) + this.legacyData;
    }

    public String toString() {
        return "ItemData{namespace='" + this.namespace + "', path='" + this.path + "', nbt=" + String.valueOf(this.nbt) + ", components=" + String.valueOf(this.components) + ", legacyData=" + this.legacyData + "}";
    }
}
