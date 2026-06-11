package net.minecraft.world.phys.shapes;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/phys/shapes/BooleanOp.class */
public interface BooleanOp {
    public static final BooleanOp FALSE = ($$0, $$1) -> {
        return false;
    };
    public static final BooleanOp NOT_OR = ($$0, $$1) -> {
        return ($$0 || $$1) ? false : true;
    };
    public static final BooleanOp ONLY_SECOND = ($$0, $$1) -> {
        return $$1 && !$$0;
    };
    public static final BooleanOp NOT_FIRST = ($$0, $$1) -> {
        return !$$0;
    };
    public static final BooleanOp ONLY_FIRST = ($$0, $$1) -> {
        return $$0 && !$$1;
    };
    public static final BooleanOp NOT_SECOND = ($$0, $$1) -> {
        return !$$1;
    };
    public static final BooleanOp NOT_SAME = ($$0, $$1) -> {
        return $$0 != $$1;
    };
    public static final BooleanOp NOT_AND = ($$0, $$1) -> {
        return ($$0 && $$1) ? false : true;
    };
    public static final BooleanOp AND = ($$0, $$1) -> {
        return $$0 && $$1;
    };
    public static final BooleanOp SAME = ($$0, $$1) -> {
        return $$0 == $$1;
    };
    public static final BooleanOp SECOND = ($$0, $$1) -> {
        return $$1;
    };
    public static final BooleanOp CAUSES = ($$0, $$1) -> {
        return !$$0 || $$1;
    };
    public static final BooleanOp FIRST = ($$0, $$1) -> {
        return $$0;
    };
    public static final BooleanOp CAUSED_BY = ($$0, $$1) -> {
        return $$0 || !$$1;
    };
    public static final BooleanOp OR = ($$0, $$1) -> {
        return $$0 || $$1;
    };
    public static final BooleanOp TRUE = ($$0, $$1) -> {
        return true;
    };

    boolean apply(boolean z, boolean z2);
}
