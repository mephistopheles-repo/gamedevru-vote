package ru.gamedev.vote.models;

import org.junit.Test;
import ru.gamedev.vote.test.AbstractTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LevelFilterTest extends AbstractTest {

    @Test
    public void testFilter() throws Exception {

        List<AuthorLevel> authorLevels = Arrays.asList(
                AuthorLevel.DONATOR
                , AuthorLevel.USER
                , AuthorLevel.OLD_USER);


        MessageFilter messageFilter = new LevelFilter(true, authorLevels);

        VoteDTO dto = new VoteDTO();
        dto.setAuthorLevel(AuthorLevel.DONATOR);
        assertFalse(messageFilter.filter(dto));

        dto.setAuthorLevel(AuthorLevel.BANNED);
        assertTrue(messageFilter.filter(dto));

        messageFilter = new LevelFilter(false, authorLevels);

        dto.setAuthorLevel(AuthorLevel.DONATOR);
        assertTrue(messageFilter.filter(dto));

        dto.setAuthorLevel(AuthorLevel.BANNED);
        assertFalse(messageFilter.filter(dto));
    }
}