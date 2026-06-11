package net.labymod.api.client.crash;

import net.labymod.api.util.function.ThrowableSupplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/crash/CrashReportAppender.class */
public abstract class CrashReportAppender {
    private StringBuilder builder;

    public abstract void append(StringBuilder sb);

    public final void appendCrashReport(StringBuilder builder) {
        this.builder = builder;
        append(builder);
    }

    public void setSubtitle(String subtitle) {
        if (this.builder == null) {
            return;
        }
        this.builder.append(subtitle).append(":").append("\n");
    }

    public void setDetail(String key, Object value) {
        if (value == null) {
            return;
        }
        setDetail(key, value.toString());
    }

    public void setDetail(String key, String value) {
        if (this.builder == null) {
            return;
        }
        this.builder.append("\t").append(key).append(": ").append(value).append("\n");
    }

    public void setDetail(String key, ThrowableSupplier<CharSequence, Throwable> value, String def) {
        if (this.builder == null) {
            return;
        }
        CharSequence finalValue = def;
        try {
            finalValue = value.get();
        } catch (Throwable th) {
        }
        this.builder.append("\t").append(key).append(": ").append(finalValue).append("\n");
    }

    protected void appendCrashReportDetails(CrashReportDetails details) {
        details.appendDetails(this);
    }

    protected void appendHeader(String name) {
        if (this.builder == null) {
            return;
        }
        this.builder.append("\n").append("-- ").append(name).append(" Details").append(" --").append("\n");
    }
}
