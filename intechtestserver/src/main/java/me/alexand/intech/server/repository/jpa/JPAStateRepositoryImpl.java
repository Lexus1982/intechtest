package me.alexand.intech.server.repository.jpa;

import me.alexand.intech.server.model.FSMState;
import me.alexand.intech.server.repository.StateRepository;
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
public class JPAStateRepositoryImpl implements StateRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public FSMState getInitState() {
        Query query = em.createNativeQuery("SELECT id, name, message, init_flag FROM states" +
                " WHERE init_flag = TRUE", FSMState.class);

        return (FSMState) query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<FSMState> getAll() {
        Query query = em.createNativeQuery("SELECT id, name, message, init_flag FROM states", FSMState.class);
        return new HashSet<>(query.getResultList());
    }
}
