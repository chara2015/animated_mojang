package net.labymod.v1_19_4.mixins.client.chat.contents;

import net.labymod.v1_19_4.client.network.chat.contents.LiteralContentsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/chat/contents/MixinLiteralContents.class */
@Mixin({uo.class})
public class MixinLiteralContents implements LiteralContentsAccessor {

    @Shadow
    @Mutable
    @Final
    private String b;

    @Override // net.labymod.v1_19_4.client.network.chat.contents.LiteralContentsAccessor
    public void setText(String text) {
        this.b = text;
    }
}
