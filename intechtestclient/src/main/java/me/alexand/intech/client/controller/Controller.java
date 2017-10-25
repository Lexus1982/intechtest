package me.alexand.intech.client.controller;

import me.alexand.intech.client.view.MainForm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author asidorov84@gmail.com
 */
public class Controller {
    private static final String DEFAULT_SRV_ADDR = "localhost";
    private static final int DEFAULT_SRV_PORT = 7777;

    private static Controller ourInstance = new Controller();

    private MainForm mainForm;
    private InputChannel inChannel;
    private OutputChannel outChannel;
    private Thread.UncaughtExceptionHandler channelsExceptionsHandler = (t, e) -> {
        mainForm.showErrorMessage("Завершение", "Связь с сервером потеряна");
        exit();
    };

    public static Controller getInstance() {
        return ourInstance;
    }

    private Controller() {
    }

    public void start() {
        mainForm = new MainForm();
        mainForm.showForm();
    }

    public boolean connect(String input) {
        if (input == null) exit();

        String srvAddress;
        int srvPort;

        if (input.length() != 0) {
            srvAddress = input.split(":")[0];
            srvPort = Integer.parseInt(input.split(":")[1]);
        } else {
            srvAddress = DEFAULT_SRV_ADDR;
            srvPort = DEFAULT_SRV_PORT;
        }

        try {
            Socket socket = new Socket();
            System.out.println("Try to connect to " + srvAddress + ":" + srvPort);
            socket.connect(new InetSocketAddress(srvAddress, srvPort), 2000);
            inChannel = new InputChannel(socket.getInputStream(), new ReceivedMessageHandler());
            outChannel = new OutputChannel(socket.getOutputStream());
            inChannel.setUncaughtExceptionHandler(channelsExceptionsHandler);
            outChannel.setUncaughtExceptionHandler(channelsExceptionsHandler);
        } catch (IOException e) {
            mainForm.showErrorMessage("Ошибка", "Не могу подключиться к серверу " + input);
            return false;
        }

        return true;
    }

    private void exit() {
        System.exit(0);
    }

    public void sendMessage(String message) {
        outChannel.sendMessage(message);
    }

    private class ReceivedMessageHandler implements MessageListener {
        @Override
        public void handle(String message) {
            mainForm.showMessage(message);
        }
    }
}
