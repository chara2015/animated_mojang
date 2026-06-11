package net.labymod.core.client.entity.player.tag;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.inject.Singleton;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.player.tag.renderer.PositionRenderer;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.laby3d.render.queue.ChannelType;
import net.labymod.api.laby3d.render.queue.ChannelTypes;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.models.Implements;
import net.labymod.api.util.collection.Lists;
import net.labymod.laby3d.api.resource.AssetId;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/tag/DefaultTagRegistry.class */
@Singleton
@Implements(TagRegistry.class)
public class DefaultTagRegistry implements TagRegistry {
    private static final AssetId UI_NAMETAG_RENDER_PASS = AssetId.of("labymod", "pass/ui/nametag");
    private final List<DefaultNameTagRegistry> positionRegistries = Lists.newArrayList();
    private final SubmissionCollector submissionCollector;
    private final RenderEnvironmentContext renderEnvironmentContext;

    public DefaultTagRegistry(SubmissionCollector submissionCollector, RenderEnvironmentContext renderEnvironmentContext) {
        this.renderEnvironmentContext = renderEnvironmentContext;
        this.submissionCollector = submissionCollector;
    }

    @Override // net.labymod.api.client.entity.player.tag.TagRegistry
    public void render(Stack stack, EntitySnapshot snapshot, float usernameWidth) {
        render(stack, snapshot, usernameWidth, TagType.CUSTOM);
    }

    @Override // net.labymod.api.client.entity.player.tag.TagRegistry
    public void render(Stack stack, EntitySnapshot snapshot, float usernameWidth, TagType tagType) {
        boolean screenContext = this.renderEnvironmentContext.isScreenContext();
        ChannelType type = screenContext ? ChannelTypes.UI : ChannelTypes.LEVEL;
        SubmissionCollector.SubmissionChannel channel = this.submissionCollector.channel(type);
        try {
            float offsetX = getRegistry(PositionType.LEFT_TO_NAME).getTotalOffsetX(snapshot) - getRegistry(PositionType.RIGHT_TO_NAME).getTotalOffsetX(snapshot);
            for (DefaultNameTagRegistry registry : this.positionRegistries) {
                PositionType positionType = registry.getPositionType();
                if (positionType.isHorizontal()) {
                    stack.translate(offsetX, 0.0f, 0.0f);
                }
                PositionRenderer renderer = positionType.renderer();
                renderer.render(stack, snapshot, this.submissionCollector, registry, usernameWidth, tagType);
                if (positionType.isHorizontal()) {
                    stack.translate(-offsetX, 0.0f, 0.0f);
                }
            }
            stack.translate(offsetX, 0.0f, 0.0f);
            if (channel != null) {
                channel.close();
            }
            if (screenContext) {
                this.submissionCollector.bus().channel(ChannelTypes.UI).render(UI_NAMETAG_RENDER_PASS);
            }
        } catch (Throwable th) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // net.labymod.api.client.entity.player.tag.TagRegistry
    public void register(String id, PositionType type, TagRenderer tag) {
        getRegistry(type).register(id, tag);
    }

    @Override // net.labymod.api.client.entity.player.tag.TagRegistry
    public void registerAfter(String afterId, String id, PositionType type, TagRenderer tag) {
        getRegistry(type).registerAfter(afterId, id, tag);
    }

    @Override // net.labymod.api.client.entity.player.tag.TagRegistry
    public void registerBefore(String beforeId, String id, PositionType type, TagRenderer tag) {
        getRegistry(type).registerBefore(beforeId, id, tag);
    }

    @Override // net.labymod.api.client.entity.player.tag.TagRegistry
    public void unregister(String id) {
        for (DefaultNameTagRegistry registry : this.positionRegistries) {
            registry.unregister(id);
        }
    }

    private DefaultNameTagRegistry getRegistry(PositionType type) {
        DefaultNameTagRegistry registry = null;
        Iterator<DefaultNameTagRegistry> it = this.positionRegistries.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            DefaultNameTagRegistry positionRegistry = it.next();
            if (positionRegistry.getPositionType().equals(type)) {
                registry = positionRegistry;
                break;
            }
        }
        if (registry == null) {
            List<DefaultNameTagRegistry> list = this.positionRegistries;
            DefaultNameTagRegistry defaultNameTagRegistry = new DefaultNameTagRegistry(type);
            registry = defaultNameTagRegistry;
            list.add(defaultNameTagRegistry);
            this.positionRegistries.sort(Comparator.comparingInt(value -> {
                return value.getPositionType().getPriority();
            }));
        }
        return registry;
    }
}
