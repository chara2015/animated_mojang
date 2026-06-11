package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Locale;
import java.util.Set;
import net.minecraft.core.Direction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/chunk/VisibilitySet.class */
public class VisibilitySet {
    private static final int FACINGS = Direction.values().length;
    private final BitSet data = new BitSet(FACINGS * FACINGS);

    public void add(Set<Direction> $$0) {
        for (Direction $$1 : $$0) {
            for (Direction $$2 : $$0) {
                set($$1, $$2, true);
            }
        }
    }

    public void set(Direction $$0, Direction $$1, boolean $$2) {
        this.data.set($$0.ordinal() + ($$1.ordinal() * FACINGS), $$2);
        this.data.set($$1.ordinal() + ($$0.ordinal() * FACINGS), $$2);
    }

    public void setAll(boolean $$0) {
        this.data.set(0, this.data.size(), $$0);
    }

    public boolean visibilityBetween(Direction $$0, Direction $$1) {
        return this.data.get($$0.ordinal() + ($$1.ordinal() * FACINGS));
    }

    public String toString() {
        StringBuilder $$0 = new StringBuilder();
        $$0.append(' ');
        for (Direction $$1 : Direction.values()) {
            $$0.append(' ').append($$1.toString().toUpperCase(Locale.ROOT).charAt(0));
        }
        $$0.append('\n');
        for (Direction $$2 : Direction.values()) {
            $$0.append($$2.toString().toUpperCase(Locale.ROOT).charAt(0));
            for (Direction $$3 : Direction.values()) {
                if ($$2 == $$3) {
                    $$0.append("  ");
                } else {
                    boolean $$4 = visibilityBetween($$2, $$3);
                    $$0.append(' ').append($$4 ? 'Y' : 'n');
                }
            }
            $$0.append('\n');
        }
        return $$0.toString();
    }
}
