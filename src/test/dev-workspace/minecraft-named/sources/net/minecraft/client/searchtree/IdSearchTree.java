package net.minecraft.client.searchtree;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/searchtree/IdSearchTree.class */
public class IdSearchTree<T> implements SearchTree<T> {
    protected final Comparator<T> additionOrder;
    protected final IdentifierSearchTree<T> identifierSearchTree;

    public IdSearchTree(Function<T, Stream<Identifier>> $$0, List<T> $$1) {
        ToIntFunction<T> $$2 = Util.createIndexLookup($$1);
        this.additionOrder = Comparator.comparingInt($$2);
        this.identifierSearchTree = IdentifierSearchTree.create($$1, $$0);
    }

    @Override // net.minecraft.client.searchtree.SearchTree
    public List<T> search(String $$0) {
        int $$1 = $$0.indexOf(58);
        if ($$1 == -1) {
            return searchPlainText($$0);
        }
        return searchIdentifier($$0.substring(0, $$1).trim(), $$0.substring($$1 + 1).trim());
    }

    protected List<T> searchPlainText(String $$0) {
        return this.identifierSearchTree.searchPath($$0);
    }

    protected List<T> searchIdentifier(String $$0, String $$1) {
        List<T> $$2 = this.identifierSearchTree.searchNamespace($$0);
        List<T> $$3 = this.identifierSearchTree.searchPath($$1);
        return ImmutableList.copyOf(new IntersectionIterator($$2.iterator(), $$3.iterator(), this.additionOrder));
    }
}
