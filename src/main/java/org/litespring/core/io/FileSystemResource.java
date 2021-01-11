package org.litespring.core.io;

import org.litespring.util.ClassUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResource implements Resource {

    private String path;

    public FileSystemResource(String path) {
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
