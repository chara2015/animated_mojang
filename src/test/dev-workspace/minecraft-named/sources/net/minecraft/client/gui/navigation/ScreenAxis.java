package net.minecraft.client.gui.navigation;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/navigation/ScreenAxis.class */
public enum ScreenAxis {
    HORIZONTAL,
    VERTICAL;

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ScreenAxis orthogonal() throws MatchException {
        switch (this) {
            case HORIZONTAL:
                return VERTICAL;
            case VERTICAL:
                return HORIZONTAL;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ScreenDirection getPositive() throws MatchException {
        switch (this) {
            case HORIZONTAL:
                return ScreenDirection.RIGHT;
            case VERTICAL:
                return ScreenDirection.DOWN;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public ScreenDirection getNegative() throws MatchException {
        switch (this) {
            case HORIZONTAL:
                return ScreenDirection.LEFT;
            case VERTICAL:
                return ScreenDirection.UP;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public ScreenDirection getDirection(boolean $$0) {
        return $$0 ? getPositive() : getNegative();
    }
}
