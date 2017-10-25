package me.alexand.intech.server.service;


import me.alexand.intech.server.util.exceptions.ConnectionLossException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author asidorov84@gmail.com
 */
public class OutboundChannel extends Thread {
    private final DataOutputStream dos;
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(20);

    public OutboundChannel(OutputStream os) {
        this.dos = new DataOutputStream(os);
        setName("Outbound-channel-thread-" + getId());
        start();
    }

    public void sendMessage(String message) {
        if (message != null) {
            queue.offer(message);
        }
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                String message = queue.take();
                dos.writeUTF(message);
                dos.flush();
            }
        } catch (InterruptedException e) {
        } catch (IOException e) {
            throw new ConnectionLossException(e);
        }
    }
}
