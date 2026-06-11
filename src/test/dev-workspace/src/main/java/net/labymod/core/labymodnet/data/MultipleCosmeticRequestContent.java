package net.labymod.core.labymodnet.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.labymod.api.util.GsonUtil;
import net.labymod.core.labymodnet.models.ChangeResponse;
import net.labymod.core.labymodnet.models.Cosmetic;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/data/MultipleCosmeticRequestContent.class */
public class MultipleCosmeticRequestContent extends AbstractRequestContent {
    private final BulkModel model;

    public MultipleCosmeticRequestContent(CosmeticRequestType type, Consumer<ChangeResponse> changeResponseConsumer) {
        super(type, changeResponseConsumer);
        this.model = new BulkModel();
    }

    public BulkModel getModel() {
        return this.model;
    }

    @Override // net.labymod.core.labymodnet.data.RequestContent
    public void fill(Map<String, String> map) {
    }

    public String toString() {
        return GsonUtil.DEFAULT_GSON.toJson(this.model);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/data/MultipleCosmeticRequestContent$BulkModel.class */
    public static class BulkModel {
        private final List<BulkEntry> items;

        public BulkModel() {
            this(new ArrayList());
        }

        public BulkModel(List<BulkEntry> items) {
            this.items = items;
        }

        public void add(BulkEntry entry) {
            BulkEntry bulkEntry = getEntry(entry.getId());
            if (bulkEntry == null) {
                this.items.add(entry);
            } else {
                bulkEntry.setEnabled(entry.isEnabled());
            }
        }

        @Nullable
        public BulkEntry getEntry(int id) {
            for (BulkEntry item : this.items) {
                if (item.getId() == id) {
                    return item;
                }
            }
            return null;
        }

        public List<BulkEntry> getItems() {
            return this.items;
        }

        public void invalidate() {
            this.items.clear();
        }

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/data/MultipleCosmeticRequestContent$BulkModel$BulkEntry.class */
        public static class BulkEntry {
            private final transient Cosmetic cosmetic;
            private final transient Consumer<ChangeResponse> changeResponseConsumer;
            private int id;
            private boolean enabled;

            public BulkEntry(Cosmetic cosmetic, boolean enabled, Consumer<ChangeResponse> changeResponseConsumer) {
                this.cosmetic = cosmetic;
                this.id = cosmetic.getId();
                this.enabled = enabled;
                this.changeResponseConsumer = changeResponseConsumer;
            }

            public Cosmetic getCosmetic() {
                return this.cosmetic;
            }

            public int getId() {
                return this.id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public Consumer<ChangeResponse> getChangeResponseConsumer() {
                return this.changeResponseConsumer;
            }
        }
    }
}
