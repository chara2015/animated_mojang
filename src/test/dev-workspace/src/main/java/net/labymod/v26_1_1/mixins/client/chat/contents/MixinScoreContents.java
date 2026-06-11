package net.labymod.v26_1_1.mixins.client.chat.contents;

import com.mojang.datafixers.util.Either;
import net.labymod.v26_1_1.client.network.chat.contents.ScoreContentsAccessor;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.contents.ScoreContents;
import net.minecraft.util.CompilableString;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/chat/contents/MixinScoreContents.class */
@Mixin({ScoreContents.class})
public class MixinScoreContents implements ScoreContentsAccessor {

    @Shadow
    @Mutable
    @Final
    private Either<CompilableString<EntitySelector>, String> name;

    @Shadow
    @Mutable
    @Final
    private String objective;

    @Override // net.labymod.v26_1_1.client.network.chat.contents.ScoreContentsAccessor
    public void setName(String name) {
        this.name = Either.right(name);
    }

    @Override // net.labymod.v26_1_1.client.network.chat.contents.ScoreContentsAccessor
    public void setObjective(String objective) {
        this.objective = objective;
    }
}
