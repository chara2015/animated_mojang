package net.labymod.api.util;

import java.util.Objects;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/IntIntTriple.class */
public class IntIntTriple<R> {
    private int left;
    private int middle;
    private R right;

    public IntIntTriple(int left, int middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getMiddle() {
        return this.middle;
    }

    public void setMiddle(int middle) {
        this.middle = middle;
    }

    public R getRight() {
        return this.right;
    }

    public void setRight(R right) {
        this.right = right;
    }

    public void set(int left, int middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public IntIntTriple<R> copy() {
        return new IntIntTriple<>(this.left, this.middle, this.right);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntIntTriple<?> that = (IntIntTriple) o;
        if (this.left != that.left || this.middle != that.middle) {
            return false;
        }
        return Objects.equals(this.right, that.right);
    }

    public int hashCode() {
        int result = this.left;
        return (31 * ((31 * result) + this.middle)) + (this.right != null ? this.right.hashCode() : 0);
    }

    public String toString() {
        return "IntIntTriple{left=" + this.left + ", middle=" + this.middle + ", right=" + String.valueOf(this.right) + "}";
    }
}
