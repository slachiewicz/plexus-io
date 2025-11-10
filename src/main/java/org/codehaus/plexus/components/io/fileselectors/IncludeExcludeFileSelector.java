package org.codehaus.plexus.components.io.fileselectors;

import javax.inject.Named;
import java.io.File;

import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.MatchPatterns;
import org.codehaus.plexus.util.SelectorUtils;
import org.jspecify.annotations.Nullable;

/**
 * This file selector uses a set of patterns for including/excluding
 * files.
 */
@Named(IncludeExcludeFileSelector.ROLE_HINT)
public class IncludeExcludeFileSelector implements FileSelector {
    /**
     * The include/exclude file selectors role-hint: "standard".
     */
    public static final String ROLE_HINT = "standard";

    private static final MatchPatterns ALL_INCLUDES = MatchPatterns.from(getCanonicalName("**/*"));

    private static final MatchPatterns ZERO_EXCLUDES = MatchPatterns.from();

    private boolean isCaseSensitive = true;

    private boolean useDefaultExcludes = true;

    private String[] includes;

    private String[] excludes;

    private MatchPatterns computedIncludes = ALL_INCLUDES;

    private MatchPatterns computedExcludes = ZERO_EXCLUDES;

    /**
     * Tests whether or not a name matches against at least one exclude
     * pattern.
     *
     * @param name The name to match. Must not be <code>null</code>.
     * @return <code>true</code> when the name matches against at least one
     *         exclude pattern, or <code>false</code> otherwise.
     */
    protected boolean isExcluded(String name) {
        return computedExcludes.matches(name, isCaseSensitive);
    }

    /**
     * Sets the list of include patterns to use. All '/' and '\' characters
     * are replaced by <code>File.separatorChar</code>, so the separator used
     * need not match <code>File.separatorChar</code>.
     * <p>
     * When a pattern ends with a '/' or '\', "**" is appended.
     *
     * @param includes A list of include patterns.
     *                 May be <code>null</code>, indicating that all files
     *                 should be included. If a non-<code>null</code>
     *                 list is given, all elements must be
     *                 non-<code>null</code>.
     */
    public void setIncludes(@Nullable String[] includes) {
        this.includes = includes;
        if (includes == null) {
            computedIncludes = ALL_INCLUDES;
        } else {
            String[] cleaned;
            cleaned = new String[includes.length];
            for (int i = 0; i < includes.length; i++) {
                cleaned[i] = asPattern(includes[i]);
            }
            computedIncludes = MatchPatterns.from(cleaned);
        }
    }

    private static String getCanonicalName(String pName) {
        return pName.replace('/', File.separatorChar).replace('\\', File.separatorChar);
    }

    private String asPattern(String pPattern) {
        String pattern = getCanonicalName(pPattern.trim());
        if (pattern.endsWith(File.separator)) {
            pattern += "**";
        }
        return pattern;
    }

    /**
     * Returns the list of include patterns to use.
     *
     * @return A list of include patterns.
     *         May be <code>null</code>, indicating that all files
     *         should be included. If a non-<code>null</code>
     *         list is given, all elements must be
     *         non-<code>null</code>.
     */
    public @Nullable String[] getIncludes() {
        return includes;
    }

    /**
     * Sets the list of exclude patterns to use. All '/' and '\' characters
     * are replaced by <code>File.separatorChar</code>, so the separator used
     * need not match <code>File.separatorChar</code>.
     * <p>
     * When a pattern ends with a '/' or '\', "**" is appended.
     *
     * @param excludes A list of exclude patterns.
     *                 May be <code>null</code>, indicating that no files
     *                 should be excluded. If a non-<code>null</code> list is
     *                 given, all elements must be non-<code>null</code>.
     */
    public void setExcludes(@Nullable String[] excludes) {
        this.excludes = excludes;
        final String[] defaultExcludes = useDefaultExcludes ? FileUtils.getDefaultExcludes() : new String[] {};
        if (excludes == null) {
            computedExcludes = MatchPatterns.from(defaultExcludes);
        } else {
            String[] temp = new String[excludes.length + defaultExcludes.length];
            for (int i = 0; i < excludes.length; i++) {
                temp[i] = asPattern(excludes[i]);
            }

            if (defaultExcludes.length > 0) {
                System.arraycopy(defaultExcludes, 0, temp, excludes.length, defaultExcludes.length);
            }
            computedExcludes = MatchPatterns.from(temp);
        }
    }

    /**
     * Returns the list of exclude patterns to use.
     *
     * @return A list of exclude patterns.
     *         May be <code>null</code>, indicating that no files
     *         should be excluded. If a non-<code>null</code> list is
     *         given, all elements must be non-<code>null</code>.
     */
    public @Nullable String[] getExcludes() {
        return excludes;
    }

    /**
     * Tests, whether the given pattern is matching the given name.
     * @param pattern The pattern to match
     * @param name The name to test
     * @param isCaseSensitive Whether the pattern is case sensitive.
     * @return True, if the pattern matches, otherwise false
     */
    protected boolean matchPath(String pattern, String name, boolean isCaseSensitive) {
        return SelectorUtils.matchPath(pattern, name, isCaseSensitive);
    }

    /**
     * Tests whether or not a name matches against at least one include
     * pattern.
     *
     * @param name The name to match. Must not be <code>null</code>.
     * @return <code>true</code> when the name matches against at least one
     *         include pattern, or <code>false</code> otherwise.
     */
    protected boolean isIncluded(String name) {
        return computedIncludes.matches(name, isCaseSensitive);
    }

    @Override
    public boolean isSelected(FileInfo fileInfo) {
        final String name = getCanonicalName(fileInfo.getName());
        return isIncluded(name) && !isExcluded(name);
    }

    /**
     * Returns, whether the include/exclude patterns are case sensitive.
     * @return True, if the patterns are case sensitive (default), or false.
     */
    public boolean isCaseSensitive() {
        return isCaseSensitive;
    }

    /**
     * Sets, whether the include/exclude patterns are case sensitive.
     * @param caseSensitive True, if the patterns are case sensitive (default), or false.
     */
    public void setCaseSensitive(boolean caseSensitive) {
        isCaseSensitive = caseSensitive;
    }

    /**
     * Returns, whether to use the default excludes, as specified by
     * {@link FileUtils#getDefaultExcludes()}.
     */
    public boolean isUseDefaultExcludes() {
        return useDefaultExcludes;
    }

    /**
     * Sets, whether to use the default excludes, as specified by
     * {@link FileUtils#getDefaultExcludes()}.
     */
    public void setUseDefaultExcludes(boolean pUseDefaultExcludes) {
        useDefaultExcludes = pUseDefaultExcludes;
        setExcludes(excludes);
    }
}
