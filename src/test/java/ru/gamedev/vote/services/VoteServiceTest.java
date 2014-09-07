package ru.gamedev.vote.services;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gamedev.vote.models.VotingDTO;
import ru.gamedev.vote.test.AbstractTest;

import java.util.Date;

public class VoteServiceTest extends AbstractTest {

    @Test
    //manual test
    @Ignore
    public void testGetVoting() throws Exception {
        VotingDTO dto = voteService.getVoting(156880l);
        int a = 0;
        dto = voteService.getVoting(192900l);
        int b = 0;

        long t = new Date().getTime() + 60 * 1000;
        while (t > new Date().getTime()){
            Thread.sleep(0);
        }

    }
}