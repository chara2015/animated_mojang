package net.minecraft.client.gui.navigation;

import it.unimi.dsi.fastutil.ints.IntComparator;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/navigation/ScreenDirection.class */
public enum ScreenDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    private final IntComparator coordinateValueComparator = ($$0, $$1) -> {
        if ($$0 == $$1) {
            return 0;
        }
        return isBefore($$0, $$1) ? -1 : 1;
    };

    ScreenDirection() {
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ScreenAxis getAxis() throws MatchException {
        switch (this) {
            case UP:
            case DOWN:
                return ScreenAxis.VERTICAL;
            case LEFT:
            case RIGHT:
                return ScreenAxis.HORIZONTAL;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ScreenDirection getOpposite() throws MatchException {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public boolean isPositive() throws MatchException {
        switch (this) {
            case UP:
            case LEFT:
                return false;
            case DOWN:
            case RIGHT:
                return true;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public boolean isAfter(int $$0, int $$1) {
        return isPositive() ? $$0 > $$1 : $$1 > $$0;
    }

    public boolean isBefore(int $$0, int $$1) {
        return isPositive() ? $$0 < $$1 : $$1 < $$0;
    }

    public IntComparator coordinateValueComparator() {
        return this.coordinateValueComparator;
    }
}
