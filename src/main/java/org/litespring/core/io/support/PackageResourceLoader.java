package org.litespring.core.io.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;
import org.litespring.util.Assert;
import org.litespring.util.ClassUtils;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class PackageResourceLoader {

    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);

    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Resource[] getResources(String basePackage) {
        Assert.notNull(basePackage, "basePackage must not be null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader classLoader = getClassLoader();
        URL url = classLoader.getResource(location);
        File rootDir = new File(url.getFile());
        Set<File> matchingFiles = retrieveMatchingFiles(rootDir);
        Resource[] result = new Resource[matchingFiles.size()];
        int i = 0;
        for (File matchingFile : matchingFiles) {
            result[i++] = new FileSystemResource(matchingFile);
        }
        return result;
    }

    private Set<File> retrieveMatchingFiles(File rootDir) {
        if (!rootDir.exists()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exists");
            }
            return Collections.emptySet();
        }
        if (!rootDir.isDirectory()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not a directory");
            }
            return Collections.emptySet();
        }
        if (!rootDir.canRead()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it is not allowed to read");
            }
            return Collections.emptySet();
        }
        Set<File> result = new LinkedHashSet<>();
        doRetrieveMatchingFiles(rootDir, result);
        return result;
    }

    private void doRetrieveMatchingFiles(File rootDir, Set<File> result) {
        File[] dirContents = rootDir.listFiles();
        if (dirContents == null) {
            return;
        }
        for (File dirContent : dirContents) {
            if (dirContent.isDirectory()) {
                if (!dirContent.canRead()) {
                    // 不能读取路径日志打印
                } else {
                    doRetrieveMatchingFiles(dirContent, result);
                }
            } else {
                result.add(dirContent);
            }

        }
    }
}
