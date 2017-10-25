package me.alexand.intech.server.repository;

import me.alexand.intech.server.model.Content;
import me.alexand.intech.server.model.ContentCategory;

import java.util.Collection;

/**
 * @author asidorov84@gmail.com
 */
public interface ContentRepository {
    Collection<Content> getAllByCategory(ContentCategory category);
}
