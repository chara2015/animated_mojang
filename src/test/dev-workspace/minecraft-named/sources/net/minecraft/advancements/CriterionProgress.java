package net.minecraft.advancements;

import java.time.Instant;
import net.minecraft.nbt.SnbtOperations;
import net.minecraft.network.FriendlyByteBuf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/CriterionProgress.class */
public class CriterionProgress {
    private Instant obtained;

    public CriterionProgress() {
    }

    public CriterionProgress(Instant $$0) {
        this.obtained = $$0;
    }

    public boolean isDone() {
        return this.obtained != null;
    }

    public void grant() {
        this.obtained = Instant.now();
    }

    public void revoke() {
        this.obtained = null;
    }

    public Instant getObtained() {
        return this.obtained;
    }

    public String toString() {
        return "CriterionProgress{obtained=" + String.valueOf(this.obtained == null ? SnbtOperations.BUILTIN_FALSE : this.obtained) + "}";
    }

    public void serializeToNetwork(FriendlyByteBuf $$0) {
        $$0.writeNullable(this.obtained, (v0, v1) -> {
            v0.writeInstant(v1);
        });
    }

    public static CriterionProgress fromNetwork(FriendlyByteBuf $$0) {
        CriterionProgress $$1 = new CriterionProgress();
        $$1.obtained = (Instant) $$0.readNullable((v0) -> {
            return v0.readInstant();
        });
        return $$1;
    }
}
