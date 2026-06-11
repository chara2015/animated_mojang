package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/AdvancementProgress.class */
public class AdvancementProgress implements Comparable<AdvancementProgress> {
    private static final DateTimeFormatter OBTAINED_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT);
    private static final Codec<Instant> OBTAINED_TIME_CODEC = ExtraCodecs.temporalCodec(OBTAINED_TIME_FORMAT).xmap(Instant::from, $$0 -> {
        return $$0.atZone(ZoneId.systemDefault());
    });
    private static final Codec<Map<String, CriterionProgress>> CRITERIA_CODEC = Codec.unboundedMap(Codec.STRING, OBTAINED_TIME_CODEC).xmap($$0 -> {
        return Util.mapValues($$0, CriterionProgress::new);
    }, $$02 -> {
        return (Map) $$02.entrySet().stream().filter($$02 -> {
            return ((CriterionProgress) $$02.getValue()).isDone();
        }).collect(Collectors.toMap((v0) -> {
            return v0.getKey();
        }, $$03 -> {
            return (Instant) Objects.requireNonNull(((CriterionProgress) $$03.getValue()).getObtained());
        }));
    });
    public static final Codec<AdvancementProgress> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(CRITERIA_CODEC.optionalFieldOf("criteria", Map.of()).forGetter($$0 -> {
            return $$0.criteria;
        }), Codec.BOOL.fieldOf("done").orElse(true).forGetter((v0) -> {
            return v0.isDone();
        })).apply($$0, ($$02, $$1) -> {
            return new AdvancementProgress(new HashMap($$02));
        });
    });
    private final Map<String, CriterionProgress> criteria;
    private AdvancementRequirements requirements;

    private AdvancementProgress(Map<String, CriterionProgress> $$0) {
        this.requirements = AdvancementRequirements.EMPTY;
        this.criteria = $$0;
    }

    public AdvancementProgress() {
        this.requirements = AdvancementRequirements.EMPTY;
        this.criteria = Maps.newHashMap();
    }

    public void update(AdvancementRequirements $$0) {
        Set<String> $$1 = $$0.names();
        this.criteria.entrySet().removeIf($$12 -> {
            return !$$1.contains($$12.getKey());
        });
        for (String $$2 : $$1) {
            this.criteria.putIfAbsent($$2, new CriterionProgress());
        }
        this.requirements = $$0;
    }

    public boolean isDone() {
        return this.requirements.test(this::isCriterionDone);
    }

    public boolean hasProgress() {
        for (CriterionProgress $$0 : this.criteria.values()) {
            if ($$0.isDone()) {
                return true;
            }
        }
        return false;
    }

    public boolean grantProgress(String $$0) {
        CriterionProgress $$1 = this.criteria.get($$0);
        if ($$1 != null && !$$1.isDone()) {
            $$1.grant();
            return true;
        }
        return false;
    }

    public boolean revokeProgress(String $$0) {
        CriterionProgress $$1 = this.criteria.get($$0);
        if ($$1 != null && $$1.isDone()) {
            $$1.revoke();
            return true;
        }
        return false;
    }

    public String toString() {
        return "AdvancementProgress{criteria=" + String.valueOf(this.criteria) + ", requirements=" + String.valueOf(this.requirements) + "}";
    }

    public void serializeToNetwork(FriendlyByteBuf $$0) {
        $$0.writeMap(this.criteria, (v0, v1) -> {
            v0.writeUtf(v1);
        }, ($$02, $$1) -> {
            $$1.serializeToNetwork($$02);
        });
    }

    public static AdvancementProgress fromNetwork(FriendlyByteBuf $$0) {
        Map<String, CriterionProgress> $$1 = $$0.readMap((v0) -> {
            return v0.readUtf();
        }, CriterionProgress::fromNetwork);
        return new AdvancementProgress($$1);
    }

    public CriterionProgress getCriterion(String $$0) {
        return this.criteria.get($$0);
    }

    private boolean isCriterionDone(String $$0) {
        CriterionProgress $$1 = getCriterion($$0);
        return $$1 != null && $$1.isDone();
    }

    public float getPercent() {
        if (this.criteria.isEmpty()) {
            return 0.0f;
        }
        float $$0 = this.requirements.size();
        float $$1 = countCompletedRequirements();
        return $$1 / $$0;
    }

    public Component getProgressText() {
        int $$0;
        if (this.criteria.isEmpty() || ($$0 = this.requirements.size()) <= 1) {
            return null;
        }
        int $$1 = countCompletedRequirements();
        return Component.translatable("advancements.progress", Integer.valueOf($$1), Integer.valueOf($$0));
    }

    private int countCompletedRequirements() {
        return this.requirements.count(this::isCriterionDone);
    }

    public Iterable<String> getRemainingCriteria() {
        List<String> $$0 = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> $$1 : this.criteria.entrySet()) {
            if (!$$1.getValue().isDone()) {
                $$0.add($$1.getKey());
            }
        }
        return $$0;
    }

    public Iterable<String> getCompletedCriteria() {
        List<String> $$0 = Lists.newArrayList();
        for (Map.Entry<String, CriterionProgress> $$1 : this.criteria.entrySet()) {
            if ($$1.getValue().isDone()) {
                $$0.add($$1.getKey());
            }
        }
        return $$0;
    }

    public Instant getFirstProgressDate() {
        return (Instant) this.criteria.values().stream().map((v0) -> {
            return v0.getObtained();
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).min(Comparator.naturalOrder()).orElse(null);
    }

    @Override // java.lang.Comparable
    public int compareTo(AdvancementProgress $$0) {
        Instant $$1 = getFirstProgressDate();
        Instant $$2 = $$0.getFirstProgressDate();
        if ($$1 == null && $$2 != null) {
            return 1;
        }
        if ($$1 != null && $$2 == null) {
            return -1;
        }
        if ($$1 == null && $$2 == null) {
            return 0;
        }
        return $$1.compareTo($$2);
    }
}
