package net.minecraft.client;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.StringDecomposer;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/StringSplitter.class */
public class StringSplitter {
    final WidthProvider widthProvider;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/StringSplitter$LinePosConsumer.class */
    @FunctionalInterface
    public interface LinePosConsumer {
        void accept(Style style, int i, int i2);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/StringSplitter$WidthProvider.class */
    @FunctionalInterface
    public interface WidthProvider {
        float getWidth(int i, Style style);
    }

    public StringSplitter(WidthProvider $$0) {
        this.widthProvider = $$0;
    }

    public float stringWidth(String $$0) {
        if ($$0 == null) {
            return 0.0f;
        }
        MutableFloat $$1 = new MutableFloat();
        StringDecomposer.iterateFormatted($$0, Style.EMPTY, ($$12, $$2, $$3) -> {
            $$1.add(this.widthProvider.getWidth($$3, $$2));
            return true;
        });
        return $$1.floatValue();
    }

    public float stringWidth(FormattedText $$0) {
        MutableFloat $$1 = new MutableFloat();
        StringDecomposer.iterateFormatted($$0, Style.EMPTY, ($$12, $$2, $$3) -> {
            $$1.add(this.widthProvider.getWidth($$3, $$2));
            return true;
        });
        return $$1.floatValue();
    }

    public float stringWidth(FormattedCharSequence $$0) {
        MutableFloat $$1 = new MutableFloat();
        $$0.accept(($$12, $$2, $$3) -> {
            $$1.add(this.widthProvider.getWidth($$3, $$2));
            return true;
        });
        return $$1.floatValue();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/StringSplitter$WidthLimitedCharSink.class */
    class WidthLimitedCharSink implements FormattedCharSink {
        private float maxWidth;
        private int position;

        public WidthLimitedCharSink(float $$0) {
            this.maxWidth = $$0;
        }

        @Override // net.minecraft.util.FormattedCharSink
        public boolean accept(int $$0, Style $$1, int $$2) {
            this.maxWidth -= StringSplitter.this.widthProvider.getWidth($$2, $$1);
            if (this.maxWidth >= 0.0f) {
                this.position = $$0 + Character.charCount($$2);
                return true;
            }
            return false;
        }

        public int getPosition() {
            return this.position;
        }

        public void resetPosition() {
            this.position = 0;
        }
    }

    public int plainIndexAtWidth(String $$0, int $$1, Style $$2) {
        WidthLimitedCharSink $$3 = new WidthLimitedCharSink($$1);
        StringDecomposer.iterate($$0, $$2, $$3);
        return $$3.getPosition();
    }

    public String plainHeadByWidth(String $$0, int $$1, Style $$2) {
        return $$0.substring(0, plainIndexAtWidth($$0, $$1, $$2));
    }

    public String plainTailByWidth(String $$0, int $$1, Style $$2) {
        MutableFloat $$3 = new MutableFloat();
        MutableInt $$4 = new MutableInt($$0.length());
        StringDecomposer.iterateBackwards($$0, $$2, ($$32, $$42, $$5) -> {
            float $$6 = $$3.addAndGet(this.widthProvider.getWidth($$5, $$42));
            if ($$6 > $$1) {
                return false;
            }
            $$4.setValue($$32);
            return true;
        });
        return $$0.substring($$4.intValue());
    }

    public FormattedText headByWidth(FormattedText $$0, int $$1, Style $$2) {
        final WidthLimitedCharSink $$3 = new WidthLimitedCharSink($$1);
        return (FormattedText) $$0.visit(new FormattedText.StyledContentConsumer<FormattedText>(this) { // from class: net.minecraft.client.StringSplitter.1
            private final ComponentCollector collector = new ComponentCollector();

            @Override // net.minecraft.network.chat.FormattedText.StyledContentConsumer
            public Optional<FormattedText> accept(Style $$02, String $$12) {
                $$3.resetPosition();
                if (!StringDecomposer.iterateFormatted($$12, $$02, $$3)) {
                    String $$22 = $$12.substring(0, $$3.getPosition());
                    if (!$$22.isEmpty()) {
                        this.collector.append(FormattedText.of($$22, $$02));
                    }
                    return Optional.of(this.collector.getResultOrEmpty());
                }
                if (!$$12.isEmpty()) {
                    this.collector.append(FormattedText.of($$12, $$02));
                }
                return Optional.empty();
            }
        }, $$2).orElse($$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/StringSplitter$LineBreakFinder.class */
    class LineBreakFinder implements FormattedCharSink {
        private final float maxWidth;
        private boolean hadNonZeroWidthChar;
        private float width;
        private int nextChar;
        private int offset;
        private int lineBreak = -1;
        private Style lineBreakStyle = Style.EMPTY;
        private int lastSpace = -1;
        private Style lastSpaceStyle = Style.EMPTY;

        public LineBreakFinder(float $$0) {
            this.maxWidth = Math.max($$0, 1.0f);
        }

        @Override // net.minecraft.util.FormattedCharSink
        public boolean accept(int $$0, Style $$1, int $$2) {
            int $$3 = $$0 + this.offset;
            switch ($$2) {
                case 10:
                    return finishIteration($$3, $$1);
                case 32:
                    this.lastSpace = $$3;
                    this.lastSpaceStyle = $$1;
                    break;
            }
            float $$4 = StringSplitter.this.widthProvider.getWidth($$2, $$1);
            this.width += $$4;
            if (this.hadNonZeroWidthChar && this.width > this.maxWidth) {
                if (this.lastSpace != -1) {
                    return finishIteration(this.lastSpace, this.lastSpaceStyle);
                }
                return finishIteration($$3, $$1);
            }
            this.hadNonZeroWidthChar |= $$4 != 0.0f;
            this.nextChar = $$3 + Character.charCount($$2);
            return true;
        }

        private boolean finishIteration(int $$0, Style $$1) {
            this.lineBreak = $$0;
            this.lineBreakStyle = $$1;
            return false;
        }

        private boolean lineBreakFound() {
            return this.lineBreak != -1;
        }

        public int getSplitPosition() {
            return lineBreakFound() ? this.lineBreak : this.nextChar;
        }

        public Style getSplitStyle() {
            return this.lineBreakStyle;
        }

        public void addToOffset(int $$0) {
            this.offset += $$0;
        }
    }

    public int findLineBreak(String $$0, int $$1, Style $$2) {
        LineBreakFinder $$3 = new LineBreakFinder($$1);
        StringDecomposer.iterateFormatted($$0, $$2, $$3);
        return $$3.getSplitPosition();
    }

    public static int getWordPosition(String $$0, int $$1, int $$2, boolean $$3) {
        int $$4 = $$2;
        boolean $$5 = $$1 < 0;
        int $$6 = Math.abs($$1);
        for (int $$7 = 0; $$7 < $$6; $$7++) {
            if ($$5) {
                while ($$3 && $$4 > 0 && ($$0.charAt($$4 - 1) == ' ' || $$0.charAt($$4 - 1) == '\n')) {
                    $$4--;
                }
                while ($$4 > 0 && $$0.charAt($$4 - 1) != ' ' && $$0.charAt($$4 - 1) != '\n') {
                    $$4--;
                }
            } else {
                int $$8 = $$0.length();
                int $$9 = $$0.indexOf(32, $$4);
                int $$10 = $$0.indexOf(10, $$4);
                if ($$9 == -1 && $$10 == -1) {
                    $$4 = -1;
                } else if ($$9 != -1 && $$10 != -1) {
                    $$4 = Math.min($$9, $$10);
                } else if ($$9 != -1) {
                    $$4 = $$9;
                } else {
                    $$4 = $$10;
                }
                if ($$4 == -1) {
                    $$4 = $$8;
                } else {
                    while ($$3 && $$4 < $$8 && ($$0.charAt($$4) == ' ' || $$0.charAt($$4) == '\n')) {
                        $$4++;
                    }
                }
            }
        }
        return $$4;
    }

    public void splitLines(String $$0, int $$1, Style $$2, boolean $$3, LinePosConsumer $$4) {
        int $$5 = 0;
        int $$6 = $$0.length();
        Style splitStyle = $$2;
        while (true) {
            Style $$7 = splitStyle;
            if ($$5 < $$6) {
                LineBreakFinder $$8 = new LineBreakFinder($$1);
                boolean $$9 = StringDecomposer.iterateFormatted($$0, $$5, $$7, $$2, $$8);
                if ($$9) {
                    $$4.accept($$7, $$5, $$6);
                    return;
                }
                int $$10 = $$8.getSplitPosition();
                char $$11 = $$0.charAt($$10);
                int $$12 = ($$11 == '\n' || $$11 == ' ') ? $$10 + 1 : $$10;
                $$4.accept($$7, $$5, $$3 ? $$12 : $$10);
                $$5 = $$12;
                splitStyle = $$8.getSplitStyle();
            } else {
                return;
            }
        }
    }

    public List<FormattedText> splitLines(String $$0, int $$1, Style $$2) {
        List<FormattedText> $$3 = Lists.newArrayList();
        splitLines($$0, $$1, $$2, false, ($$22, $$32, $$4) -> {
            $$3.add(FormattedText.of($$0.substring($$32, $$4), $$22));
        });
        return $$3;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/StringSplitter$LineComponent.class */
    static class LineComponent implements FormattedText {
        final String contents;
        final Style style;

        public LineComponent(String $$0, Style $$1) {
            this.contents = $$0;
            this.style = $$1;
        }

        @Override // net.minecraft.network.chat.FormattedText
        public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
            return $$0.accept(this.contents);
        }

        @Override // net.minecraft.network.chat.FormattedText
        public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
            return $$0.accept(this.style.applyTo($$1), this.contents);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/StringSplitter$FlatComponents.class */
    static class FlatComponents {
        final List<LineComponent> parts;
        private String flatParts;

        public FlatComponents(List<LineComponent> $$0) {
            this.parts = $$0;
            this.flatParts = (String) $$0.stream().map($$02 -> {
                return $$02.contents;
            }).collect(Collectors.joining());
        }

        public char charAt(int $$0) {
            return this.flatParts.charAt($$0);
        }

        public FormattedText splitAt(int $$0, int $$1, Style $$2) {
            ComponentCollector $$3 = new ComponentCollector();
            ListIterator<LineComponent> $$4 = this.parts.listIterator();
            int $$5 = $$0;
            boolean $$6 = false;
            while (true) {
                if (!$$4.hasNext()) {
                    break;
                }
                LineComponent $$7 = $$4.next();
                String $$8 = $$7.contents;
                int $$9 = $$8.length();
                if (!$$6) {
                    if ($$5 > $$9) {
                        $$3.append($$7);
                        $$4.remove();
                        $$5 -= $$9;
                    } else {
                        String $$10 = $$8.substring(0, $$5);
                        if (!$$10.isEmpty()) {
                            $$3.append(FormattedText.of($$10, $$7.style));
                        }
                        $$5 += $$1;
                        $$6 = true;
                    }
                }
                if ($$6) {
                    if ($$5 > $$9) {
                        $$4.remove();
                        $$5 -= $$9;
                    } else {
                        String $$11 = $$8.substring($$5);
                        if ($$11.isEmpty()) {
                            $$4.remove();
                        } else {
                            $$4.set(new LineComponent($$11, $$2));
                        }
                    }
                }
            }
            this.flatParts = this.flatParts.substring($$0 + $$1);
            return $$3.getResultOrEmpty();
        }

        public FormattedText getRemainder() {
            ComponentCollector $$0 = new ComponentCollector();
            List<LineComponent> list = this.parts;
            Objects.requireNonNull($$0);
            list.forEach((v1) -> {
                r1.append(v1);
            });
            this.parts.clear();
            return $$0.getResult();
        }
    }

    public List<FormattedText> splitLines(FormattedText $$0, int $$1, Style $$2) {
        List<FormattedText> $$3 = Lists.newArrayList();
        splitLines($$0, $$1, $$2, ($$12, $$22) -> {
            $$3.add($$12);
        });
        return $$3;
    }

    public void splitLines(FormattedText $$0, int $$1, Style $$2, BiConsumer<FormattedText, Boolean> $$3) {
        List<LineComponent> $$4 = Lists.newArrayList();
        $$0.visit(($$12, $$22) -> {
            if (!$$22.isEmpty()) {
                $$4.add(new LineComponent($$22, $$12));
            }
            return Optional.empty();
        }, $$2);
        FlatComponents $$5 = new FlatComponents($$4);
        boolean $$6 = true;
        boolean $$7 = false;
        boolean $$8 = false;
        while ($$6) {
            $$6 = false;
            LineBreakFinder $$9 = new LineBreakFinder($$1);
            Iterator<LineComponent> it = $$5.parts.iterator();
            while (true) {
                if (it.hasNext()) {
                    LineComponent $$10 = it.next();
                    boolean $$11 = StringDecomposer.iterateFormatted($$10.contents, 0, $$10.style, $$2, $$9);
                    if (!$$11) {
                        int $$122 = $$9.getSplitPosition();
                        Style $$13 = $$9.getSplitStyle();
                        char $$14 = $$5.charAt($$122);
                        boolean $$15 = $$14 == '\n';
                        boolean $$16 = $$15 || $$14 == ' ';
                        $$7 = $$15;
                        FormattedText $$17 = $$5.splitAt($$122, $$16 ? 1 : 0, $$13);
                        $$3.accept($$17, Boolean.valueOf($$8));
                        $$8 = !$$15;
                        $$6 = true;
                    } else {
                        $$9.addToOffset($$10.contents.length());
                    }
                }
            }
        }
        FormattedText $$18 = $$5.getRemainder();
        if ($$18 != null) {
            $$3.accept($$18, Boolean.valueOf($$8));
        } else if ($$7) {
            $$3.accept(FormattedText.EMPTY, false);
        }
    }
}
