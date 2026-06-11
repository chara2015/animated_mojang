package net.minecraft.util;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Supplier;
import org.apache.commons.lang3.ObjectUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ModCheck.class */
public final class ModCheck extends Record {
    private final Confidence confidence;
    private final String description;

    public ModCheck(Confidence $$0, String $$1) {
        this.confidence = $$0;
        this.description = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ModCheck.class), ModCheck.class, "confidence;description", "FIELD:Lnet/minecraft/util/ModCheck;->confidence:Lnet/minecraft/util/ModCheck$Confidence;", "FIELD:Lnet/minecraft/util/ModCheck;->description:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ModCheck.class), ModCheck.class, "confidence;description", "FIELD:Lnet/minecraft/util/ModCheck;->confidence:Lnet/minecraft/util/ModCheck$Confidence;", "FIELD:Lnet/minecraft/util/ModCheck;->description:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ModCheck.class, Object.class), ModCheck.class, "confidence;description", "FIELD:Lnet/minecraft/util/ModCheck;->confidence:Lnet/minecraft/util/ModCheck$Confidence;", "FIELD:Lnet/minecraft/util/ModCheck;->description:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Confidence confidence() {
        return this.confidence;
    }

    public String description() {
        return this.description;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ModCheck$Confidence.class */
    public enum Confidence {
        PROBABLY_NOT("Probably not.", false),
        VERY_LIKELY("Very likely;", true),
        DEFINITELY("Definitely;", true);

        final String description;
        final boolean shouldReportAsModified;

        Confidence(String $$0, boolean $$1) {
            this.description = $$0;
            this.shouldReportAsModified = $$1;
        }
    }

    public static ModCheck identify(String $$0, Supplier<String> $$1, String $$2, Class<?> $$3) {
        String $$4 = $$1.get();
        if (!$$0.equals($$4)) {
            return new ModCheck(Confidence.DEFINITELY, $$2 + " brand changed to '" + $$4 + "'");
        }
        if ($$3.getSigners() == null) {
            return new ModCheck(Confidence.VERY_LIKELY, $$2 + " jar signature invalidated");
        }
        return new ModCheck(Confidence.PROBABLY_NOT, $$2 + " jar signature and brand is untouched");
    }

    public boolean shouldReportAsModified() {
        return this.confidence.shouldReportAsModified;
    }

    public ModCheck merge(ModCheck $$0) {
        return new ModCheck((Confidence) ObjectUtils.max(new Confidence[]{this.confidence, $$0.confidence}), this.description + "; " + $$0.description);
    }

    public String fullDescription() {
        return this.confidence.description + " " + this.description;
    }
}
