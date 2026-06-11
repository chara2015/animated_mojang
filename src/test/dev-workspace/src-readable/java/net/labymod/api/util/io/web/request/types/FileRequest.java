package net.labymod.api.util.io.web.request.types;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Response;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/util/io/web/request/types/FileRequest.class */
public class FileRequest extends AbstractFileRequest<Path, FileRequest> {
    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    protected /* bridge */ /* synthetic */ Object handle(Response response, WebInputStream webInputStream) throws Exception {
        return handle((Response<Path>) response, webInputStream);
    }

    private FileRequest() {
        super(null, null);
    }

    protected FileRequest(@NotNull Path path, @Nullable Consumer<Double> percentageConsumer) {
        this();
        Objects.requireNonNull(path, "Path cannot be null");
        this.path = path;
        this.percentageConsumer = percentageConsumer;
    }

    public static FileRequest of(@NotNull Path path) {
        return new FileRequest(path, null);
    }

    public static FileRequest of(@NotNull Path path, @Nullable Consumer<Double> percentageConsumer) {
        return new FileRequest(path, percentageConsumer);
    }

    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    protected Path handle(Response<Path> response, WebInputStream inputStream) throws IOException {
        return download(response, inputStream);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.util.io.web.request.AbstractRequest
    public FileRequest prepareCopy() {
        return applyRequestDataTo(new FileRequest());
    }
}
