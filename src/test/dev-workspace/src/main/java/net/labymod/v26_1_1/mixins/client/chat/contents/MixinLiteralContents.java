package net.labymod.v26_1_1.mixins.client.chat.contents;

import net.labymod.v26_1_1.client.network.chat.contents.LiteralContentsAccessor;
import net.minecraft.network.chat.contents.PlainTextContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/chat/contents/MixinLiteralContents.class */
@Mixin({PlainTextContents.LiteralContents.class})
public class MixinLiteralContents implements LiteralContentsAccessor {

    @Shadow
    @Mutable
    @Final
    private String text;

    @Override // net.labymod.v26_1_1.client.network.chat.contents.LiteralContentsAccessor
    public void setText(String text) {
        this.text = text;
    }
}
