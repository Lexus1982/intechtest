package me.alexand.intech.server.repository.jpa;

import me.alexand.intech.server.model.Transition;
import me.alexand.intech.server.repository.TransitionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

/**
 * @author asidorov84@gmail.com
 */

@Repository
@Transactional(readOnly = true)
public class JPATransitionRepositoryImpl implements TransitionRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public Set<Transition> getAll() {
        //TODO
        Query query = em.createNativeQuery("SELECT * FROM transitions", Transition.class);

        return new HashSet<>(query.getResultList());
    }
}
