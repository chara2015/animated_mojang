package net.minecraft.commands.arguments;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.level.block.Mirror;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/arguments/TemplateMirrorArgument.class */
public class TemplateMirrorArgument extends StringRepresentableArgument<Mirror> {
    private TemplateMirrorArgument() {
        super(Mirror.CODEC, Mirror::values);
    }

    public static StringRepresentableArgument<Mirror> templateMirror() {
        return new TemplateMirrorArgument();
    }

    public static Mirror getMirror(CommandContext<CommandSourceStack> $$0, String $$1) {
        return (Mirror) $$0.getArgument($$1, Mirror.class);
    }
}
