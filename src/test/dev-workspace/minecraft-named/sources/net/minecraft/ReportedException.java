package net.minecraft;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/ReportedException.class */
public class ReportedException extends RuntimeException {
    private final CrashReport report;

    public ReportedException(CrashReport $$0) {
        this.report = $$0;
    }

    public CrashReport getReport() {
        return this.report;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.report.getException();
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.report.getTitle();
    }
}
