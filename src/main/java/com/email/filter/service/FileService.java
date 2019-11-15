package com.email.filter.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author
 */
@Service
public class FileService {

    final static Logger logger = Logger.getLogger(FileService.class);

    public String rootDir;

    public byte[] readFile(String identifier) {
        try {
            return fileTOBytesArray(rootDir + identifier);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private byte[] fileTOBytesArray(String fileName) throws FileNotFoundException, IOException {
        if (fileName != null) {
            File f = new File(fileName);
            if (f.exists()) {
                byte[] fileAsBytes = IOUtils.toByteArray(new FileInputStream(new File(fileName)));
                return fileAsBytes;
            }
        }
        return new byte[0];
    }

    private String getFilePath(String identifier) {
        try {
            File f = new File(rootDir + identifier.split("/")[0]);
            if (f.isDirectory()) {
                File[] files = f.listFiles();
                for (File _f : files) {
                    String a = FilenameUtils.removeExtension(_f.getName());
                    if (a.equals(identifier.split("/")[1])) {
                        return _f.getPath();
                    }
                }
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void deleteFile(String identifier) {
        File f = new File(rootDir + "/" + identifier);
        if (f.exists()) {
            try {
                f.delete();
            } catch (Exception ex) {

            }
        }
    }

    public String getFileFullPath(String name) {
        return rootDir + "/" + name;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    Dimension getImageDimension(File imgFile) throws IOException {
        BufferedImage img = ImageIO.read(imgFile);
        return new Dimension(img.getWidth(), img.getHeight());
    }
}
