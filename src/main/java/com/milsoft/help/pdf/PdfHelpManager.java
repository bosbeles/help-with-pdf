package com.milsoft.help.pdf;

import com.milsoft.help.HelpFile;

import java.util.HashMap;
import java.util.Map;

public abstract class PdfHelpManager {

    private Map<String, HelpFile> helpFileMap = new HashMap<>();

    public void register(HelpFile helpFile) {
        helpFileMap.put(helpFile.getFileKey(), helpFile);
        PdfBookmarkExtractor extractor = new PdfBookmarkExtractor(helpFile.getFile());
        Map<String, Integer> bookmarkToPageMap = extractor.extract();
        helpFile.setBookmarkToPageMap(bookmarkToPageMap);
    }

    public void deregister(HelpFile helpFile) {
        deregister(helpFile.getFileKey());
    }

    public void deregister(String fileKey) {
        helpFileMap.remove(fileKey);
    }

    public void open(String fileKey, Integer page) {
        open(helpFileMap.get(fileKey), page);
    }

    public void open(String fileKey, String tag) {
        HelpFile helpFile = helpFileMap.get(fileKey);
        if(helpFile != null) {
            String bookmark = helpFile.getTagToBookmarkMap().get(tag);
            Integer page = helpFile.getBookmarkToPageMap().get(bookmark);
            open(helpFile, page);
        }
    }

    protected abstract void open(HelpFile helpFile, Integer page);
}
