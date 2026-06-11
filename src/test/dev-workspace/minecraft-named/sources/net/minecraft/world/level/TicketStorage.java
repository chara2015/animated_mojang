package net.minecraft.world.level;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.SharedConstants;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkLevel;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.server.level.Ticket;
import net.minecraft.server.level.TicketType;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/TicketStorage.class */
public class TicketStorage extends SavedData {
    private static final int INITIAL_TICKET_LIST_CAPACITY = 4;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Codec<Pair<ChunkPos, Ticket>> TICKET_ENTRY = Codec.mapPair(ChunkPos.CODEC.fieldOf("chunk_pos"), Ticket.CODEC).codec();
    public static final Codec<TicketStorage> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(TICKET_ENTRY.listOf().optionalFieldOf("tickets", List.of()).forGetter((v0) -> {
            return v0.packTickets();
        })).apply($$0, TicketStorage::fromPacked);
    });
    public static final SavedDataType<TicketStorage> TYPE = new SavedDataType<>("chunks", TicketStorage::new, CODEC, DataFixTypes.SAVED_DATA_FORCED_CHUNKS);
    private final Long2ObjectOpenHashMap<List<Ticket>> tickets;
    private final Long2ObjectOpenHashMap<List<Ticket>> deactivatedTickets;
    private LongSet chunksWithForcedTickets;
    private ChunkUpdated loadingChunkUpdatedListener;
    private ChunkUpdated simulationChunkUpdatedListener;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/TicketStorage$ChunkUpdated.class */
    @FunctionalInterface
    public interface ChunkUpdated {
        void update(long j, int i, boolean z);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/TicketStorage$TicketPredicate.class */
    public interface TicketPredicate {
        boolean test(Ticket ticket, long j);
    }

    private TicketStorage(Long2ObjectOpenHashMap<List<Ticket>> $$0, Long2ObjectOpenHashMap<List<Ticket>> $$1) {
        this.chunksWithForcedTickets = new LongOpenHashSet();
        this.tickets = $$0;
        this.deactivatedTickets = $$1;
        updateForcedChunks();
    }

    public TicketStorage() {
        this(new Long2ObjectOpenHashMap(4), new Long2ObjectOpenHashMap());
    }

    private static TicketStorage fromPacked(List<Pair<ChunkPos, Ticket>> $$0) {
        Long2ObjectOpenHashMap<List<Ticket>> $$1 = new Long2ObjectOpenHashMap<>();
        for (Pair<ChunkPos, Ticket> $$2 : $$0) {
            ChunkPos $$3 = (ChunkPos) $$2.getFirst();
            List<Ticket> $$4 = (List) $$1.computeIfAbsent($$3.toLong(), $$02 -> {
                return new ObjectArrayList(4);
            });
            $$4.add((Ticket) $$2.getSecond());
        }
        return new TicketStorage(new Long2ObjectOpenHashMap(4), $$1);
    }

    private List<Pair<ChunkPos, Ticket>> packTickets() {
        List<Pair<ChunkPos, Ticket>> $$0 = new ArrayList<>();
        forEachTicket(($$1, $$2) -> {
            if ($$2.getType().persist()) {
                $$0.add(new Pair($$1, $$2));
            }
        });
        return $$0;
    }

    private void forEachTicket(BiConsumer<ChunkPos, Ticket> $$0) {
        forEachTicket($$0, this.tickets);
        forEachTicket($$0, this.deactivatedTickets);
    }

    private static void forEachTicket(BiConsumer<ChunkPos, Ticket> $$0, Long2ObjectOpenHashMap<List<Ticket>> $$1) {
        ObjectIterator it = Long2ObjectMaps.fastIterable($$1).iterator();
        while (it.hasNext()) {
            Long2ObjectMap.Entry<List<Ticket>> $$2 = (Long2ObjectMap.Entry) it.next();
            ChunkPos $$3 = new ChunkPos($$2.getLongKey());
            for (Ticket $$4 : (List) $$2.getValue()) {
                $$0.accept($$3, $$4);
            }
        }
    }

    public void activateAllDeactivatedTickets() {
        ObjectIterator it = Long2ObjectMaps.fastIterable(this.deactivatedTickets).iterator();
        while (it.hasNext()) {
            Long2ObjectMap.Entry<List<Ticket>> $$0 = (Long2ObjectMap.Entry) it.next();
            for (Ticket $$1 : (List) $$0.getValue()) {
                addTicket($$0.getLongKey(), $$1);
            }
        }
        this.deactivatedTickets.clear();
    }

    public void setLoadingChunkUpdatedListener(ChunkUpdated $$0) {
        this.loadingChunkUpdatedListener = $$0;
    }

    public void setSimulationChunkUpdatedListener(ChunkUpdated $$0) {
        this.simulationChunkUpdatedListener = $$0;
    }

    public boolean hasTickets() {
        return !this.tickets.isEmpty();
    }

    public boolean shouldKeepDimensionActive() {
        ObjectIterator it = this.tickets.values().iterator();
        while (it.hasNext()) {
            List<Ticket> $$0 = (List) it.next();
            for (Ticket $$1 : $$0) {
                if ($$1.getType().shouldKeepDimensionActive()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Ticket> getTickets(long $$0) {
        return (List) this.tickets.getOrDefault($$0, List.of());
    }

    private List<Ticket> getOrCreateTickets(long $$0) {
        return (List) this.tickets.computeIfAbsent($$0, $$02 -> {
            return new ObjectArrayList(4);
        });
    }

    public void addTicketWithRadius(TicketType $$0, ChunkPos $$1, int $$2) {
        Ticket $$3 = new Ticket($$0, ChunkLevel.byStatus(FullChunkStatus.FULL) - $$2);
        addTicket($$1.toLong(), $$3);
    }

    public void addTicket(Ticket $$0, ChunkPos $$1) {
        addTicket($$1.toLong(), $$0);
    }

    public boolean addTicket(long $$0, Ticket $$1) {
        List<Ticket> $$2 = getOrCreateTickets($$0);
        for (Ticket $$3 : $$2) {
            if (isTicketSameTypeAndLevel($$1, $$3)) {
                $$3.resetTicksLeft();
                setDirty();
                return false;
            }
        }
        int $$4 = getTicketLevelAt($$2, true);
        int $$5 = getTicketLevelAt($$2, false);
        $$2.add($$1);
        if (SharedConstants.DEBUG_VERBOSE_SERVER_EVENTS) {
            LOGGER.debug("ATI {} {}", new ChunkPos($$0), $$1);
        }
        if ($$1.getType().doesSimulate() && $$1.getTicketLevel() < $$4 && this.simulationChunkUpdatedListener != null) {
            this.simulationChunkUpdatedListener.update($$0, $$1.getTicketLevel(), true);
        }
        if ($$1.getType().doesLoad() && $$1.getTicketLevel() < $$5 && this.loadingChunkUpdatedListener != null) {
            this.loadingChunkUpdatedListener.update($$0, $$1.getTicketLevel(), true);
        }
        if ($$1.getType().equals(TicketType.FORCED)) {
            this.chunksWithForcedTickets.add($$0);
        }
        setDirty();
        return true;
    }

    private static boolean isTicketSameTypeAndLevel(Ticket $$0, Ticket $$1) {
        return $$1.getType() == $$0.getType() && $$1.getTicketLevel() == $$0.getTicketLevel();
    }

    public int getTicketLevelAt(long $$0, boolean $$1) {
        return getTicketLevelAt(getTickets($$0), $$1);
    }

    private static int getTicketLevelAt(List<Ticket> $$0, boolean $$1) {
        Ticket $$2 = getLowestTicket($$0, $$1);
        return $$2 == null ? ChunkLevel.MAX_LEVEL + 1 : $$2.getTicketLevel();
    }

    private static Ticket getLowestTicket(List<Ticket> $$0, boolean $$1) {
        if ($$0 == null) {
            return null;
        }
        Ticket $$2 = null;
        for (Ticket $$3 : $$0) {
            if ($$2 == null || $$3.getTicketLevel() < $$2.getTicketLevel()) {
                if ($$1 && $$3.getType().doesSimulate()) {
                    $$2 = $$3;
                } else if (!$$1 && $$3.getType().doesLoad()) {
                    $$2 = $$3;
                }
            }
        }
        return $$2;
    }

    public void removeTicketWithRadius(TicketType $$0, ChunkPos $$1, int $$2) {
        Ticket $$3 = new Ticket($$0, ChunkLevel.byStatus(FullChunkStatus.FULL) - $$2);
        removeTicket($$1.toLong(), $$3);
    }

    public void removeTicket(Ticket $$0, ChunkPos $$1) {
        removeTicket($$1.toLong(), $$0);
    }

    public boolean removeTicket(long $$0, Ticket $$1) {
        List<Ticket> $$2 = (List) this.tickets.get($$0);
        if ($$2 == null) {
            return false;
        }
        boolean $$3 = false;
        Iterator<Ticket> $$4 = $$2.iterator();
        while (true) {
            if (!$$4.hasNext()) {
                break;
            }
            Ticket $$5 = $$4.next();
            if (isTicketSameTypeAndLevel($$1, $$5)) {
                $$4.remove();
                if (SharedConstants.DEBUG_VERBOSE_SERVER_EVENTS) {
                    LOGGER.debug("RTI {} {}", new ChunkPos($$0), $$5);
                }
                $$3 = true;
            }
        }
        if (!$$3) {
            return false;
        }
        if ($$2.isEmpty()) {
            this.tickets.remove($$0);
        }
        if ($$1.getType().doesSimulate() && this.simulationChunkUpdatedListener != null) {
            this.simulationChunkUpdatedListener.update($$0, getTicketLevelAt($$2, true), false);
        }
        if ($$1.getType().doesLoad() && this.loadingChunkUpdatedListener != null) {
            this.loadingChunkUpdatedListener.update($$0, getTicketLevelAt($$2, false), false);
        }
        if ($$1.getType().equals(TicketType.FORCED)) {
            updateForcedChunks();
        }
        setDirty();
        return true;
    }

    private void updateForcedChunks() {
        this.chunksWithForcedTickets = getAllChunksWithTicketThat($$0 -> {
            return $$0.getType().equals(TicketType.FORCED);
        });
    }

    public String getTicketDebugString(long $$0, boolean $$1) {
        List<Ticket> $$2 = getTickets($$0);
        Ticket $$3 = getLowestTicket($$2, $$1);
        return $$3 == null ? "no_ticket" : $$3.toString();
    }

    public void purgeStaleTickets(ChunkMap $$0) {
        removeTicketIf(($$1, $$2) -> {
            if (canTicketExpire($$0, $$1, $$2)) {
                $$1.decreaseTicksLeft();
                return $$1.isTimedOut();
            }
            return false;
        }, null);
        setDirty();
    }

    private boolean canTicketExpire(ChunkMap $$0, Ticket $$1, long $$2) {
        ChunkHolder $$3;
        if ($$1.getType().hasTimeout()) {
            return $$1.getType().canExpireIfUnloaded() || ($$3 = $$0.getUpdatingChunkIfPresent($$2)) == null || $$3.isReadyForSaving();
        }
        return false;
    }

    public void deactivateTicketsOnClosing() {
        removeTicketIf(($$0, $$1) -> {
            return $$0.getType() != TicketType.UNKNOWN;
        }, this.deactivatedTickets);
    }

    public void removeTicketIf(TicketPredicate $$0, Long2ObjectOpenHashMap<List<Ticket>> $$1) {
        ObjectIterator<Long2ObjectMap.Entry<List<Ticket>>> $$2 = this.tickets.long2ObjectEntrySet().fastIterator();
        boolean $$3 = false;
        while ($$2.hasNext()) {
            Long2ObjectMap.Entry<List<Ticket>> $$4 = (Long2ObjectMap.Entry) $$2.next();
            Iterator<Ticket> $$5 = ((List) $$4.getValue()).iterator();
            long $$6 = $$4.getLongKey();
            boolean $$7 = false;
            boolean $$8 = false;
            while ($$5.hasNext()) {
                Ticket $$9 = $$5.next();
                if ($$0.test($$9, $$6)) {
                    if ($$1 != null) {
                        List<Ticket> $$10 = (List) $$1.computeIfAbsent($$6, $$12 -> {
                            return new ObjectArrayList(((List) $$4.getValue()).size());
                        });
                        $$10.add($$9);
                    }
                    $$5.remove();
                    if ($$9.getType().doesLoad()) {
                        $$8 = true;
                    }
                    if ($$9.getType().doesSimulate()) {
                        $$7 = true;
                    }
                    if ($$9.getType().equals(TicketType.FORCED)) {
                        $$3 = true;
                    }
                }
            }
            if ($$8 || $$7) {
                if ($$8 && this.loadingChunkUpdatedListener != null) {
                    this.loadingChunkUpdatedListener.update($$6, getTicketLevelAt((List<Ticket>) $$4.getValue(), false), false);
                }
                if ($$7 && this.simulationChunkUpdatedListener != null) {
                    this.simulationChunkUpdatedListener.update($$6, getTicketLevelAt((List<Ticket>) $$4.getValue(), true), false);
                }
                setDirty();
                if (((List) $$4.getValue()).isEmpty()) {
                    $$2.remove();
                }
            }
        }
        if ($$3) {
            updateForcedChunks();
        }
    }

    public void replaceTicketLevelOfType(int $$0, TicketType $$1) {
        List<Pair<Ticket, Long>> $$2 = new ArrayList<>();
        ObjectIterator it = this.tickets.long2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Long2ObjectMap.Entry<List<Ticket>> $$3 = (Long2ObjectMap.Entry) it.next();
            for (Ticket $$4 : (List) $$3.getValue()) {
                if ($$4.getType() == $$1) {
                    $$2.add(Pair.of($$4, Long.valueOf($$3.getLongKey())));
                }
            }
        }
        for (Pair<Ticket, Long> $$5 : $$2) {
            Long $$6 = (Long) $$5.getSecond();
            Ticket $$7 = (Ticket) $$5.getFirst();
            removeTicket($$6.longValue(), $$7);
            TicketType $$8 = $$7.getType();
            addTicket($$6.longValue(), new Ticket($$8, $$0));
        }
    }

    public boolean updateChunkForced(ChunkPos $$0, boolean $$1) {
        Ticket $$2 = new Ticket(TicketType.FORCED, ChunkMap.FORCED_TICKET_LEVEL);
        if ($$1) {
            return addTicket($$0.toLong(), $$2);
        }
        return removeTicket($$0.toLong(), $$2);
    }

    public LongSet getForceLoadedChunks() {
        return this.chunksWithForcedTickets;
    }

    private LongSet getAllChunksWithTicketThat(Predicate<Ticket> $$0) {
        LongOpenHashSet $$1 = new LongOpenHashSet();
        ObjectIterator it = Long2ObjectMaps.fastIterable(this.tickets).iterator();
        while (it.hasNext()) {
            Long2ObjectMap.Entry<List<Ticket>> $$2 = (Long2ObjectMap.Entry) it.next();
            Iterator it2 = ((List) $$2.getValue()).iterator();
            while (true) {
                if (it2.hasNext()) {
                    Ticket $$3 = (Ticket) it2.next();
                    if ($$0.test($$3)) {
                        $$1.add($$2.getLongKey());
                        break;
                    }
                }
            }
        }
        return $$1;
    }
}
