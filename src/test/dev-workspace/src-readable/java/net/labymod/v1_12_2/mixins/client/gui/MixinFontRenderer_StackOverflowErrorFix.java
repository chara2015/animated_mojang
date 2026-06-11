package net.labymod.v1_12_2.mixins.client.gui;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/gui/MixinFontRenderer_StackOverflowErrorFix.class */
@Mixin({bip.class})
public abstract class MixinFontRenderer_StackOverflowErrorFix {
    @Shadow
    protected abstract int e(String str, int i);

    @Shadow
    public static String b(String text) {
        return null;
    }

    @Overwrite
    String d(String str, int wrapWidth) {
        if (wrapWidth <= 0 || str.length() <= 1) {
            return str;
        }
        StringBuilder out = new StringBuilder();
        String remaining = str;
        int safety = 0;
        while (true) {
            if (!remaining.isEmpty()) {
                int i = safety;
                safety++;
                if (i >= 100000) {
                    break;
                }
                int width = e(remaining, wrapWidth);
                if (width <= 0) {
                    width = Math.min(1, remaining.length());
                }
                if (remaining.length() <= width) {
                    out.append(remaining);
                    break;
                }
                String head = remaining.substring(0, width);
                char cutChar = remaining.charAt(width);
                boolean cutOnBoundary = cutChar == ' ' || cutChar == '\n';
                String carryFormats = b(head);
                int nextStart = width + (cutOnBoundary ? 1 : 0);
                if (nextStart >= remaining.length()) {
                    out.append(head);
                    break;
                }
                remaining = carryFormats + remaining.substring(nextStart);
                out.append(head).append("\n");
            } else {
                break;
            }
        }
        return out.toString();
    }
}
