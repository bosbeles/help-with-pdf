package com.milsoft.help.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDNamedDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PdfBookmarkExtractor {

    private final File file;
    private Map<String, Integer> bookmarkToPageMap;

    public PdfBookmarkExtractor(File file) {
        this.file = file;
        this.bookmarkToPageMap = new HashMap<>();
    }

    public Map<String, Integer> extract() {
        bookmarkToPageMap = new HashMap<>();

        try (PDDocument document = PDDocument.load(file)) {
            PDDocumentOutline outline = document.getDocumentCatalog().getDocumentOutline();
            if (outline != null) {
                extractBookmarks(document, outline, "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookmarkToPageMap;
    }


    private void extractBookmarks(PDDocument document, PDOutlineNode bookmark, String indentation) throws IOException {
        PDOutlineItem current = bookmark.getFirstChild();
        while (current != null) {
            // one could also use current.findDestinationPage(document) to get the page number,
            // but this example does it the hard way to explain the different types
            // Note that bookmarks can also do completely different things, e.g. link to a website,
            // or to an external file. This example focuses on internal pages.
            if (current.getDestination() instanceof PDPageDestination) {
                PDPageDestination pd = (PDPageDestination) current.getDestination();
                bookmarkToPageMap.put(current.getTitle(), pd.retrievePageNumber() + 1);
            } else if (current.getDestination() instanceof PDNamedDestination) {
                PDPageDestination pd = document.getDocumentCatalog().findNamedDestinationPage((PDNamedDestination) current.getDestination());
                if (pd != null) {
                    bookmarkToPageMap.put(current.getTitle(), pd.retrievePageNumber() + 1);
                }
            }

            if (current.getAction() instanceof PDActionGoTo) {
                PDActionGoTo gta = (PDActionGoTo) current.getAction();
                if (gta.getDestination() instanceof PDPageDestination) {
                    PDPageDestination pd = (PDPageDestination) gta.getDestination();
                    bookmarkToPageMap.put(current.getTitle(), pd.retrievePageNumber() + 1);
                } else if (gta.getDestination() instanceof PDNamedDestination) {
                    PDPageDestination pd = document.getDocumentCatalog().findNamedDestinationPage((PDNamedDestination) gta.getDestination());
                    if (pd != null) {
                        bookmarkToPageMap.put(current.getTitle(), pd.retrievePageNumber() + 1);
                    }
                }
            }

            extractBookmarks(document, current, indentation + "    ");
            current = current.getNextSibling();
        }
    }
}
