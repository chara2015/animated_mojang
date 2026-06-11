package net.labymod.api.util;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Pair.class */
public class Pair<F, S> {

    @Nullable
    private F first;

    @Nullable
    private S second;

    private Pair(@Nullable F first, @Nullable S second) {
        this.first = first;
        this.second = second;
    }

    public static <T, U> Pair<T, U> of(@Nullable T first, @Nullable U second) {
        return new Pair<>(first, second);
    }

    @Nullable
    public F getFirst() {
        return this.first;
    }

    public void setFirst(@Nullable F first) {
        this.first = first;
    }

    @Nullable
    public S getSecond() {
        return this.second;
    }

    public void setSecond(@Nullable S second) {
        this.second = second;
    }

    public void set(@Nullable F first, @Nullable S second) {
        this.first = first;
        this.second = second;
    }

    public Pair<F, S> copy() {
        return new Pair<>(this.first, this.second);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Pair<?, ?> pair = (Pair) object;
        return Objects.equals(this.first, pair.first) && Objects.equals(this.second, pair.second);
    }

    public int hashCode() {
        int result = this.first != null ? this.first.hashCode() : 0;
        return (31 * result) + (this.second != null ? this.second.hashCode() : 0);
    }

    public String toString() {
        return "Pair{first=" + String.valueOf(this.first) + ", second=" + String.valueOf(this.second) + "}";
    }
}
