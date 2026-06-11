package net.minecraft;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletionException;
import net.minecraft.util.Crypt;
import net.minecraft.util.FileUtil;
import net.minecraft.util.MemoryReserve;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/CrashReport.class */
public class CrashReport {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
    private final String title;
    private final Throwable exception;
    private Path saveFile;
    private final List<CrashReportCategory> details = Lists.newArrayList();
    private boolean trackingStackTrace = true;
    private StackTraceElement[] uncategorizedStackTrace = new StackTraceElement[0];
    private final SystemReport systemReport = new SystemReport();

    public CrashReport(String $$0, Throwable $$1) {
        this.title = $$0;
        this.exception = $$1;
    }

    public String getTitle() {
        return this.title;
    }

    public Throwable getException() {
        return this.exception;
    }

    public String getDetails() {
        StringBuilder $$0 = new StringBuilder();
        getDetails($$0);
        return $$0.toString();
    }

    public void getDetails(StringBuilder $$0) {
        if ((this.uncategorizedStackTrace == null || this.uncategorizedStackTrace.length <= 0) && !this.details.isEmpty()) {
            this.uncategorizedStackTrace = (StackTraceElement[]) ArrayUtils.subarray(this.details.get(0).getStacktrace(), 0, 1);
        }
        if (this.uncategorizedStackTrace != null && this.uncategorizedStackTrace.length > 0) {
            $$0.append("-- Head --\n");
            $$0.append("Thread: ").append(Thread.currentThread().getName()).append(Crypt.MIME_LINE_SEPARATOR);
            $$0.append("Stacktrace:\n");
            for (StackTraceElement $$1 : this.uncategorizedStackTrace) {
                $$0.append("\t").append("at ").append($$1);
                $$0.append(Crypt.MIME_LINE_SEPARATOR);
            }
            $$0.append(Crypt.MIME_LINE_SEPARATOR);
        }
        for (CrashReportCategory $$2 : this.details) {
            $$2.getDetails($$0);
            $$0.append("\n\n");
        }
        this.systemReport.appendToCrashReportString($$0);
    }

    public String getExceptionMessage() {
        StringWriter $$0 = null;
        PrintWriter $$1 = null;
        Throwable $$2 = this.exception;
        if ($$2.getMessage() == null) {
            if ($$2 instanceof NullPointerException) {
                $$2 = new NullPointerException(this.title);
            } else if ($$2 instanceof StackOverflowError) {
                $$2 = new StackOverflowError(this.title);
            } else if ($$2 instanceof OutOfMemoryError) {
                $$2 = new OutOfMemoryError(this.title);
            }
            $$2.setStackTrace(this.exception.getStackTrace());
        }
        try {
            $$0 = new StringWriter();
            $$1 = new PrintWriter($$0);
            $$2.printStackTrace($$1);
            String string = $$0.toString();
            IOUtils.closeQuietly($$0);
            IOUtils.closeQuietly($$1);
            return string;
        } catch (Throwable th) {
            IOUtils.closeQuietly($$0);
            IOUtils.closeQuietly($$1);
            throw th;
        }
    }

    public String getFriendlyReport(ReportType $$0, List<String> $$1) {
        StringBuilder $$2 = new StringBuilder();
        $$0.appendHeader($$2, $$1);
        $$2.append("Time: ");
        $$2.append(DATE_TIME_FORMATTER.format(ZonedDateTime.now()));
        $$2.append(Crypt.MIME_LINE_SEPARATOR);
        $$2.append("Description: ");
        $$2.append(this.title);
        $$2.append("\n\n");
        $$2.append(getExceptionMessage());
        $$2.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        for (int $$3 = 0; $$3 < 87; $$3++) {
            $$2.append("-");
        }
        $$2.append("\n\n");
        getDetails($$2);
        return $$2.toString();
    }

    public String getFriendlyReport(ReportType $$0) {
        return getFriendlyReport($$0, List.of());
    }

    public Path getSaveFile() {
        return this.saveFile;
    }

    public boolean saveToFile(Path $$0, ReportType $$1, List<String> $$2) {
        if (this.saveFile != null) {
            return false;
        }
        try {
            if ($$0.getParent() != null) {
                FileUtil.createDirectoriesSafe($$0.getParent());
            }
            Writer $$3 = Files.newBufferedWriter($$0, StandardCharsets.UTF_8, new OpenOption[0]);
            try {
                $$3.write(getFriendlyReport($$1, $$2));
                if ($$3 != null) {
                    $$3.close();
                }
                this.saveFile = $$0;
                return true;
            } finally {
            }
        } catch (Throwable $$4) {
            LOGGER.error("Could not save crash report to {}", $$0, $$4);
            return false;
        }
    }

    public boolean saveToFile(Path $$0, ReportType $$1) {
        return saveToFile($$0, $$1, List.of());
    }

    public SystemReport getSystemReport() {
        return this.systemReport;
    }

    public CrashReportCategory addCategory(String $$0) {
        return addCategory($$0, 1);
    }

    public CrashReportCategory addCategory(String $$0, int $$1) {
        CrashReportCategory $$2 = new CrashReportCategory($$0);
        if (this.trackingStackTrace) {
            int $$3 = $$2.fillInStackTrace($$1);
            StackTraceElement[] $$4 = this.exception.getStackTrace();
            StackTraceElement $$5 = null;
            StackTraceElement $$6 = null;
            int $$7 = $$4.length - $$3;
            if ($$7 < 0) {
                LOGGER.error("Negative index in crash report handler ({}/{})", Integer.valueOf($$4.length), Integer.valueOf($$3));
            }
            if ($$4 != null && 0 <= $$7 && $$7 < $$4.length) {
                $$5 = $$4[$$7];
                if (($$4.length + 1) - $$3 < $$4.length) {
                    $$6 = $$4[($$4.length + 1) - $$3];
                }
            }
            this.trackingStackTrace = $$2.validateStackTrace($$5, $$6);
            if ($$4 != null && $$4.length >= $$3 && 0 <= $$7 && $$7 < $$4.length) {
                this.uncategorizedStackTrace = new StackTraceElement[$$7];
                System.arraycopy($$4, 0, this.uncategorizedStackTrace, 0, this.uncategorizedStackTrace.length);
            } else {
                this.trackingStackTrace = false;
            }
        }
        this.details.add($$2);
        return $$2;
    }

    public static CrashReport forThrowable(Throwable $$0, String $$1) {
        CrashReport $$4;
        while (($$0 instanceof CompletionException) && $$0.getCause() != null) {
            $$0 = $$0.getCause();
        }
        if ($$0 instanceof ReportedException) {
            ReportedException $$2 = (ReportedException) $$0;
            $$4 = $$2.getReport();
        } else {
            $$4 = new CrashReport($$1, $$0);
        }
        return $$4;
    }

    public static void preload() {
        MemoryReserve.allocate();
        new CrashReport("Don't panic!", new Throwable()).getFriendlyReport(ReportType.CRASH);
    }
}
