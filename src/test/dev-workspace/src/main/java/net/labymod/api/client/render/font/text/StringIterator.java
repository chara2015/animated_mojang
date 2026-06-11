package net.labymod.api.client.render.font.text;

import java.util.Iterator;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.adventure.LegacyChatFormatting;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/font/text/StringIterator.class */
public class StringIterator {
    private static final LegacyChatFormatting LEGACY_CHAT_FORMATTING = Laby.references().legacyChatFormatting();
    private static final char LEGACY_COLOR_CODE_PREFIX = 167;
    private static final int REPLACEMENT_CHAR = 65533;

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a1, code lost:
    
        r8.finish();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00a8, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean iterate(java.lang.String r6, net.labymod.api.client.component.format.Style r7, net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor r8) {
        /*
            r0 = r6
            int r0 = r0.length()
            r9 = r0
            r0 = 0
            r10 = r0
        L8:
            r0 = r10
            r1 = r9
            if (r0 >= r1) goto La1
            r0 = r6
            r1 = r10
            char r0 = r0.charAt(r1)
            r11 = r0
            r0 = r11
            boolean r0 = java.lang.Character.isHighSurrogate(r0)
            if (r0 == 0) goto L87
            r0 = r10
            r1 = 1
            int r0 = r0 + r1
            r1 = r9
            if (r0 < r1) goto L3c
            r0 = r8
            r1 = r10
            r2 = r7
            r3 = 65533(0xfffd, float:9.1831E-41)
            boolean r0 = r0.accept(r1, r2, r3)
            if (r0 != 0) goto La1
            r0 = r8
            r0.finish()
            r0 = 0
            return r0
        L3c:
            r0 = r6
            r1 = r10
            r2 = 1
            int r1 = r1 + r2
            char r0 = r0.charAt(r1)
            r12 = r0
            r0 = r12
            boolean r0 = java.lang.Character.isLowSurrogate(r0)
            if (r0 == 0) goto L6f
            r0 = r8
            r1 = r10
            r2 = r7
            r3 = r11
            r4 = r12
            int r3 = java.lang.Character.toCodePoint(r3, r4)
            boolean r0 = r0.accept(r1, r2, r3)
            if (r0 != 0) goto L69
            r0 = r8
            r0.finish()
            r0 = 0
            return r0
        L69:
            int r10 = r10 + 1
            goto L84
        L6f:
            r0 = r8
            r1 = r9
            r2 = r7
            r3 = 65533(0xfffd, float:9.1831E-41)
            boolean r0 = r0.accept(r1, r2, r3)
            if (r0 != 0) goto L84
            r0 = r8
            r0.finish()
            r0 = 0
            return r0
        L84:
            goto L9b
        L87:
            r0 = r7
            r1 = r8
            r2 = r10
            r3 = r11
            boolean r0 = feedConsumer(r0, r1, r2, r3)
            if (r0 != 0) goto L9b
            r0 = r8
            r0.finish()
            r0 = 0
            return r0
        L9b:
            int r10 = r10 + 1
            goto L8
        La1:
            r0 = r8
            r0.finish()
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.api.client.render.font.text.StringIterator.iterate(java.lang.String, net.labymod.api.client.component.format.Style, net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor):boolean");
    }

    public static boolean iterateFormatted(String text, Style style, boolean capitalize, GlyphProcessor processor) {
        return iterateFormatted(text, 0, style, capitalize, processor);
    }

    public static boolean iterateFormatted(String text, int start, Style style, boolean capitalize, GlyphProcessor processor) {
        return iterateFormatted(text, start, style, style, capitalize, processor);
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0108, code lost:
    
        r12.finish();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0110, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean iterateFormatted(java.lang.String r7, int r8, net.labymod.api.client.component.format.Style r9, net.labymod.api.client.component.format.Style r10, boolean r11, net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor r12) {
        /*
            Method dump skipped, instruction units count: 273
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.api.client.render.font.text.StringIterator.iterateFormatted(java.lang.String, int, net.labymod.api.client.component.format.Style, net.labymod.api.client.component.format.Style, boolean, net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor):boolean");
    }

    private static char shouldBeUpperCase(boolean capitalize, char ch) {
        return capitalize ? Character.toUpperCase(ch) : ch;
    }

    private static int shouldBeUpperCase(boolean capitalize, int codePoint) {
        return capitalize ? Character.toUpperCase(codePoint) : codePoint;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x009e, code lost:
    
        r8.finish();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00a5, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean iterateBackwards(java.lang.String r6, net.labymod.api.client.component.format.Style r7, net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor r8) {
        /*
            r0 = r6
            int r0 = r0.length()
            r9 = r0
            r0 = r9
            r1 = 1
            int r0 = r0 - r1
            r10 = r0
        La:
            r0 = r10
            if (r0 < 0) goto L9e
            r0 = r6
            r1 = r10
            char r0 = r0.charAt(r1)
            r11 = r0
            r0 = r11
            boolean r0 = java.lang.Character.isLowSurrogate(r0)
            if (r0 == 0) goto L84
            r0 = r10
            r1 = 1
            int r0 = r0 - r1
            if (r0 >= 0) goto L3b
            r0 = r8
            r1 = 0
            r2 = r7
            r3 = 65533(0xfffd, float:9.1831E-41)
            boolean r0 = r0.accept(r1, r2, r3)
            if (r0 != 0) goto L9e
            r0 = r8
            r0.finish()
            r0 = 0
            return r0
        L3b:
            r0 = r6
            r1 = r10
            r2 = 1
            int r1 = r1 - r2
            char r0 = r0.charAt(r1)
            r12 = r0
            r0 = r12
            boolean r0 = java.lang.Character.isHighSurrogate(r0)
            if (r0 == 0) goto L6b
            int r10 = r10 + (-1)
            r0 = r8
            r1 = r10
            r2 = r7
            r3 = r12
            r4 = r11
            int r3 = java.lang.Character.toCodePoint(r3, r4)
            boolean r0 = r0.accept(r1, r2, r3)
            if (r0 != 0) goto L81
            r0 = r8
            r0.finish()
            r0 = 0
            return r0
        L6b:
            r0 = r8
            r1 = r10
            r2 = r7
            r3 = 65533(0xfffd, float:9.1831E-41)
            boolean r0 = r0.accept(r1, r2, r3)
            if (r0 != 0) goto L81
            r0 = r8
            r0.finish()
            r0 = 0
            return r0
        L81:
            goto L98
        L84:
            r0 = r7
            r1 = r8
            r2 = r10
            r3 = r11
            boolean r0 = feedConsumer(r0, r1, r2, r3)
            if (r0 != 0) goto L98
            r0 = r8
            r0.finish()
            r0 = 0
            return r0
        L98:
            int r10 = r10 + (-1)
            goto La
        L9e:
            r0 = r8
            r0.finish()
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.api.client.render.font.text.StringIterator.iterateBackwards(java.lang.String, net.labymod.api.client.component.format.Style, net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor):boolean");
    }

    private static boolean feedConsumer(Style style, GlyphProcessor processor, int glyphPosition, char glyph) {
        if (Character.isSurrogate(glyph)) {
            return processor.accept(glyphPosition, style, REPLACEMENT_CHAR);
        }
        return processor.accept(glyphPosition, style, glyph);
    }

    private static Style applyLegacyFormat(Style source, Style other) {
        if (other.isEmpty()) {
            return Style.EMPTY;
        }
        List<TextDecoration> values = TextDecoration.getValues();
        int size = values.size();
        int count = 0;
        for (TextDecoration decoration : values) {
            if (!other.hasDecoration(decoration)) {
                count++;
            } else {
                source = source.decorate(decoration);
            }
        }
        if (count == size) {
            Iterator<TextDecoration> it = values.iterator();
            while (it.hasNext()) {
                source = source.decoration(it.next(), false);
            }
        }
        if (other.getColor() != null) {
            source = source.color(other.getColor());
        }
        if (other.getShadowColor() != null) {
            source = source.shadowColor(other.getShadowColor().intValue());
        }
        return source;
    }
}
