package net.labymod.core.configuration.converter.converter;

import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import net.labymod.api.Constants;
import net.labymod.api.configuration.converter.LegacyConverter;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.account.AccountManagerController;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/converter/converter/LegacyAccountConverter.class */
public class LegacyAccountConverter extends LegacyConverter<JsonObject> {
    public LegacyAccountConverter() {
        super("accounts.json", JsonObject.class);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public void convert(JsonObject jsonObject) throws Exception {
        Path file = Constants.Files.ACCOUNTS;
        Files.createDirectories(file.getParent(), new FileAttribute[0]);
        Files.write(file, this.gson.toJson(jsonObject).getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        LabyMod.getInstance().getAccountManager().load(AccountManagerController.AZURE);
    }

    @Override // net.labymod.api.configuration.converter.LegacyConverter
    public boolean hasStuffToConvert() {
        return getValue() != null;
    }
}
