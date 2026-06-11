package net.labymod.api.util.version.comparison;

import java.util.Locale;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.labymod.api.models.version.VersionComparison;
import net.labymod.api.util.version.exception.VersionException;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/comparison/VersionRangeComparison.class */
public class VersionRangeComparison<T extends VersionComparison<T>> implements VersionComparison<T> {
    private final T from;
    private final T to;

    @Nullable
    private final BiConsumer<T, T> rangeValidator;

    private VersionRangeComparison(@Nullable T from, @Nullable T to, @Nullable BiConsumer<T, T> rangeValidator) {
        this.from = from;
        this.to = to;
        this.rangeValidator = rangeValidator;
        validateRange();
    }

    public static <T extends VersionComparison<T>> VersionRangeComparison<T> parse(String value, Function<String, T> comparisonFactory) {
        return parse(value, comparisonFactory, null);
    }

    public static <T extends VersionComparison<T>> VersionRangeComparison<T> parse(String value, Function<String, T> comparisonFactory, @Nullable BiConsumer<T, T> rangeValidator) {
        String[] entries = value.split("<");
        VersionComparison versionComparisonFromVersion = fromVersion(entries[0], comparisonFactory);
        return new VersionRangeComparison<>(versionComparisonFromVersion, entries.length == 1 ? versionComparisonFromVersion : fromVersion(entries[1], comparisonFactory), rangeValidator);
    }

    private static <T extends VersionComparison<T>> T fromVersion(String version, Function<String, T> comparisonFactory) {
        if (version.equals("*")) {
            return null;
        }
        return comparisonFactory.apply(version);
    }

    private void validateRange() {
        if (this.from == null || this.to == null) {
            return;
        }
        if (this.rangeValidator != null) {
            this.rangeValidator.accept(this.from, this.to);
        }
        if (this.from.isGreaterThan(this.to)) {
            throw new VersionException("Range from " + String.valueOf(this.from) + " is greater than to " + String.valueOf(this.to));
        }
    }

    @Override // net.labymod.api.models.version.VersionComparison
    public boolean isCompatible(T version) {
        if (this.from == null || !this.from.isCompatible(version)) {
            if (this.to == null || !this.to.isCompatible(version)) {
                return (this.from == null || version.isGreaterThan(this.from)) && (this.to == null || version.isLowerThan(this.to));
            }
            return true;
        }
        return true;
    }

    @Override // net.labymod.api.models.version.VersionComparison
    public boolean isGreaterThan(T version) {
        return this.from != null && this.from.isGreaterThan(version);
    }

    @Override // net.labymod.api.models.version.VersionComparison
    public boolean isLowerThan(T version) {
        return this.to != null && this.to.isLowerThan(version);
    }

    public String toString() {
        if (Objects.equals(this.from, this.to)) {
            return this.from == null ? "*" : this.from.toString();
        }
        Locale locale = Locale.ROOT;
        Object[] objArr = new Object[2];
        objArr[0] = this.from == null ? "*" : this.from;
        objArr[1] = this.to == null ? "*" : this.to;
        return String.format(locale, "%s<%s", objArr);
    }
}
