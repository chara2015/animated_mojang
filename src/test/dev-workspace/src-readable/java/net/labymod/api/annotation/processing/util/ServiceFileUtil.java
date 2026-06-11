package net.labymod.api.annotation.processing.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.tools.FileObject;
import net.labymod.api.annotation.processing.exception.ProcessingException;
import net.labymod.api.annotation.processing.model.CustomServiceModel;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/annotation/processing/util/ServiceFileUtil.class */
public class ServiceFileUtil {
    private static final Gson GSON = new GsonBuilder().create();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/annotation/processing/util/ServiceFileUtil$WriterConsumer.class */
    @FunctionalInterface
    public interface WriterConsumer {
        void accept(Writer writer) throws IOException;
    }

    public static void writeService(FileObject fileObject, double classVersion, Collection<String> fileNames) {
        List<CustomServiceModel> models = new ArrayList<>(fileNames.size());
        for (String fileName : fileNames) {
            CustomServiceModel model = new CustomServiceModel(fileName, classVersion);
            models.add(model);
        }
        writeService(fileObject, models);
    }

    public static void writeService(FileObject fileObject, Collection<CustomServiceModel> models) {
        if (models.isEmpty()) {
            return;
        }
        try {
            Writer writer = fileObject.openWriter();
            try {
                writer.write(GSON.toJson(models));
                if (writer != null) {
                    writer.close();
                }
            } finally {
            }
        } catch (IOException exception) {
            throw new ProcessingException(exception);
        }
    }

    public static void writeService(FileObject fileObject, WriterConsumer consumer) {
        try {
            Writer writer = fileObject.openWriter();
            try {
                consumer.accept(writer);
                if (writer != null) {
                    writer.close();
                }
            } finally {
            }
        } catch (IOException exception) {
            throw new ProcessingException(exception);
        }
    }
}
