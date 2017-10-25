package me.alexand.intech.server.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static me.alexand.intech.server.util.MockData.POP_CONTENT_1;

/**
 * @author asidorov84@gmail.com
 */

@ContextConfiguration("classpath:spring-app.xml")
@RunWith(SpringRunner.class)
public class UsersContentRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersContentRepositoryTest.class);

    @Autowired
    private UsersContentRepository repository;

    @Test
    public void testGetUserContent() {
        Assert.assertEquals(Arrays.asList(POP_CONTENT_1), repository.get(123));
    }
}
