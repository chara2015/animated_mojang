package net.minecraft.world.level.gameevent.vibrations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gameevent/vibrations/VibrationSelector.class */
public class VibrationSelector {
    public static final Codec<VibrationSelector> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(VibrationInfo.CODEC.lenientOptionalFieldOf("event").forGetter($$0 -> {
            return $$0.currentVibrationData.map((v0) -> {
                return v0.getLeft();
            });
        }), Codec.LONG.fieldOf("tick").forGetter($$02 -> {
            return (Long) $$02.currentVibrationData.map((v0) -> {
                return v0.getRight();
            }).orElse(-1L);
        })).apply($$0, (v1, v2) -> {
            return new VibrationSelector(v1, v2);
        });
    });
    private Optional<Pair<VibrationInfo, Long>> currentVibrationData;

    public VibrationSelector(Optional<VibrationInfo> $$0, long $$1) {
        this.currentVibrationData = $$0.map($$12 -> {
            return Pair.of($$12, Long.valueOf($$1));
        });
    }

    public VibrationSelector() {
        this.currentVibrationData = Optional.empty();
    }

    public void addCandidate(VibrationInfo $$0, long $$1) {
        if (shouldReplaceVibration($$0, $$1)) {
            this.currentVibrationData = Optional.of(Pair.of($$0, Long.valueOf($$1)));
        }
    }

    private boolean shouldReplaceVibration(VibrationInfo $$0, long $$1) {
        if (this.currentVibrationData.isEmpty()) {
            return true;
        }
        Pair<VibrationInfo, Long> $$2 = this.currentVibrationData.get();
        long $$3 = ((Long) $$2.getRight()).longValue();
        if ($$1 != $$3) {
            return false;
        }
        VibrationInfo $$4 = (VibrationInfo) $$2.getLeft();
        if ($$0.distance() < $$4.distance()) {
            return true;
        }
        return $$0.distance() <= $$4.distance() && VibrationSystem.getGameEventFrequency($$0.gameEvent()) > VibrationSystem.getGameEventFrequency($$4.gameEvent());
    }

    public Optional<VibrationInfo> chosenCandidate(long $$0) {
        if (this.currentVibrationData.isEmpty()) {
            return Optional.empty();
        }
        if (((Long) this.currentVibrationData.get().getRight()).longValue() < $$0) {
            return Optional.of((VibrationInfo) this.currentVibrationData.get().getLeft());
        }
        return Optional.empty();
    }

    public void startOver() {
        this.currentVibrationData = Optional.empty();
    }
}
