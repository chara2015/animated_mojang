package net.minecraft.world.entity.ai;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeSystem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.ExpirableValue;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/Brain.class */
public class Brain<E extends LivingEntity> {
    static final Logger LOGGER = LogUtils.getLogger();
    private final Supplier<Codec<Brain<E>>> codec;
    private static final int SCHEDULE_UPDATE_DELAY = 20;
    private EnvironmentAttribute<Activity> schedule;
    private final Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> memories = Maps.newHashMap();
    private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> sensors = Maps.newLinkedHashMap();
    private final Map<Integer, Map<Activity, Set<BehaviorControl<? super E>>>> availableBehaviorsByPriority = Maps.newTreeMap();
    private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryStatus>>> activityRequirements = Maps.newHashMap();
    private final Map<Activity, Set<MemoryModuleType<?>>> activityMemoriesToEraseWhenStopped = Maps.newHashMap();
    private Set<Activity> coreActivities = Sets.newHashSet();
    private final Set<Activity> activeActivities = Sets.newHashSet();
    private Activity defaultActivity = Activity.IDLE;
    private long lastScheduleUpdate = -9999;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/Brain$Provider.class */
    public static final class Provider<E extends LivingEntity> {
        private final Collection<? extends MemoryModuleType<?>> memoryTypes;
        private final Collection<? extends SensorType<? extends Sensor<? super E>>> sensorTypes;
        private final Codec<Brain<E>> codec;

        Provider(Collection<? extends MemoryModuleType<?>> $$0, Collection<? extends SensorType<? extends Sensor<? super E>>> $$1) {
            this.memoryTypes = $$0;
            this.sensorTypes = $$1;
            this.codec = Brain.codec($$0, $$1);
        }

        public Brain<E> makeBrain(Dynamic<?> $$0) {
            DataResult dataResult = this.codec.parse($$0);
            Logger logger = Brain.LOGGER;
            Objects.requireNonNull(logger);
            return (Brain) dataResult.resultOrPartial(logger::error).orElseGet(() -> {
                return new Brain(this.memoryTypes, this.sensorTypes, ImmutableList.of(), () -> {
                    return this.codec;
                });
            });
        }
    }

    public static <E extends LivingEntity> Provider<E> provider(Collection<? extends MemoryModuleType<?>> $$0, Collection<? extends SensorType<? extends Sensor<? super E>>> $$1) {
        return new Provider<>($$0, $$1);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [net.minecraft.world.entity.ai.Brain$1] */
    public static <E extends LivingEntity> Codec<Brain<E>> codec(final Collection<? extends MemoryModuleType<?>> $$0, final Collection<? extends SensorType<? extends Sensor<? super E>>> $$1) {
        final MutableObject<Codec<Brain<E>>> $$2 = new MutableObject<>();
        $$2.setValue(new MapCodec<Brain<E>>() { // from class: net.minecraft.world.entity.ai.Brain.1
            public <T> Stream<T> keys(DynamicOps<T> $$02) {
                return $$0.stream().flatMap($$03 -> {
                    return $$03.getCodec().map($$12 -> {
                        return BuiltInRegistries.MEMORY_MODULE_TYPE.getKey($$03);
                    }).stream();
                }).map($$12 -> {
                    return $$02.createString($$12.toString());
                });
            }

            public <T> DataResult<Brain<E>> decode(DynamicOps<T> $$02, MapLike<T> $$12) {
                MutableObject<DataResult<ImmutableList.Builder<MemoryValue<?>>>> $$22 = new MutableObject<>(DataResult.success(ImmutableList.builder()));
                $$12.entries().forEach($$23 -> {
                    DataResult<MemoryModuleType<?>> $$3 = BuiltInRegistries.MEMORY_MODULE_TYPE.byNameCodec().parse($$02, $$23.getFirst());
                    DataResult<? extends MemoryValue<?>> $$4 = $$3.flatMap($$23 -> {
                        return captureRead($$23, $$02, $$23.getSecond());
                    });
                    $$22.setValue(((DataResult) $$22.get()).apply2((v0, v1) -> {
                        return v0.add(v1);
                    }, $$4));
                });
                DataResult dataResult = (DataResult) $$22.get();
                Logger logger = Brain.LOGGER;
                Objects.requireNonNull(logger);
                ImmutableList<MemoryValue<?>> $$3 = (ImmutableList) dataResult.resultOrPartial(logger::error).map((v0) -> {
                    return v0.build();
                }).orElseGet(ImmutableList::of);
                return DataResult.success(new Brain($$0, $$1, $$3, $$2));
            }

            private <T, U> DataResult<MemoryValue<U>> captureRead(MemoryModuleType<U> $$02, DynamicOps<T> $$12, T $$22) {
                return ((DataResult) $$02.getCodec().map((v0) -> {
                    return DataResult.success(v0);
                }).orElseGet(() -> {
                    return DataResult.error(() -> {
                        return "No codec for memory: " + String.valueOf($$02);
                    });
                })).flatMap($$23 -> {
                    return $$23.parse($$12, $$22);
                }).map($$13 -> {
                    return new MemoryValue($$02, Optional.of($$13));
                });
            }

            public <T> RecordBuilder<T> encode(Brain<E> $$02, DynamicOps<T> $$12, RecordBuilder<T> $$22) {
                $$02.memories().forEach($$23 -> {
                    $$23.serialize($$12, $$22);
                });
                return $$22;
            }
        }.fieldOf("memories").codec());
        return (Codec) $$2.get();
    }

    public Brain(Collection<? extends MemoryModuleType<?>> $$0, Collection<? extends SensorType<? extends Sensor<? super E>>> $$1, ImmutableList<MemoryValue<?>> $$2, Supplier<Codec<Brain<E>>> $$3) {
        this.codec = $$3;
        for (MemoryModuleType<?> $$4 : $$0) {
            this.memories.put($$4, Optional.empty());
        }
        for (SensorType<? extends Sensor<? super E>> $$5 : $$1) {
            this.sensors.put($$5, $$5.create());
        }
        for (Sensor<? super E> $$6 : this.sensors.values()) {
            for (MemoryModuleType<?> $$7 : $$6.requires()) {
                this.memories.put($$7, Optional.empty());
            }
        }
        UnmodifiableIterator it = $$2.iterator();
        while (it.hasNext()) {
            MemoryValue<?> $$8 = (MemoryValue) it.next();
            $$8.setMemoryInternal(this);
        }
    }

    public <T> DataResult<T> serializeStart(DynamicOps<T> $$0) {
        return this.codec.get().encodeStart($$0, this);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/ai/Brain$MemoryValue.class */
    static final class MemoryValue<U> {
        private final MemoryModuleType<U> type;
        private final Optional<? extends ExpirableValue<U>> value;

        /* JADX INFO: Access modifiers changed from: package-private */
        public static <U> MemoryValue<U> createUnchecked(MemoryModuleType<U> $$0, Optional<? extends ExpirableValue<?>> $$1) {
            return new MemoryValue<>($$0, $$1);
        }

        MemoryValue(MemoryModuleType<U> $$0, Optional<? extends ExpirableValue<U>> $$1) {
            this.type = $$0;
            this.value = $$1;
        }

        void setMemoryInternal(Brain<?> $$0) {
            $$0.setMemoryInternal(this.type, this.value);
        }

        public <T> void serialize(DynamicOps<T> $$0, RecordBuilder<T> $$1) {
            this.type.getCodec().ifPresent($$2 -> {
                this.value.ifPresent($$3 -> {
                    $$1.add(BuiltInRegistries.MEMORY_MODULE_TYPE.byNameCodec().encodeStart($$0, this.type), $$2.encodeStart($$0, $$3));
                });
            });
        }
    }

    Stream<MemoryValue<?>> memories() {
        return this.memories.entrySet().stream().map($$0 -> {
            return MemoryValue.createUnchecked((MemoryModuleType) $$0.getKey(), (Optional) $$0.getValue());
        });
    }

    public boolean hasMemoryValue(MemoryModuleType<?> $$0) {
        return checkMemory($$0, MemoryStatus.VALUE_PRESENT);
    }

    public void clearMemories() {
        this.memories.keySet().forEach($$0 -> {
            this.memories.put($$0, Optional.empty());
        });
    }

    public <U> void eraseMemory(MemoryModuleType<U> $$0) {
        setMemory((MemoryModuleType) $$0, (Optional) Optional.empty());
    }

    public <U> void setMemory(MemoryModuleType<U> $$0, U $$1) {
        setMemory((MemoryModuleType) $$0, (Optional) Optional.ofNullable($$1));
    }

    public <U> void setMemoryWithExpiry(MemoryModuleType<U> $$0, U $$1, long $$2) {
        setMemoryInternal($$0, Optional.of(ExpirableValue.of($$1, $$2)));
    }

    public <U> void setMemory(MemoryModuleType<U> $$0, Optional<? extends U> $$1) {
        setMemoryInternal($$0, $$1.map(ExpirableValue::of));
    }

    <U> void setMemoryInternal(MemoryModuleType<U> $$0, Optional<? extends ExpirableValue<?>> $$1) {
        if (this.memories.containsKey($$0)) {
            if ($$1.isPresent() && isEmptyCollection($$1.get().getValue())) {
                eraseMemory($$0);
            } else {
                this.memories.put($$0, $$1);
            }
        }
    }

    public <U> Optional<U> getMemory(MemoryModuleType<U> $$0) {
        Optional<? extends ExpirableValue<?>> $$1 = this.memories.get($$0);
        if ($$1 == null) {
            throw new IllegalStateException("Unregistered memory fetched: " + String.valueOf($$0));
        }
        return $$1.map((v0) -> {
            return v0.getValue();
        });
    }

    public <U> Optional<U> getMemoryInternal(MemoryModuleType<U> $$0) {
        Optional<? extends ExpirableValue<?>> $$1 = this.memories.get($$0);
        if ($$1 == null) {
            return null;
        }
        return $$1.map((v0) -> {
            return v0.getValue();
        });
    }

    public <U> long getTimeUntilExpiry(MemoryModuleType<U> memoryModuleType) {
        return ((Long) this.memories.get(memoryModuleType).map((v0) -> {
            return v0.getTimeToLive();
        }).orElse(0L)).longValue();
    }

    @VisibleForDebug
    @Deprecated
    public Map<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> getMemories() {
        return this.memories;
    }

    public <U> boolean isMemoryValue(MemoryModuleType<U> $$0, U $$1) {
        if (!hasMemoryValue($$0)) {
            return false;
        }
        return getMemory($$0).filter($$12 -> {
            return $$12.equals($$1);
        }).isPresent();
    }

    public boolean checkMemory(MemoryModuleType<?> $$0, MemoryStatus $$1) {
        Optional<? extends ExpirableValue<?>> $$2 = this.memories.get($$0);
        if ($$2 == null) {
            return false;
        }
        return $$1 == MemoryStatus.REGISTERED || ($$1 == MemoryStatus.VALUE_PRESENT && $$2.isPresent()) || ($$1 == MemoryStatus.VALUE_ABSENT && $$2.isEmpty());
    }

    public void setSchedule(EnvironmentAttribute<Activity> $$0) {
        this.schedule = $$0;
    }

    public void setCoreActivities(Set<Activity> $$0) {
        this.coreActivities = $$0;
    }

    @VisibleForDebug
    @Deprecated
    public Set<Activity> getActiveActivities() {
        return this.activeActivities;
    }

    @VisibleForDebug
    @Deprecated
    public List<BehaviorControl<? super E>> getRunningBehaviors() {
        ObjectArrayList objectArrayList = new ObjectArrayList();
        for (Map<Activity, Set<BehaviorControl<? super E>>> $$1 : this.availableBehaviorsByPriority.values()) {
            for (Set<BehaviorControl<? super E>> $$2 : $$1.values()) {
                for (BehaviorControl<? super E> $$3 : $$2) {
                    if ($$3.getStatus() == Behavior.Status.RUNNING) {
                        objectArrayList.add($$3);
                    }
                }
            }
        }
        return objectArrayList;
    }

    public void useDefaultActivity() {
        setActiveActivity(this.defaultActivity);
    }

    public Optional<Activity> getActiveNonCoreActivity() {
        for (Activity $$0 : this.activeActivities) {
            if (!this.coreActivities.contains($$0)) {
                return Optional.of($$0);
            }
        }
        return Optional.empty();
    }

    public void setActiveActivityIfPossible(Activity $$0) {
        if (activityRequirementsAreMet($$0)) {
            setActiveActivity($$0);
        } else {
            useDefaultActivity();
        }
    }

    private void setActiveActivity(Activity $$0) {
        if (isActive($$0)) {
            return;
        }
        eraseMemoriesForOtherActivitesThan($$0);
        this.activeActivities.clear();
        this.activeActivities.addAll(this.coreActivities);
        this.activeActivities.add($$0);
    }

    private void eraseMemoriesForOtherActivitesThan(Activity $$0) {
        Set<MemoryModuleType<?>> $$2;
        for (Activity $$1 : this.activeActivities) {
            if ($$1 != $$0 && ($$2 = this.activityMemoriesToEraseWhenStopped.get($$1)) != null) {
                Iterator<MemoryModuleType<?>> it = $$2.iterator();
                while (it.hasNext()) {
                    eraseMemory((MemoryModuleType) it.next());
                }
            }
        }
    }

    public void updateActivityFromSchedule(EnvironmentAttributeSystem $$0, long $$1, Vec3 $$2) {
        if ($$1 - this.lastScheduleUpdate > 20) {
            this.lastScheduleUpdate = $$1;
            Activity $$3 = this.schedule != null ? (Activity) $$0.getValue(this.schedule, $$2) : Activity.IDLE;
            if (!this.activeActivities.contains($$3)) {
                setActiveActivityIfPossible($$3);
            }
        }
    }

    public void setActiveActivityToFirstValid(List<Activity> $$0) {
        for (Activity $$1 : $$0) {
            if (activityRequirementsAreMet($$1)) {
                setActiveActivity($$1);
                return;
            }
        }
    }

    public void setDefaultActivity(Activity $$0) {
        this.defaultActivity = $$0;
    }

    public void addActivity(Activity $$0, int $$1, ImmutableList<? extends BehaviorControl<? super E>> $$2) {
        addActivity($$0, createPriorityPairs($$1, $$2));
    }

    public void addActivityAndRemoveMemoryWhenStopped(Activity $$0, int $$1, ImmutableList<? extends BehaviorControl<? super E>> $$2, MemoryModuleType<?> $$3) {
        addActivityAndRemoveMemoriesWhenStopped($$0, createPriorityPairs($$1, $$2), ImmutableSet.of(Pair.of($$3, MemoryStatus.VALUE_PRESENT)), ImmutableSet.of($$3));
    }

    public void addActivity(Activity $$0, ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> $$1) {
        addActivityAndRemoveMemoriesWhenStopped($$0, $$1, ImmutableSet.of(), Sets.newHashSet());
    }

    public void addActivityWithConditions(Activity $$0, int $$1, ImmutableList<? extends BehaviorControl<? super E>> $$2, Set<Pair<MemoryModuleType<?>, MemoryStatus>> $$3) {
        addActivityWithConditions($$0, createPriorityPairs($$1, $$2), $$3);
    }

    public void addActivityWithConditions(Activity $$0, ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> $$1, Set<Pair<MemoryModuleType<?>, MemoryStatus>> $$2) {
        addActivityAndRemoveMemoriesWhenStopped($$0, $$1, $$2, Sets.newHashSet());
    }

    public void addActivityAndRemoveMemoriesWhenStopped(Activity $$0, ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> $$1, Set<Pair<MemoryModuleType<?>, MemoryStatus>> $$2, Set<MemoryModuleType<?>> $$3) {
        this.activityRequirements.put($$0, $$2);
        if (!$$3.isEmpty()) {
            this.activityMemoriesToEraseWhenStopped.put($$0, $$3);
        }
        UnmodifiableIterator it = $$1.iterator();
        while (it.hasNext()) {
            Pair<Integer, ? extends BehaviorControl<? super E>> $$4 = (Pair) it.next();
            this.availableBehaviorsByPriority.computeIfAbsent((Integer) $$4.getFirst(), $$02 -> {
                return Maps.newHashMap();
            }).computeIfAbsent($$0, $$03 -> {
                return Sets.newLinkedHashSet();
            }).add((BehaviorControl) $$4.getSecond());
        }
    }

    @VisibleForTesting
    public void removeAllBehaviors() {
        this.availableBehaviorsByPriority.clear();
    }

    public boolean isActive(Activity $$0) {
        return this.activeActivities.contains($$0);
    }

    public Brain<E> copyWithoutBehaviors() {
        Brain<E> $$0 = new Brain<>(this.memories.keySet(), this.sensors.keySet(), ImmutableList.of(), this.codec);
        for (Map.Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> $$1 : this.memories.entrySet()) {
            MemoryModuleType<?> $$2 = $$1.getKey();
            if ($$1.getValue().isPresent()) {
                $$0.memories.put($$2, $$1.getValue());
            }
        }
        return $$0;
    }

    public void tick(ServerLevel $$0, E $$1) {
        forgetOutdatedMemories();
        tickSensors($$0, $$1);
        startEachNonRunningBehavior($$0, $$1);
        tickEachRunningBehavior($$0, $$1);
    }

    private void tickSensors(ServerLevel $$0, E $$1) {
        for (Sensor<? super E> $$2 : this.sensors.values()) {
            $$2.tick($$0, $$1);
        }
    }

    private void forgetOutdatedMemories() {
        for (Map.Entry<MemoryModuleType<?>, Optional<? extends ExpirableValue<?>>> $$0 : this.memories.entrySet()) {
            if ($$0.getValue().isPresent()) {
                ExpirableValue<?> $$1 = $$0.getValue().get();
                if ($$1.hasExpired()) {
                    eraseMemory((MemoryModuleType) $$0.getKey());
                }
                $$1.tick();
            }
        }
    }

    public void stopAll(ServerLevel $$0, E $$1) {
        long $$2 = $$1.level().getGameTime();
        for (BehaviorControl<? super E> $$3 : getRunningBehaviors()) {
            $$3.doStop($$0, $$1, $$2);
        }
    }

    private void startEachNonRunningBehavior(ServerLevel $$0, E $$1) {
        long $$2 = $$0.getGameTime();
        for (Map<Activity, Set<BehaviorControl<? super E>>> $$3 : this.availableBehaviorsByPriority.values()) {
            for (Map.Entry<Activity, Set<BehaviorControl<? super E>>> $$4 : $$3.entrySet()) {
                Activity $$5 = $$4.getKey();
                if (this.activeActivities.contains($$5)) {
                    Set<BehaviorControl<? super E>> $$6 = $$4.getValue();
                    for (BehaviorControl<? super E> $$7 : $$6) {
                        if ($$7.getStatus() == Behavior.Status.STOPPED) {
                            $$7.tryStart($$0, $$1, $$2);
                        }
                    }
                }
            }
        }
    }

    private void tickEachRunningBehavior(ServerLevel $$0, E $$1) {
        long $$2 = $$0.getGameTime();
        for (BehaviorControl<? super E> $$3 : getRunningBehaviors()) {
            $$3.tickOrStop($$0, $$1, $$2);
        }
    }

    private boolean activityRequirementsAreMet(Activity $$0) {
        if (!this.activityRequirements.containsKey($$0)) {
            return false;
        }
        for (Pair<MemoryModuleType<?>, MemoryStatus> $$1 : this.activityRequirements.get($$0)) {
            MemoryModuleType<?> $$2 = (MemoryModuleType) $$1.getFirst();
            MemoryStatus $$3 = (MemoryStatus) $$1.getSecond();
            if (!checkMemory($$2, $$3)) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmptyCollection(Object $$0) {
        return ($$0 instanceof Collection) && ((Collection) $$0).isEmpty();
    }

    ImmutableList<? extends Pair<Integer, ? extends BehaviorControl<? super E>>> createPriorityPairs(int $$0, ImmutableList<? extends BehaviorControl<? super E>> $$1) {
        int $$2 = $$0;
        ImmutableList.Builder<Pair<Integer, ? extends BehaviorControl<? super E>>> $$3 = ImmutableList.builder();
        UnmodifiableIterator it = $$1.iterator();
        while (it.hasNext()) {
            BehaviorControl<? super E> $$4 = (BehaviorControl) it.next();
            int i = $$2;
            $$2++;
            $$3.add(Pair.of(Integer.valueOf(i), $$4));
        }
        return $$3.build();
    }

    public boolean isBrainDead() {
        return this.memories.isEmpty() && this.sensors.isEmpty() && this.availableBehaviorsByPriority.isEmpty();
    }
}
