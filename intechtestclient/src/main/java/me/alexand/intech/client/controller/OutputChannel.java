package me.alexand.intech.client.controller;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author asidorov84@gmail.com
 */
public class OutputChannel extends Thread {
    private final DataOutputStream os;
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(20);

    public OutputChannel(OutputStream os) {
        this.os = new DataOutputStream(os);
        setName("Input-channel-thread-" + getId());
        start();
    }

    public void sendMessage(String message) {
        queue.offer(message);
    }

    @Override
    public void run() {
        try {
            while (!interrupted()) {
                String message = queue.take();
                os.writeUTF(message);
                os.flush();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            throw new ConnectionLossException(e);
        }
    }
}
