package net.minecraft.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementTree;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.advancements.AdvancementVisibilityEvaluator;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.FileUtil;
import net.minecraft.util.StrictJsonParser;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.gamerules.GameRules;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/PlayerAdvancements.class */
public class PlayerAdvancements {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final PlayerList playerList;
    private final Path playerSavePath;
    private AdvancementTree tree;
    private ServerPlayer player;
    private AdvancementHolder lastSelectedTab;
    private final Codec<Data> codec;
    private final Map<AdvancementHolder, AdvancementProgress> progress = new LinkedHashMap();
    private final Set<AdvancementHolder> visible = new HashSet();
    private final Set<AdvancementHolder> progressChanged = new HashSet();
    private final Set<AdvancementNode> rootsToUpdate = new HashSet();
    private boolean isFirstPacket = true;

    public PlayerAdvancements(DataFixer $$0, PlayerList $$1, ServerAdvancementManager $$2, Path $$3, ServerPlayer $$4) {
        this.playerList = $$1;
        this.playerSavePath = $$3;
        this.player = $$4;
        this.tree = $$2.tree();
        this.codec = DataFixTypes.ADVANCEMENTS.wrapCodec(Data.CODEC, $$0, 1343);
        load($$2);
    }

    public void setPlayer(ServerPlayer $$0) {
        this.player = $$0;
    }

    public void stopListening() {
        for (CriterionTrigger<?> $$0 : BuiltInRegistries.TRIGGER_TYPES) {
            $$0.removePlayerListeners(this);
        }
    }

    public void reload(ServerAdvancementManager $$0) {
        stopListening();
        this.progress.clear();
        this.visible.clear();
        this.rootsToUpdate.clear();
        this.progressChanged.clear();
        this.isFirstPacket = true;
        this.lastSelectedTab = null;
        this.tree = $$0.tree();
        load($$0);
    }

    private void registerListeners(ServerAdvancementManager $$0) {
        for (AdvancementHolder $$1 : $$0.getAllAdvancements()) {
            registerListeners($$1);
        }
    }

    private void checkForAutomaticTriggers(ServerAdvancementManager $$0) {
        for (AdvancementHolder $$1 : $$0.getAllAdvancements()) {
            Advancement $$2 = $$1.value();
            if ($$2.criteria().isEmpty()) {
                award($$1, "");
                $$2.rewards().grant(this.player);
            }
        }
    }

    private void load(ServerAdvancementManager $$0) {
        if (Files.isRegularFile(this.playerSavePath, new LinkOption[0])) {
            try {
                Reader $$1 = Files.newBufferedReader(this.playerSavePath, StandardCharsets.UTF_8);
                try {
                    JsonElement $$2 = StrictJsonParser.parse($$1);
                    Data $$3 = (Data) this.codec.parse(JsonOps.INSTANCE, $$2).getOrThrow(JsonParseException::new);
                    applyFrom($$0, $$3);
                    if ($$1 != null) {
                        $$1.close();
                    }
                } catch (Throwable th) {
                    if ($$1 != null) {
                        try {
                            $$1.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (JsonParseException $$5) {
                LOGGER.error("Couldn't parse player advancements in {}", this.playerSavePath, $$5);
            } catch (IOException | JsonIOException $$4) {
                LOGGER.error("Couldn't access player advancements in {}", this.playerSavePath, $$4);
            }
        }
        checkForAutomaticTriggers($$0);
        registerListeners($$0);
    }

    public void save() {
        JsonElement $$0 = (JsonElement) this.codec.encodeStart(JsonOps.INSTANCE, asData()).getOrThrow();
        try {
            FileUtil.createDirectoriesSafe(this.playerSavePath.getParent());
            Writer $$1 = Files.newBufferedWriter(this.playerSavePath, StandardCharsets.UTF_8, new OpenOption[0]);
            try {
                GSON.toJson($$0, GSON.newJsonWriter($$1));
                if ($$1 != null) {
                    $$1.close();
                }
            } finally {
            }
        } catch (IOException | JsonIOException $$2) {
            LOGGER.error("Couldn't save player advancements to {}", this.playerSavePath, $$2);
        }
    }

    private void applyFrom(ServerAdvancementManager $$0, Data $$1) {
        $$1.forEach(($$12, $$2) -> {
            AdvancementHolder $$3 = $$0.get($$12);
            if ($$3 == null) {
                LOGGER.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", $$12, this.playerSavePath);
                return;
            }
            startProgress($$3, $$2);
            this.progressChanged.add($$3);
            markForVisibilityUpdate($$3);
        });
    }

    private Data asData() {
        Map<Identifier, AdvancementProgress> $$0 = new LinkedHashMap<>();
        this.progress.forEach(($$1, $$2) -> {
            if ($$2.hasProgress()) {
                $$0.put($$1.id(), $$2);
            }
        });
        return new Data($$0);
    }

    public boolean award(AdvancementHolder $$0, String $$1) {
        boolean $$2 = false;
        AdvancementProgress $$3 = getOrStartProgress($$0);
        boolean $$4 = $$3.isDone();
        if ($$3.grantProgress($$1)) {
            unregisterListeners($$0);
            this.progressChanged.add($$0);
            $$2 = true;
            if (!$$4 && $$3.isDone()) {
                $$0.value().rewards().grant(this.player);
                $$0.value().display().ifPresent($$12 -> {
                    if ($$12.shouldAnnounceChat() && ((Boolean) this.player.level().getGameRules().get(GameRules.SHOW_ADVANCEMENT_MESSAGES)).booleanValue()) {
                        this.playerList.broadcastSystemMessage($$12.getType().createAnnouncement($$0, this.player), false);
                    }
                });
            }
        }
        if (!$$4 && $$3.isDone()) {
            markForVisibilityUpdate($$0);
        }
        return $$2;
    }

    public boolean revoke(AdvancementHolder $$0, String $$1) {
        boolean $$2 = false;
        AdvancementProgress $$3 = getOrStartProgress($$0);
        boolean $$4 = $$3.isDone();
        if ($$3.revokeProgress($$1)) {
            registerListeners($$0);
            this.progressChanged.add($$0);
            $$2 = true;
        }
        if ($$4 && !$$3.isDone()) {
            markForVisibilityUpdate($$0);
        }
        return $$2;
    }

    private void markForVisibilityUpdate(AdvancementHolder $$0) {
        AdvancementNode $$1 = this.tree.get($$0);
        if ($$1 != null) {
            this.rootsToUpdate.add($$1.root());
        }
    }

    private void registerListeners(AdvancementHolder $$0) {
        AdvancementProgress $$1 = getOrStartProgress($$0);
        if ($$1.isDone()) {
            return;
        }
        for (Map.Entry<String, Criterion<?>> $$2 : $$0.value().criteria().entrySet()) {
            CriterionProgress $$3 = $$1.getCriterion($$2.getKey());
            if ($$3 != null && !$$3.isDone()) {
                registerListener($$0, $$2.getKey(), $$2.getValue());
            }
        }
    }

    private <T extends CriterionTriggerInstance> void registerListener(AdvancementHolder $$0, String $$1, Criterion<T> $$2) {
        $$2.trigger().addPlayerListener(this, new CriterionTrigger.Listener<>($$2.triggerInstance(), $$0, $$1));
    }

    private void unregisterListeners(AdvancementHolder $$0) {
        AdvancementProgress $$1 = getOrStartProgress($$0);
        for (Map.Entry<String, Criterion<?>> $$2 : $$0.value().criteria().entrySet()) {
            CriterionProgress $$3 = $$1.getCriterion($$2.getKey());
            if ($$3 != null && ($$3.isDone() || $$1.isDone())) {
                removeListener($$0, $$2.getKey(), $$2.getValue());
            }
        }
    }

    private <T extends CriterionTriggerInstance> void removeListener(AdvancementHolder $$0, String $$1, Criterion<T> $$2) {
        $$2.trigger().removePlayerListener(this, new CriterionTrigger.Listener<>($$2.triggerInstance(), $$0, $$1));
    }

    public void flushDirty(ServerPlayer $$0, boolean $$1) {
        if (this.isFirstPacket || !this.rootsToUpdate.isEmpty() || !this.progressChanged.isEmpty()) {
            Map<Identifier, AdvancementProgress> $$2 = new HashMap<>();
            Set<AdvancementHolder> $$3 = new HashSet<>();
            Set<Identifier> $$4 = new HashSet<>();
            for (AdvancementNode $$5 : this.rootsToUpdate) {
                updateTreeVisibility($$5, $$3, $$4);
            }
            this.rootsToUpdate.clear();
            for (AdvancementHolder $$6 : this.progressChanged) {
                if (this.visible.contains($$6)) {
                    $$2.put($$6.id(), this.progress.get($$6));
                }
            }
            this.progressChanged.clear();
            if (!$$2.isEmpty() || !$$3.isEmpty() || !$$4.isEmpty()) {
                $$0.connection.send(new ClientboundUpdateAdvancementsPacket(this.isFirstPacket, $$3, $$4, $$2, $$1));
            }
        }
        this.isFirstPacket = false;
    }

    public void setSelectedTab(AdvancementHolder $$0) {
        AdvancementHolder $$1 = this.lastSelectedTab;
        if ($$0 != null && $$0.value().isRoot() && $$0.value().display().isPresent()) {
            this.lastSelectedTab = $$0;
        } else {
            this.lastSelectedTab = null;
        }
        if ($$1 != this.lastSelectedTab) {
            this.player.connection.send(new ClientboundSelectAdvancementsTabPacket(this.lastSelectedTab == null ? null : this.lastSelectedTab.id()));
        }
    }

    public AdvancementProgress getOrStartProgress(AdvancementHolder $$0) {
        AdvancementProgress $$1 = this.progress.get($$0);
        if ($$1 == null) {
            $$1 = new AdvancementProgress();
            startProgress($$0, $$1);
        }
        return $$1;
    }

    private void startProgress(AdvancementHolder $$0, AdvancementProgress $$1) {
        $$1.update($$0.value().requirements());
        this.progress.put($$0, $$1);
    }

    private void updateTreeVisibility(AdvancementNode $$0, Set<AdvancementHolder> $$1, Set<Identifier> $$2) {
        AdvancementVisibilityEvaluator.evaluateVisibility($$0, $$02 -> {
            return getOrStartProgress($$02.holder()).isDone();
        }, ($$22, $$3) -> {
            AdvancementHolder $$4 = $$22.holder();
            if ($$3) {
                if (this.visible.add($$4)) {
                    $$1.add($$4);
                    if (this.progress.containsKey($$4)) {
                        this.progressChanged.add($$4);
                        return;
                    }
                    return;
                }
                return;
            }
            if (this.visible.remove($$4)) {
                $$2.add($$4.id());
            }
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/PlayerAdvancements$Data.class */
    static final class Data extends Record {
        private final Map<Identifier, AdvancementProgress> map;
        public static final Codec<Data> CODEC = Codec.unboundedMap(Identifier.CODEC, AdvancementProgress.CODEC).xmap(Data::new, (v0) -> {
            return v0.map();
        });

        Data(Map<Identifier, AdvancementProgress> $$0) {
            this.map = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Data.class), Data.class, "map", "FIELD:Lnet/minecraft/server/PlayerAdvancements$Data;->map:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Data.class), Data.class, "map", "FIELD:Lnet/minecraft/server/PlayerAdvancements$Data;->map:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Data.class, Object.class), Data.class, "map", "FIELD:Lnet/minecraft/server/PlayerAdvancements$Data;->map:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Map<Identifier, AdvancementProgress> map() {
            return this.map;
        }

        public void forEach(BiConsumer<Identifier, AdvancementProgress> $$0) {
            this.map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach($$1 -> {
                $$0.accept((Identifier) $$1.getKey(), (AdvancementProgress) $$1.getValue());
            });
        }
    }
}
