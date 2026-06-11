package net.labymod.v1_8_9.client.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/util/VersionedWailaUtil.class */
public class VersionedWailaUtil {
    private static final int WOOD_LEVEL = 0;
    private static final int STONE_LEVEL = 1;
    private static final int IRON_LEVEL = 2;
    private static final int DIAMOND_LEVEL = 3;
    private static final Reference2ObjectMap<Class<?>, Int2ObjectMap<Set<afh>>> TOOLS = new Reference2ObjectArrayMap();
    private static final Set<afh> WOOD_AXE_BLOCKS = Set.of((Object[]) new afh[]{afi.f, afi.X, afi.r, afi.s, afi.ae, afi.aU, afi.aZ, afi.bk, afi.au, afi.ai, afi.cg, afi.ax, afi.an, afi.B});
    private static final Set<afh> WOOD_SHOVEL_BLOCKS = Set.of(afi.aL, afi.d, afi.ak, afi.c, afi.n, afi.bw, afi.m, afi.aJ, afi.aH, afi.aW);
    private static final Set<afh> WOOD_PICKAXE_BLOCKS = Set.of((Object[]) new afh[]{afi.cs, afi.q, afi.e, afi.E, afi.T, afi.D, afi.aI, afi.Y, afi.aV, afi.cB, afi.av, afi.aC, afi.A, afi.cM, afi.b, afi.U, afi.co, afi.al});
    private static final Set<afh> MIN_TOOL_BLOCKS = new HashSet();
    private static boolean initialized = false;

    public static boolean canHarvest(zx stack, afh block) {
        Set<afh> blocks;
        za zaVarB = stack.b();
        if (!(zaVarB instanceof za)) {
            return false;
        }
        za itemTool = zaVarB;
        Int2ObjectMap<Set<afh>> materialIndex = getBlocks(zaVarB.getClass());
        return (materialIndex == null || (blocks = (Set) materialIndex.get(itemTool.g().d())) == null || !blocks.contains(block)) ? false : true;
    }

    public static boolean requiresMinToolForDrops(afh block) {
        return MIN_TOOL_BLOCKS.contains(block);
    }

    public static String mapEntityName(pk entity) {
        if (entity instanceof uf) {
            return "End Crystal";
        }
        if (entity instanceof uo) {
            return bnq.a("item.frame.name", new Object[0]);
        }
        if (entity instanceof up) {
            return bnq.a("item.leash.name", new Object[0]);
        }
        return entity.e_();
    }

    public static void initialize() {
        if (initialized) {
            return;
        }
        initialized = true;
        registerDefaultTools();
        setToolMaterialBlocks((Class<?>) aag.class, 0, WOOD_PICKAXE_BLOCKS);
        setToolMaterialBlocks((Class<?>) aag.class, 1, afi.p, afi.S, afi.x, afi.y);
        setToolMaterialBlocks((Class<?>) aag.class, 2, afi.bP, afi.bT, afi.ag, afi.ah, afi.o, afi.R, afi.aC, afi.aD);
        setToolMaterialBlocks((Class<?>) aag.class, 3, afi.Z);
        setToolMaterialBlocks((Class<?>) aaq.class, 0, WOOD_SHOVEL_BLOCKS);
        setToolMaterialBlocks((Class<?>) yl.class, 0, WOOD_AXE_BLOCKS);
        ObjectIterator it = ((Int2ObjectMap) TOOLS.get(aag.class)).values().iterator();
        while (it.hasNext()) {
            Set<afh> value = (Set) it.next();
            MIN_TOOL_BLOCKS.addAll(value);
        }
        MIN_TOOL_BLOCKS.remove(afi.cs);
        MIN_TOOL_BLOCKS.remove(afi.E);
        MIN_TOOL_BLOCKS.remove(afi.D);
        MIN_TOOL_BLOCKS.remove(afi.av);
        MIN_TOOL_BLOCKS.add(afi.aJ);
        MIN_TOOL_BLOCKS.add(afi.aH);
    }

    private static Int2ObjectMap<Set<afh>> getBlocks(Class<?> cls) {
        initialize();
        return (Int2ObjectMap) TOOLS.get(cls);
    }

    private static void setToolMaterialBlocks(Class<?> toolClass, int materialLevel, Set<afh> blocks) {
        setToolMaterialBlocks(toolClass, materialLevel, (afh[]) blocks.toArray(new afh[0]));
    }

    private static void setToolMaterialBlocks(Class<?> toolClass, int materialLevel, afh... blocks) {
        ((Set) ((Int2ObjectMap) TOOLS.get(toolClass)).computeIfAbsent(materialLevel, index -> {
            return new HashSet();
        })).addAll(buildBlocks(blocks));
    }

    private static Set<afh> buildBlocks(afh... blocks) {
        return Set.of((Object[]) blocks);
    }

    private static void registerDefaultTools() {
        TOOLS.put(yl.class, new Int2ObjectArrayMap());
        TOOLS.put(aaq.class, new Int2ObjectArrayMap());
        TOOLS.put(aag.class, new Int2ObjectArrayMap());
    }
}
