package net.minecraft.util;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringEscapeUtils;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/CsvOutput.class */
public class CsvOutput {
    private static final String LINE_SEPARATOR = "\r\n";
    private static final String FIELD_SEPARATOR = ",";
    private final Writer output;
    private final int columnCount;

    CsvOutput(Writer $$0, List<String> $$1) throws IOException {
        this.output = $$0;
        this.columnCount = $$1.size();
        writeLine($$1.stream());
    }

    public static Builder builder() {
        return new Builder();
    }

    public void writeRow(Object... $$0) throws IOException {
        if ($$0.length != this.columnCount) {
            throw new IllegalArgumentException("Invalid number of columns, expected " + this.columnCount + ", but got " + $$0.length);
        }
        writeLine(Stream.of($$0));
    }

    private void writeLine(Stream<? extends Object> $$0) throws IOException {
        this.output.write(((String) $$0.map(CsvOutput::getStringValue).collect(Collectors.joining(FIELD_SEPARATOR))) + "\r\n");
    }

    private static String getStringValue(Object $$0) {
        return StringEscapeUtils.escapeCsv($$0 != null ? $$0.toString() : "[null]");
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/CsvOutput$Builder.class */
    public static class Builder {
        private final List<String> headers = Lists.newArrayList();

        public Builder addColumn(String $$0) {
            this.headers.add($$0);
            return this;
        }

        public CsvOutput build(Writer $$0) throws IOException {
            return new CsvOutput($$0, this.headers);
        }
    }
}
