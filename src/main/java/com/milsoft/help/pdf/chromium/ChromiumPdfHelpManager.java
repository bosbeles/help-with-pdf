package com.milsoft.help.pdf.chromium;

import com.milsoft.help.HelpFile;
import com.milsoft.help.pdf.PdfHelpManager;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ChromiumPdfHelpManager extends PdfHelpManager {

    private final File chromiumExecutable;
    Process lastProcess;

    public ChromiumPdfHelpManager(File chromiumExecutable) {
        this.chromiumExecutable = chromiumExecutable;
    }

    @Override
    protected void open(HelpFile helpFile, Integer page) {
        Objects.requireNonNull(helpFile);

        ProcessBuilder pb = new ProcessBuilder();
        String chromePath = chromiumExecutable.getAbsolutePath();
        String pdfString = helpFile.getFile().getAbsolutePath()
                + (page == null ? "" : "#page=" + page);
        pb.command(chromePath, "--app=" + pdfString);


        try {
            if (lastProcess != null) {
                lastProcess.destroyForcibly();
            }
            lastProcess = pb.start();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
