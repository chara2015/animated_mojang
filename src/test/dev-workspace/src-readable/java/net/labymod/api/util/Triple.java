package net.labymod.api.util;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/Triple.class */
public final class Triple<L, M, R> {

    @Nullable
    private L left;

    @Nullable
    private M middle;

    @Nullable
    private R right;

    public Triple(@Nullable L left, @Nullable M middle, @Nullable R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public static <F, S, T> Triple<F, S, T> of(@Nullable F first, @Nullable S second, @Nullable T third) {
        return new Triple<>(first, second, third);
    }

    @Nullable
    public L getLeft() {
        return this.left;
    }

    public void setLeft(@Nullable L left) {
        this.left = left;
    }

    @Nullable
    public M getMiddle() {
        return this.middle;
    }

    public void setMiddle(@Nullable M middle) {
        this.middle = middle;
    }

    @Nullable
    public R getRight() {
        return this.right;
    }

    public void setRight(@Nullable R right) {
        this.right = right;
    }

    public void set(@Nullable L left, @Nullable M middle, @Nullable R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public Triple<L, M, R> copy() {
        return new Triple<>(this.left, this.middle, this.right);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Triple<?, ?, ?> triple = (Triple) o;
        return Objects.equals(this.left, triple.left) && Objects.equals(this.middle, triple.middle) && Objects.equals(this.right, triple.right);
    }

    public int hashCode() {
        int result = this.left != null ? this.left.hashCode() : 0;
        return (31 * ((31 * result) + (this.middle != null ? this.middle.hashCode() : 0))) + (this.right != null ? this.right.hashCode() : 0);
    }

    public String toString() {
        return "Triple{left=" + String.valueOf(this.left) + ", middle=" + String.valueOf(this.middle) + ", right=" + String.valueOf(this.right) + "}";
    }
}
