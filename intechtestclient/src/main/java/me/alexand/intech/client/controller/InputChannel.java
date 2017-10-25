package me.alexand.intech.client.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author asidorov84@gmail.com
 */
public class InputChannel extends Thread {
    private final DataInputStream is;
    private final MessageListener listener;

    public InputChannel(InputStream os, MessageListener listener) {
        this.is = new DataInputStream(os);
        this.listener = listener;
        setName("Input-channel-thread-" + getId());
        start();
    }

    @Override
    public void run() {
        try {
            while (!interrupted()) {
                listener.handle(is.readUTF());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new ConnectionLossException(e);
        }
    }

}
