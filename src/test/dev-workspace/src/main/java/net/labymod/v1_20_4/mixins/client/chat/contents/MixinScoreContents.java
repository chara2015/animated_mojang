package net.labymod.v1_20_4.mixins.client.chat.contents;

import net.labymod.v1_20_4.client.network.chat.contents.ScoreContentsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/client/chat/contents/MixinScoreContents.class */
@Mixin({wn.class})
public class MixinScoreContents implements ScoreContentsAccessor {

    @Shadow
    @Mutable
    @Final
    private String d;

    @Shadow
    @Mutable
    @Final
    private String f;

    @Override // net.labymod.v1_20_4.client.network.chat.contents.ScoreContentsAccessor
    public void setName(String name) {
        this.d = name;
    }

    @Override // net.labymod.v1_20_4.client.network.chat.contents.ScoreContentsAccessor
    public void setObjective(String objective) {
        this.f = objective;
    }
}
