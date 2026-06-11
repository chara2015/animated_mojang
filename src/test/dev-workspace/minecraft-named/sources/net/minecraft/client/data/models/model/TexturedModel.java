package net.minecraft.client.data.models.model;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/data/models/model/TexturedModel.class */
public class TexturedModel {
    public static final Provider CUBE = createDefault(TextureMapping::cube, ModelTemplates.CUBE_ALL);
    public static final Provider CUBE_INNER_FACES = createDefault(TextureMapping::cube, ModelTemplates.CUBE_ALL_INNER_FACES);
    public static final Provider CUBE_MIRRORED = createDefault(TextureMapping::cube, ModelTemplates.CUBE_MIRRORED_ALL);
    public static final Provider COLUMN = createDefault(TextureMapping::column, ModelTemplates.CUBE_COLUMN);
    public static final Provider COLUMN_HORIZONTAL = createDefault(TextureMapping::column, ModelTemplates.CUBE_COLUMN_HORIZONTAL);
    public static final Provider CUBE_TOP_BOTTOM = createDefault(TextureMapping::cubeBottomTop, ModelTemplates.CUBE_BOTTOM_TOP);
    public static final Provider CUBE_TOP = createDefault(TextureMapping::cubeTop, ModelTemplates.CUBE_TOP);
    public static final Provider ORIENTABLE_ONLY_TOP = createDefault(TextureMapping::orientableCubeOnlyTop, ModelTemplates.CUBE_ORIENTABLE);
    public static final Provider ORIENTABLE = createDefault(TextureMapping::orientableCube, ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM);
    public static final Provider CARPET = createDefault(TextureMapping::wool, ModelTemplates.CARPET);
    public static final Provider MOSSY_CARPET_SIDE = createDefault(TextureMapping::side, ModelTemplates.MOSSY_CARPET_SIDE);
    public static final Provider FLOWERBED_1 = createDefault(TextureMapping::flowerbed, ModelTemplates.FLOWERBED_1);
    public static final Provider FLOWERBED_2 = createDefault(TextureMapping::flowerbed, ModelTemplates.FLOWERBED_2);
    public static final Provider FLOWERBED_3 = createDefault(TextureMapping::flowerbed, ModelTemplates.FLOWERBED_3);
    public static final Provider FLOWERBED_4 = createDefault(TextureMapping::flowerbed, ModelTemplates.FLOWERBED_4);
    public static final Provider LEAF_LITTER_1 = createDefault(TextureMapping::defaultTexture, ModelTemplates.LEAF_LITTER_1);
    public static final Provider LEAF_LITTER_2 = createDefault(TextureMapping::defaultTexture, ModelTemplates.LEAF_LITTER_2);
    public static final Provider LEAF_LITTER_3 = createDefault(TextureMapping::defaultTexture, ModelTemplates.LEAF_LITTER_3);
    public static final Provider LEAF_LITTER_4 = createDefault(TextureMapping::defaultTexture, ModelTemplates.LEAF_LITTER_4);
    public static final Provider GLAZED_TERRACOTTA = createDefault(TextureMapping::pattern, ModelTemplates.GLAZED_TERRACOTTA);
    public static final Provider CORAL_FAN = createDefault(TextureMapping::fan, ModelTemplates.CORAL_FAN);
    public static final Provider ANVIL = createDefault(TextureMapping::top, ModelTemplates.ANVIL);
    public static final Provider LEAVES = createDefault(TextureMapping::cube, ModelTemplates.LEAVES);
    public static final Provider LANTERN = createDefault(TextureMapping::lantern, ModelTemplates.LANTERN);
    public static final Provider HANGING_LANTERN = createDefault(TextureMapping::lantern, ModelTemplates.HANGING_LANTERN);
    public static final Provider CHAIN = createDefault(TextureMapping::defaultTexture, ModelTemplates.CHAIN);
    public static final Provider SEAGRASS = createDefault(TextureMapping::defaultTexture, ModelTemplates.SEAGRASS);
    public static final Provider COLUMN_ALT = createDefault(TextureMapping::logColumn, ModelTemplates.CUBE_COLUMN);
    public static final Provider COLUMN_HORIZONTAL_ALT = createDefault(TextureMapping::logColumn, ModelTemplates.CUBE_COLUMN_HORIZONTAL);
    public static final Provider TOP_BOTTOM_WITH_WALL = createDefault(TextureMapping::cubeBottomTopWithWall, ModelTemplates.CUBE_BOTTOM_TOP);
    public static final Provider COLUMN_WITH_WALL = createDefault(TextureMapping::columnWithWall, ModelTemplates.CUBE_COLUMN);
    private final TextureMapping mapping;
    private final ModelTemplate template;

    private TexturedModel(TextureMapping $$0, ModelTemplate $$1) {
        this.mapping = $$0;
        this.template = $$1;
    }

    public ModelTemplate getTemplate() {
        return this.template;
    }

    public TextureMapping getMapping() {
        return this.mapping;
    }

    public TexturedModel updateTextures(Consumer<TextureMapping> $$0) {
        $$0.accept(this.mapping);
        return this;
    }

    public Identifier create(Block $$0, BiConsumer<Identifier, ModelInstance> $$1) {
        return this.template.create($$0, this.mapping, $$1);
    }

    public Identifier createWithSuffix(Block $$0, String $$1, BiConsumer<Identifier, ModelInstance> $$2) {
        return this.template.createWithSuffix($$0, $$1, this.mapping, $$2);
    }

    private static Provider createDefault(Function<Block, TextureMapping> $$0, ModelTemplate $$1) {
        return $$2 -> {
            return new TexturedModel((TextureMapping) $$0.apply($$2), $$1);
        };
    }

    public static TexturedModel createAllSame(Identifier $$0) {
        return new TexturedModel(TextureMapping.cube($$0), ModelTemplates.CUBE_ALL);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/data/models/model/TexturedModel$Provider.class */
    @FunctionalInterface
    public interface Provider {
        TexturedModel get(Block block);

        default Identifier create(Block $$0, BiConsumer<Identifier, ModelInstance> $$1) {
            return get($$0).create($$0, $$1);
        }

        default Identifier createWithSuffix(Block $$0, String $$1, BiConsumer<Identifier, ModelInstance> $$2) {
            return get($$0).createWithSuffix($$0, $$1, $$2);
        }

        default Provider updateTexture(Consumer<TextureMapping> $$0) {
            return $$1 -> {
                return get($$1).updateTextures($$0);
            };
        }
    }
}
