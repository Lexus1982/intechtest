package me.alexand.intech.server.repository.jpa;

import me.alexand.intech.server.model.Content;
import me.alexand.intech.server.repository.UsersContentRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

/**
 * @author asidorov84@gmail.com
 */

@Repository
public class JPAUsersContentRepositoryImpl implements UsersContentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings(value = "unchecked")
    public Collection<Content> get(int userID) {
        Query query = em.createNativeQuery("SELECT c.id, c.title, c.body, c.category, c.duration FROM users_content u" +
                " INNER JOIN contents c ON u.content_id = c.id WHERE u.user_id = :user", Content.class)
                .setParameter("user", userID);

        return query.getResultList();
    }

    @Override
    @Transactional
    public void buy(int userID, Content currentTrack) {
        Query query = em.createNativeQuery("SELECT 1 FROM users_content" +
                " WHERE user_id = :userID AND content_id = :contentID")
                .setParameter("userID", userID)
                .setParameter("contentID", currentTrack.getId());

        if (query.getResultList().size() == 0) {
            query = em.createNativeQuery("INSERT INTO users_content VALUES (:userID, :contentID)")
                    .setParameter("userID", userID)
                    .setParameter("contentID", currentTrack.getId());

            query.executeUpdate();
        }
    }

    @Override
    @Transactional
    public void remove(int userID, Content currentTrack) {
        Query query = em.createNativeQuery("DELETE FROM users_content" +
                " WHERE user_id = :userID AND content_id = :contentID")
                .setParameter("userID", userID)
                .setParameter("contentID", currentTrack.getId());

        query.executeUpdate();
    }
}
