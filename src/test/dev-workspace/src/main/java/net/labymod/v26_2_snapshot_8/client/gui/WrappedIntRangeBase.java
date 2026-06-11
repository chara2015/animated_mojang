package net.labymod.v26_2_snapshot_8.client.gui;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.client.OptionInstance;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/gui/WrappedIntRangeBase.class */
public class WrappedIntRangeBase implements OptionInstance.IntRangeBase {
    private final OptionInstance.IntRangeBase delegate;

    public WrappedIntRangeBase(OptionInstance.IntRangeBase delegate) {
        this.delegate = delegate;
    }

    public int minInclusive() {
        return this.delegate.minInclusive();
    }

    public int maxInclusive() {
        return this.delegate.maxInclusive();
    }

    public Optional<Integer> validateValue(Integer value) {
        return this.delegate.validateValue(value);
    }

    public Codec<Integer> codec() {
        return this.delegate.codec();
    }
}
