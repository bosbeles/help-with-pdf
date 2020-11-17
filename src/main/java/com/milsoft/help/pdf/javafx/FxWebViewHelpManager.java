package com.milsoft.help.pdf.javafx;

import com.milsoft.help.HelpFile;
import com.milsoft.help.pdf.PdfHelpManager;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class FxWebViewHelpManager extends PdfHelpManager {

    private volatile String lastHelpFile = null;
    private volatile PDFDisplayer displayer;
    private final JFXPanel fxPanel;
    private final JFrame frame;


    public FxWebViewHelpManager(JFrame owner) {
        frame = new JFrame("Help");
        fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(960, 600);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    @Override
    protected void open(HelpFile helpFile, Integer page) {
        frame.setVisible(true);
        int pageNo = page == null ? 1 : page;

        if (lastHelpFile == null || !lastHelpFile.equals(helpFile.getFileKey())) {
            Platform.runLater(() -> initFX(fxPanel, helpFile, page));
        }
        else {
            Platform.runLater(() -> {
                displayer.navigateByPage(pageNo);
            });
        }

    }

    private void initFX(JFXPanel fxPanel, HelpFile file, Integer page) {

        displayer = new PDFDisplayer(page);
        try {
            //displayer.displayPdf(file.getName() + "#page=" + page);
            displayer.displayPdf(file.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StackPane stack = new StackPane(displayer.toNode());
        Scene scene = new Scene(stack, 960, 600);

        fxPanel.setScene(scene);

        lastHelpFile = file.getFileKey();
    }

}
