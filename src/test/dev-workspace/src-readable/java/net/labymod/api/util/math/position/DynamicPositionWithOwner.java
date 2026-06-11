package net.labymod.api.util.math.position;

import java.util.function.Supplier;
import net.labymod.api.util.function.DoubleAssigner;
import net.labymod.api.util.function.DoubleExtractor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/math/position/DynamicPositionWithOwner.class */
public class DynamicPositionWithOwner<T> implements Position {
    private final Supplier<T> owner;
    private final DoubleExtractor<T> xGetter;
    private final DoubleAssigner<T> xSetter;
    private final DoubleExtractor<T> yGetter;
    private final DoubleAssigner<T> ySetter;
    private final DoubleExtractor<T> zGetter;
    private final DoubleAssigner<T> zSetter;

    public DynamicPositionWithOwner(Supplier<T> owner, DoubleExtractor<T> xGetter, DoubleAssigner<T> xSetter, DoubleExtractor<T> yGetter, DoubleAssigner<T> ySetter, DoubleExtractor<T> zGetter, DoubleAssigner<T> zSetter) {
        this.owner = owner;
        this.xGetter = xGetter;
        this.xSetter = xSetter;
        this.yGetter = yGetter;
        this.ySetter = ySetter;
        this.zGetter = zGetter;
        this.zSetter = zSetter;
    }

    @Override // net.labymod.api.util.math.position.Position
    public double getX() {
        return this.xGetter.get(this.owner.get());
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // net.labymod.api.util.math.position.Position
    public void setX(double d) {
        this.xSetter.set(this.owner.get(), d);
    }

    @Override // net.labymod.api.util.math.position.Position
    public double getY() {
        return this.yGetter.get(this.owner.get());
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // net.labymod.api.util.math.position.Position
    public void setY(double d) {
        this.ySetter.set(this.owner.get(), d);
    }

    @Override // net.labymod.api.util.math.position.Position
    public double getZ() {
        return this.zGetter.get(this.owner.get());
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // net.labymod.api.util.math.position.Position
    public void setZ(double d) {
        this.zSetter.set(this.owner.get(), d);
    }
}
