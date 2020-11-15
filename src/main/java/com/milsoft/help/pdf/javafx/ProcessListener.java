package com.milsoft.help.pdf.javafx;

@FunctionalInterface
public interface ProcessListener {
    void listen(boolean finished);
}