package net.labymod.v1_21_3.client.gui;

import com.mojang.serialization.Codec;
import java.util.Optional;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/gui/WrappedIntRangeBase.class */
public class WrappedIntRangeBase implements g {
    private final g delegate;

    public WrappedIntRangeBase(g delegate) {
        this.delegate = delegate;
    }

    public int d() {
        return this.delegate.d();
    }

    public int b() {
        return this.delegate.b();
    }

    /* JADX INFO: renamed from: validateValue, reason: merged with bridge method [inline-methods] */
    public Optional<Integer> a(Integer value) {
        return this.delegate.a(value);
    }

    public Codec<Integer> f() {
        return this.delegate.f();
    }
}
