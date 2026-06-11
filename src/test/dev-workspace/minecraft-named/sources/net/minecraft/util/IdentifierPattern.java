package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/IdentifierPattern.class */
public class IdentifierPattern {
    public static final Codec<IdentifierPattern> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.PATTERN.optionalFieldOf("namespace").forGetter($$0 -> {
            return $$0.namespacePattern;
        }), ExtraCodecs.PATTERN.optionalFieldOf("path").forGetter($$02 -> {
            return $$02.pathPattern;
        })).apply($$0, IdentifierPattern::new);
    });
    private final Optional<Pattern> namespacePattern;
    private final Predicate<String> namespacePredicate;
    private final Optional<Pattern> pathPattern;
    private final Predicate<String> pathPredicate;
    private final Predicate<Identifier> locationPredicate = $$0 -> {
        return this.namespacePredicate.test($$0.getNamespace()) && this.pathPredicate.test($$0.getPath());
    };

    private IdentifierPattern(Optional<Pattern> $$0, Optional<Pattern> $$1) {
        this.namespacePattern = $$0;
        this.namespacePredicate = (Predicate) $$0.map((v0) -> {
            return v0.asPredicate();
        }).orElse($$02 -> {
            return true;
        });
        this.pathPattern = $$1;
        this.pathPredicate = (Predicate) $$1.map((v0) -> {
            return v0.asPredicate();
        }).orElse($$03 -> {
            return true;
        });
    }

    public Predicate<String> namespacePredicate() {
        return this.namespacePredicate;
    }

    public Predicate<String> pathPredicate() {
        return this.pathPredicate;
    }

    public Predicate<Identifier> locationPredicate() {
        return this.locationPredicate;
    }
}
