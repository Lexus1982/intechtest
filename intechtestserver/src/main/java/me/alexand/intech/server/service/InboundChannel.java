package me.alexand.intech.server.service;

import me.alexand.intech.server.util.exceptions.ConnectionLossException;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author asidorov84@gmail.com
 */
public class InboundChannel extends Thread {
    private final DataInputStream dis;
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(20);

    public InboundChannel(InputStream is) {
        this.dis = new DataInputStream(is);
        setName("Inbound-channel-thread-" + getId());
        start();
    }

    public String getMessage() {
        String message = null;

        try {
            message = queue.take();
        } catch (InterruptedException e) {
            interrupt();
        }

        return message;
    }

    public void clear() {
        queue.clear();
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                String message = dis.readUTF();
                queue.offer(message);
            }
        } catch (IOException e) {
            throw new ConnectionLossException(e);
        }
    }

}
