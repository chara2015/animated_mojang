package net.minecraft.client.data.models.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/data/models/model/ModelTemplate.class */
public class ModelTemplate {
    private final Optional<Identifier> model;
    private final Set<TextureSlot> requiredSlots;
    private final Optional<String> suffix;

    public ModelTemplate(Optional<Identifier> $$0, Optional<String> $$1, TextureSlot... $$2) {
        this.model = $$0;
        this.suffix = $$1;
        this.requiredSlots = ImmutableSet.copyOf($$2);
    }

    public Identifier getDefaultModelLocation(Block $$0) {
        return ModelLocationUtils.getModelLocation($$0, this.suffix.orElse(""));
    }

    public Identifier create(Block $$0, TextureMapping $$1, BiConsumer<Identifier, ModelInstance> $$2) {
        return create(ModelLocationUtils.getModelLocation($$0, this.suffix.orElse("")), $$1, $$2);
    }

    public Identifier createWithSuffix(Block $$0, String $$1, TextureMapping $$2, BiConsumer<Identifier, ModelInstance> $$3) {
        return create(ModelLocationUtils.getModelLocation($$0, $$1 + this.suffix.orElse("")), $$2, $$3);
    }

    public Identifier createWithOverride(Block $$0, String $$1, TextureMapping $$2, BiConsumer<Identifier, ModelInstance> $$3) {
        return create(ModelLocationUtils.getModelLocation($$0, $$1), $$2, $$3);
    }

    public Identifier create(Item $$0, TextureMapping $$1, BiConsumer<Identifier, ModelInstance> $$2) {
        return create(ModelLocationUtils.getModelLocation($$0, this.suffix.orElse("")), $$1, $$2);
    }

    public Identifier create(Identifier $$0, TextureMapping $$1, BiConsumer<Identifier, ModelInstance> $$2) {
        Map<TextureSlot, Identifier> $$3 = createMap($$1);
        $$2.accept($$0, () -> {
            JsonObject $$12 = new JsonObject();
            this.model.ifPresent($$13 -> {
                $$12.addProperty("parent", $$13.toString());
            });
            if (!$$3.isEmpty()) {
                JsonObject $$22 = new JsonObject();
                $$3.forEach(($$14, $$23) -> {
                    $$22.addProperty($$14.getId(), $$23.toString());
                });
                $$12.add("textures", $$22);
            }
            return $$12;
        });
        return $$0;
    }

    private Map<TextureSlot, Identifier> createMap(TextureMapping $$0) {
        Stream streamConcat = Streams.concat(new Stream[]{this.requiredSlots.stream(), $$0.getForced()});
        Function functionIdentity = Function.identity();
        Objects.requireNonNull($$0);
        return (Map) streamConcat.collect(ImmutableMap.toImmutableMap(functionIdentity, $$0::get));
    }
}
