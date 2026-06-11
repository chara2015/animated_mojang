package net.minecraft.advancements;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/AdvancementTree.class */
public class AdvancementTree {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Map<Identifier, AdvancementNode> nodes = new Object2ObjectOpenHashMap();
    private final Set<AdvancementNode> roots = new ObjectLinkedOpenHashSet();
    private final Set<AdvancementNode> tasks = new ObjectLinkedOpenHashSet();
    private Listener listener;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/AdvancementTree$Listener.class */
    public interface Listener {
        void onAddAdvancementRoot(AdvancementNode advancementNode);

        void onRemoveAdvancementRoot(AdvancementNode advancementNode);

        void onAddAdvancementTask(AdvancementNode advancementNode);

        void onRemoveAdvancementTask(AdvancementNode advancementNode);

        void onAdvancementsCleared();
    }

    private void remove(AdvancementNode $$0) {
        for (AdvancementNode $$1 : $$0.children()) {
            remove($$1);
        }
        LOGGER.info("Forgot about advancement {}", $$0.holder());
        this.nodes.remove($$0.holder().id());
        if ($$0.parent() == null) {
            this.roots.remove($$0);
            if (this.listener != null) {
                this.listener.onRemoveAdvancementRoot($$0);
                return;
            }
            return;
        }
        this.tasks.remove($$0);
        if (this.listener != null) {
            this.listener.onRemoveAdvancementTask($$0);
        }
    }

    public void remove(Set<Identifier> $$0) {
        for (Identifier $$1 : $$0) {
            AdvancementNode $$2 = this.nodes.get($$1);
            if ($$2 == null) {
                LOGGER.warn("Told to remove advancement {} but I don't know what that is", $$1);
            } else {
                remove($$2);
            }
        }
    }

    public void addAll(Collection<AdvancementHolder> $$0) {
        List<AdvancementHolder> $$1 = new ArrayList<>($$0);
        while (true) {
            if ($$1.isEmpty()) {
                break;
            } else if (!$$1.removeIf(this::tryInsert)) {
                LOGGER.error("Couldn't load advancements: {}", $$1);
                break;
            }
        }
        LOGGER.info("Loaded {} advancements", Integer.valueOf(this.nodes.size()));
    }

    private boolean tryInsert(AdvancementHolder $$0) {
        Optional<Identifier> $$1 = $$0.value().parent();
        Map<Identifier, AdvancementNode> map = this.nodes;
        Objects.requireNonNull(map);
        AdvancementNode $$2 = (AdvancementNode) $$1.map((v1) -> {
            return r1.get(v1);
        }).orElse(null);
        if ($$2 == null && $$1.isPresent()) {
            return false;
        }
        AdvancementNode $$3 = new AdvancementNode($$0, $$2);
        if ($$2 != null) {
            $$2.addChild($$3);
        }
        this.nodes.put($$0.id(), $$3);
        if ($$2 == null) {
            this.roots.add($$3);
            if (this.listener != null) {
                this.listener.onAddAdvancementRoot($$3);
                return true;
            }
            return true;
        }
        this.tasks.add($$3);
        if (this.listener != null) {
            this.listener.onAddAdvancementTask($$3);
            return true;
        }
        return true;
    }

    public void clear() {
        this.nodes.clear();
        this.roots.clear();
        this.tasks.clear();
        if (this.listener != null) {
            this.listener.onAdvancementsCleared();
        }
    }

    public Iterable<AdvancementNode> roots() {
        return this.roots;
    }

    public Collection<AdvancementNode> nodes() {
        return this.nodes.values();
    }

    public AdvancementNode get(Identifier $$0) {
        return this.nodes.get($$0);
    }

    public AdvancementNode get(AdvancementHolder $$0) {
        return this.nodes.get($$0.id());
    }

    public void setListener(Listener $$0) {
        this.listener = $$0;
        if ($$0 != null) {
            for (AdvancementNode $$1 : this.roots) {
                $$0.onAddAdvancementRoot($$1);
            }
            for (AdvancementNode $$2 : this.tasks) {
                $$0.onAddAdvancementTask($$2);
            }
        }
    }
}
