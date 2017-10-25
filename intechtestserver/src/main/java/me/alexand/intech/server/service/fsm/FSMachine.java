package me.alexand.intech.server.service.fsm;

import javafx.util.Pair;
import me.alexand.intech.server.model.FSMEvent;
import me.alexand.intech.server.util.exceptions.IllegalFSMEvent;

/**
 * @author asidorov84@gmail.com
 */
public interface FSMachine {
    Pair<String, String> sendEvent(FSMEvent event) throws IllegalFSMEvent;
}
