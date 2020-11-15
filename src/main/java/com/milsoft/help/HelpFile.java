package com.milsoft.help;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HelpFile {
    private String fileKey;
    private File file;
    private Map<String, Integer> bookmarkToPageMap;
    private Map<String, String> tagToBookmarkMap;

    public HelpFile(File file) {
        this(file.getName(), file);
    }

    public HelpFile(String fileKey, File file) {
        this.fileKey = fileKey;
        this.file = file;
        bookmarkToPageMap = new HashMap<>();
        tagToBookmarkMap = new HashMap<>();
    }

    public File getFile() {
        return file;
    }

    public String getFileKey() {
        return fileKey;
    }

    public Map<String, Integer> getBookmarkToPageMap() {
        return bookmarkToPageMap;
    }

    public Map<String, String> getTagToBookmarkMap() {
        return tagToBookmarkMap;
    }

    public void setBookmarkToPageMap(Map<String, Integer> bookmarkToPageMap) {
        this.bookmarkToPageMap = bookmarkToPageMap;
    }

    public void setTagToBookmarkMap(Map<String, String> tagToBookmarkMap) {
        this.tagToBookmarkMap = tagToBookmarkMap;
    }
}
