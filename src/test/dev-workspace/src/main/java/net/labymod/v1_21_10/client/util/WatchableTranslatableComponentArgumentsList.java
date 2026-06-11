package net.labymod.v1_21_10.client.util;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.client.component.Component;
import net.labymod.core.watcher.list.WatchableList;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/util/WatchableTranslatableComponentArgumentsList.class */
public class WatchableTranslatableComponentArgumentsList implements WatchableList<Component> {
    private final Supplier<Object[]> argumentsSupplier;
    private final Consumer<Object[]> argumentsConsumer;

    public WatchableTranslatableComponentArgumentsList(Supplier<Object[]> argumentsSupplier, Consumer<Object[]> argumentsConsumer) {
        this.argumentsSupplier = argumentsSupplier;
        this.argumentsConsumer = argumentsConsumer;
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onAdd(Component component) {
        Object[] arguments = this.argumentsSupplier.get();
        Object[] newArguments = new Object[arguments.length + 1];
        System.arraycopy(arguments, 0, newArguments, 0, arguments.length);
        newArguments[arguments.length] = component;
        this.argumentsConsumer.accept(newArguments);
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onRemove(Component component) {
        Object[] arguments = this.argumentsSupplier.get();
        Object[] newArguments = new Object[arguments.length - 1];
        int index = 0;
        for (Object argument : arguments) {
            if (argument != component) {
                int i = index;
                index++;
                newArguments[i] = argument;
            }
        }
        this.argumentsConsumer.accept(newArguments);
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onClear() {
        this.argumentsConsumer.accept(new Object[0]);
    }

    @Override // net.labymod.core.watcher.collection.WatchableCollection
    public void onAddAll(Collection<? extends Component> c) {
        Object[] arguments = this.argumentsSupplier.get();
        Object[] newArguments = new Object[arguments.length + c.size()];
        System.arraycopy(arguments, 0, newArguments, 0, arguments.length);
        int index = arguments.length;
        for (Component component : c) {
            int i = index;
            index++;
            newArguments[i] = component;
        }
        this.argumentsConsumer.accept(newArguments);
    }

    @Override // net.labymod.core.watcher.list.WatchableList
    public void onAdd(int index, Component component) {
    }
}
