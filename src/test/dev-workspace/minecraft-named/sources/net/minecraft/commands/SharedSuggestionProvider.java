package net.minecraft.commands;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.permissions.PermissionSetSupplier;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Scoreboard;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/SharedSuggestionProvider.class */
public interface SharedSuggestionProvider extends PermissionSetSupplier {
    public static final CharMatcher MATCH_SPLITTER = CharMatcher.anyOf("._/");

    Collection<String> getOnlinePlayerNames();

    Collection<String> getAllTeams();

    Stream<Identifier> getAvailableSounds();

    CompletableFuture<Suggestions> customSuggestion(CommandContext<?> commandContext);

    Set<ResourceKey<Level>> levels();

    RegistryAccess registryAccess();

    FeatureFlagSet enabledFeatures();

    CompletableFuture<Suggestions> suggestRegistryElements(ResourceKey<? extends Registry<?>> resourceKey, ElementSuggestionType elementSuggestionType, SuggestionsBuilder suggestionsBuilder, CommandContext<?> commandContext);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/SharedSuggestionProvider$TextCoordinates.class */
    public static class TextCoordinates {
        public static final TextCoordinates DEFAULT_LOCAL = new TextCoordinates("^", "^", "^");
        public static final TextCoordinates DEFAULT_GLOBAL = new TextCoordinates("~", "~", "~");
        public final String x;
        public final String y;
        public final String z;

        public TextCoordinates(String $$0, String $$1, String $$2) {
            this.x = $$0;
            this.y = $$1;
            this.z = $$2;
        }
    }

    default Collection<String> getCustomTabSugggestions() {
        return getOnlinePlayerNames();
    }

    default Collection<String> getSelectedEntities() {
        return Collections.emptyList();
    }

    default Collection<TextCoordinates> getRelevantCoordinates() {
        return Collections.singleton(TextCoordinates.DEFAULT_GLOBAL);
    }

    default Collection<TextCoordinates> getAbsoluteCoordinates() {
        return Collections.singleton(TextCoordinates.DEFAULT_GLOBAL);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/SharedSuggestionProvider$ElementSuggestionType.class */
    public enum ElementSuggestionType {
        TAGS,
        ELEMENTS,
        ALL;

        public boolean shouldSuggestTags() {
            return this == TAGS || this == ALL;
        }

        public boolean shouldSuggestElements() {
            return this == ELEMENTS || this == ALL;
        }
    }

    default void suggestRegistryElements(HolderLookup<?> $$0, ElementSuggestionType $$1, SuggestionsBuilder $$2) {
        if ($$1.shouldSuggestTags()) {
            suggestResource((Stream<Identifier>) $$0.listTagIds().map((v0) -> {
                return v0.location();
            }), $$2, Scoreboard.HIDDEN_SCORE_PREFIX);
        }
        if ($$1.shouldSuggestElements()) {
            suggestResource((Stream<Identifier>) $$0.listElementIds().map((v0) -> {
                return v0.identifier();
            }), $$2);
        }
    }

    static <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1, ResourceKey<? extends Registry<?>> $$2, ElementSuggestionType $$3) {
        Object source = $$0.getSource();
        if (source instanceof SharedSuggestionProvider) {
            SharedSuggestionProvider $$4 = (SharedSuggestionProvider) source;
            return $$4.suggestRegistryElements($$2, $$3, $$1, $$0);
        }
        return $$1.buildFuture();
    }

    static <T> void filterResources(Iterable<T> $$0, String $$1, Function<T, Identifier> $$2, Consumer<T> $$3) {
        boolean $$4 = $$1.indexOf(58) > -1;
        for (T $$5 : $$0) {
            Identifier $$6 = $$2.apply($$5);
            if ($$4) {
                String $$7 = $$6.toString();
                if (matchesSubStr($$1, $$7)) {
                    $$3.accept($$5);
                }
            } else if (matchesSubStr($$1, $$6.getNamespace()) || matchesSubStr($$1, $$6.getPath())) {
                $$3.accept($$5);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static <T> void filterResources(Iterable<T> $$0, String $$1, String $$2, Function<T, Identifier> $$3, Consumer<T> consumer) {
        if ($$1.isEmpty()) {
            $$0.forEach(consumer);
            return;
        }
        String $$5 = Strings.commonPrefix($$1, $$2);
        if (!$$5.isEmpty()) {
            String $$6 = $$1.substring($$5.length());
            filterResources($$0, $$6, $$3, consumer);
        }
    }

    static CompletableFuture<Suggestions> suggestResource(Iterable<Identifier> $$0, SuggestionsBuilder $$1, String $$2) {
        String $$3 = $$1.getRemaining().toLowerCase(Locale.ROOT);
        filterResources($$0, $$3, $$2, $$02 -> {
            return $$02;
        }, $$22 -> {
            $$1.suggest($$2 + String.valueOf($$22));
        });
        return $$1.buildFuture();
    }

    static CompletableFuture<Suggestions> suggestResource(Stream<Identifier> $$0, SuggestionsBuilder $$1, String $$2) {
        Objects.requireNonNull($$0);
        return suggestResource((Iterable<Identifier>) $$0::iterator, $$1, $$2);
    }

    static CompletableFuture<Suggestions> suggestResource(Iterable<Identifier> $$0, SuggestionsBuilder $$1) {
        String $$2 = $$1.getRemaining().toLowerCase(Locale.ROOT);
        filterResources($$0, $$2, $$02 -> {
            return $$02;
        }, $$12 -> {
            $$1.suggest($$12.toString());
        });
        return $$1.buildFuture();
    }

    static <T> CompletableFuture<Suggestions> suggestResource(Iterable<T> $$0, SuggestionsBuilder $$1, Function<T, Identifier> $$2, Function<T, Message> $$3) {
        String $$4 = $$1.getRemaining().toLowerCase(Locale.ROOT);
        filterResources($$0, $$4, $$2, $$32 -> {
            $$1.suggest(((Identifier) $$2.apply($$32)).toString(), (Message) $$3.apply($$32));
        });
        return $$1.buildFuture();
    }

    static CompletableFuture<Suggestions> suggestResource(Stream<Identifier> $$0, SuggestionsBuilder $$1) {
        Objects.requireNonNull($$0);
        return suggestResource((Iterable<Identifier>) $$0::iterator, $$1);
    }

    static <T> CompletableFuture<Suggestions> suggestResource(Stream<T> $$0, SuggestionsBuilder $$1, Function<T, Identifier> $$2, Function<T, Message> $$3) {
        Objects.requireNonNull($$0);
        return suggestResource($$0::iterator, $$1, $$2, $$3);
    }

    static CompletableFuture<Suggestions> suggestCoordinates(String $$0, Collection<TextCoordinates> $$1, SuggestionsBuilder $$2, Predicate<String> $$3) {
        List<String> $$4 = Lists.newArrayList();
        if (Strings.isNullOrEmpty($$0)) {
            for (TextCoordinates $$5 : $$1) {
                String $$6 = $$5.x + " " + $$5.y + " " + $$5.z;
                if ($$3.test($$6)) {
                    $$4.add($$5.x);
                    $$4.add($$5.x + " " + $$5.y);
                    $$4.add($$6);
                }
            }
        } else {
            String[] $$7 = $$0.split(" ");
            if ($$7.length == 1) {
                for (TextCoordinates $$8 : $$1) {
                    String $$9 = $$7[0] + " " + $$8.y + " " + $$8.z;
                    if ($$3.test($$9)) {
                        $$4.add($$7[0] + " " + $$8.y);
                        $$4.add($$9);
                    }
                }
            } else if ($$7.length == 2) {
                for (TextCoordinates $$10 : $$1) {
                    String $$11 = $$7[0] + " " + $$7[1] + " " + $$10.z;
                    if ($$3.test($$11)) {
                        $$4.add($$11);
                    }
                }
            }
        }
        return suggest($$4, $$2);
    }

    static CompletableFuture<Suggestions> suggest2DCoordinates(String $$0, Collection<TextCoordinates> $$1, SuggestionsBuilder $$2, Predicate<String> $$3) {
        List<String> $$4 = Lists.newArrayList();
        if (Strings.isNullOrEmpty($$0)) {
            for (TextCoordinates $$5 : $$1) {
                String $$6 = $$5.x + " " + $$5.z;
                if ($$3.test($$6)) {
                    $$4.add($$5.x);
                    $$4.add($$6);
                }
            }
        } else {
            String[] $$7 = $$0.split(" ");
            if ($$7.length == 1) {
                for (TextCoordinates $$8 : $$1) {
                    String $$9 = $$7[0] + " " + $$8.z;
                    if ($$3.test($$9)) {
                        $$4.add($$9);
                    }
                }
            }
        }
        return suggest($$4, $$2);
    }

    static CompletableFuture<Suggestions> suggest(Iterable<String> $$0, SuggestionsBuilder $$1) {
        String $$2 = $$1.getRemaining().toLowerCase(Locale.ROOT);
        for (String $$3 : $$0) {
            if (matchesSubStr($$2, $$3.toLowerCase(Locale.ROOT))) {
                $$1.suggest($$3);
            }
        }
        return $$1.buildFuture();
    }

    static CompletableFuture<Suggestions> suggest(Stream<String> $$0, SuggestionsBuilder $$1) {
        String $$2 = $$1.getRemaining().toLowerCase(Locale.ROOT);
        Stream<String> streamFilter = $$0.filter($$12 -> {
            return matchesSubStr($$2, $$12.toLowerCase(Locale.ROOT));
        });
        Objects.requireNonNull($$1);
        streamFilter.forEach($$1::suggest);
        return $$1.buildFuture();
    }

    static CompletableFuture<Suggestions> suggest(String[] $$0, SuggestionsBuilder $$1) {
        String $$2 = $$1.getRemaining().toLowerCase(Locale.ROOT);
        for (String $$3 : $$0) {
            if (matchesSubStr($$2, $$3.toLowerCase(Locale.ROOT))) {
                $$1.suggest($$3);
            }
        }
        return $$1.buildFuture();
    }

    static <T> CompletableFuture<Suggestions> suggest(Iterable<T> $$0, SuggestionsBuilder $$1, Function<T, String> $$2, Function<T, Message> $$3) {
        String $$4 = $$1.getRemaining().toLowerCase(Locale.ROOT);
        for (T $$5 : $$0) {
            String $$6 = $$2.apply($$5);
            if (matchesSubStr($$4, $$6.toLowerCase(Locale.ROOT))) {
                $$1.suggest($$6, $$3.apply($$5));
            }
        }
        return $$1.buildFuture();
    }

    static boolean matchesSubStr(String $$0, String $$1) {
        int i = 0;
        while (true) {
            int $$2 = i;
            if (!$$1.startsWith($$0, $$2)) {
                int $$3 = MATCH_SPLITTER.indexIn($$1, $$2);
                if ($$3 < 0) {
                    return false;
                }
                i = $$3 + 1;
            } else {
                return true;
            }
        }
    }
}
