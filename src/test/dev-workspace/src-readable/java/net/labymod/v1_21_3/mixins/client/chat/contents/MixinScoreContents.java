package net.labymod.v1_21_3.mixins.client.chat.contents;

import com.mojang.datafixers.util.Either;
import net.labymod.v1_21_3.client.network.chat.contents.ScoreContentsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/client/chat/contents/MixinScoreContents.class */
@Mixin({zd.class})
public class MixinScoreContents implements ScoreContentsAccessor {

    @Shadow
    @Mutable
    @Final
    private Either<hn, String> d;

    @Shadow
    @Mutable
    @Final
    private String e;

    @Override // net.labymod.v1_21_3.client.network.chat.contents.ScoreContentsAccessor
    public void setName(String name) {
        this.d = Either.right(name);
    }

    @Override // net.labymod.v1_21_3.client.network.chat.contents.ScoreContentsAccessor
    public void setObjective(String objective) {
        this.e = objective;
    }
}
