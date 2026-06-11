package net.labymod.v1_8_9.mixins.client.gui;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfoReturnable;
import net.labymod.v1_8_9.client.font.text.FloatingFontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/gui/MixinFloatingGuiUtilRenderComponents.class */
@Mixin({avu.class})
public class MixinFloatingGuiUtilRenderComponents {
    @Shadow
    public static String a(String p_178909_0_, boolean p_178909_1_) {
        return null;
    }

    @Insert(method = {"splitText"}, at = @At("HEAD"), cancellable = true)
    private static void labyMod$splitTextFloat(eu rawText, int maxWidth, avn renderer, boolean removeLeadingSpaces, boolean p_splitText_4_, InsertInfoReturnable<List<eu>> callback) {
        if (!(renderer instanceof FloatingFontRenderer)) {
            return;
        }
        FloatingFontRenderer floatingRenderer = (FloatingFontRenderer) renderer;
        float var5 = 0.0f;
        fa var6 = new fa("");
        ArrayList var7 = Lists.newArrayList();
        ArrayList var8 = Lists.newArrayList(rawText);
        for (int var9 = 0; var9 < var8.size(); var9++) {
            eu var10 = (eu) var8.get(var9);
            String var11 = var10.e();
            boolean var12 = false;
            if (var11.contains("\n")) {
                int var13 = var11.indexOf(10);
                String var14 = var11.substring(var13 + 1);
                var11 = var11.substring(0, var13 + 1);
                fa var15 = new fa(var14);
                var15.a(var10.b().m());
                var8.add(var9 + 1, var15);
                var12 = true;
            }
            String var21 = a(var10.b().k() + var11, p_splitText_4_);
            String var142 = var21.endsWith("\n") ? var21.substring(0, var21.length() - 1) : var21;
            float var22 = floatingRenderer.getStringWidthFloat(var142);
            fa var16 = new fa(var142);
            var16.a(var10.b().m());
            if (var5 + var22 > maxWidth) {
                String var17 = floatingRenderer.trimStringToWidthFloat(var21, maxWidth - var5, false);
                String var18 = var17.length() < var21.length() ? var21.substring(var17.length()) : null;
                if (var18 != null && var18.length() > 0) {
                    int var19 = var17.lastIndexOf(" ");
                    if (var19 >= 0 && floatingRenderer.getStringWidthFloat(var21.substring(0, var19)) > 0.0f) {
                        var17 = var21.substring(0, var19);
                        if (removeLeadingSpaces) {
                            var19++;
                        }
                        var18 = var21.substring(var19);
                    } else if (var5 > 0.0f && !var21.contains(" ")) {
                        var17 = "";
                        var18 = var21;
                    }
                    fa var20 = new fa(var18);
                    var20.a(var10.b().m());
                    var8.add(var9 + 1, var20);
                }
                var22 = floatingRenderer.getStringWidthFloat(var17);
                var16 = new fa(var17);
                var16.a(var10.b().m());
                var12 = true;
            }
            if (var5 + var22 <= maxWidth) {
                var5 += var22;
                var6.a(var16);
            } else {
                var12 = true;
            }
            if (var12) {
                var7.add(var6);
                var5 = 0.0f;
                var6 = new fa("");
            }
        }
        var7.add(var6);
        callback.setReturnValue(var7);
    }
}
