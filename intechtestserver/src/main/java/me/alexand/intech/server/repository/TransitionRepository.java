package me.alexand.intech.server.repository;

import me.alexand.intech.server.model.FSMEvent;
import me.alexand.intech.server.model.FSMState;
import me.alexand.intech.server.model.Transition;

import java.util.Set;

/**
 * @author asidorov84@gmail.com
 */
public interface TransitionRepository {
    Set<Transition> getAll();
}
