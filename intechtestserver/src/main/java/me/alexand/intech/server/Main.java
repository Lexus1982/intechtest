package me.alexand.intech.server;

import me.alexand.intech.server.service.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author asidorov84@gmail.com
 */


public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final int SERVER_PORT = 7777;


    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring-app.xml");

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            LOGGER.info("Start listening on 0.0.0.0:{}", SERVER_PORT);

            while (true) {
                Socket socket = serverSocket.accept();

                InetAddress remoteAddress = socket.getInetAddress();
                int remotePort = socket.getPort();

                LOGGER.debug("Get connection from {}:{}", remoteAddress.getHostAddress(), remotePort);

                //Сразу читаем id пользователя, сессия создается на основе ID

                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();

                String request = new DataInputStream(is).readUTF();
                int userID;

                try {
                    userID = Integer.parseInt(request);
                    LOGGER.info("User ID {}: Starting session...", userID);

                    Session session = appCtx.getBean(Session.class);
                    session.start(userID, is, os);
                } catch (NumberFormatException e) {
                    LOGGER.error(e.getMessage());
                    is.close();
                    os.close();
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

    }
}
