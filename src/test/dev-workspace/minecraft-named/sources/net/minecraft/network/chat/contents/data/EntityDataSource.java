package net.minecraft.network.chat.contents.data;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.advancements.criterion.NbtPredicate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.SpawnData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/data/EntityDataSource.class */
public final class EntityDataSource extends Record implements DataSource {
    private final String selectorPattern;
    private final EntitySelector compiledSelector;
    public static final MapCodec<EntityDataSource> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.STRING.fieldOf(SpawnData.ENTITY_TAG).forGetter((v0) -> {
            return v0.selectorPattern();
        })).apply($$0, EntityDataSource::new);
    });

    public EntityDataSource(String $$0, EntitySelector $$1) {
        this.selectorPattern = $$0;
        this.compiledSelector = $$1;
    }

    public String selectorPattern() {
        return this.selectorPattern;
    }

    public EntitySelector compiledSelector() {
        return this.compiledSelector;
    }

    public EntityDataSource(String $$0) {
        this($$0, compileSelector($$0));
    }

    private static EntitySelector compileSelector(String $$0) {
        try {
            EntitySelectorParser $$1 = new EntitySelectorParser(new StringReader($$0), true);
            return $$1.parse();
        } catch (CommandSyntaxException e) {
            return null;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.mojang.brigadier.exceptions.CommandSyntaxException */
    @Override // net.minecraft.network.chat.contents.data.DataSource
    public Stream<CompoundTag> getData(CommandSourceStack $$0) throws CommandSyntaxException {
        if (this.compiledSelector != null) {
            List<? extends Entity> $$1 = this.compiledSelector.findEntities($$0);
            return $$1.stream().map(NbtPredicate::getEntityTagToCompare);
        }
        return Stream.empty();
    }

    @Override // net.minecraft.network.chat.contents.data.DataSource
    public MapCodec<EntityDataSource> codec() {
        return MAP_CODEC;
    }

    @Override // java.lang.Record
    public String toString() {
        return "entity=" + this.selectorPattern;
    }

    @Override // java.lang.Record
    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        if ($$0 instanceof EntityDataSource) {
            EntityDataSource $$1 = (EntityDataSource) $$0;
            if (this.selectorPattern.equals($$1.selectorPattern)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.lang.Record
    public int hashCode() {
        return this.selectorPattern.hashCode();
    }
}
