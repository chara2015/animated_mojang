package net.labymod.api.util.version.comparison;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.labymod.api.models.version.VersionComparison;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/version/comparison/VersionMultiRangeComparison.class */
public class VersionMultiRangeComparison<T extends VersionComparison<T>> implements VersionComparison<T> {
    private final List<VersionComparison<T>> ranges;

    private VersionMultiRangeComparison(List<VersionComparison<T>> ranges) {
        this.ranges = ranges;
    }

    public static <T extends VersionComparison<T>> VersionMultiRangeComparison<T> parse(@NotNull String value, @NotNull Function<String, T> comparisonFactory) {
        return parse(value, comparisonFactory, null);
    }

    public static <T extends VersionComparison<T>> VersionMultiRangeComparison<T> parse(@NotNull String value, @NotNull Function<String, T> comparisonFactory, @Nullable BiConsumer<T, T> rangeValidator) {
        String[] entries = value.split(",");
        List<VersionComparison<T>> ranges = new ArrayList<>(entries.length);
        for (String entry : entries) {
            ranges.add(VersionRangeComparison.parse(entry, comparisonFactory, rangeValidator));
        }
        return new VersionMultiRangeComparison<>(ranges);
    }

    @Override // net.labymod.api.models.version.VersionComparison
    public boolean isCompatible(T version) {
        for (VersionComparison<T> range : this.ranges) {
            if (range.isCompatible(version)) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.models.version.VersionComparison
    public boolean isGreaterThan(T version) {
        for (VersionComparison<T> range : this.ranges) {
            if (range.isGreaterThan(version)) {
                return true;
            }
        }
        return false;
    }

    @Override // net.labymod.api.models.version.VersionComparison
    public boolean isLowerThan(T version) {
        for (VersionComparison<T> range : this.ranges) {
            if (range.isLowerThan(version)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.ranges.size(); i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(this.ranges.get(i).toString());
        }
        return builder.toString();
    }
}
