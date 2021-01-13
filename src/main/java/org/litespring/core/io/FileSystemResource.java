package org.litespring.core.io;

import org.litespring.util.ClassUtils;

import java.io.*;

public class FileSystemResource implements Resource {

    private final String path;
    private final File file;

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        this.file = new File(path);
        this.path = path;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(path);
    }

    @Override
    public String getDescription() {
        return path;
    }
}
