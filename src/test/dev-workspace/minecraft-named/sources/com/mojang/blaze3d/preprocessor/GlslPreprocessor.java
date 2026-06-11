package com.mojang.blaze3d.preprocessor;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.util.FileUtil;
import net.minecraft.util.StringUtil;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/preprocessor/GlslPreprocessor.class */
public abstract class GlslPreprocessor {
    private static final String C_COMMENT = "/\\*(?:[^*]|\\*+[^*/])*\\*+/";
    private static final String LINE_COMMENT = "//[^\\v]*";
    private static final Pattern REGEX_MOJ_IMPORT = Pattern.compile("(#(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*moj_import(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*(?:\"(.*)\"|<(.*)>))");
    private static final Pattern REGEX_VERSION = Pattern.compile("(#(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*version(?:/\\*(?:[^*]|\\*+[^*/])*\\*+/|\\h)*(\\d+))\\b");
    private static final Pattern REGEX_ENDS_WITH_WHITESPACE = Pattern.compile("(?:^|\\v)(?:\\s|/\\*(?:[^*]|\\*+[^*/])*\\*+/|(//[^\\v]*))*\\z");

    public abstract String applyImport(boolean z, String str);

    public List<String> process(String $$0) {
        Context $$1 = new Context();
        List<String> $$2 = processImports($$0, $$1, "");
        $$2.set(0, setVersion($$2.get(0), $$1.glslVersion));
        return $$2;
    }

    private List<String> processImports(String $$0, Context $$1, String $$2) {
        int $$3 = $$1.sourceId;
        int $$4 = 0;
        String $$5 = "";
        List<String> $$6 = Lists.newArrayList();
        Matcher $$7 = REGEX_MOJ_IMPORT.matcher($$0);
        while ($$7.find()) {
            if (!isDirectiveDisabled($$0, $$7, $$4)) {
                String $$8 = $$7.group(2);
                boolean $$9 = $$8 != null;
                if (!$$9) {
                    $$8 = $$7.group(3);
                }
                if ($$8 != null) {
                    String $$10 = $$0.substring($$4, $$7.start(1));
                    String $$11 = $$2 + $$8;
                    String $$12 = applyImport($$9, $$11);
                    if (!Strings.isNullOrEmpty($$12)) {
                        if (!StringUtil.endsWithNewLine($$12)) {
                            $$12 = $$12 + System.lineSeparator();
                        }
                        $$1.sourceId++;
                        int $$13 = $$1.sourceId;
                        List<String> $$14 = processImports($$12, $$1, $$9 ? FileUtil.getFullResourcePath($$11) : "");
                        $$14.set(0, String.format(Locale.ROOT, "#line %d %d\n%s", 0, Integer.valueOf($$13), processVersions($$14.get(0), $$1)));
                        if (!StringUtil.isBlank($$10)) {
                            $$6.add($$10);
                        }
                        $$6.addAll($$14);
                    } else {
                        String $$15 = $$9 ? String.format(Locale.ROOT, "/*#moj_import \"%s\"*/", $$8) : String.format(Locale.ROOT, "/*#moj_import <%s>*/", $$8);
                        $$6.add($$5 + $$10 + $$15);
                    }
                    int $$16 = StringUtil.lineCount($$0.substring(0, $$7.end(1)));
                    $$5 = String.format(Locale.ROOT, "#line %d %d", Integer.valueOf($$16), Integer.valueOf($$3));
                    $$4 = $$7.end(1);
                }
            }
        }
        String $$17 = $$0.substring($$4);
        if (!StringUtil.isBlank($$17)) {
            $$6.add($$5 + $$17);
        }
        return $$6;
    }

    private String processVersions(String $$0, Context $$1) {
        Matcher $$2 = REGEX_VERSION.matcher($$0);
        if ($$2.find() && isDirectiveEnabled($$0, $$2)) {
            $$1.glslVersion = Math.max($$1.glslVersion, Integer.parseInt($$2.group(2)));
            return $$0.substring(0, $$2.start(1)) + "/*" + $$0.substring($$2.start(1), $$2.end(1)) + "*/" + $$0.substring($$2.end(1));
        }
        return $$0;
    }

    private String setVersion(String $$0, int $$1) {
        Matcher $$2 = REGEX_VERSION.matcher($$0);
        if ($$2.find() && isDirectiveEnabled($$0, $$2)) {
            return $$0.substring(0, $$2.start(2)) + Math.max($$1, Integer.parseInt($$2.group(2))) + $$0.substring($$2.end(2));
        }
        return $$0;
    }

    private static boolean isDirectiveEnabled(String $$0, Matcher $$1) {
        return !isDirectiveDisabled($$0, $$1, 0);
    }

    private static boolean isDirectiveDisabled(String $$0, Matcher $$1, int $$2) {
        int $$3 = $$1.start() - $$2;
        if ($$3 == 0) {
            return false;
        }
        Matcher $$4 = REGEX_ENDS_WITH_WHITESPACE.matcher($$0.substring($$2, $$1.start()));
        if (!$$4.find()) {
            return true;
        }
        int $$5 = $$4.end(1);
        return $$5 == $$1.start();
    }

    public static String injectDefines(String $$0, ShaderDefines $$1) {
        if ($$1.isEmpty()) {
            return $$0;
        }
        int $$2 = $$0.indexOf(10);
        int $$3 = $$2 + 1;
        return $$0.substring(0, $$3) + $$1.asSourceDirectives() + "#line 1 0\n" + $$0.substring($$3);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/preprocessor/GlslPreprocessor$Context.class */
    static final class Context {
        int glslVersion;
        int sourceId;

        Context() {
        }
    }
}
