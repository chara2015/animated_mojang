package net.labymod.v1_21_8.mixins.client.chat.contents;

import net.labymod.v1_21_8.client.network.chat.contents.LiteralContentsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/mixins/client/chat/contents/MixinLiteralContents.class */
@Mixin({a.class})
public class MixinLiteralContents implements LiteralContentsAccessor {

    @Shadow
    @Mutable
    @Final
    private String d;

    @Override // net.labymod.v1_21_8.client.network.chat.contents.LiteralContentsAccessor
    public void setText(String text) {
        this.d = text;
    }
}
