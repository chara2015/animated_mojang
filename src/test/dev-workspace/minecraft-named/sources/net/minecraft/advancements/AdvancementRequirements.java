package net.minecraft.advancements;

import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.network.FriendlyByteBuf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/AdvancementRequirements.class */
public final class AdvancementRequirements extends Record {
    private final List<List<String>> requirements;
    public static final Codec<AdvancementRequirements> CODEC = Codec.STRING.listOf().listOf().xmap(AdvancementRequirements::new, (v0) -> {
        return v0.requirements();
    });
    public static final AdvancementRequirements EMPTY = new AdvancementRequirements((List<List<String>>) List.of());

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/AdvancementRequirements$Strategy.class */
    public interface Strategy {
        public static final Strategy AND = AdvancementRequirements::allOf;
        public static final Strategy OR = AdvancementRequirements::anyOf;

        AdvancementRequirements create(Collection<String> collection);
    }

    public AdvancementRequirements(List<List<String>> $$0) {
        this.requirements = $$0;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AdvancementRequirements.class), AdvancementRequirements.class, "requirements", "FIELD:Lnet/minecraft/advancements/AdvancementRequirements;->requirements:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AdvancementRequirements.class, Object.class), AdvancementRequirements.class, "requirements", "FIELD:Lnet/minecraft/advancements/AdvancementRequirements;->requirements:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<List<String>> requirements() {
        return this.requirements;
    }

    public AdvancementRequirements(FriendlyByteBuf $$0) {
        this((List<List<String>>) $$0.readList($$02 -> {
            return $$02.readList((v0) -> {
                return v0.readUtf();
            });
        }));
    }

    public void write(FriendlyByteBuf $$0) {
        $$0.writeCollection(this.requirements, ($$02, $$1) -> {
            $$02.writeCollection($$1, (v0, v1) -> {
                v0.writeUtf(v1);
            });
        });
    }

    public static AdvancementRequirements allOf(Collection<String> $$0) {
        return new AdvancementRequirements((List<List<String>>) $$0.stream().map((v0) -> {
            return List.of(v0);
        }).toList());
    }

    public static AdvancementRequirements anyOf(Collection<String> $$0) {
        return new AdvancementRequirements((List<List<String>>) List.of(List.copyOf($$0)));
    }

    public int size() {
        return this.requirements.size();
    }

    public boolean test(Predicate<String> $$0) {
        if (this.requirements.isEmpty()) {
            return false;
        }
        for (List<String> $$1 : this.requirements) {
            if (!anyMatch($$1, $$0)) {
                return false;
            }
        }
        return true;
    }

    public int count(Predicate<String> $$0) {
        int $$1 = 0;
        for (List<String> $$2 : this.requirements) {
            if (anyMatch($$2, $$0)) {
                $$1++;
            }
        }
        return $$1;
    }

    private static boolean anyMatch(List<String> $$0, Predicate<String> $$1) {
        for (String $$2 : $$0) {
            if ($$1.test($$2)) {
                return true;
            }
        }
        return false;
    }

    public DataResult<AdvancementRequirements> validate(Set<String> $$0) {
        ObjectOpenHashSet objectOpenHashSet = new ObjectOpenHashSet();
        for (List<String> $$2 : this.requirements) {
            if ($$2.isEmpty() && $$0.isEmpty()) {
                return DataResult.error(() -> {
                    return "Requirement entry cannot be empty";
                });
            }
            objectOpenHashSet.addAll($$2);
        }
        if (!$$0.equals(objectOpenHashSet)) {
            Sets.SetView setViewDifference = Sets.difference($$0, objectOpenHashSet);
            Sets.SetView setViewDifference2 = Sets.difference(objectOpenHashSet, $$0);
            return DataResult.error(() -> {
                return "Advancement completion requirements did not exactly match specified criteria. Missing: " + String.valueOf(setViewDifference) + ". Unknown: " + String.valueOf(setViewDifference2);
            });
        }
        return DataResult.success(this);
    }

    public boolean isEmpty() {
        return this.requirements.isEmpty();
    }

    @Override // java.lang.Record
    public String toString() {
        return this.requirements.toString();
    }

    public Set<String> names() {
        ObjectOpenHashSet objectOpenHashSet = new ObjectOpenHashSet();
        for (List<String> $$1 : this.requirements) {
            objectOpenHashSet.addAll($$1);
        }
        return objectOpenHashSet;
    }
}
