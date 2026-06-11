package net.labymod.v1_21_10.mixins.client.chat.contents;

import com.mojang.datafixers.util.Either;
import net.labymod.v1_21_10.client.network.chat.contents.ScoreContentsAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/chat/contents/MixinScoreContents.class */
@Mixin({ze.class})
public class MixinScoreContents implements ScoreContentsAccessor {

    @Shadow
    @Mutable
    @Final
    private Either<he, String> c;

    @Shadow
    @Mutable
    @Final
    private String d;

    @Override // net.labymod.v1_21_10.client.network.chat.contents.ScoreContentsAccessor
    public void setName(String name) {
        this.c = Either.right(name);
    }

    @Override // net.labymod.v1_21_10.client.network.chat.contents.ScoreContentsAccessor
    public void setObjective(String objective) {
        this.d = objective;
    }
}
