package net.labymod.api.mappings.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import net.labymod.api.mappings.ClassMapping;
import net.labymod.api.mappings.FieldMapping;
import net.labymod.api.mappings.MappingFile;
import net.labymod.api.mappings.MethodMapping;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mappings/io/SrgMappingReader.class */
public class SrgMappingReader implements MappingReader {
    @Override // net.labymod.api.mappings.io.MappingReader
    public MappingFile read(InputStream stream) {
        Collection<String> lines = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).lines().filter(l -> {
            return !l.isEmpty();
        }).toList();
        MappingFile mappingFile = new MappingFile();
        if (lines.isEmpty()) {
            return mappingFile;
        }
        for (String line : lines) {
            if (line.startsWith("CL:")) {
                String[] split = line.substring(4).split(" ", 2);
                mappingFile.getOrCreate(split[0], split[1]);
            } else if (line.startsWith("FD:")) {
                String[] split2 = line.substring(4).split(" ", 2);
                String left = split2[0];
                String right = split2[1];
                int leftLastIndex = left.lastIndexOf(47);
                String leftClassName = left.substring(0, leftLastIndex);
                String leftFieldName = left.substring(leftLastIndex + 1);
                int rightLastIndex = right.lastIndexOf(47);
                String rightClassName = right.substring(0, rightLastIndex);
                String rightFieldName = right.substring(rightLastIndex + 1);
                ClassMapping cls = mappingFile.getOrCreate(leftClassName, rightClassName);
                cls.addField(new FieldMapping(leftFieldName, rightFieldName));
            } else if (line.startsWith("MD:")) {
                String[] split3 = line.substring(4).split(" ", 4);
                String left2 = split3[0];
                String right2 = split3[2];
                int leftLastIndex2 = left2.lastIndexOf(47);
                String leftClassName2 = left2.substring(0, leftLastIndex2);
                String leftMethodName = left2.substring(leftLastIndex2 + 1);
                String leftMethodDescriptor = split3[1];
                int rightLastIndex2 = right2.lastIndexOf(47);
                String rightClassName2 = right2.substring(0, rightLastIndex2);
                String rightMethodName = right2.substring(rightLastIndex2 + 1);
                String rightMethodDescriptor = split3[3];
                ClassMapping cls2 = mappingFile.getOrCreate(leftClassName2, rightClassName2);
                cls2.addMethod(new MethodMapping(leftMethodName, leftMethodDescriptor, rightMethodName, rightMethodDescriptor));
            }
        }
        return mappingFile;
    }
}
