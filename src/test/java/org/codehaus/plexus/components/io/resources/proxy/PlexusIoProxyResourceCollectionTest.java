package org.codehaus.plexus.components.io.resources.proxy;

import java.io.Closeable;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import org.codehaus.plexus.components.io.resources.AbstractPlexusIoResource;
import org.codehaus.plexus.components.io.resources.AbstractPlexusIoResourceCollection;
import org.codehaus.plexus.components.io.resources.PlexusIoResource;
import org.codehaus.plexus.components.io.resources.Stream;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test case for {@link PlexusIoProxyResourceCollection}.
 */
class PlexusIoProxyResourceCollectionTest {
    private final String[] SAMPLE_INCLUDES = {"junk.*", "test/**", "dir*/file.xml"};

    private final String[] SAMPLE_EXCLUDES = {"*.junk", "somwhere/**"};

    @Test
    void getDefaultFileSelector() {
        PlexusIoProxyResourceCollection resCol = new PlexusIoProxyResourceCollection(null);

        // This will throw an exception if there is a bug
        resCol.getDefaultFileSelector();

        resCol.setIncludes(SAMPLE_INCLUDES);
        resCol.setExcludes(SAMPLE_EXCLUDES);

        // This will throw an exception if there is a bug
        resCol.getDefaultFileSelector();
    }

    static class CloseableIterator implements Iterator<PlexusIoResource>, Closeable {
        boolean next = true;

        boolean closed = false;

        @Override
        public void close() {
            closed = true;
        }

        @Override
        public boolean hasNext() {
            if (next) {
                next = false;
                return true;
            }
            return false;
        }

        @Override
        public PlexusIoResource next() {
            return new AbstractPlexusIoResource("fud", 123, 22, true, false, false) {

                @Override
                public @Nullable InputStream getContents() {
                    return null;
                }

                @Override
                public @Nullable URL getURL() {
                    return null;
                }
            };
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Test
    void closing() throws Exception {
        final CloseableIterator closeableIterator = new CloseableIterator();
        PlexusIoProxyResourceCollection resCol =
                new PlexusIoProxyResourceCollection(new AbstractPlexusIoResourceCollection() {
                    @Override
                    public Iterator<PlexusIoResource> getResources() {
                        return closeableIterator;
                    }

                    @Override
                    public Stream stream() {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public boolean isConcurrentAccessSupported() {
                        return true;
                    }
                });
        Iterator<PlexusIoResource> resources1 = resCol.getResources();
        resources1.hasNext();
        resources1.next();
        assertFalse(resources1.hasNext());
        ((Closeable) resources1).close();
        assertTrue(closeableIterator.closed);
    }
}
