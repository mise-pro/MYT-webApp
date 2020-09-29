package ru.myt.data.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;

public class FileToInputStreamConverter {
    //use this if external files are in /resources/ dir
    public static InputStream accessFileInsideArch(String filename) {
        try {
            // this is the path within the jar file
            InputStream input = FileToInputStreamConverter.class.getResourceAsStream("/resources/" + filename);
            if (input == null) {
                // this is how we load file within editor (eg eclipse), or vice versa!
                input = FileToInputStreamConverter.class.getClassLoader().getResourceAsStream(filename);
            }

            return input;

        } catch (Exception ex) {
            System.out.println("FileFileNotFoundException - no file" + filename);
            System.exit(1);
        }
        return null;
    }
    //use this if external files are near jar or at root folder of IDE project
    public static InputStream accessFileOutsideArch(String filename) {
        String path = Paths.get(".").toAbsolutePath().normalize().toString() + "\\" + filename;
        try {
            File file = new File(path);
            FileInputStream input = new FileInputStream(file);
            return input;

        } catch (FileNotFoundException ex) {
            System.out.println("FileFileNotFoundException - no file" + filename);
            System.out.println("at path:" + path);
            System.exit(1);
        }
        return null;
    }
}