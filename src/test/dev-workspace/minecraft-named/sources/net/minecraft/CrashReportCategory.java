package net.minecraft;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.state.BlockState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/CrashReportCategory.class */
public class CrashReportCategory {
    private final String title;
    private final List<Entry> entries = Lists.newArrayList();
    private StackTraceElement[] stackTrace = new StackTraceElement[0];

    public CrashReportCategory(String $$0) {
        this.title = $$0;
    }

    public static String formatLocation(double $$0, double $$1, double $$2) {
        return String.format(Locale.ROOT, "%.2f,%.2f,%.2f", Double.valueOf($$0), Double.valueOf($$1), Double.valueOf($$2));
    }

    public static String formatLocation(LevelHeightAccessor $$0, double $$1, double $$2, double $$3) {
        return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", Double.valueOf($$1), Double.valueOf($$2), Double.valueOf($$3), formatLocation($$0, BlockPos.containing($$1, $$2, $$3)));
    }

    public static String formatLocation(LevelHeightAccessor $$0, BlockPos $$1) {
        return formatLocation($$0, $$1.getX(), $$1.getY(), $$1.getZ());
    }

    public static String formatLocation(LevelHeightAccessor $$0, int $$1, int $$2, int $$3) {
        StringBuilder $$4 = new StringBuilder();
        try {
            $$4.append(String.format(Locale.ROOT, "World: (%d,%d,%d)", Integer.valueOf($$1), Integer.valueOf($$2), Integer.valueOf($$3)));
        } catch (Throwable th) {
            $$4.append("(Error finding world loc)");
        }
        $$4.append(ComponentUtils.DEFAULT_SEPARATOR_TEXT);
        try {
            int $$6 = SectionPos.blockToSectionCoord($$1);
            int $$7 = SectionPos.blockToSectionCoord($$2);
            int $$8 = SectionPos.blockToSectionCoord($$3);
            int $$9 = $$1 & 15;
            int $$10 = $$2 & 15;
            int $$11 = $$3 & 15;
            int $$12 = SectionPos.sectionToBlockCoord($$6);
            int $$13 = $$0.getMinY();
            int $$14 = SectionPos.sectionToBlockCoord($$8);
            int $$15 = SectionPos.sectionToBlockCoord($$6 + 1) - 1;
            int $$16 = $$0.getMaxY();
            int $$17 = SectionPos.sectionToBlockCoord($$8 + 1) - 1;
            $$4.append(String.format(Locale.ROOT, "Section: (at %d,%d,%d in %d,%d,%d; chunk contains blocks %d,%d,%d to %d,%d,%d)", Integer.valueOf($$9), Integer.valueOf($$10), Integer.valueOf($$11), Integer.valueOf($$6), Integer.valueOf($$7), Integer.valueOf($$8), Integer.valueOf($$12), Integer.valueOf($$13), Integer.valueOf($$14), Integer.valueOf($$15), Integer.valueOf($$16), Integer.valueOf($$17)));
        } catch (Throwable th2) {
            $$4.append("(Error finding chunk loc)");
        }
        $$4.append(ComponentUtils.DEFAULT_SEPARATOR_TEXT);
        try {
            int $$19 = $$1 >> 9;
            int $$20 = $$3 >> 9;
            int $$21 = $$19 << 5;
            int $$22 = $$20 << 5;
            int $$23 = (($$19 + 1) << 5) - 1;
            int $$24 = (($$20 + 1) << 5) - 1;
            int $$25 = $$19 << 9;
            int $$26 = $$0.getMinY();
            int $$27 = $$20 << 9;
            int $$28 = (($$19 + 1) << 9) - 1;
            int $$29 = $$0.getMaxY();
            int $$30 = (($$20 + 1) << 9) - 1;
            $$4.append(String.format(Locale.ROOT, "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,%d,%d to %d,%d,%d)", Integer.valueOf($$19), Integer.valueOf($$20), Integer.valueOf($$21), Integer.valueOf($$22), Integer.valueOf($$23), Integer.valueOf($$24), Integer.valueOf($$25), Integer.valueOf($$26), Integer.valueOf($$27), Integer.valueOf($$28), Integer.valueOf($$29), Integer.valueOf($$30)));
        } catch (Throwable th3) {
            $$4.append("(Error finding world loc)");
        }
        return $$4.toString();
    }

    public CrashReportCategory setDetail(String $$0, CrashReportDetail<String> $$1) {
        try {
            setDetail($$0, $$1.call());
        } catch (Throwable $$2) {
            setDetailError($$0, $$2);
        }
        return this;
    }

    public CrashReportCategory setDetail(String $$0, Object $$1) {
        this.entries.add(new Entry($$0, $$1));
        return this;
    }

    public void setDetailError(String $$0, Throwable $$1) {
        setDetail($$0, $$1);
    }

    public int fillInStackTrace(int $$0) {
        StackTraceElement[] $$1 = Thread.currentThread().getStackTrace();
        if ($$1.length <= 0) {
            return 0;
        }
        this.stackTrace = new StackTraceElement[($$1.length - 3) - $$0];
        System.arraycopy($$1, 3 + $$0, this.stackTrace, 0, this.stackTrace.length);
        return this.stackTrace.length;
    }

    public boolean validateStackTrace(StackTraceElement $$0, StackTraceElement $$1) {
        if (this.stackTrace.length == 0 || $$0 == null) {
            return false;
        }
        StackTraceElement $$2 = this.stackTrace[0];
        if ($$2.isNativeMethod() != $$0.isNativeMethod() || !$$2.getClassName().equals($$0.getClassName()) || !$$2.getFileName().equals($$0.getFileName()) || !$$2.getMethodName().equals($$0.getMethodName())) {
            return false;
        }
        if (($$1 != null) != (this.stackTrace.length > 1)) {
            return false;
        }
        if ($$1 != null && !this.stackTrace[1].equals($$1)) {
            return false;
        }
        this.stackTrace[0] = $$0;
        return true;
    }

    public void trimStacktrace(int $$0) {
        StackTraceElement[] $$1 = new StackTraceElement[this.stackTrace.length - $$0];
        System.arraycopy(this.stackTrace, 0, $$1, 0, $$1.length);
        this.stackTrace = $$1;
    }

    public void getDetails(StringBuilder $$0) {
        $$0.append("-- ").append(this.title).append(" --\n");
        $$0.append("Details:");
        for (Entry $$1 : this.entries) {
            $$0.append("\n\t");
            $$0.append($$1.getKey());
            $$0.append(": ");
            $$0.append($$1.getValue());
        }
        if (this.stackTrace != null && this.stackTrace.length > 0) {
            $$0.append("\nStacktrace:");
            for (StackTraceElement $$2 : this.stackTrace) {
                $$0.append("\n\tat ");
                $$0.append($$2);
            }
        }
    }

    public StackTraceElement[] getStacktrace() {
        return this.stackTrace;
    }

    public static void populateBlockDetails(CrashReportCategory $$0, LevelHeightAccessor $$1, BlockPos $$2, BlockState $$3) {
        Objects.requireNonNull($$3);
        $$0.setDetail("Block", $$3::toString);
        populateBlockLocationDetails($$0, $$1, $$2);
    }

    public static CrashReportCategory populateBlockLocationDetails(CrashReportCategory $$0, LevelHeightAccessor $$1, BlockPos $$2) {
        return $$0.setDetail("Block location", () -> {
            return formatLocation($$1, $$2);
        });
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/CrashReportCategory$Entry.class */
    static class Entry {
        private final String key;
        private final String value;

        public Entry(String $$0, Object $$1) {
            this.key = $$0;
            if ($$1 == null) {
                this.value = "~~NULL~~";
            } else if ($$1 instanceof Throwable) {
                Throwable $$2 = (Throwable) $$1;
                this.value = "~~ERROR~~ " + $$2.getClass().getSimpleName() + ": " + $$2.getMessage();
            } else {
                this.value = $$1.toString();
            }
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }
    }
}
