package net.labymod.core.watcher.list;

import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/watcher/list/WatchableArrayList.class */
public class WatchableArrayList<T> extends ArrayList<T> {
    private final WatchableList<T> watchableList;

    public WatchableArrayList(WatchableList<T> watchableList) {
        this.watchableList = watchableList;
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(T t) {
        this.watchableList.onAdd(t);
        return super.add(t);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public void add(int index, T element) {
        this.watchableList.onAdd(index, element);
        super.add(index, element);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends T> collection) {
        this.watchableList.onAddAll(collection);
        return super.addAll(collection);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public T remove(int i) {
        T t = (T) super.remove(i);
        this.watchableList.onRemove(t);
        return t;
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object o) {
        this.watchableList.onRemove(o);
        return super.remove(o);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        this.watchableList.onClear();
        super.clear();
    }

    public void addUnwatched(T t) {
        super.add(t);
    }
}
