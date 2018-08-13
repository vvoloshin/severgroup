package com.severgroup.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

public class FileUtil {

    private final static Logger LOGGER = Logger.getLogger(FileUtil.class);

    public static void delete(Path path) {
        if ((isExist(path)) && (path.toFile().delete())) {
            LOGGER.debug("file sucessfully delete from: " + path);
        } else try {
            throw new IOException("error, file not delete from: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rename(Path oldPath, Path newPath) {
        if ((isExist(oldPath)) && (oldPath.toFile().renameTo(newPath.toFile()))) {
            LOGGER.debug("file sucessfully remove from: " + oldPath + " to: " + newPath);
        } else try {
            throw new IOException("error, file not removed from: " + oldPath + " to: " + newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isExist(Path path) {
        if (path.toFile().exists() && path.toFile().isFile()) {
            LOGGER.debug("file sucessfully exist in: " + path);
            return true;
        } else {
            try {
                throw new IOException("error, file not found in: " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public static boolean isNotEmpty(Path path) {
        if (isExist(path) && path.toFile().length() != 0) {
            LOGGER.debug("file in: " + path + " not empty");
            return true;
        } else {
            try {
                throw new IOException("error, file in: " + path + " is empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public static boolean isFolder(Path path) {
        if (path.toFile().isDirectory()) return true;
        else try {
            throw new IOException("error, path for DATA : " + path + " is not directory");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
