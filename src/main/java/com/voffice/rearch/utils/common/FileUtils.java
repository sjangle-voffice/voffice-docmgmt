package com.voffice.rearch.utils.common;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    public static boolean isImgFile (File file) {

        try {
            String mimetype = Files.probeContentType(file.toPath());
            if (mimetype != null &&
                    mimetype.split("/")[0].equals("image")) {
                return true;
            } else {
                return false;
            }
        } catch ( Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static StringBuffer getFileContents (File file) throws IOException {
        StringBuffer fileBuff = new StringBuffer();

        Path filePath = Paths.get(file.getPath());
        Charset charset = StandardCharsets.UTF_8;

        List<String> lines = Files.readAllLines(filePath, charset);
        for (String str : lines) {
            fileBuff.append(str);
        }
        return fileBuff;
    }
}