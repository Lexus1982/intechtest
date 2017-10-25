package me.alexand.intech.server.repository;

import me.alexand.intech.server.model.FSMState;

import java.util.Set;

/**
 * @author asidorov84@gmail.com
 */
public interface StateRepository {
    FSMState getInitState();

    Set<FSMState> getAll();
}
