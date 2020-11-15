package com;

import com.milsoft.help.HelpFile;
import com.milsoft.help.pdf.PdfBookmarkExtractor;
import com.milsoft.help.pdf.chromium.ChromiumPdfHelpManager;
import com.milsoft.help.pdf.javafx.FxWebViewHelpManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Comparator;
import java.util.Map;

public class MilsoftHelp {
    private JPanel panel;
    private JRadioButton fxWebViewRadioButton;
    private JRadioButton chromiumRadioButton;
    private JTextField pdfFileField;
    private JButton chooseButton;
    private JPanel helpChooser;
    private JButton openBookmarkButton;
    private JComboBox bookmarkCombo;
    private JTextField pageNoField;
    private JButton openPageButton;

    private Process process;
    private HelpFile helpFile;
    private FxWebViewHelpManager fxWebViewHelpManager;
    private ChromiumPdfHelpManager chromiumPdfHelpManager;
    private JFileChooser chooser;


    public MilsoftHelp() {
        fxWebViewHelpManager = new FxWebViewHelpManager(null);
        chromiumPdfHelpManager = new ChromiumPdfHelpManager(new File("C:\\Users\\iciftci\\Downloads\\chrome-win\\mil-chrome.exe"));

        chooseButton.addActionListener(e -> {
            chooser = new JFileChooser(pdfFileField.getText());
            chooser.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
            int ret = chooser.showOpenDialog(null);
            File selectedFile = chooser.getSelectedFile();
            if (selectedFile != null) {
                pdfFileField.setText(selectedFile.toPath().toAbsolutePath().toString());
                helpFile = new HelpFile(selectedFile);
                PdfBookmarkExtractor extractor = new PdfBookmarkExtractor(selectedFile);
                Map<String, Integer> bookmarkMap = extractor.extract();
                Object[] bookmarks = bookmarkMap.entrySet().stream()
                        .sorted(Comparator.comparingInt(Map.Entry::getValue))
                        .map(entry -> entry.getKey())
                        .toArray();

                bookmarkCombo.setModel(new DefaultComboBoxModel(bookmarks));

                fxWebViewHelpManager.register(helpFile);
                chromiumPdfHelpManager.register(helpFile);
            }


        });
        openBookmarkButton.addActionListener(e -> {
            String pdfFile = pdfFileField.getText();
            String bookmark = (String) bookmarkCombo.getSelectedItem();


            if (fxWebViewRadioButton.isSelected()) {
                fxWebViewHelpManager.open(helpFile.getFileKey(), helpFile.getBookmarkToPageMap().get(bookmarkCombo.getSelectedItem()));
            } else {
                chromiumPdfHelpManager.open(helpFile.getFileKey(), helpFile.getBookmarkToPageMap().get(bookmarkCombo.getSelectedItem()));
            }
        });

        openPageButton.addActionListener(e -> {
            String pdfFile = pdfFileField.getText();
            Integer page = 1;
            try {
                page = Integer.parseInt(pageNoField.getText());
            } catch (Exception ex) {

            }


            if (fxWebViewRadioButton.isSelected()) {
                fxWebViewHelpManager.open(helpFile.getFileKey(), page);
            } else {
                chromiumPdfHelpManager.open(helpFile.getFileKey(), page);
            }
        });
    }

    public JPanel getHelpChooser() {
        return helpChooser;
    }


}
