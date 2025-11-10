package org.codehaus.plexus.components.io.filemappers;

import javax.inject.Named;

/**
 * Default implementation of {@link FileMapper}, which performs the identity mapping: All names are left unchanged.
 */
@Named(IdentityMapper.ROLE_HINT)
public class IdentityMapper extends AbstractFileMapper {
    /**
     * The identity mappers role-hint: "identity".
     */
    public static final String ROLE_HINT = "identity";

    @Override
    public String getMappedFileName(String pName) {
        if (pName == null || pName.isEmpty()) {
            throw new IllegalArgumentException("The source name must not be null.");
        }
        return pName;
    }
}
