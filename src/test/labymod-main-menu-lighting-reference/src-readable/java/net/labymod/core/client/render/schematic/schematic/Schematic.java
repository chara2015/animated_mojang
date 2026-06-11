package net.labymod.core.client.render.schematic;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig;
import net.labymod.api.nbt.NBTFactory;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.nbt.tags.NBTTagInt;
import net.labymod.api.nbt.tags.NBTTagList;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import net.labymod.core.client.render.schematic.block.material.material.Material;
import net.labymod.core.client.render.schematic.lighting.legacy.ColoredLegacyLightEngine;
import net.labymod.core.client.render.schematic.lighting.legacy.DefaultLegacyLightEngine;
import net.labymod.core.client.render.schematic.lighting.legacy.LegacyLightEngine;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/Schematic.class */
public class Schematic implements SchematicAccessor {
    private final Logging logger;
    private final int paletteMax;
    private final int version;
    private final int dataVersion;
    private final short width;
    private final short height;
    private final short length;
    private final Int2ObjectMap<Block> palette;
    private final List<HashMap<String, NBTTag<?>>> blockEntities;
    private final byte[] blocks;
    private final int[] offset;
    private final Block[] blockCache;
    private final Block airBlock;
    private final Boolean[] translucentCache;
    private final Boolean[] fullBlockCache;
    private final Boolean[] isLightSourceCache;
    private final ColoredLegacyLightEngine legacyColoredLightEngine;
    private final LegacyLightEngine legacyDefaultLightEngine;
    private final int chunksInWidth;
    private final int chunksInHeight;
    private final int chunksInLength;
    private WEOffset weOffsetX;
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Schematic.class.desiredAssertionStatus();
    }

    public Schematic(ResourceLocation resourceLocation) throws IOException {
        this(resourceLocation.openStream());
    }

    public Schematic(Path path) throws IOException {
        this(IOUtil.newInputStream(path));
    }

    public Schematic(InputStream inputStream) throws IOException {
        this.logger = Logging.getLogger();
        this.palette = new Int2ObjectOpenHashMap();
        NBTTagCompound root = Laby.references().nbtFactory().readCompressed(inputStream);
        Map<String, NBTTag<?>> items = root.value();
        if (items == null) {
            throw new IOException("Schematic file is empty");
        }
        this.paletteMax = ((Integer) items.get("PaletteMax").value()).intValue();
        this.version = ((Integer) items.get("Version").value()).intValue();
        this.dataVersion = ((Integer) items.get("DataVersion").value()).intValue();
        this.offset = (int[]) items.get("Offset").value();
        Map<String, NBTTagInt> metadata = (Map) items.get("Metadata").value();
        if (metadata != null) {
            NBTTagInt xTag = metadata.get("WEOffsetX");
            NBTTagInt yTag = metadata.get("WEOffsetY");
            NBTTagInt zTag = metadata.get("WEOffsetZ");
            if (xTag != null && yTag != null && zTag != null) {
                Integer x = xTag.value();
                Integer y = yTag.value();
                Integer z = zTag.value();
                if (x != null && y != null && z != null) {
                    this.weOffsetX = new WEOffset(x.intValue(), y.intValue(), z.intValue());
                }
            }
        }
        this.width = ((Short) items.get("Width").value()).shortValue();
        this.height = ((Short) items.get("Height").value()).shortValue();
        this.length = ((Short) items.get("Length").value()).shortValue();
        Map<String, NBTTagInt> palette = (Map) items.get("Palette").value();
        if (palette != null) {
            for (Map.Entry<String, NBTTagInt> entry : palette.entrySet()) {
                String[] args = entry.getKey().split(":", 2);
                String namespace = args[0];
                String id = args[1];
                Integer index = entry.getValue().value();
                if (index != null) {
                    this.palette.put(index.intValue(), new Block(namespace, id));
                }
            }
        }
        this.blocks = (byte[]) items.get("BlockData").value();
        if (!$assertionsDisabled && this.blocks == null) {
            throw new AssertionError();
        }
        this.blockEntities = (List) items.get("BlockEntities").value();
        this.blockCache = new Block[this.blocks.length];
        this.translucentCache = new Boolean[this.blocks.length];
        this.fullBlockCache = new Boolean[this.blocks.length];
        this.isLightSourceCache = new Boolean[this.blocks.length];
        this.airBlock = new Block(Namespaces.MINECRAFT, MaterialRegistry.AIR);
        this.legacyColoredLightEngine = new ColoredLegacyLightEngine(this, this.width, this.height, this.length, 1, 1, 1);
        this.legacyDefaultLightEngine = new DefaultLegacyLightEngine(this, this.width, this.height, this.length, 1);
        NBTTag<?> blockLight = root.get("BlockLight");
        if (blockLight != null) {
            this.legacyDefaultLightEngine.setData((byte[]) blockLight.value());
        } else {
            this.logger.warn("Schematic file does not contain BlockLight. Calculate it and save it using Schematic#saveTo(Path)", new Object[0]);
        }
        NBTTag<?> coloredBlockRed = root.get("BlockLightRed");
        NBTTag<?> coloredBlockGreen = root.get("BlockLightGreen");
        NBTTag<?> coloredBlockBlue = root.get("BlockLightBlue");
        if (coloredBlockRed != null && coloredBlockGreen != null && coloredBlockBlue != null) {
            byte[] red = (byte[]) coloredBlockRed.value();
            byte[] green = (byte[]) coloredBlockGreen.value();
            byte[] blue = (byte[]) coloredBlockBlue.value();
            if (red != null && green != null && blue != null) {
                this.legacyColoredLightEngine.getRedEngine().setData(red);
                this.legacyColoredLightEngine.getGreenEngine().setData(green);
                this.legacyColoredLightEngine.getBlueEngine().setData(blue);
            }
        } else {
            this.logger.warn("Schematic file does not contain colored BlockLight. Calculate it and save it using Schematic#saveTo(Path)", new Object[0]);
        }
        this.chunksInWidth = MathHelper.ceil(this.width / 16.0f);
        this.chunksInHeight = MathHelper.ceil(this.height / 16.0f);
        this.chunksInLength = MathHelper.ceil(this.length / 16.0f);
        inputStream.close();
    }

    public void saveTo(Path path) throws IOException {
        NBTFactory factory = Laby.references().nbtFactory();
        NBTTagCompound root = factory.compound();
        if (IOUtil.exists(path)) {
            IOUtil.delete(path);
        }
        root.set("PaletteMax", factory.create(this.paletteMax));
        root.set("Version", factory.create(this.version));
        root.set("DataVersion", factory.create(this.dataVersion));
        root.set("Offset", factory.create(this.offset));
        root.set("Width", factory.create(this.width));
        root.set("Height", factory.create(this.height));
        root.set("Length", factory.create(this.length));
        NBTTagCompound metadata = factory.compound();
        if (this.weOffsetX != null) {
            metadata.set("WEOffsetX", factory.create(this.weOffsetX.getX()));
            metadata.set("WEOffsetY", factory.create(this.weOffsetX.getY()));
            metadata.set("WEOffsetZ", factory.create(this.weOffsetX.getZ()));
        }
        root.set("Metadata", metadata);
        NBTTagCompound palette = factory.compound();
        for (Map.Entry<Integer, Block> entry : this.palette.entrySet()) {
            palette.set(entry.getValue().toString(), factory.create(entry.getKey().intValue()));
        }
        root.set("Palette", palette);
        root.set("BlockData", factory.create(this.blocks));
        NBTTagList<?, NBTTagCompound> blockEntities = factory.list();
        for (HashMap<String, NBTTag<?>> attributes : this.blockEntities) {
            NBTTagCompound blockEntity = factory.compound();
            for (Map.Entry<String, NBTTag<?>> entry2 : attributes.entrySet()) {
                blockEntity.set(entry2.getKey(), entry2.getValue());
            }
            blockEntities.add(blockEntity);
        }
        root.set("BlockEntities", blockEntities);
        if (!this.legacyDefaultLightEngine.isFullyCalculated()) {
            throw new IllegalStateException("Light not calculated for default block light. Did you preload it with the enabled setting?");
        }
        if (!this.legacyColoredLightEngine.isFullyCalculated()) {
            throw new IllegalStateException("Light not calculated for colored block light. Did you preload it with the enabled setting?");
        }
        root.set("BlockLight", factory.create(this.legacyDefaultLightEngine.getData()));
        ColoredLegacyLightEngine coloredEngine = this.legacyColoredLightEngine;
        root.set("BlockLightRed", factory.create(coloredEngine.getRedEngine().getData()));
        root.set("BlockLightGreen", factory.create(coloredEngine.getGreenEngine().getData()));
        root.set("BlockLightBlue", factory.create(coloredEngine.getBlueEngine().getData()));
        factory.writeCompressed(root, IOUtil.newOutputStream(path));
        this.logger.info("Saved schematic to {}", path.toAbsolutePath());
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.WorldLightAccessor
    public Block getBlockAt(int x, int y, int z) {
        int index = getIndex(x, y, z);
        if (index < 0 || index >= this.blockCache.length) {
            return this.airBlock;
        }
        Block block = this.blockCache[index];
        if (block == null) {
            int paletteId = getPaletteId(x, y, z);
            if (paletteId == -1) {
                return this.airBlock;
            }
            block = (Block) this.palette.get(paletteId);
            this.blockCache[index] = block;
        }
        return block;
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.WorldLightAccessor
    public boolean isTranslucent(int x, int y, int z) {
        int index = getIndex(x, y, z);
        if (index < 0 || index >= this.translucentCache.length) {
            return true;
        }
        Boolean translucent = this.translucentCache[index];
        if (translucent == null) {
            Material material = getBlockAt(x, y, z).material();
            Boolean[] boolArr = this.translucentCache;
            Boolean boolValueOf = Boolean.valueOf(material.isTranslucent());
            translucent = boolValueOf;
            boolArr[index] = boolValueOf;
        }
        return translucent.booleanValue();
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.WorldLightAccessor
    public boolean isFullBlock(int x, int y, int z) {
        int index = getIndex(x, y, z);
        if (index < 0 || index >= this.fullBlockCache.length) {
            return false;
        }
        Boolean fullBlock = this.fullBlockCache[index];
        if (fullBlock == null) {
            Material material = getBlockAt(x, y, z).material();
            Boolean[] boolArr = this.fullBlockCache;
            Boolean boolValueOf = Boolean.valueOf(material.isFullBlock());
            fullBlock = boolValueOf;
            boolArr[index] = boolValueOf;
        }
        return fullBlock.booleanValue();
    }

    @Override // net.labymod.core.client.render.schematic.lighting.legacy.WorldLightAccessor
    public boolean isLightSource(int x, int y, int z) {
        int index = getIndex(x, y, z);
        if (index < 0 || index >= this.isLightSourceCache.length) {
            return true;
        }
        Boolean isLightSource = this.isLightSourceCache[index];
        if (isLightSource == null) {
            Block block = getBlockAt(x, y, z);
            Boolean[] boolArr = this.isLightSourceCache;
            Boolean boolIsLightSource = block.material().isLightSource(block);
            isLightSource = boolIsLightSource;
            boolArr[index] = boolIsLightSource;
        }
        return isLightSource.booleanValue();
    }

    private int getPaletteId(int x, int y, int z) {
        int index = getIndex(x, y, z);
        if (index < 0 || index >= this.blocks.length) {
            return -1;
        }
        return this.blocks[index] & 255;
    }

    @Override // net.labymod.core.client.render.schematic.SchematicAccessor
    public LegacyLightEngine legacyLightEngine() {
        DynamicBackgroundConfig config = Laby.labyAPI().config().appearance().dynamicBackground();
        if (config.shader().get().booleanValue() || !config.coloredLight().get().booleanValue()) {
            return this.legacyDefaultLightEngine;
        }
        return this.legacyColoredLightEngine;
    }

    @Override // net.labymod.core.client.render.schematic.SchematicAccessor
    public short getWidth() {
        return this.width;
    }

    @Override // net.labymod.core.client.render.schematic.SchematicAccessor
    public short getHeight() {
        return this.height;
    }

    @Override // net.labymod.core.client.render.schematic.SchematicAccessor
    public short getLength() {
        return this.length;
    }

    public int getChunksInLength() {
        return this.chunksInLength;
    }

    public int getChunksInHeight() {
        return this.chunksInHeight;
    }

    public int getChunksInWidth() {
        return this.chunksInWidth;
    }

    private int getIndex(int x, int y, int z) {
        return (((y * this.length) + z) * this.width) + x;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/Schematic$WEOffset.class */
    private static class WEOffset {
        private final int x;
        private final int y;
        private final int z;

        public WEOffset(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getZ() {
            return this.z;
        }
    }
}
