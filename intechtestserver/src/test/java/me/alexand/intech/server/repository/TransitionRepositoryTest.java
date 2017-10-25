package me.alexand.intech.server.repository;

import me.alexand.intech.server.model.FSMEvent;
import me.alexand.intech.server.model.Transition;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static me.alexand.intech.server.util.MockData.*;

/**
 * @author asidorov84@gmail.com
 */

@ContextConfiguration("classpath:spring-app.xml")
@RunWith(SpringRunner.class)
public class TransitionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransitionRepositoryTest.class);

    @Autowired
    private TransitionRepository repository;

    @Test
    public void testGetAll() {
        Set<Transition> actual = repository.getAll();
        Set<Transition> expected = new HashSet<>(
                Arrays.asList(TRANSITION1,
                        TRANSITION2,
                        TRANSITION3,
                        TRANSITION4,
                        TRANSITION5,
                        TRANSITION6,
                        TRANSITION7,
                        TRANSITION8,
                        TRANSITION9,
                        TRANSITION10,
                        TRANSITION11,
                        TRANSITION12,
                        TRANSITION13,
                        TRANSITION14,
                        TRANSITION15,
                        TRANSITION16,
                        TRANSITION17,
                        TRANSITION18,
                        TRANSITION19,
                        TRANSITION20,
                        TRANSITION21));

        Assert.assertEquals(expected, actual);
    }
}
