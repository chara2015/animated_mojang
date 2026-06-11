package net.labymod.core.client.render.font.component;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.chat.ChatSymbolRegistry;
import net.labymod.api.client.render.font.TextColorStripper;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/font/component/DefaultTextColorStripper.class */
@Singleton
@Implements(TextColorStripper.class)
public class DefaultTextColorStripper implements TextColorStripper {
    private static final char DEFAULT_COLOR_CHAR = 167;
    private final ChatSymbolRegistry chatSymbolRegistry;

    @Inject
    public DefaultTextColorStripper(ChatSymbolRegistry chatSymbolRegistry) {
        this.chatSymbolRegistry = chatSymbolRegistry;
    }

    @Override // net.labymod.api.client.render.font.TextColorStripper
    public String stripColorCodes(String text) {
        return stripColorCodes(text, (char) 167);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004b  */
    @Override // net.labymod.api.client.render.font.TextColorStripper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String stripColorCodes(java.lang.String r5, char r6) {
        /*
            r4 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = r0
            r1.<init>()
            r7 = r0
            r0 = 0
            r8 = r0
        Lb:
            r0 = r8
            r1 = r5
            int r1 = r1.length()
            if (r0 >= r1) goto L58
            r0 = r5
            r1 = r8
            char r0 = r0.charAt(r1)
            r9 = r0
            r0 = r9
            r1 = r6
            if (r0 != r1) goto L4b
            r0 = r8
            r1 = r5
            int r1 = r1.length()
            r2 = 1
            int r1 = r1 - r2
            if (r0 >= r1) goto L4b
            r0 = r5
            r1 = r8
            r2 = 1
            int r1 = r1 + r2
            char r0 = r0.charAt(r1)
            r10 = r0
            r0 = r4
            net.labymod.api.client.chat.ChatSymbolRegistry r0 = r0.chatSymbolRegistry
            r1 = r10
            net.labymod.api.client.component.format.Style r0 = r0.getStyle(r1)
            if (r0 == 0) goto L4b
            int r8 = r8 + 1
            goto L52
        L4b:
            r0 = r7
            r1 = r9
            java.lang.StringBuilder r0 = r0.append(r1)
        L52:
            int r8 = r8 + 1
            goto Lb
        L58:
            r0 = r7
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.core.client.render.font.component.DefaultTextColorStripper.stripColorCodes(java.lang.String, char):java.lang.String");
    }
}
