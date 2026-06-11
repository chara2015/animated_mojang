package net.labymod.core.main.user;

import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/GameUserItem.class */
public class GameUserItem {
    private final CosmeticDefinition definition;
    private ItemMetadata itemMetadata;

    public GameUserItem(CosmeticDefinition definition, ItemMetadata itemMetadata) {
        this.definition = definition;
        this.itemMetadata = itemMetadata;
    }

    public CosmeticDefinition definition() {
        return this.definition;
    }

    public int identifier() {
        return this.definition.id();
    }

    public ItemMetadata itemMetadata() {
        return this.itemMetadata;
    }

    public void itemMetadata(ItemMetadata itemMetadata) {
        this.itemMetadata = itemMetadata;
    }
}
