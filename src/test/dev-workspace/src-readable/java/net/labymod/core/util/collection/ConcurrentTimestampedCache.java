package net.labymod.core.util.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/util/collection/ConcurrentTimestampedCache.class */
public class ConcurrentTimestampedCache<K, V> extends ConcurrentHashMap<K, TimestampedValue<V>> {
    private static final long NULL = 0;
    private static final long DEFAULT_MAX_LIFE_DURATION = 30000;
    private static final long DEFAULT_CHECK_DURATION = 5000;
    private final long maxLifeDuration;
    private final long checkDuration;
    private final AtomicLong lastCheckTimestamp;

    @Nullable
    private Consumer<V> removeConsumer;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.ConcurrentHashMap, java.util.Map, java.util.concurrent.ConcurrentMap
    public /* bridge */ /* synthetic */ Object computeIfAbsent(Object obj, Function function) {
        return computeIfAbsent(obj, (Function<? super Object, ? extends TimestampedValue<V>>) function);
    }

    public ConcurrentTimestampedCache(int initialCapacity) {
        this(initialCapacity, NULL, NULL);
    }

    public ConcurrentTimestampedCache(int initialCapacity, long maxLifeDuration) {
        this(initialCapacity, maxLifeDuration, NULL);
    }

    public ConcurrentTimestampedCache(int initialCapacity, long maxLifeDuration, long checkDuration) {
        super(initialCapacity);
        this.lastCheckTimestamp = new AtomicLong(NULL);
        this.removeConsumer = null;
        this.maxLifeDuration = maxLifeDuration == NULL ? 30000L : maxLifeDuration;
        this.checkDuration = checkDuration == NULL ? DEFAULT_CHECK_DURATION : checkDuration;
    }

    public TimestampedValue<V> putTimestamped(K key, V value) {
        return put((Object) key, (TimestampedValue) new TimestampedValue<>(value));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.ConcurrentHashMap, java.util.Map, java.util.concurrent.ConcurrentMap
    public TimestampedValue<V> computeIfAbsent(K key, Function<? super K, ? extends TimestampedValue<V>> function) {
        return (TimestampedValue) super.computeIfAbsent((Object) key, (Function) function);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
    public TimestampedValue<V> put(K key, TimestampedValue<V> timestampedValue) {
        checkEntries();
        return (TimestampedValue) super.put(key, timestampedValue);
    }

    @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
    public TimestampedValue<V> remove(Object key) {
        TimestampedValue<V> value = (TimestampedValue) super.remove(key);
        consumeRemove(value);
        return value;
    }

    @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
    public TimestampedValue<V> get(Object key) {
        checkEntries();
        return (TimestampedValue) super.get(key);
    }

    public V getValue(Object key) {
        TimestampedValue<V> timestampedValue = get(key);
        if (timestampedValue == null) {
            return null;
        }
        return timestampedValue.getValue();
    }

    @Override // java.util.concurrent.ConcurrentHashMap, java.util.AbstractMap, java.util.Map
    public int size() {
        checkEntries();
        return super.size();
    }

    public void forEachPlain(BiConsumer<? super K, ? super V> biConsumer) {
        for (Map.Entry<K, V> entry : entrySet()) {
            Object key = entry.getKey();
            TimestampedValue timestampedValue = (TimestampedValue) entry.getValue();
            biConsumer.accept(key, timestampedValue == null ? null : (V) timestampedValue.getValue());
        }
    }

    public void onEntryRemove(Consumer<V> removeConsumer) {
        this.removeConsumer = removeConsumer;
    }

    private void checkEntries() {
        long currentTimeMillis = TimeUtil.getCurrentTimeMillis();
        long j = this.lastCheckTimestamp.get();
        if (j + this.checkDuration > currentTimeMillis || !this.lastCheckTimestamp.compareAndSet(j, currentTimeMillis)) {
            return;
        }
        ArrayList arrayList = null;
        for (Map.Entry<K, V> entry : entrySet()) {
            if (((TimestampedValue) entry.getValue()).getTimestamp() + this.maxLifeDuration <= currentTimeMillis) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(entry.getKey());
            }
        }
        if (arrayList == null) {
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
    }

    private void consumeRemove(TimestampedValue<V> value) {
        if (value == null || this.removeConsumer == null) {
            return;
        }
        this.removeConsumer.accept(value.getValue());
    }
}
