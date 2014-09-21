package ru.gamedev.vote.models;

import org.junit.Test;
import ru.gamedev.vote.test.AbstractTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserFilterTest extends AbstractTest {

    @Test
    public void testFilter() throws Exception {
        List<Long> authorIds = Arrays.asList(10l, 20l, 30l);


        MessageFilter messageFilter = new UserFilter(true, authorIds);

        VoteDTO dto = new VoteDTO();
        dto.setAuthorId(20l);
        assertFalse(messageFilter.filter(dto));

        dto.setAuthorId(25l);
        assertTrue(messageFilter.filter(dto));

        messageFilter = new UserFilter(false, authorIds);

        dto.setAuthorId(20l);
        assertTrue(messageFilter.filter(dto));

        dto.setAuthorId(25l);
        assertFalse(messageFilter.filter(dto));
    }
}