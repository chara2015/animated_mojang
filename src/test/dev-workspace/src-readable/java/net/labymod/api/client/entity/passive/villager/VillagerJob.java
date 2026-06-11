package net.labymod.api.client.entity.passive.villager;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/passive/villager/VillagerJob.class */
public class VillagerJob {
    private static final String TRANSLATION_KEY = "labymod.hudWidget.waila.tooltip.merchant.level.";
    private final String name;
    private final int level;
    public static final VillagerJob NITWIT = new VillagerJob("nitwit", 1);
    private static final Map<String, VillagerJob> PROFESSIONS = new HashMap();

    private VillagerJob(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public static VillagerJob getOrCreate(String name, int level) {
        return PROFESSIONS.computeIfAbsent(name + ";" + level, profession -> {
            return new VillagerJob(name, level);
        });
    }

    @NotNull
    public String name() {
        return this.name;
    }

    public int level() {
        return this.level;
    }

    public String levelDescriptionId() {
        return "labymod.hudWidget.waila.tooltip.merchant.level." + level();
    }
}
