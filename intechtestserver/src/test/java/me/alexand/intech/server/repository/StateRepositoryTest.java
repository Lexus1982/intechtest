package me.alexand.intech.server.repository;

import me.alexand.intech.server.model.FSMState;
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
public class StateRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(StateRepositoryTest.class);

    @Autowired
    private StateRepository repository;

    @Test
    public void testGetInitState() {
        FSMState actualState = repository.getInitState();
        Assert.assertEquals(INIT, actualState);
    }

    @Test
    public void testGetAll() {
        Set<FSMState> actual = repository.getAll();
        Set<FSMState> expected = new HashSet<>(
                Arrays.asList(INIT,
                        MENU_0,
                        MENU_1,
                        MENU_2,
                        MENU_3,
                        MENU_4,
                        LISTEN_1,
                        LISTEN_2));

        System.out.println(actual.size());

        Assert.assertEquals(expected, actual);
    }
}
