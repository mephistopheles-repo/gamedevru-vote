package ru.gamedev.vote.models;

import org.junit.Test;
import ru.gamedev.vote.test.AbstractTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WordFilterTest extends AbstractTest {

    @Test
    public void testFilter() throws Exception {
        List<String> authorVotes = Arrays.asList(
                "купилки"
                , "вареции"
                , "другие слова");


        MessageFilter messageFilter = new WordFilter(true, authorVotes);

        VoteDTO dto = new VoteDTO();
        dto.setVote("вареции");
        assertFalse(messageFilter.filter(dto));

        dto.setVote("adasdaqgqwg");
        assertTrue(messageFilter.filter(dto));

        messageFilter = new WordFilter(false, authorVotes);

        dto.setVote("вареции");
        assertTrue(messageFilter.filter(dto));

        dto.setVote("adasdaqgqwg");
        assertFalse(messageFilter.filter(dto));
    }
}