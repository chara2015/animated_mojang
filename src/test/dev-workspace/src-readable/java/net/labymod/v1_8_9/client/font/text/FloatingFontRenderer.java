package net.labymod.v1_8_9.client.font.text;

import java.util.Arrays;
import java.util.List;
import net.labymod.api.util.math.MathHelper;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/font/text/FloatingFontRenderer.class */
public abstract class FloatingFontRenderer extends avn {
    public abstract float getCharWidthFloat(char c);

    public abstract float getStringWidthFloat(String str);

    public FloatingFontRenderer(avh options, jy location, bmj textureManager, boolean useUnicode) {
        super(options, location, textureManager, useUnicode);
    }

    public int a(char c) {
        return MathHelper.ceil(getCharWidthFloat(c));
    }

    protected final int getOriginalCharWidth(char c) {
        return super.a(c);
    }

    public int a(String text) {
        if (text == null) {
            return 0;
        }
        return MathHelper.ceil(getStringWidthFloat(text));
    }

    protected final int getOriginalStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        return super.a(text);
    }

    public String a(String s, int maxWidth, boolean reverse) {
        return trimStringToWidthFloat(s, maxWidth, reverse);
    }

    public String trimStringToWidthFloat(String s, float maxWidth, boolean reverse) {
        StringBuilder var4 = new StringBuilder();
        float currentWidth = 0.0f;
        int var6 = reverse ? s.length() - 1 : 0;
        int var7 = reverse ? -1 : 1;
        boolean var8 = false;
        boolean var9 = false;
        int i = var6;
        while (true) {
            int var10 = i;
            if (var10 < 0 || var10 >= s.length() || currentWidth >= maxWidth) {
                break;
            }
            char var11 = s.charAt(var10);
            float var12 = getCharWidthFloat(var11);
            if (var8) {
                var8 = false;
                if (var11 != 'l' && var11 != 'L') {
                    if (var11 == 'r' || var11 == 'R') {
                        var9 = false;
                    }
                } else {
                    var9 = true;
                }
            } else if (var12 < 0.0f) {
                var8 = true;
            } else {
                currentWidth += var12;
                if (var9) {
                    currentWidth += 1.0f;
                }
            }
            if (currentWidth > maxWidth) {
                break;
            }
            if (reverse) {
                var4.insert(0, var11);
            } else {
                var4.append(var11);
            }
            i = var10 + var7;
        }
        return var4.toString();
    }

    public List<String> c(String text, int maxWidth) {
        return Arrays.asList(wrapFormattedStringToWidth(text, maxWidth).split("\n"));
    }

    String wrapFormattedStringToWidth(String text, float maxWidth) {
        if (maxWidth <= 0.0f || text.length() <= 1) {
            return text;
        }
        int width = sizeStringToWidth(text, maxWidth);
        if (text.length() <= width) {
            return text;
        }
        String textSection = text.substring(0, width);
        char lastChar = text.charAt(width);
        boolean spaceOrNewLine = lastChar == ' ' || lastChar == '\n';
        String formattedString = b(textSection) + text.substring(width + (spaceOrNewLine ? 1 : 0));
        return textSection + "\n" + wrapFormattedStringToWidth(formattedString, maxWidth);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:11:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int sizeStringToWidth(java.lang.String r5, float r6) {
        /*
            Method dump skipped, instruction units count: 227
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.v1_8_9.client.font.text.FloatingFontRenderer.sizeStringToWidth(java.lang.String, float):int");
    }

    private static boolean c(char p_isFormatColor_0_) {
        return (p_isFormatColor_0_ >= '0' && p_isFormatColor_0_ <= '9') || (p_isFormatColor_0_ >= 'a' && p_isFormatColor_0_ <= 'f') || (p_isFormatColor_0_ >= 'A' && p_isFormatColor_0_ <= 'F');
    }

    private static boolean d(char p_isFormatSpecial_0_) {
        return (p_isFormatSpecial_0_ >= 'k' && p_isFormatSpecial_0_ <= 'o') || (p_isFormatSpecial_0_ >= 'K' && p_isFormatSpecial_0_ <= 'O') || p_isFormatSpecial_0_ == 'r' || p_isFormatSpecial_0_ == 'R';
    }
}
