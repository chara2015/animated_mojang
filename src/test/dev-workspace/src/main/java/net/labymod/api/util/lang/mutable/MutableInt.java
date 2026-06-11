package net.labymod.api.util.lang.mutable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/lang/mutable/MutableInt.class */
public class MutableInt {
    private int value;

    public MutableInt(int value) {
        this.value = value;
    }

    public MutableInt(Number value) {
        this(value.intValue());
    }

    public MutableInt(String value) {
        this(Integer.parseInt(value));
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void increment() {
        this.value++;
    }

    public int getAndIncrement() {
        int last = this.value;
        this.value++;
        return last;
    }

    public int incrementAndGet() {
        this.value++;
        return this.value;
    }

    public void decrement() {
        this.value--;
    }

    public int getAndDecrement() {
        int last = this.value;
        this.value--;
        return last;
    }

    public int decrementAndGet() {
        this.value--;
        return this.value;
    }

    public void add(int operand) {
        this.value += operand;
    }

    public void subtract(int operand) {
        this.value -= operand;
    }
}
