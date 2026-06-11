package net.minecraft.server.dialog.action;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.Map;
import net.minecraft.commands.functions.StringTemplate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/action/ParsedTemplate.class */
public class ParsedTemplate {
    public static final Codec<ParsedTemplate> CODEC = Codec.STRING.comapFlatMap(ParsedTemplate::parse, $$0 -> {
        return $$0.raw;
    });
    public static final Codec<String> VARIABLE_CODEC = Codec.STRING.validate($$0 -> {
        return StringTemplate.isValidVariableName($$0) ? DataResult.success($$0) : DataResult.error(() -> {
            return $$0 + " is not a valid input name";
        });
    });
    private final String raw;
    private final StringTemplate parsed;

    private ParsedTemplate(String $$0, StringTemplate $$1) {
        this.raw = $$0;
        this.parsed = $$1;
    }

    private static DataResult<ParsedTemplate> parse(String $$0) {
        try {
            StringTemplate $$3 = StringTemplate.fromString($$0);
            return DataResult.success(new ParsedTemplate($$0, $$3));
        } catch (Exception $$2) {
            return DataResult.error(() -> {
                return "Failed to parse template " + $$0 + ": " + $$2.getMessage();
            });
        }
    }

    public String instantiate(Map<String, String> $$0) {
        List<String> $$1 = this.parsed.variables().stream().map($$12 -> {
            return (String) $$0.getOrDefault($$12, "");
        }).toList();
        return this.parsed.substitute($$1);
    }
}
