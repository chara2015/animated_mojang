package net.labymod.api.util;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.concurrent.task.Task;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Debounce.class */
public class Debounce {
    private static final Map<String, Task> TASKS = new HashMap();

    public static void of(ResourceLocation id, long milliseconds, Runnable runnable) {
        of(id.toString(), milliseconds, runnable);
    }

    public static void of(String id, long milliseconds, Runnable runnable) {
        Task task = TASKS.get(id);
        if (task != null) {
            task.cancel();
        }
        Task task2 = Task.builder(() -> {
            runnable.run();
            TASKS.remove(id);
        }).delay(milliseconds, java.util.concurrent.TimeUnit.MILLISECONDS).build();
        task2.execute();
        TASKS.put(id, task2);
    }
}
