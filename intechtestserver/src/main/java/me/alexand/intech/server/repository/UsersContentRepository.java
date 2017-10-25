package me.alexand.intech.server.repository;

import me.alexand.intech.server.model.Content;

import java.util.Collection;

/**
 * @author asidorov84@gmail.com
 */
public interface UsersContentRepository {

    Collection<Content> get(int userID);

    void buy(int userID, Content currentTrack);

    void remove(int userID, Content currentTrack);
}
