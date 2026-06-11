package net.minecraft.network.chat;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ComponentContents.class */
public interface ComponentContents {
    MapCodec<? extends ComponentContents> codec();

    default <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
        return Optional.empty();
    }

    default <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
        return Optional.empty();
    }

    default MutableComponent resolve(CommandSourceStack $$0, Entity $$1, int $$2) throws CommandSyntaxException {
        return MutableComponent.create(this);
    }
}
