package net.minecraft.server.network;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/network/TextFilter.class */
public interface TextFilter {
    public static final TextFilter DUMMY = new TextFilter() { // from class: net.minecraft.server.network.TextFilter.1
        @Override // net.minecraft.server.network.TextFilter
        public CompletableFuture<FilteredText> processStreamMessage(String $$0) {
            return CompletableFuture.completedFuture(FilteredText.passThrough($$0));
        }

        @Override // net.minecraft.server.network.TextFilter
        public CompletableFuture<List<FilteredText>> processMessageBundle(List<String> $$0) {
            return CompletableFuture.completedFuture((List) $$0.stream().map(FilteredText::passThrough).collect(ImmutableList.toImmutableList()));
        }
    };

    CompletableFuture<FilteredText> processStreamMessage(String str);

    CompletableFuture<List<FilteredText>> processMessageBundle(List<String> list);

    default void join() {
    }

    default void leave() {
    }
}
