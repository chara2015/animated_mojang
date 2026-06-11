package net.minecraft.util.profiling.metrics;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/profiling/metrics/MetricCategory.class */
public enum MetricCategory {
    PATH_FINDING("pathfinding"),
    EVENT_LOOPS("event-loops"),
    CONSECUTIVE_EXECUTORS("consecutive-executors"),
    TICK_LOOP("ticking"),
    JVM("jvm"),
    CHUNK_RENDERING("chunk rendering"),
    CHUNK_RENDERING_DISPATCHING("chunk rendering dispatching"),
    CPU("cpu"),
    GPU("gpu");

    private final String description;

    MetricCategory(String $$0) {
        this.description = $$0;
    }

    public String getDescription() {
        return this.description;
    }
}
