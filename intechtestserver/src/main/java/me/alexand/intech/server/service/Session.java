package me.alexand.intech.server.service;

import javafx.util.Pair;
import me.alexand.intech.server.model.Command;
import me.alexand.intech.server.model.Content;
import me.alexand.intech.server.model.ContentCategory;
import me.alexand.intech.server.model.FSMEvent;
import me.alexand.intech.server.repository.ContentRepository;
import me.alexand.intech.server.repository.UsersContentRepository;
import me.alexand.intech.server.service.fsm.FSMachine;
import me.alexand.intech.server.util.CommandUtils;
import me.alexand.intech.server.util.exceptions.IllegalFSMEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * @author asidorov84@gmail.com
 */

@Component
@Scope(scopeName = "prototype")
public class Session extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(Session.class);
    private static final String BUY_MESSAGE = "Мелодия \"%s\" добавлена в Ваш личный кабинет";
    private static final String REMOVE_MESSAGE = "Мелодия \"%s\" удалена из Вашего кабинета";
    private static final int MESSAGE_DELAY = 3000;

    private final ContentRepository contentRepository;
    private final UsersContentRepository usersContentRepository;

    private int userID;
    private InboundChannel inChannel;
    private OutboundChannel outChannel;
    private ContentPlayer player;

    private FSMachine machine;

    @Autowired
    public Session(ContentRepository contentRepository,
                   UsersContentRepository usersContentRepository,
                   FSMachine machine) {
        this.machine = machine;
        this.contentRepository = contentRepository;
        this.usersContentRepository = usersContentRepository;
        setName("Session-thread-" + getId());
    }

    public void start(int userID, InputStream is, OutputStream os) {
        this.userID = userID;
        setChannels(is, os);
        start();
    }

    @Override
    public void run() {
        LOGGER.info("UserID {}: session started", userID);
        Pair<String, String> machineResponse;

        try {
            machineResponse = machine.sendEvent(FSMEvent.LOGIN);
            String stateMessage = machineResponse.getKey();
            sendResponse(stateMessage);

            String userRequest;

            while (!isInterrupted()) {
                userRequest = getRequest();

                try {

                    machineResponse = machine.sendEvent(FSMEvent.valueOf(userRequest));
                    processTransition(machineResponse.getKey(), machineResponse.getValue());

                } catch (IllegalArgumentException e) {
                    LOGGER.error("UserID {}: illegal request received ", userID, e.getMessage());
                } catch (IllegalFSMEvent e) {
                    LOGGER.error("UserID {}: illegal FSM event: {}", userID, e.getMessage());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }

        inChannel.interrupt();
        outChannel.interrupt();
        if (player != null) player.interrupt();
        LOGGER.info("UserID {}: connection loss", userID);
    }

    private void processTransition(String stateMessage, String transitionAction) throws InterruptedException {
        Pair<Command, String> pair = CommandUtils.parseAction(transitionAction);
        Command command = pair.getKey();
        String commandParameter = pair.getValue();
        Content currentTrack;

        LOGGER.debug("UserID {}: execute command {}", userID, command.toString());

        switch (command) {
            case PLAY:
                sendResponse(stateMessage);
                Thread.sleep(MESSAGE_DELAY);
                LOGGER.debug("UserID {}: start playing content with category {}", userID, commandParameter);
                player = new ContentPlayer(contentRepository.getAllByCategory(ContentCategory.valueOf(commandParameter)));
                inChannel.clear();
                break;

            case PLAYLK:
                sendResponse(stateMessage);
                Thread.sleep(MESSAGE_DELAY);
                LOGGER.debug("UserID {}: start playing personal content {}", userID);
                player = new ContentPlayer(usersContentRepository.get(userID));
                inChannel.clear();
                break;

            case NEXT:
                player.nextContent();
                break;

            case STOP:
                player.stopPlaying();
                player.join();
                sendResponse(stateMessage);
                break;

            case BUY:
                player.pausePlaying();
                currentTrack = player.getCurrentTrack();
                sendResponse(String.format(BUY_MESSAGE, currentTrack.getTitle()));
                usersContentRepository.buy(userID, currentTrack);
                Thread.sleep(MESSAGE_DELAY);
                player.resumePlaying();
                inChannel.clear();
                break;

            case REMOVE:
                player.pausePlaying();
                currentTrack = player.getCurrentTrack();
                sendResponse(String.format(REMOVE_MESSAGE, currentTrack.getTitle()));
                usersContentRepository.remove(userID, currentTrack);
                Thread.sleep(MESSAGE_DELAY);
                player.resumePlaying();
                inChannel.clear();
                break;

            case NOP:
                sendResponse(stateMessage);
        }
    }

    private void setChannels(InputStream is, OutputStream os) {
        inChannel = new InboundChannel(is);
        outChannel = new OutboundChannel(os);

        UncaughtExceptionHandler channelsExceptionsHandler = (t, e) -> {
            LOGGER.error(e.toString());
            interrupt();
        };

        inChannel.setUncaughtExceptionHandler(channelsExceptionsHandler);
        outChannel.setUncaughtExceptionHandler(channelsExceptionsHandler);
    }


    private void sendResponse(String response) {
        LOGGER.debug("UserID {}: sending response: {}", userID, response);
        outChannel.sendMessage(response);
    }

    private String getRequest() {
        String request = inChannel.getMessage();
        LOGGER.debug("UserID {}: got request: {}", userID, request);
        return request;
    }

    private class ContentPlayer extends Thread {
        private boolean pause = false;
        private final Object monitor = new Object();
        private final Collection<Content> playlist;
        private Content currentTrack;

        ContentPlayer(Collection<Content> playlist) {
            this.playlist = playlist;
            setName("Content-player-thread-" + getId());
            start();
        }

        void nextContent() {
            synchronized (monitor) {
                monitor.notify();
            }
        }

        void pausePlaying() {
            synchronized (monitor) {
                pause = true;
                monitor.notify();
            }
        }

        void resumePlaying() {
            if (pause) {
                synchronized (monitor) {
                    pause = false;
                    monitor.notify();
                }
            }
        }

        void stopPlaying() {
            interrupt();
        }

        Content getCurrentTrack() {
            synchronized (monitor) {
                return currentTrack;
            }
        }

        @Override
        public void run() {
            synchronized (monitor) {
                while (true) {
                    for (Content content : playlist) {
                        LOGGER.debug("UserID {}: play next content", userID);
                        try {
                            sendResponse(content.getBody());
                            currentTrack = content;
                            monitor.wait(content.getDuration() * 1000);
                            if (pause) monitor.wait();
                        } catch (InterruptedException e) {
                            LOGGER.debug("UserID {}: player stopped", userID);
                            return;
                        }
                    }
                }
            }
        }
    }
}

