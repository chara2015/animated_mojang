package com.mojang.realmsclient.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/util/TextRenderingUtils.class */
public class TextRenderingUtils {
    private TextRenderingUtils() {
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/util/TextRenderingUtils$Line.class */
    public static class Line {
        public final List<LineSegment> segments;

        Line(LineSegment... $$0) {
            this((List<LineSegment>) Arrays.asList($$0));
        }

        Line(List<LineSegment> $$0) {
            this.segments = $$0;
        }

        public String toString() {
            return "Line{segments=" + String.valueOf(this.segments) + "}";
        }

        public boolean equals(Object $$0) {
            if (this == $$0) {
                return true;
            }
            if ($$0 == null || getClass() != $$0.getClass()) {
                return false;
            }
            Line $$1 = (Line) $$0;
            return Objects.equals(this.segments, $$1.segments);
        }

        public int hashCode() {
            return Objects.hash(this.segments);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/util/TextRenderingUtils$LineSegment.class */
    public static class LineSegment {
        private final String fullText;
        private final String linkTitle;
        private final String linkUrl;

        private LineSegment(String $$0) {
            this.fullText = $$0;
            this.linkTitle = null;
            this.linkUrl = null;
        }

        private LineSegment(String $$0, String $$1, String $$2) {
            this.fullText = $$0;
            this.linkTitle = $$1;
            this.linkUrl = $$2;
        }

        public boolean equals(Object $$0) {
            if (this == $$0) {
                return true;
            }
            if ($$0 == null || getClass() != $$0.getClass()) {
                return false;
            }
            LineSegment $$1 = (LineSegment) $$0;
            return Objects.equals(this.fullText, $$1.fullText) && Objects.equals(this.linkTitle, $$1.linkTitle) && Objects.equals(this.linkUrl, $$1.linkUrl);
        }

        public int hashCode() {
            return Objects.hash(this.fullText, this.linkTitle, this.linkUrl);
        }

        public String toString() {
            return "Segment{fullText='" + this.fullText + "', linkTitle='" + this.linkTitle + "', linkUrl='" + this.linkUrl + "'}";
        }

        public String renderedText() {
            return isLink() ? this.linkTitle : this.fullText;
        }

        public boolean isLink() {
            return this.linkTitle != null;
        }

        public String getLinkUrl() {
            if (!isLink()) {
                throw new IllegalStateException("Not a link: " + String.valueOf(this));
            }
            return this.linkUrl;
        }

        public static LineSegment link(String $$0, String $$1) {
            return new LineSegment(null, $$0, $$1);
        }

        @VisibleForTesting
        protected static LineSegment text(String $$0) {
            return new LineSegment($$0);
        }
    }

    @VisibleForTesting
    protected static List<String> lineBreak(String $$0) {
        return Arrays.asList($$0.split("\\n"));
    }

    public static List<Line> decompose(String $$0, LineSegment... $$1) {
        return decompose($$0, (List<LineSegment>) Arrays.asList($$1));
    }

    private static List<Line> decompose(String $$0, List<LineSegment> $$1) {
        List<String> $$2 = lineBreak($$0);
        return insertLinks($$2, $$1);
    }

    private static List<Line> insertLinks(List<String> $$0, List<LineSegment> $$1) {
        int $$2 = 0;
        List<Line> $$3 = Lists.newArrayList();
        for (String $$4 : $$0) {
            List<LineSegment> $$5 = Lists.newArrayList();
            List<String> $$6 = split($$4, "%link");
            for (String $$7 : $$6) {
                if ("%link".equals($$7)) {
                    int i = $$2;
                    $$2++;
                    $$5.add($$1.get(i));
                } else {
                    $$5.add(LineSegment.text($$7));
                }
            }
            $$3.add(new Line($$5));
        }
        return $$3;
    }

    public static List<String> split(String $$0, String $$1) {
        int $$3;
        if ($$1.isEmpty()) {
            throw new IllegalArgumentException("Delimiter cannot be the empty string");
        }
        List<String> $$2 = Lists.newArrayList();
        int length = 0;
        while (true) {
            $$3 = length;
            int $$4 = $$0.indexOf($$1, $$3);
            if ($$4 == -1) {
                break;
            }
            if ($$4 > $$3) {
                $$2.add($$0.substring($$3, $$4));
            }
            $$2.add($$1);
            length = $$4 + $$1.length();
        }
        if ($$3 < $$0.length()) {
            $$2.add($$0.substring($$3));
        }
        return $$2;
    }
}
