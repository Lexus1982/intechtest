package me.alexand.intech.server.repository;

import me.alexand.intech.server.model.Content;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static me.alexand.intech.server.model.ContentCategory.HITS;
import static me.alexand.intech.server.util.MockData.*;

/**
 * @author asidorov84@gmail.com
 */

@ContextConfiguration("classpath:spring-app.xml")
@RunWith(SpringRunner.class)
public class ContentRepositoryTest {
    @Autowired
    private ContentRepository repository;

    @Test
    public void testGetAllByCategory() {
        Set<Content> actual = new HashSet<>(repository.getAllByCategory(HITS));

        Set<Content> expected = new HashSet<>(
                Arrays.asList(HITS_CONTENT_1,
                        HITS_CONTENT_2,
                        HITS_CONTENT_3));

        Assert.assertEquals(expected, actual);
    }
}
