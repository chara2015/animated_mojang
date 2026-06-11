package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.util.context.ContextKey;
import net.minecraft.util.context.ContextKeySet;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/ValidationContext.class */
public class ValidationContext {
    private final ProblemReporter reporter;
    private final ContextKeySet contextKeySet;
    private final Optional<HolderGetter.Provider> resolver;
    private final Set<ResourceKey<?>> visitedElements;

    public ValidationContext(ProblemReporter $$0, ContextKeySet $$1, HolderGetter.Provider $$2) {
        this($$0, $$1, Optional.of($$2), Set.of());
    }

    public ValidationContext(ProblemReporter $$0, ContextKeySet $$1) {
        this($$0, $$1, Optional.empty(), Set.of());
    }

    private ValidationContext(ProblemReporter $$0, ContextKeySet $$1, Optional<HolderGetter.Provider> $$2, Set<ResourceKey<?>> $$3) {
        this.reporter = $$0;
        this.contextKeySet = $$1;
        this.resolver = $$2;
        this.visitedElements = $$3;
    }

    public ValidationContext forChild(ProblemReporter.PathElement $$0) {
        return new ValidationContext(this.reporter.forChild($$0), this.contextKeySet, this.resolver, this.visitedElements);
    }

    public ValidationContext enterElement(ProblemReporter.PathElement $$0, ResourceKey<?> $$1) {
        return new ValidationContext(this.reporter.forChild($$0), this.contextKeySet, this.resolver, ImmutableSet.builder().addAll(this.visitedElements).add($$1).build());
    }

    public boolean hasVisitedElement(ResourceKey<?> $$0) {
        return this.visitedElements.contains($$0);
    }

    public void reportProblem(ProblemReporter.Problem $$0) {
        this.reporter.report($$0);
    }

    public void validateContextUsage(LootContextUser $$0) {
        Set<ContextKey<?>> $$1 = $$0.getReferencedContextParams();
        Sets.SetView setViewDifference = Sets.difference($$1, this.contextKeySet.allowed());
        if (!setViewDifference.isEmpty()) {
            this.reporter.report(new ParametersNotProvidedProblem(setViewDifference));
        }
    }

    public HolderGetter.Provider resolver() {
        return this.resolver.orElseThrow(() -> {
            return new UnsupportedOperationException("References not allowed");
        });
    }

    public boolean allowsReferences() {
        return this.resolver.isPresent();
    }

    public ValidationContext setContextKeySet(ContextKeySet $$0) {
        return new ValidationContext(this.reporter, $$0, this.resolver, this.visitedElements);
    }

    public ProblemReporter reporter() {
        return this.reporter;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/ValidationContext$ParametersNotProvidedProblem.class */
    public static final class ParametersNotProvidedProblem extends Record implements ProblemReporter.Problem {
        private final Set<ContextKey<?>> notProvided;

        public ParametersNotProvidedProblem(Set<ContextKey<?>> $$0) {
            this.notProvided = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ParametersNotProvidedProblem.class), ParametersNotProvidedProblem.class, "notProvided", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$ParametersNotProvidedProblem;->notProvided:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ParametersNotProvidedProblem.class), ParametersNotProvidedProblem.class, "notProvided", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$ParametersNotProvidedProblem;->notProvided:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ParametersNotProvidedProblem.class, Object.class), ParametersNotProvidedProblem.class, "notProvided", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$ParametersNotProvidedProblem;->notProvided:Ljava/util/Set;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Set<ContextKey<?>> notProvided() {
            return this.notProvided;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Parameters " + String.valueOf(this.notProvided) + " are not provided in this context";
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/ValidationContext$ReferenceNotAllowedProblem.class */
    public static final class ReferenceNotAllowedProblem extends Record implements ProblemReporter.Problem {
        private final ResourceKey<?> referenced;

        public ReferenceNotAllowedProblem(ResourceKey<?> $$0) {
            this.referenced = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ReferenceNotAllowedProblem.class), ReferenceNotAllowedProblem.class, "referenced", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$ReferenceNotAllowedProblem;->referenced:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ReferenceNotAllowedProblem.class), ReferenceNotAllowedProblem.class, "referenced", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$ReferenceNotAllowedProblem;->referenced:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ReferenceNotAllowedProblem.class, Object.class), ReferenceNotAllowedProblem.class, "referenced", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$ReferenceNotAllowedProblem;->referenced:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ResourceKey<?> referenced() {
            return this.referenced;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Reference to " + String.valueOf(this.referenced.identifier()) + " of type " + String.valueOf(this.referenced.registry()) + " was used, but references are not allowed";
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/ValidationContext$RecursiveReferenceProblem.class */
    public static final class RecursiveReferenceProblem extends Record implements ProblemReporter.Problem {
        private final ResourceKey<?> referenced;

        public RecursiveReferenceProblem(ResourceKey<?> $$0) {
            this.referenced = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RecursiveReferenceProblem.class), RecursiveReferenceProblem.class, "referenced", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$RecursiveReferenceProblem;->referenced:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RecursiveReferenceProblem.class), RecursiveReferenceProblem.class, "referenced", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$RecursiveReferenceProblem;->referenced:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RecursiveReferenceProblem.class, Object.class), RecursiveReferenceProblem.class, "referenced", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$RecursiveReferenceProblem;->referenced:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ResourceKey<?> referenced() {
            return this.referenced;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return String.valueOf(this.referenced.identifier()) + " of type " + String.valueOf(this.referenced.registry()) + " is recursively called";
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/ValidationContext$MissingReferenceProblem.class */
    public static final class MissingReferenceProblem extends Record implements ProblemReporter.Problem {
        private final ResourceKey<?> referenced;

        public MissingReferenceProblem(ResourceKey<?> $$0) {
            this.referenced = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MissingReferenceProblem.class), MissingReferenceProblem.class, "referenced", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$MissingReferenceProblem;->referenced:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MissingReferenceProblem.class), MissingReferenceProblem.class, "referenced", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$MissingReferenceProblem;->referenced:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MissingReferenceProblem.class, Object.class), MissingReferenceProblem.class, "referenced", "FIELD:Lnet/minecraft/world/level/storage/loot/ValidationContext$MissingReferenceProblem;->referenced:Lnet/minecraft/resources/ResourceKey;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ResourceKey<?> referenced() {
            return this.referenced;
        }

        @Override // net.minecraft.util.ProblemReporter.Problem
        public String description() {
            return "Missing element " + String.valueOf(this.referenced.identifier()) + " of type " + String.valueOf(this.referenced.registry());
        }
    }
}
