package net.labymod.api.event.laby3d;

import java.util.function.Function;
import net.labymod.api.event.Event;
import net.labymod.api.event.ReplayableEvent;
import net.labymod.laby3d.api.shaders.block.UniformBlock;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/laby3d/UniformBlockRegistrationEvent.class */
@ReplayableEvent
public class UniformBlockRegistrationEvent implements Event {
    private final Function<UniformBlock, UniformBlock> uniformBlockRegistrationCallback;

    public UniformBlockRegistrationEvent(Function<UniformBlock, UniformBlock> uniformBlockRegistrationCallback) {
        this.uniformBlockRegistrationCallback = uniformBlockRegistrationCallback;
    }

    public <T extends UniformBlock> T registerUniformBlock(T uniformBlock) {
        return (T) this.uniformBlockRegistrationCallback.apply(uniformBlock);
    }
}
