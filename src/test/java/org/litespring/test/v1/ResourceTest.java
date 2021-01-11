package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class ResourceTest {

    @Test
    public void testClassPathResource() throws IOException {
        Resource r = new ClassPathResource("petstore-v1.xml");
        InputStream is = null;
        try {
            is = r.getInputStream();
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws IOException {
        Resource r = new FileSystemResource("/Users/imac/Development/Workspace/IdeaWorkspace/little-spring/src/test/resources/petstore-v1.xml");
        InputStream is = null;
        try {
            is = r.getInputStream();
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
