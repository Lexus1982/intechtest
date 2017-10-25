package me.alexand.intech.server.repository.jpa;

import me.alexand.intech.server.model.Content;
import me.alexand.intech.server.model.ContentCategory;
import me.alexand.intech.server.repository.ContentRepository;
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
@Transactional(readOnly = true)
public class JPAContentRepositoryImpl implements ContentRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Content> getAllByCategory(ContentCategory category) {
        Query query = em.createNativeQuery("SELECT id, title, body, category, duration FROM contents" +
                " WHERE category = :category", Content.class)
                .setParameter("category", category.toString());

        return query.getResultList();
    }
}
